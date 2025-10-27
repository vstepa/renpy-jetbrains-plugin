package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.type

import com.intellij.psi.tree.IElementType
import ee.vstepa.jetbrains.plugins.renpy.lang.RenPyScriptLanguage
import org.jetbrains.annotations.NonNls

class RenPyScriptElementType(debugName: @NonNls String) : IElementType(debugName, RenPyScriptLanguage.INSTANCE)
