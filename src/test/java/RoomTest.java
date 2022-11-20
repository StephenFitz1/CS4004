import code.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {
    Room sut;

    @BeforeEach
    void setSut() {
        sut = new Room("test");
    }

    @Test
    void testConstructorNullArg() {
        assertThrows(IllegalArgumentException.class, () -> {new Room(null);});
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Projector",
            "Laptop",
            "Whiteboard",
            "Internet connection"
    })
    void testSetRequirementsDefaultRequirements(String requirement) {
        sut.setRequirement(requirement);

        assertEquals(true, sut.getRequirements().get(requirement));
    }

    @Test
    void testAddRequirement() {
        sut.addRequirement("test");

        assertEquals(6, sut.getRequirements().keySet().size());
    }

    @Test
    void testAddRequirementTimeTaken() {
        assertTimeout(Duration.ofMillis(10), () -> {
            sut.addRequirement("test");
        });
    }

    @Test
    void testSetRequirement_ChangeToOriginalState() {
        sut.setRequirement("Projector");
        sut.setRequirement("Projector");

        assertEquals(false, sut.getRequirements().get("Projector"));
    }





    @Test
    void negativeTest_ConstructorWillFailWithEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> new Room(null));
        assertThrows(IllegalArgumentException.class, () -> new Room(""));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            "",
            " ",
            "Multi word parameter",
            "!!!->Bugus@",
            "123"
    })
    void negativeTest_setRequirementWillFail_IfRequirementDoesNotExist(String req) {
        assertThrows(IllegalArgumentException.class, () -> sut.setRequirement(req));
    }

    @Test
    void negativeTest_AddRequirementAlreadyExist() {
        assertThrows(IllegalArgumentException.class, () -> sut.addRequirement("Projector"));
    }
}