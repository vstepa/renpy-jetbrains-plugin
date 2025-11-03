package ee.vstepa.jetbrains.plugins.renpy.lang.script.parser

import com.intellij.lang.PsiBuilder
import com.intellij.lang.PsiBuilder.Marker
import com.intellij.openapi.util.NlsContexts.ParsingError
import com.intellij.psi.tree.IElementType
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.type.RenPyScriptElementType
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.type.RenPyScriptElementTypes
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.token.RenPyScriptTokenSets
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.token.RenPyScriptTokenType
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.token.RenPyScriptTokenTypes

open class RenPyScriptParsing(private val builder: PsiBuilder) {

    open fun parseScript() {
        val scriptMarker = mark()
        parseStatementsList()
        advance()
        scriptMarker.done(RenPyScriptElementTypes.REN_PY_SCRIPT)
    }

    protected open fun parseStatementsList(advance: Boolean = false): Boolean {
        val statementsListMarker = mark()
        if (advance) advance()

        while (!eof()) {
            val token = token()
            var success = true
            if (token == RenPyScriptTokenTypes.INDENT) {
                // Indents are handled by statements, if we encounter indent as statement start char during parsing statements list - indentation error
                mark().also {
                    advance()
                    flushError(
                        it,
                        "Line is indented, but the preceding statement does not expect a block. Please check this line's indentation."
                    )
                }
                success = parseStatementsList()
            } else if (token == RenPyScriptTokenTypes.NEW_LINE) {
                // Skipping trailing new lines
                advance()
            } else if (isTokenDialogueStatementStart()) {
                // Start of dialog statement, with character or without
                success = parseDialogStatement()
            } else if (isTokenBlockStatementStart()) {
                // Start of label, menu
                success = parseBlockStatement()
            } else if (token == RenPyScriptTokenTypes.JUMP_KEYWORD) {
                // Start of jump ... or jump expression ...
                success = parseJumpStatement()
            } else if (isTokenImageDisplayControlStatementStart()) {
                // Start of show ... or show expression ...
                success = parseImageDisplayControlStatement()
            } else if (token == RenPyScriptTokenTypes.DEDENT) {
                // End of current statements list
                advance()
                break
            } else {
                mark().also {
                    advance()
                    flushError(it, "Unexpected statement start token: $token")
                }
                success = false
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
            RenPyScriptTokenTypes.STRING if (tokenAhead() == RenPyScriptTokenTypes.STRING) -> {
                // Our token text identifier is also a text line
                // Dialog line looks like this: "John" "Hello, my name is John!"
                val dialogStatementTextIdentifierMarker = mark()
                advance()
                dialogStatementTextIdentifierMarker.done(RenPyScriptElementTypes.DIALOG_STMT_TEXT_IDENTIFIER)
            }
        }

        var success = true
        val dialogStatementTextMarker = mark()
        if (token() == RenPyScriptTokenTypes.STRING) {
            advance()
            dialogStatementTextMarker.done(RenPyScriptElementTypes.DIALOG_STMT_TEXT)
        } else {
            error("Dialog statement text expected")
            dialogStatementTextMarker.drop()
            success = false
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
        when (val token = token()) {
            RenPyScriptTokenTypes.SHOW_KEYWORD -> {
                return parseShowStatement()
            }
            RenPyScriptTokenTypes.SCENE_KEYWORD -> {
                return parseSceneStatement()
            }
            else -> {
                this.builder.error("Invalid image display control statement start token: $token")
                return true
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
                if (token() == RenPyScriptTokenTypes.IDENTIFIER) {
                    advance()
                    it.done(stmtWithClauseValueElementType)
                } else {
                    error("An 'expression' is expected as '$stmtName' statement 'with' clause value")
                    it.drop()
                    success = false
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

    protected fun lookAheadForIndentAfterNewLines(): Boolean {
        var aheadToken: IElementType? = null
        var step = 0
        while (this.builder.lookAhead(step++).also { aheadToken = it } != null) {
            if (aheadToken == RenPyScriptTokenTypes.NEW_LINE) continue
            if (aheadToken == RenPyScriptTokenTypes.INDENT) return true
            break
        }
        return false
    }

    protected fun token(): IElementType? = this.builder.tokenType

    protected fun tokenAhead(step: Int = 1): IElementType? = this.builder.lookAhead(step)

    protected fun tokenText(): String? = this.builder.tokenText

    protected fun eof(): Boolean = this.builder.eof()

    protected fun mark(): Marker = this.builder.mark()

    protected fun error(@ParsingError message: String) {
        this.builder.error(message)
    }

    protected open fun isTokenBlockStatementStart(token: IElementType? = null): Boolean = RenPyScriptTokenSets.BLOCK_STATEMENT_STARTS.contains(token ?: token())

    protected open fun isTokenDialogueStatementStart(token: IElementType? = null): Boolean = RenPyScriptTokenSets.DIALOGUE_STATEMENT_STARTS.contains(token ?: token())

    protected open fun isTokenExpressionValue(token: IElementType? = null): Boolean = RenPyScriptTokenSets.EXPRESSION_VALUES.contains(token ?: token())

    protected open fun isTokenImageDisplayControlStatementStart(token: IElementType? = null): Boolean = RenPyScriptTokenSets.IMAGE_DISPLAY_CONTROL_STATEMENT_STARTS.contains(token ?: token())

    protected open fun isTokenShowOrScenePropName(token: IElementType? = null): Boolean = RenPyScriptTokenSets.SHOW_OR_SCENE_PARAM_KEYWORDS.contains(token ?: token())

    protected open fun isTokenNewLineOrSimilarToIt(token: IElementType? = null): Boolean = RenPyScriptTokenSets.NEW_LINE_OR_SIMILAR_TO_IT_TOKENS.contains(token ?: token())

    protected open fun isTokenImageLabelCompatibleIdentifier(token: IElementType? = null): Boolean = RenPyScriptTokenSets.IMAGE_LABEL_COMPATIBLE_IDENTIFIERS.contains(token ?: token())

    protected open fun verifyTokenIsNewLineOrEqualOrEof(@ParsingError errorMessage: String): Boolean {
        if (eof() || isTokenNewLineOrSimilarToIt()) return true
        error(errorMessage)
        return false
    }

    companion object {
        fun isRenPyScriptToken(tokenType: IElementType?): Boolean {
            return tokenType != null && tokenType is RenPyScriptTokenType
        }

        fun flushError(errorMarker: Marker?, @ParsingError message: String = "Unexpected error while parsing Ren'Py Script: flushing"): Marker? {
            errorMarker?.error(message)
            return null
        }
    }
}
