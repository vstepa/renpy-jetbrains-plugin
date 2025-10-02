package ee.vstepa.jetbrains.plugins.renpy

import com.intellij.lang.Language

object RenPyScriptLanguage : Language("Ren'Py Script") {
    @Suppress("unused")
    private fun readResolve(): Any = RenPyScriptLanguage
}
