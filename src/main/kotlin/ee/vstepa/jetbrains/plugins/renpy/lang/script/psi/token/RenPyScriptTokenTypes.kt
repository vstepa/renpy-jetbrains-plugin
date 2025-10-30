package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.token

import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.type.RenPyScriptElementTypes

object RenPyScriptTokenTypes {
    // FILE
    @JvmField val FILE = RenPyScriptElementTypes.FILE

    // Keywords
    @JvmField val LABEL_KEYWORD = RenPyScriptTokenType("LABEL_KEYWORD")
    @JvmField val JUMP_KEYWORD = RenPyScriptTokenType("JUMP_KEYWORD")
    @JvmField val MENU_KEYWORD = RenPyScriptTokenType("MENU_KEYWORD")
    @JvmField val SCENE_KEYWORD = RenPyScriptTokenType("SCENE_KEYWORD")
    @JvmField val WITH_KEYWORD = RenPyScriptTokenType("WITH_KEYWORD")
    @JvmField val SHOW_KEYWORD = RenPyScriptTokenType("SHOW_KEYWORD")
    @JvmField val HIDE_KEYWORD = RenPyScriptTokenType("HIDE_KEYWORD")
    @JvmField val EXPRESSION_KEYWORD = RenPyScriptTokenType("EXPRESSION_KEYWORD")

    // Keywords - show/scene props
    @JvmField val AS_KEYWORD = RenPyScriptTokenType("AS_KEYWORD")
    @JvmField val AT_KEYWORD = RenPyScriptTokenType("AT_KEYWORD")
    @JvmField val BEHIND_KEYWORD = RenPyScriptTokenType("BEHIND_KEYWORD")
    @JvmField val ONLAYER_KEYWORD = RenPyScriptTokenType("ONLAYER_KEYWORD")
    @JvmField val ZORDER_KEYWORD = RenPyScriptTokenType("ZORDER_KEYWORD")

    // Indent/Dedent
    @JvmField val INDENT = RenPyScriptTokenType("INDENT")
    @JvmField val DEDENT = RenPyScriptTokenType("DEDENT")

    // Python
    @JvmField val QUICK_PYTHON_STATEMENT = RenPyScriptTokenType("QUICK_PYTHON_STATEMENT")

    // Others
    @JvmField val IDENTIFIER = RenPyScriptTokenType("IDENTIFIER")
    @JvmField val STRING = RenPyScriptTokenType("STRING")
    @JvmField val COMMENT = RenPyScriptTokenType("COMMENT")
    @JvmField val COLON = RenPyScriptTokenType("COLON")
    @JvmField val NEW_LINE = RenPyScriptTokenType("NEW_LINE")
    @JvmField val EMPTY_LINE = RenPyScriptTokenType("EMPTY_LINE")
    @JvmField val COMMA = RenPyScriptTokenType("COMMA")
    @JvmField val PLAIN_NUMBER = RenPyScriptTokenType("PLAIN_NUMBER")
}
