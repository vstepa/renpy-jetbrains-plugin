package ee.vstepa.jetbrains.plugins.renpy.lang.script.lexer

import com.intellij.lexer.FlexLexer
import java.util.Stack

abstract class RenPyScriptLexerBase : FlexLexer {
    protected val indents = Stack<Int>()
    protected var pendingDedents = 0
}
