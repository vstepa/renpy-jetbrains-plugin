package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.type

import com.intellij.psi.tree.IFileElementType
import ee.vstepa.jetbrains.plugins.renpy.lang.RenPyScriptLanguage

open class RenPyScriptFileElementType(debugName: String) : IFileElementType(debugName, RenPyScriptLanguage.INSTANCE) {
    protected constructor() : this("Ren'Py")

    companion object {
        @JvmField
        val INSTANCE: IFileElementType = RenPyScriptFileElementType()
    }
}
