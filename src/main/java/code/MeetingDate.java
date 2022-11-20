package code;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
   An appointment date.
*/
public class MeetingDate
{  
   private LocalDate date;

   public MeetingDate(String s) {
       String[] delimiters = s.replaceAll("[0-9]", "").split("");

       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d" + delimiters[0] + "M" + delimiters[1] + "uuuu");

       date = LocalDate.parse(s, formatter);
   }

    public int getYear() {
        return date.getYear();
    }

    public int getMonth() {
        return date.getMonthValue();
    }

    public int getDay() {
        return date.getDayOfMonth();
    }

    @Override
    public String toString() {
        return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public boolean equals(MeetingDate o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        return date.equals(o.date);
    }

    public int compareTo(MeetingDate date) {
        return this.date.compareTo(date.date);
    }
}