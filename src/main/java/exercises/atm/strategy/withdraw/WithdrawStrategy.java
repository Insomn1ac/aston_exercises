package exercises.atm.strategy.withdraw;

public interface WithdrawStrategy {
    boolean withdraw(double amount, String cardNumber) throws Exception;
}
