package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.type.conditional

import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.token.RenPyScriptTokenTypes

class RenPyScriptElseStmtElementType : RenPyScriptConditionalSubStmtElementType("REN_PY_SCRIPT_ELSE_STMT") {
    override val stmtName = "else"
    override val hasCondition = false
    override val keywordTokenType = RenPyScriptTokenTypes.ELSE_KEYWORD
}
