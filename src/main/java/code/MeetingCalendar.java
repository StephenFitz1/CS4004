package code;

import java.util.ArrayList;

/**
 An appointment calendar.
 */
public class MeetingCalendar {
   private ArrayList<Meeting> meetings;
   private Participant owner;

   public MeetingCalendar(Participant owner) {
      this.meetings = new ArrayList<>();
      this.owner = owner;
   }

   public void add(String a) {
      Meeting meeting =  new Meeting(a, owner);

      if (!overlap(meeting)) {
         meetings.add(meeting);
      } else {
         System.out.println("This appointment overlaps with other.");
      }
   }

   public void add(Meeting a) {
      if (!overlap(a)) {
         meetings.add(a);
      } else {
         System.out.println("This appointment overlaps with other.");
      }
   }

   public boolean overlap(Meeting meeting) {
      if (meetings.isEmpty()) {
         return false;
      }

      ArrayList<Meeting> tempList = getMeetingsForDay(meeting.getDate());

      for (Meeting existingMeeting: tempList) {
         if ((meeting.getEndingTime().compareTo(existingMeeting.getStartingTime()) > 0 &&
                 meeting.getStartingTime().compareTo(existingMeeting.getEndingTime()) < 0) ||
                 (meeting.getStartingTime().compareTo(existingMeeting.getStartingTime()) > 1 &&
                         meeting.getStartingTime().compareTo(existingMeeting.getEndingTime()) < 0) ||
                 (meeting.getEndingTime().compareTo(existingMeeting.getEndingTime()) < 0 &&
                         meeting.getEndingTime().compareTo(existingMeeting.getStartingTime()) > 0)) {
            return true;
         }
      }

      return false;
   }

   public ArrayList<Meeting> getMeetingsForDay(MeetingDate day) {
      var selectedAppointment = new ArrayList<Meeting>();

      for (Meeting i: meetings) {
         if (i.getDate().equals(day)) {
            selectedAppointment.add(i);
         }
      }

      return selectedAppointment;
   }

   public ArrayList<Meeting> getMeetingsForDay(String day) {
      return getMeetingsForDay(new MeetingDate(day));
   }

   public ArrayList<Meeting> getMeetings() {
      return meetings;
   }

   public void cancel(Meeting a) {
      meetings.remove(a);
   }
}