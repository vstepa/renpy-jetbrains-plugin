package ee.vstepa.jetbrains.plugins.renpy.lang.script

import com.intellij.lang.ASTNode
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.util.UnsupportedNodeElementTypeException
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.RenPyScriptFile
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.audio.impl.*
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.conditional.impl.*
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.dialog.impl.RenPyScriptDialogStmtIdentifierImpl
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.dialog.impl.RenPyScriptDialogStmtImpl
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.dialog.impl.RenPyScriptDialogStmtTextIdentifierImpl
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.dialog.impl.RenPyScriptDialogStmtTextImpl
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.image.impl.*
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.impl.*
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.jump.impl.RenPyScriptJumpStmtImpl
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.jump.impl.RenPyScriptJumpStmtTargetImpl
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.label.impl.RenPyScriptLabelImpl
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.label.impl.RenPyScriptLabelStmtImpl
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.label.impl.RenPyScriptLabelStmtNameImpl
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.menu.impl.*
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.python.impl.*
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.type.RenPyScriptElementTypes

class RenPyScriptElementFactoryImpl : RenPyScriptElementFactory {
    override fun createFile(viewProvider: FileViewProvider): RenPyScriptFile = RenPyScriptFile(viewProvider)

    override fun createElement(node: ASTNode): PsiElement {
        val elementType = node.elementType

        return when(elementType) {
            RenPyScriptElementTypes.REN_PY_SCRIPT -> RenPyScriptImpl(node)

            RenPyScriptElementTypes.GEN_STMT_KEYWORD -> RenPyScriptGenStmtKeywordImpl(node)
            RenPyScriptElementTypes.GEN_STMT_VALUE -> RenPyScriptGenStmtValueImpl(node)
            RenPyScriptElementTypes.GEN_STMT_COLON -> RenPyScriptGenStmtColonImpl(node)
            RenPyScriptElementTypes.GEN_STMT_EXPRESSION -> RenPyScriptGenStmtExpressionImpl(node)
            RenPyScriptElementTypes.GEN_STMT_WITH_CLAUSE -> RenPyScriptGenStmtWithClauseImpl(node)
            RenPyScriptElementTypes.GEN_STMT_ARGS_LIST -> RenPyScriptGenStmtArgsListImpl(node)
            RenPyScriptElementTypes.GEN_STMT_ARGS_LIST_OPEN -> RenPyScriptGenStmtArgsListOpenImpl(node)
            RenPyScriptElementTypes.GEN_STMT_ARGS_LIST_CLOSE -> RenPyScriptGenStmtArgsListCloseImpl(node)
            RenPyScriptElementTypes.GEN_STMT_ARGS_LIST_CONTENT -> RenPyScriptGenStmtArgsListContentImpl(node)
            RenPyScriptElementTypes.STMTS_LIST -> RenPyScriptStmtsListImpl(node)

            RenPyScriptElementTypes.LABEL -> RenPyScriptLabelImpl(node)
            RenPyScriptElementTypes.LABEL_STMT -> RenPyScriptLabelStmtImpl(node)
            RenPyScriptElementTypes.LABEL_STMT_NAME -> RenPyScriptLabelStmtNameImpl(node)


            RenPyScriptElementTypes.CONDITIONAL_STMT -> RenPyScriptConditionalStmtImpl(node)
            RenPyScriptElementTypes.CONDITIONAL_STMT_CONDITION -> RenPyScriptConditionalStmtConditionImpl(node)

            RenPyScriptElementTypes.IF_STMT -> RenPyScriptIfStmtImpl(node)
            RenPyScriptElementTypes.ELIF_STMT -> RenPyScriptElifStmtImpl(node)
            RenPyScriptElementTypes.ELSE_STMT -> RenPyScriptElseStmtImpl(node)

            RenPyScriptElementTypes.WHILE_STMT -> RenPyScriptWhileStmtImpl(node)


            RenPyScriptElementTypes.MENU_STMT -> RenPyScriptMenuStmtImpl(node)
            RenPyScriptElementTypes.MENU_STMT_NAME -> RenPyScriptMenuStmtNameImpl(node)

            RenPyScriptElementTypes.MENU_STMT_CHOICE -> RenPyScriptMenuStmtChoiceImpl(node)
            RenPyScriptElementTypes.MENU_STMT_CHOICE_CAPTION -> RenPyScriptMenuStmtChoiceCaptionImpl(node)
            RenPyScriptElementTypes.MENU_STMT_CHOICE_IF_CLAUSE -> RenPyScriptMenuStmtChoiceIfClauseImpl(node)

            RenPyScriptElementTypes.MENU_STMT_SET_STMT -> RenPyScriptMenuStmtSetStmtImpl(node)


            RenPyScriptElementTypes.DIALOG_STMT -> RenPyScriptDialogStmtImpl(node)
            RenPyScriptElementTypes.DIALOG_STMT_IDENTIFIER -> RenPyScriptDialogStmtIdentifierImpl(node)
            RenPyScriptElementTypes.DIALOG_STMT_TEXT -> RenPyScriptDialogStmtTextImpl(node)
            RenPyScriptElementTypes.DIALOG_STMT_TEXT_IDENTIFIER -> RenPyScriptDialogStmtTextIdentifierImpl(node)

            RenPyScriptElementTypes.JUMP_STMT -> RenPyScriptJumpStmtImpl(node)
            RenPyScriptElementTypes.JUMP_STMT_TARGET -> RenPyScriptJumpStmtTargetImpl(node)

            RenPyScriptElementTypes.CALL_STMT -> RenPyScriptCallStmtImpl(node)
            RenPyScriptElementTypes.CALL_STMT_TARGET -> RenPyScriptCallStmtTargetImpl(node)
            RenPyScriptElementTypes.CALL_STMT_FROM_CLAUSE -> RenPyScriptCallStmtFromClauseImpl(node)


            RenPyScriptElementTypes.SCENE_STMT -> RenPyScriptSceneStmtImpl(node)
            RenPyScriptElementTypes.SHOW_STMT -> RenPyScriptShowStmtImpl(node)
            RenPyScriptElementTypes.HIDE_STMT -> RenPyScriptHideStmtImpl(node)
            RenPyScriptElementTypes.WITH_STMT -> RenPyScriptWithStmtImpl(node)

            RenPyScriptElementTypes.IMAGE_DISPLAY_CONTROL_STMT_IMAGE -> RenPyScriptImageDisplayControlStmtImageImpl(node)
            RenPyScriptElementTypes.IMAGE_DISPLAY_CONTROL_STMT_IMAGE_PART -> RenPyScriptImageDisplayControlStmtImagePartImpl(node)
            RenPyScriptElementTypes.IMAGE_DISPLAY_CONTROL_STMT_PROPS_LIST -> RenPyScriptImageDisplayControlStmtPropsListImpl(node)
            RenPyScriptElementTypes.IMAGE_DISPLAY_CONTROL_STMT_PROP -> RenPyScriptImageDisplayControlStmtPropImpl(node)
            RenPyScriptElementTypes.IMAGE_DISPLAY_CONTROL_STMT_ATL -> RenPyScriptImageDisplayControlStmtATLImpl(node)


            RenPyScriptElementTypes.PLAY_STMT -> RenPyScriptPlayStmtImpl(node)
            RenPyScriptElementTypes.QUEUE_STMT -> RenPyScriptQueueStmtImpl(node)
            RenPyScriptElementTypes.STOP_STMT -> RenPyScriptStopStmtImpl(node)

            RenPyScriptElementTypes.AUDIO_CONTROL_STMT_CHANNEL -> RenPyScriptAudioControlStmtChannelImpl(node)
            RenPyScriptElementTypes.AUDIO_CONTROL_STMT_AUDIO -> RenPyScriptAudioControlStmtAudioImpl(node)
            RenPyScriptElementTypes.AUDIO_CONTROL_STMT_AUDIO_FILE -> RenPyScriptAudioControlStmtAudioFileImpl(node)
            RenPyScriptElementTypes.AUDIO_CONTROL_STMT_AUDIO_LIST -> RenPyScriptAudioControlStmtAudioListImpl(node)
            RenPyScriptElementTypes.AUDIO_CONTROL_STMT_AUDIO_LIST_OPEN -> RenPyScriptAudioControlStmtAudioListOpenImpl(node)
            RenPyScriptElementTypes.AUDIO_CONTROL_STMT_AUDIO_LIST_CLOSE -> RenPyScriptAudioControlStmtAudioListCloseImpl(node)
            RenPyScriptElementTypes.AUDIO_CONTROL_STMT_AUDIO_LIST_FILES -> RenPyScriptAudioControlStmtAudioListFilesImpl(node)
            RenPyScriptElementTypes.AUDIO_CONTROL_STMT_CLAUSES_LIST -> RenPyScriptAudioControlStmtClausesListImpl(node)
            RenPyScriptElementTypes.AUDIO_CONTROL_STMT_CLAUSE -> RenPyScriptAudioControlStmtClauseImpl(node)


            RenPyScriptElementTypes.PAUSE_STMT -> RenPyScriptPauseStmtImpl(node)

            RenPyScriptElementTypes.PASS_STMT -> RenPyScriptPassStmtImpl(node)

            RenPyScriptElementTypes.RETURN_STMT -> RenPyScriptReturnStmtImpl(node)

            RenPyScriptElementTypes.ONE_LINE_PYTHON_STMT -> RenPyScriptOneLinePythonStmtImpl(node)

            RenPyScriptElementTypes.PYTHON_METHOD_CALL -> RenPyScriptPythonMethodCallImpl(node)
            RenPyScriptElementTypes.PYTHON_METHOD_CALL_NAME -> RenPyScriptPythonMethodCallNameImpl(node)
            RenPyScriptElementTypes.PYTHON_METHOD_CALL_PARENTHESES -> RenPyScriptPythonMethodCallParenthesesImpl(node)
            RenPyScriptElementTypes.PYTHON_METHOD_CALL_PARENTHESES_OPEN -> RenPyScriptPythonMethodCallParenthesesOpenImpl(node)
            RenPyScriptElementTypes.PYTHON_METHOD_CALL_PARENTHESES_CLOSE -> RenPyScriptPythonMethodCallParenthesesCloseImpl(node)
            RenPyScriptElementTypes.PYTHON_METHOD_CALL_PARENTHESES_CONTENT -> RenPyScriptPythonMethodCallParenthesesContentImpl(node)

            else -> throw UnsupportedNodeElementTypeException(node)
        }
    }
}
