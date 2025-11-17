package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.conditional.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.conditional.RenPyScriptConditionalStmtCondition

class RenPyScriptConditionalStmtConditionImpl(node: ASTNode) : ASTWrapperPsiElement(node), RenPyScriptConditionalStmtCondition
