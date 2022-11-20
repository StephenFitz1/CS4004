package code;

import java.util.HashMap;

public class Room {
    private String name;
    private HashMap<String, Boolean> requirements = new HashMap<String, Boolean>();

    {
        requirements.put("Projector", false);
        requirements.put("Laptop", false);
        requirements.put("Whiteboard", false);
        requirements.put("Internet connection", false);
        requirements.put("Videoconferencing facilities", false);
    }

    public Room(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("You can not use empty name.");
        }

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setRequirement(String index) {
        if (!requirements.containsKey(index)) {
            throw new IllegalArgumentException("There is no such requirement in the list.");
        }

        if (this.requirements.get(index)) {
            this.requirements.put(index, false);
        } else {
            this.requirements.put(index, true);
        }
    }

    public boolean addRequirement(String requirement) {
        if(requirements.containsKey(requirement)) {
            throw new IllegalArgumentException("This requirement already exists.");
        } else {
            requirements.put(requirement, true);

            return true;
        }
    }

    public HashMap<String, Boolean> getRequirements() {
        return requirements;
    }
}
