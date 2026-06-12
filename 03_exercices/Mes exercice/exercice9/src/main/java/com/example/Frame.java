package com.example;

import java.util.ArrayList;
import java.util.List;

public class Frame {
    private int score;
    private boolean lastFrame;
    private IGenerateur generateur;
    private List<Roll> rolls;

    public Frame(IGenerateur generateur, boolean lastFrame) {
        this.lastFrame = lastFrame;
        this.generateur = generateur;
        this.rolls = new ArrayList<>();
    }

    public boolean makeRoll() {
        if (!canRoll()) {
            return false;
        }
        int maxPins = computeMaxPins();
        int pins = generateur.randomPin(maxPins);
        rolls.add(new Roll(pins));
        score += pins;
        return true;
    }

    private boolean canRoll() {
        int rollCount = rolls.size();
        if (!lastFrame) {
            return rollCount == 0 || (rollCount == 1 && !isStrike());
        }
        if (rollCount >= 3) return false;
        if (rollCount == 2) return isStrike() || isSpare();
        return true;
    }

    private boolean isStrike() {
        return !rolls.isEmpty() && rolls.get(0).getPins() == 10;
    }

    private boolean isSpare() {
        return rolls.size() >= 2
                && rolls.get(0).getPins() < 10
                && rolls.get(0).getPins() + rolls.get(1).getPins() == 10;
    }

    private int computeMaxPins() {
        int rollCount = rolls.size();
        if (rollCount == 0) return 10;
        if (!lastFrame) {
            return 10 - rolls.get(0).getPins();
        }
        if (rollCount == 1) {
            return isStrike() ? 10 : 10 - rolls.get(0).getPins();
        }
        // rollCount == 2, last frame, 3rd roll
        if (isStrike()) {
            int roll2Pins = rolls.get(1).getPins();
            return roll2Pins == 10 ? 10 : 10 - roll2Pins;
        }
        // spare: fresh set of pins
        return 10;
    }

    public int getScore() {
        return score;
    }
}
