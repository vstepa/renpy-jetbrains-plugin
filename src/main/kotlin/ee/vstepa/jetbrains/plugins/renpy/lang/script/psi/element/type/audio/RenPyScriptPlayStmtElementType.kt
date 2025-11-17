package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.type.audio

class RenPyScriptPlayStmtElementType : RenPyScriptAudioControlStmtElementType("REN_PY_SCRIPT_PLAY_STMT") {
    override val clausesKeywords: List<String> = listOf(
        FADEIN_CLAUSE,
        FADEOUT_CLAUSE,
        LOOP_CLAUSE,
        NOLOOP_CLAUSE,
        IF_CHANGED_CLAUSE,
        VOLUME_CLAUSE,
    )
    override val hasAudio: Boolean = true
    override val stmtName: String = "play"
}
