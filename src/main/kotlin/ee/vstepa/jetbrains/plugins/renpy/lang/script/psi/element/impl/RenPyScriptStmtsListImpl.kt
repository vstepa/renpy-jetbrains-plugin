package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.RenPyScriptStmtsList

class RenPyScriptStmtsListImpl(node: ASTNode) : ASTWrapperPsiElement(node), RenPyScriptStmtsList
