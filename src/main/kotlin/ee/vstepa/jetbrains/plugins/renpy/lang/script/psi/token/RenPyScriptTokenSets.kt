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
        RenPyScriptTokenTypes.MENU_KEYWORD,
        RenPyScriptTokenTypes.IF_KEYWORD,
        RenPyScriptTokenTypes.WHILE_KEYWORD,
    )

    @JvmField
    val DIALOGUE_STATEMENT_STARTS = TokenSet.create(
        RenPyScriptTokenTypes.IDENTIFIER,
        RenPyScriptTokenTypes.STRING,
        RenPyScriptTokenTypes.MULTILINE_DIALOG_STRING,
    )

    @JvmField
    val MENU_CHOICE_CAPTIONS = TokenSet.create(
        RenPyScriptTokenTypes.STRING,
        RenPyScriptTokenTypes.MULTILINE_DIALOG_STRING,
    )

    @JvmField
    val DIALOGUE_STATEMENT_TEXTS = TokenSet.create(
        RenPyScriptTokenTypes.STRING,
        RenPyScriptTokenTypes.MULTILINE_DIALOG_STRING,
    )

    @JvmField
    val IMAGE_DISPLAY_CONTROL_STATEMENT_STARTS = TokenSet.create(
        RenPyScriptTokenTypes.SHOW_KEYWORD,
        RenPyScriptTokenTypes.SCENE_KEYWORD,
        RenPyScriptTokenTypes.HIDE_KEYWORD,
        RenPyScriptTokenTypes.WITH_KEYWORD
    )

    @JvmField
    val AUDIO_CONTROL_STATEMENT_STARTS = TokenSet.create(
        RenPyScriptTokenTypes.PLAY_KEYWORD,
        RenPyScriptTokenTypes.STOP_KEYWORD,
        RenPyScriptTokenTypes.QUEUE_KEYWORD,
    )

    @JvmField
    val EXPRESSION_VALUES = TokenSet.create(
        RenPyScriptTokenTypes.IDENTIFIER,
        RenPyScriptTokenTypes.STRING
    )

    @JvmField
    val NEW_LINES = TokenSet.create(
        RenPyScriptTokenTypes.NEW_LINE
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
    val HIDE_PARAM_KEYWORDS = TokenSet.create(
        RenPyScriptTokenTypes.ONLAYER_KEYWORD,
    )

    @JvmField
    val IMAGE_LABEL_COMPATIBLE_IDENTIFIERS = TokenSet.create(
        RenPyScriptTokenTypes.IDENTIFIER,
        RenPyScriptTokenTypes.IMAGE_LABEL_IDENTIFIER,
        RenPyScriptTokenTypes.PLAIN_NUMBER,

        // Apparently following keywords (their text) can be used as image label identifiers
        RenPyScriptTokenTypes.PLAY_KEYWORD,
        RenPyScriptTokenTypes.STOP_KEYWORD,
        RenPyScriptTokenTypes.QUEUE_KEYWORD,
        RenPyScriptTokenTypes.LABEL_KEYWORD,
        RenPyScriptTokenTypes.PASS_KEYWORD,
    )

    @JvmField
    val PYTHON_METHOD_CALL_NAME_PARTS = TokenSet.create(
        RenPyScriptTokenTypes.IDENTIFIER,
        RenPyScriptTokenTypes.DOT
    )

    @JvmField
    val NUMBERS = TokenSet.create(
        RenPyScriptTokenTypes.PLAIN_NUMBER,
        RenPyScriptTokenTypes.FLOAT_NUMBER,
    )

    @JvmField
    val AUDIO_CONTROL_STATEMENT_CLAUSE_VALUES = TokenSet.create(
        RenPyScriptTokenTypes.PLAIN_NUMBER,
        RenPyScriptTokenTypes.FLOAT_NUMBER,
        RenPyScriptTokenTypes.STRING,
        RenPyScriptTokenTypes.IDENTIFIER,
    )
}
