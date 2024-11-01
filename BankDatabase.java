// BankDatabase.java
// Represents the bank account information database 

public class BankDatabase
{
   private Account[][] accounts; // array of Accounts
   
   // no-argument BankDatabase constructor initializes accounts
   public BankDatabase()
   {
      // Changed from Account[][] accounts = new Account[4][2] to this.accounts = new Account[4][2]
      this.accounts = new Account[4][2];
      
      // Initialize user 1's accounts (12345: Savings, Cheque)
      accounts[0][0] = new SavingsAccount(12345, 54321, 1000.0, 1200.0);
      accounts[0][1] = new ChequeAccount(12345, 54321, 500.0, 600.0);
      
      // Initialize user 2's accounts (98765: Savings, Cheque)
      accounts[1][0] = new SavingsAccount(98765, 56789, 200.0, 200.0);
      accounts[1][1] = new ChequeAccount(98765, 56789, 100.0, 150.0);
      
      // Initialize user 3's accounts (13579: Savings, Cheque)
      accounts[2][0] = new SavingsAccount(13579, 24681, 1500.0, 1800.0);
      accounts[2][1] = new ChequeAccount(13579, 24681, 700.0, 850.0);
      
      // Initialize user 4's accounts (22222: Savings, Cheque)
      accounts[3][0] = new SavingsAccount(22222, 45454, 20000.0, 20000.0);
      accounts[3][1] = new ChequeAccount(22222, 45454, 10002.0, 20000.0);
   }

   public SavingsAccount getAuthenticatedSavingsAccount(int accountNumber)
   {
      Account account = getAccount(accountNumber, 0); // 0 for SavingsAccount
      if (account instanceof SavingsAccount) {
         return (SavingsAccount) account; // Authorization passed, return to savings account
      }
      return null; // Access not allowed
   }
   
   // Retrieve the ChequeAccount for a specific user
   public ChequeAccount getAuthenticatedChequeAccount(int accountNumber)
   {
      Account account = getAccount(accountNumber, 1); // 1 for ChequeAccount
      if (account instanceof ChequeAccount) {
         return (ChequeAccount) account; // Authorization passed, return to cheque account
      }
      return null; // Access not allowed
   }
   
   // retrieve specific type of Account (Savings or Cheque) containing specified account number
   private Account getAccount(int accountNumber, int accountType)
   {
      // loop through accounts searching for matching account number
      for (int i = 0; i < accounts.length; i++)
      {
         // check if the first account in the user's row (Savings) matches the account number
         if (accounts[i][0].getAccountNumber() == accountNumber) {
            return accounts[i][accountType]; // return specific type (Savings or Cheque)
         }
      }

      return null; // if no matching account was found, return null
   }

   // determine whether user-specified account number and PIN match
   public boolean authenticateUser(int userAccountNumber, int userPIN)
   {
      // attempt to retrieve the savings account with the account number (as the common account identifier)
      Account userAccount = getAccount(userAccountNumber, 0); // Use SavingsAccount to authenticate

      // if account exists, return result of Account method validatePIN
      if (userAccount != null)
         return userAccount.validatePIN(userPIN);
      else
         return false; // account number not found, so return false
   } // end method authenticateUser

   // return available balance of a specific type of Account (Savings or Cheque)
   public double getAvailableBalance(int userAccountNumber, int accountType)
   {
      return getAccount(userAccountNumber, accountType).getAvailableBalance();
   } 

   // return total balance of a specific type of Account (Savings or Cheque)
   public double getTotalBalance(int userAccountNumber, int accountType)
   {
      return getAccount(userAccountNumber, accountType).getTotalBalance();
   }

   // credit an amount to a specific type of Account (Savings or Cheque)
   public void credit(int userAccountNumber, int accountType, double amount)
   {
      getAccount(userAccountNumber, accountType).credit(amount);
   }


   // debit an amount from of Account with specified account number
   public void debit( int userAccountNumber, int accountType, double amount )
   {
      getAccount( userAccountNumber, accountType).debit( amount );
   } // end method debit
} // end class BankDatabase



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
