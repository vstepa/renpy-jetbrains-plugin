package ee.vstepa.jetbrains.plugins.renpy.lang.script

import com.intellij.lang.ASTNode
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.util.UnsupportedNodeElementTypeException
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.RenPyScriptFile
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.impl.*
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.type.RenPyScriptElementTypes

class RenPyScriptElementFactoryImpl : RenPyScriptElementFactory {
    override fun createFile(viewProvider: FileViewProvider): RenPyScriptFile = RenPyScriptFile(viewProvider)

    override fun createElement(node: ASTNode): PsiElement {
        val elementType = node.elementType

        return when(elementType) {
            RenPyScriptElementTypes.REN_PY_SCRIPT -> RenPyScriptImpl(node)
            RenPyScriptElementTypes.LABEL -> RenPyScriptLabelImpl(node)
            RenPyScriptElementTypes.LABEL_STMT -> RenPyScriptLabelStmtImpl(node)
            RenPyScriptElementTypes.LABEL_STMT_COLON -> RenPyScriptLabelStmtColonImpl(node)
            RenPyScriptElementTypes.LABEL_STMT_KEYWORD -> RenPyScriptLabelStmtKeywordImpl(node)
            RenPyScriptElementTypes.LABEL_STMT_NAME -> RenPyScriptLabelStmtNameImpl(node)
            RenPyScriptElementTypes.STMTS_LIST -> RenPyScriptStmtsListImpl(node)
            RenPyScriptElementTypes.DIALOG_STMT -> RenPyScriptDialogStmtImpl(node)
            RenPyScriptElementTypes.DIALOG_STMT_IDENTIFIER -> RenPyScriptDialogStmtIdentifierImpl(node)
            RenPyScriptElementTypes.DIALOG_STMT_TEXT -> RenPyScriptDialogStmtTextImpl(node)

            else -> throw UnsupportedNodeElementTypeException(node)
        }
    }
}
