package ee.vstepa.jetbrains.plugins.renpy.lang.script.parser

import com.intellij.lang.ASTNode
import com.intellij.lang.LightPsiParser
import com.intellij.lang.PsiBuilder
import com.intellij.lang.PsiParser
import com.intellij.psi.tree.IElementType

open class RenPyScriptParser : PsiParser, LightPsiParser {
    override fun parse(root: IElementType, builder: PsiBuilder): ASTNode {
        parseLight(root, builder)
        return builder.treeBuilt
    }

    override fun parseLight(root: IElementType, builder: PsiBuilder) {
        val parsing = createParsing(builder)
        val file = builder.mark()
        parsing.parseScript()
        file.done(root)
    }

    protected open fun createParsing(builder: PsiBuilder): RenPyScriptParsing = RenPyScriptParsing(builder)
}
