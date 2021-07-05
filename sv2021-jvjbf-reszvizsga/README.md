# Vizsgafeladat

A feladatok megoldásához az IDEA fejlesztőeszközt használd! 
Bármely régebbi osztályt megnyithatsz.

Új repository-ba dolgozz!
Ezen könyvtár tartalmát nyugodtan át lehet másolni (`pom.xml`, tesztek). 
Projekt, könyvtár, repository neve legyen: `sv2021-jvjbf-reszvizsga`. 
GroupId: `training360`, artifactId: `sv2021-jvjbf-reszvizsga`. Csomagnév: `cinema`.

Ha ezzel kész vagy, azonnal commitolj!

Először másold át magadhoz a teszteseteket, majd commitolj azonnal!

A feladatra 3 órád van összesen!

Ha letelik az idő mindenképp commitolj, akkor is
ha nem vagy kész!

## Feladatleírás

A feladatban egy mozi foglalási rendszerét kell megvalósítanod. 

Az alap entitás a film. Minden filmnek van egy azonosítója egy címe, egy pontos időpont, hogy mikor játszák, a férőhelyek maximális száma és a szabad helyek száma.

A `Movie` osztályban továbbá szerepeljen egy metódus ami paraméterül vár egy egész számot, ami a beérkezett foglalás szám. Ha van elég szabad hely akkor paraméter értékét vonjuk le a szabad helyek számából, különben dobjunk IllegalStateExceptiont!

A `MovieService` osztály tárolja egy listában a filmeket. Kezdetben a lista üres. Ez az osztály felelős az egyedi azonosítók kiosztásáért is. 

A `MovieController` osztály alapértelmezzetten a `api/cinema` URL-n várja a kéréseket és a következő funkciókat valósítja meg!

* Le lehet kérdezni az összes filmet, és opcionálisan a film címére is rá lehet szűrni. Ekkor mindig a címet, az időpontot és szabad helyek számát adjuk vissza!

* Lehessen a `/{id}` URL-n id alapján egy filmet elérni. (cím, időpont, szabad_helyek) 

* Az alapértelmezett URL-n lehessen új filmet felvenni. Ekkor a címet az időpontot, és a max helyet adjuk meg. Természetesen új film létrehozásánál a szabad helyek száma a maximális ülőhellyel egyezik meg

* Lehessen egy filmre foglalni a `/{id}/reserve` URL-n kersztül. Ekkor egy számot várunk a törzsben, hogy mennyi ülőhelyet szeretnének foglalni

* Lehessen egy film időpontját frissíteni egy új dátumra. 

* Lehessen törölni az összes filmet. 

* A következő szempontokat vegyük még figyelembe:
	* Új film címe nem lehet üres, és legalább 20 helynek kell lennie rá
	* Ha a megfelelő id-n keresztül nem található a film akkor 404-es státuszkóddal térjünk vissza.
	* Ha több helyet akarunk foglalni mint ahány szabad hely van, akkor ne történjen meg a foglalás. Térjünk vissza 400 BAD_REQUEST státuszkóddal
  

