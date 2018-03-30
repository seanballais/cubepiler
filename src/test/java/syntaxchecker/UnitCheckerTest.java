package syntaxchecker;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import syntaxchecker.UnitChecker;

public class UnitCheckerTest {
    private UnitChecker unitChecker;

    @BeforeClass public static void setup() {
        unitChecker = new UnitChecker();
        unitChecker.addVariable("used_var", "One");
        unitChecker.addVariable("X", "true");
        unitChecker.addFunction("some_func");
        unitChecker.addFunction("func", "arg", "arg2");
    }

    @Test public void testVariableGetSet() {
        System.out.println("Testing Variable Get Set Checks..."):

        assertEquals(true, unitChecker.hasVariable("X"));
        assertEquals("true", unitChecker.getVariable("X"));
        
        unitChecker.setVariable("X", "false");
        assertEquals("false", unitChecker.getVariable("X"));
    }

    @Test public void testVariableDeclarationChecker() {
        System.out.println("Testing Variable Declaration Checker...");

        assertEquals(true, unitChecker.isValidVarDeclaration("var x = 1"));
        assertEquals(false, unitChecker.isValidVarDeclaration("var X = 1"));
        assertEquals(false, unitChecker.isValidVarDeclaration("var used_var = q"));
    }

    @Test public void testFunctionChecker() {
        System.out.println("Testing Function Checker...");

        assertEquals(true, unitChecker.isValidFunction("fn x()\nend"));
        assertEquals(true, unitChecker.isValidFunction("fn x(arg)\nend"));
        assertEquals(true, unitChecker.isValidFunction("fn x(arg, arg2)\nend"));
        assertEquals(true, unitChecker.isValidFunction("fn x()\nsomeexpression\nend"));
        assertEquals(false, unitChecker.isValidFunction("fn X()\nend"));
        assertEquals(false, unitChecker.isValidFunction("fn X\nend"));
        assertEquals(false, unitChecker.isValidFunction("fn x(arg,)\nend"));
        assertEquals(false, unitChecker.isValidFunction("fn X()\nsomeexpression"));
        assertEquals(false, unitChecker.isValidFunction("fn x()\nsomeexpression"));
        assertEquals(false, unitChecker.isValidFunction("fn some_func()\nexp\nend"));
    }

    @Test public void testAssignmentChecker() {
        System.out.println("Testing Assignment Checker...");

        assertEquals(true, unitChecker.isValidVarDeclaration("var x = 1"));
        assertEquals(true, unitChecker.isValidVarDeclaration("var x1 = 1"));
        assertEquals(false, unitChecker.isValidVarDeclaration("var ! = 1"));
        assertEquals(false, unitChecker.isValidVarDeclaration("var 1x = 1"));
        assertEquals(false, unitChecker.isValidVarDeclaration("var a"));
        assertEquals(false, unitChecker.isValidVarDeclaration("var a = "));
    }

    @Test public void testExpressionChecker() {
        System.out.println("Testing Expression Checker...");

        assertEquals(true, unitChecker.isValidExpression("1 + 1"));
        assertEquals(true, unitChecker.isValidExpression("1+1"));
        assertEquals(true, unitChecker.isValidExpression("a + 1"));
        assertEquals(true, unitChecker.isValidExpression("a - 1"));
        assertEquals(true, unitChecker.isValidExpression("a * 1"));
        assertEquals(true, unitChecker.isValidExpression("a / 1"));
        assertEquals(true, unitChecker.isValidExpression("a & 1"));
        assertEquals(true, unitChecker.isValidExpression("a |  1"));
        assertEquals(true, unitChecker.isValidExpression("a%b"));
        assertEquals(true, unitChecker.isValidExpression("a>b"));
        assertEquals(true, unitChecker.isValidExpression("a<b"));
        assertEquals(true, unitChecker.isValidExpression("a>=b"));
        assertEquals(true, unitChecker.isValidExpression("a<=b"));
        assertEquals(true, unitChecker.isValidExpression("(a / a) + (b + 1 * (c + 2))"));
        assertEquals(true, unitChecker.isValidExpression("!a"));
        assertEquals(true, unitChecker.isValidExpression("a > b & b == 1"));

        assertEquals(false, unitChecker.isValidExpression("a++1"));
        assertEquals(false, unitChecker.isValidExpression("a--1"));
        assertEquals(false, unitChecker.isValidExpression("a**1"));
        assertEquals(false, unitChecker.isValidExpression("a//1"));
        assertEquals(false, unitChecker.isValidExpression("a!1"));
        assertEquals(false, unitChecker.isValidExpression("&a + 1"));
        assertEquals(false, unitChecker.isValidExpression("(a + 1"));
    }

    @Test public void testConditionalChecker() {
        System.out.println("Testing Conditional Checker...");

        assertEquals(true, unitChecker.isValidConditional("if some > condition do\nexpression\nend\n"));
        assertEquals(true, unitChecker.isValidConditional("if s > c do\nexp\nelse if c do\nexp\nend"));
        assertEquals(true, unitChecker.isValidConditional("if s > c do\nexp\nelse if c do\nexp\nelse\nexp\nend"));
        assertEquals(true, unitChecker.isValidConditional("if s > c do\nexp\nif c do\nexp\nend\nexp\nend"));
        assertEquals(false, unitChecker.isValidConditional("if s > c do\nexp\nif c do\nexp\nexp\nend"));
        assertEquals(false, unitChecker.isValidConditional("if some > condition do\nexpression\n"));
        assertEquals(false, unitChecker.isValidConditional("if do\nexpression\n"));
    }

    @Test public void testWhileLoopChecker() {
        System.out.println("Testing While Loop Checker...");

        assertEquals(true, unitChecker.isValidWhile("while cond do\nexp\nend"));
        assertEquals(false, unitChecker.isValidWhile("while cond do\nexp"));
    }

    @Test public void testForEachChecker() {
        System.out.println("Testing For Each Checker...");

        assertEquals(true, unitChecker.isValidForEach("for each e in arr\nexp\nend"));
        assertEquals(false, unitChecker.isValidForEach("for each in arr\nexp\nend"));
        assertEquals(false, unitChecker.isValidForEach("for each x arr\nexp\nend"));
        assertEquals(false, unitChecker.isValidForEach("for each x in\nexp\nend"));
        assertEquals(false, unitChecker.isValidForEach("for each x in arr\nexp"));
    }

    @Test public void testDoWhileChecker() {
        System.out.println("Testing For Do While Checker...");

        assertEquals(true, unitChecker.isValidDoWhile("do\nexp\nwhile cond"));
        assertEquals(true, unitChecker.isValidDoWhile("dod\nwhile cond"));
        assertEquals(false, unitChecker.isValidDoWhile("do\nexp\nwhile"));
        assertEquals(false, unitChecker.isValidDoWhile("do\nexp\n"));
        assertEquals(false, unitChecker.isValidDoWhile("do\nexp\ncond"));
    }

    @Test public void testFuncCallChecker() {
        System.out.println("Testing Func Call Checker...");

        assertEquals(true, unitChecker.isValidFuncCall("some_func()"));
        assertEquals(true, unitChecker.isValidFuncCall("func(arg, lol)"));
        assertEquals(false, unitChecker.isValidFuncCall("func(arg,)"));
        assertEquals(false, unitChecker.isValidFuncCall("func()"));
        assertEquals(false, unitChecker.isValidFuncCall("func)"));
        assertEquals(false, unitChecker.isValidFuncCall("func("));
        assertEquals(false, unitChecker.isValidFuncCall("some()"));
    }

    @Test public void testCommentChecker() {
        System.out.println("Testing Comment Checker...");

        // Check single line comments.
        assertEquals(true, unitChecker.isValidComment("#This is a valid comment."));
        assertEquals(true, unitChecker.isValidComment("# This is a valid comment."));
        assertEquals(true, unitChecker.isValidComment("# This is still a valid comment. #"));
        assertEquals(true, unitChecker.isValidComment("## This is still a valid #comment."));
        assertEquals(true, unitChecker.isValidComment("######"));
        assertEquals(true, unitChecker.isValidComment("#####"));
        assertEquals(true, unitChecker.isValidComment("### Hello"));

        // Check multi-line comments.
        assertEquals(true, unitChecker.isValidComment("### This\nis\na\nvalid\ncomment.###"));
        assertEquals(true, unitChecker.isValidComment("### This\nis\na\nvalid\ncomment.\n###"));
        assertEquals(false, unitChecker.isValidComment("### a comment\nanothercomment"));
    }
}