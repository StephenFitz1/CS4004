import code.ConsoleInput;
import code.Organisation;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class OrganisationTest {
    Organisation sut;
    ConsoleInput input;

    @BeforeEach
    void setUp() {
        input = Mockito.mock(ConsoleInput.class);
        sut = new Organisation("ul", input);
    }

    @Test
    void testSetHierarchy() {
        sut.setHierarchy("student");
        sut.setHierarchy("lecture");

        assertTrue(!sut.getHierarchy().isEmpty());
    }

    @Test
    void testGetHierarchy() {
        sut.setHierarchy("student");
        sut.setHierarchy("lecturer");

        var temp = new HashMap<Integer, String>();

        temp.put(0, "student");
        temp.put(1, "lecturer");

        assertTrue(temp.equals(sut.getHierarchy()));
    }

    @Test
    void testGetRank_NonExistingTitle() {
        sut.setHierarchy("student, lecture");

        assertEquals(null, sut.getRank("test"));
    }

    @Test
    void testGetRank_existingTitle() {
        sut.setHierarchy("student");
        sut.setHierarchy("lecturer");

        assertEquals(0, sut.getRank("student"));
    }

    @Test
    void testAddCollaborator_withoutHierarchy_withTitle() {
        Throwable error = assertThrows(IllegalArgumentException.class,
                () -> sut.addPerson("Boris", "student"));

        assertEquals("There is no such title or hierarchy does not exist", error.getMessage());
    }

    @Test
    void testAddCollaborator_withHierarchy_withoutTitle() {
        sut.setHierarchy("student");

        Throwable error = assertThrows(IllegalArgumentException.class,
                () -> sut.addPerson("Boris", null));

        assertEquals("There is no such title or hierarchy does not exist", error.getMessage());
    }

    @Test
    void testAddCollaborator_withHierarchy_withWrongTitle() {
        sut.setHierarchy("student");

        Throwable error = assertThrows(IllegalArgumentException.class,
                () -> sut.addPerson("Boris", "test"));

        assertEquals("There is no such title or hierarchy does not exist", error.getMessage());
    }

    @Test
    void testAddCollaborator_withHierarchy_withTitle() {
        sut.setHierarchy("student");

        sut.addPerson("Boris", "student");

        assertEquals("Name: Boris Title: student", sut.getAllPersons().get(0).toString());
    }

    @Test
    void testRemoveCollaborator() {
        sut.setHierarchy("student");
        sut.setHierarchy("lecturer");

        sut.addPerson("boris", "student");
        sut.addPerson("test", "lecturer");
        sut.removePerson(sut.getAllPersons().get(1));

        assertEquals("boris", sut.getAllPersons().get(0).getName());
        assertEquals(1, sut.getAllPersons().size());
    }

    @Test
    void testToString() {
        sut = new Organisation("test", input);

        assertEquals("test", sut.toString());
    }

    @ParameterizedTest
    @ValueSource(strings = {"test1,title1,title2"})
    void testPickTitle(String i) {
        String[] titles = i.split(",");
        Mockito.when(input.nextInt()).thenReturn(1,2,3,1,2,3);

        for (int j = 0; j < titles.length; j++) {
            sut.setHierarchy(titles[j]);
        }

        assertAll(
                () -> {
                    assertEquals(titles[0], sut.pickTitle());
                },
                () -> {
                    assertEquals(titles[1],sut.pickTitle());
                },
                () -> {
                    assertEquals(titles[2],sut.pickTitle());
                }
        );
    }
}