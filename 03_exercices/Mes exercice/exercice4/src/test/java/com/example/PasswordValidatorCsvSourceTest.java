package com.example;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PasswordValidatorCsvSourceTest {

    @ParameterizedTest(name = "{index} => motDePasse={0}, résultatAttendu={1}")
    @CsvSource({
            "Password1!, true",
            "Admin2024@, true",
            "short1!, false",
            "PASSWORD1!, false",
            "password1!, false",
            "Password!, false",
            "Password1, false"
    })
    void devraitRetournerLeResultatAttenduQuandOnValideLeMotDePasse(String password, boolean resultatAttendu) {
        // Arrange
        PasswordValidator passwordValidator = new PasswordValidator();

        // Act
        boolean result = passwordValidator.isValid(password);

        // Assert
        assertEquals(resultatAttendu, result);
    }
}
