package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.pass.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.pass.RenPyScriptPassStmt

class RenPyScriptPassStmtImpl(node: ASTNode) : ASTWrapperPsiElement(node), RenPyScriptPassStmt
