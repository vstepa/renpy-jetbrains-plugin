package ee.vstepa.jetbrains.plugins.renpy.filetype

import com.intellij.openapi.fileTypes.LanguageFileType
import ee.vstepa.jetbrains.plugins.renpy.RenPyIcons
import ee.vstepa.jetbrains.plugins.renpy.lang.RenPyScriptLanguage

object RenPyScriptFileType : LanguageFileType(RenPyScriptLanguage) {
    override fun getName() = "Ren'Py Script File"
    override fun getDescription() = "Ren'Py script language file"
    override fun getDefaultExtension() = "rpy"
    override fun getIcon() = RenPyIcons.RenPyScriptFile
}
