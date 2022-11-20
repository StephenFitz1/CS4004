import code.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ParticipantTest {
    Participant participant;
    Organisation organisation;

    @BeforeEach
    void setUp() {
        organisation = new Organisation();
        organisation.setHierarchy("Student");
        organisation.setHierarchy("Lecturer");
        participant = new Participant("Steve", "Student", organisation);
    }

    @Test
    void testGetRankWithARank() {
        assertEquals(0, participant.getRank());
    }

    @Test
    void testGetAppointments_noAppointmentsSet() {
        assertTrue(participant.getMeetingCalendar().getMeetings().isEmpty());
    }

    @Test
    void testGetAppointments_withAppointmentsSet() {
        participant.addAppointment("test 14-11-2022 13:00 14:00");
        assertEquals("test 14-11-2022 13:00 14:00", participant.getMeetingCalendar().getMeetings().get(0).toString());
    }

    @Test
    void testGetCollabAppointments() {
        Participant temp = new Participant("test", "Student", organisation);
        temp.addAppointment("test add collaborator 14-11-2022 13:00 14:00");
        Meeting tempAppointment = temp.getMeetingCalendar().getMeetings().get(0);
        tempAppointment.addParticipant(participant);
        assertEquals(tempAppointment.toString(), participant.getCollabMeetings().get(0).toString());
    }

    @Test
    void testEmptyCollabAppointment() {
        Participant temp = new Participant("test", "Student", organisation);
        temp.addAppointment("test add collaborator 14-11-2022 13:00 14:00");
        Meeting tempAppointment = temp.getMeetingCalendar().getMeetings().get(0);
        tempAppointment.addParticipant(participant);
        ArrayList<Meeting> appointmentsList = new ArrayList<>();
        participant.emptyCollabAppointments();
        assertEquals(appointmentsList, participant.getCollabMeetings());
    }

    @Test
    void testGetNotifications() {
        Participant temp = new Participant("test", "Student", organisation);
        temp.addAppointment("test add collaborator 14-11-2022 13:00 14:00");
        Meeting tempAppointment = temp.getMeetingCalendar().getMeetings().get(0);
        tempAppointment.addParticipant(participant);
        tempAppointment.removeParticipant(participant);
        assertEquals(tempAppointment.toString(), participant.getNotifications().get(0).toString());
    }

    @Test
    void testEmptyNotifications() {
        Participant temp = new Participant("test", "Student", organisation);
        temp.addAppointment("test add collaborator 14-11-2022 13:00 14:00");
        Meeting tempAppointment = temp.getMeetingCalendar().getMeetings().get(0);
        tempAppointment.addParticipant(participant);
        tempAppointment.removeParticipant(participant);
        participant.emptyNotifications();
        assertTrue(participant.getNotifications().isEmpty());
    }

    @Test
    void throwsIllegalArgumentExceptionWhenCreatingCollabObj(){
        Organisation organisationTest = new Organisation();
        assertThrows(IllegalArgumentException.class,
                () -> new Participant("Steve", "tester", organisationTest), "Since no rank it should throw exception");
    }
}