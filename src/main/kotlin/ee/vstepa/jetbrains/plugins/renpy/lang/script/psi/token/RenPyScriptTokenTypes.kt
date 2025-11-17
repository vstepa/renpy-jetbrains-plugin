package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.token

object RenPyScriptTokenTypes {
    // Keywords
    @JvmField val LABEL_KEYWORD = RenPyScriptTokenType("LABEL_KEYWORD")
    @JvmField val IF_KEYWORD = RenPyScriptTokenType("IF_KEYWORD")
    @JvmField val ELIF_KEYWORD = RenPyScriptTokenType("ELIF_KEYWORD")
    @JvmField val ELSE_KEYWORD = RenPyScriptTokenType("ELSE_KEYWORD")
    @JvmField val WHILE_KEYWORD = RenPyScriptTokenType("WHILE_KEYWORD")
    @JvmField val JUMP_KEYWORD = RenPyScriptTokenType("JUMP_KEYWORD")
    @JvmField val CALL_KEYWORD = RenPyScriptTokenType("CALL_KEYWORD")
    @JvmField val MENU_KEYWORD = RenPyScriptTokenType("MENU_KEYWORD")
    @JvmField val SCENE_KEYWORD = RenPyScriptTokenType("SCENE_KEYWORD")
    @JvmField val WITH_KEYWORD = RenPyScriptTokenType("WITH_KEYWORD")
    @JvmField val SHOW_KEYWORD = RenPyScriptTokenType("SHOW_KEYWORD")
    @JvmField val HIDE_KEYWORD = RenPyScriptTokenType("HIDE_KEYWORD")
    @JvmField val EXPRESSION_KEYWORD = RenPyScriptTokenType("EXPRESSION_KEYWORD")
    @JvmField val PASS_KEYWORD = RenPyScriptTokenType("PASS_KEYWORD")
    @JvmField val PAUSE_KEYWORD = RenPyScriptTokenType("PAUSE_KEYWORD")
    @JvmField val PLAY_KEYWORD = RenPyScriptTokenType("PLAY_KEYWORD")
    @JvmField val STOP_KEYWORD = RenPyScriptTokenType("STOP_KEYWORD")
    @JvmField val QUEUE_KEYWORD = RenPyScriptTokenType("QUEUE_KEYWORD")
    @JvmField val RETURN_KEYWORD = RenPyScriptTokenType("RETURN_KEYWORD")

    // Screen display control keywords
    @JvmField val SHOW_SCREEN_KEYWORD = RenPyScriptTokenType("SHOW_SCREEN_KEYWORD")
    @JvmField val HIDE_SCREEN_KEYWORD = RenPyScriptTokenType("HIDE_SCREEN_KEYWORD")
    @JvmField val CALL_SCREEN_KEYWORD = RenPyScriptTokenType("CALL_SCREEN_KEYWORD")

    // Keywords - show/scene props
    @JvmField val AS_KEYWORD = RenPyScriptTokenType("AS_KEYWORD")
    @JvmField val AT_KEYWORD = RenPyScriptTokenType("AT_KEYWORD")
    @JvmField val BEHIND_KEYWORD = RenPyScriptTokenType("BEHIND_KEYWORD")
    @JvmField val ONLAYER_KEYWORD = RenPyScriptTokenType("ONLAYER_KEYWORD")
    @JvmField val ZORDER_KEYWORD = RenPyScriptTokenType("ZORDER_KEYWORD")

    // Indent
    @JvmField val INDENT = RenPyScriptTokenType("INDENT")

    // Python
    @JvmField val ONE_LINE_PYTHON_STATEMENT = RenPyScriptTokenType("ONE_LINE_PYTHON_STATEMENT")

    // Others
    @JvmField val IDENTIFIER = RenPyScriptTokenType("IDENTIFIER")
    @JvmField val IMAGE_LABEL_IDENTIFIER = RenPyScriptTokenType("IMAGE_LABEL_IDENTIFIER")
    @JvmField val STRING = RenPyScriptTokenType("STRING")
    @JvmField val MULTILINE_DIALOG_STRING = RenPyScriptTokenType("MULTILINE_DIALOG_STRING")
    @JvmField val COMMENT = RenPyScriptTokenType("COMMENT")
    @JvmField val COLON = RenPyScriptTokenType("COLON")
    @JvmField val NEW_LINE = RenPyScriptTokenType("NEW_LINE")
    @JvmField val COMMA = RenPyScriptTokenType("COMMA")
    @JvmField val PLAIN_NUMBER = RenPyScriptTokenType("PLAIN_NUMBER")
    @JvmField val FLOAT_NUMBER = RenPyScriptTokenType("FLOAT_NUMBER")
    @JvmField val NONE = RenPyScriptTokenType("NONE")
    @JvmField val PARENTHESES_OPEN = RenPyScriptTokenType("PARENTHESES_OPEN")
    @JvmField val PARENTHESES_CLOSE = RenPyScriptTokenType("PARENTHESES_CLOSE")
    @JvmField val SQUARE_BRACKETS_OPEN = RenPyScriptTokenType("SQUARE_BRACKETS_OPEN")
    @JvmField val SQUARE_BRACKETS_CLOSE = RenPyScriptTokenType("SQUARE_BRACKETS_CLOSE")
    @JvmField val DOT = RenPyScriptTokenType("DOT")
}
