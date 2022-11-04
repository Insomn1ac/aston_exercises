package exercises.atm.strategy.withdraw;

import exercises.atm.service.Operation;

public class RubWithdraw implements WithdrawStrategy {
    @Override
    public boolean withdraw(double amount, String cardNumber) throws Exception {
        Operation operation = new Operation();
        return operation.withdraw(amount, cardNumber);
    }
}
