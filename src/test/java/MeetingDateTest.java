import code.MeetingDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.DateTimeException;

import static org.junit.jupiter.api.Assertions.*;

class MeetingDateTest {
    MeetingDate sut;

    @BeforeEach
    void setSut(){
        sut = new MeetingDate("8-11-2022");
    }

    @ParameterizedTest
    @ValueSource(strings = {"11-12-2022", "11.12.2022", "11/12/2022", "11\\12\\2022"})
    void testConstructor(String date) {
        sut = new MeetingDate(date);

        assertEquals("11-12-2022", sut.toString());
    }

    @Test
    void testWrongTimeFormatConstructorArg1() {
        assertThrows(DateTimeException.class, () -> new MeetingDate("06-22-2022"));
    }

    @Test
    void testToString() {
        assertEquals("08-11-2022", sut.toString());
    }

    @Test
    void testEqualToItself() {
        assertEquals(true, sut.equals(sut));
    }

    @Test
    void testEqualToNull() {
        assertEquals(false, sut.equals(null));
    }

    @Test
    void testEqualToDifferentDate() {
        var temp = new MeetingDate("02-11-2022");

        assertEquals(false, sut.equals(temp));
    }

    @Test
    void testEqualToSameDate() {
        var temp = new MeetingDate("08-11-2022");

        assertEquals(true, sut.equals(temp));
    }

    @Test
    void testGetYear() {
        assertEquals(2022, sut.getYear());
    }

    @Test
    void testGetMonth() {
        assertEquals(11, sut.getMonth());
    }

    @Test
    void testGetDay() {
        assertEquals(8, sut.getDay());
    }

    @Test
    void testCompareTo_itself() {
        sut = new MeetingDate("08-11-2022");
        assertEquals(0, sut.compareTo(sut));
    }
}