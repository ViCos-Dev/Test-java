package com.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordValidatorTest {

    // --- isValid ---

    @Test
    void devraitRetournerTrueQuandLeMotDePasseEstPassword1Exclamation() {
        // Arrange
        PasswordValidator passwordValidator = new PasswordValidator();

        // Act
        boolean result = passwordValidator.isValid("Password1!");

        // Assert
        assertTrue(result);
    }

    @Test
    void devraitRetournerTrueQuandLeMotDePasseEstAdmin2024Arobase() {
        // Arrange
        PasswordValidator passwordValidator = new PasswordValidator();

        // Act
        boolean result = passwordValidator.isValid("Admin2024@");

        // Assert
        assertTrue(result);
    }

    @Test
    void devraitRetournerFalseQuandLeMotDePasseContientMoinsDe8Caracteres() {
        // Arrange
        PasswordValidator passwordValidator = new PasswordValidator();

        // Act
        boolean result = passwordValidator.isValid("short1!");

        // Assert
        assertFalse(result);
    }

    @Test
    void devraitRetournerFalseQuandLeMotDePasseNePasContenirDeMinuscule() {
        // Arrange
        PasswordValidator passwordValidator = new PasswordValidator();

        // Act
        boolean result = passwordValidator.isValid("PASSWORD1!");

        // Assert
        assertFalse(result);
    }

    @Test
    void devraitRetournerFalseQuandLeMotDePasseNePasContenirDeMajuscule() {
        // Arrange
        PasswordValidator passwordValidator = new PasswordValidator();

        // Act
        boolean result = passwordValidator.isValid("password1!");

        // Assert
        assertFalse(result);
    }

    @Test
    void devraitRetournerFalseQuandLeMotDePasseNePasContenirDeChiffre() {
        // Arrange
        PasswordValidator passwordValidator = new PasswordValidator();

        // Act
        boolean result = passwordValidator.isValid("Password!");

        // Assert
        assertFalse(result);
    }

    @Test
    void devraitRetournerFalseQuandLeMotDePasseNePasContenirDeCaractereSpecial() {
        // Arrange
        PasswordValidator passwordValidator = new PasswordValidator();

        // Act
        boolean result = passwordValidator.isValid("Password1");

        // Assert
        assertFalse(result);
    }

    @Test
    void devraitRetournerFalseQuandLeMotDePasseEstNull() {
        // Arrange
        PasswordValidator passwordValidator = new PasswordValidator();

        // Act
        boolean result = passwordValidator.isValid(null);

        // Assert
        assertFalse(result);
    }

    // --- getErrorMessage ---

    @Test
    void devraitRetournerPasswordIsValidQuandLeMotDePasseEstValide() {
        // Arrange
        PasswordValidator passwordValidator = new PasswordValidator();

        // Act
        String result = passwordValidator.getErrorMessage("Password1!");

        // Assert
        assertEquals("Password is valid", result);
    }

    @Test
    void devraitRetournerPasswordMustNotBeNullQuandLeMotDePasseEstNull() {
        // Arrange
        PasswordValidator passwordValidator = new PasswordValidator();

        // Act
        String result = passwordValidator.getErrorMessage(null);

        // Assert
        assertEquals("Password must not be null", result);
    }

    @Test
    void devraitRetournerPasswordMustContain8CharactersQuandLeMotDePasseEstTropCourt() {
        // Arrange
        PasswordValidator passwordValidator = new PasswordValidator();

        // Act
        String result = passwordValidator.getErrorMessage("short1!");

        // Assert
        assertEquals("Password must contain at least 8 characters", result);
    }

    @Test
    void devraitRetournerPasswordMustContainOneLowercaseQuandPasDeMinuscule() {
        // Arrange
        PasswordValidator passwordValidator = new PasswordValidator();

        // Act
        String result = passwordValidator.getErrorMessage("PASSWORD1!");

        // Assert
        assertEquals("Password must contain at least one lowercase letter", result);
    }

    @Test
    void devraitRetournerPasswordMustContainOneUppercaseQuandPasDeMajuscule() {
        // Arrange
        PasswordValidator passwordValidator = new PasswordValidator();

        // Act
        String result = passwordValidator.getErrorMessage("password1!");

        // Assert
        assertEquals("Password must contain at least one uppercase letter", result);
    }

    @Test
    void devraitRetournerPasswordMustContainOneDigitQuandPasDeChiffre() {
        // Arrange
        PasswordValidator passwordValidator = new PasswordValidator();

        // Act
        String result = passwordValidator.getErrorMessage("Password!");

        // Assert
        assertEquals("Password must contain at least one digit", result);
    }

    @Test
    void devraitRetournerPasswordMustContainOneSpecialCharacterQuandPasDeCaractereSpecial() {
        // Arrange
        PasswordValidator passwordValidator = new PasswordValidator();

        // Act
        String result = passwordValidator.getErrorMessage("Password1");

        // Assert
        assertEquals("Password must contain at least one special character", result);
    }
}
