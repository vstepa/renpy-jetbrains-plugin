package ee.vstepa.jetbrains.plugins.renpy.lang.script

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType
import ee.vstepa.jetbrains.plugins.renpy.lang.script.lexer.RenPyScriptLexerAdapter
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.token.RenPyScriptTokenTypes

class RenPyScriptSyntaxHighlighter : SyntaxHighlighterBase() {

    override fun getHighlightingLexer(): Lexer = RenPyScriptLexerAdapter()

    override fun getTokenHighlights(tokenType: IElementType?): Array<out TextAttributesKey?> = when (tokenType) {
        RenPyScriptTokenTypes.LABEL_KEYWORD,
        RenPyScriptTokenTypes.IF_KEYWORD,
        RenPyScriptTokenTypes.ELIF_KEYWORD,
        RenPyScriptTokenTypes.ELSE_KEYWORD,
        RenPyScriptTokenTypes.WHILE_KEYWORD,
        RenPyScriptTokenTypes.JUMP_KEYWORD,
        RenPyScriptTokenTypes.CALL_KEYWORD,
        RenPyScriptTokenTypes.MENU_KEYWORD,
        RenPyScriptTokenTypes.SCENE_KEYWORD,
        RenPyScriptTokenTypes.WITH_KEYWORD,
        RenPyScriptTokenTypes.SHOW_KEYWORD,
        RenPyScriptTokenTypes.HIDE_KEYWORD,
        RenPyScriptTokenTypes.EXPRESSION_KEYWORD,
        RenPyScriptTokenTypes.PASS_KEYWORD,
        RenPyScriptTokenTypes.PAUSE_KEYWORD,
        RenPyScriptTokenTypes.PLAY_KEYWORD,
        RenPyScriptTokenTypes.STOP_KEYWORD,
        RenPyScriptTokenTypes.QUEUE_KEYWORD,
        RenPyScriptTokenTypes.RETURN_KEYWORD,
        RenPyScriptTokenTypes.SHOW_SCREEN_KEYWORD,
        RenPyScriptTokenTypes.HIDE_SCREEN_KEYWORD,
        RenPyScriptTokenTypes.CALL_SCREEN_KEYWORD,
        RenPyScriptTokenTypes.AS_KEYWORD,
        RenPyScriptTokenTypes.AT_KEYWORD,
        RenPyScriptTokenTypes.BEHIND_KEYWORD,
        RenPyScriptTokenTypes.ONLAYER_KEYWORD,
        RenPyScriptTokenTypes.ZORDER_KEYWORD,
        RenPyScriptTokenTypes.NONE,
            -> KEYWORD_KEYS

        RenPyScriptTokenTypes.ONE_LINE_PYTHON_STATEMENT -> DIFF_LANG_PART_KEYS

        RenPyScriptTokenTypes.IDENTIFIER,
        RenPyScriptTokenTypes.IMAGE_LABEL_IDENTIFIER,
            -> IDENTIFIER_KEYS

        RenPyScriptTokenTypes.STRING,
        RenPyScriptTokenTypes.MULTILINE_DIALOG_STRING,
            -> STRING_KEYS

        RenPyScriptTokenTypes.COMMENT -> COMMENT_KEYS

        RenPyScriptTokenTypes.COLON -> COLON_KEYS

        RenPyScriptTokenTypes.COMMA -> COMMA_KEYS

        RenPyScriptTokenTypes.PLAIN_NUMBER,
        RenPyScriptTokenTypes.FLOAT_NUMBER,
            -> NUMBER_KEYS

        RenPyScriptTokenTypes.PARENTHESES_OPEN,
        RenPyScriptTokenTypes.PARENTHESES_CLOSE,
            -> PARENTHESES_KEYS

        RenPyScriptTokenTypes.SQUARE_BRACKETS_OPEN,
        RenPyScriptTokenTypes.SQUARE_BRACKETS_CLOSE,
            -> BRACKETS_KEYS

        RenPyScriptTokenTypes.DOT -> DOT_KEYS

//        TokenType.BAD_CHARACTER -> BAD_CHARACTER_KEYS

        else -> EMPTY_KEYS
    }

    companion object {
        val KEYWORD = TextAttributesKey.createTextAttributesKey("REN_PY_SCRIPT_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD)
        val IDENTIFIER = TextAttributesKey.createTextAttributesKey("REN_PY_SCRIPT_IDENTIFIER", DefaultLanguageHighlighterColors.IDENTIFIER)
        val STRING = TextAttributesKey.createTextAttributesKey("REN_PY_SCRIPT_STRING", DefaultLanguageHighlighterColors.STRING)
        val COMMENT = TextAttributesKey.createTextAttributesKey("REN_PY_SCRIPT_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT)
        val COLON = TextAttributesKey.createTextAttributesKey("REN_PY_SCRIPT_COLON", DefaultLanguageHighlighterColors.SEMICOLON)
        val COMMA = TextAttributesKey.createTextAttributesKey("REN_PY_SCRIPT_COMMA", DefaultLanguageHighlighterColors.COMMA)
        val NUMBER = TextAttributesKey.createTextAttributesKey("REN_PY_SCRIPT_NUMBER", DefaultLanguageHighlighterColors.NUMBER)
        val PARENTHESES = TextAttributesKey.createTextAttributesKey("REN_PY_SCRIPT_PARENTHESES", DefaultLanguageHighlighterColors.PARENTHESES)
        val BRACKETS = TextAttributesKey.createTextAttributesKey("REN_PY_SCRIPT_BRACKETS", DefaultLanguageHighlighterColors.BRACKETS)
        val DOT = TextAttributesKey.createTextAttributesKey("REN_PY_SCRIPT_DOT", DefaultLanguageHighlighterColors.DOT)
//        val BAD_CHARACTER = TextAttributesKey.createTextAttributesKey("REN_PY_SCRIPT_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER)
        val DIFF_LANG_PART = TextAttributesKey.createTextAttributesKey("REN_PY_SCRIPT_DIFF_LANG_PART", DefaultLanguageHighlighterColors.DOC_COMMENT)

        private val KEYWORD_KEYS = arrayOf(KEYWORD)
        private val IDENTIFIER_KEYS = arrayOf(IDENTIFIER)
        private val STRING_KEYS = arrayOf(STRING)
        private val COMMENT_KEYS = arrayOf(COMMENT)
        private val COLON_KEYS = arrayOf(COLON)
        private val COMMA_KEYS = arrayOf(COMMA)
        private val NUMBER_KEYS = arrayOf(NUMBER)
        private val PARENTHESES_KEYS = arrayOf(PARENTHESES)
        private val BRACKETS_KEYS = arrayOf(BRACKETS)
        private val DOT_KEYS = arrayOf(DOT)
        private val DIFF_LANG_PART_KEYS = arrayOf(DIFF_LANG_PART)
//        private val BAD_CHARACTER_KEYS = arrayOf(BAD_CHARACTER)
        private val EMPTY_KEYS = emptyArray<TextAttributesKey>()
    }
}
