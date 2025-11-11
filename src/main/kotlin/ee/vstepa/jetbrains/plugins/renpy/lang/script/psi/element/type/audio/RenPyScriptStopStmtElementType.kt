package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.type.audio

class RenPyScriptStopStmtElementType : RenPyScriptAudioControlStmtElementType("REN_PY_SCRIPT_STOP_STMT") {
    override val clausesKeywords: List<String> = listOf(FADEOUT_CLAUSE)
    override val hasAudio: Boolean = false
    override val stmtName: String = "stop"
}
