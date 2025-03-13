import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<String> history = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Calculator!");

        while (true) {
            System.out.print("Please enter your arithmetic expression: ");
            String input = scanner.nextLine();

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

            System.out.println("If you want to see the history, type 'history'. If not, type 'exit'.");
            System.out.print("Do you want to continue? (y / n) : ");
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("n")) {
                break;
            }
        }

        System.out.println("Thank you for using this Calculator!");
        scanner.close();
    }

    private static double evaluateExpression(String input) throws Exception {
        String[] tokens = input.split(" ");
        if (tokens.length < 2) {
            throw new Exception("Invalid expression format");
        }

        double num1 = Double.parseDouble(tokens[0]);
        String operator = tokens[1];
        double num2 = (tokens.length > 2) ? Double.parseDouble(tokens[2]) : 0;

        return switch (operator) {
            case "+" -> num1 + num2;
            case "-" -> num1 - num2;
            case "*" -> num1 * num2;
            case "/" -> {
                if (num2 == 0) throw new Exception("Cannot divide by zero");
                yield num1 / num2;
            }
            case "%" -> num1 % num2;
            case "^" -> Math.pow(num1, num2);
            case "sqrt" -> Math.sqrt(num1);
            case "abs" -> Math.abs(num1);
            case "round" -> (double) Math.round(num1);
            default -> throw new Exception("Invalid operator");
        };
    }

    private static void printHistory() {
        if (history.isEmpty()) {
            System.out.println("No past calculations.");
        } else {
            for (String record : history) {
                System.out.println(record);
            }
        }
    }
}
