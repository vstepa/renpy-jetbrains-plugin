package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.pass.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.pass.RenPyScriptPassStmtKeyword

class RenPyScriptPassStmtKeywordImpl(node: ASTNode) : ASTWrapperPsiElement(node), RenPyScriptPassStmtKeyword
