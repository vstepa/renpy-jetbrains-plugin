package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.type

import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.IFileElementType
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.type.audio.RenPyScriptPlayStmtElementType
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.type.audio.RenPyScriptQueueStmtElementType
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.type.audio.RenPyScriptStopStmtElementType

object RenPyScriptElementTypes {
    @JvmField val REN_PY_SCRIPT: IElementType = RenPyScriptElementType("REN_PY_SCRIPT")

    @JvmField val FILE: IFileElementType = RenPyScriptFileElementType.INSTANCE

    // Generals
    @JvmField val GEN_STMT_KEYWORD = RenPyScriptElementType("REN_PY_SCRIPT_GEN_STMT_KEYWORD")
    @JvmField val GEN_STMT_VALUE = RenPyScriptElementType("REN_PY_SCRIPT_GEN_STMT_VALUE")
    @JvmField val STMTS_LIST = RenPyScriptElementType("REN_PY_SCRIPT_STMTS_LIST")

    @JvmField val LABEL = RenPyScriptElementType("REN_PY_SCRIPT_LABEL")
    @JvmField val LABEL_STMT = RenPyScriptElementType("REN_PY_SCRIPT_LABEL_STMT")
    @JvmField val LABEL_STMT_COLON = RenPyScriptElementType("REN_PY_SCRIPT_LABEL_STMT_COLON")
    @JvmField val LABEL_STMT_NAME = RenPyScriptElementType("REN_PY_SCRIPT_LABEL_STMT_NAME")

    @JvmField val DIALOG_STMT = RenPyScriptElementType("REN_PY_SCRIPT_DIALOG_STMT")
    @JvmField val DIALOG_STMT_TEXT_IDENTIFIER = RenPyScriptElementType("REN_PY_SCRIPT_DIALOG_STMT_TEXT_IDENTIFIER")
    @JvmField val DIALOG_STMT_IDENTIFIER = RenPyScriptElementType("REN_PY_SCRIPT_DIALOG_STMT_IDENTIFIER")
    @JvmField val DIALOG_STMT_TEXT = RenPyScriptElementType("REN_PY_SCRIPT_DIALOG_STMT_TEXT")

    @JvmField val JUMP_STMT = RenPyScriptElementType("REN_PY_SCRIPT_JUMP_STMT")
    @JvmField val JUMP_STMT_TARGET = RenPyScriptElementType("REN_PY_SCRIPT_JUMP_STMT_TARGET")
    @JvmField val JUMP_STMT_EXPRESSION = RenPyScriptElementType("REN_PY_SCRIPT_JUMP_STMT_EXPRESSION")
    @JvmField val JUMP_STMT_EXPRESSION_KEYWORD = RenPyScriptElementType("REN_PY_SCRIPT_JUMP_STMT_EXPRESSION_KEYWORD")
    @JvmField val JUMP_STMT_EXPRESSION_VALUE = RenPyScriptElementType("REN_PY_SCRIPT_JUMP_STMT_EXPRESSION_VALUE")

    @JvmField val SCENE_STMT = RenPyScriptElementType("REN_PY_SCRIPT_SCENE_STMT")
    @JvmField val SCENE_STMT_IMAGE = RenPyScriptElementType("REN_PY_SCRIPT_SCENE_STMT_IMAGE")
    @JvmField val SCENE_STMT_IMAGE_PART = RenPyScriptElementType("REN_PY_SCRIPT_SCENE_STMT_IMAGE_PART")
    @JvmField val SCENE_STMT_EXPRESSION = RenPyScriptElementType("REN_PY_SCRIPT_SCENE_STMT_EXPRESSION")
    @JvmField val SCENE_STMT_EXPRESSION_KEYWORD = RenPyScriptElementType("REN_PY_SCRIPT_SCENE_STMT_EXPRESSION_KEYWORD")
    @JvmField val SCENE_STMT_EXPRESSION_VALUE = RenPyScriptElementType("REN_PY_SCRIPT_SCENE_STMT_EXPRESSION_VALUE")
    @JvmField val SCENE_STMT_PROPS_LIST = RenPyScriptElementType("REN_PY_SCRIPT_SCENE_STMT_PROPS_LIST")
    @JvmField val SCENE_STMT_PROP = RenPyScriptElementType("REN_PY_SCRIPT_SCENE_STMT_PROP")
    @JvmField val SCENE_STMT_PROP_KEYWORD = RenPyScriptElementType("REN_PY_SCRIPT_SCENE_STMT_PROP_KEYWORD")
    @JvmField val SCENE_STMT_PROP_VALUE = RenPyScriptElementType("REN_PY_SCRIPT_SCENE_STMT_PROP_VALUE")
    @JvmField val SCENE_STMT_WITH_CLAUSE = RenPyScriptElementType("REN_PY_SCRIPT_SCENE_STMT_WITH_CLAUSE")
    @JvmField val SCENE_STMT_WITH_CLAUSE_KEYWORD = RenPyScriptElementType("REN_PY_SCRIPT_SCENE_STMT_WITH_CLAUSE_KEYWORD")
    @JvmField val SCENE_STMT_WITH_CLAUSE_VALUE = RenPyScriptElementType("REN_PY_SCRIPT_SCENE_STMT_WITH_CLAUSE_VALUE")
    @JvmField val SCENE_STMT_COLON = RenPyScriptElementType("REN_PY_SCRIPT_SCENE_STMT_COLON")
    @JvmField val SCENE_STMT_ATL = RenPyScriptElementType("REN_PY_SCRIPT_SCENE_STMT_ATL")

    @JvmField val SHOW_STMT = RenPyScriptElementType("REN_PY_SCRIPT_SHOW_STMT")
    @JvmField val SHOW_STMT_IMAGE = RenPyScriptElementType("REN_PY_SCRIPT_SHOW_STMT_IMAGE")
    @JvmField val SHOW_STMT_IMAGE_PART = RenPyScriptElementType("REN_PY_SCRIPT_SHOW_STMT_IMAGE_PART")
    @JvmField val SHOW_STMT_EXPRESSION = RenPyScriptElementType("REN_PY_SCRIPT_SHOW_STMT_EXPRESSION")
    @JvmField val SHOW_STMT_EXPRESSION_KEYWORD = RenPyScriptElementType("REN_PY_SCRIPT_SHOW_STMT_EXPRESSION_KEYWORD")
    @JvmField val SHOW_STMT_EXPRESSION_VALUE = RenPyScriptElementType("REN_PY_SCRIPT_SHOW_STMT_EXPRESSION_VALUE")
    @JvmField val SHOW_STMT_PROPS_LIST = RenPyScriptElementType("REN_PY_SCRIPT_SHOW_STMT_PROPS_LIST")
    @JvmField val SHOW_STMT_PROP = RenPyScriptElementType("REN_PY_SCRIPT_SHOW_STMT_PROP")
    @JvmField val SHOW_STMT_PROP_KEYWORD = RenPyScriptElementType("REN_PY_SCRIPT_SHOW_STMT_PROP_KEYWORD")
    @JvmField val SHOW_STMT_PROP_VALUE = RenPyScriptElementType("REN_PY_SCRIPT_SHOW_STMT_PROP_VALUE")
    @JvmField val SHOW_STMT_WITH_CLAUSE = RenPyScriptElementType("REN_PY_SCRIPT_SHOW_STMT_WITH_CLAUSE")
    @JvmField val SHOW_STMT_WITH_CLAUSE_KEYWORD = RenPyScriptElementType("REN_PY_SCRIPT_SHOW_STMT_WITH_CLAUSE_KEYWORD")
    @JvmField val SHOW_STMT_WITH_CLAUSE_VALUE = RenPyScriptElementType("REN_PY_SCRIPT_SHOW_STMT_WITH_CLAUSE_VALUE")
    @JvmField val SHOW_STMT_COLON = RenPyScriptElementType("REN_PY_SCRIPT_SHOW_STMT_COLON")
    @JvmField val SHOW_STMT_ATL = RenPyScriptElementType("REN_PY_SCRIPT_SHOW_STMT_ATL")

    @JvmField val HIDE_STMT = RenPyScriptElementType("REN_PY_SCRIPT_HIDE_STMT")
    @JvmField val HIDE_STMT_IMAGE = RenPyScriptElementType("REN_PY_SCRIPT_HIDE_STMT_IMAGE")
    @JvmField val HIDE_STMT_IMAGE_PART = RenPyScriptElementType("REN_PY_SCRIPT_HIDE_STMT_IMAGE_PART")
    @JvmField val HIDE_STMT_PROPS_LIST = RenPyScriptElementType("REN_PY_SCRIPT_HIDE_STMT_PROPS_LIST")
    @JvmField val HIDE_STMT_PROP = RenPyScriptElementType("REN_PY_SCRIPT_HIDE_STMT_PROP")
    @JvmField val HIDE_STMT_PROP_KEYWORD = RenPyScriptElementType("REN_PY_SCRIPT_HIDE_STMT_PROP_KEYWORD")
    @JvmField val HIDE_STMT_PROP_VALUE = RenPyScriptElementType("REN_PY_SCRIPT_HIDE_STMT_PROP_VALUE")
    @JvmField val HIDE_STMT_WITH_CLAUSE = RenPyScriptElementType("REN_PY_SCRIPT_HIDE_STMT_WITH_CLAUSE")
    @JvmField val HIDE_STMT_WITH_CLAUSE_KEYWORD = RenPyScriptElementType("REN_PY_SCRIPT_HIDE_STMT_WITH_CLAUSE_KEYWORD")
    @JvmField val HIDE_STMT_WITH_CLAUSE_VALUE = RenPyScriptElementType("REN_PY_SCRIPT_HIDE_STMT_WITH_CLAUSE_VALUE")

    @JvmField val WITH_STMT = RenPyScriptElementType("REN_PY_SCRIPT_WITH_STMT")


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
    @JvmField val AUDIO_CONTROL_STMT_CLAUSE_KEYWORD = RenPyScriptElementType("REN_PY_SCRIPT_AUDIO_CONTROL_STMT_CLAUSE_KEYWORD")
    @JvmField val AUDIO_CONTROL_STMT_CLAUSE_VALUE = RenPyScriptElementType("REN_PY_SCRIPT_AUDIO_CONTROL_STMT_CLAUSE_VALUE")


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
