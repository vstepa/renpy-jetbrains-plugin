package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.type.conditional

import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.token.RenPyScriptTokenTypes

class RenPyScriptWhileStmtElementType : RenPyScriptConditionalSubStmtElementType("REN_PY_SCRIPT_WHILE_STMT") {
    override val stmtName = "while"
    override val hasCondition = true
    override val keywordTokenType = RenPyScriptTokenTypes.WHILE_KEYWORD
}
