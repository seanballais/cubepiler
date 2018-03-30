package syntaxchecker;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import syntaxchecker.UnitChecker;

public class UnitCheckerTest {
    private UnitChecker unitChecker;

    @BeforeClass public static void setup() {
        unitChecker = new UnitChecker();
    }

    @Test public void testVariableDeclarationChecker() {
        System.out.println("Testing Variable Declaration Checker...");

        assertEquals(true, unitChecker.isValidVarDeclaration("var x = 1"));
        assertEquals(true, unitChecker.isValidVarDeclaration("var x1 = 1"));
        assertEquals(false, unitChecker.isValidVarDeclaration("var ! = 1"));
        assertEquals(false, unitChecker.isValidVarDeclaration("var 1x = 1"));
        assertEquals(false, unitChecker.isValidVarDeclaration("var a"));
        assertEquals(false, unitChecker.isValidVarDeclaration("var a = "));

        assertEquals(true, unitChecker.isValidExpression("var x = 1 + 1"));
        assertEquals(true, unitChecker.isValidExpression("var x = 1+1"));
        assertEquals(true, unitChecker.isValidExpression("var x = a + 1"));
        assertEquals(true, unitChecker.isValidExpression("var x = a - 1"));
        assertEquals(true, unitChecker.isValidExpression("var x = a * 1"));
        assertEquals(true, unitChecker.isValidExpression("var x = a / 1"));
        assertEquals(true, unitChecker.isValidExpression("var x = a & 1"));
        assertEquals(true, unitChecker.isValidExpression("var x = a |  1"));
        assertEquals(true, unitChecker.isValidExpression("var x = (a / a) + (b + 1 * (c + 2))"));
        assertEquals(true, unitChecker.isValidExpression("var x = !a"));

        assertEquals(false, unitChecker.isValidExpression("var x = a++1"));
        assertEquals(false, unitChecker.isValidExpression("var x = a--1"));
        assertEquals(false, unitChecker.isValidExpression("var x = a**1"));
        assertEquals(false, unitChecker.isValidExpression("var x = a//1"));
        assertEquals(false, unitChecker.isValidExpression("var x = a!1"))
        assertEquals(false, unitChecker.isValidExpression("var x = &a + 1")):
        assertEquals(false, unitChecker.isValidExpression("var x = (a + 1")):
    }

    @Test public void testFunctionChecker() {
        System.out.println("Testing Function Checker...");

        assertEquals(false, )
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
        assertEquals(true, unitChecker.isValidExpression("(a / a) + (b + 1 * (c + 2))"));
        assertEquals(true, unitChecker.isValidExpression("!a"));

        assertEquals(false, unitChecker.isValidExpression("a++1"));
        assertEquals(false, unitChecker.isValidExpression("a--1"));
        assertEquals(false, unitChecker.isValidExpression("a**1"));
        assertEquals(false, unitChecker.isValidExpression("a//1"));
        assertEquals(false, unitChecker.isValidExpression("a!1"))
        assertEquals(false, unitChecker.isValidExpression("&a + 1")):
        assertEquals(false, unitChecker.isValidExpression("(a + 1")):
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