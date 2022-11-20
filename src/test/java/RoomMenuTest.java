import code.*;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class RoomMenuTest {
    RoomMenu sut;
    ConsoleInput input;
    ConsoleOutput output;
    Room room;

    @BeforeEach
    void setUp() {
        input = Mockito.mock(ConsoleInput.class);
        output = Mockito.mock(ConsoleOutput.class);
        room = new Room("Test");
        sut = new RoomMenu(input, output, room);
    }

    @Test
    void testAddNewRequirement() {
        Mockito.when(input.nextLine()).thenReturn("A", "test", "B");
        Mockito.when(input.nextInt()).thenCallRealMethod();

        sut.run();

        assertAll(
                () -> {
                    assertTrue(room.getRequirements().containsKey("test"));
                },
                () -> {
                    assertEquals(6, room.getRequirements().keySet().size());
                }
        );
    }

    @Test
    void testAddNewRequirement_WrongInput() {
        Mockito.when(input.nextLine()).thenReturn("A", "", "test", "B");
        Mockito.when(input.nextInt()).thenCallRealMethod();

        sut.run();

        assertAll(
                () -> {
                    assertTrue(room.getRequirements().containsKey("test"));
                },
                () -> {
                    assertEquals(6, room.getRequirements().keySet().size());
                }
        );
    }

    @Test
    void testChangeExistingRequirement() {
        Mockito.when(input.nextLine()).thenReturn("C", "Projector", "B");
        Mockito.when(input.nextInt()).thenCallRealMethod();

        sut.run();

        assertTrue(room.getRequirements().get("Projector"));
    }

    @Test
    void testChangeNewRequirement() {
        Mockito.when(input.nextLine()).thenReturn("A", "test", "C", "test", "B");
        Mockito.when(input.nextInt()).thenCallRealMethod();

        sut.run();

        assertFalse(room.getRequirements().get("test"));
    }

    @Test
    void testMenuItem_ShowRequirements() {
        Mockito.when(input.nextLine()).thenReturn("S", "B");

        sut.run();

        Mockito.verify(output).printf("    %d) %s, %s", 1, "Internet connection", "excluded");
        Mockito.verify(output).printf("    %d) %s, %s", 2, "Laptop", "excluded");
        Mockito.verify(output).printf("    %d) %s, %s", 3, "Projector", "excluded");
        Mockito.verify(output).printf("    %d) %s, %s", 4, "Videoconferencing facilities", "excluded");
        Mockito.verify(output).printf("    %d) %s, %s", 5, "Whiteboard", "excluded");
    }

    @Test
    void testChangeNewRequirement_WrongInput() {
        Mockito.when(input.nextLine()).thenReturn("A", "test", "C", "test1", "test", "B");
        Mockito.when(input.nextInt()).thenCallRealMethod();

        sut.run();

        assertFalse(room.getRequirements().get("test"));
    }
}