package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.scene.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.scene.RenPyScriptSceneStmtWithClauseKeyword

class RenPyScriptSceneStmtWithClauseKeywordImpl(node: ASTNode) : ASTWrapperPsiElement(node),
    RenPyScriptSceneStmtWithClauseKeyword
