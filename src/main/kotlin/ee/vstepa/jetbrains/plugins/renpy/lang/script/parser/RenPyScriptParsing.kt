package ee.vstepa.jetbrains.plugins.renpy.lang.script.parser

import com.intellij.lang.PsiBuilder
import com.intellij.lang.PsiBuilder.Marker
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.util.NlsContexts.ParsingError
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.type.RenPyScriptElementType
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.type.RenPyScriptElementTypes
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.type.audio.RenPyScriptAudioControlStmtElementType
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.token.RenPyScriptTokenSets
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.token.RenPyScriptTokenType
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.token.RenPyScriptTokenTypes


val LOG = Logger.getInstance(RenPyScriptParsing::class.java)


open class RenPyScriptParsing(private val builder: PsiBuilder) {

    open fun parseScript() {
        mark().also {
            parseStatementsList()
            advance()
            it.done(RenPyScriptElementTypes.REN_PY_SCRIPT)
        }
    }

    protected open fun parseStatementsList(advance: Boolean = false): Boolean {
        val statementsListMarker = mark()
        if (advance) advance()

        while (!eof()) {
            val token = token()
            var success = true
            when {
                // Indents are handled by statements, if we encounter indent as statement start char during parsing
                // statements list - indentation error
                token == RenPyScriptTokenTypes.INDENT -> {
                    mark().also {
                        advance()
                        flushError(
                            it,
                            "Line is indented, but the preceding statement does not expect a block. Please check this line's indentation."
                        )
                    }
                    success = parseStatementsList()
                }

                // Skipping trailing new lines
                token == RenPyScriptTokenTypes.NEW_LINE -> advance()

                // Start of dialog statement, with character or without
                isTokenDialogueStatementStart(token) -> success = parseDialogStatement()

                // Start of label, menu and other block statements
                isTokenBlockStatementStart(token) -> success = parseBlockStatement()

                // Start of jump ... or jump expression ...
                token == RenPyScriptTokenTypes.JUMP_KEYWORD -> success = parseJumpStatement()

                // Start of show, scene, hide or with statement
                isTokenImageDisplayControlStatementStart(token) -> success = parseImageDisplayControlStatement()

                // One-line python statement, e.g., $ var_1 += 2
                token == RenPyScriptTokenTypes.ONE_LINE_PYTHON_STATEMENT -> success = parseQuickPythonStatement()

                // 'pass' statement in statements list
                token == RenPyScriptTokenTypes.PASS_KEYWORD -> success = parsePassStatement()

                // 'return' statement in statements list
                token == RenPyScriptTokenTypes.RETURN_KEYWORD -> success = parseReturnStatement()

                // 'play', 'stop' or 'queue' statements
                isTokenAudioControlStatementStart(token) -> success = parseAudioControlStatement()

                // 'pause' statement
                token == RenPyScriptTokenTypes.PAUSE_KEYWORD -> success = parsePauseStatement()

                // End of current statements list
                token == RenPyScriptTokenTypes.DEDENT -> {
                    advance()
                    break
                }

                else -> {
                    mark().also {
                        advance()
                        flushError(it, "Unexpected statement start token: $token")
                    }
                    success = false
                }
            }

            if (!success) advanceToNewLineOrEqualOrEof()
        }

        statementsListMarker.done(RenPyScriptElementTypes.STMTS_LIST)
        return true
    }

    protected open fun parseBlockStatement(): Boolean {
        when (val token = token()) {
            RenPyScriptTokenTypes.LABEL_KEYWORD -> {
                return parseLabel()
            }
            RenPyScriptTokenTypes.MENU_KEYWORD -> {
                return parseMenu()
            }
            else -> {
                this.builder.error("Invalid block statement start token: $token")
                return true
            }
        }
    }

    protected open fun parseLabel(): Boolean {
        if (token() != RenPyScriptTokenTypes.LABEL_KEYWORD) {
            error("Invalid label start token: ${token()}")
            return false
        }

        val labelMarker = mark()
        val labelStatementMarker = mark()

        val labelStatementKeywordMarker = mark()
        advance()
        labelStatementKeywordMarker.done(RenPyScriptElementTypes.LABEL_STMT_KEYWORD)

        var success = true
        val labelNameMarker = mark()
        if (token() == RenPyScriptTokenTypes.IDENTIFIER) {
            advance()
            labelNameMarker.done(RenPyScriptElementTypes.LABEL_STMT_NAME)
        } else {
            error("Label name expected")
            labelNameMarker.drop()
            success = false
        }

        val labelColonMarker = mark()
        if (token() == RenPyScriptTokenTypes.COLON) {
            advance()
            labelColonMarker.done(RenPyScriptElementTypes.LABEL_STMT_COLON)
        } else {
            error("Colon is expected at the end of the label statement")
            labelColonMarker.drop()
            success = false
        }

        success = verifyTokenIsNewLineOrEqualOrEof("New line is expected at the end of the label statement") && success

        labelStatementMarker.done(RenPyScriptElementTypes.LABEL_STMT)

        if (success && !eof() && lookAheadForIndentAfterNewLines()) {
            success = parseStatementsList(advance = true)
        }

        labelMarker.done(RenPyScriptElementTypes.LABEL)
        return success
    }

    protected open fun parseMenu(): Boolean {
        return false
    }

    protected open fun parseDialogStatement(): Boolean {
        if (!isTokenDialogueStatementStart()) {
            error("Invalid dialog statement start token: ${token()}")
            return false
        }

        val dialogStatementMarker = mark()

        when (token()) {
            RenPyScriptTokenTypes.IDENTIFIER -> {
                val dialogStatementIdentifierMarker = mark()
                advance()
                dialogStatementIdentifierMarker.done(RenPyScriptElementTypes.DIALOG_STMT_IDENTIFIER)
            }
            RenPyScriptTokenTypes.STRING if (isTokenDialogueStatementTextStart(tokenAhead())) -> {
                // Our token text identifier is also a text line
                // Dialog line looks like this: "John" "Hello, my name is John!"
                mark().also {
                    advance()
                    it.done(RenPyScriptElementTypes.DIALOG_STMT_TEXT_IDENTIFIER)
                }
            }
        }

        var success = true
        mark().also {
            if (isTokenDialogueStatementTextStart()) {
                advance()
                it.done(RenPyScriptElementTypes.DIALOG_STMT_TEXT)
            } else {
                error("Dialog statement text expected")
                it.drop()
                success = false
            }
        }

        success = verifyTokenIsNewLineOrEqualOrEof("New line is expected at the end of the dialog statement") && success

        dialogStatementMarker.done(RenPyScriptElementTypes.DIALOG_STMT)
        return success
    }

    protected open fun parseJumpStatement(): Boolean {
        var token = token()
        if (token != RenPyScriptTokenTypes.JUMP_KEYWORD) {
            error("Invalid jump statement start token: $token")
            return false
        }

        val jumpStatementMarker = mark()

        val jumpStatementKeywordMarker = mark()
        advance()
        jumpStatementKeywordMarker.done(RenPyScriptElementTypes.JUMP_STMT_KEYWORD)

        var success = true
        token = token()
        when (token) {
            RenPyScriptTokenTypes.EXPRESSION_KEYWORD -> {
                val jumpStatementExpressionMarker = mark()
                val jumpStatementExpressionKeywordMarker = mark()
                advance()
                jumpStatementExpressionKeywordMarker.done(RenPyScriptElementTypes.JUMP_STMT_EXPRESSION_KEYWORD)

                val jumpStatementExpressionValueMarker = mark()
                if (isTokenExpressionValue()) {
                    advance()
                    jumpStatementExpressionValueMarker.done(RenPyScriptElementTypes.JUMP_STMT_EXPRESSION_VALUE)
                } else {
                    error("Jump statement expression expected")
                    jumpStatementExpressionValueMarker.drop()
                    success = false
                }

                jumpStatementExpressionMarker.done(RenPyScriptElementTypes.JUMP_STMT_EXPRESSION)
            }
            RenPyScriptTokenTypes.IDENTIFIER -> {
                val jumpStatementTargetMarker = mark()
                advance()
                jumpStatementTargetMarker.done(RenPyScriptElementTypes.JUMP_STMT_TARGET)
            }
            else -> {
                error("Jump statement target or expression expected")
                success = false
            }
        }

        success = verifyTokenIsNewLineOrEqualOrEof("New line is expected at the end of the jump statement") && success

        jumpStatementMarker.done(RenPyScriptElementTypes.JUMP_STMT)
        return success
    }

    protected open fun parseImageDisplayControlStatement(): Boolean {
        return when (val token = token()) {
            RenPyScriptTokenTypes.SHOW_KEYWORD -> parseShowStatement()
            RenPyScriptTokenTypes.SCENE_KEYWORD -> parseSceneStatement()
            RenPyScriptTokenTypes.HIDE_KEYWORD -> parseHideStatement()
            RenPyScriptTokenTypes.WITH_KEYWORD -> parseWithStatement()

            else -> {
                this.builder.error("Invalid image display control statement start token: $token")
                return false
            }
        }
    }

    protected open fun parseShowOrSceneStatement(
        stmtKeywordToken: RenPyScriptTokenType,
        allowSingleKeyword: Boolean,
        stmtElementType: RenPyScriptElementType,
        stmtKeywordElementType: RenPyScriptElementType,
        stmtImageElementType: RenPyScriptElementType,
        stmtImagePartElementType: RenPyScriptElementType,
        stmtExpressionElementType: RenPyScriptElementType,
        stmtExpressionKeywordElementType: RenPyScriptElementType,
        stmtExpressionValueElementType: RenPyScriptElementType,
        stmtPropsListElementType: RenPyScriptElementType,
        stmtPropElementType: RenPyScriptElementType,
        stmtPropKeywordElementType: RenPyScriptElementType,
        stmtPropValueElementType: RenPyScriptElementType,
        stmtWithClauseElementType: RenPyScriptElementType,
        stmtWithClauseKeywordElementType: RenPyScriptElementType,
        stmtWithClauseValueElementType: RenPyScriptElementType,
        stmtColonElementType: RenPyScriptElementType,
        stmtATLElementType: RenPyScriptElementType,
    ): Boolean {
        val stmtName = when (stmtKeywordToken) {
            RenPyScriptTokenTypes.SHOW_KEYWORD -> {
                "show"
            }
            RenPyScriptTokenTypes.SCENE_KEYWORD -> {
                "scene"
            }
            else -> {
                error("'Show' or 'scene' statement expected, got: $stmtKeywordToken")
                return false
            }
        }

        var token = token()
        if (token != stmtKeywordToken) {
            error("Invalid '$stmtName' statement start token: $token")
            return false
        }

        val stmtMarker = mark()

        // Mark keyword token with element type
        mark().also {
            advance()
            it.done(stmtKeywordElementType)
        }

        var success = true
        token = token()
        when {
            token == RenPyScriptTokenTypes.EXPRESSION_KEYWORD -> {
                val stmtExpressionMarker = mark()

                // Mark statement expression keyword token with element type
                mark().also {
                    advance()
                    it.done(stmtExpressionKeywordElementType)
                }

                // Mark or drop statement expression value
                mark().also {
                    if (isTokenExpressionValue()) {
                        advance()
                        it.done(stmtExpressionValueElementType)
                    } else {
                        error("'$stmtName' statement expression expected")
                        it.drop()
                        success = false
                    }
                }

                stmtExpressionMarker.done(stmtExpressionElementType)
            }
            isTokenImageLabelCompatibleIdentifier(token) -> {
                val stmtImageMarker = mark()
                while (isTokenImageLabelCompatibleIdentifier()) {
                    mark().also {
                        advance()
                        it.done(stmtImagePartElementType)
                    }
                }
                stmtImageMarker.done(stmtImageElementType)
            }
            else -> {
                if (allowSingleKeyword && (eof() || isTokenNewLineOrSimilarToIt(token))) {
                    // If single keyword without any image is allowed for the statement - accept it and exit here
                    stmtMarker.done(stmtElementType)
                    return true
                }
                error("'$stmtName' statement image identifier or expression expected")
                success = false
            }
        }

        if (isTokenShowOrScenePropName()) {
            val stmtPropsListMarker = mark()

            while (true) {
                val propKeywordToken = token()

                val stmtPropMarker = mark()

                mark().also {
                    advance()
                    it.done(stmtPropKeywordElementType)
                }

                val stmtPropValMarker = mark()
                val currentToken = token()
                when (propKeywordToken) {
                    RenPyScriptTokenTypes.AS_KEYWORD, RenPyScriptTokenTypes.ONLAYER_KEYWORD -> {
                        if (currentToken == RenPyScriptTokenTypes.IDENTIFIER) {
                            advance()
                            stmtPropValMarker.done(stmtPropValueElementType)
                        } else {
                            if (propKeywordToken == RenPyScriptTokenTypes.AS_KEYWORD) {
                                error("A 'name' is expected as 'as' property value")
                            } else {
                                error("A 'name' of layer is expected as 'onlayer' property value")
                            }
                            stmtPropValMarker.drop()
                            success = false
                        }
                    }
                    RenPyScriptTokenTypes.AT_KEYWORD, RenPyScriptTokenTypes.BEHIND_KEYWORD -> {
                        if (currentToken == RenPyScriptTokenTypes.IDENTIFIER) {
                            advance()
                            while (token() == RenPyScriptTokenTypes.COMMA && tokenAhead() == RenPyScriptTokenTypes.IDENTIFIER) {
                                advance()
                                advance()
                            }
                            stmtPropValMarker.done(stmtPropValueElementType)
                        } else {
                            if (propKeywordToken == RenPyScriptTokenTypes.AT_KEYWORD) {
                                error("An 'expression' or a list of 'expressions' is expected as 'at' property value")
                            } else {
                                error("A 'name' or a list of 'names' is expected as 'behind' property value")
                            }
                            stmtPropValMarker.drop()
                            success = false
                        }
                    }
                    RenPyScriptTokenTypes.ZORDER_KEYWORD -> {
                        if (currentToken == RenPyScriptTokenTypes.IDENTIFIER || currentToken == RenPyScriptTokenTypes.STRING || currentToken == RenPyScriptTokenTypes.PLAIN_NUMBER) {
                            advance()
                            stmtPropValMarker.done(stmtPropValueElementType)
                        } else {
                            error("An 'integer' is expected as 'zorder' property value")
                            stmtPropValMarker.drop()
                            success = false
                        }
                    }
                    else -> {
                        error("Unexpected '$stmtName' property keyword: $propKeywordToken")
                        stmtPropValMarker.drop()
                        success = false
                    }
                }

                stmtPropMarker.done(stmtPropElementType)

                if (eof() || isTokenNewLineOrSimilarToIt() || !isTokenShowOrScenePropName()) break
            }

            stmtPropsListMarker.done(stmtPropsListElementType)
        }

        token = token()
        if (token == RenPyScriptTokenTypes.WITH_KEYWORD) {
            val stmtWithClauseMarker = mark()

            mark().also {
                advance()
                it.done(stmtWithClauseKeywordElementType)
            }

            mark().also {
                var failMessage: String? = null
                when {
                    isCurrentlyAtPythonMethodCallStart() -> {
                        if (!parsePythonMethodCall()) failMessage = "Failed to parse '$stmtName' statement 'with' clause value python method call"
                    }
                    token() == RenPyScriptTokenTypes.IDENTIFIER -> advance()

                    else -> failMessage = "An 'expression' is expected as '$stmtName' statement 'with' clause value"
                }

                if (failMessage != null) {
                    error(failMessage)
                    it.drop()
                    success = false
                } else {
                    it.done(stmtWithClauseValueElementType)
                }
            }

            stmtWithClauseMarker.done(stmtWithClauseElementType)
        }

        if (token() == RenPyScriptTokenTypes.COLON) {
            mark().also {
                advance()
                it.done(stmtColonElementType)
            }

            if (token() != RenPyScriptTokenTypes.INDENT) {
                error("An indented ATL block is expected after '$stmtName' statement finishing colon")
                success = false
            } else {
                mark().also {
                    advanceToDedentOfCurrentBlockOrEof()
                    it.done(stmtATLElementType)
                }
            }
        } else {
            success = verifyTokenIsNewLineOrEqualOrEof("New line is expected at the end of the '$stmtName' statement") && success
        }

        stmtMarker.done(stmtElementType)
        return success
    }

    protected open fun parseShowStatement(): Boolean = parseShowOrSceneStatement(
        RenPyScriptTokenTypes.SHOW_KEYWORD,
        false,
        RenPyScriptElementTypes.SHOW_STMT,
        RenPyScriptElementTypes.SHOW_STMT_KEYWORD,
        RenPyScriptElementTypes.SHOW_STMT_IMAGE,
        RenPyScriptElementTypes.SHOW_STMT_IMAGE_PART,
        RenPyScriptElementTypes.SHOW_STMT_EXPRESSION,
        RenPyScriptElementTypes.SHOW_STMT_EXPRESSION_KEYWORD,
        RenPyScriptElementTypes.SHOW_STMT_EXPRESSION_VALUE,
        RenPyScriptElementTypes.SHOW_STMT_PROPS_LIST,
        RenPyScriptElementTypes.SHOW_STMT_PROP,
        RenPyScriptElementTypes.SHOW_STMT_PROP_KEYWORD,
        RenPyScriptElementTypes.SHOW_STMT_PROP_VALUE,
        RenPyScriptElementTypes.SHOW_STMT_WITH_CLAUSE,
        RenPyScriptElementTypes.SHOW_STMT_WITH_CLAUSE_KEYWORD,
        RenPyScriptElementTypes.SHOW_STMT_WITH_CLAUSE_VALUE,
        RenPyScriptElementTypes.SHOW_STMT_COLON,
        RenPyScriptElementTypes.SHOW_STMT_ATL,
    )

    protected open fun parseSceneStatement(): Boolean = parseShowOrSceneStatement(
        RenPyScriptTokenTypes.SCENE_KEYWORD,
        true,
        RenPyScriptElementTypes.SCENE_STMT,
        RenPyScriptElementTypes.SCENE_STMT_KEYWORD,
        RenPyScriptElementTypes.SCENE_STMT_IMAGE,
        RenPyScriptElementTypes.SCENE_STMT_IMAGE_PART,
        RenPyScriptElementTypes.SCENE_STMT_EXPRESSION,
        RenPyScriptElementTypes.SCENE_STMT_EXPRESSION_KEYWORD,
        RenPyScriptElementTypes.SCENE_STMT_EXPRESSION_VALUE,
        RenPyScriptElementTypes.SCENE_STMT_PROPS_LIST,
        RenPyScriptElementTypes.SCENE_STMT_PROP,
        RenPyScriptElementTypes.SCENE_STMT_PROP_KEYWORD,
        RenPyScriptElementTypes.SCENE_STMT_PROP_VALUE,
        RenPyScriptElementTypes.SCENE_STMT_WITH_CLAUSE,
        RenPyScriptElementTypes.SCENE_STMT_WITH_CLAUSE_KEYWORD,
        RenPyScriptElementTypes.SCENE_STMT_WITH_CLAUSE_VALUE,
        RenPyScriptElementTypes.SCENE_STMT_COLON,
        RenPyScriptElementTypes.SCENE_STMT_ATL,
    )

    protected open fun parseWithStatement(): Boolean {
        token().also {
            if (it != RenPyScriptTokenTypes.WITH_KEYWORD) {
                error("Invalid 'with' statement start token: $it")
                return false
            }
        }

        var success = true
        mark().also { stmtMarker ->

            // Mark keyword token with element type
            mark().also { keywordMarker ->
                advance()
                keywordMarker.done(RenPyScriptElementTypes.WITH_STMT_KEYWORD)
            }

            val token = token()
            mark().also { valueMarker ->
                var markerElementType: IElementType? = null
                when {
                    token == RenPyScriptTokenTypes.NONE -> {
                        advance()
                        markerElementType = RenPyScriptElementTypes.WITH_STMT_NONE_OBJ
                    }
                    isCurrentlyAtPythonMethodCallStart() -> {
                        parsePythonMethodCall()
                        markerElementType = RenPyScriptElementTypes.WITH_STMT_TRANSITION_OBJ
                    }
                    token == RenPyScriptTokenTypes.IDENTIFIER -> {
                        advance()
                        markerElementType = RenPyScriptElementTypes.WITH_STMT_TRANSITION_OBJ
                    }

                    else -> {
                        error("'with' statement transition object or None expected")
                    }
                }

                if (markerElementType == null) {
                    valueMarker.drop()
                    success = false
                } else {
                    valueMarker.done(markerElementType)
                }
            }

            stmtMarker.done(RenPyScriptElementTypes.WITH_STMT)
        }

        return verifyTokenIsNewLineOrEqualOrEof("New line is expected at the end of the 'with' statement") && success
    }

    protected open fun parseHideStatement(): Boolean {
        token().also {
            if (it != RenPyScriptTokenTypes.HIDE_KEYWORD) {
                error("Invalid 'hide' statement start token: $it")
                return false
            }
        }

        val stmtMarker = mark()

        // Mark keyword token with element type
        mark().also {
            advance()
            it.done(RenPyScriptElementTypes.HIDE_STMT_KEYWORD)
        }

        var success = true

        if (isTokenImageLabelCompatibleIdentifier()) {
            val stmtImageMarker = mark()
            while (isTokenImageLabelCompatibleIdentifier()) {
                mark().also {
                    advance()
                    it.done(RenPyScriptElementTypes.HIDE_STMT_IMAGE_PART)
                }
            }
            stmtImageMarker.done(RenPyScriptElementTypes.HIDE_STMT_IMAGE)
        } else {
            error("'hide' statement image identifier expected")
            success = false
        }

        if (isTokenHidePropName()) {
            val stmtPropsListMarker = mark()

            val propKeywordToken = token()
            if (propKeywordToken == RenPyScriptTokenTypes.ONLAYER_KEYWORD) {
                val stmtPropMarker = mark()

                mark().also {
                    advance()
                    it.done(RenPyScriptElementTypes.HIDE_STMT_PROP_KEYWORD)
                }

                // 'hide' statement only has one keyword - onlayer, which only has one possible value - an identifier
                mark().also {
                    val token = token()
                    if (token == RenPyScriptTokenTypes.IDENTIFIER) {
                        advance()
                        it.done(RenPyScriptElementTypes.HIDE_STMT_PROP_VALUE)
                    } else {
                        error("A 'name' of layer is expected as 'onlayer' property value")
                        it.drop()
                        success = false
                    }
                }

                stmtPropMarker.done(RenPyScriptElementTypes.HIDE_STMT_PROP)
            } else {
                error("Unexpected 'hide' statement property keyword: $propKeywordToken")
                success = false
            }

            stmtPropsListMarker.done(RenPyScriptElementTypes.HIDE_STMT_PROPS_LIST)
        }

        token().also { token ->
            if (token == RenPyScriptTokenTypes.WITH_KEYWORD) {
                val stmtWithClauseMarker = mark()

                mark().also {
                    advance()
                    it.done(RenPyScriptElementTypes.HIDE_STMT_WITH_CLAUSE_KEYWORD)
                }

                mark().also {
                    var failMessage: String? = null
                    when {
                        isCurrentlyAtPythonMethodCallStart() -> {
                            if (!parsePythonMethodCall()) failMessage = "Failed to parse 'hide' statement 'with' clause value python method call"
                        }
                        token() == RenPyScriptTokenTypes.IDENTIFIER -> advance()

                        else -> failMessage = "An 'expression' is expected as 'hide' statement 'with' clause value"
                    }

                    if (failMessage != null) {
                        error(failMessage)
                        it.drop()
                        success = false
                    } else {
                        it.done(RenPyScriptElementTypes.HIDE_STMT_WITH_CLAUSE_VALUE)
                    }
                }

                stmtWithClauseMarker.done(RenPyScriptElementTypes.HIDE_STMT_WITH_CLAUSE)
            }
        }

        success = verifyTokenIsNewLineOrEqualOrEof("New line is expected at the end of the 'hide' statement") && success

        stmtMarker.done(RenPyScriptElementTypes.HIDE_STMT)
        return success
    }

    protected open fun parsePassStatement(): Boolean {
        token().also {
            if (it != RenPyScriptTokenTypes.PASS_KEYWORD) {
                error("Invalid 'pass' statement start token: $it")
                return false
            }
        }

        mark().also { stmtMarker ->
            mark().also { keywordMarker ->
                advance()
                keywordMarker.done(RenPyScriptElementTypes.PASS_STMT_KEYWORD)
            }
            stmtMarker.done(RenPyScriptElementTypes.PASS_STMT)
        }

        return verifyTokenIsNewLineOrEqualOrEof("New line is expected at the end of the 'pass' statement")
    }

    protected open fun parseReturnStatement(): Boolean {
        token().also {
            if (it != RenPyScriptTokenTypes.RETURN_KEYWORD) {
                error("Invalid 'return' statement start token: $it")
                return false
            }
        }

        mark().also { stmtMarker ->
            mark().also { keywordMarker ->
                advance()
                keywordMarker.done(RenPyScriptElementTypes.RETURN_STMT_KEYWORD)
            }

            if (!eof() && !isTokenNewLineOrSimilarToIt()) {
                // Return have optional value
                mark().also { valueMarker ->
                    advanceToNewLineOrEqualOrEof(error = false)
                    valueMarker.done(RenPyScriptElementTypes.RETURN_STMT_VALUE)
                }
            }

            stmtMarker.done(RenPyScriptElementTypes.RETURN_STMT)
        }

        return verifyTokenIsNewLineOrEqualOrEof("New line is expected at the end of the 'return' statement")
    }

    protected open fun parseAudioControlStatement(): Boolean {
        val stmtKeywordToken = token()

        val stmtElementType: RenPyScriptAudioControlStmtElementType
        when (stmtKeywordToken) {
            RenPyScriptTokenTypes.PLAY_KEYWORD -> {
                stmtElementType = RenPyScriptElementTypes.PLAY_STMT
            }
            RenPyScriptTokenTypes.STOP_KEYWORD -> {
                stmtElementType = RenPyScriptElementTypes.STOP_STMT
            }
            RenPyScriptTokenTypes.QUEUE_KEYWORD -> {
                stmtElementType = RenPyScriptElementTypes.QUEUE_STMT
            }

            else -> {
                this.builder.error("Invalid audio control statement keyword token: $stmtKeywordToken")
                return false
            }
        }

        val stmtMarker = mark()

        // Mark keyword token with element type
        markAndDoneGeneralKeyword()

        var success = true

        // Channel
        if (token() == RenPyScriptTokenTypes.IDENTIFIER) {
            markAdvanceDone(RenPyScriptElementTypes.AUDIO_CONTROL_STMT_CHANNEL)
        } else {
            error("'${stmtElementType.stmtName}' statement channel expected")
            success = false
        }

        if (stmtElementType.hasAudio) {
            mark().also {
                var localSuccess = true
                when (token()) {
                    RenPyScriptTokenTypes.SQUARE_BRACKETS_OPEN ->
                        success = parseAudioControlStatementAudioList(stmtElementType.stmtName) && success

                    RenPyScriptTokenTypes.IDENTIFIER, RenPyScriptTokenTypes.STRING ->
                        success = parseAudioControlStatementAudioFile(stmtElementType.stmtName) && success

                    else -> {
                        it.drop()
                        error("'${stmtElementType.stmtName}' audio file(s) expected")
                        success = false
                        localSuccess = false
                    }
                }

                if (localSuccess) it.done(RenPyScriptElementTypes.AUDIO_CONTROL_STMT_AUDIO)
            }
        }

        if (token() == RenPyScriptTokenTypes.IDENTIFIER) {
            mark().also { stmtClausesListMarker ->
                while (!eof()) {
                    if (token() != RenPyScriptTokenTypes.IDENTIFIER) break
                    mark().also { stmtClauseMarker ->
                        val clauseKeywordText = tokenText()
                        if (stmtElementType.clausesKeywords.contains(clauseKeywordText)) {
                            markAdvanceDone(RenPyScriptElementTypes.AUDIO_CONTROL_STMT_CLAUSE_KEYWORD)

                            if (RenPyScriptAudioControlStmtElementType.CLAUSES_WITH_VALUES.contains(clauseKeywordText)) {
                                if (isTokenAudioControlStatementClauseValue()) {
                                    markAdvanceDone(RenPyScriptElementTypes.AUDIO_CONTROL_STMT_CLAUSE_VALUE)
                                } else {
                                    error("'${stmtElementType.stmtName}' clause '$clauseKeywordText' expects value")
                                    success = false
                                }
                            }
                        } else {
                            if (RenPyScriptAudioControlStmtElementType.ALL_CLAUSES.contains(clauseKeywordText)) {
                                markAdvanceError("Clause '$clauseKeywordText' is not expected in audio control statement '${stmtElementType.stmtName}'")
                            } else {
                                markAdvanceError("Incorrect clause keyword '$clauseKeywordText'. Expected one of the following: ${stmtElementType.clausesKeywords}")
                            }
                            success = false
                        }
                        stmtClauseMarker.done(RenPyScriptElementTypes.AUDIO_CONTROL_STMT_CLAUSE)
                    }
                }
                stmtClausesListMarker.done(RenPyScriptElementTypes.AUDIO_CONTROL_STMT_CLAUSES_LIST)
            }
        }

        stmtMarker.done(stmtElementType)
        return verifyTokenIsNewLineOrEqualOrEof("New line is expected at the end of the '${stmtElementType.stmtName}' audio control statement") && success
    }

    protected open fun parseAudioControlStatementAudioFile(stmtName: String): Boolean {
        val token = token()
        if (token == RenPyScriptTokenTypes.IDENTIFIER || token == RenPyScriptTokenTypes.STRING) {
            markAdvanceDone(RenPyScriptElementTypes.AUDIO_CONTROL_STMT_AUDIO_FILE)
            return true
        }

        error("Invalid '$stmtName' statement audio file identifier token: $token")
        return false
    }

    protected open fun parseAudioControlStatementAudioList(stmtName: String): Boolean {
        if (token() != RenPyScriptTokenTypes.SQUARE_BRACKETS_OPEN) {
            error("Invalid '$stmtName' statement audio list start token (expected: '['): ${token()}")
            return false
        }

        val listMarker = mark()

        markAdvanceDone(RenPyScriptElementTypes.AUDIO_CONTROL_STMT_AUDIO_LIST_OPEN)

        var success = true

        mark().also { marker ->
            var started = false
            var foundEndBracket = false
            outer@ while (!eof()) {
                started = true

                if (isTokenNewLineOrSimilarToIt()) {
                    advance()
                    continue
                }

                success = parseAudioControlStatementAudioFile(stmtName) && success

                while (true) {
                    when (val token = token()) {
                        RenPyScriptTokenTypes.SQUARE_BRACKETS_CLOSE -> {
                            foundEndBracket = true
                            break@outer
                        }
                        RenPyScriptTokenTypes.COMMA -> {
                            advance()
                            break
                        }
                        else -> {
                            if (isTokenNewLineOrSimilarToIt(token)) {
                                break
                            }
                            markAdvanceError("Unexpected token inside '$stmtName' audio control statement audio list: $token")
                            success = false
                        }
                    }
                }
            }
            marker.done(RenPyScriptElementTypes.AUDIO_CONTROL_STMT_AUDIO_LIST_FILES)
            success = started && foundEndBracket && success
        }

        if (token() == RenPyScriptTokenTypes.SQUARE_BRACKETS_CLOSE) {
            markAdvanceDone(RenPyScriptElementTypes.AUDIO_CONTROL_STMT_AUDIO_LIST_CLOSE)
        } else {
            error("Invalid '$stmtName' statement audio list end token (expected: ']'): ${token()}")
            success = false
        }

        listMarker.done(RenPyScriptElementTypes.AUDIO_CONTROL_STMT_AUDIO_LIST)
        return success
    }

    protected open fun parsePauseStatement(): Boolean {
        token().also {
            if (it != RenPyScriptTokenTypes.PAUSE_KEYWORD) {
                error("Invalid 'pause' statement start token: $it")
                return false
            }
        }

        mark().also { stmtMarker ->
            mark().also { keywordMarker ->
                advance()
                keywordMarker.done(RenPyScriptElementTypes.GEN_STMT_KEYWORD)
            }

            if (!eof() && !isTokenNewLineOrSimilarToIt()) {
                // Pause have optional number value
                mark().also { valueMarker ->
                    val token = token()
                    advance()
                    if (isTokenNumber(token) || token == RenPyScriptTokenTypes.IDENTIFIER) {
                        valueMarker.done(RenPyScriptElementTypes.GEN_STMT_VALUE)
                    } else {
                        valueMarker.error("Invalid 'pause' statement value")
                    }
                }
            }

            stmtMarker.done(RenPyScriptElementTypes.PAUSE_STMT)
        }

        return verifyTokenIsNewLineOrEqualOrEof("New line is expected at the end of the 'return' statement")
    }

    protected open fun parseQuickPythonStatement(): Boolean {
        token().also {
            if (it != RenPyScriptTokenTypes.ONE_LINE_PYTHON_STATEMENT) {
                error("Invalid one-line python statement start token: $it")
                return false
            }
        }

        mark().also {
            advance()
            it.done(RenPyScriptElementTypes.ONE_LINE_PYTHON_STMT)
        }

        return verifyTokenIsNewLineOrEqualOrEof("New line is expected at the end of the one-line python statement")
    }

    protected open fun isCurrentlyAtPythonMethodCallStart(): Boolean = token() == RenPyScriptTokenTypes.IDENTIFIER &&
            lookAheadForTokenAfterTokenSet(
                RenPyScriptTokenTypes.PARENTHESES_OPEN,
                RenPyScriptTokenSets.PYTHON_METHOD_CALL_NAME_PARTS
            )

    protected open fun parsePythonMethodCall(): Boolean {
        token().also {
            if (!isCurrentlyAtPythonMethodCallStart()) {
                error("Invalid python method call start token: $it")
                return false
            }
        }

        val pythonMethodCallMarker = mark()

        mark().also {
            while (isTokenPythonMethodCallNamePart()) {
                advance()
            }
            it.done(RenPyScriptElementTypes.PYTHON_METHOD_CALL_NAME)
        }

        val parseParenthesesResult = parsePythonMethodCallParentheses()

        if (!parseParenthesesResult) {
            error("Failed to parse python method call parentheses")
        }

        pythonMethodCallMarker.done(RenPyScriptElementTypes.PYTHON_METHOD_CALL)
        return parseParenthesesResult
    }

    protected open fun parsePythonMethodCallParentheses(): Boolean {
        token().also {
            if (it != RenPyScriptTokenTypes.PARENTHESES_OPEN) {
                error("Invalid python method call parenthesis open token: $it")
                return false
            }
        }

        val parenthesesMarker = mark()

        mark().also {
            advance()
            it.done(RenPyScriptElementTypes.PYTHON_METHOD_CALL_PARENTHESES_OPEN)
        }

        var closeFound = false
        var success = true
        mark().also {
            while (!eof()) {
                when (token()) {
                    RenPyScriptTokenTypes.PARENTHESES_CLOSE -> {
                        closeFound = true
                        break
                    }

                    RenPyScriptTokenTypes.PARENTHESES_OPEN -> {
                        success = parsePythonMethodCallParentheses() && success
                    }

                    else -> advance()
                }
            }
            it.done(RenPyScriptElementTypes.PYTHON_METHOD_CALL_PARENTHESES_CONTENT)
        }

        if (!closeFound) {
            error("Close parenthesis for python method call is not found")
        } else {
            mark().also {
                advance()
                it.done(RenPyScriptElementTypes.PYTHON_METHOD_CALL_PARENTHESES_CLOSE)
            }
        }

        parenthesesMarker.done(RenPyScriptElementTypes.PYTHON_METHOD_CALL_PARENTHESES)

        return closeFound && success
    }

    protected fun advance() {
        this.builder.advanceLexer()
    }

    protected fun advanceToNewLineOrEqualOrEof(error: Boolean = true) {
        var errorMarker: Marker? = null
        while (true) {
            if (eof() || isTokenNewLineOrSimilarToIt()) {
                if (error) flushError(errorMarker, "Unexpected tokens")
                break
            }
            if (errorMarker == null && error) errorMarker = mark()
            advance()
        }
    }

    protected fun advanceToDedentOfCurrentBlockOrEof() {
        if (token() == RenPyScriptTokenTypes.INDENT) advance()
        var additionalIndents = 0
        while (true) {
            val token = token()

            if (eof() || token == RenPyScriptTokenTypes.DEDENT) {
                if (additionalIndents == 0) {
                    advance()
                    return
                }

                additionalIndents--
            } else if (token == RenPyScriptTokenTypes.INDENT) {
                additionalIndents++
            }

            advance()
        }
    }

    protected fun lookAheadForTokenAfterTokenSet(expectedToken: IElementType, acceptableTokenSet: TokenSet): Boolean {
        if (acceptableTokenSet.contains(expectedToken)) {
            LOG.error("Acceptable token set '$acceptableTokenSet' contains expected token '$expectedToken'. It will cause 'false' searching till EOF")
            return false
        }

        var aheadToken: IElementType? = null
        var step = 0
        while (this.builder.lookAhead(step++).also { aheadToken = it } != null) {
            if (acceptableTokenSet.contains(aheadToken)) continue
            if (aheadToken == expectedToken) return true
            break
        }
        return false
    }

    protected fun lookAheadForIndentAfterNewLines(): Boolean = lookAheadForTokenAfterTokenSet(RenPyScriptTokenTypes.INDENT, RenPyScriptTokenSets.NEW_LINES)

    protected fun token(): IElementType? = this.builder.tokenType

    protected fun tokenText(): String? = this.builder.tokenText

    protected fun tokenAhead(step: Int = 1): IElementType? = this.builder.lookAhead(step)

    protected fun eof(): Boolean = this.builder.eof()

    protected fun mark(): Marker = this.builder.mark()

    protected fun error(@ParsingError message: String) {
        this.builder.error(message)
    }

    protected open fun markAdvanceDone(elementType: IElementType) {
        mark().also {
            advance()
            it.done(elementType)
        }
    }

    protected open fun markAdvanceError(message: String) {
        mark().also {
            advance()
            it.error(message)
        }
    }

    protected open fun markAndDoneGeneralKeyword() = markAdvanceDone(RenPyScriptElementTypes.GEN_STMT_KEYWORD)

    protected open fun isTokenBlockStatementStart(token: IElementType? = null): Boolean = RenPyScriptTokenSets.BLOCK_STATEMENT_STARTS.contains(token ?: token())

    protected open fun isTokenDialogueStatementStart(token: IElementType? = null): Boolean = RenPyScriptTokenSets.DIALOGUE_STATEMENT_STARTS.contains(token ?: token())

    protected open fun isTokenDialogueStatementTextStart(token: IElementType? = null): Boolean = RenPyScriptTokenSets.DIALOGUE_STATEMENT_TEXTS.contains(token ?: token())

    protected open fun isTokenExpressionValue(token: IElementType? = null): Boolean = RenPyScriptTokenSets.EXPRESSION_VALUES.contains(token ?: token())

    protected open fun isTokenImageDisplayControlStatementStart(token: IElementType? = null): Boolean = RenPyScriptTokenSets.IMAGE_DISPLAY_CONTROL_STATEMENT_STARTS.contains(token ?: token())

    protected open fun isTokenAudioControlStatementStart(token: IElementType? = null): Boolean = RenPyScriptTokenSets.AUDIO_CONTROL_STATEMENT_STARTS.contains(token ?: token())

    protected open fun isTokenAudioControlStatementClauseValue(token: IElementType? = null): Boolean = RenPyScriptTokenSets.AUDIO_CONTROL_STATEMENT_CLAUSE_VALUES.contains(token ?: token())

    protected open fun isTokenNumber(token: IElementType? = null): Boolean = RenPyScriptTokenSets.NUMBERS.contains(token ?: token())

    protected open fun isTokenShowOrScenePropName(token: IElementType? = null): Boolean = RenPyScriptTokenSets.SHOW_OR_SCENE_PARAM_KEYWORDS.contains(token ?: token())

    protected open fun isTokenHidePropName(token: IElementType? = null): Boolean = RenPyScriptTokenSets.HIDE_PARAM_KEYWORDS.contains(token ?: token())

    protected open fun isTokenNewLineOrSimilarToIt(token: IElementType? = null): Boolean = RenPyScriptTokenSets.NEW_LINE_OR_SIMILAR_TO_IT_TOKENS.contains(token ?: token())

    protected open fun isTokenImageLabelCompatibleIdentifier(token: IElementType? = null): Boolean = RenPyScriptTokenSets.IMAGE_LABEL_COMPATIBLE_IDENTIFIERS.contains(token ?: token())

    protected open fun isTokenPythonMethodCallNamePart(token: IElementType? = null): Boolean = RenPyScriptTokenSets.PYTHON_METHOD_CALL_NAME_PARTS.contains(token ?: token())

    protected open fun verifyTokenIsNewLineOrEqualOrEof(@ParsingError errorMessage: String): Boolean {
        if (eof() || isTokenNewLineOrSimilarToIt()) return true
        error(errorMessage)
        return false
    }

    companion object {
        fun flushError(errorMarker: Marker?, @ParsingError message: String = "Unexpected error while parsing Ren'Py Script: flushing"): Marker? {
            errorMarker?.error(message)
            return null
        }
    }
}
