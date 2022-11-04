package exercises.atm.model;

import exercises.atm.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CashMachine {
    private double machineBalance;
    private final Connection connection;

    public CashMachine() throws Exception {
        connection = Database.getConnection();
    }

    public double getMachineBalance() {
        try (ResultSet rs = connection.prepareStatement(
                "SELECT sum_money FROM money_inside").executeQuery()) {
            if (rs.next()) {
                machineBalance = rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return machineBalance;
    }

    public void setMachineBalance(int fifties, int hundreds, int fiveHundreds, int thousands) {
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE cash_machine SET fifties = fifties + ?,"
                        + " hundreds = hundreds + ?,"
                        + " five_hundreds = five_hundreds + ?,"
                        + " thousands = thousands + ?"
                        + " WHERE id = 1")) {
            statement.setInt(1, fifties);
            statement.setInt(2, hundreds);
            statement.setInt(3, fiveHundreds);
            statement.setInt(4, thousands);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setDolMachineBalance(int tens, int twenties, int fifties, int hundreds) {
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE cash_machine SET dol_tens = dol_tens + ?,"
                        + " dol_twenties = dol_twenties + ?,"
                        + " dol_fifties = dol_fifties + ?,"
                        + " dol_hundreds = dol_hundreds + ?"
                        + " WHERE id = 1")) {
            statement.setInt(1, tens);
            statement.setInt(2, twenties);
            statement.setInt(3, fifties);
            statement.setInt(4, hundreds);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}