package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import ee.vstepa.jetbrains.plugins.renpy.filetype.RenPyScriptFileType
import ee.vstepa.jetbrains.plugins.renpy.lang.RenPyScriptLanguage

class RenPyScriptFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, RenPyScriptLanguage) {
    override fun getFileType(): FileType = RenPyScriptFileType.INSTANCE
    override fun toString() = fileType.name
}
