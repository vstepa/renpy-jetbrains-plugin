package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.menu.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.menu.RenPyScriptMenuStmtChoice

class RenPyScriptMenuStmtChoiceImpl(node: ASTNode) : ASTWrapperPsiElement(node), RenPyScriptMenuStmtChoice
