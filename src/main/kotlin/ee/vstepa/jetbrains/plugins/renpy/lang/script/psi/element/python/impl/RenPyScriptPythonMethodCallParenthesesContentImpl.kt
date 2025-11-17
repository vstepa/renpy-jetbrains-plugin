package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.python.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.python.RenPyScriptPythonMethodCallParenthesesContent

class RenPyScriptPythonMethodCallParenthesesContentImpl(node: ASTNode) : ASTWrapperPsiElement(node), RenPyScriptPythonMethodCallParenthesesContent
