import code.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class ParticipantMenuTest {
    ParticipantMenu sut;
    ConsoleInput input;
    Participant participant;
    Organisation org;

    @BeforeEach
    void setSut() {
        input = Mockito.mock(ConsoleInput.class);
        input = Mockito.mock(ConsoleInput.class);
        org = new Organisation("test", input);
        org.setHierarchy("title 1");
        org.setHierarchy("title 2");
        org.addPerson("test", "title 1");
        participant = org.getAllPersons().get(0);
        sut = new ParticipantMenu(input, participant);
    }

    @Test
    void testAddMeeting() {
        Mockito.when(input.nextLine()).thenReturn("A","test meeting", "20-12-2002", "13:00", "15:00", "B");

        sut.run();

        var meeting = participant.getMeetingCalendar().getMeetings().get(0);

        assertEquals("test meeting 20-12-2002 13:00 15:00", meeting.toString());
        assertEquals(1, participant.getMeetingCalendar().getMeetings().size());
    }

    @Test
    void testAddMeeting_WrongInput() {
        Mockito.when(input.nextLine()).thenReturn("A","test meeting", "20-21-2002",
                "13:00", "15:00",
                "test meeting", "20-12-2002",
                "13:00", "15:00",
                "B");

        sut.run();

        var meeting = participant.getMeetingCalendar().getMeetings().get(0);

        assertEquals("test meeting 20-12-2002 13:00 15:00", meeting.toString());
        assertEquals(1, participant.getMeetingCalendar().getMeetings().size());
    }

    @Test
    void testAddedToMeeting() {
        org.addPerson("test1", "title 1");

        var participant = org.getAllPersons().get(1);

        Mockito.when(input.nextLine()).thenReturn(
                "A","test meeting", "20-12-2002", "13:00", "15:00",
                "C", "C", "20-12-2002", "1", "A", "2", "B", "B");
        Mockito.when(input.nextInt()).thenCallRealMethod();

        sut.run();

        var meeting = participant.getCollabMeetings().get(0);

        assertEquals("test meeting 20-12-2002 13:00 15:00", meeting.toString());
        assertEquals(1, participant.getCollabMeetings().size());
    }

    @Test
    void testRemovedFromMeeting() {
        org.addPerson("test1", "title 1");

        var secondParticipant = org.getAllPersons().get(1);

        Mockito.when(input.nextLine()).thenReturn("A","test meeting",
                "20-12-2002", "13:00", "15:00",
                "C", "C",
                "20-12-2002", "1", "A", "2",
                "C", "20-12-2002", "1", "R", "1", "B", "B", "y", "b");
        Mockito.when(input.nextInt()).thenCallRealMethod();

        sut.run();

        var sut1 = new ParticipantMenu(input, secondParticipant);
        sut1.run();

        assertEquals(0, secondParticipant.getCollabMeetings().size());
        assertEquals(0, secondParticipant.getNotifications().size());
        assertEquals(0, secondParticipant.getMeetingCalendar().getMeetings().size());
    }

    @Test
    void testAddedToMeeting_Accept() {
        org.addPerson("test1", "title 1");

        var participant = org.getAllPersons().get(1);

        Mockito.when(input.nextLine()).thenReturn("A", "test meeting", "20-12-2002",
                "13:00", "15:00", "C", "C",
                "20-12-2002", "1", "A", "2", "B", "B", "Y", "B");
        Mockito.when(input.nextInt()).thenCallRealMethod();

        sut.run();

        var sut1 = new ParticipantMenu(input, participant);
        sut1.run();

        assertEquals(0, participant.getCollabMeetings().size());
        assertEquals(0, participant.getNotifications().size());
        assertEquals(1, participant.getMeetingCalendar().getMeetings().size());
    }

    @Test
    void testRemove() {
        Mockito.when(input.nextLine()).thenReturn("A","test meeting", "20-12-2002", "13:00", "15:00", "R", "20-12-2002", "1", "B");
        Mockito.when(input.nextInt()).thenCallRealMethod();

        sut.run();

        assertEquals(0, participant.getMeetingCalendar().getMeetings().size());
    }
}