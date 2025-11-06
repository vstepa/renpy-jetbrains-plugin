package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.hide.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.hide.RenPyScriptHideStmtWithClauseKeyword

class RenPyScriptHideStmtWithClauseKeywordImpl(node: ASTNode) : ASTWrapperPsiElement(node),
    RenPyScriptHideStmtWithClauseKeyword
