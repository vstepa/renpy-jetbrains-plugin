package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.type.audio

import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.type.RenPyScriptElementType
import org.jetbrains.annotations.NonNls

abstract class RenPyScriptAudioControlStmtElementType(debugName: @NonNls String) : RenPyScriptElementType(debugName) {
    abstract val clausesKeywords: List<String>
    abstract val hasAudio: Boolean
    abstract val stmtName: String

    companion object {
        const val FADEIN_CLAUSE = "fadein"
        const val FADEOUT_CLAUSE = "fadeout"
        const val LOOP_CLAUSE = "loop"
        const val NOLOOP_CLAUSE = "noloop"
        const val IF_CHANGED_CLAUSE = "if_changed"
        const val VOLUME_CLAUSE = "volume"

        val ALL_CLAUSES = listOf(FADEIN_CLAUSE, FADEOUT_CLAUSE, LOOP_CLAUSE, NOLOOP_CLAUSE, IF_CHANGED_CLAUSE, VOLUME_CLAUSE)
        val CLAUSES_WITH_VALUES = listOf(FADEIN_CLAUSE, FADEOUT_CLAUSE, VOLUME_CLAUSE)
    }
}
