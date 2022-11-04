package exercises.atm.service;

import exercises.atm.model.Account;
import exercises.atm.strategy.deposit.DepositStrategy;
import exercises.atm.strategy.deposit.DolDeposit;
import exercises.atm.strategy.deposit.RubDeposit;
import exercises.atm.strategy.withdraw.DolWithdraw;
import exercises.atm.strategy.withdraw.RubWithdraw;
import exercises.atm.strategy.withdraw.WithdrawStrategy;
import exercises.atm.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Locale;
import java.util.Scanner;

public class AtmService {

    public static int mainMenu(int option, Scanner scanner) {
        while (option == 0) {
            System.out.println("==== ВЫБЕРИТЕ ОПЦИЮ ====");
            System.out.println("1. Создать новый аккаунт");
            System.out.println("2. Войти в уже существующий\n");
            while (option < 1 || option > 2) {
                System.out.println("Выберите опцию: ");
                option = scanner.nextInt();
            }
        }
        return option;
    }

    public static void newAccount(Scanner scanner) {
        System.out.println("\n==== СОЗДАТЬ НОВЫЙ АККАУНТ ====\n");
        System.out.println("Введите имя: ");
        String firstName = scanner.next().trim();
        System.out.println("Введите фамилию: ");
        String lastName = scanner.next().trim();
        Account account = new Account(firstName, lastName);
        account.registration();
    }

    public static void signedIn(Account account, Scanner scanner) throws Exception {
        int counter = 0;
        while (counter < 3) {
            System.out.println("\n==== ВОЙТИ ====\n");
            System.out.println("Введите номер карты: ");
            String cardNumber = scanner.next();
            System.out.println("Введите пин-код: ");
            String pin = scanner.next();
            Operation operation = new Operation();
            try (Connection connection = Database.getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         "SELECT * FROM card WHERE card_number = ? AND pin = ?")) {
                statement.setInt(1, Integer.parseInt(cardNumber));
                statement.setInt(2, Integer.parseInt(pin));
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    try (PreparedStatement userStmt = connection.prepareStatement(
                            "SELECT * FROM account WHERE id = (SELECT account_id FROM card WHERE card_number = ?)")) {
                        userStmt.setInt(1, Integer.parseInt(cardNumber));
                        ResultSet user = userStmt.executeQuery();
                        if (user.next()) {
                            account.setFirstName(user.getString("first_name"));
                        }
                    }
                    int userOption = getUserOption(scanner, account);
                    if (userOption == 1) {
                        System.out.println("Ваш текущий баланс равен: " + operation.showBalance(cardNumber));
                    } else if (userOption == 2) {
                        depositMoney(scanner, cardNumber, operation);
                    } else if (userOption == 3) {
                        withdrawMoney(scanner, cardNumber, operation);
                    } else if (userOption == 4) {
                        sendToOtherUser(scanner, cardNumber, operation);
                    } else {
                        insertMoneyByAdmin(scanner, operation);
                    }
                } else {
                    System.out.println("\nНе удалось войти. Проверьте правильность ввода номера карты/пин-кода.");
                    counter++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static int getUserOption(Scanner scanner, Account account) {
        boolean isAdmin = false;
        System.out.println("\n==== УСПЕШНЫЙ ВХОД, " + account.getFirstName().toUpperCase(Locale.ROOT) + " ====\n");
        System.out.println("1. Текущий баланс");
        System.out.println("2. Положить деньги на счёт");
        System.out.println("3. Снять деньги");
        System.out.println("4. Отправить деньги по номеру карты");
        if (account.getFirstName().equals("admin")) {
            isAdmin = true;
            System.out.println("5. Пополнить счет банкомата");
        }
        int userOption = 0;
        while (userOption < 1 || userOption > 4 && !isAdmin) {
            System.out.println("\nВыберите опцию: ");
            userOption = scanner.nextInt();
        }
        return userOption;
    }

    public static void depositMoney(Scanner scanner, String cardNumber, Operation operation) throws Exception {
        DepositStrategy strategy;
        System.out.println("Выберите валюту пополнения, руб/дол: ");
        System.out.println("1. Пополнение в рублях");
        System.out.println("2. Пополнение в долларах");
        int userOption = 0;
        while (userOption < 1 || userOption > 2) {
            System.out.println("\nВыберите опцию: ");
            userOption = scanner.nextInt();
        }
        if (userOption == 1) {
            strategy = new RubDeposit();
        } else {
            strategy = new DolDeposit();
        }
        strategy.deposit(cardNumber);
        System.out.println("\nТекущий баланс равен: " + operation.showBalance(cardNumber));
    }

    public static void withdrawMoney(Scanner scanner, String cardNumber, Operation operation) throws Exception {
        System.out.println("\n==== СНЯТЬ ДЕНЬГИ ====\n");
        WithdrawStrategy strategy;
        System.out.println("Выберите валюту снятия, руб/дол: ");
        System.out.println("1. Снятие рублей");
        System.out.println("2. Снятие долларов");
        int userOption = 0;
        while (userOption < 1 || userOption > 2) {
            System.out.println("\nВыберите опцию: ");
            userOption = scanner.nextInt();
        }
        double amount = 0;
        while (amount <= 0) {
            System.out.println("Введите сумму: ");
            amount = Double.parseDouble(scanner.next());
        }
        if (userOption == 1) {
            strategy = new RubWithdraw();
        } else {
            strategy = new DolWithdraw();
        }
        boolean isWithdrawn = strategy.withdraw(amount, cardNumber);
        if (isWithdrawn) {
            System.out.println("\nТекущий баланс равен: " + operation.showBalance(cardNumber));
        }
    }

    public static void sendToOtherUser(Scanner scanner, String cardNumber, Operation operation) {
        System.out.println("\n==== ОТПРАВИТЬ ДЕНЬГИ ДРУГОМУ ПОЛЬЗОВАТЕЛЮ ====\n");
        System.out.println("Введите номер карты другого пользователя: ");
        String otherUser = scanner.next();
        double otherUserAmount = 0;
        while (otherUserAmount <= 0) {
            System.out.println("Введите сумму перевода: ");
            otherUserAmount = scanner.nextInt();
        }
        operation.sendMoneyToOther(otherUserAmount, otherUser, cardNumber);
        System.out.println("\nВы отправили " + otherUserAmount + " на карту " + otherUser);
    }

    public static void insertMoneyByAdmin(Scanner scanner, Operation operation) {
        System.out.println("\n==== ПОПОЛНИТЬ БАНКОМАТ ====\n");
        System.out.println("Введите количество купюр по 50 рублей: ");
        int fifties = Integer.parseInt(scanner.next());
        System.out.println("Введите количество купюр по 100 рублей: ");
        int hundreds = Integer.parseInt(scanner.next());
        System.out.println("Введите количество купюр по 500 рублей: ");
        int fiveHundreds = Integer.parseInt(scanner.next());
        System.out.println("Введите количество купюр по 1000 рублей: ");
        int thousands = Integer.parseInt(scanner.next());
        System.out.println("Введите количество купюр по 10 долларов: ");
        int dolTens = Integer.parseInt(scanner.next());
        System.out.println("Введите количество купюр по 20 долларов: ");
        int dolTwenties = Integer.parseInt(scanner.next());
        System.out.println("Введите количество купюр по 50 долларов: ");
        int dolFifties = Integer.parseInt(scanner.next());
        System.out.println("Введите количество купюр по 100 долларов: ");
        int dolHundreds = Integer.parseInt(scanner.next());
        operation.insertMoneyByAdmin(fifties, hundreds, fiveHundreds, thousands,
                dolTens, dolTwenties, dolFifties, dolHundreds);
        int amount = fifties * 50 + hundreds * 100 + fiveHundreds * 500 + thousands * 1000
                + dolTens * 600 + dolTwenties * 120 + dolFifties * 3000 + dolHundreds * 6000;
        System.out.println("\nБанкомат был успешно пополнен на " + amount + " рублей");
    }
}