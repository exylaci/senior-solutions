# Képesítővizsga - Guinness rekord app

A feladatok megoldásához az IDEA fejlesztőeszközt használd!
Bármely régebbi osztályt megnyithatsz.

Új repository-ba dolgozz, melynek neve legyen `sv2021-jvjbf-kepesitovizsga`!
Ezen könyvtár tartalmát nyugodtan át lehet másolni (`pom.xml`, tesztek).
GroupId: `training360`, artifactId: `sv2021-jvjbf-kepesitovizsga`. Csomagnév: `training360`.

Először másold át magadhoz a `pom.xml`-t és a teszteseteket, majd commitolj azonnal!
A vizsga végéig bárhányszor commitolhatsz.

Csak a vizsga vége előtt 15 perccel push-olhatsz először, utána push-olhatsz a vizsga végéig bármennyiszer. 

Ha letelik az idő mindenképp pusholj, akkor is
ha nem vagy kész!

## Alkalmazás

Hozz létre egy alkalmazást, amivel rekordereket és világrekordokat lehet kezelni!

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

## Általános elvárások (9 pont)

Három rétegű Spring Boot webalkalmazást készíts!

Adatbázist indíthatsz a következő Docker paranccsal:

```shell
docker run -d -e MYSQL_DATABASE=guinessapp -e MYSQL_USER=guinessapp -e MYSQL_PASSWORD=guinessapp -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -p 3306:3306 --name guinessapp-mariadb mariadb
```

A feladatleírást olvasd el részletesen, és nézd meg az egyes részfeladatokhoz tartozó teszteseteket is, 
hogy milyen inputra mi az elvárt viselkedése az alkalmazásnak! 

### Részpontszámok

- Alkalmazás szerkezeti felépítése, következetes mappa és package szerkezet - 1 pont
- Működő és futtatható Spring Boot webalkalmazás - 3 pont
- Clean code - 5 pont

## Rekorder mentése (9 pont)

### `POST /api/recorders`

A rekorderről az alábbi adatokat kell elmenteni:

- id (`Long`, adatbázis adja ki)
- név (`String`, nem lehet `null`, üres `String` vagy csak whitespace karakter)
- születési idő (`LocalDate`, nem lehet `null`, csak múltbeli lehet)

A kérésben beérkező adatokat a fenti feltételek alapján validáld le, és 
hiba esetén küldj vissza hibaüzenet (a pontos hibaüzeneteket megtalálhatod a vonatkozó teszteseteknél), 
valamint 400-as hibakódot!

Sikeres mentés esetén küldd vissza az elmentett rekorder összes adatát (id-val együtt), és 201-es kódot!

Teszteset: `RecorderSavingIT`

### Részpontok

* A beérkező HTTP kérést az alkalmazás kezeli (controller) - 3 pont
* Az adatok elmentésre kerülnek - 2 pont
* Validálás és hibakezelés - 2 pont
* A válasz tartalmazza a megfelelő adatokat - 2 pont

## Világrekord mentése (12 pont)

### `POST /api/worldrecords`

A világrekordról az alábbi adatokat kell elmenteni:

- id (`Long`, szerver adja ki sorban)
- leírás (`String`, nem lehet `null`, üres `String` vagy csak whitespace karakter)
- érték (`Double`, nem lehet null)
- mértékegység (`String`, nem lehet `null`, üres `String` vagy csak whitespace karakter)
- dátum (`LocalDate`, nem lehet null)
- rekorder id-ja (`Long`, nem lehet `null`)

A kérésben beérkező adatokat a fenti feltételek alapján validáld le, és hiba esetén küldj vissza hibaüzenet 
(a pontos hibaüzeneteket megtalálhatod a vonatkozó teszteseteknél), valamint 400-as hibakódot!

Ha a megadott id-nak megfelelő rekorder nem található a rendszerben, akkor küldj vissza ugyanúgy 400-as hibakódot és hibaüzenetet! 
Ehhez használhatod a validációs hiba formátumát, a pontos hibaüzenetet megtalálhatod a megfelelő tesztesetnél.

Sikeres mentés esetén küldd vissza az elmentett világrekord adatait (id-val együtt), és a rekorder nevét, valamint 201-es státuszkódot! 


### Részpontok

* A beérkező HTTP kérést az alkalmazás kezeli - 3 pont
* Az adatok elmentésre kerülnek - 3 pont
* Egyszerű mezők validálása és hibakezelése - 2 pont
* Nem létező rekorder hiba kezelése - 2 pont
* A válasz tartalmazza a megfelelő adatokat - 2 pont

Teszteset: `WorldRecordSavingIT`

## Rekorder megdönt egy világrekordot (18 pont)

### PUT /api/worldrecords/{id}/beatrecords

A kérés hatására a következő adatok érkeznek:

- URL-ben: rekorder id (`Long`)
  
JSON-ben:

- világrekord id  (`Long`, nem lehet null)
- új rekord értéke (`Double`, nem lehet null)

A kérésben beérkező adatokat a fenti feltételek alapján validáld le, 
és hiba esetén küldj vissza hibaüzenet (a pontos hibaüzeneteket megtalálhatod a vonatkozó teszteseteknél), valamint 400-as hibakódot!

Amennyiben...

- a megadott id-nak megfelelő rekorder nem található a rendszerben,
- vagy a megadott id-nak megfelelő világrekord nem található a rendszerben,
- vagy az új rekord értéke kisebb, mint a megtalált rekord eredeti értéke,

... akkor küldj vissza ugyanúgy 400-as hibakódot és hibaüzenetet! 
Ezekhez használhatod a validációs hibák formátumait, a pontos hibaüzeneteket megtalálhatod a megfelelő teszteseteknél.

Ha minden validáció, és ellenőzés sikeres volt, a világrekodon állíts be az új rekordert, az új rekord értékét, és az aktuális dátumot!

Sikeres módosítás esetén az alkalmazás küldjön vissza egy 200-es státuszkódot, valamint a következő adatokat:

- rekord leírása
- mértékegység
- korábbi rekorder neve
- korábbi rekord értéke
- új rekorder neve
- új rekord értéke
- két rekord különbsége

A megfelelő tesztesetben megtalálod a szükséges elnevezéseket.

### Részpontok

- A beérkező HTTP kérést az alkalmazás kezeli - 3 pont
- Egyszerű mezők validálása és hibakezelése - 2 pont
- Nem létező rekorder hibájának kezelése - 2 pont
- Nem létező világrekord hibájának kezelése - 2 pont
- Új rekord értékének ellenőrzése - 2 pont
- A megfelelő módosítások megtörténnek - 4 pont
- A válasz tartalmazza a megfelelő adatokat - 3 pont

Teszteset: `WorldRecordBeatIT`

## Bizonyos rekorderek listázása (12 pont)

### `GET /api/recorder`

A kérés hatására az alkalmazás kilistáz bizonyos feltételeknek megfelelő rekordereket, 200-as státuszkóddal.

Minden logikát egy darab JPQL segítségével kell végrehajtani!

A lekérdezés feltételei:

- A lekérdezésnek rögtön egy dto listát kell visszaadnia, mely tartalmazza a rekorder nevét és születési idejét.
- Csak azokat a rekordereket adja vissza, melyek neve vagy 'B' betűvel kezdődnek, vagy tartalmaznak 'e' betűt!
- A rekordereket sorrendezve kapjuk vissza, a legfiatalabbtól haladva a legidősebb felé.

### Részpontok

- A beérkező HTTP kérést az alkalmazás kezeli - 3 pont
- A lekérdezés JPQL-t használ - 2 pont
- A lekérdezés dto-vel tér vissza - 2 pont
- A lekérdezés tartalmazza a megfelelő név szűrést - 1 pont
- a lekérdezés megfelelően sorrendezett - 2 pont
- a válasz tartalmazza a megfelelő adatokat - 2 pont

Teszteset: `RecordersListIT`