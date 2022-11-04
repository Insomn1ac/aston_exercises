package exercises.atm.strategy.deposit;

import exercises.atm.service.Operation;

import java.util.Scanner;

public class DolDeposit implements DepositStrategy {
    @Override
    public void deposit(String cardNumber) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Operation operation = new Operation();
        System.out.println("\n==== ПОЛОЖИТЬ ДЕНЬГИ НА СЧЁТ ====\n");
        System.out.println("Введите количество ДЕСЯТИДОЛЛАРОВЫХ купюр: ");
        int tens = Integer.parseInt(scanner.next());
        System.out.println("Введите количество ДВАДЦАТИДОЛЛАРОВЫХ купюр: ");
        int twenties = Integer.parseInt(scanner.next());
        System.out.println("Введите количество ПЯТИДЕСЯТИДОЛЛАРОВЫХ купюр: ");
        int fifties = Integer.parseInt(scanner.next());
        System.out.println("Введите количество СТОДОЛЛАРОВЫХ купюр: ");
        int hundreds = Integer.parseInt(scanner.next());
        operation.depositInDol(tens, twenties, fifties, hundreds, cardNumber);
    }
}
