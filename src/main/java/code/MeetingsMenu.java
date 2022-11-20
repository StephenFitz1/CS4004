package code;

import java.util.ArrayList;

public class MeetingsMenu extends Menu<Meeting> {
    private ConsoleInput input;
    private MeetingCalendar appointments;
    private ArrayList<Participant> participants;
    private int rankOfParticipant;

    public MeetingsMenu(
            ConsoleInput input,
            MeetingCalendar appointments,
            int rank,
            ArrayList<Participant> participants) {
        super(input);

        this.input = input;
        this.appointments = appointments;
        this.rankOfParticipant = rank;
        this.participants = participants;
    }

    @Override
    public void run() {
        var more = true;

        while (more) {
            System.out.println("---> MEETING MENU");
            System.out.println("C)hange meeting");
            System.out.println("B)ack ");
            String input = this.input.nextLine().toUpperCase();
            System.out.println();

            switch (input) {
                case "C" -> {
                    System.out.println("Enter a date of your appointment (DD-MM-YYYY): ");
                    String date = this.input.nextLine();

                    try {
                        new MeetingDate(date);
                    } catch (Exception e) {
                        date = "1-1-0001";
                    }

                    System.out.println("Pick appointment to change:");
                    Meeting meeting = getChoice(appointments.getMeetingsForDay(date));

                    if (meeting == null) {
                        return;
                    }

                    System.out.println("---> MEETING MODIFICATIONS MENU");
                    System.out.println("A)dd participant");
                    System.out.println("R)emove participant");
                    System.out.println("D)ate");
                    System.out.println("S)tarting time");
                    System.out.println("E)nding time");
                    System.out.println("I) Add room");
                    System.out.println("Q) Change room");
                    System.out.println("B)ack");
                    String fieldToChange = this.input.nextLine().toUpperCase();

                    if (fieldToChange != null) {
                        switch (fieldToChange) {
                            case "A" -> {
                                if (meeting.getRank() <= rankOfParticipant) {
                                    System.out.println("Pick participant to add:");
                                    var temp = getChoiceParticipant(participants, meeting.getOwner());

                                    if (temp != null) {
                                        meeting.addParticipant(temp);
                                    }
                                    System.out.println();
                                } else {
                                    System.out.println("You can not change this appointment.");
                                    System.out.println();
                                }
                            }
                            case "R" -> {
                                if (meeting.getRank() <= rankOfParticipant) {
                                    System.out.println("REMOVE PARTICIPANT");
                                    System.out.println("Pick participant to remove: ");

                                    var temp = getChoiceParticipant(meeting.getParticipants(), meeting.getOwner());

                                    if (temp != null) {
                                        meeting.removeParticipant(temp);
                                    }

                                    System.out.println();
                                } else {
                                    System.out.println("You can not change this appointment.");
                                    System.out.println();
                                }
                            }
                            case "D" -> {
                                System.out.print("Enter new date (dd-mm-yyyy):");
                                var newDate = this.input.nextLine();

                                var result = false;

                                try {
                                    result = meeting.changeAppointmentDate(newDate, rankOfParticipant);

                                    if (result) {
                                        System.out.println("New date was set for this appointment.");
                                    } else {
                                        System.out.println("You can not change this appointment.");
                                    }
                                } catch (Exception exception) {
                                    System.out.println(exception.getMessage());
                                }

                                System.out.println();
                            }
                            case "S" -> {
                                System.out.print("Enter new starting time (hh:mm):");
                                var temp = this.input.nextLine();

                                var result = meeting.changeAppointmentStartingTime(temp, rankOfParticipant);

                                if (result) {
                                    System.out.println("New starting time was set for this appointment.");
                                } else {
                                    System.out.println("You can not change this appointment.");
                                }

                                System.out.println();
                            }
                            case "E" -> {
                                System.out.println("Enter new ending time (hh:mm):");
                                var temp = this.input.nextLine();

                                var result = meeting.changeAppointmentEndingTime(temp, rankOfParticipant);

                                if (result) {
                                    System.out.println("New ending time was set for this appointment.");
                                } else {
                                    System.out.println("You can not change this appointment.");
                                }

                                System.out.println();
                            }
                            case "B" -> {
                                return;
                            }

                            case "I" -> {
                                if (meeting.getRank() <= rankOfParticipant) {
                                    System.out.println("ADD ROOM");
                                    System.out.print("     Enter name of the room: ");
                                    var name = this.input.nextLine();

                                    while (true) {
                                        try {
                                            meeting.addRoom(name);
                                            break;
                                        } catch (Exception ex) {
                                            System.out.println(ex.getMessage());
                                            System.out.print("     Enter name of the room: ");
                                            name = this.input.nextLine();
                                        }
                                    }
                                } else {
                                    System.out.println("You can not change this appointment.");
                                    System.out.println();
                                }
                            }
                            case "Q" -> {
                                if (meeting.getRank() <= rankOfParticipant) {
                                    System.out.println();
                                    new RoomMenu(this.input, meeting.getRoom()).run();
                                    System.out.println();
                                } else {
                                    System.out.println("You can not change this appointment.");
                                    System.out.println();
                                }
                            }
                            default -> {
                                System.out.println("Something went wrong.");
                            }
                        }
                    }
                }
                case "B" -> {
                    return;
                }
                default -> {
                }
            }
        }
    }

    private Participant getChoiceParticipant(ArrayList<Participant> choices, Participant owner) {
        if (choices.size() == 0) {
            return null;
        }

        var skip = -1;

        while (true) {
            System.out.println("    0) Exit form selection");
            int c = 1;
            for (Participant choice : choices) {
                if (choice != owner) {
                    System.out.printf("    %d) %s", c++, choice.toString());
                    System.out.println();
                } else {
                    skip = c;
                    c++;

                    System.out.printf("    Owner) %s", choice.toString());
                    System.out.println();
                }
            }

            System.out.println("(You can not pick owner)");
            System.out.print("Pick a line above: ");

            while (true) {
                int n = -1;

                while (true) {
                    try {
                        n = this.input.nextInt();
                        break;
                    } catch (Exception ex) {
                        System.out.print("Please pick a line above: ");
                    }
                }

                if (n == 0) {
                    return null;
                }

                if (n > 0 && n <= choices.size() && n != skip) {
                    return choices.get(n - 1);
                }

                System.out.print("Please pick a line above: ");
            }
        }
    }
}
