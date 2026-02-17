package tests;

import tree.IntegerNode;
import java.util.HashMap;

public class IntegerNodeTest {

    static int passed = 0;
    static int failed = 0;

    static void check(String name, boolean condition) {
        if (condition) {
            System.out.println("PASS: " + name);
            passed++;
        } else {
            System.out.println("FAIL: " + name);
            failed++;
        }
    }

    public static void main(String[] args) {

        HashMap<String, Integer> vars = new HashMap<String, Integer>();

        check("positive number 42",     new IntegerNode(42).evaluate(vars) == 42);
        check("zero",                   new IntegerNode(0).evaluate(vars) == 0);
        check("negative number -7",     new IntegerNode(-7).evaluate(vars) == -7);
        check("variables are ignored",  new IntegerNode(99).evaluate(vars) == 99);
        check("large number 1000000",   new IntegerNode(1000000).evaluate(vars) == 1000000);

        System.out.println("\nIntegerNodeTest: " + passed + " passed, " + failed + " failed");
    }
}
