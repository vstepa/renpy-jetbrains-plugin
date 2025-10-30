package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.label.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.label.RenPyScriptLabelStmtKeyword

class RenPyScriptLabelStmtKeywordImpl(node: ASTNode) : ASTWrapperPsiElement(node), RenPyScriptLabelStmtKeyword
