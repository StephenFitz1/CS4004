import code.MeetingTime;
import org.junit.jupiter.api.*;

import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.*;

class MeetingTimeTest {

    @Test
    void testNullConstructor() {
        assertThrows(NullPointerException.class, () -> new MeetingTime(null));
    }

    @Test
    void testEmptyConstructor() {
        assertThrows(DateTimeParseException.class, () -> new MeetingTime(""));
    }

    @Test
    void testWrongData() {
        assertThrows(DateTimeParseException.class, () -> new MeetingTime("99:98"));
    }

    @Test
    void testWrongFormat() {
        assertThrows(DateTimeParseException.class, () -> new MeetingTime("99/98"));
    }

    @Test
    void testCorrectInputEveningTime() {
        var sut = new MeetingTime("16:07");

        assertEquals(16, sut.getHours());
        assertEquals(7, sut.getMinutes());
    }

    @Test
    void testCorrectInputMorningTime() {
        var sut = new MeetingTime("6:07");

        assertEquals(6, sut.getHours());
        assertEquals(7, sut.getMinutes());
    }

    @Test
    void testCompareTo_itself() {
        var sut = new MeetingTime("16:00");
        assertEquals(0, sut.compareTo(sut));
    }

    @Test
    void testCompareTo_sameTime() {
        var sut = new MeetingTime("16:00");
        var sameTime = new MeetingTime("16:00");
        assertEquals(0, sut.compareTo(sameTime));
    }

    @Test
    void testCompareTo_after() {
        var sut = new MeetingTime("16:00");
        var time = new MeetingTime("17:00");
        assertEquals(-1, sut.compareTo(time));
    }

    @Test
    void testCompareTo_before() {
        var sut = new MeetingTime("16:00");
        var time = new MeetingTime("15:00");
        assertEquals(1, sut.compareTo(time));
    }

    @Test
    void testCompareTo_tootsieWithDifferentiators() {
        var sut = new MeetingTime("16/00");
        assertEquals(0, sut.compareTo(sut));
    }

    @Test
    void testCompareTo_sameTimeWithDifferentiators() {
        var sut = new MeetingTime("16.00");
        var sameTime = new MeetingTime("16)00");
        assertEquals(0, sut.compareTo(sameTime));
    }

    @Test
    void testCompareTo_afterWithDifferentiators() {
        var sut = new MeetingTime("16!00");
        var time = new MeetingTime("17.00");
        assertEquals(-1, sut.compareTo(time));
    }

    @Test
    void testCompareTo_beforeWithDifferentiators() {
        var sut = new MeetingTime("16_00");
        var time = new MeetingTime("15)00");
        assertEquals(1, sut.compareTo(time));
    }
}