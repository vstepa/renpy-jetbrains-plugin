package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.conditional.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.conditional.RenPyScriptElifStmt

class RenPyScriptElifStmtImpl(node: ASTNode) : ASTWrapperPsiElement(node), RenPyScriptElifStmt
