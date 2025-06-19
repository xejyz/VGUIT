package controller;

import model.CalculatorModel;
import view.CalculatorView;

public class CalculatorController {

    private final CalculatorModel model;
    private final CalculatorView view;

    public CalculatorController(CalculatorModel model, CalculatorView view) {
        this.model = model;
        this.view = view;
    }

    public void run() {
        String input = view.getUserInput();
        try {
            if (!validateInput(input)) {
                view.displayError("Некорректное уравнение.");
                return;
            }

            double result = model.evaluateExpression(input);
            view.displayResult(result);
        } catch (Exception e) {
            view.displayError(e.getMessage());
        }
    }

    private boolean validateInput(String input) {
        input = input.trim();
        return input.matches("^-?\\d.*\\d$") && input.split("[+\\-*/^()]").length <= 100;
    }
}
