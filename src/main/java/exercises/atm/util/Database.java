package exercises.atm.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    private static final String CONNECTION = "jdbc:postgresql://localhost:5432/bank_atm";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "password";

    public static Connection getConnection() throws Exception {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
    }
}