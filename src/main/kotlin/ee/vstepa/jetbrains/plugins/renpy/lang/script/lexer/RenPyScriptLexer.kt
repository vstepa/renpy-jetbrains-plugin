package ee.vstepa.jetbrains.plugins.renpy.lang.script.lexer

import com.intellij.psi.tree.IElementType
import java.io.Reader

internal class RenPyScriptLexer(reader: Reader?) : RenPyScriptGeneratedLexer(reader) {
    override fun advance(): IElementType? {
        return super.advance()
    }
}
