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
        var output = Mockito.mock(ConsoleOutput.class);

        Mockito.when(input.nextLine()).thenReturn("A","test", "level1", "N", "Q");

        var sut = new StartMenu(input, output);

        sut.run();

        assertEquals(1, sut.getOrganisations().size());
        assertEquals("test", sut.getOrganisations().get(0).toString());
    }

    @Test
    void testMenuItem_Cannot_AddOrganisation_With_EmptyName() {
        var input = Mockito.mock(ConsoleInput.class);
        var output = Mockito.mock(ConsoleOutput.class);

        Mockito.when(input.nextLine()).thenReturn("A","", "orgName", "level1", "N", "Q");

        var sut = new StartMenu(input, output);

        sut.run();

        Mockito.verify(output).println("    Error: You can not enter empty name for organisation");

        assertEquals(1, sut.getOrganisations().size());
        assertEquals("orgName", sut.getOrganisations().get(0).toString());
    }

    @Test
    void testMenuItem_Cannot_AddOrganisation_With_EmptyHierarchyName() {
        var input = Mockito.mock(ConsoleInput.class);
        var output = Mockito.mock(ConsoleOutput.class);

        Mockito.when(input.nextLine()).thenReturn("A", "orgName", "", "level1", "N", "Q");

        var sut = new StartMenu(input, output);

        sut.run();

        Mockito.verify(output).println("    Error: you can not enter empty name for hierarchy level.");

        assertEquals(1, sut.getOrganisations().size());
        assertEquals("orgName", sut.getOrganisations().get(0).toString());
    }


    @Test
    void testMenuItem_Add() {
        var input = Mockito.mock(ConsoleInput.class);
        var output = Mockito.mock(ConsoleOutput.class);

        Mockito.when(input.nextLine()).thenReturn("A","test", "level1", "Y", "level2", "N", "Q");

        var sut = new StartMenu(input, output);

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
        var output = Mockito.mock(ConsoleOutput.class);

        Mockito.when(input.nextLine()).thenReturn("A","test", "level1", "Y", "level2", "N",
                "A", "test1", "title1", "Y", "title2", "N", "Q");

        var sut = new StartMenu(input, output);

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
        var output = Mockito.mock(ConsoleOutput.class);

        Mockito.when(input.nextLine()).thenReturn("A","test", "level1", "Y", "level2", "N",
                "A", "test1", "title1", "Y", "title2", "N",
                "R", "1", "Q");
        Mockito.when(input.nextInt()).thenCallRealMethod();

        StartMenu sut = new StartMenu(input, output);

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