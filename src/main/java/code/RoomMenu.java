package code;

import java.util.HashMap;

public class RoomMenu extends Menu<String>{
    private Room room;
    private ConsoleInput input;

    public RoomMenu(ConsoleInput input, Room room) {
        super(input);

        this.room = room;
        this.input = input;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("A)dd new requirement,\n" +
                    "C)hange requirements,\n" +
                    "S)how all requirements,\n" +
                    "L)ist requirements used\n"+
                    "B)ack");

            String input = this.input.nextLine().toUpperCase();

            switch (input) {
                case ("A") -> {
                    System.out.print("Enter your requirement: ");
                    String name = this.input.nextLine();

                    while (name.isBlank()) {
                        System.out.println("You entered empty requirement.");
                        System.out.print("Enter your requirement: ");
                        name = this.input.nextLine();
                    }

                    while(true) {
                        try {
                            var result = room.addRequirement(name);

                            if (result) {
                                System.out.println("Your new requirement was add to the room");
                                break;
                            }
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                }

                case ("C") -> {
                    showRequirements(room.getRequirements());

                    String index = "";

                    while (true) {
                        if (room.getRequirements().containsKey(index)) {
                            break;
                        } else {
                            System.out.print("Enter a requirement to change: ");

                            index = this.input.nextLine();
                        }
                    }

                    room.setRequirement(index);
                }

                case ("S") -> {
                    showRequirements(room.getRequirements());
                }
                case ("L") -> {
                    showRequirementsUsed(room);
                }
                case "B" -> {
                    break;
                }
                default -> {
                    System.out.println("Something went wrong.");
                }
            }
        }
    }

    private void showRequirementsUsed(Room room) {
        int index = 1;

        for (String i: room.getRequirements().keySet()) {
            System.out.printf("    %d) %s", index, i);
        }
    }

    public void showRequirements(HashMap<String, Boolean> requirements) {
        int count = 0;
        for (String i: requirements.keySet()) {
            System.out.printf("    %d) %s, ", count, i, (requirements.get(i)) ? "included" : "excluded");
        }
    }
}
