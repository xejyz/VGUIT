package view;

import java.util.Scanner;

public class CalculatorView {

    private final Scanner scanner = new Scanner(System.in);

    public String getUserInput() {
        System.out.print("Введите уравнение: ");
        return scanner.nextLine();
    }

    public void displayResult(double result) {
        System.out.println("Результат: " + result);
    }

    public void displayError(String message) {
        System.out.println("Ошибка: " + message);
    }
}
