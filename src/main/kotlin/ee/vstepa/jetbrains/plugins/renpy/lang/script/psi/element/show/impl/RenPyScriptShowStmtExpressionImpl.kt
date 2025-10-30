package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.show.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.show.RenPyScriptShowStmtExpression

class RenPyScriptShowStmtExpressionImpl(node: ASTNode) : ASTWrapperPsiElement(node), RenPyScriptShowStmtExpression
