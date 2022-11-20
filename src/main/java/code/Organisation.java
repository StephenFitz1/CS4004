package code;

import java.util.ArrayList;
import java.util.HashMap;

public class Organisation {
    private String name;
    private HashMap<Integer, String> hierarchy;
    private ArrayList<Participant> participants;
    private ConsoleInput input;

    public Organisation() {
        this.hierarchy = new HashMap<>();
        this.participants = new ArrayList<>();
    }

    public Organisation(String name, ConsoleInput input) {
        this();
        this.name = name;
        this.input = input;
    }

    public Integer getRank(String title) {
        if (hierarchy.containsValue(title)) {
            for (Integer i: hierarchy.keySet()) {
                if (hierarchy.get(i).equals(title)) {
                    return i;
                }
            }
        }
        return null;
    }

    public HashMap<Integer, String> getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(String title) {
        if (!hierarchy.containsValue(title)) {
            hierarchy.put(hierarchy.keySet().size(), title);
        } else {
            System.out.println("You already have this title.");
        }
    }

    public void showHierarchy() {
        for (int i = 0; i < hierarchy.keySet().size(); i++) {
            System.out.printf("    %d) Title: %s, Level: %d\n", i+1, hierarchy.get(i), i);
        }
    }

    public ArrayList<Participant> getAllPersons() {
        return participants;
    }

    public Participant addPerson(String name, String title) {
        Participant tempParticipant = new Participant(name, title, this);

        participants.add(tempParticipant);

        return tempParticipant;
    }

    public void removePerson(Participant participant) {
        participants.remove(participant);
    }

    @Override
    public String toString() {
        return name;
    }

    public String pickTitle() {
        showHierarchy();

        System.out.print("Enter index of title: ");

        int title = -1;

        while (title < 0 || title > hierarchy.keySet().size()) {
            while (true) {
                try {
                    title = input.nextInt();
                    break;
                } catch (Exception ex) {
                    System.out.print("Please pick an line above: ");
                }
            }
        }

        return hierarchy.get(title - 1);
    }
}
