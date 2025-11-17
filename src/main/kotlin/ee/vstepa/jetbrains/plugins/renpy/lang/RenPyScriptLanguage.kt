package ee.vstepa.jetbrains.plugins.renpy.lang

import com.intellij.lang.Language

class RenPyScriptLanguage : Language("Ren'Py Script") {
    @Suppress("unused")
    private fun readResolve(): Any = INSTANCE

    companion object {
        @JvmField
        val INSTANCE: RenPyScriptLanguage = RenPyScriptLanguage()
    }
}
