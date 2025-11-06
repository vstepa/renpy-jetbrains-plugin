package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.ret.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.ret.RenPyScriptReturnStmtValue

class RenPyScriptReturnStmtValueImpl(node: ASTNode) : ASTWrapperPsiElement(node), RenPyScriptReturnStmtValue
