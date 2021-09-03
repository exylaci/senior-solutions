## Bank applikáció gyakorló feladat

A feladatod egy olyan egyszerű banki alkalmazás backendjének létrehozása, 
ami képes az egyszerű számlaműveletek kezelésére.

### Bankszámla

Minden bankszámlához el kell menteni:
- a számlatulajdonos nevét
- a számlaszámot (az egyszerűség kedvéért ez lehet egy összesen 8 számjegyből álló azonosító, 
amit kioszthattok sorban a számlák létrehozásakor)
- az egyenleget (egész számban, kezdésnek 0)
- a számla létrehozásának időpontját.

Az alkalmazásnak az alábbi műveleteket kell támogatnia (endpointok):

- számla létrehozása (csak a tulajdonos nevét kell bekérni a felhasználótól, minden mást a szerveren állíts be)
- számlatulajdonos nevének módosítása
- számla törlése (opcionális: legyen csak logikai a törlés)
- összes számla listázása (nevek, bankszámlaszámok és aktuális egyenlegek)
- egy bizonyos számla lekérdezése (név, bankszámlaszám, aktuális egyenleg és tranzakciótörténet)

### Pénzmozgások

Minden számlára be lehet fizetni készpénzt, le lehet venni róla pénzt és át lehet utalni számlák között.
  
Minden pénzmozgást el kell menteni, az alábbi adatokkal:

- érintett számla száma
- egyenleg változása (pozitív vagy negatív előjellel)
- tranzakció típusa (befizetés, levétel, kimenő utalás, bejövő utalás)
- tranzakció időpontja
- tranzakció utáni egyenleg
- átutalás esetén a másik bankszámla számlaszáma

Az alkalmazásnak az alábbi műveleteket kell támogatnia:

- készpénzbefizetés és -levétel (lehet egy vagy két endpointtal is megoldva)
- átutalás indítása
- összes készpénzmozgás listázása
- összes átutalás listázása (opcionális, mert a fenti leírás szerint nem egyszerű összepárosítani az átutalásokat a két számlán)

Készpénzlevételnél és átutalás indításánál ellenőrizni kell, hogy a számlán van-e elegendő pénz, és ha nem, akkor 
`400 BAD REQUEST` válasszal kell visszatérni.
Minden esetben ellenőrizni kell, hogy a számlaszámok léteznek-e, és ha nem, akkor `400 BAD REQUEST` 
válasszal kell visszatérni.

### Technikai feltételek

Az alkalmazást három rétegű architecktúra szerint hozd létre, az adatelérés réteg memóriában tárolja az adatokat.  
Az üzleti logikához tartozó dolgokat (időpontok beállítása, kezdő egyenleg beállítása, egyenleg ellenőrzése, stb) 
a service rétegben hajtsd végre, az adatelérés réteg ténylegesen csak "adatbázis-műveletet" tartalmazzon.  
Az alkalmazáshoz írj unit- és integration teszteket is, valamint controller tesztet is!