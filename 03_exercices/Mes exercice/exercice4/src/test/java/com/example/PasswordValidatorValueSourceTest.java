package com.example;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordValidatorValueSourceTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "Password1!",
            "Admin2024@",
            "Secure9#",
            "Hello1@World"
    })
    void devraitRetournerTrueQuandLeMotDePasseRespecteToutesLesRegles(String password) {
        // Arrange
        PasswordValidator passwordValidator = new PasswordValidator();

        // Act
        boolean result = passwordValidator.isValid(password);

        // Assert
        assertTrue(result);
    }
}
