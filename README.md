CALCULATOR

Initializing the Program

     import java.util.ArrayList;
     import java.util.Scanner;

Here, we create a list called history that will store all the calculations and their results.

        public class Main {
        private static ArrayList<String> history = new ArrayList<>();

This is the main method of the program. We create a Scanner object to read text input from the user, and we print a welcome message.

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Calculator!");
        
The program enters an infinite loop. It keeps asking the user to enter a mathematical expression, and the input is saved in the input variable.

        while (true) {
            System.out.print("Please enter your arithmetic expression: ");
            String input = scanner.nextLine();

If the user types exit, the program will stop running and break out of the loop.

            if (input.equalsIgnoreCase("exit")) {
                break;

If the user types history, the program calls the printHistory() method to show all previous calculations. Then it goes back to asking for a new input.
            
            } else if (input.equalsIgnoreCase("history")) {
                printHistory();
                continue;
            }
Inside the try block:

The program calls evaluateExpression(input) to calculate the result of the expression the user entered.
It then prints the result.
The input and the result are saved in the history list.
If there’s an error (like dividing by zero or an invalid expression), the program will catch the error and print an error message.

            try {
                double result = evaluateExpression(input);
                System.out.println("Result: " + result);
                history.add(input + " = " + result);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
After each calculation, the program asks if the user wants to continue or exit. If the user types n, the program stops.

            System.out.println("If you want to see the history, type 'history'. If not, type 'exit'.");
            System.out.print("Do you want to continue? (y / n) : ");
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("n")) {
                break;
            }
        }
Once the program ends, it prints a thank you message and closes the Scanner object to free up resources.

        System.out.println("Thank you for using the Calculator!");
        scanner.close();
    }
In the evaluateExpression method:

The input is split into parts using split(" "), which separates the string by spaces.
The first part is the first number (num1), the second part is the operator (like +, -, etc.), and the third part (if exists) is the second number (num2).

    private static double evaluateExpression(String input) throws Exception {
        String[] tokens = input.split(" ");
        if (tokens.length < 2) {
            throw new Exception("Invalid expression format");
        }

        double num1 = Double.parseDouble(tokens[0]);
        String operator = tokens[1];
        double num2 = (tokens.length > 2) ? Double.parseDouble(tokens[2]) : 0;
Based on the operator (like +, -, /, etc.), the program performs the corresponding operation.
If the user tries to divide by zero, an exception is thrown.
If the operator is invalid, an exception is also thrown.

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
In the printHistory method:

It checks if there are any records in the history list.
If the list is empty, it prints "No past calculations".
Otherwise, it prints all the records (operations and results) from the history.

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
