package com.jarnoluu.tetris.domain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Luokka joka pitää muistissa pelin asetuksia, ja voi ladata ne tiedostosta
 */
public class GameConfig {
    private Map<String, String> values = new HashMap();
    
    private GameConfig(Map<String, String> values) {
        this.values = values;
    }
    
    /**
     * Palauttaa asetuksen lukuna.
     * @param name asetuksen nimi
     * @return asetus lukuna
     */
    public int getInt(String name) {
        return Integer.parseInt(this.values.get(name));
    }
    
    /**
     * Palauttaa asetuksen merkkijonona.
     * @param name asetuksen nimi
     * @return asetus merkkijonona
     */
    public String getString(String name) {
        return this.values.get(name);
    }
    
    private static Map<String, String> defaultValues() {
        Map<String, String> values = new HashMap();
        
        values.put("width", "10");
        values.put("height", "20");
        
        return values;
    }
    
    public static GameConfig defaults() {
        return new GameConfig(GameConfig.defaultValues());
    }
    
    public static GameConfig load(String file) {
        Map<String, String> values = GameConfig.defaultValues();
        
        try {
            Path path = Paths.get(file);

            if (Files.exists(path)) {
                List<String> lines = Files.readAllLines(path);
                
                for (String line : lines) {
                    String[] parts = line.split("=", 2);
                    
                    values.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            
        }
        
        return new GameConfig(values);
    }
}
