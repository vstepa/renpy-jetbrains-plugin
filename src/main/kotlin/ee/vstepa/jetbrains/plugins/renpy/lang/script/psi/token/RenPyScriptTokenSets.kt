package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.token

import com.intellij.psi.tree.TokenSet

object RenPyScriptTokenSets {
    @JvmField
    val COMMENTS = TokenSet.create(RenPyScriptTokenTypes.COMMENT)

    @JvmField
    val STRINGS = TokenSet.create(RenPyScriptTokenTypes.STRING)

    @JvmField
    val BLOCK_STATEMENT_STARTS = TokenSet.create(
        RenPyScriptTokenTypes.LABEL_KEYWORD,
        RenPyScriptTokenTypes.MENU_KEYWORD
    )

    @JvmField
    val DIALOGUE_STATEMENT_STARTS = TokenSet.create(
        RenPyScriptTokenTypes.IDENTIFIER,
        RenPyScriptTokenTypes.STRING
    )

    @JvmField
    val EXPRESSION_VALUES = TokenSet.create(
        RenPyScriptTokenTypes.IDENTIFIER,
        RenPyScriptTokenTypes.STRING
    )

    @JvmField
    val NEW_LINE_OR_EQUALS = TokenSet.create(
        RenPyScriptTokenTypes.NEW_LINE,
        RenPyScriptTokenTypes.INDENT,
        RenPyScriptTokenTypes.DEDENT
    )
}
