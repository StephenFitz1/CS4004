package code;

import java.io.IOException;

/**
   A system to manage appointments.
*/
public class MeetingsSystem
{  
   public static void main(String[] args)
        throws IOException
   {
      ConsoleInput input = new ConsoleInput();
      ConsoleOutput output = new ConsoleOutput();
      PasswordManager pwdManager = new PasswordManager();

      System.out.println("Welcome to the Wonderland Software Services.");
      System.out.println("");

      System.out.print("Enter master password: ");

      var password = input.nextLine();

      if (pwdManager.CheckPassword(password)) {
         StartMenu menu = new StartMenu(input, output);
         menu.run();
      } else {
         System.out.println("Your password is incorrect. Please try again.");
      }
   }
}
