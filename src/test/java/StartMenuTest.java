import code.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class StartMenuTest {
//positive tests
    @Test
    void testMenuItem_AddOrganisation() {
        var input = Mockito.mock(ConsoleInput.class);

        Mockito.when(input.nextLine()).thenReturn("A","test", "level1", "N", "Q");

        var sut = new StartMenu(input);

        sut.run();

        assertEquals(1, sut.getOrganisations().size());
        assertEquals("test", sut.getOrganisations().get(0).toString());
    }

    @Test
    void testMenuItem_Add() {
        var input = Mockito.mock(ConsoleInput.class);

        Mockito.when(input.nextLine()).thenReturn("A","test", "level1", "Y", "level2", "N", "Q");

        var sut = new StartMenu(input);

        sut.run();

        var title = sut.getOrganisations().get(0).getHierarchy();

        assertAll(
                () -> {
                    assertTrue(title.containsValue("level1"));
                },

                () -> {
                    assertTrue(title.containsValue("level2"));
                }
        );
    }

    @Test
    void testMenuItem_AddMultipleOrganisations() {
        var input = Mockito.mock(ConsoleInput.class);

        Mockito.when(input.nextLine()).thenReturn("A","test", "level1", "Y", "level2", "N",
                "A", "test1", "title1", "Y", "title2", "N", "Q");

        var sut = new StartMenu(input);

        sut.run();

        var org1 = sut.getOrganisations().get(0);
        var org2 = sut.getOrganisations().get(1);

        assertAll(
            () ->{
                assertAll(
                    () -> {
                        assertEquals("test", org1.toString());
                    },

                    () -> {
                        assertEquals("test1", org2.toString());
                    }
                );
            },

            () -> {
                    assertAll(
                            () -> {
                                assertTrue(org1.getHierarchy().containsValue("level1"));
                            },
                            () -> {
                                assertTrue(org1.getHierarchy().containsValue("level2"));
                            }
                    );
            },

            () -> {
                    assertAll(
                            () -> {
                                assertTrue(org2.getHierarchy().containsValue("title1"));
                            },
                            () -> {
                                assertTrue(org2.getHierarchy().containsValue("title2"));
                            }
                    );
            }
        );

    }

    @Test
    void testMenuItem_RemoveOrganisation() {
        ConsoleInput input = Mockito.mock(ConsoleInput.class);

        Mockito.when(input.nextLine()).thenReturn("A","test", "level1", "Y", "level2", "N",
                "A", "test1", "title1", "Y", "title2", "N",
                "R", "1", "Q");
        Mockito.when(input.nextInt()).thenCallRealMethod();

        StartMenu sut = new StartMenu(input);

        sut.run();

        assertAll(
                () -> {
                    assertEquals("test1", sut.getOrganisations().get(0).toString());
                },
                () -> {
                    assertEquals(1, sut.getOrganisations().size());
                }
        );
    }


}