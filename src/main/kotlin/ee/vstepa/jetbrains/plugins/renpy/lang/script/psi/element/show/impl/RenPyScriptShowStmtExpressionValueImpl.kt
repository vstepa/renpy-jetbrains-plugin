package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.show.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.show.RenPyScriptShowStmtExpressionValue

class RenPyScriptShowStmtExpressionValueImpl(node: ASTNode) : ASTWrapperPsiElement(node),
    RenPyScriptShowStmtExpressionValue
