package code;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
   An appointment time.
*/
public class MeetingTime {
   private LocalTime time;

   public MeetingTime(String s) {
      String delimiter = new String(s);
      delimiter = delimiter.replaceAll("[0-9]", "");

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H" + delimiter + "m");
      time = LocalTime.parse(s, formatter);
   }

   public int getMinutes() {
      return time.getMinute();
   }

   public int getHours() {
      return time.getHour();
   }

   @Override
   public String toString() {
      return time.toString();
   }

   public int compareTo(MeetingTime startingTime) {
      return this.time.compareTo(startingTime.time);
   }
}