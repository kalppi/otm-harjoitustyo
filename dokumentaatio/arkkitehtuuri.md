# Arkkitehtuurikuvaus

## Rakenne

![rakenne](rakenne.png)

Ohjelmassa on kolme ylimmän tason pakkausta. Pakkaus `com.jarnoluu.tetris.domain` pitää sisällään sovelluslogiikan, sekä pakkauksen `blocks` jossa on palikoiden käsittelyä koskevat luokat. Pakkauksessa `com.jarnoluu.tetris.ui` on JavaFX:llä toteutettu käyttöliittymä, sekä pakkaus `graphics` joka hoitaa ulkoasun piirtämisen tarkemmin. `com.jarnoluu.tetris.dao` hoitaa pistelistan pysyväistallennuksen.

## Käyttöliittymä

Käyttöliittymässä on kolme erilaista näkymää:

- nimen syöttäminen ennen peliä
- pelin pelaaminen
- pelin jälkeinen pistelista

Itse peli ja pelin statistiikkoen piirtäminen on eriytetty täysin pelilogiikasta, ja ne piirretään kahteen JavaFX kanvakseen, jotka annetaan konstruktorissa piirtämisen hoitavalle luokalle.

Nimen syöttäminen on toteutettu luomalle VBox joka sijoitetaan uudeksi tasoksi muiden komponenttien päälle.

## Sovelluslogiikka

![Sovelluslogiikka](sovelluslogiikka.png)

Pelitilanteen ja grafiikoiden jatkuvan päivittämisen hoittaa luokka `GameManager`, joka saa `Game` ja `IGraphics` ilmentymät argumentteina konstruktorissa.

Luokka `Game` hoitaa pelin logiikan ja pelitilanteen etenemisen.

Peliin voisi toteuttaa useita erilaisia graafisia vaihtoehtoja toteuttamalla `IGraphics` rajapinnan, mutta nyt sen on toteuttanut vain yksi luokka `FancyGraphics`.

Luokassa `Game` on kaksi protected-metodia (`findExplosions` ja `freezeBlock`). Normaalisti niitä kutsutaan täysin luokan sisäisesti vain `update` metodista lähtevän kutsuketjun avulla, mutta testauksen helpottamiseksi on tehty kompromissi.

## Tietojen pysyväistallennus

Pakkaus `com.jarnoluu.tetris.dao` hoitaa pelin pistelistan pysyväistallennuksen internetiin. [ei vielä toteutettu]

## Päätoiminnallisuudet

Sekvenssikaaviona pelin aloittaminen:

![Sekvenssikaavio](sekvenssikaavio.png)