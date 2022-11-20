import code.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.internal.matchers.Or;

import static org.junit.jupiter.api.Assertions.*;

class MeetingTest {
    Organisation org;
    Participant owner, secondParticipant;

    @BeforeEach
    void setUp()
    {
        org = new Organisation("test", new ConsoleInput());
        org.setHierarchy("level1");
        org.setHierarchy("level2");
        owner = org.addPerson("participant", "level1");
    }

    @ParameterizedTest
    @ValueSource(strings = {"12:00", "12.00", "12/00", "12\\00"})
    void testConstructor(String time) {
        var sut = new MeetingTime(time);

        assertEquals("12:00", sut.toString());
    }
    @Test
    void testEmptyConstructorArg() {
        assertThrows(IllegalArgumentException.class,
                () -> new Meeting(null, new Participant("asdf", "level1", org)));
    }

    @Test
    void testEmptyConstructorArg2() {
        assertThrows(IllegalArgumentException.class,
                () -> new Meeting("",  new Participant("asdf", "level1", org)));
    }

    @Test
    void testConstructorArgument_singleArg() {
        assertAll(
                () -> {
                    assertThrows(IllegalArgumentException.class,
                            () -> new Meeting("20-10-2022",  new Participant("asdf", "level1", org)));
                },
                () -> {
                            assertThrows(IllegalArgumentException.class,
                            () -> new Meeting("20-10-2022 12:00",  new Participant("asdf", "level1", org)));
                },
                () -> {
                    assertThrows(IllegalArgumentException.class,
                            () -> new Meeting("20-10-2022 12:00 13:00",  new Participant("asdf", "level1", org)));
                }
        );
    }

    @Test
    void testConstructorArgument_singleChar() {
        assertThrows(IllegalArgumentException.class, () -> new Meeting("s                       ",  new Participant("asdf", "level1", org)));
    }

    @Test
    void testProperConstructorArg() {
        var sut = new Meeting("test descr string 22-11-2022 14:00 16:00", owner);

        assertEquals("test descr string", sut.getDescription());
        assertEquals("22-11-2022", sut.getDate().toString());
        assertEquals("14:00", sut.getStartingTime().toString());
        assertEquals("16:00", sut.getEndingTime().toString());
    }

    @Test
    void testProperConstructorWithExtraSpacesArg() {
        var sut = new Meeting("test descr string   22-11-2022    14:00    16:00    ",  new Participant("asdf", "level1", org));

        assertEquals("test descr string  ", sut.getDescription());
        assertEquals("22-11-2022", sut.getDate().toString());
        assertEquals("14:00", sut.getStartingTime().toString());
        assertEquals("16:00", sut.getEndingTime().toString());
    }

    @Test
    void testFormat() {
        var sut = new Meeting("test descr string 22-11-2022    14:00    16:00    ",  new Participant("asdf", "level1", org));

        assertEquals("Description: test descr string, From: 14:00, To: 16:00", sut.format());
    }

    @Test
    void testChangeAppointmentDate_WithHigherRank() {
        owner = org.addPerson("participant2", "level2");
        secondParticipant = org.addPerson("participant2", "level1");

        var sut = new Meeting("test descr string 22-11-2022 14:00 16:00", owner);

        sut.changeAppointmentDate("23-11-2022", secondParticipant.getRank());

        assertEquals("22-11-2022", sut.getDate().toString());
    }

    @Test
    void testChangeAppointmentStarting_WithHigherRank() {
        owner = org.addPerson("participant2", "level2");
        secondParticipant = org.addPerson("participant2", "level1");

        var sut = new Meeting("test descr string 22-11-2022 14:00 16:00", owner);

        sut.changeAppointmentStartingTime("11:00", secondParticipant.getRank());

        assertEquals("14:00", sut.getStartingTime().toString());
    }

    @Test
    void testChangeAppointmentEndingTime_WithHigherRank() {
        owner = org.addPerson("participant2", "level2");
        secondParticipant = org.addPerson("participant2", "level1");

        var sut = new Meeting("test descr string 22-11-2022 14:00 16:00", owner);

        sut.changeAppointmentEndingTime("15:00", secondParticipant.getRank());

        assertEquals("16:00", sut.getEndingTime().toString());
    }

    @Test
    void testChangeAppointmentDateWithRankHigher() {
        var sut = new Meeting("test descr string 22-11-2022 14:00 16:00", owner);

        sut.changeAppointmentDate("23-11-2022", 2);

        assertEquals("23-11-2022", sut.getDate().toString());
    }

    @Test
    void testChangeAppointmentStartingTimeWithRankHigher() {
        var sut = new Meeting("test descr string 22-11-2022 14:00 16:00", owner);

        sut.changeAppointmentStartingTime("11:00", 2);

        assertEquals("11:00", sut.getStartingTime().toString());
    }

    @Test
    void testChangeAppointmentEndingTimeWithRankHigher() {
        var sut = new Meeting("test descr string 22-11-2022 14:00 16:00", owner);

        sut.changeAppointmentEndingTime("15:00", 2);

        assertEquals("15:00", sut.getEndingTime().toString());
    }

    @Test
    void testChangeAppointmentDateWithRankSame() {
        var sut = new Meeting("test descr string 22-11-2022 14:00 16:00", owner);

        sut.changeAppointmentDate("23-11-2022", 1);

        assertEquals("23-11-2022", sut.getDate().toString());
    }

    @Test
    void testChangeAppointmentStartingTimeWithRankSame() {
        var sut = new Meeting("test descr string 22-11-2022 14:00 16:00", owner);

        sut.changeAppointmentStartingTime("11:00", 1);

        assertEquals("11:00", sut.getStartingTime().toString());
    }

    @Test
    void testChangeAppointmentEndingTimeWithRankSame() {
        var sut = new Meeting("test descr string 22-11-2022 14:00 16:00", owner);

        sut.changeAppointmentEndingTime("15:00", 1);

        assertEquals("15:00", sut.getEndingTime().toString());
    }
}