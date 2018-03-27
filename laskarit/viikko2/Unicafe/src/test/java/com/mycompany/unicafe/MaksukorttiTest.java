package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void saldoAlussaOikein() {
        assertEquals(10, kortti.saldo());
    }
    
    @Test
    public void lataaminenKasvattaaSaldoa() {
        kortti.lataaRahaa(5);
        
        assertEquals(15, kortti.saldo());
    }
    
    @Test
    public void saldoVahenee() {
        kortti.otaRahaa(3);
        
        assertEquals(7, kortti.saldo());
    }
    
    @Test
    public void saldoEiMuutu() {
        kortti.otaRahaa(12);
        
        assertEquals(10, kortti.saldo());
    }
    
    @Test
    public void palauttaaTrue() {
        assertEquals(true, kortti.otaRahaa(10));
    }
    
    @Test
    public void palauttaaFalse() {
        assertEquals(false, kortti.otaRahaa(11));
    }
    
    @Test
    public void oikeinMerkkijonona() {
        assertEquals("saldo: 0.10", kortti.toString());
    }
}
