package com.example;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class PasswordValidatorNullAndEmptySourceTest {

    @ParameterizedTest
    @NullAndEmptySource
    void devraitRetournerFalseQuandLeMotDePasseEstNullOuVide(String password) {
        // Arrange
        PasswordValidator passwordValidator = new PasswordValidator();

        // Act
        boolean result = passwordValidator.isValid(password);

        // Assert
        assertFalse(result);
    }
}
