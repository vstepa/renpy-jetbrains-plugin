package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.ret.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.ret.RenPyScriptReturnStmt

class RenPyScriptReturnStmtImpl(node: ASTNode) : ASTWrapperPsiElement(node), RenPyScriptReturnStmt
