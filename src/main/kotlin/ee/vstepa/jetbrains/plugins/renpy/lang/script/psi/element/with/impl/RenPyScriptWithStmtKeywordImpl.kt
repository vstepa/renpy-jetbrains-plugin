package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.with.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.with.RenPyScriptWithStmtKeyword

class RenPyScriptWithStmtKeywordImpl(node: ASTNode) : ASTWrapperPsiElement(node), RenPyScriptWithStmtKeyword
