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
}
