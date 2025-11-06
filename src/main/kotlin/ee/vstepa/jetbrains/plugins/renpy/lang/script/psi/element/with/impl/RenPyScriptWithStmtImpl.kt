package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.with.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.with.RenPyScriptWithStmt

class RenPyScriptWithStmtImpl(node: ASTNode) : ASTWrapperPsiElement(node), RenPyScriptWithStmt
