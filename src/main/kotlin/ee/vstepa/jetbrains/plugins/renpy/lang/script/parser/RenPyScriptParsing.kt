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

    open fun parseStatementsList(advance: Boolean = false): Boolean {
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
            } else if (token == RenPyScriptTokenTypes.DEDENT) {
                // End of current statements list
                advance()
                break
            } else {
                error("Unexpected statement start token: $token")
                success = false
            }

            if (!success) advanceToNewLineOrEqualOrEof()
        }

        statementsListMarker.done(RenPyScriptElementTypes.STMTS_LIST)
        return true
    }

    open fun parseBlockStatement(): Boolean {
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

    open fun parseLabel(): Boolean {
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

    open fun parseMenu(): Boolean {
        return false
    }

    open fun parseDialogStatement(): Boolean {
        if (!isTokenDialogueStatementStart()) {
            error("Invalid dialog statement start token: ${token()}")
            return false
        }

        val dialogStatementMarker = mark()

        if (token() == RenPyScriptTokenTypes.IDENTIFIER) {
            val dialogStatementIdentifierMarker = mark()
            advance()
            dialogStatementIdentifierMarker.done(RenPyScriptElementTypes.DIALOG_STMT_IDENTIFIER)
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

    open fun parseJumpStatement(): Boolean {
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

    protected fun eof(): Boolean = this.builder.eof()

    protected fun mark(): Marker = this.builder.mark()

    protected fun error(@ParsingError message: String) {
        this.builder.error(message)
    }

    protected open fun isTokenBlockStatementStart(): Boolean = RenPyScriptTokenSets.BLOCK_STATEMENT_STARTS.contains(token())

    protected open fun isTokenDialogueStatementStart(): Boolean = RenPyScriptTokenSets.DIALOGUE_STATEMENT_STARTS.contains(token())

    protected open fun isTokenExpressionValue(): Boolean = RenPyScriptTokenSets.EXPRESSION_VALUES.contains(token())

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
