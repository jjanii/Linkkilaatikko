# Janin linkkilaatikko

### Idea 
Sovellukseni ideana on, että kuka tahansa voi luoda oman "linkkilaatikon" jonne voi lisätä talteen hyödyllisiä linkkejä esimerkiksi tärkeää opiskelumateriaalia. Jokaisen linkkilaatikon voi luoda vain kerran samalla nimellä. 

### Käyttöohje
Sovelluksen käyttö on hyvin yksinkertaista, etusivulla on ohjeet sovelluksen käyttöön. Oman linkkilaatikon luominen tapahtuu siirtymällä selaimella osoitteeseen http://nykyinenosoite.com/linkki_laatikkosi_nimi , jolloin luodaan linkki_laatikkosi_nimi niminen linkkilaatikko. Linkkilaatikossa voit lisätä linkkejä, linkin lisääminen vaatii kuvauksen ja nimen antamisen linkille, sekä validin url osoitteen (alkaa http:// jne.). Linkkejä voi myös poistaa klikkaamalla linkin kuvauksen vieressä olevaa x nappia.

### Tarkennuksia
Jokaisella linkkilaatikolla on myös oma vieraskirja, jonne vierailijat voivat jättää viestejä. Viestien ja nimimerkkien pituus on rajoitettu, joten pitkiä viestejä ei pysty lähettämään. Kommentit on järjestetty niin, että uusin on aina ylimpänä. Myös kommentoinnissa nimimerkki sekä kommentti ei voi olla tyhjä.

Koska oletuksena kaikki linkkilaatikot näkyvät etusivulla olevassa listassa, on jokainen linkkilaatikko mahdollista piilottaa etusivun listasta klikkaamalla linkkilaatikon sivulla olevaa piilota nappia. Sivun voi myös palauttaa näkyville klikkaamalla taas näytä nappia.

Sovelluksella on myös adminpaneeli, jossa admin näkee logit ja adminilla on mahdollisuus poistaa jokin tietty linkkilaatikko. Adminpaneelin pääsee käsiksi menemällä osoitteeseen http://nykyinenosoite/admin ja kirjautumalla tunnuksilla admin:admin.

### Tietokanta
Ohjelma käyttää 4 tietokantataulua

|   | Create  |  Read  | Update  |  Delete |
|---|---|---|---|---|
| [Sivu](https://github.com/jjanii/wepa/blob/master/src/main/java/wad/domain/Sivu.java)  |  x | x  |   | x  |
| [Linkki](https://github.com/jjanii/wepa/blob/master/src/main/java/wad/domain/Linkki.java)  | x  | x  |   | x  |
| [Kommentti](https://github.com/jjanii/wepa/blob/master/src/main/java/wad/domain/Kommentt.java)  | x  | x  |   |   |
| [Logi](https://github.com/jjanii/wepa/blob/master/src/main/java/wad/domain/Logi.java)  |  x | x  |   |   |

### Lisäyksiä
Sovellus on mielestäni kattava, ja toteutin kaikki ominaisuudet jotka itse koin tarpeellisiksi. Kuitenkin jatkokehitystä varten esimerkiksi tietyn ip-osoitteen estäminen häiriköinnin tms takia voisi olla suotavaa. Sivutus olisi myös yksi mahdollinen lisäys tulevaisuutta varten, varsinkin logeille koska ne saattavat kasvaa huomattavan kokoisiksi.
