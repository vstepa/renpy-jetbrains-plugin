package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.type.audio

class RenPyScriptQueueStmtElementType : RenPyScriptAudioControlStmtElementType("REN_PY_SCRIPT_QUEUE_STMT") {
    override val clausesKeywords: List<String> = listOf(FADEIN_CLAUSE, LOOP_CLAUSE, NOLOOP_CLAUSE, VOLUME_CLAUSE)
    override val hasAudio: Boolean = true
    override val stmtName: String = "queue"
}
