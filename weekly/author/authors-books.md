# Feladat

A feladatban szerzőket és általuk írt könyveket kell tárolunk.
Entitások:

* `Author` (id, név, könyvek listája)
* `Book` (id, ISBN szám, cím, szerző)

Mint látható, a két entitás között kétirányú egy-több kapcsolat van. A könyvek táblájában a szerző id-ja külső kulcsként kell hogy szerepeljen.

`Author`-t létre lehet hozni könyv nélkül, könyvet hozzárendelni add metódussal lehet, de figyeljünk hogy a könyv szerzőjét állítsuk be. 

Az repository réteget az `AuthorRepository` reprezentálja, míg az üzleti logikát az `AuthorService`-ben valósítjuk meg. 

Az `AuthorController` alapértelmezetten a `/api/authors` végponton hallgatózik.
* Lehessen a szerzőket lekérdezni, ekkor jelenjenek meg az összes könyvükkel együtt. 
* Lehessen szerzőt létrehozni, ekkor csak a szerző nevét várjuk
* Lehessen szerzőhöz könyvet hozzáadni a `/{id}/books` végponton. Ekkor a könyv ISBN számát és címét várjuk
* Lehessen szerzőt törölni. Ekkor figyeljünk arra, hogy a hozzá tartozó könyvek is törlődjenek. 
	(A törlés id alapján történjen a /api/authors/id url-n) 





