package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.with.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.with.RenPyScriptWithStmtTransitionObj

class RenPyScriptWithStmtTransitionObjImpl(node: ASTNode) : ASTWrapperPsiElement(node),
    RenPyScriptWithStmtTransitionObj
