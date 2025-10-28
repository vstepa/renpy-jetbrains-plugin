package ee.vstepa.jetbrains.plugins.renpy.lang.script.lexer;

import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import ee.vstepa.jetbrains.plugins.renpy.lang.script.psi.token.RenPyScriptTokenTypes;

%%

%class RenPyScriptGeneratedLexer
%extends RenPyScriptLexerBase
%function advance
%type IElementType
%unicode

//%{
//  private Stack<Integer> indents = new Stack<>();
//  private int pendingDedents = 0;
//%}

NEW_LINE = \r?\n
WS = [ ]+
IDENTIFIER = [a-zA-Z_][a-zA-Z0-9_-]*
STRING = \"([^\\\"\n\r]|\\.)*\" | \'([^\\\'\n\r]|\\.)*\'
COMMENT = \#.*
//PLAIN_NUMBER = [0-9]+
QUICK_PYTHON_STATEMENT = \$ [^\r\n]*

%%

{NEW_LINE} {
    int spaces = 0;
    try {
        while (yycharat(++spaces) == ' ');
    } catch (IndexOutOfBoundsException e) {
        return RenPyScriptTokenTypes.NEW_LINE;
    }
    char postSpacesChar = yycharat(spaces);
    if (postSpacesChar == '\n' || postSpacesChar == '\r') {
        return RenPyScriptTokenTypes.NEW_LINE;
    }
    spaces--;
    if (spaces > 0) yybegin(YYINITIAL);

    int prevIndent = getIndents().isEmpty() ? 0 : getIndents().peek();
    if (spaces > prevIndent) {
        getIndents().push(spaces);
        return RenPyScriptTokenTypes.INDENT;
    } else if (spaces < prevIndent) {
        while (!getIndents().isEmpty() && getIndents().peek() > spaces) {
            getIndents().pop();
            setPendingDedents(getPendingDedents() + 1);
        }
        if (getPendingDedents() > 0) {
            setPendingDedents(getPendingDedents() - 1);
            return RenPyScriptTokenTypes.DEDENT;
        }
    }
    return RenPyScriptTokenTypes.NEW_LINE;
}

{WS}          { return TokenType.WHITE_SPACE; }
{COMMENT}      { return RenPyScriptTokenTypes.COMMENT; }
{STRING}       { return RenPyScriptTokenTypes.STRING; }
"label"        { return RenPyScriptTokenTypes.LABEL_KEYWORD; }
"menu"         { return RenPyScriptTokenTypes.MENU_KEYWORD; }
"jump"         { return RenPyScriptTokenTypes.JUMP_KEYWORD; }
"scene" { return RenPyScriptTokenTypes.SCENE_KEYWORD; }
"with" { return RenPyScriptTokenTypes.WITH_KEYWORD; }
"show" { return RenPyScriptTokenTypes.SHOW_KEYWORD; }
"hide" { return RenPyScriptTokenTypes.HIDE_KEYWORD; }
"expression" { return RenPyScriptTokenTypes.EXPRESSION_KEYWORD; }
//"play" { return RenPyScriptTypes.PLAY_KEYWORD; }
//"sound" { return RenPyScriptTypes.SOUND_KEYWORD; }
//"$"    { return RenPyScriptTypes.QUICK_PYTHON_KEYWORD; }
{QUICK_PYTHON_STATEMENT} { return RenPyScriptTokenTypes.QUICK_PYTHON_STATEMENT; }
//"=" { return RenPyScriptTypes.EQ; }
//"." { return RenPyScriptTypes.DOT; }
//{PLAIN_NUMBER} { return RenPyScriptTypes.NUMBER; }
{IDENTIFIER}        { return RenPyScriptTokenTypes.IDENTIFIER; }
":"            { return RenPyScriptTokenTypes.COLON; }

.            { return TokenType.BAD_CHARACTER; }
