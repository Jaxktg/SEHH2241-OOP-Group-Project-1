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
import java.awt.FlowLayout;
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
        setSize(400,600);
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
        JPanel welcomePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel welcomeLabel = new JLabel("Welcome to ATM");
        
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 30)); // set text size 30  
        welcomePanel.add(welcomeLabel);// Display "Welcome to ATM" on screen
        welcomePanel.add(space());// Display spacing on screen
        
        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("Arial", Font.BOLD, 30)); // set text size 30
        startButton.setPreferredSize(new Dimension(200, 60)); // set Button size 60
        startButton.addActionListener(e -> cardLayout.show(mainPanel, "Authenticate"));
        welcomePanel.add(startButton);
        
        return welcomePanel;
    }

    private JPanel createAuthenticatePanel() {
        JPanel authPanel = new JPanel(new GridLayout(6, 1));
        JTextField accountNumberField = new JTextField();
        JPasswordField pinField = new JPasswordField();

        
        Font inputFont = new Font("Arial", Font.PLAIN, 40); //set font to input
        accountNumberField.setFont(inputFont); // Apply font to account number field
        pinField.setFont(inputFont); // Apply font to PIN field
        
        JLabel accountNumberLabel = new JLabel("Account Number:");
        accountNumberLabel.setFont(new Font("Arial", Font.BOLD, 30));//set text size 30
        
        authPanel.add(accountNumberLabel);// Display "Account Number:" on screen
        authPanel.add(accountNumberField);// Display the input space of account number on the screen.
        
        JLabel pinLabel = new JLabel("PIN:");
        pinLabel.setFont(new Font("Arial", Font.BOLD, 30));
        authPanel.add(pinLabel);// Display "PIN:" on screen
        authPanel.add(pinField);//Display the input space of pin on the screen
        
        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.BOLD, 30));// set text size 30 
        submitButton.addActionListener(e -> {//After kick the submit button 
            String accountNumber = accountNumberField.getText();
            String pin = new String(pinField.getPassword());
            if (validateAccount(accountNumber, pin)) {
                currentAccountNumber = Integer.parseInt(accountNumber);
                cardLayout.show(mainPanel, "MainMenu");//back to mainmenu
                accountNumberField.setText("");//Clear account number field
                pinField.setText("");//Clear pin field 
            }
        });
        authPanel.add(submitButton);//Display the submit button on screen 
        return authPanel;
    }
    
    //Validate account 
    private boolean validateAccount(String accountNumber, String pin) {
        try {
            int accountNum = Integer.parseInt(accountNumber);
            int pinNum = Integer.parseInt(pin);
            if (bankDatabase.authenticateUser(accountNum, pinNum)) {
                return true;
            } else {
                JLabel InvalidAcNumorPin = new JLabel("Invalid Account Number or PIN. Please try again.");
                InvalidAcNumorPin.setFont(new Font("Arial", Font.BOLD, 24));
                JOptionPane.showMessageDialog(null,InvalidAcNumorPin );
                return false;
            }
        } catch (NumberFormatException e) {
            JLabel Validvalues_AcNumorPin = new JLabel("Please enter valid numerical values for Account Number and PIN.");
            Validvalues_AcNumorPin.setFont(new Font("Arial", Font.BOLD, 24));
            JOptionPane.showMessageDialog(null,Validvalues_AcNumorPin );
            return false;
        }
    }

    private JPanel createMainMenuPanel() {
        JPanel menuPanel = new JPanel((new GridLayout(3,2)));
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
            JLabel Insuff_funds_transaction = new JLabel("Insufficient funds for this transaction.");
            Insuff_funds_transaction.setFont(new Font("Arial", Font.BOLD, 24));
            JOptionPane.showMessageDialog(null,Insuff_funds_transaction );
        }
    }

    private void showCustomAmountInput() {
        JLabel enterCusAmount = new JLabel("Enter Custom Amount (must be multiple of 100):");
        enterCusAmount.setFont(new Font("Arial", Font.BOLD, 24));
        String amountText = JOptionPane.showInputDialog(enterCusAmount);
        if (amountText != null) {
            try {
                withdrawalAmount = Double.parseDouble(amountText);
                if (withdrawalAmount <= 0 || withdrawalAmount % 100 != 0) {
                    JLabel multpositive = new JLabel("The amount must be a positive multiple of 100.");
                    multpositive.setFont(new Font("Arial", Font.BOLD, 24));
                    JOptionPane.showMessageDialog(null,multpositive);
                    return;
                }

                double availableSavingsBalance = bankDatabase.getAvailableBalance(currentAccountNumber, 0); 
                double availableChequeBalance = bankDatabase.getAvailableBalance(currentAccountNumber, 1); 

                if (availableSavingsBalance >= withdrawalAmount || availableChequeBalance >= withdrawalAmount) {
                    updateWithdrawalConfirmationLabel();
                    cardLayout.show(mainPanel, "WithdrawalConfirmation");
                } else {
                    JLabel Insuff_funds_transaction_2 = new JLabel ("Insufficient funds for this transaction.");
                    Insuff_funds_transaction_2.setFont(new Font("Arial", Font.BOLD, 24));
                    JOptionPane.showMessageDialog(null,Insuff_funds_transaction_2);
                }
            } catch (NumberFormatException e) {
                JLabel enterValidAmount = new JLabel("Please enter a valid numerical amount.");
                enterValidAmount.setFont(new Font("Arial", Font.BOLD, 24));
                JOptionPane.showMessageDialog(null,enterValidAmount );
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

                if (accountType == 1 && bankDatabase.getAvailableBalance(currentAccountNumber, 1) < withdrawalAmount) 
                {
                    JLabel Insuff_funds_ChequeAcc = new JLabel("Insufficient funds in Cheque Account.");
                    Insuff_funds_ChequeAcc.setFont(new Font("Arial", Font.BOLD, 24));
                    JOptionPane.showMessageDialog(null,Insuff_funds_ChequeAcc );
                    return;
                }

                if (cashDispenser.isSufficientCashAvailable(withdrawalAmountInt)) {
                    bankDatabase.debit(currentAccountNumber, accountType, withdrawalAmountInt);
                    cashDispenser.dispenseCash(withdrawalAmountInt); 
                    cardLayout.show(mainPanel, "CashDispensed");
                } else {
                    JLabel Insuff_cash_ATM = new JLabel ("Insufficient cash available in the ATM.");
                    Insuff_cash_ATM.setFont(new Font("Arial", Font.BOLD, 24));
                    JOptionPane.showMessageDialog(null, Insuff_cash_ATM);
                }
            }
        });
        confirmationPanel.add(confirmWithdrawalButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 20));
        cancelButton.addActionListener(e -> cardLayout.show(mainPanel, "Withdrawal"));
        confirmationPanel.add(space());
        confirmationPanel.add(cancelButton);

        return confirmationPanel;
    }

    private int selectAccountType() {
        String[] options = {"Savings Account", "Cheque Account"};
        JLabel selectAcctype_withdrawal = new JLabel("Select account type for withdrawal:");
        selectAcctype_withdrawal.setFont(new Font("Arial", Font.BOLD, 24));
        
        return JOptionPane.showOptionDialog(null,selectAcctype_withdrawal ,"Account Type Selection",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
    }

    private JPanel createCashDispensedPanel() {
        JPanel cashPanel = new JPanel();
        JLabel messageLabel = new JLabel("Please take your cash and card.");
        messageLabel.setFont(new Font("Arial", Font.BOLD, 20));
        cashPanel.add(messageLabel);

        JButton finishButton = new JButton("Finish");
        finishButton.setFont(new Font("Arial", Font.BOLD, 20));//set finishButton text size 20
        finishButton.addActionListener(e -> {
            JLabel Thank_takecard = new JLabel("Thank you! Please take your card.");
            Thank_takecard.setFont(new Font("Arial", Font.BOLD, 24));
            JOptionPane.showMessageDialog(null,Thank_takecard );//pop up messages
            cardLayout.show(mainPanel, "MainMenu");//jump to MainMenu
        });
       
        
        cashPanel.add(space());// show spacing on screen 
        cashPanel.add(finishButton);//show finishButton on screen 

        return cashPanel;
    }

   private JPanel createBalanceInquiryPanel() {
        JPanel balancePanel = new JPanel();
        JButton checkBalanceButton = new JButton("Check Balance");
        JLabel balanceLabel = new JLabel("<html></html>");
        //Newline using html(<html>...text...</html>)
        JLabel space = new JLabel();
        JLabel updatebalance = new JLabel("--Please kick the Check Balance again--");
        
        checkBalanceButton.setFont(new Font("Arial", Font.BOLD, 20));
        updatebalance.setFont(new Font("Arial",Font.BOLD, 20));//update balance
        checkBalanceButton.addActionListener(e -> {
            double availableSavingsBalance = bankDatabase.getAvailableBalance(currentAccountNumber, 0);
            double totalSavingsBalance = bankDatabase.getTotalBalance(currentAccountNumber, 0);
            double availableChequeBalance = bankDatabase.getAvailableBalance(currentAccountNumber, 1);
            double totalChequeBalance = bankDatabase.getTotalBalance(currentAccountNumber, 1);
            balanceLabel.setText(String.format("<html>Savings - Available: $%.2f<br>" +
                "Savings- Total:$%.2f<br>" +
                "Cheque - Available: $%.2f<br>" +
                "Cheque - Total: $%.2f</html>",
                    availableSavingsBalance, totalSavingsBalance, availableChequeBalance, totalChequeBalance));
            //<br> for newline       
            balanceLabel.setFont(new Font("Arial", Font.BOLD, 24));        
        });

        balancePanel.add(checkBalanceButton);
        balancePanel.add(space());
        balancePanel.add(balanceLabel);
        balancePanel.add(space());
        balancePanel.add(updatebalance);
        balancePanel.add(space());
        

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
        
        Font inputFont = new Font("Arial", Font.PLAIN, 40); //set font to input
        targetAccountField.setFont(inputFont); // Apply font to target accountfield
        amountField.setFont(inputFont); // Apply font to amount field
        
        
        JLabel targetAccountLabel = new JLabel("<html>Target Account Number<br> (Cheque Only):</html>");
        //use the <br> from html
        targetAccountLabel.setFont(new Font("Arial", Font.BOLD, 24));
        
        transferPanel.add(targetAccountLabel);
        transferPanel.add(targetAccountField);
        
        JLabel amountLabel = new JLabel("<html>Enter Amount <br>(max: $" + CHECKING_ACCOUNT_TRANSFER_LIMIT + "):</html>");
        //use the <br> from html
        amountLabel.setFont(new Font("Arial",Font.BOLD, 24));
        
        transferPanel.add(amountLabel);
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
                        JLabel amount_posNum = new JLabel("Amount must be a positive number.");
                        amount_posNum.setFont(new Font("Arial", Font.BOLD, 24));
                        JOptionPane.showMessageDialog(null,amount_posNum);
                        return;
                    }
                    if (amount > CHECKING_ACCOUNT_TRANSFER_LIMIT) {
                        JLabel Transfer_amountmaxexceeds = new JLabel("Transfer amount exceeds maximum limit of $"+CHECKING_ACCOUNT_TRANSFER_LIMIT);
                        Transfer_amountmaxexceeds.setFont(new Font("Arial", Font.BOLD, 24));
                        JOptionPane.showMessageDialog(null, Transfer_amountmaxexceeds );
                        return;
                    }
                    if (targetNum == currentAccountNumber) {
                        JLabel transfer_yourself = new JLabel("You cannot transfer money to yourself.");
                        transfer_yourself.setFont(new Font("Arial", Font.BOLD, 24));
                        JOptionPane.showMessageDialog(null,transfer_yourself );
                        return;
                    }
                    performTransfer(targetNum, amount);
                    amountField.setText("");//Clear amount field
                    targetAccountField.setText("");//Clear target account field
                } catch (NumberFormatException ex) {
                    JLabel valid_Amount_TargetAcc = new JLabel("Please enter valid numerical values for Amount and Target Account.");
                    valid_Amount_TargetAcc.setFont(new Font("Arial", Font.BOLD, 24));
                    JOptionPane.showMessageDialog(null,valid_Amount_TargetAcc);
                }
            } else {
                JLabel fill_all_fields = new JLabel("Please fill all fields.");
                fill_all_fields.setFont(new Font("Arial", Font.BOLD, 24));
                JOptionPane.showMessageDialog(null,fill_all_fields );
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
            JLabel Transfer_success = new JLabel("Transfer successful!");
            Transfer_success.setFont(new Font("Arial", Font.BOLD, 24));
            JOptionPane.showMessageDialog(null,Transfer_success );
        } else {
            JLabel Transfer_insuff_funds = new JLabel("Insufficient funds for this transaction.");
            Transfer_insuff_funds.setFont(new Font("Arial", Font.BOLD, 24));
            JOptionPane.showMessageDialog(null,Transfer_insuff_funds );
        }
    }

    private int selectAccountTypeForTransfer() {
        String[] options = {"Savings Account", "Cheque Account"};
        JLabel Transfer_Acctype = new JLabel("Select account type for transfer:");
        Transfer_Acctype.setFont(new Font("Arial", Font.BOLD, 24));
        return JOptionPane.showOptionDialog(null,Transfer_Acctype ,
                "Account Type Selection",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
    }

    private JPanel createApplyInterestPanel() {
        JPanel interestPanel = new JPanel(new GridLayout(4, 1));
        JTextField periodField = new JTextField();
        
        Font input = new Font("Arial",Font.BOLD, 28);
        periodField.setFont(input);
        
        JLabel interestperiodlabel = new JLabel("<html>Interest Period <br>(year/quarter/month):</html>");
        interestperiodlabel.setFont(new Font("Arial", Font.BOLD, 28));
        interestPanel.add(interestperiodlabel);
        interestPanel.add(periodField);

        JButton applyButton = new JButton("Apply Interest");
        applyButton.setFont(new Font("Arial", Font.BOLD, 20));
        applyButton.addActionListener(e -> {
           String period = periodField.getText();
            applyInterest_GUI(period);
        });
        interestPanel.add(applyButton);

        JButton backButton = new JButton("Back to Main Menu");
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MainMenu"));
        interestPanel.add(backButton);

        return interestPanel;
    }

    private void applyInterest_GUI(String period) {
        SavingsAccount account = bankDatabase.getAuthenticatedSavingsAccount(currentAccountNumber);
        if (account != null) {
            switch(period)
            {
             case"year":
             case"quarter":
             case"month":
                account.applyInterest(period);
                JLabel Interest_applied = new JLabel("Interest applied for period: " + period);
                Interest_applied.setFont(new Font("Arial", Font.BOLD, 24));
                JOptionPane.showMessageDialog(null,Interest_applied); 
                return;
             default:
                break;
            }  
            JLabel Interest_unapplie = new JLabel("Unable to apply interest. Account does not exist or permission denied.");
            Interest_unapplie.setFont(new Font("Arial", Font.BOLD, 24));
            JOptionPane.showMessageDialog(null,Interest_unapplie );
            createApplyInterestPanel();
        }
        
            
            
    }


    private void showExitConfirmation() {
        JLabel Ask_back_welcome = new JLabel("Do you want to return to the welcome screen instead of exiting?");
        Ask_back_welcome.setFont(new Font("Arial", Font.BOLD, 24));
        int response = JOptionPane.showConfirmDialog(null,Ask_back_welcome , 
            "Exit Confirmation", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            cardLayout.show(mainPanel, "Welcome");
        } else {
            System.exit(0);
        }
    }
    private JLabel space()//spacing
    {
        JLabel space = new JLabel();//null JLabel
        space.setPreferredSize(new Dimension(0,150)); //Set the spacing in height
        return space;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ATM_GUI atmGui = new ATM_GUI();
            atmGui.setVisible(true);
        });
    }
}
