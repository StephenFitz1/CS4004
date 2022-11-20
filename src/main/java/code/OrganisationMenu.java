package code;

public class OrganisationMenu extends Menu<Participant> {
    private ConsoleInput input;
    private Organisation organisation;

    public OrganisationMenu(ConsoleInput input,
            Organisation organisation) {
        super(input);

        this.input = input;
        this.organisation = organisation;
    }

    public void run() {
        boolean more = true;

        while (more) {
            System.out.println("---> ORGANISATION MENU");
            System.out.println("A)dd person");
            System.out.println("P)ick person");
            System.out.println("R)emove person");
            System.out.println("S)how person");
            System.out.println("L)ist Hierarchy");
            System.out.println("B)ack");
            String input = this.input.nextLine().toUpperCase();

            switch (input) {
                case "A" -> {
                    System.out.println("ADD NEW PERSON");

                    System.out.print("    Enter participant name: ");

                    var name = this.input.nextLine();

                    while(name.isBlank()) {
                        System.out.println("You can not enter empty name.");
                        System.out.print("    Enter participant name: ");
                        name = this.input.nextLine();
                    }

                    System.out.println("Pick title");
                    String title = organisation.pickTitle();

                    organisation.addPerson(name, title);

                    System.out.println();
                    }
                case "P" -> {
                    System.out.println("Pick participant:");

                    var temp = getChoice(organisation.getAllPersons());

                    if (temp != null) {
                        System.out.println();

                        new ParticipantMenu(this.input, temp).run();
                    }

                    System.out.println();
                }
                case "R" -> {
                    System.out.println("Pick participant:");

                    var temp = getChoice(organisation.getAllPersons());

                    if (temp != null) {
                        organisation.removePerson(temp);
                    }

                    System.out.println();
                }
                case "S" -> {
                    System.out.println("SHOW EXISTING PERSONS");
                    show(organisation.getAllPersons());
                    System.out.println();
                }
                case "B" -> {
                    more = false;
                }
                case "L" -> {
                    System.out.println("LIST HIERARCHY");
                    organisation.showHierarchy();
                    System.out.println();
                }
                case "\n" -> {}
                default -> {
                    System.out.println("Something went wrong.");
                }
            }
        }
    }

    public Organisation getOrganisation() {
        return organisation;
    }
}
