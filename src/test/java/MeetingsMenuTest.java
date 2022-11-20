import code.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class MeetingsMenuTest {
    MeetingsMenu sut;
    ConsoleInput input;
    Organisation organisation;
    Participant participant;
    Participant secondParticipant;

    @BeforeEach
    void serUp() {
        input = Mockito.mock(ConsoleInput.class);
        organisation = new Organisation("test", input);

        organisation.setHierarchy("level 1");
        organisation.setHierarchy("level 2");

        participant = organisation.addPerson("participant 1", "level 2");
        secondParticipant = organisation.addPerson("participant 2", "level 1");

        participant.addAppointment("test appointment 11.11.1111 12.00 13.00");

        sut = new MeetingsMenu(input, participant.getMeetingCalendar(),
                participant.getRank(), organisation.getAllPersons());
    }

    @Test
    void testChangeDatePersonalMeeting() {
        Mockito.when(input.nextLine()).thenReturn("C", "11.11.1111", "1", "D", "12.11.1111", "B");
        Mockito.when(input.nextInt()).thenCallRealMethod();

        sut.run();

        assertEquals("12-11-1111", participant.getMeetingCalendar().getMeetings().get(0).getDate().toString());
    }

    @Test
    void testChangeDateWrongTypePersonalMeeting() {
        Mockito.when(input.nextLine()).thenReturn("C", "11.11.1111", "1", "D", "1a2.11.1111", "B");
        Mockito.when(input.nextInt()).thenCallRealMethod();

        sut.run();

        assertEquals("11-11-1111", participant.getMeetingCalendar().getMeetings().get(0).getDate().toString());
    }

    @Test
    void testChangeStartingTimePersonalMeeting() {
        Mockito.when(input.nextLine()).thenReturn("C", "11.11.1111", "1", "S", "10.00", "B");
        Mockito.when(input.nextInt()).thenCallRealMethod();

        sut.run();

        assertEquals("10:00",
                participant.getMeetingCalendar().getMeetings().get(0).getStartingTime().toString());
    }

    @Test
    void testChangeEndingTimePersonalMeeting() {
        Mockito.when(input.nextLine()).thenReturn("C", "11.11.1111", "1", "E", "17.00", "B");
        Mockito.when(input.nextInt()).thenCallRealMethod();

        sut.run();

        assertEquals("17:00",
                participant.getMeetingCalendar().getMeetings().get(0).getEndingTime().toString());
    }

    @Test
    void testAddRoomPersonalMeeting() {
        Mockito.when(input.nextLine()).thenReturn("C", "11.11.1111", "1", "I", "Test", "B");
        Mockito.when(input.nextInt()).thenCallRealMethod();

        sut.run();

        assertEquals("Test",
                participant.getMeetingCalendar().getMeetings().get(0).getRoom().getName());
    }

    @Test
    void testAddRoomWithEmptyNamePersonalMeeting() {
        Mockito.when(input.nextLine()).thenReturn("C", "11.11.1111", "1", "I", "", "Test", "B");
        Mockito.when(input.nextInt()).thenCallRealMethod();

        sut.run();

        assertEquals("Test",
                participant.getMeetingCalendar().getMeetings().get(0).getRoom().getName());
    }

    @Test
    void testChangeRoomPersonalMeeting() {
        Mockito.when(input.nextLine()).thenReturn("C", "11.11.1111", "1", "I", "Test", "C", "11.11.1111", "1", "Q", "B");
        Mockito.when(input.nextInt()).thenCallRealMethod();

        sut.run();

        assertEquals("Test",
                participant.getMeetingCalendar().getMeetings().get(0).getRoom().getName());
    }

    @Test
    void testAddParticipantToMeeting() {
        Mockito.when(input.nextLine()).thenReturn("C", "11.11.1111", "1", "A", "2", "B");
        Mockito.when(input.nextInt()).thenCallRealMethod();

        sut.run();

        assertAll(
                () -> {
                    assertEquals(1, secondParticipant.getCollabMeetings().size());
                },
                () -> {
                    assertEquals("test appointment 11-11-1111 12:00 13:00",
                            secondParticipant.getCollabMeetings().get(0).toString());
                }
        );
    }

    @Test
    void testAddParticipantWithWrongIndex() {
        Mockito.when(input.nextLine()).thenReturn("C", "11.11.1111", "1", "A", "3", "1", "0", "B");
        Mockito.when(input.nextInt()).thenCallRealMethod();

        sut.run();

        assertAll(
                () -> {
                    assertTrue(participant.getCollabMeetings().isEmpty());
                },
                () -> {
                    assertTrue(secondParticipant.getCollabMeetings().isEmpty());
                }
        );
    }

    @Test
    void testEnterStringInsteadOfDate() {
        Mockito.when(input.nextLine()).thenReturn("C", "1a1.11.1111", "B");
        Mockito.when(input.nextInt()).thenCallRealMethod();

        sut.run();

        assertAll(
                () -> {
                    assertTrue(participant.getCollabMeetings().isEmpty());
                },
                () -> {
                    assertTrue(secondParticipant.getCollabMeetings().isEmpty());
                }
        );
    }

    @Test
    void testChangeCollabMeetingDate_withLowerTitle() {
        Mockito.when(input.nextLine()).thenReturn("C", "11.11.1111", "1", "A", "2", "B",
                "C", "11.11.1111", "1", "D", "12.11.1111", "B");
        Mockito.when(input.nextInt()).thenCallRealMethod();

        sut.run();

        sut = new MeetingsMenu(input, participant.getMeetingCalendar(),
                secondParticipant.getRank(), organisation.getAllPersons());

        sut.run();

        assertEquals("11-11-1111",
                participant.getMeetingCalendar().getMeetings().get(0).getDate().toString());
    }

    @Test
    void testChangeCollabMeetingStartingTime_withLowerTitle() {
        Mockito.when(input.nextLine()).thenReturn("C", "11.11.1111", "1", "A", "2", "B",
                "C", "11.11.1111", "1", "S", "10.00", "B");
        Mockito.when(input.nextInt()).thenCallRealMethod();

        sut.run();

        sut = new MeetingsMenu(input, participant.getMeetingCalendar(),
                secondParticipant.getRank(), organisation.getAllPersons());

        sut.run();

        assertEquals("12:00",
                participant.getMeetingCalendar().getMeetings().get(0).getStartingTime().toString());
    }

    @Test
    void testChangeCollabMeetingEndingTime_withLowerTitle() {
        Mockito.when(input.nextLine()).thenReturn("C", "11.11.1111", "1", "A", "2", "B",
                "C", "11.11.1111", "1", "E", "17.00", "B");
        Mockito.when(input.nextInt()).thenCallRealMethod();

        sut.run();

        sut = new MeetingsMenu(input, participant.getMeetingCalendar(),
                secondParticipant.getRank(), organisation.getAllPersons());

        sut.run();

        assertEquals("13:00",
                participant.getMeetingCalendar().getMeetings().get(0).getEndingTime().toString());
    }

    @Test
    void testAddRoomToCollabMeeting_withLowerTitle() {
        Mockito.when(input.nextLine()).thenReturn("C", "11.11.1111", "1", "A", "2", "B",
                "C", "11.11.1111", "1", "I", "B");
        Mockito.when(input.nextInt()).thenCallRealMethod();

        sut.run();

        sut = new MeetingsMenu(input, participant.getMeetingCalendar(),
                secondParticipant.getRank(), organisation.getAllPersons());

        sut.run();
    }

    @Test
    void testChangeRoomOfCollabMeeting_withLowerTitle() {
        Mockito.when(input.nextLine()).thenReturn("C", "11.11.1111", "1", "A", "2", "B",
                "C", "11.11.1111", "1", "Q", "B");
        Mockito.when(input.nextInt()).thenCallRealMethod();

        sut.run();

        sut = new MeetingsMenu(input, participant.getMeetingCalendar(),
                secondParticipant.getRank(), organisation.getAllPersons());

        sut.run();
    }

    @Test
    void testRemoveParticipantFromCollabMeeting_withLowerTitle() {
        Mockito.when(input.nextLine()).thenReturn("C", "11.11.1111", "1", "A", "2", "B",
                "C", "11.11.1111", "1", "R", "B");
        Mockito.when(input.nextInt()).thenCallRealMethod();

        sut.run();

        sut = new MeetingsMenu(input, participant.getMeetingCalendar(),
                secondParticipant.getRank(), organisation.getAllPersons());

        sut.run();

        assertEquals(1,
                participant.getMeetingCalendar().getMeetings().get(0).getParticipants().size());
    }

}