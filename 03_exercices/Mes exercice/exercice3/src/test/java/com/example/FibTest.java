package com.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FibTest {

    // --- Range 1 ---

    @Test
    void devraitRetournerUneListeNonVideQuandLeRangeEst1() {
        // Arrange
        Fib fib = new Fib(1);

        // Act
        List<Integer> result = fib.getFibSeries();

        // Assert
        assertFalse(result.isEmpty());
    }

    @Test
    void devraitRetournerLaListe0QuandLeRangeEst1() {
        // Arrange
        Fib fib = new Fib(1);

        // Act
        List<Integer> result = fib.getFibSeries();

        // Assert
        assertEquals(List.of(0), result);
    }

    // --- Range 6 ---

    @Test
    void devraitRetournerUneListeContenantLeChiffre3QuandLeRangeEst6() {
        // Arrange
        Fib fib = new Fib(6);

        // Act
        List<Integer> result = fib.getFibSeries();

        // Assert
        assertTrue(result.contains(3));
    }

    @Test
    void devraitRetourner6ElementsQuandLeRangeEst6() {
        // Arrange
        Fib fib = new Fib(6);

        // Act
        List<Integer> result = fib.getFibSeries();

        // Assert
        assertEquals(6, result.size());
    }

    @Test
    void devraitRetournerUneListeNePasContenirLeChiffre4QuandLeRangeEst6() {
        // Arrange
        Fib fib = new Fib(6);

        // Act
        List<Integer> result = fib.getFibSeries();

        // Assert
        assertFalse(result.contains(4));
    }

    @Test
    void devraitRetournerLaListe0_1_1_2_3_5QuandLeRangeEst6() {
        // Arrange
        Fib fib = new Fib(6);

        // Act
        List<Integer> result = fib.getFibSeries();

        // Assert
        assertEquals(List.of(0, 1, 1, 2, 3, 5), result);
    }

    @Test
    void devraitRetournerUneListeTrieeDeManierAscendanteQuandLeRangeEst6() {
        // Arrange
        Fib fib = new Fib(6);

        // Act
        List<Integer> result = fib.getFibSeries();

        // Assert
        List<Integer> sorted = new ArrayList<>(result);
        Collections.sort(sorted);
        assertEquals(sorted, result);
    }
}
