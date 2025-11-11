package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.audio.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.audio.RenPyScriptQueueStmt

class RenPyScriptQueueStmtImpl(node: ASTNode) : ASTWrapperPsiElement(node), RenPyScriptQueueStmt
