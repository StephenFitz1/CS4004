package code;

public class ParticipantMenu extends Menu<Meeting>{
    private Participant participant;
    private ConsoleInput input;

    public ParticipantMenu(ConsoleInput input,
                           Participant participant) {
        super(input);

        this.input = input;
        this.participant = participant;
    }

    @Override
    public void run() {
        boolean more = true;

        if (!participant.getChangesNotification().isEmpty()) {
            int i = 1;
            for (String s: participant.getChangesNotification()) {
                System.out.println(s);
            }
        }

        //Add appointment from other participants.
        if (!participant.getCollabMeetings().isEmpty()) {
            System.out.println("You have new meeting to add");

            for (Meeting meeting : participant.getCollabMeetings()) {
                System.out.println(meeting.toString());
                System.out.print("Do you want to add this meeting? Y)es or N)o: ");

                String answer = input.nextLine().toUpperCase();

                while (!answer.equals("Y") && !answer.equals("N")) {
                    System.out.print("Please enter Y)es or N)o: ");
                    answer = input.nextLine().toUpperCase();
                }

                if (answer.equals("Y")) {
                    participant.addAppointment(meeting);
                } else {
                    meeting.removeParticipant(participant);
                }
            }

            participant.emptyCollabAppointments();
        }

        if (!participant.getNotifications().isEmpty()) {
            System.out.println("You were removed from this appointments:");
            for (Meeting notification: participant.getNotifications()) {
                System.out.println(notification.toString());
            }
            participant.emptyNotifications();
        }

        while (more) {

            System.out.println("---> PERSON MENU");
            System.out.println("A)dd appointment");
            System.out.println("R)emove appointment");
            System.out.println("C)hange appointment");
            System.out.println("S)how");
            System.out.println("B)ack");
            String input = this.input.nextLine().toUpperCase();

            switch (input) {
                case "A" -> {
                    while (true) {
                        System.out.println("ADD NEW MEETING");
                        System.out.print("  Enter your appointment description: ");
                        String description = this.input.nextLine().trim();

                        System.out.print("  Enter your appointment date: ");
                        String date = this.input.nextLine().trim();

                        System.out.print("  Enter your appointment starting time: ");
                        String from = this.input.nextLine().trim();

                        System.out.print("  Enter your appointment ending time: ");
                        String to = this.input.nextLine().trim();

                        String menu = description + " " + date + " " + from + " " + to;

                        try {
                            participant.addAppointment(menu);
                            System.out.println();
                            break;
                        } catch (Exception ex) {
                            System.out.println("You entered incorrect appointment");
                        }
                    }
                }

                case "R" -> {
                    System.out.println("Pick appointment to remove:");
                    Meeting choice = getChoice(participant.getMeetingCalendar().getMeetings());

                    if (choice != null) {
                        participant.cancelAppointment(choice);
                    }

                    System.out.println();
                }

                case "C" -> {
                    System.out.println();

                    new MeetingsMenu(this.input, participant.getMeetingCalendar(),
                            participant.getRank(),
                            participant.getOrganisation().getAllPersons()).run();

                    System.out.println();
                }

                case "S" -> {
                    System.out.println("LIST OF MEETINGS");
                    show(participant.getMeetingCalendar().getMeetings());

                    System.out.println();
                }

                case "B" -> {
                    more = false;
                }

                default -> {
                    System.out.println("Something went wrong.");
                }
            }
        }
    }
}
