package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.menu.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.menu.RenPyScriptMenuStmt

class RenPyScriptMenuStmtImpl(node: ASTNode) : ASTWrapperPsiElement(node), RenPyScriptMenuStmt
