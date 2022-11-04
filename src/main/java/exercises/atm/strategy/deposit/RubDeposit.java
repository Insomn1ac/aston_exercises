package exercises.atm.strategy.deposit;

import exercises.atm.service.Operation;

import java.util.Scanner;

public class RubDeposit implements DepositStrategy {
    @Override
    public void deposit(String cardNumber) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Operation operation = new Operation();
        System.out.println("\n==== ПОЛОЖИТЬ ДЕНЬГИ НА СЧЁТ ====\n");
        System.out.println("Введите количество ПЯТИДЕСЯТИРУБЛЕВЫХ купюр: ");
        int fifties = Integer.parseInt(scanner.next());
        System.out.println("Введите количество СТОРУБЛЕВЫХ купюр: ");
        int hundreds = Integer.parseInt(scanner.next());
        System.out.println("Введите количество ПЯТИСОТРУБЛЕВЫХ купюр: ");
        int fiveHundreds = Integer.parseInt(scanner.next());
        System.out.println("Введите количество ТЫСЯЧЕРУБЛЕВЫХ купюр: ");
        int thousands = Integer.parseInt(scanner.next());
        operation.deposit(fifties, hundreds, fiveHundreds, thousands, cardNumber);
    }
}
