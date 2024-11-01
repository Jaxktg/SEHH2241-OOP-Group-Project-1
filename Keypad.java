// Keypad.java
// Represents the keypad of the ATM
import java.util.Scanner; // program uses Scanner to obtain user input

public class Keypad
{
   private Scanner input; // reads data from the command line
                         
   // no-argument constructor initializes the Scanner
   public Keypad()
   {
      input = new Scanner( System.in );    
   } // end no-argument Keypad constructor

   // return an integer value entered by user 
   public int getInput() {
       while (true) {
           try {
               return input.nextInt(); // attempt to read an integer
           }
           catch (java.util.InputMismatchException e) {
               // Clear the invalid input from the scanner
               input.nextLine();
               System.out.print(String.format("\033[%dA",2)); //move up 1 line in console
               System.out.println("Invalid input, please enter an integer.");
               //System.out.print(String.format("\033[%dA",1)); //move up 1 line in console
               //System.out.print("\033[2K");
               System.out.print("\033[" + 34 + "C" + "\033[K");
           }
       }
   } // end method getInput
   //
   public double getDoubleInput() {
       while (true) {
           try {
               return input.nextDouble(); // attempt to read a double
           } catch (java.util.InputMismatchException e) {
               input.nextLine(); // Clear the invalid input from the scanner
               System.out.print("Invalid input, please enter a number.");
           }
       }
   }
} // end class Keypad  



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
