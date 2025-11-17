package ee.vstepa.jetbrains.plugins.renpy

import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

object RenPyIcons {
    @JvmField val RenPyScriptFile: Icon = IconLoader.getIcon("/icons/filetypes/renPyScript.svg", RenPyIcons::class.java)
    @JvmField val RenPyCompiledScriptFile: Icon = IconLoader.getIcon("/icons/filetypes/renPyCompiledScript.svg", RenPyIcons::class.java)
}
