package exercises.atm.strategy.withdraw;

import exercises.atm.service.Operation;

public class DolWithdraw implements WithdrawStrategy {
    @Override
    public boolean withdraw(double amount, String cardNumber) throws Exception {
        Operation operation = new Operation();
        return operation.withdrawInDol(amount, cardNumber);
    }
}
