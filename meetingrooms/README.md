# Tárgyalók nyilvántartása

Az előkészítő projektmunka azokra a témakörökre fókuszál, melyeket
érdemes átismételni a haladó képzés előtt.

Ezek:

* Többrétegű alkalmazás architektúra: repository (DAO), service és controller réteg
* DAO réteg implementálása `JdbcTemplate` használatával
* Streamek használata
* Tesztesetek írása

## Feladat

Készíts egy programot, mely egy irodaházban lévő tárgyalókat tartja nyilván!
A tárgyalókat fel lehet venni, ki lehet listázni, és lehet keresni.

Egy tárgyalóhoz a következő adatokat kell nyilvántartani:

* Tárgyaló egyedi azonosítója (generált egész szám)
* Tárgyaló neve
* Tárgyaló szélessége méterben
* Tárgyaló hosszúsága méterben

Majd írj ki a felhasználónak egy menüt, a következőképp:

```plain
0. Tárgyaló rögzítése
1. Tárgyalók névsorrendben
2. Tárgyalók név alapján visszafele sorrendben
3. Minden második tárgyaló
4. Területek
5. Keresés pontos név alapján
6. Keresés névtöredék alapján
7. Keresés terület alapján
```

Majd kérj be a felhasználótól egy számot! Hajtsd végre a számhoz tartozó funkciót.

* Tárgyaló rögzítése: kérd be az adatokat, és rögzítsd!
* Tárgyalók sorrendben: csak a neveket kell kiírni névsorrendben. Figyelj a magyar ékezetekre!
* Tárgyalók visszafele sorrendben: csak a neveket kell kiírni fordított névsorrendben.
* Minden második tárgyaló: minden második tárgyaló nevét kell kiírni, névsorrendben.
* Területek: ki kell írni minden tárgyaló nevét, szélességét, hosszúságát és területét, terület alapján csökkenő sorrendben
* Keresés pontos név alapján: kérj be a felhasználótól egy nevet, és annak a tárgyalónak írd ki a szélességét, hosszúságát és területét, melynek ez a neve. Ha nincs ilyen nevű, nem kell kiírni semmit.
* Keresés névtöredék alapján: kérj be a felhasználótól egy névtöredéket, és annak a tárgyalónak írd ki a szélességét, hosszúságát és területét, amelynek a nevében benne van ez a névtöredék! Ne számítson a kis- és nagybetű különbség! Név szerint növekvő sorrendben.
* Keresés terület alapján: kérj be egy egész számot, és csak azoknak a tárgyalóknak írd ki a nevét, szélességét, hosszúságát és területét, melyeknek a területe nagyobb, mint a felhasználó által beírt terület!

### Technológia

Az alkalmazást kétféleképp kell megvalósítani:

* Az első az adatokat tárolja memóriában egy listában!
* A második az adatokat tárolja adatbázisban!

Az alkalmazás többrétegű legyen, legyen egy `MeetingRoomsController`, egy `MeetingRoomsService` és egy `MeetingRoomsRepository`. Az első kettő egy osztály legyen, míg a harmadik egy interfész.
Adatokat csak a controller kérjen be. 
A repository-nak két implementációja van: `InMemoryMeetingRoomsRepository` és egy `MariaDbMeetingRoomsRepository`.

Az adatbázishoz `JdbcTemplate`-et használj! Flyway-jel hozd létre a táblát!

Írjál teszteseteket a service réteghez! Ahhoz, hogy a teszteseteknek ne legyen egymásra hatása, kell
egy külön metódus, mely az adatbázisból kitörli az adatokat. Ezt minden teszteset futása előtt 
meg kell hívni.


