package code;

import java.util.ArrayList;

public class Participant {
    private String name;
    private String title;
    private Integer rank = null;
    private MeetingCalendar meetings;
    private ArrayList<Meeting> collabMeetings;
    private ArrayList<Meeting> notifications;
    private ArrayList<String> changesNotifications;
    private Organisation organisation;

    public Participant(String name, String title, Organisation organisation) {
        this.name = name;
        this.title = title;
        if (organisation.getRank(title) != null) {
            rank = organisation.getRank(title);
        } else {
            throw new IllegalArgumentException("There is no such title or hierarchy does not exist");
        }
        this.meetings = new MeetingCalendar(this);
        this.collabMeetings = new ArrayList<>();
        this.notifications = new ArrayList<>();
        this.organisation = organisation;
        this.changesNotifications = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public int getRank() {
        if (rank == null) {
            return 0;
        }
        return rank;
    }

    public MeetingCalendar getMeetingCalendar() {
        return meetings;
    }

    public ArrayList<Meeting> getNotifications() {
        return notifications;
    }

    public ArrayList<Meeting> getCollabMeetings() {
        return collabMeetings;
    }

    public ArrayList<String> getChangesNotifications() {
        return changesNotifications;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public void emptyCollabAppointments() {
        collabMeetings = new ArrayList<>();
    }

    public void emptyNotifications() {
        notifications = new ArrayList<>();
    }

    public void emptyChangesNotifications() {
        changesNotifications = new ArrayList<>();
    }

    public void addAppointment(String appointment) {
        meetings.add(appointment);
    }

    public void addAppointment(Meeting appointment) {
        meetings.add(appointment);
    }

    public void cancelAppointment(Meeting meeting) {
        meetings.cancel(meeting);
    }

    public void addChangeNotification(String s) {
        changesNotifications.add(s);
    }

    public void addCollabMeeting(Meeting s) {
        collabMeetings.add(s);
    }

    public void notification(Meeting notification) {
        notifications.add(notification);

        if (collabMeetings.contains(notification)) {
            collabMeetings.remove(notification);
        }
    }

    @Override
    public String toString() {
        if (title == null) {
            return  "Name: " + name;
        } else {
            return  "Name: " + name + " Title: " + title;
        }
    }
}
