public class ChequeAccount extends Account {
    private double limitPerCheque;
    //constructor for cheque account
    public ChequeAccount(int accountNumber, int pin, double availableBalance, double totalBalance) {
        super(accountNumber, pin, availableBalance, totalBalance);
        this.limitPerCheque = 10000.0;
    }
    //Get limit per cheque
    public double getLimitPerCheque() {
        return limitPerCheque;
    }
    //Set limit per cheque
    public void setLimitPerCheque(double limitPerCheque) {
        this.limitPerCheque = limitPerCheque;
    }
    //Check Withdrawal Methods
    public void withdrawByCheque(double amount) {
        if (amount > limitPerCheque) {
            System.out.println("The withdrawal amount exceeds the per-cheque limit HK$" + limitPerCheque);
        } else if (amount > getAvailableBalance()) {
            System.out.println("Insufficient account balance. Your current available balance is HK$" + getAvailableBalance());
        } else {
            debit(amount);
            System.out.printf("Successful withdrawal via cheque HK$%.2f\n", amount);
        }
    }
    //Check Deposit Methods
    public void depositByCheque(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be greater than 0！");
        } else {
            credit(amount);
            System.out.printf("Successfully deposited check HK$%.2f\n", amount);
        }
    }

    public boolean isTransferAmountValid(double amount) {
        if (this.limitPerCheque < amount) {
            return true;
        } else {
            return false;
        }
    }

    public void displayAccountDetails() {

        System.out.printf("Account Number: %d\nAvailable balance: HK$%.2f\nTotal balance: HK$%.2f\nLimit per check: HK$%.2f\n", 
            getAccountNumber(), getAvailableBalance(), getTotalBalance(), getLimitPerCheque());
    }
}