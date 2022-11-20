package code;

import java.util.Scanner;

public class ConsoleInput {
    private Scanner in = new Scanner(System.in);

    public String nextLine() {
        return in.nextLine();
    }

    public Integer nextInt() {
        Integer output = Integer.parseInt(nextLine());

        return output;
    }
}
