import code.*;
import org.junit.jupiter.api.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class MeetingCalendarTest {
    MeetingCalendar sut;

    @BeforeEach
    void setSut() {
        var org= new Organisation("test", new ConsoleInput());
        org.setHierarchy("test");
        org.addPerson("test", "test");
        var owner = org.getAllPersons().get(0);
        sut = new MeetingCalendar(owner);
    }

    @Test
    void testAddSecondAppointment_sameEndingTime() {
        sut.add("test add collaborator 14-11-2022 13:00 14:00");
        sut.add("test add collaborator 14-11-2022 12:00 14:00");

        assertTrue(sut.getMeetings().size() == 1);
    }

    @Test
    void testAddSecondAppointment_sameStartingTime() {
        sut.add("test add collaborator 14-11-2022 13:00 14:00");
        sut.add("test add collaborator 14-11-2022 13:00 15:00");

        assertTrue(sut.getMeetings().size() == 1);
    }

    @Test
    void testAddSecondAppointment_startingTimeBeforeEndingTime() {
        sut.add("test add collaborator 14-11-2022 13:00 14:00");
        sut.add("test add collaborator 14-11-2022 13:30 15:00");

        assertTrue(sut.getMeetings().size() == 1);
    }

    @Test
    void testAddSecondAppointment_sameStartingAdnEndingTime() {
        sut.add("test add collaborator 14-11-2022 13:00 14:00");
        sut.add("test add collaborator 14-11-2022 10:30 13:00");

        assertTrue(sut.getMeetings().size() == 2);
    }

    @Test
    void testAddSecondAppointment_sameEndingAdnStartingTime() {
        sut.add("test add collaborator 14-11-2022 13:00 14:00");
        sut.add("test add collaborator 14-11-2022 14:00 16:00");

        assertTrue(sut.getMeetings().size() == 2);
    }

    @Test
    void testAddSecondAppointment() {
        sut.add("test add collaborator 14-11-2022 13:00 14:00");
        sut.add("test add collaborator 14-11-2022 15:00 16:00");

        assertTrue(sut.getMeetings().size() == 2);
    }

    @Test
    void testAdd_sameEndingAdnStartingTime() {
        sut.add("test add collaborator 14-11-2022 13:00 13:00");

        assertEquals("test add collaborator 14-11-2022 13:00 13:00", sut.getMeetings().get(0).toString());
    }

    @Test
    void testAdd_endingBeforeStartingTime() {
        sut.add("test add collaborator 14-11-2022 13:00 11:00");

        assertEquals("test add collaborator 14-11-2022 11:00 13:00", sut.getMeetings().get(0).toString());
    }

    @Test
    void testAdd() {
        sut.add("test add collaborator 14-11-2022 13:00 14:00");

        assertTrue(sut.getMeetings().get(0).toString().equals("test add collaborator 14-11-2022 13:00 14:00"));
    }

    @Test
    @Timeout(1)
    void testGetAppointmentsForDay() {
        for (int i = 1; i < 12; i++) {
            for (int j = 1; j < 28; j++) {
                sut.add("test " + j + "-" + i + "-2022 12:00 13:00");
            }
        }

        assertTimeout(Duration.ofMillis(1), () -> sut.getMeetingsForDay("11-12-2020"));
    }
}