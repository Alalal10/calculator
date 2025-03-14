import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    private static final ArrayList<String> history = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Calculator!");

        while (true) {
            System.out.print("\nEnter an arithmetic expression (or type 'history' / 'exit'): ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                break;
            } else if (input.equalsIgnoreCase("history")) {
                printHistory();
                continue;
            }

            try {
                double result = evaluateExpression(input);
                System.out.println("Result: " + result);
                history.add(input + " = " + result);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.print("Do you want to continue? (y/n): ");
            String choice = scanner.nextLine().trim();
            if (choice.equalsIgnoreCase("n")) {
                break;
            }
        }

        System.out.println("Thank you for using the Calculator!");
        scanner.close();
    }

    private static double evaluateExpression(String input) throws Exception {
        input = input.replaceAll("\\s+", ""); 
        return evaluatePostfix(infixToPostfix(input));
    }

    private static String infixToPostfix(String expression) throws Exception {
        StringBuilder output = new StringBuilder();
        Stack<String> operators = new Stack<>();
        char[] tokens = expression.toCharArray();

        for (int i = 0; i < tokens.length; i++) {
            char c = tokens[i];

            if (Character.isDigit(c) || c == '.') {
                while (i < tokens.length && (Character.isDigit(tokens[i]) || tokens[i] == '.')) {
                    output.append(tokens[i++]);
                }
                output.append(" ");
                i--;
            } else if (c == '(') {
                operators.push(String.valueOf(c));
            } else if (c == ')') {
                while (!operators.isEmpty() && !operators.peek().equals("(")) {
                    output.append(operators.pop()).append(" ");
                }
                if (operators.isEmpty()) throw new Exception("Mismatched parentheses");
                operators.pop(); 
            } else if (isFunctionStart(c, tokens, i)) {
                String function = getFunctionName(tokens, i);
                operators.push(function);
                i += function.length() - 1; 
            } else if (isOperator(c)) {
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(String.valueOf(c))) {
                    output.append(operators.pop()).append(" ");
                }
                operators.push(String.valueOf(c));
            } else {
                throw new Exception("Invalid character in expression");
            }
        }

        while (!operators.isEmpty()) {
            output.append(operators.pop()).append(" ");
        }

        return output.toString();
    }

    private static boolean isFunctionStart(char c, char[] tokens, int i) {
        return Character.isAlphabetic(c) && (i == 0 || !Character.isDigit(tokens[i-1]));
    }

    private static String getFunctionName(char[] tokens, int index) {
        StringBuilder function = new StringBuilder();
        while (index < tokens.length && Character.isAlphabetic(tokens[index])) {
            function.append(tokens[index]);
            index++;
        }
        return function.toString();
    }

    private static double evaluatePostfix(String postfix) throws Exception {
        Stack<Double> stack = new Stack<>();
        String[] tokens = postfix.split(" ");

        for (String token : tokens) {
            if (token.isEmpty()) continue;

            if (token.matches("-?\\d+(\\.\\d+)?")) { 
                stack.push(Double.parseDouble(token));
            } else if (token.equals("^")) { 
                double b = stack.pop();
                double a = stack.pop();
                stack.push(Math.pow(a, b));
            } else if (token.equals("sqrt")) { 
                double a = stack.pop();
                stack.push(Math.sqrt(a));
            } else if (token.equals("sin")) { 
                double a = stack.pop();
                stack.push(Math.sin(Math.toRadians(a)));
            } else if (token.equals("cos")) { 
                double a = stack.pop();
                stack.push(Math.cos(Math.toRadians(a)));
            } else if (token.equals("tan")) { 
                double a = stack.pop();
                stack.push(Math.tan(Math.toRadians(a)));
            } else if (token.equals("log")) { 
                double a = stack.pop();
                stack.push(Math.log10(a));
            } else { 
                double b = stack.pop();
                double a = stack.pop();
                switch (token) {
                    case "+" -> stack.push(a + b);
                    case "-" -> stack.push(a - b);
                    case "*" -> stack.push(a * b);
                    case "/" -> {
                        if (b == 0) throw new Exception("Cannot divide by zero");
                        stack.push(a / b);
                    }
                    case "%" -> stack.push(a % b);
                }
            }
        }

        return stack.pop();
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '^';
    }

    private static int precedence(String op) {
        return switch (op) {
            case "+", "-" -> 1;
            case "*", "/", "%" -> 2;
            case "^" -> 3;
            default -> -1;
        };
    }

    private static void printHistory() {
        if (history.isEmpty()) {
            System.out.println("No past calculations.");
        } else {
            System.out.println("Calculation History:");
            for (String record : history) {
                System.out.println(record);
            }
        }
    }
}
