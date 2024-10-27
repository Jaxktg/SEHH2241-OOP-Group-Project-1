public class BalanceTransfer extends Transaction {
    private final Keypad keypad;
    private final static int CANCELED = 2;

    public BalanceTransfer(int userAccountNumber, Screen atmScreen, BankDatabase atmBankDatabase, Keypad atmKeypad) {
        super(userAccountNumber, atmScreen, atmBankDatabase);
        keypad = atmKeypad;
    }

    @Override
    public void execute() {
        BankDatabase bankDatabase = getBankDatabase();
        Screen screen = getScreen();
     
        screen.displayMessageLine("\nSelect account type to send money from: ");
        screen.displayMessageLine("1 - Savings Account");
        screen.displayMessageLine("2 - Cheque Account");
        screen.displayMessage("\nChoose an account               : ");

        int accountType = keypad.getInput() - 1; // Convert to 0-based index
    
        if (accountType != 0 && accountType != 1) {
            screen.displayMessageLine("\nInvalid account type. Transaction canceled.");
            return;
        }

        int[] transferData = displayMenuOfTargetAccount();
        
        if (transferData[0] != CANCELED) {
            processTransfer(accountType, transferData);
        }
    }

    private void processTransfer(int sourceAccountType, int[] transferData) {
        Screen screen = getScreen();
        BankDatabase bankDatabase = getBankDatabase();
        boolean validAmount = false;
        do {
            try {
                screen.displayMessage("\nEnter amount to transfer        : ");
                double amount = keypad.getInput();
                validAmount = transferAmountValidator(amount);

                if (validAmount) {
                    double availableBalance = bankDatabase.getAvailableBalance(getAccountNumber(), sourceAccountType);
                    if (amount <= availableBalance) {
                        performTransfer(amount, sourceAccountType, transferData);
                        screen.displayMessageLine("\nTransfer successful!");
                        return;
                    } else {
                        screen.displayMessageLine("\nInsufficient funds. Transaction canceled.");
                        return;
                    }
                }
            } catch(NullPointerException e ) { screen.displayMessageLine("Recipient ID not found. Transaction canceled."); return;}
        } while (!validAmount);   
    }

    private void performTransfer(double amount, int sourceAccountType, int[] transferData) {
        BankDatabase bankDatabase = getBankDatabase();
        
        if (transferData[0] == 1) {
            bankDatabase.debit(getAccountNumber(), sourceAccountType, amount);
            bankDatabase.credit(transferData[1], 1, amount); // Assuming target is always a checking account
        }
    }

    private boolean transferAmountValidator(double inputAmount) {
        Screen screen = getScreen();
        if (inputAmount <= 0) {
            screen.displayMessageLine("Input amount invalid!");
            return false;
        }
        return true;
    }

    private int[] displayMenuOfTargetAccount() {
        int userChoice = 0;
        int targetAccount = 0;
        Screen screen = getScreen();

        while (userChoice == 0) {
            screen.displayMessageLine("Transfer Menu:");
            screen.displayMessageLine("1 - Transfer to other account no."); 
            screen.displayMessageLine("2 - Cancel Transaction");
            screen.displayMessage("\nEnter a choice:                 : ");
            
            userChoice = keypad.getInput();

            switch (userChoice) {
                case 1: 
                    while (userChoice == 1 && targetAccount == 0) { //TODO 
                        screen.displayMessage("Enter the recipient account no. : ");
                        int inputAccountNo = keypad.getInput();
                        if (inputAccountNo != getAccountNumber()) {
                            targetAccount = inputAccountNo;
                        } else {
                            screen.displayMessageLine("ERROR: You can't transfer to yourself!");
                            targetAccount = 0;
                        }
                    }
                    break;
                case CANCELED:
                    break;
                default:
                    screen.displayMessageLine("\nInvalid selection. Try again.");
                    userChoice = 0;
            }
        }

        return new int[]{userChoice, targetAccount};
    }
}
