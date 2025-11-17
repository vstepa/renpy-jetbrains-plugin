package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.type.conditional

import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.token.RenPyScriptTokenTypes

class RenPyScriptIfStmtElementType : RenPyScriptConditionalSubStmtElementType("REN_PY_SCRIPT_IF_STMT") {
    override val stmtName = "if"
    override val hasCondition = true
    override val keywordTokenType = RenPyScriptTokenTypes.IF_KEYWORD
}
