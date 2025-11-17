package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.image.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.image.RenPyScriptImageDisplayControlStmtImagePart

class RenPyScriptImageDisplayControlStmtImagePartImpl(node: ASTNode) : ASTWrapperPsiElement(node),
    RenPyScriptImageDisplayControlStmtImagePart
