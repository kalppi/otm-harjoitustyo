package com.jarnoluu.tetris.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Pistelista.
 */
public class Highscores {
    private final List<Score> scores = new ArrayList();
    private final int limit;
    
    public Highscores(int limit) {
        this.limit = limit;
    }
    
    private void limit() {
        if (this.scores.size() > this.limit) {
            this.scores.remove(this.scores.size() - 1);
        }
    }
    
    /**
     * Lisää nimen pistelistalle.
     * @param name pelaajan nimi
     * @param score pelaajan pisteet
     */
    public void add(String name, int score) {
        if (this.scores.size() > 0) {
            for (int i = 0; i < this.scores.size(); i++) {
                if (this.scores.get(i).getScore() <= score) {
                    this.scores.add(i, new Score(name, score));
                    this.limit();
                    return;
                }
            }
        }
        
        this.scores.add(new Score(name, score));
        this.limit();
    }
    
    public List<Score> getScores() {
        return this.scores;
    }
}
