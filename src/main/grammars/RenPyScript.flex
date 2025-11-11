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

NEW_LINE = \r?\n[ ]*
WS = [ ]+
IDENTIFIER = [a-zA-Z_][a-zA-Z0-9_]*
IMAGE_LABEL_IDENTIFIER = [a-zA-Z0-9_][a-zA-Z0-9_-]*
STRING = \"([^\\\"\n\r]|\\.)*\" | \'([^\\\'\n\r]|\\.)*\'
MULTILINE_DIALOG_STRING = \"([^\\\"]|\\.)*\" | \'([^\\\']|\\.)*\'
COMMENT = \#.*
ONE_LINE_PYTHON_STATEMENT = \$ [^\r\n]*
FLOAT_NUMBER = (0?|[1-9][0-9]*)\.[0-9]+

%%

{NEW_LINE} {
    CharSequence cs = yytext();
    int spaces = 0;
    int indentChars = 0;
    for (int i = 0; i < cs.length(); i++) {
        char c = cs.charAt(i);
        if (c == ' ') {
            spaces++;
            continue;
        }
        if (c == '\r' || c == '\n') {
            indentChars++;
            continue;
        }
    }

    try {
        char charPostMatched = yycharat(spaces + indentChars);
        if (charPostMatched == '\n' || charPostMatched == '\r') return RenPyScriptTokenTypes.NEW_LINE;
    } catch (IndexOutOfBoundsException e) {
        return RenPyScriptTokenTypes.NEW_LINE;
    }

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
{MULTILINE_DIALOG_STRING}       { return RenPyScriptTokenTypes.MULTILINE_DIALOG_STRING; }
"label"        { return RenPyScriptTokenTypes.LABEL_KEYWORD; }
"menu"         { return RenPyScriptTokenTypes.MENU_KEYWORD; }
"jump"         { return RenPyScriptTokenTypes.JUMP_KEYWORD; }
"scene" { return RenPyScriptTokenTypes.SCENE_KEYWORD; }
"with" { return RenPyScriptTokenTypes.WITH_KEYWORD; }
"show" { return RenPyScriptTokenTypes.SHOW_KEYWORD; }
"hide" { return RenPyScriptTokenTypes.HIDE_KEYWORD; }
"expression" { return RenPyScriptTokenTypes.EXPRESSION_KEYWORD; }
"as" { return RenPyScriptTokenTypes.AS_KEYWORD; }
"at" { return RenPyScriptTokenTypes.AT_KEYWORD; }
"behind" { return RenPyScriptTokenTypes.BEHIND_KEYWORD; }
"onlayer" { return RenPyScriptTokenTypes.ONLAYER_KEYWORD; }
"zorder" { return RenPyScriptTokenTypes.ZORDER_KEYWORD; }
"pass" { return RenPyScriptTokenTypes.PASS_KEYWORD; }
"pause" { return RenPyScriptTokenTypes.PAUSE_KEYWORD; }
"play" { return RenPyScriptTokenTypes.PLAY_KEYWORD; }
"stop" { return RenPyScriptTokenTypes.STOP_KEYWORD; }
"queue" { return RenPyScriptTokenTypes.QUEUE_KEYWORD; }
"return" { return RenPyScriptTokenTypes.RETURN_KEYWORD; }
{ONE_LINE_PYTHON_STATEMENT} { return RenPyScriptTokenTypes.ONE_LINE_PYTHON_STATEMENT; }
"None" { return RenPyScriptTokenTypes.NONE; }
//"=" { return RenPyScriptTypes.EQ; }
{FLOAT_NUMBER} { return RenPyScriptTokenTypes.FLOAT_NUMBER; }
"." { return RenPyScriptTokenTypes.DOT; }
"(" { return RenPyScriptTokenTypes.PARENTHESES_OPEN; }
")" { return RenPyScriptTokenTypes.PARENTHESES_CLOSE; }
"[" { return RenPyScriptTokenTypes.SQUARE_BRACKETS_OPEN; }
"]" { return RenPyScriptTokenTypes.SQUARE_BRACKETS_CLOSE; }
"," { return RenPyScriptTokenTypes.COMMA; }
{IDENTIFIER}        { return RenPyScriptTokenTypes.IDENTIFIER; }
{IMAGE_LABEL_IDENTIFIER} {
    CharSequence cs = yytext();
    boolean allDigits = true;

    for (int i = 0; i < cs.length(); i++) {
        if (!Character.isDigit(cs.charAt(i))) {
            allDigits = false;
            break;
        }
    }

    if (allDigits) {
        return RenPyScriptTokenTypes.PLAIN_NUMBER;
    }

    return RenPyScriptTokenTypes.IMAGE_LABEL_IDENTIFIER;
}
":"            { return RenPyScriptTokenTypes.COLON; }

.            { return TokenType.BAD_CHARACTER; }
