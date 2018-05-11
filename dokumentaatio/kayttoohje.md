# Käyttöohje

Lataa tiedosto [tetris.jar](https://github.com/kalppi/otm-harjoitustyo/releases/tag/loppupalautus)

## Konfigurointi

Pelin asetuksia pystyy muuttamaan asetustiedostolla. Ruudun koko on oletuksena 10x20 ja palikoiden koko 15 pikseliä, mutta luomalla pelin juureen tiedoston `config` voidaan niitä muokata esimerkiksi seuraavalla tavalla:

```
width=15
height=15
block-size=10
```

## Ohjelman käyttistäminen

Ohjelma käynnistetään komennolla

```
java -jar tetris.jar
```

## Nimen valinta

Sovellus aukeaa nimen valitsemiseen:

![nimi](nimi.png)

Nimen täytyy olla vähintään kolme merkkiä. Hyväksyminen tapahtuu rivinvaihtoa painamalla.