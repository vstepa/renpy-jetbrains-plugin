package ee.vstepa.jetbrains.plugins.renpy.lang.script.lexer

import com.intellij.psi.tree.IElementType
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.token.RenPyScriptTokenTypes
import java.io.Reader

internal class RenPyScriptLexer(reader: Reader?) : RenPyScriptGeneratedLexer(reader) {
    override fun advance(): IElementType? {
        if (this.pendingDedents > 0) {
            this.pendingDedents--
            return RenPyScriptTokenTypes.DEDENT
        }

        return super.advance()
    }
}
