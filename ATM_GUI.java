import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingUtilities;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 

public class ATM_GUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private BankDatabase bankDatabase;
    private int currentAccountNumber;
    private double withdrawalAmount; 
    private JLabel confirmationLabel; 
    private CashDispenser cashDispenser; 

    private static final double CHECKING_ACCOUNT_TRANSFER_LIMIT = 10000.00;

    public ATM_GUI() {
        bankDatabase = new BankDatabase(); 
        cashDispenser = new CashDispenser(); 
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        setTitle("ATM System");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 

        // Create different panels
        mainPanel.add(createWelcomePanel(), "Welcome");
        mainPanel.add(createAuthenticatePanel(), "Authenticate");
        mainPanel.add(createMainMenuPanel(), "MainMenu");
        mainPanel.add(createWithdrawalPanel(), "Withdrawal");
        mainPanel.add(createWithdrawalConfirmationPanel(), "WithdrawalConfirmation");
        mainPanel.add(createCashDispensedPanel(), "CashDispensed");
        mainPanel.add(createBalanceInquiryPanel(), "BalanceInquiry");
        mainPanel.add(createTransferPanel(), "Transfer");
        mainPanel.add(createApplyInterestPanel(), "ApplyInterest");

        add(mainPanel);
    }

    private JPanel createWelcomePanel() {
        JPanel welcomePanel = new JPanel();
        JLabel welcomeLabel = new JLabel("Welcome to ATM");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 30)); // Larger font
        welcomePanel.add(welcomeLabel);
        
        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("Arial", Font.BOLD, 20)); // Larger font
        startButton.setPreferredSize(new Dimension(200, 60)); // Button size
        startButton.addActionListener(e -> cardLayout.show(mainPanel, "Authenticate"));
        welcomePanel.add(startButton);
        
        return welcomePanel;
    }

    private JPanel createAuthenticatePanel() {
        JPanel authPanel = new JPanel(new GridLayout(4, 1));
        JTextField accountNumberField = new JTextField();
        JPasswordField pinField = new JPasswordField();

        authPanel.add(new JLabel("Account Number:"));
        authPanel.add(accountNumberField);
        authPanel.add(new JLabel("PIN:"));
        authPanel.add(pinField);

        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.BOLD, 20)); // Larger font
        submitButton.addActionListener(e -> {
            String accountNumber = accountNumberField.getText();
            String pin = new String(pinField.getPassword());
            if (validateAccount(accountNumber, pin)) {
                currentAccountNumber = Integer.parseInt(accountNumber);
                cardLayout.show(mainPanel, "MainMenu");
            }
        });
        authPanel.add(submitButton);
        return authPanel;
    }

    private boolean validateAccount(String accountNumber, String pin) {
        try {
            int accountNum = Integer.parseInt(accountNumber);
            int pinNum = Integer.parseInt(pin);
            if (bankDatabase.authenticateUser(accountNum, pinNum)) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Account Number or PIN. Please try again.");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter valid numerical values for Account Number and PIN.");
            return false;
        }
    }

    private JPanel createMainMenuPanel() {
        JPanel menuPanel = new JPanel();
        JButton withdrawalButton = new JButton("Withdrawal");
        JButton balanceInquiryButton = new JButton("Balance Inquiry");
        JButton transferButton = new JButton("Transfer Money");
        JButton applyInterestButton = new JButton("Apply Interest");
        JButton exitButton = new JButton("Exit");

        ActionListener menuActionListener = e -> {
            JButton sourceButton = (JButton) e.getSource();
            switch (sourceButton.getText()) {
                case "Withdrawal":
                    cardLayout.show(mainPanel, "Withdrawal");
                    break;
                case "Balance Inquiry":
                    cardLayout.show(mainPanel, "BalanceInquiry");
                    break;
                case "Transfer Money":
                    cardLayout.show(mainPanel, "Transfer");
                    break;
                case "Apply Interest":
                    cardLayout.show(mainPanel, "ApplyInterest");
                    break;
                case "Exit":
                    showExitConfirmation();
                    break;
            }
        };

        withdrawalButton.addActionListener(menuActionListener);
        balanceInquiryButton.addActionListener(menuActionListener);
        transferButton.addActionListener(menuActionListener);
        applyInterestButton.addActionListener(menuActionListener);
        exitButton.addActionListener(menuActionListener);

        withdrawalButton.setFont(new Font("Arial", Font.BOLD, 20));
        balanceInquiryButton.setFont(new Font("Arial", Font.BOLD, 20));
        transferButton.setFont(new Font("Arial", Font.BOLD, 20));
        applyInterestButton.setFont(new Font("Arial", Font.BOLD, 20));
        exitButton.setFont(new Font("Arial", Font.BOLD, 20));

        menuPanel.add(withdrawalButton);
        menuPanel.add(balanceInquiryButton);
        menuPanel.add(transferButton);
        menuPanel.add(applyInterestButton);
        menuPanel.add(exitButton);
        
        return menuPanel;
    }

    private JPanel createWithdrawalPanel() {
        JPanel withdrawalPanel = new JPanel(new GridLayout(6, 1, 10, 10)); // Add gaps
        JButton predefinedButton1 = new JButton("Withdraw $100");
        JButton predefinedButton2 = new JButton("Withdraw $500");
        JButton predefinedButton3 = new JButton("Withdraw $1000");
        JButton customButton = new JButton("Withdraw Custom Amount");

        predefinedButton1.setFont(new Font("Arial", Font.BOLD, 20));
        predefinedButton2.setFont(new Font("Arial", Font.BOLD, 20));
        predefinedButton3.setFont(new Font("Arial", Font.BOLD, 20));
        customButton.setFont(new Font("Arial", Font.BOLD, 20));

        predefinedButton1.addActionListener(e -> initiateWithdrawal(100));
        predefinedButton2.addActionListener(e -> initiateWithdrawal(500));
        predefinedButton3.addActionListener(e -> initiateWithdrawal(1000));
        customButton.addActionListener(e -> showCustomAmountInput());

        withdrawalPanel.add(predefinedButton1);
        withdrawalPanel.add(predefinedButton2);
        withdrawalPanel.add(predefinedButton3);
        withdrawalPanel.add(customButton);

        JButton backButton = new JButton("Back to Main Menu");
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MainMenu"));
        withdrawalPanel.add(backButton);

        return withdrawalPanel;
    }

    private void initiateWithdrawal(double amount) {
        withdrawalAmount = amount;

        double availableSavingsBalance = bankDatabase.getAvailableBalance(currentAccountNumber, 0); 
        double availableChequeBalance = bankDatabase.getAvailableBalance(currentAccountNumber, 1); 

        if (availableSavingsBalance >= withdrawalAmount || availableChequeBalance >= withdrawalAmount) {
            updateWithdrawalConfirmationLabel();
            cardLayout.show(mainPanel, "WithdrawalConfirmation");
        } else {
            JOptionPane.showMessageDialog(null, "Insufficient funds for this transaction.");
        }
    }

    private void showCustomAmountInput() {
        String amountText = JOptionPane.showInputDialog("Enter Custom Amount (must be multiple of 100):");
        if (amountText != null) {
            try {
                withdrawalAmount = Double.parseDouble(amountText);
                if (withdrawalAmount <= 0 || withdrawalAmount % 100 != 0) {
                    JOptionPane.showMessageDialog(null, "The amount must be a positive multiple of 100.");
                    return;
                }

                double availableSavingsBalance = bankDatabase.getAvailableBalance(currentAccountNumber, 0); 
                double availableChequeBalance = bankDatabase.getAvailableBalance(currentAccountNumber, 1); 

                if (availableSavingsBalance >= withdrawalAmount || availableChequeBalance >= withdrawalAmount) {
                    updateWithdrawalConfirmationLabel();
                    cardLayout.show(mainPanel, "WithdrawalConfirmation");
                } else {
                    JOptionPane.showMessageDialog(null, "Insufficient funds for this transaction.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid numerical amount.");
            }
        }
    }

    private void updateWithdrawalConfirmationLabel() {
        confirmationLabel.setText("Please confirm the amount: $" + withdrawalAmount);
    }

    private JPanel createWithdrawalConfirmationPanel() {
        JPanel confirmationPanel = new JPanel();
        confirmationLabel = new JLabel(); 
        confirmationLabel.setFont(new Font("Arial", Font.BOLD, 20));
        confirmationPanel.add(confirmationLabel);

        JButton confirmWithdrawalButton = new JButton("Confirm Withdrawal");
        confirmWithdrawalButton.setFont(new Font("Arial", Font.BOLD, 20)); 
        confirmWithdrawalButton.addActionListener(e -> {
            int accountType = selectAccountType(); 
            if (accountType != -1) {
                int withdrawalAmountInt = (int) withdrawalAmount;

                if (accountType == 1 && bankDatabase.getAvailableBalance(currentAccountNumber, 1) < withdrawalAmount) {
                    JOptionPane.showMessageDialog(null, "Insufficient funds in Cheque Account.");
                    return;
                }

                if (cashDispenser.isSufficientCashAvailable(withdrawalAmountInt)) {
                    bankDatabase.debit(currentAccountNumber, accountType, withdrawalAmountInt);
                    cashDispenser.dispenseCash(withdrawalAmountInt); 
                    cardLayout.show(mainPanel, "CashDispensed");
                } else {
                    JOptionPane.showMessageDialog(null, "Insufficient cash available in the ATM.");
                }
            }
        });
        confirmationPanel.add(confirmWithdrawalButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 20));
        cancelButton.addActionListener(e -> cardLayout.show(mainPanel, "Withdrawal"));
        confirmationPanel.add(cancelButton);

        return confirmationPanel;
    }

    private int selectAccountType() {
        String[] options = {"Savings Account", "Cheque Account"};
        return JOptionPane.showOptionDialog(null, "Select account type for withdrawal:",
                "Account Type Selection",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
    }

    private JPanel createCashDispensedPanel() {
        JPanel cashPanel = new JPanel();
        JLabel messageLabel = new JLabel("Please take your cash and card.");
        messageLabel.setFont(new Font("Arial", Font.BOLD, 20));
        cashPanel.add(messageLabel);

        JButton finishButton = new JButton("Finish");
        finishButton.setFont(new Font("Arial", Font.BOLD, 20));
        finishButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Thank you! Please take your card.");
            cardLayout.show(mainPanel, "MainMenu");
        });
        cashPanel.add(finishButton);

        return cashPanel;
    }

    private JPanel createBalanceInquiryPanel() {
        JPanel balancePanel = new JPanel();
        JButton checkBalanceButton = new JButton("Check Balance");
        JLabel balanceLabel = new JLabel("");

        checkBalanceButton.setFont(new Font("Arial", Font.BOLD, 20));
        checkBalanceButton.addActionListener(e -> {
            double availableSavingsBalance = bankDatabase.getAvailableBalance(currentAccountNumber, 0);
            double totalSavingsBalance = bankDatabase.getTotalBalance(currentAccountNumber, 0);
            double availableChequeBalance = bankDatabase.getAvailableBalance(currentAccountNumber, 1);
            double totalChequeBalance = bankDatabase.getTotalBalance(currentAccountNumber, 1);
            balanceLabel.setText(String.format("Savings - Available: $%.2f, Total: $%.2f | Cheque - Available: $%.2f, Total: $%.2f",
                    availableSavingsBalance, totalSavingsBalance, availableChequeBalance, totalChequeBalance));
        });

        balancePanel.add(checkBalanceButton);
        balancePanel.add(balanceLabel);

        JButton backButton = new JButton("Back to Main Menu");
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MainMenu"));
        balancePanel.add(backButton);

        return balancePanel;
    }

    private JPanel createTransferPanel() {
        JPanel transferPanel = new JPanel(new GridLayout(5, 1));
        JTextField amountField = new JTextField();
        JTextField targetAccountField = new JTextField();

        transferPanel.add(new JLabel("Target Account Number (Cheque Only):"));
        transferPanel.add(targetAccountField);
        transferPanel.add(new JLabel("Enter Amount (max: $" + CHECKING_ACCOUNT_TRANSFER_LIMIT + "):"));
        transferPanel.add(amountField);

        JButton transferButton = new JButton("Transfer");
        transferButton.setFont(new Font("Arial", Font.BOLD, 20));
        transferButton.addActionListener(e -> {
            String targetAccount = targetAccountField.getText();
            String amountText = amountField.getText();
            if (!targetAccount.isEmpty() && !amountText.isEmpty()) {
                try {
                    int targetNum = Integer.parseInt(targetAccount);
                    double amount = Double.parseDouble(amountText);
                    if (amount <= 0) {
                        JOptionPane.showMessageDialog(null, "Amount must be a positive number.");
                        return;
                    }
                    if (amount > CHECKING_ACCOUNT_TRANSFER_LIMIT) {
                        JOptionPane.showMessageDialog(null, "Transfer amount exceeds maximum limit of $" + CHECKING_ACCOUNT_TRANSFER_LIMIT);
                        return;
                    }
                    if (targetNum == currentAccountNumber) {
                        JOptionPane.showMessageDialog(null, "You cannot transfer money to yourself.");
                        return;
                    }
                    performTransfer(targetNum, amount);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter valid numerical values for Amount and Target Account.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please fill all fields.");
            }
        });
        transferPanel.add(transferButton);

        JButton backButton = new JButton("Back to Main Menu");
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MainMenu"));
        transferPanel.add(backButton);

        return transferPanel;
    }

    private void performTransfer(int targetAccountNumber, double amount) {
        int targetAccountType = 1; 

        int sourceAccountType = selectAccountTypeForTransfer();

        double availableBalance = bankDatabase.getAvailableBalance(currentAccountNumber, sourceAccountType);
        
        if (amount > 0 && availableBalance >= amount) {
            bankDatabase.debit(currentAccountNumber, sourceAccountType, amount);
            bankDatabase.credit(targetAccountNumber, targetAccountType, amount);
            JOptionPane.showMessageDialog(null, "Transfer successful!");
        } else {
            JOptionPane.showMessageDialog(null, "Insufficient funds for this transaction.");
        }
    }

    private int selectAccountTypeForTransfer() {
        String[] options = {"Savings Account", "Cheque Account"};
        return JOptionPane.showOptionDialog(null, "Select account type for transfer:",
                "Account Type Selection",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
    }

    private JPanel createApplyInterestPanel() {
        JPanel interestPanel = new JPanel(new GridLayout(4, 1));
        JTextField periodField = new JTextField();

        interestPanel.add(new JLabel("Interest Period (year/quarter/month):"));
        interestPanel.add(periodField);

        JButton applyButton = new JButton("Apply Interest");
        applyButton.setFont(new Font("Arial", Font.BOLD, 20));
        applyButton.addActionListener(e -> {
            String period = periodField.getText();
            applyInterest(period);
        });
        interestPanel.add(applyButton);

        JButton backButton = new JButton("Back to Main Menu");
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MainMenu"));
        interestPanel.add(backButton);

        return interestPanel;
    }

    private void applyInterest(String period) {
        SavingsAccount account = bankDatabase.getAuthenticatedSavingsAccount(currentAccountNumber);
        if (account != null) {
            account.applyInterest(period);
            JOptionPane.showMessageDialog(null, "Interest applied for period: " + period);
        } else {
            JOptionPane.showMessageDialog(null, "Unable to apply interest. Account does not exist or permission denied.");
        }
    }

    private void showExitConfirmation() {
        int response = JOptionPane.showConfirmDialog(null, "Do you want to return to the welcome screen instead of exiting?", 
            "Exit Confirmation", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            cardLayout.show(mainPanel, "Welcome");
        } else {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ATM_GUI atmGui = new ATM_GUI();
            atmGui.setVisible(true);
        });
    }
}
