package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.hide.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.hide.RenPyScriptHideStmtProp

class RenPyScriptHideStmtPropImpl(node: ASTNode) : ASTWrapperPsiElement(node), RenPyScriptHideStmtProp
