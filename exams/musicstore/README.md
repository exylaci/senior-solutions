# Feladat

Ebben a feladatban egy hangszeráruház online webshopalkalmazás backend részét kell megvalósítanod.

Az alap entitás az `Instrument` melynek van egy egyedi azonosítója, egy márkája, egy típusa, egy ára, és egy közzététel dátuma.
Kritériumok:
* A típus enum legyen, melyben a következő értékek lehetnek : `ELECTRIC_GUITAR`, `ACOUSTIC_GUITAR`, `PIANO`
* A közzététel dátuma `LocalDate`

Valósítsd meg a `MusicStoreService` osztályt, mely egy listában tárolja a hangszereket. 
Ez a lista kezdetben üres. Ez az osztály felelős az `id` kiosztásért is, amikor új elem érkezik.

A `MusicController` osztálynak a következő funkciókat kell megvalósítania:

* Alapértelmezetten a `/api/instruments` URL-en várjuk a kéréseket
* Az alapértelmezett URL-en lehessen az összes hangszert lekérdezni. Itt opcionálisan lehessen márkát és/vagy árat megadni. 
  Ilyenkor csak a lekérdezett márkájú, vagy árú vagy a kérésnek megfelelően mindkét tulajdonsággal rendelkező elemek jelenjenek meg.
* Az alapértelmezett URL-en keresztül lehessen új hangszert felvenni. Ekkor csak a márkát, típust és árat várjuk, a dátumot 
  az aznapi dátumra állítsuk be.
* Az alapértelmezett URL-en keresztül lehessen törölni az összes hangszert.
* A `/{id}` URL-en keresztül lehessen lekérdezni egy hangszert. Figyeljünk arra, hogyha nem megfelelő id-t kapunk, 
  akkor `404, not found` státusszal térjünk vissza
* A `/{id}` URL-en keresztül lehessen frissíteni az árat. Ha az ár ugyanaz mint amit már tárolunk, akkor ne történjen semmi, 
  ha az ár más, akkor az árat és a dátumot is frissítsük!
* A `/{id}` URL-en keresztül lehessen törölni az aktuális elemet.

* További kritériumok:
    * Ne lehessen létrehozni elemet meg nem adott márkával és negatív árral.
    * Ne lehessen frissíteni az árat negatív árral.
    * Figyeljünk, hogy a tesztnek megfelelő kritériumokat teljesítsük (url, státusz-kód, stb.)

Jó munkát!
