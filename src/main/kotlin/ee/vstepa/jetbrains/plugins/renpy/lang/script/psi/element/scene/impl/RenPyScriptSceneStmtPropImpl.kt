package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.scene.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.scene.RenPyScriptSceneStmtProp

class RenPyScriptSceneStmtPropImpl(node: ASTNode) : ASTWrapperPsiElement(node), RenPyScriptSceneStmtProp
