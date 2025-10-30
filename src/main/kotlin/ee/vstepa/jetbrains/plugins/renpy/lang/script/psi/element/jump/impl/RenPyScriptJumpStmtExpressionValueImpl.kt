package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.jump.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.jump.RenPyScriptJumpStmtExpressionValue

class RenPyScriptJumpStmtExpressionValueImpl(node: ASTNode) : ASTWrapperPsiElement(node),
    RenPyScriptJumpStmtExpressionValue
