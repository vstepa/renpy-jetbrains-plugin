package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.scene.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.scene.RenPyScriptSceneStmtExpression

class RenPyScriptSceneStmtExpressionImpl(node: ASTNode) : ASTWrapperPsiElement(node), RenPyScriptSceneStmtExpression
