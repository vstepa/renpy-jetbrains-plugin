package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.type

import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.IFileElementType
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.type.audio.RenPyScriptPlayStmtElementType
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.type.audio.RenPyScriptQueueStmtElementType
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.type.audio.RenPyScriptStopStmtElementType
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.type.conditional.*

object RenPyScriptElementTypes {
    @JvmField val REN_PY_SCRIPT: IElementType = RenPyScriptElementType("REN_PY_SCRIPT")

    @JvmField val FILE: IFileElementType = RenPyScriptFileElementType.INSTANCE

    // Generals
    @JvmField val GEN_STMT_KEYWORD = RenPyScriptElementType("REN_PY_SCRIPT_GEN_STMT_KEYWORD")
    @JvmField val GEN_STMT_VALUE = RenPyScriptElementType("REN_PY_SCRIPT_GEN_STMT_VALUE")
    @JvmField val GEN_STMT_COLON = RenPyScriptElementType("REN_PY_SCRIPT_GEN_STMT_COLON")
    @JvmField val GEN_STMT_EXPRESSION = RenPyScriptElementType("REN_PY_SCRIPT_GEN_STMT_EXPRESSION")
    @JvmField val GEN_STMT_WITH_CLAUSE = RenPyScriptElementType("REN_PY_SCRIPT_GEN_STMT_WITH_CLAUSE")
    @JvmField val GEN_STMT_ARGS_LIST = RenPyScriptElementType("REN_PY_SCRIPT_GEN_STMT_ARGS_LIST")
    @JvmField val GEN_STMT_ARGS_LIST_OPEN = RenPyScriptElementType("REN_PY_SCRIPT_GEN_STMT_ARGS_LIST_OPEN")
    @JvmField val GEN_STMT_ARGS_LIST_CLOSE = RenPyScriptElementType("REN_PY_SCRIPT_GEN_STMT_ARGS_LIST_CLOSE")
    @JvmField val GEN_STMT_ARGS_LIST_CONTENT = RenPyScriptElementType("REN_PY_SCRIPT_GEN_STMT_ARGS_LIST_CONTENT")
    @JvmField val STMTS_LIST = RenPyScriptElementType("REN_PY_SCRIPT_STMTS_LIST")

    @JvmField val LABEL = RenPyScriptElementType("REN_PY_SCRIPT_LABEL")
    @JvmField val LABEL_STMT = RenPyScriptElementType("REN_PY_SCRIPT_LABEL_STMT")
    @JvmField val LABEL_STMT_NAME = RenPyScriptElementType("REN_PY_SCRIPT_LABEL_STMT_NAME")


    @JvmField val CONDITIONAL_STMT = RenPyScriptElementType("REN_PY_SCRIPT_CONDITIONAL_STMT")
    @JvmField val CONDITIONAL_STMT_CONDITION = RenPyScriptElementType("REN_PY_SCRIPT_CONDITIONAL_STMT_CONDITION")

    @JvmField val IF_STMT = RenPyScriptIfStmtElementType()
    @JvmField val ELIF_STMT = RenPyScriptElifStmtElementType()
    @JvmField val ELSE_STMT = RenPyScriptElseStmtElementType()

    @JvmField val WHILE_STMT = RenPyScriptWhileStmtElementType()


    @JvmField val MENU_STMT = RenPyScriptElementType("REN_PY_SCRIPT_MENU_STMT")
    @JvmField val MENU_STMT_NAME = RenPyScriptElementType("REN_PY_SCRIPT_MENU_STMT_NAME")

    @JvmField val MENU_STMT_CHOICE = RenPyScriptElementType("REN_PY_SCRIPT_MENU_STMT_CHOICE")
    @JvmField val MENU_STMT_CHOICE_CAPTION = RenPyScriptElementType("REN_PY_SCRIPT_MENU_STMT_CHOICE_CAPTION")
    @JvmField val MENU_STMT_CHOICE_IF_CLAUSE = RenPyScriptMenuStmtChoiceIfClauseElementType()

    @JvmField val MENU_STMT_SET_STMT = RenPyScriptElementType("REN_PY_SCRIPT_MENU_STMT_SET_STMT")


    @JvmField val DIALOG_STMT = RenPyScriptElementType("REN_PY_SCRIPT_DIALOG_STMT")
    @JvmField val DIALOG_STMT_TEXT_IDENTIFIER = RenPyScriptElementType("REN_PY_SCRIPT_DIALOG_STMT_TEXT_IDENTIFIER")
    @JvmField val DIALOG_STMT_IDENTIFIER = RenPyScriptElementType("REN_PY_SCRIPT_DIALOG_STMT_IDENTIFIER")
    @JvmField val DIALOG_STMT_TEXT = RenPyScriptElementType("REN_PY_SCRIPT_DIALOG_STMT_TEXT")

    @JvmField val JUMP_STMT = RenPyScriptElementType("REN_PY_SCRIPT_JUMP_STMT")
    @JvmField val JUMP_STMT_TARGET = RenPyScriptElementType("REN_PY_SCRIPT_JUMP_STMT_TARGET")

    @JvmField val CALL_STMT = RenPyScriptElementType("REN_PY_SCRIPT_CALL_STMT")
    @JvmField val CALL_STMT_TARGET = RenPyScriptElementType("REN_PY_SCRIPT_CALL_STMT_TARGET")
    @JvmField val CALL_STMT_FROM_CLAUSE= RenPyScriptElementType("REN_PY_SCRIPT_CALL_STMT_FROM_CLAUSE")


    // Image display control statements element types
    @JvmField val SCENE_STMT = RenPyScriptElementType("REN_PY_SCRIPT_SCENE_STMT")
    @JvmField val SHOW_STMT = RenPyScriptElementType("REN_PY_SCRIPT_SHOW_STMT")
    @JvmField val HIDE_STMT = RenPyScriptElementType("REN_PY_SCRIPT_HIDE_STMT")
    @JvmField val WITH_STMT = RenPyScriptElementType("REN_PY_SCRIPT_WITH_STMT")

    @JvmField val IMAGE_DISPLAY_CONTROL_STMT_IMAGE = RenPyScriptElementType("REN_PY_SCRIPT_IMAGE_DISPLAY_CONTROL_STMT_IMAGE")
    @JvmField val IMAGE_DISPLAY_CONTROL_STMT_IMAGE_PART = RenPyScriptElementType("REN_PY_SCRIPT_IMAGE_DISPLAY_CONTROL_STMT_IMAGE_PART")
    @JvmField val IMAGE_DISPLAY_CONTROL_STMT_PROPS_LIST = RenPyScriptElementType("REN_PY_SCRIPT_IMAGE_DISPLAY_CONTROL_STMT_PROPS_LIST")
    @JvmField val IMAGE_DISPLAY_CONTROL_STMT_PROP = RenPyScriptElementType("REN_PY_SCRIPT_IMAGE_DISPLAY_CONTROL_STMT_PROP")
    @JvmField val IMAGE_DISPLAY_CONTROL_STMT_ATL = RenPyScriptElementType("REN_PY_SCRIPT_IMAGE_DISPLAY_CONTROL_STMT_ATL")


    // Audio control statements element types
    @JvmField val PLAY_STMT = RenPyScriptPlayStmtElementType()
    @JvmField val QUEUE_STMT = RenPyScriptQueueStmtElementType()
    @JvmField val STOP_STMT = RenPyScriptStopStmtElementType()

    @JvmField val AUDIO_CONTROL_STMT_CHANNEL = RenPyScriptElementType("REN_PY_SCRIPT_AUDIO_CONTROL_STMT_CHANNEL")
    @JvmField val AUDIO_CONTROL_STMT_AUDIO = RenPyScriptElementType("REN_PY_SCRIPT_AUDIO_CONTROL_STMT_AUDIO")
    @JvmField val AUDIO_CONTROL_STMT_AUDIO_FILE = RenPyScriptElementType("REN_PY_SCRIPT_AUDIO_CONTROL_STMT_AUDIO_FILE")
    @JvmField val AUDIO_CONTROL_STMT_AUDIO_LIST = RenPyScriptElementType("REN_PY_SCRIPT_AUDIO_CONTROL_STMT_AUDIO_LIST")
    @JvmField val AUDIO_CONTROL_STMT_AUDIO_LIST_OPEN = RenPyScriptElementType("REN_PY_SCRIPT_AUDIO_CONTROL_STMT_AUDIO_LIST_OPEN")
    @JvmField val AUDIO_CONTROL_STMT_AUDIO_LIST_CLOSE = RenPyScriptElementType("REN_PY_SCRIPT_AUDIO_CONTROL_STMT_AUDIO_LIST_CLOSE")
    @JvmField val AUDIO_CONTROL_STMT_AUDIO_LIST_FILES = RenPyScriptElementType("REN_PY_SCRIPT_AUDIO_CONTROL_STMT_AUDIO_LIST_FILES")
    @JvmField val AUDIO_CONTROL_STMT_CLAUSES_LIST = RenPyScriptElementType("REN_PY_SCRIPT_AUDIO_CONTROL_STMT_CLAUSES_LIST")
    @JvmField val AUDIO_CONTROL_STMT_CLAUSE = RenPyScriptElementType("REN_PY_SCRIPT_AUDIO_CONTROL_STMT_CLAUSE")


    @JvmField val PAUSE_STMT = RenPyScriptElementType("REN_PY_SCRIPT_PAUSE_STMT")

    @JvmField val PASS_STMT = RenPyScriptElementType("REN_PY_SCRIPT_PASS_STMT")

    @JvmField val RETURN_STMT = RenPyScriptElementType("REN_PY_SCRIPT_RETURN_STMT")

    @JvmField val ONE_LINE_PYTHON_STMT = RenPyScriptElementType("REN_PY_SCRIPT_ONE_LINE_PYTHON_STMT")

    @JvmField val PYTHON_METHOD_CALL = RenPyScriptElementType("REN_PYTHON_METHOD_CALL")
    @JvmField val PYTHON_METHOD_CALL_NAME = RenPyScriptElementType("REN_PYTHON_METHOD_CALL_NAME")
    @JvmField val PYTHON_METHOD_CALL_PARENTHESES = RenPyScriptElementType("REN_PYTHON_METHOD_CALL_PARENTHESES")
    @JvmField val PYTHON_METHOD_CALL_PARENTHESES_OPEN = RenPyScriptElementType("REN_PYTHON_METHOD_CALL_PARENTHESES_OPEN")
    @JvmField val PYTHON_METHOD_CALL_PARENTHESES_CLOSE = RenPyScriptElementType("REN_PYTHON_METHOD_CALL_PARENTHESES_CLOSE")
    @JvmField val PYTHON_METHOD_CALL_PARENTHESES_CONTENT = RenPyScriptElementType("REN_PYTHON_METHOD_CALL_PARENTHESES_CLOSE")
}
