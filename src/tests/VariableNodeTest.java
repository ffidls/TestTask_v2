package tests;

import tree.VariableNode;
import java.util.HashMap;

public class VariableNodeTest {

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
        vars.put("x",    5);
        vars.put("y",   10);
        vars.put("zero", 0);

        check("x = 5",                      new VariableNode("x").evaluate(vars) == 5);
        check("y = 10",                     new VariableNode("y").evaluate(vars) == 10);
        check("zero = 0",                   new VariableNode("zero").evaluate(vars) == 0);
        check("unknown variable returns 0", new VariableNode("unknown").evaluate(vars) == 0);
        check("empty map returns 0",        new VariableNode("x").evaluate(new HashMap<String, Integer>()) == 0);

        System.out.println("\nVariableNodeTest: " + passed + " passed, " + failed + " failed");
    }
}
