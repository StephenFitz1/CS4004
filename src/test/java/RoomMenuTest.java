import code.*;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class RoomMenuTest {
    RoomMenu sut;
    ConsoleInput input;
    Room room;

    @BeforeEach
    void setUp() {
        input = Mockito.mock(ConsoleInput.class);
        room = new Room("Test");
        sut = new RoomMenu(input, room);
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
    void testChangeNewRequirement_WrongInput() {
        Mockito.when(input.nextLine()).thenReturn("A", "test", "C", "test1", "test", "B");
        Mockito.when(input.nextInt()).thenCallRealMethod();

        sut.run();

        assertFalse(room.getRequirements().get("test"));
    }
}