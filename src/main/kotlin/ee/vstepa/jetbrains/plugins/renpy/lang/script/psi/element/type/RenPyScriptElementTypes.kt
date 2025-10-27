package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.type

import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.IFileElementType

object RenPyScriptElementTypes {
    @JvmField
    val REN_PY_SCRIPT: IElementType = RenPyScriptElementType("REN_PY_SCRIPT")

    @JvmField
    val FILE: IFileElementType = RenPyScriptFileElementType.INSTANCE

    @JvmField val STMTS_LIST = RenPyScriptElementType("REN_PY_SCRIPT_STMTS_LIST")

    @JvmField val LABEL = RenPyScriptElementType("REN_PY_SCRIPT_LABEL")
    @JvmField val LABEL_STMT = RenPyScriptElementType("REN_PY_SCRIPT_LABEL_STMT")
    @JvmField val LABEL_STMT_COLON = RenPyScriptElementType("REN_PY_SCRIPT_LABEL_STMT_COLON")
    @JvmField val LABEL_STMT_KEYWORD = RenPyScriptElementType("REN_PY_SCRIPT_LABEL_STMT_KEYWORD")
    @JvmField val LABEL_STMT_NAME = RenPyScriptElementType("REN_PY_SCRIPT_LABEL_STMT_NAME")

    @JvmField val DIALOG_STMT = RenPyScriptElementType("REN_PY_SCRIPT_DIALOG_STMT")
    @JvmField val DIALOG_STMT_IDENTIFIER = RenPyScriptElementType("REN_PY_SCRIPT_DIALOG_STMT_IDENTIFIER")
    @JvmField val DIALOG_STMT_TEXT = RenPyScriptElementType("REN_PY_SCRIPT_DIALOG_STMT_TEXT")
}
