package ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.type.conditional

import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.type.RenPyScriptElementType
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.token.RenPyScriptTokenType
import org.jetbrains.annotations.NonNls

abstract class RenPyScriptConditionalSubStmtElementType(debugName: @NonNls String) : RenPyScriptElementType(debugName) {
    abstract val stmtName: String
    abstract val hasCondition: Boolean
    abstract val keywordTokenType: RenPyScriptTokenType

    open val hasIndentedBlock: Boolean = true
}
