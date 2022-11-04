package exercises.atm.service;

import exercises.atm.model.CashMachine;
import exercises.atm.util.AtmCurrency;
import exercises.atm.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Operation {
    private Double balance;
    private final Connection connection;
    private final CashMachine cashMachine = new CashMachine();

    public Operation() throws Exception {
        connection = Database.getConnection();
    }

    public Double showBalance(String cardNumber) {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM balance WHERE card_number = ?")) {
            statement.setInt(1, Integer.parseInt(cardNumber));
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    balance = rs.getDouble(2);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return balance;
    }

    public void deposit(int fifties, int hundreds, int fiveHundreds, int thousands, String cardNumber) {
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE balance SET user_balance = user_balance + ? WHERE card_number = ?")) {
            statement.setInt(1, fifties * 50 + hundreds * 100 + fiveHundreds * 500 + thousands * 1000);
            statement.setInt(2, Integer.parseInt(cardNumber));
            statement.executeUpdate();
            cashMachine.setMachineBalance(fifties, hundreds, fiveHundreds, thousands);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void depositInDol(int tens, int twenties, int fifties, int hundreds, String cardNumber) {
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE balance SET user_balance = user_balance + ? WHERE card_number = ?")) {
            statement.setInt(1, tens * 600 + twenties * 1200 + fifties * 3000 + hundreds * 6000);
            statement.setInt(2, Integer.parseInt(cardNumber));
            statement.executeUpdate();
            cashMachine.setDolMachineBalance(tens, twenties, fifties, hundreds);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean withdraw(Double amount, String cardNumber) throws Exception {
        boolean isWithdrawn = false;
        if (cashMachine.getMachineBalance() - amount >= 0 && AtmCurrency.banknotesCounter(amount).size() > 0) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE balance SET user_balance = user_balance - ? WHERE card_number = ?")) {
                statement.setDouble(1, amount);
                statement.setInt(2, Integer.parseInt(cardNumber));
                statement.executeUpdate();
                Integer[] solution = AtmCurrency.banknotesCounter(amount).get(0);
                cashMachine.setMachineBalance(solution[0] * -1,
                        solution[1] * -1,
                        solution[2] * -1,
                        solution[3] * -1);
                isWithdrawn = true;
                System.out.println("Выдано купюр: " + solution[0] + " по 50 рублей, "
                        + solution[1] + " по 100, "
                        + solution[2] + " по 500, "
                        + solution[3] + " по 1000");
                System.out.println("Всего выдано " + amount + " рублей.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("В банкомате не хватает денег для операции снятия. "
                    + "В данный момент там " + cashMachine.getMachineBalance() + " рублей.");
        }
        return isWithdrawn;
    }

    public boolean withdrawInDol(Double amount, String cardNumber) throws Exception {
        boolean isWithdrawn = false;
        if (cashMachine.getMachineBalance() - amount >= 0 && AtmCurrency.banknotesCounter(amount).size() > 0) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE balance SET user_balance = user_balance - ? WHERE card_number = ?")) {
                statement.setDouble(1, amount * 60);
                statement.setInt(2, Integer.parseInt(cardNumber));
                statement.executeUpdate();
                Integer[] solution = AtmCurrency.banknotesCounter(amount).get(0);
                cashMachine.setDolMachineBalance(solution[4] * -1,
                        solution[5] * -1,
                        solution[6] * -1,
                        solution[7] * -1);
                isWithdrawn = true;
                System.out.println("Выдано купюр: " + solution[4] + " по 10 долларов, "
                        + solution[5] + " по 20, "
                        + solution[6] + " по 50, "
                        + solution[7] + " по 100");
                System.out.println("Всего выдано " + amount + " долларов.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("В банкомате не хватает денег для операции снятия.");
        }
        return isWithdrawn;
    }

    public void sendMoneyToOther(Double amount, String otherUserNumber, String cardNumber) {
        String writeOff = "UPDATE balance SET user_balance = user_balance - ? WHERE card_number = ?";
        String enroll = "UPDATE balance SET user_balance = user_balance + ? WHERE card_number = ?";
        try (PreparedStatement withdraw = connection.prepareStatement(writeOff);
             PreparedStatement deposit = connection.prepareStatement(enroll)) {
            connection.setAutoCommit(false);
            withdraw.setDouble(1, amount);
            withdraw.setInt(2, Integer.parseInt(cardNumber));
            withdraw.executeUpdate();
            deposit.setDouble(1, amount);
            deposit.setInt(2, Integer.parseInt(otherUserNumber));
            deposit.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }
    }

    public void insertMoneyByAdmin(int fifties, int hundreds, int fiveHundreds, int thousands,
                                   int dolTens, int dolTwenties, int dolFifties, int dolHundreds) {
        cashMachine.setMachineBalance(fifties, hundreds, fiveHundreds, thousands);
        cashMachine.setDolMachineBalance(dolTens, dolTwenties, dolFifties, dolHundreds);
    }
}