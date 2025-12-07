package ee.vstepa.jetbrains.plugins.renpy.filetype

import com.intellij.openapi.fileTypes.LanguageFileType
import ee.vstepa.jetbrains.plugins.renpy.RenPyIcons
import ee.vstepa.jetbrains.plugins.renpy.lang.RenPyScriptLanguage

object RenPyCompiledScriptFileType : LanguageFileType(RenPyScriptLanguage) {
    override fun getName() = "Ren'Py Compiled Script File"
    override fun getDescription() = "Ren'Py compiled script language file"
    override fun getDefaultExtension() = "rpyc"
    override fun getIcon() = RenPyIcons.RenPyCompiledScriptFile

    // Overriding it here, because super returns name of the language, and since RenPyScriptFileType also has the same
    // language, getDisplayName() returns same for both - and it triggers build warning.
    // If in the future decide to give RenPyCompiledScriptFileType different language - will be able to remove override
    override fun getDisplayName() = super.getDisplayName() + " (Compiled)"
}
