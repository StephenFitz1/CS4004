import code.ConsoleInput;
import code.Organisation;
import code.OrganisationMenu;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class OrganisationMenuTest {
    OrganisationMenu sut;
    ConsoleInput input;
    Organisation org;

    @BeforeEach
    void setUp() {
        input = Mockito.mock(ConsoleInput.class);
        org = new Organisation("test", input);
        org.setHierarchy("title 1");
        org.setHierarchy("title 2");
        sut = new OrganisationMenu(input, org);
    }
    
    @Test
    void testAddCollaborator() {
        Mockito.when(input.nextLine()).thenReturn("A", "test", "1", "B");
        Mockito.when(input.nextInt()).thenCallRealMethod();

        sut.run();

        var participant = sut.getOrganisation().getAllPersons().get(0);

        assertEquals("Name: test Title: title 1", participant.toString());
    }

    
    @Test
    void testPickCollaborator() {
        Mockito.when(input.nextLine()).thenReturn("A", "test", "1",
                "P", "1", "A", "test meeting", "20-12-2002", "13:00", "15:00", "B", "B");
        Mockito.when(input.nextInt()).thenCallRealMethod();

        sut.run();

        var participant = sut.getOrganisation().getAllPersons().get(0);
        var meeting = participant.getMeetingCalendar().getMeetings().get(0);

        assertEquals("test meeting 20-12-2002 13:00 15:00", meeting.toString());
    }

    
    @Test
    void testRemoveCollaborator() {
        Mockito.when(input.nextLine()).thenReturn("A", "test", "1", "R", "1", "B", "B");
        Mockito.when(input.nextInt()).thenCallRealMethod();

        sut.run();

        var participants = sut.getOrganisation().getAllPersons();

        assertEquals(0, participants.size());
    }

    @Test
    void testRemoveCollaborator_WrongIndex() {
        Mockito.when(input.nextLine()).thenReturn("A", "test", "1", "R", "2", "1", "B");
        Mockito.when(input.nextInt()).thenCallRealMethod();

        sut.run();

        var participants = sut.getOrganisation().getAllPersons();

        assertEquals(0, participants.size());
    }
}