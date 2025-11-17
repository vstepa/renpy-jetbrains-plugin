package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.label.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.label.RenPyScriptLabel

class RenPyScriptLabelImpl(node: ASTNode) : ASTWrapperPsiElement(node), RenPyScriptLabel
