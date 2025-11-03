package ee.vstepa.jetbrains.plugins.renpy.lang.script

import com.intellij.lang.ASTNode
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.util.UnsupportedNodeElementTypeException
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.RenPyScriptFile
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.dialog.impl.RenPyScriptDialogStmtIdentifierImpl
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.dialog.impl.RenPyScriptDialogStmtImpl
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.dialog.impl.RenPyScriptDialogStmtTextIdentifierImpl
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.dialog.impl.RenPyScriptDialogStmtTextImpl
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.impl.RenPyScriptImpl
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.impl.RenPyScriptStmtsListImpl
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.jump.impl.*
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.label.impl.*
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.scene.impl.*
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.show.impl.*
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.element.type.RenPyScriptElementTypes

class RenPyScriptElementFactoryImpl : RenPyScriptElementFactory {
    override fun createFile(viewProvider: FileViewProvider): RenPyScriptFile = RenPyScriptFile(viewProvider)

    override fun createElement(node: ASTNode): PsiElement {
        val elementType = node.elementType

        return when(elementType) {
            RenPyScriptElementTypes.REN_PY_SCRIPT -> RenPyScriptImpl(node)

            RenPyScriptElementTypes.STMTS_LIST -> RenPyScriptStmtsListImpl(node)

            RenPyScriptElementTypes.LABEL -> RenPyScriptLabelImpl(node)
            RenPyScriptElementTypes.LABEL_STMT -> RenPyScriptLabelStmtImpl(node)
            RenPyScriptElementTypes.LABEL_STMT_COLON -> RenPyScriptLabelStmtColonImpl(node)
            RenPyScriptElementTypes.LABEL_STMT_KEYWORD -> RenPyScriptLabelStmtKeywordImpl(node)
            RenPyScriptElementTypes.LABEL_STMT_NAME -> RenPyScriptLabelStmtNameImpl(node)

            RenPyScriptElementTypes.DIALOG_STMT -> RenPyScriptDialogStmtImpl(node)
            RenPyScriptElementTypes.DIALOG_STMT_IDENTIFIER -> RenPyScriptDialogStmtIdentifierImpl(node)
            RenPyScriptElementTypes.DIALOG_STMT_TEXT -> RenPyScriptDialogStmtTextImpl(node)
            RenPyScriptElementTypes.DIALOG_STMT_TEXT_IDENTIFIER -> RenPyScriptDialogStmtTextIdentifierImpl(node)

            RenPyScriptElementTypes.JUMP_STMT -> RenPyScriptJumpStmtImpl(node)
            RenPyScriptElementTypes.JUMP_STMT_KEYWORD -> RenPyScriptJumpStmtKeywordImpl(node)
            RenPyScriptElementTypes.JUMP_STMT_TARGET -> RenPyScriptJumpStmtTargetImpl(node)
            RenPyScriptElementTypes.JUMP_STMT_EXPRESSION -> RenPyScriptJumpStmtExpressionImpl(node)
            RenPyScriptElementTypes.JUMP_STMT_EXPRESSION_KEYWORD -> RenPyScriptJumpStmtExpressionKeywordImpl(node)
            RenPyScriptElementTypes.JUMP_STMT_EXPRESSION_VALUE -> RenPyScriptJumpStmtExpressionValueImpl(node)

            RenPyScriptElementTypes.SCENE_STMT -> RenPyScriptSceneStmtImpl(node)
            RenPyScriptElementTypes.SCENE_STMT_KEYWORD -> RenPyScriptSceneStmtKeywordImpl(node)
            RenPyScriptElementTypes.SCENE_STMT_IMAGE -> RenPyScriptSceneStmtImageImpl(node)
            RenPyScriptElementTypes.SCENE_STMT_IMAGE_PART -> RenPyScriptSceneStmtImagePartImpl(node)
            RenPyScriptElementTypes.SCENE_STMT_EXPRESSION -> RenPyScriptSceneStmtExpressionImpl(node)
            RenPyScriptElementTypes.SCENE_STMT_EXPRESSION_KEYWORD -> RenPyScriptSceneStmtExpressionKeywordImpl(node)
            RenPyScriptElementTypes.SCENE_STMT_EXPRESSION_VALUE -> RenPyScriptSceneStmtExpressionValueImpl(node)
            RenPyScriptElementTypes.SCENE_STMT_PROPS_LIST -> RenPyScriptSceneStmtPropsListImpl(node)
            RenPyScriptElementTypes.SCENE_STMT_PROP -> RenPyScriptSceneStmtPropImpl(node)
            RenPyScriptElementTypes.SCENE_STMT_PROP_KEYWORD -> RenPyScriptSceneStmtPropKeywordImpl(node)
            RenPyScriptElementTypes.SCENE_STMT_PROP_VALUE -> RenPyScriptSceneStmtPropValueImpl(node)
            RenPyScriptElementTypes.SCENE_STMT_WITH_CLAUSE -> RenPyScriptSceneStmtWithClauseImpl(node)
            RenPyScriptElementTypes.SCENE_STMT_WITH_CLAUSE_KEYWORD -> RenPyScriptSceneStmtWithClauseKeywordImpl(node)
            RenPyScriptElementTypes.SCENE_STMT_WITH_CLAUSE_VALUE -> RenPyScriptSceneStmtWithClauseValueImpl(node)
            RenPyScriptElementTypes.SCENE_STMT_COLON -> RenPyScriptSceneStmtColonImpl(node)
            RenPyScriptElementTypes.SCENE_STMT_ATL -> RenPyScriptSceneStmtATLImpl(node)

            RenPyScriptElementTypes.SHOW_STMT -> RenPyScriptShowStmtImpl(node)
            RenPyScriptElementTypes.SHOW_STMT_KEYWORD -> RenPyScriptShowStmtKeywordImpl(node)
            RenPyScriptElementTypes.SHOW_STMT_IMAGE -> RenPyScriptShowStmtImageImpl(node)
            RenPyScriptElementTypes.SHOW_STMT_IMAGE_PART -> RenPyScriptShowStmtImagePartImpl(node)
            RenPyScriptElementTypes.SHOW_STMT_EXPRESSION -> RenPyScriptShowStmtExpressionImpl(node)
            RenPyScriptElementTypes.SHOW_STMT_EXPRESSION_KEYWORD -> RenPyScriptShowStmtExpressionKeywordImpl(node)
            RenPyScriptElementTypes.SHOW_STMT_EXPRESSION_VALUE -> RenPyScriptShowStmtExpressionValueImpl(node)
            RenPyScriptElementTypes.SHOW_STMT_PROPS_LIST -> RenPyScriptShowStmtPropsListImpl(node)
            RenPyScriptElementTypes.SHOW_STMT_PROP -> RenPyScriptShowStmtPropImpl(node)
            RenPyScriptElementTypes.SHOW_STMT_PROP_KEYWORD -> RenPyScriptShowStmtPropKeywordImpl(node)
            RenPyScriptElementTypes.SHOW_STMT_PROP_VALUE -> RenPyScriptShowStmtPropValueImpl(node)
            RenPyScriptElementTypes.SHOW_STMT_WITH_CLAUSE -> RenPyScriptShowStmtWithClauseImpl(node)
            RenPyScriptElementTypes.SHOW_STMT_WITH_CLAUSE_KEYWORD -> RenPyScriptShowStmtWithClauseKeywordImpl(node)
            RenPyScriptElementTypes.SHOW_STMT_WITH_CLAUSE_VALUE -> RenPyScriptShowStmtWithClauseValueImpl(node)
            RenPyScriptElementTypes.SHOW_STMT_COLON -> RenPyScriptShowStmtColonImpl(node)
            RenPyScriptElementTypes.SHOW_STMT_ATL -> RenPyScriptShowStmtATLImpl(node)

            else -> throw UnsupportedNodeElementTypeException(node)
        }
    }
}
