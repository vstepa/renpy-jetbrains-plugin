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
    val IMAGE_DISPLAY_CONTROL_STATEMENT_STARTS = TokenSet.create(
        RenPyScriptTokenTypes.SHOW_KEYWORD,
        RenPyScriptTokenTypes.SCENE_KEYWORD
    )

    @JvmField
    val EXPRESSION_VALUES = TokenSet.create(
        RenPyScriptTokenTypes.IDENTIFIER,
        RenPyScriptTokenTypes.STRING
    )

    @JvmField
    val NEW_LINE_OR_SIMILAR_TO_IT_TOKENS = TokenSet.create(
        RenPyScriptTokenTypes.NEW_LINE,
        RenPyScriptTokenTypes.INDENT,
        RenPyScriptTokenTypes.DEDENT
    )

    @JvmField
    val SHOW_OR_SCENE_PARAM_KEYWORDS = TokenSet.create(
        RenPyScriptTokenTypes.AS_KEYWORD,
        RenPyScriptTokenTypes.AT_KEYWORD,
        RenPyScriptTokenTypes.BEHIND_KEYWORD,
        RenPyScriptTokenTypes.ONLAYER_KEYWORD,
        RenPyScriptTokenTypes.ZORDER_KEYWORD
    )

    @JvmField
    val IMAGE_LABEL_COMPATIBLE_IDENTIFIERS = TokenSet.create(
        RenPyScriptTokenTypes.IDENTIFIER,
        RenPyScriptTokenTypes.IMAGE_LABEL_IDENTIFIER,
        RenPyScriptTokenTypes.PLAIN_NUMBER
    )
}
