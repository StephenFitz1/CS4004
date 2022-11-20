package code;

import java.io.IOException;
import java.util.Scanner;

/**
   A system to manage appointments.
*/
public class MeetingsSystem
{  
   public static void main(String[] args)
        throws IOException
   {
      ConsoleInput input = new ConsoleInput();
      PasswordManager pwdManager = new PasswordManager();

      System.out.println("Welcome to the Wonderland Software Services.");
      System.out.println("");

      System.out.print("Enter master password: ");

//      var password = input.nextLine();
      var password = "123";

      if (pwdManager.CheckPassword(password)) {
         StartMenu menu = new StartMenu(input);
         menu.run();
      } else {
         System.out.println("Your password is incorrect. Please try again.");
      }
   }
}
