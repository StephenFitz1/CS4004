package code;

public class PasswordManager {
    public boolean CheckPassword(String password) {
        //
        // WE HAVE HARDCODED PASSWORD HERE
        // IN THE FUTURE, WE CAN EXTEND THIS METHOD
        // TO READ FROM FILE FOR EXAMPLE.
        //
        return password.equals("123");
    }
}
