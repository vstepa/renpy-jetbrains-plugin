package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.python.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.python.RenPyScriptOneLinePythonStmt

class RenPyScriptOneLinePythonStmtImpl(node: ASTNode) : ASTWrapperPsiElement(node), RenPyScriptOneLinePythonStmt
