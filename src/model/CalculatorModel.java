package model;

import java.util.Stack;

public class CalculatorModel {

    public double evaluateExpression(String expression) throws Exception {
        return evaluate(toPostfix(expression));
    }

    // Преобразование инфиксного выражения в постфиксное (обратная польская нотация)
    private String toPostfix(String infix) throws Exception {
        StringBuilder output = new StringBuilder();
        Stack<String> stack = new Stack<>();

        String[] tokens = infix.replaceAll("//", " // ")
                .replaceAll("([+\\-*/^()])", " $1 ")
                .trim()
                .split("\\s+");

        for (String token : tokens) {
            if (isNumber(token)) {
                output.append(token).append(" ");
            } else if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    output.append(stack.pop()).append(" ");
                }
                if (stack.isEmpty()) throw new Exception("Mismatched parentheses");
                stack.pop(); // Remove '('
            } else {
                while (!stack.isEmpty() && precedence(token) <= precedence(stack.peek())) {
                    output.append(stack.pop()).append(" ");
                }
                stack.push(token);
            }
        }

        while (!stack.isEmpty()) {
            if (stack.peek().equals("(")) throw new Exception("Mismatched parentheses");
            output.append(stack.pop()).append(" ");
        }

        return output.toString().trim();
    }

    private double evaluate(String postfix) throws Exception {
        Stack<Double> stack = new Stack<>();
        String[] tokens = postfix.split("\\s+");

        for (String token : tokens) {
            if (isNumber(token)) {
                stack.push(Double.parseDouble(token));
            } else {
                double b = stack.pop();
                double a = stack.pop();
                switch (token) {
                    case "+": stack.push(a + b); break;
                    case "-": stack.push(a - b); break;
                    case "*": stack.push(a * b); break;
                    case "/": stack.push(a / b); break;
                    case "//": stack.push((double)((int)(a / b))); break;
                    case "^": stack.push(Math.pow(a, b)); break;
                    default: throw new Exception("Unknown operator: " + token);
                }
            }
        }

        return stack.pop();
    }

    private boolean isNumber(String s) {
        return s.matches("-?\\d+(\\.\\d+)?");
    }

    private int precedence(String op) {
        switch (op) {
            case "^": return 4;
            case "*": case "/": case "//": return 3;
            case "+": case "-": return 2;
            default: return 1;
        }
    }
}
