package com.jarnoluu.tetris.domain;

import com.jarnoluu.tetris.dao.IDataObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Pistelista.
 */
public class Highscores implements IDataObject {
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
    
    public int getLimit() {
        return this.limit;
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

    @Override
    public Map<Object, Object> getData() {
        Map<Object, Object> data = new HashMap();
        
        for (Score s : this.scores) {
            data.put(s.getName(), s.getScore());
        }
        
        return data;
    }
    
    public void load(Map<Object, Object> data) {
        for (Map.Entry<Object, Object> e : data.entrySet()) {
            String name = String.valueOf(e.getKey());
            int score = Integer.parseInt(e.getValue().toString());
            
            this.add(name, score);
        }
    }
}
