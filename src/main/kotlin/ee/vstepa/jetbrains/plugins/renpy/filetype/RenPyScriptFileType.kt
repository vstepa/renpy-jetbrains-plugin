package ee.vstepa.jetbrains.plugins.renpy.filetype

import com.intellij.openapi.fileTypes.LanguageFileType
import ee.vstepa.jetbrains.plugins.renpy.RenPyIcons
import ee.vstepa.jetbrains.plugins.renpy.lang.RenPyScriptLanguage

class RenPyScriptFileType private constructor() : LanguageFileType(RenPyScriptLanguage.INSTANCE) {
    override fun getName() = "Ren'Py Script File"
    override fun getDescription() = "Ren'Py script language file"
    override fun getDefaultExtension() = "rpy"
    override fun getIcon() = RenPyIcons.RenPyScriptFile

    @Suppress("CompanionObjectInExtension")
    companion object {
        @Suppress("unused")
        @JvmField
        val INSTANCE = RenPyScriptFileType()
    }
}
