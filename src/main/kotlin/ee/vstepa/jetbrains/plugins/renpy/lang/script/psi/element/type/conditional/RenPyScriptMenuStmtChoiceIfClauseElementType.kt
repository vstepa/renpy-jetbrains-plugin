package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.type.conditional

import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.token.RenPyScriptTokenTypes

class RenPyScriptMenuStmtChoiceIfClauseElementType : RenPyScriptConditionalSubStmtElementType("REN_PY_SCRIPT_MENU_STMT_CHOICE_IF_CLAUSE") {
    override val stmtName = "menu if clause"
    override val hasCondition = true
    override val keywordTokenType = RenPyScriptTokenTypes.IF_KEYWORD

    override val hasIndentedBlock: Boolean = false
}
