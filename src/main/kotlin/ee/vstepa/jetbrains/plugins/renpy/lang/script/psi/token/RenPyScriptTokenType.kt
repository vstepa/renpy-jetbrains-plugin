package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.token

import com.intellij.psi.tree.IElementType
import ee.vstepa.jetbrains.plugins.renpy.lang.RenPyScriptLanguage
import org.jetbrains.annotations.NonNls

class RenPyScriptTokenType(debugName: @NonNls String) : IElementType(debugName, RenPyScriptLanguage) {
    override fun toString() = "RenPyScriptTokenType." + super.toString()
}
