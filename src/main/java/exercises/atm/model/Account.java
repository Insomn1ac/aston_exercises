package exercises.atm.model;

import exercises.atm.util.Database;

import java.sql.*;
import java.util.Objects;

public class Account {
    private String firstName;
    private String lastName;

    public Account() {
    }

    public Account(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void registration() {
        String sql = "INSERT INTO account (first_name, last_name) VALUES (?, ?)";
        try (Connection connection = Database.getConnection();
             PreparedStatement accountTable = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            accountTable.setString(1, firstName);
            accountTable.setString(2, lastName);
            accountTable.execute();
            int accountId = 0;
            try (ResultSet generatedKeys = accountTable.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    accountId = generatedKeys.getInt(1);
                }
            }
            String cardNumber = generateCardNumber();
            String pin = generatePin();
            PreparedStatement cardTable = connection.prepareStatement("INSERT INTO card VALUES (?, ?, ?)");
            cardTable.setInt(1, accountId);
            cardTable.setInt(2, Integer.parseInt(cardNumber));
            cardTable.setInt(3, Integer.parseInt(pin));
            cardTable.executeUpdate();
            PreparedStatement balanceTable = connection.prepareStatement("INSERT INTO balance VALUES (?, '0')");
            balanceTable.setInt(1, Integer.parseInt(cardNumber));
            balanceTable.executeUpdate();
            System.out.println("Аккаунт был успешно создан. Ваш номер карты: " + cardNumber);
            System.out.println("Ваш пин-код: " + pin);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String generateCardNumber() {
        String numberSet = "1234567890";
        char[] cardNumber = new char[8];
        for (int i = 0; i < 8; i++) {
            int rand = (int) (Math.random() * numberSet.length());
            cardNumber[i] = numberSet.charAt(rand);
        }
        return new String(cardNumber);
    }

    public String generatePin() {
        String pinSet = "1234567890";
        char[] cardPin = new char[4];
        for (int i = 0; i < 4; i++) {
            int rand = (int) (Math.random() * pinSet.length());
            cardPin[i] = pinSet.charAt(rand);
        }
        return new String(cardPin);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account account = (Account) o;
        return Objects.equals(firstName, account.firstName)
                && Objects.equals(lastName, account.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    @Override
    public String toString() {
        return "first name='" + firstName + '\''
                + ", last name='" + lastName + '\'';
    }
}