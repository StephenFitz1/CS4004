import code.PasswordManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PasswordManagerTest {
    @Test
    void testCheckPassword() {
        assertTrue(new PasswordManager().CheckPassword("123"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"qweq", "1234", "12", "!"})
    void testCheckPassword(String testCase) {
        assertFalse(new PasswordManager().CheckPassword(testCase));
    }
}