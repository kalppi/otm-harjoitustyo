package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jarno
 */
public class KassapaateTest {
    Kassapaate paate;
    
    public KassapaateTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        paate = new Kassapaate();
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void luvutAlussaOikein() {
        assertEquals(100000, paate.kassassaRahaa());
        assertEquals(0, paate.edullisiaLounaitaMyyty() + paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kateisostoEdullinenToimii() {
        paate.syoEdullisesti(240);
        
        assertEquals(100240, paate.kassassaRahaa());
    }
    
    @Test
    public void kateisostoEdullinenToimiiVaihtorahaOikein() {
        assertEquals(60, paate.syoEdullisesti(300));
    }
    
    @Test
    public void kateisostoEdullinenToimiiMyyntiKasvaa() {
        paate.syoEdullisesti(300);
        
        assertEquals(1, paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void kateisostoEdullinenJosRahaEiRiita() {
        assertEquals(230, paate.syoEdullisesti(230));
        assertEquals(100000, paate.kassassaRahaa());
        assertEquals(0, paate.edullisiaLounaitaMyyty());
    }
    
    
    @Test
    public void kateisostoMaukasToimii() {
        paate.syoMaukkaasti(400);
        
        assertEquals(100400, paate.kassassaRahaa());
    }
    
    @Test
    public void kateisostoMaukasToimiiVaihtorahaOikein() {
        assertEquals(50, paate.syoMaukkaasti(450));
    }
    
    @Test
    public void kateisostoMaukasToimiiMyyntiKasvaa() {
        paate.syoMaukkaasti(400);
        
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kateisostoMaukasJosRahaEiRiita() {
        assertEquals(300, paate.syoMaukkaasti(300));
        assertEquals(100000, paate.kassassaRahaa());
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void toimiiEdullinenKortilla() {
        Maksukortti kortti = new Maksukortti(500);
        
        assertEquals(true, paate.syoEdullisesti(kortti));
    }
    
    @Test
    public void toimiiEdullinenKortillaMyyntiKasvaa() {
        Maksukortti kortti = new Maksukortti(500);
        paate.syoEdullisesti(kortti);
        
        assertEquals(1, paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void oikeinJosKortillaEiRahaaEdullinen() {
        Maksukortti kortti = new Maksukortti(200);
        
        assertEquals(false, paate.syoEdullisesti(kortti));
        assertEquals(0, paate.edullisiaLounaitaMyyty());
        assertEquals(200, kortti.saldo());
    }
    
    @Test
    public void kassarahaEiMuutuKunOstaaKortillaEdullinen() {
        Maksukortti kortti = new Maksukortti(500);
        paate.syoEdullisesti(kortti);
        
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void toimiiMaukasKortilla() {
        Maksukortti kortti = new Maksukortti(500);
        
        assertEquals(true, paate.syoMaukkaasti(kortti));
    }
    
    @Test
    public void toimiiMaukasKortillaMyyntiKasvaa() {
        Maksukortti kortti = new Maksukortti(500);
        paate.syoMaukkaasti(kortti);
        
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void oikeinJosKortillaEiRahaaMaukas() {
        Maksukortti kortti = new Maksukortti(200);
        
        assertEquals(false, paate.syoMaukkaasti(kortti));
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
        assertEquals(200, kortti.saldo());
    }
    
    @Test
    public void kassarahaEiMuutuKunOstaaKortillaMaukas() {
        Maksukortti kortti = new Maksukortti(500);
        paate.syoMaukkaasti(kortti);
        
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void latausToimii() {
        Maksukortti kortti = new Maksukortti(0);
        
        paate.lataaRahaaKortille(kortti, 100);
        
        assertEquals(100, kortti.saldo());
        assertEquals(100100, paate.kassassaRahaa());
    }
    
    @Test
    public void eiTapahduMitaanJosLataaNegatiivisesti() {
        Maksukortti kortti = new Maksukortti(100);
        
        paate.lataaRahaaKortille(kortti, -100);
        
        assertEquals(100, kortti.saldo());
        assertEquals(100000, paate.kassassaRahaa());
    }
}
