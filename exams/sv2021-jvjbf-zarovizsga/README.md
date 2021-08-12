# Vizsga feladat

A feladatok megoldásához az IDEA fejlesztőeszközt használd!
Bármely régebbi osztályt megnyithatsz.

Új repository-ba dolgozz!
Ezen könyvtár tartalmát nyugodtan át lehet másolni (`pom.xml`, tesztek).
Projekt, könyvtár, repository neve legyen: `sv2021-jvjbf-zarovizsga`.
GroupId: `org.training360`, artifactId: `final-exam`. Csomagnév: `org.training360.finalexam`.

Először másold át magadhoz a teszteseteket, majd commitolj azonnal!

A feladatra 3,5 órád van összesen!

Ha letelik az idő mindenképp commitolj, akkor is
ha nem vagy kész!

A vizsga feladatban egy focibajnokságot nyilvántartó alkalmazást kell megvalósítanod.

## Nem-funkcionális követelmények

Klasszikus háromrétegű alkalmazás, MariaDB adatbázissal,
Java Spring backenddel, REST webszolgáltatásokkal.

Követelmények tételesen:

* SQL adatbázis kezelő réteg megvalósítása Spring Data JPA-val (`Repository`)
* Flyway
* Üzleti logika réteg megvalósítása `@Service` osztályokkal
* Controller réteg megvalósítása, RESTful API implementálására.
* Hibakezelés, validáció
* Hozz létre egy `Dockerfile`-t!

Cheat sheet: https://github.com/Training360/strukt-val-java-public/blob/master/annotations-cheat_sheet.md


## Entitások

* Csapat (id, név, játékosok)
* Játékos (id, név, születési idő, pozíció, csapat)

Az entitások között kétirányú egy-több kapcsolat van, vagyis egy csapatnak lehet több játékosa,
de egy játékos egyszerre csak egy csapatban játszhat. 
A pontos elnevezéseket a tesztben megtalálod.
Lehetséges pozíciók: `GOALKEEPER`, `RIGHT_FULLBACK`, `LEFT_FULLBACK`, `CENTER_BACK`, `DEFENDING_MIDFIELDER`, `RIGHT_WINGER`, `LEFT_WINGER`, `STRIKER`

Táblák neve legyen `teams` és `players`!


## Megvalósítás

A feladat megoldásához a következő funkciókat kell megvalósítanod a megfelelő végpontokon.

* A `PlayerController` a `/api/players` végponton figyel
    * Lehessen lekérdezni az összes adatbázisban szereplő játékost
    * Lehessen felvenni új játékost. (Ekkor bekerül a liga nyilvántartásába, de lehet hogy még egy csapat
      sem igazolta le.) A nevet, születési időt és pozíciót lehet megadni. Ebből a név megadása kötelező.
    * Lehessen törölni egy játékost id alapján a `/api/players/{id}` végponton
    
* A `TeamController` a `/api/teams` végponton figyel
    * Lehessen lekérdezni a csapatokat, az összes játékosukkal együtt
    * Lehessen új csapatot létrehozni. Ehhez csak a csapat nevét kell megadni, ami kötelező.
    * Lehessen egy csapathoz új játékost hozzáadni a `/api/teams/{id}/players` végponton keresztül post metódussal. Ebben az esetben
    egy játékos minden adatát várjuk és úgy adjuk hozzá a csapathoz, hogy egyúttal a játékosok táblába is lementjük
    * Lehessen a meglévő, szabad játékosok közül játékost igazolni a `/api/teams/{id}/players` végponton keresztül put metódussal.
    Ekkor a következő feltételeknek kell teljesülnie:
        * A játékosnak ne legyen csapata
        * A csapatban kevesebb mint kettő ezen a pozíción szereplő játékos legyen (ezt a service rétegben implementáld!)
        * Ha a fenti kettő közül valamelyik nem teljesül, ne történjen meg az igazolás