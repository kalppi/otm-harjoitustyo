package com.jarnoluu.tetris.domain;

/**
 * Pistelistassa käytetty apuluokka.
 */
public class Score {
    private String name;
    private int score;

    public Score(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}
