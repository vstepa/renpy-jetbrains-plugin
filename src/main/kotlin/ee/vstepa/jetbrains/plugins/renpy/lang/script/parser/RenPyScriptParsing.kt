package ee.vstepa.jetbrains.plugins.renpy.lang.script.parser

import com.intellij.lang.PsiBuilder
import com.intellij.lang.PsiBuilder.Marker
import com.intellij.openapi.util.NlsContexts.ParsingError
import com.intellij.psi.tree.IElementType
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
                // Start of new statements block
                success = parseStatementsList(true)
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
            } else if (token == RenPyScriptTokenTypes.SHOW_KEYWORD) {
                // Start of show ... or show expression ...
                success = parseShowStatement()
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
            success = parseStatementsList(true)
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

    protected open fun parseShowStatement(): Boolean {
        var token = token()
        if (token != RenPyScriptTokenTypes.SHOW_KEYWORD) {
            error("Invalid show statement start token: $token")
            return false
        }

        val showStatementMarker = mark()

        val showStatementKeywordMarker = mark()
        advance()
        showStatementKeywordMarker.done(RenPyScriptElementTypes.SHOW_STMT_KEYWORD)

        var success = true
        token = token()
        when (token) {
            RenPyScriptTokenTypes.EXPRESSION_KEYWORD -> {
                val showStatementExpressionMarker = mark()
                val showStatementExpressionKeywordMarker = mark()
                advance()
                showStatementExpressionKeywordMarker.done(RenPyScriptElementTypes.SHOW_STMT_EXPRESSION_KEYWORD)

                val showStatementExpressionValueMarker = mark()
                if (isTokenExpressionValue()) {
                    advance()
                    showStatementExpressionValueMarker.done(RenPyScriptElementTypes.SHOW_STMT_EXPRESSION_VALUE)
                } else {
                    error("Show statement expression expected")
                    showStatementExpressionValueMarker.drop()
                    success = false
                }

                showStatementExpressionMarker.done(RenPyScriptElementTypes.SHOW_STMT_EXPRESSION)
            }
            RenPyScriptTokenTypes.IDENTIFIER -> {
                val showStatementImageMarker = mark()
                while (token() == RenPyScriptTokenTypes.IDENTIFIER) {
                    val showStatementImagePartMarker = mark()
                    advance()
                    showStatementImagePartMarker.done(RenPyScriptElementTypes.SHOW_STMT_IMAGE_PART)
                }
                showStatementImageMarker.done(RenPyScriptElementTypes.SHOW_STMT_IMAGE)
            }
            else -> {
                error("Show statement image identifier or expression expected")
                success = false
            }
        }

        if (isTokenShowOrScenePropName()) {
            val showStatementPropsListMarker = mark()

            while (true) {
                val propKeywordToken = token()
                val showStatementPropMarker = mark()
                val showStatementPropKeywordMarker = mark()
                advance()
                showStatementPropKeywordMarker.done(RenPyScriptElementTypes.SHOW_STMT_PROP_KEYWORD)

                val showStatementPropValMarker = mark()
                val currentToken = token()
                when (propKeywordToken) {
                    RenPyScriptTokenTypes.AS_KEYWORD, RenPyScriptTokenTypes.ONLAYER_KEYWORD -> {
                        if (currentToken == RenPyScriptTokenTypes.IDENTIFIER) {
                            advance()
                            showStatementPropValMarker.done(RenPyScriptElementTypes.SHOW_STMT_PROP_VALUE)
                        } else {
                            if (propKeywordToken == RenPyScriptTokenTypes.AS_KEYWORD) {
                                error("A 'name' is expected as 'as' property value")
                            } else {
                                error("A 'name' of layer is expected as 'onlayer' property value")
                            }
                            showStatementPropValMarker.drop()
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
                            showStatementPropValMarker.done(RenPyScriptElementTypes.SHOW_STMT_PROP_VALUE)
                        } else {
                            if (propKeywordToken == RenPyScriptTokenTypes.AT_KEYWORD) {
                                error("An 'expression' or a list of 'expressions' is expected as 'at' property value")
                            } else {
                                error("A 'name' or a list of 'names' is expected as 'behind' property value")
                            }
                            showStatementPropValMarker.drop()
                            success = false
                        }
                    }
                    RenPyScriptTokenTypes.ZORDER_KEYWORD -> {
                        if (currentToken == RenPyScriptTokenTypes.IDENTIFIER || currentToken == RenPyScriptTokenTypes.STRING || currentToken == RenPyScriptTokenTypes.PLAIN_NUMBER) {
                            advance()
                            showStatementPropValMarker.done(RenPyScriptElementTypes.SHOW_STMT_PROP_VALUE)
                        } else {
                            error("An 'integer' is expected as 'zorder' property value")
                            showStatementPropValMarker.drop()
                            success = false
                        }
                    }
                    else -> {
                        error("Unexpected 'show' property keyword: $propKeywordToken")
                        showStatementPropValMarker.drop()
                        success = false
                    }
                }

                showStatementPropMarker.done(RenPyScriptElementTypes.SHOW_STMT_PROP)

                if (eof() || RenPyScriptTokenSets.NEW_LINE_OR_EQUALS.contains(token()) || !isTokenShowOrScenePropName()) break
            }

            showStatementPropsListMarker.done(RenPyScriptElementTypes.SHOW_STMT_PROPS_LIST)
        }

        token = token()
        if (token == RenPyScriptTokenTypes.WITH_KEYWORD) {
            val showStatementWithClauseMarker = mark()
            val showStatementWithClauseKeywordMarker = mark()
            advance()
            showStatementWithClauseKeywordMarker.done(RenPyScriptElementTypes.SHOW_STMT_WITH_CLAUSE_KEYWORD)

            val showStatementWithClauseValueMarker = mark()
            if (token() == RenPyScriptTokenTypes.IDENTIFIER) {
                advance()
                showStatementWithClauseValueMarker.done(RenPyScriptElementTypes.SHOW_STMT_WITH_CLAUSE_VALUE)
            } else {
                error("An 'expression' is expected as 'show' statement 'with' clause value")
                showStatementWithClauseValueMarker.drop()
                success = false
            }
            showStatementWithClauseMarker.done(RenPyScriptElementTypes.SHOW_STMT_WITH_CLAUSE)
        }

        success = verifyTokenIsNewLineOrEqualOrEof("New line is expected at the end of the 'show' statement") && success

        showStatementMarker.done(RenPyScriptElementTypes.SHOW_STMT)
        return success
    }

    protected fun advance() {
        this.builder.advanceLexer()
    }

    protected fun advanceToNewLineOrEqualOrEof() {
        var errorMarker: Marker? = null
        while (true) {
            if (eof() || RenPyScriptTokenSets.NEW_LINE_OR_EQUALS.contains(token())) {
                flushError(errorMarker, "Unexpected tokens")
                break
            }
            if (errorMarker == null) errorMarker = mark()
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

    protected open fun isTokenBlockStatementStart(): Boolean = RenPyScriptTokenSets.BLOCK_STATEMENT_STARTS.contains(token())

    protected open fun isTokenDialogueStatementStart(): Boolean = RenPyScriptTokenSets.DIALOGUE_STATEMENT_STARTS.contains(token())

    protected open fun isTokenExpressionValue(): Boolean = RenPyScriptTokenSets.EXPRESSION_VALUES.contains(token())

    protected open fun isTokenShowOrScenePropName(): Boolean = RenPyScriptTokenSets.SHOW_OR_SCENE_PARAM_KEYWORDS.contains(token())

    protected open fun verifyTokenIsNewLineOrEqualOrEof(@ParsingError errorMessage: String): Boolean {
        if (eof() || RenPyScriptTokenSets.NEW_LINE_OR_EQUALS.contains(token())) return true
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
