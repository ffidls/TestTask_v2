import lexer.Lexer;
import lexer.Token;
import parser.Parser;
import tree.Node;

import java.util.*;

public class Main {
    private static Node root = null;
    private static Map<String, Integer> variables = new HashMap<>();
    private static final Random random = new Random();


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter expression (example: (12 + x) * 23 + y):");
        String expression = scanner.nextLine().trim();

        try {
            Lexer lexer = new Lexer(expression);
            List<Token> tokens = lexer.tokenize();
            Parser parser = new Parser(tokens);
            root = parser.parse();

            for (Token t : tokens) {
                if (t.type == Token.Type.VARIABLE && !variables.containsKey(t.value)) {
                    variables.put(t.value, random.nextInt(65536));
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            return;
        }

        System.out.println("\nRandom init + first calculation:");
        printCurrentState();

        System.out.println("\nCommands:");
        System.out.println("  print          - print AST");
        System.out.println("  calc           - calculate expression");
        System.out.println("  vars           - show variables");
        System.out.println("  x = 20         - set variable value");
        System.out.println("  exit           - quit");

        while (true) {
            System.out.print("\n> ");
            String line = scanner.nextLine().trim();

            if (line.equalsIgnoreCase("exit")) {
                break;
            }

            if (line.isEmpty()) continue;

            if (line.equalsIgnoreCase("print")) {
                System.out.println("Abstract Syntax Tree:");
                root.print("");
                continue;
            }

            if (line.equalsIgnoreCase("calc")) {
                printCurrentState();
                continue;
            }

            if (line.equalsIgnoreCase("vars")) {
                System.out.println("Current variables: " + variables);
                continue;
            }

            if (line.contains("=")) {
                handleAssignment(line);
                continue;
            }

            System.out.println("ERROR: unknown command");
        }
    }

    private static void handleAssignment(String line) {
        String[] parts = line.split("=");
        if (parts.length != 2) {
            System.out.println("ERROR: use format: x = 20");
            return;
        }

        String name = parts[0].trim();
        String valueStr = parts[1].trim();

        if (!name.matches("[A-Za-z][A-Za-z0-9]*")) {
            System.out.println("ERROR: invalid variable name");
            return;
        }

        try {
            int value = Integer.parseInt(valueStr);
            if (value < -32768 || value > 65535) {
                System.out.println("WARNING: value exceeds 16-bit range");
            }
            variables.put(name, value);
            System.out.println(name + " = " + value);
        } catch (NumberFormatException e) {
            System.out.println("ERROR: invalid integer value");
        }
    }

    private static void printCurrentState() {
        try {
            System.out.println("Variables: " + variables);
            int result = root.evaluate(variables);
            System.out.println("Result: " + result);
        } catch (Exception e) {
            System.out.println("ERROR during calculation: " + e.getMessage());
        }
    }
}