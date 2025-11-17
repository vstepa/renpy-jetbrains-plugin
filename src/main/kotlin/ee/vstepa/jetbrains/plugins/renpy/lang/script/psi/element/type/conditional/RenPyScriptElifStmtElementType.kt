package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.type.conditional

import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.token.RenPyScriptTokenTypes

class RenPyScriptElifStmtElementType : RenPyScriptConditionalSubStmtElementType("REN_PY_SCRIPT_ELIF_STMT") {
    override val stmtName = "elif"
    override val hasCondition = true
    override val keywordTokenType = RenPyScriptTokenTypes.ELIF_KEYWORD
}
