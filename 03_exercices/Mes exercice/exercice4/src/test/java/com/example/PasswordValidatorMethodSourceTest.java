package com.example;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PasswordValidatorMethodSourceTest {

    static Stream<Object[]> casDeMotsDePasse() {
        return Stream.of(
                new Object[]{"Password1!", true},
                new Object[]{"Admin2024@", true},
                new Object[]{"short1!", false},
                new Object[]{"PASSWORD1!", false},
                new Object[]{"password1!", false},
                new Object[]{"Password!", false},
                new Object[]{"Password1", false}
        );
    }

    @ParameterizedTest(name = "{index} => motDePasse={0}, résultatAttendu={1}")
    @MethodSource("casDeMotsDePasse")
    void devraitRetournerLeResultatAttenduQuandOnValideLeMotDePasseDepuisUneMethodeSource(String password, boolean resultatAttendu) {
        // Arrange
        PasswordValidator passwordValidator = new PasswordValidator();

        // Act
        boolean result = passwordValidator.isValid(password);

        // Assert
        assertEquals(resultatAttendu, result);
    }
}
