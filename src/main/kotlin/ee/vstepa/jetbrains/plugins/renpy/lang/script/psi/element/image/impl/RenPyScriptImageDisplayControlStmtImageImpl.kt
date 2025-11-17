package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.image.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.image.RenPyScriptImageDisplayControlStmtImage

class RenPyScriptImageDisplayControlStmtImageImpl(node: ASTNode) : ASTWrapperPsiElement(node),
    RenPyScriptImageDisplayControlStmtImage
