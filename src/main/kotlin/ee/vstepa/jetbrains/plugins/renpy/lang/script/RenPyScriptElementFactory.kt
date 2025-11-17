package ee.vstepa.jetbrains.plugins.renpy.lang.script

import com.intellij.lang.ASTNode
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.RenPyScriptFile


interface RenPyScriptElementFactory {
    fun createFile(viewProvider: FileViewProvider): RenPyScriptFile
    fun createElement(node: ASTNode): PsiElement
}
