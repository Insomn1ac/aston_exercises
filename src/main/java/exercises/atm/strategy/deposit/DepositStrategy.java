package exercises.atm.strategy.deposit;

public interface DepositStrategy {
    void deposit(String cardNumber) throws Exception;
}
