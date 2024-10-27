// Withdrawal.java
// Represents a withdrawal ATM transaction

public class Withdrawal extends Transaction
{
   private int amount; // amount to withdraw
   private Keypad keypad; // reference to keypad
   private CashDispenser cashDispenser; // reference to cash dispenser

   // constant corresponding to menu option to cancel
   private final static int CANCELED = 5;

   // Withdrawal constructor
   public Withdrawal( int userAccountNumber, Screen atmScreen, 
      BankDatabase atmBankDatabase, Keypad atmKeypad, 
      CashDispenser atmCashDispenser )
   {
      // initialize superclass variables
      super( userAccountNumber, atmScreen, atmBankDatabase );
      
      // initialize references to keypad and cash dispenser
      keypad = atmKeypad;
      cashDispenser = atmCashDispenser;
   } // end Withdrawal constructor

   // perform transaction
   public void execute()
{
    boolean cashDispensed = false;
    double availableBalance;
    BankDatabase bankDatabase = getBankDatabase(); 
    Screen screen = getScreen();

    // First select account type
    screen.displayMessageLine("\nSelect account type:");
    screen.displayMessageLine("1 - Savings Account");
    screen.displayMessageLine("2 - Cheque Account");
    screen.displayMessage("\nChoose an account               : ");
    
    int accountType = keypad.getInput() - 1; // Convert to 0-based index
    
    if (accountType != 0 && accountType != 1) {
        screen.displayMessageLine("\nInvalid account type. Transaction canceled.");
        return;
    }

    do
    {
        amount = displayMenuOfAmounts();
        
        if (amount != CANCELED) //genius!!
        {
            availableBalance = 
                bankDatabase.getAvailableBalance(getAccountNumber(), accountType);
      
            if (amount <= availableBalance)
            {   
                if (cashDispenser.isSufficientCashAvailable(amount))
                {
                    bankDatabase.debit(getAccountNumber(), accountType, amount);
                    cashDispenser.dispenseCash(amount);
                    cashDispensed = true;
                    screen.displayMessageLine("\nPlease take your cash now.");
                }
                else 
                    screen.displayMessageLine(
                        "\nInsufficient cash available in the ATM." +
                        "\n\nPlease choose a smaller amount.");
            }
            else
            {
                screen.displayMessageLine(
                    "\nInsufficient funds in your account." +
                    "\n\nPlease choose a smaller amount.");
            }
        }
        else
        {
            screen.displayMessageLine("\nCanceling transaction...");
            return;
        }
    } while (!cashDispensed);
}

   // display a menu of withdrawal amounts and the option to cancel;
   // return the chosen amount or 0 if the user chooses to cancel
   private int displayMenuOfAmounts()
   {
      int userChoice = 0; // local variable to store return value

      Screen screen = getScreen(); // get screen reference
      
      // array of amounts to correspond to menu numbers
      int customAmount = 0;
      int amounts[] = { 0, 100, 500, 1000, customAmount};

      // loop while no valid choice has been made
      while ( userChoice == 0 )
      {
         // display the menu
         screen.displayMessageLine( "\nWithdrawal Menu:" );
         screen.displayMessageLine( "1 - $100" );
         screen.displayMessageLine( "2 - $500" );
         screen.displayMessageLine( "3 - $1000" );
         screen.displayMessageLine("4 - Other amount:");
         screen.displayMessageLine( "5 - Cancel transaction" );
         screen.displayMessage( "\nChoose a withdrawal amount      : " );

         int input = keypad.getInput(); // get user input through keypad

         // determine how to proceed based on the input value
         switch ( input )
         {
            case 1: // if the user chose a withdrawal amount 
            case 2: // (i.e., chose option 1, 2 or 3), return the
            case 3: // corresponding amount from amounts array
               userChoice = amounts[ input ]; // save user's choice
               break;
            case 4:
                userChoice = CustomAmountMenu();
                break;
            case CANCELED: // the user chose to cancel
               userChoice = CANCELED; // save user's choice
               break;
            default: // the user did not enter a value from 1-6
               screen.displayMessageLine( 
                  "\nInvalid selection. Try again." );
         } // end switch
      } // end while

      return userChoice; // return withdrawal amount or CANCELED
   } // end method displayMenuOfAmounts

   //       ---Highlight---       //
   // To be written in the report //
      private int CustomAmountMenu() {
       Screen screen = getScreen();
       int amount = 0;
       boolean isInvalid;
       
       do {
           screen.displayMessage("\nEnter an amount multiple of 100 : ");
           amount = keypad.getInput();
           // Check is moved inside the loop and updated each time
           isInvalid = ((amount % 100) != 0 || amount <= 0);
           
           if (isInvalid) {
               screen.displayMessageLine("Invalid amount.");
           }
       } while (isInvalid);  // Loop continues while amount is invalid
       
       return amount;
   }
} // end class Withdrawal

   //       ---Highlight---       //
   // To be written in the report //



/**************************************************************************
 * (C) Copyright 1992-2007 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/
