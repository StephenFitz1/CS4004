package code;

import java.util.ArrayList;

public class Meeting {
    private String description;
    private MeetingDate date;
    private MeetingTime startingTime;
    private MeetingTime endingTime;
    private ArrayList<Participant> participants;
    private Room room;
    private Participant owner;

    public String getDescription() {
        return description;
    }

    public MeetingDate getDate() {
        return date;
    }

    public MeetingTime getStartingTime() {
        return startingTime;
    }

    public MeetingTime getEndingTime() {
        return endingTime;
    }

    public ArrayList<Participant> getParticipants() {
        return participants;
    }

    public Room getRoom() {
        return room;
    }

    public Participant getOwner() {
        return owner;
    }

    public int getRank() {
        return this.owner.getRank();
    }


    public Meeting(String input, Participant owner) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("Input is empty.");
        }

        StringSegment segment = readLastSegment(input, input.length() - 1);
        if (segment.pos == 0) {
            throw new IllegalArgumentException("Only one parameter has been passed.");
        }
        endingTime = new MeetingTime(segment.text);


        segment = readLastSegment(input, segment.pos);
        if (segment.pos == 0) {
            throw new IllegalArgumentException("Only one parameter has been passed.");
        }
        startingTime = new MeetingTime(segment.text);

        segment = readLastSegment(input, segment.pos);
        if (segment.pos == 0) {
            throw new IllegalArgumentException("Only one parameter has been passed.");
        }
        date = new MeetingDate(segment.text);

        description = input.substring(0, segment.pos);

        participants = new ArrayList<>();

        if (owner == null) {
            throw new IllegalArgumentException("Owner of the meeting has not been set.");
        }

        this.owner = owner;

        checkTime();
    }

    private Meeting(Participant owner, String date, String from, String to) {
        this.owner = owner;
        this.date = new MeetingDate(date);
        this.startingTime = new MeetingTime(from);
        this.endingTime = new MeetingTime(to);
    }

    public String format() {
        return "Description: " + description +
                ", From: " + startingTime +
                ", To: " + endingTime;
    }

    public boolean changeAppointmentDate(String newDate, int rank) {
        if (rank >= this.owner.getRank()) {
            changeAppointmentDate(newDate);

            return true;
        }

        return false;
    }

    public boolean changeAppointmentStartingTime(String newStartingTime, int rank) {
        if (rank >= this.owner.getRank()) {
            changeAppointmentStartingTime(newStartingTime);

            return true;
        }

        return false;
    }

    public boolean changeAppointmentEndingTime(String newEndingTime, int rank) {
        if (rank >= this.owner.getRank()) {
            changeAppointmentEndingTime(newEndingTime);

            return true;
        }

        return false;
    }

    public void addParticipant(Participant participant) {
        for (Participant p: participants) {
            p.addChangeNotification("New participant was added to the meeting");
        }

        participants.add(participant);
        participant.addCollabMeeting(this);
    }

    public void removeParticipant(Participant participant) {
        participants.remove(participant);

        for (Participant p: participants) {
            p.addChangeNotification("Participant was removed from the meeting");
        }

        participant.notification(this);
        participant.cancelAppointment(this);
    }

    @Override
    public String toString() {
        return description + " " +
                date.toString() + " " +
                startingTime.toString() + " " +
                endingTime.toString();
    }

    public void addRoom(String name) {
        room = new Room(name);

        for (Participant p: participants) {
            p.addChangeNotification("Room was added to the meeting");
        }
    }

    private void changeAppointmentDate(String newDate) {
        var tempMeeting = new Meeting(owner, newDate, startingTime.toString(), endingTime.toString());

        if (owner.getMeetingCalendar().overlap(tempMeeting, this)) {
            throw new IllegalArgumentException("You can not change date. Somebody have another meeting at this time on that day.");
        } else {
            for (Participant participant: participants) {
                if (participant.getMeetingCalendar().overlap(tempMeeting, this)) {
                    throw new IllegalArgumentException("You can not change date. Somebody has another meeting at this time on that day.");
                }
            }

            for (Participant p: participants) {
                p.addChangeNotification("Date was changed from " + toString() + "\n" +
                        "to " + newDate);
            }

            owner.addChangeNotification("Date was changed from " + toString() + "\n" +
                    "to " + newDate);

            date = new MeetingDate(newDate);

        }
    }

    private void changeAppointmentStartingTime(String newStartingTime) {
        var tempMeeting = new Meeting(owner, date.toString(), newStartingTime, endingTime.toString());

        if (owner.getMeetingCalendar().overlap(tempMeeting, this)) {
            throw new IllegalArgumentException("You can not change starting time. Somebody has another meeting at this time.");
        } else {
            for (Participant participant: participants) {
                if (participant.getMeetingCalendar().overlap(tempMeeting, this)) {
                    throw new IllegalArgumentException("You can not change starting time. Somebody has another meeting at this time.");
                }
            }

            var format = toString();

            startingTime = new MeetingTime(newStartingTime);
            checkTime();

            for (Participant p: participants) {
                p.addChangeNotification("Starting time was changed from " + format + " to " + startingTime.toString());
            }

            owner.addChangeNotification("Starting time was changed from " + format + " to " + startingTime.toString());
        }
    }

    private void changeAppointmentEndingTime(String newEndingTime) {
        var tempMeeting = new Meeting(owner, date.toString(), startingTime.toString(), newEndingTime);

        if (owner.getMeetingCalendar().overlap(tempMeeting,this)) {
            throw new IllegalArgumentException("You can not change starting time. You have another meeting at this time.");
        } else {
            for (Participant participant: participants) {
                if (participant.getMeetingCalendar().overlap(tempMeeting, this)) {
                    throw new IllegalArgumentException("You can not change starting time. Somebody has another meeting at this time.");
                }
            }

            var format = toString();

            endingTime = new MeetingTime(newEndingTime);
            checkTime();

            for (Participant p: participants) {
                p.addChangeNotification("Ending time was changed from " + format + " to " + endingTime.toString());
            }

            owner.addChangeNotification("Ending time was changed from " + format + " to " + endingTime.toString());
        }
    }

    private void checkTime() {
        if (startingTime.compareTo(endingTime) > 0) {
            var temp = startingTime;
            startingTime = endingTime;
            endingTime = temp;
        }
    }

    private StringSegment readLastSegment(String input, Integer pos) {
        int endPos = pos;
        String segment = "";

        while (pos != 0 && input.charAt(pos) == ' ') {
            pos--;
            endPos--;
        }

        if (pos == 0 && endPos == 0) {
            throw new IllegalArgumentException("Input is empty.");
        }

        while (pos != 0 && input.charAt(pos) != ' ') {
            pos--;
        }


        segment = input.substring(pos + 1, endPos + 1);

        return new StringSegment(segment, pos);
    }

    private class StringSegment {
        public String text;
        public int pos;

        public StringSegment(String text, int pos) {
            this.text = text;
            this.pos = pos;
        }
    }
}