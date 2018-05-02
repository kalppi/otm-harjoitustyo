# OTM-harjoitustyö: Tetris

Sovellus on perinteinen Tetris peli. Pelaaja liikuttaa erimuotoisia palikoita, ja yrittää täyttää ruudun tyhjiä aukkoja jättämättä.

Sovellus on Helsingin yliopiston Tietojenkäsittelytieteen kurssilla Ohjelmistotekniikan menetelmät toteutettava vapaa-aiheinen projekti.

## Dokumentaatio

[Käyttöohje](dokumentaatio/kayttoohje.md)

[Vaatimusmäärittely](dokumentaatio/vaatimusmaarittely.md)

[Työaikakirjanpito](dokumentaatio/tuntikirjanpito.md)

[Arkkitehtuuri](dokumentaatio/arkkitehtuuri.md)

## Releaset

[Viikko 5](https://github.com/kalppi/otm-harjoitustyo/releases/tag/viikko5)
[Viikko 6](https://github.com/kalppi/otm-harjoitustyo/releases/tag/viikko6)


## Komentorivitoiminnot

### Testaus

Testit suoritetaan komennolla

```
mvn test
```

Testikattavuusraportti luodaan komennolla

```
mvn jacoco:report
```

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto _target/site/jacoco/index.html_

### Suoritettavan jarin generointi

Komento

```
mvn package
```

generoi hakemistoon _target_ suoritettavan jar-tiedoston _Tetris-1.0-SNAPSHOT.jar_

### JavaDoc

JavaDoc generoidaan komennolla

```
mvn javadoc:javadoc
```

JavaDocia voi tarkastella avaamalla selaimella tiedosto _target/site/apidocs/index.html_

### Checkstyle

Tiedostoon [checkstyle.xml](https://github.com/kalppi/otm-harjoitustyo/blob/master/Tetris/checkstyle.xml) määrittelemät tarkistukset suoritetaan komennolla

```
 mvn jxr:jxr checkstyle:checkstyle
```

Mahdolliset virheilmoitukset selviävät avaamalla selaimella tiedosto _target/site/checkstyle.html_
