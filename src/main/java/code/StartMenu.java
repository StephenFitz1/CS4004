package code;

import java.security.Permissions;
import java.util.ArrayList;

public class StartMenu extends Menu<Organisation> {
    private ArrayList<Organisation> organisations;
    private ConsoleInput input;
    private ConsoleOutput output;

    public StartMenu(ConsoleInput input,
                     ConsoleOutput output) {
        super(input);

        this.input = input;
        this.output = output;
        this.organisations = new ArrayList<>();
    }

    public ArrayList<Organisation> getOrganisations() {
        return organisations;
    }

    public void run() {

        boolean more = true;

        while (more) {
            System.out.println("---> STARTING MENU");
            System.out.println("A)dd organisation");
            System.out.println("P)ick organisation");
            System.out.println("R)emove organisation");
            System.out.println("S)how organisations");
            System.out.println("Q)uit");
            String input = this.input.nextLine().toUpperCase();

            switch (input) {
                case "A" -> {
                    System.out.println("ADD NEW ORGANISATION");
                    System.out.print("    Enter organisation name: ");
                    String name = this.input.nextLine();

                    while (name.isBlank()) {
                        output.println("    Error: You can not enter empty name for organisation");
                        output.print("    Enter organisation name: ");
                        name = this.input.nextLine();
                    }

                    Organisation org = new Organisation(name, this.input);

                    System.out.println("Set-up hierarchy levels (from the bottom up, first entry is lowest level):");
                    while (true) {
                        System.out.print("    Enter hierarchy level name: ");
                        var title = this.input.nextLine();

                        while (title.isBlank()) {
                            output.println("    Error: you can not enter empty name for hierarchy level.");
                            output.print("    Enter level name: ");
                            title = this.input.nextLine();
                        }

                        org.setHierarchy(title);
                        System.out.print("Do you want to add more titles? Y)es or N)o: ");
                        var answer = this.input.nextLine().toUpperCase();

                        while (!answer.equals("Y") && !answer.equals("N")) {
                            System.out.print("Please enter Y)es or N)o: ");
                            answer = this.input.nextLine().toUpperCase();
                        }

                        if (answer.equals("N")) {
                            System.out.println();
                            break;
                        }
                    }

                    organisations.add(org);
                }

                case "P" -> {
                    System.out.println("Pick organisation:");

                    Organisation organisation = getChoice(organisations);

                    if (organisation != null) {
                        System.out.println();

                        new OrganisationMenu(this.input, this.output, organisation).run();
                    }

                    System.out.println();
                }

                case "R" -> {
                    System.out.println("Pick organisation to remove:");

                    Organisation temp = getChoice(organisations);

                    organisations.remove(temp);

                    System.out.println();
                }

                case "S" -> {
                    System.out.println("LIST OF EXISTING ORGANISATIONS");

                    show(organisations);

                    System.out.println();
                }

                case "Q" -> {
                    more = false;
                }

                default -> {
                    System.out.println("Please choose an appropriate command.");
                }
            }
        }
    }
}
