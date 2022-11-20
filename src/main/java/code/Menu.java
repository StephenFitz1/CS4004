package code;

import java.util.ArrayList;

public abstract class Menu<T> {
    private ConsoleInput in;

    public Menu(ConsoleInput input) {
        this.in = input;
    }

    public abstract void run();

    protected T getChoice(ArrayList<T> choices) {
        if (choices.isEmpty()) {
            System.out.println("There is nothing to pick from.");
            return null;
        }

        while (true)
        {
            System.out.println("    0) Exit form selection");
            int c = 1;
            for (T choice : choices)
            {
                System.out.printf("    %d) %s", c, choice.toString());
                System.out.println();
                c++;
            }

            System.out.print("Please pick an index above: ");

            while (true) {
                int n = - 1;

                while (true) {
                    try {
                        n = in.nextInt();
                        break;
                    } catch (Exception ex) {
                        System.out.print("Please pick an index above: ");
                    }
                }

                if (n == 0) {
                    return null;
                }

                if (0 < n && n <= choices.size()) {
                    return choices.get(n - 1);
                }

                System.out.print("Please pick an index above: ");
            }
        }
    }

    protected void show(ArrayList<T> choices) {
        int c = 1;
        for (T choice : choices)
        {
            System.out.println("    " + c++ + ") " + choice.toString());
        }
    }
}
