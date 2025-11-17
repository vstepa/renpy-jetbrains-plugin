package ee.vstepa.jetbrains.plugins.renpy.lang.script

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import ee.vstepa.jetbrains.plugins.renpy.lang.script.lexer.RenPyScriptLexerAdapter
import ee.vstepa.jetbrains.plugins.renpy.lang.script.parser.RenPyScriptParser
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.type.RenPyScriptElementTypes
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.token.RenPyScriptTokenSets

class RenPyScriptParserDefinition : ParserDefinition {

    private val elementFactory: RenPyScriptElementFactory by lazy {
        service<RenPyScriptElementFactory>()
    }

    override fun createLexer(project: Project?): Lexer = RenPyScriptLexerAdapter()

    override fun createParser(project: Project?): PsiParser = RenPyScriptParser()

    override fun getFileNodeType(): IFileElementType = RenPyScriptElementTypes.FILE

    override fun getCommentTokens(): TokenSet = RenPyScriptTokenSets.COMMENTS

    override fun getStringLiteralElements(): TokenSet = RenPyScriptTokenSets.STRINGS

    override fun createElement(node: ASTNode): PsiElement = elementFactory.createElement(node)

    override fun createFile(viewProvider: FileViewProvider): PsiFile = elementFactory.createFile(viewProvider)
}
