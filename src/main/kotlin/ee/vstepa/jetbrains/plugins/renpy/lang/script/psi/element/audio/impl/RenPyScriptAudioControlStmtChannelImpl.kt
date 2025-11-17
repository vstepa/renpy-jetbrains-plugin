package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.audio.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.audio.RenPyScriptAudioControlStmtChannel

class RenPyScriptAudioControlStmtChannelImpl(node: ASTNode) : ASTWrapperPsiElement(node), RenPyScriptAudioControlStmtChannel
