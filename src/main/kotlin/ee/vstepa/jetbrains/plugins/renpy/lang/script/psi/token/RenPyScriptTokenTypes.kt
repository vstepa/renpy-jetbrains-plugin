package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.token

import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.type.RenPyScriptElementTypes

object RenPyScriptTokenTypes {
    // FILE
    @JvmField val FILE = RenPyScriptElementTypes.FILE

    // Keywords
    @JvmField val LABEL_KEYWORD = RenPyScriptTokenType("LABEL_KEYWORD")
    @JvmField val JUMP_KEYWORD = RenPyScriptTokenType("JUMP_KEYWORD")
    @JvmField val MENU_KEYWORD = RenPyScriptTokenType("MENU_KEYWORD")

    // Indent/Dedent
    @JvmField val INDENT = RenPyScriptTokenType("INDENT")
    @JvmField val DEDENT = RenPyScriptTokenType("DEDENT")

    // Others
    @JvmField val IDENTIFIER = RenPyScriptTokenType("IDENTIFIER")
    @JvmField val STRING = RenPyScriptTokenType("STRING")
    @JvmField val COMMENT = RenPyScriptTokenType("COMMENT")
    @JvmField val COLON = RenPyScriptTokenType("COLON")
    @JvmField val NEW_LINE = RenPyScriptTokenType("NEW_LINE")
    @JvmField val EMPTY_LINE = RenPyScriptTokenType("EMPTY_LINE")
}
