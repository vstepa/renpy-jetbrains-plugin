package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.dialog.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.dialog.RenPyScriptDialogStmt

class RenPyScriptDialogStmtImpl(node: ASTNode) : ASTWrapperPsiElement(node), RenPyScriptDialogStmt
