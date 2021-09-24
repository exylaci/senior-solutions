# Struktúraváltó résztvevők haladását nyomonkövető alkalmazás

## Vízió

A projektmunkát egy valós igény keltette életre. A Struktúraváltó
projekt során a résztvevők előrehaladásának ellenőrzése, tárolása,
lekérdezése, áttekintése elég körülményes, különböző rendszerekben
történik.

A projektmunka eredményét fel fogjuk használni a következő
évfolyamoknál, tehát egy valós szoftvert fogunk
megvalósítani. Az egyéni ötletek megvalósítására is van lehetőség
a megadottakon felül. A legjobbakat összeválogatjuk, és egy
projektbe másoljuk.

Amennyiben a kötelező részt megcsináltad, a továbbiakról egyeztessünk,
mert még több részlet ki van dolgozva!

Projekt neve legyen `mentor-tools`! Az adatbázis neve, felhasználó, 
jelszó lehet `mentortools`.

## Sprint 1

### Funkcionális követelmények

A következőket kell karbantartani:

* Évfolyamokat (`TrainingClass`)
* Résztvevőket (`Student`), egy résztvevő akár több évfolyamon is szerepelhet
* Tanmeneteket (`Syllabus`)
* A tanmenetekhez modulok (`Module`), ahhoz leckék (`Lesson`) tartoznak
* A tanmenetet évfolyamhoz lehet rendelni
* Be lehessen jelölni, hogy melyik résztvevő melyik leckét dolgozta fel: megnézte a videót, és elkészítette a
  gyakorlati feladatot

A különböző adatokat kell nyilvántartani:

#### Évfolyam

* Elnevezés (nem üres, max. 255 karakter)
* Kezdés dátuma (tetszőleges)
* Befejezés dátuma (tetszőleges, később legyen, mint a kezdés)

Lehet listázni, lekérdezni, létrehozni, mindhárom attribútumot módosítani, törölni.

#### Résztvevők

* Név (nem üres, max. 255 karakter)
* E-mail cím (nem üres, max. 255 karakter)
* GitHub felhasználónév (nem üres, max. 255 karakter)
* Megjegyzés

A résztvevőket is lehet felvenni, lekérdezni, listázni, módosítani és törölni 
az `/api/students` végponton.

#### Évfolyam - résztvevő kapcsolat

Egy résztvevő be tud iratkozni egy tanfolyamra. Ezt a beiratkozás
osztály/tábla tartalmazza (`registration`).

A beiratkozásnak vannak státuszai: aktív (`ACTIVE`), kilépés alatt (`EXIT_IN_PROGRESS`), kilépett (`EXITED`).
Hiszen egy résztvevő egy évfolyamon lehet aktív, míg egy másikon kilépett.

Beiratkozás történhet a `/trainingclasses/{id}/registrations` címen. Meg kell adni a résztvevő azonosítóját.
Itt le lehet kérdezni az évfolyamra beiratkozottakat (a résztvevőkről csak az id-ját, nevét és státuszát adja vissza).

Egy résztvevő beiratkozásait is le lehet kérdezni a `/students/{id}/registrations` címen. 
Csak az évfolyamok id-ját és nevét adja vissza.

Az évfolyam és a résztvevő között nincs közvetlen kapcsolat.

#### Tanmenet

* Név (nem üres, max. 255 karakter)

Egy tanmenet több évfolyamhoz is tartozhat, egy évfolyamhoz egy tanmenet!

Lehet listázni, lekérdezni, létrehozni, minden adatot módosítani, törölni.

A tanmenetet a `/trainingclasses/{id}/syllabus` címen lehet az
évfolyamhoz rendelni.
Amikor létrehozod az évfolyamot, a hozzá tartozó tanmenet még üres.
Ezen a címen lehet POST-tal felvenni, a megfelelő tanmenet id-jának
beküldésével. Módosítani lehet PUT-tal, ekkor másik tanmenetet lehet
hozzá rendelni.

#### Modul

* Cím (nem üres, max. 255 karakter)
* URL (nem üres, max. 255 karakter)

Egy tanmenethez több modul is tartozhat. Egy modul több tanmenethez is
tartozhat. Ne mutasson vissza a modul a tanmenetre.

Lehet listázni, lekérdezni, létrehozni, minden adatot módosítani, törölni.
A `/modules` címen adminisztrálható.

#### Lecke

* Cím (nem üres, max. 255 karakter)
* URL (nem üres, max. 255 karakter)

Egy modulhoz több lecke is tartozhat. A lecke visszahivatkozhat a modulra.

Lehet listázni, lekérdezni, létrehozni, minden adatot módosítani, törölni.

A `/modules/{id}/lessons` címen adminisztrálható.

#### Lecke elvégzése

* Melyik résztvevő
* Melyik leckét
* Videót, gyakorlati feladatot, vagy mindkettőt

Szintén egy kapcsoló entitásra van szükség, ami hivatkozik egy résztvevőre és egy leckére (`LessonCompletion`). 
Valamint az adatai: 

* Videót megnézte-e (nem `boolean`, hanem enum: `COMPLETED`, `NOT_COMPLETED`)
* Gyakorlati feladatot elvégezte-e (nem `boolean`, hanem enum: `COMPLETED`, `NOT_COMPLETED`)
* Elvégzésének dátuma
* Commit URL-je (lehet üres, max. 255 karakter)

Elérhető a `/students/{id}/lessioncompletition` címen.

Lehet listázni, lekérdezni, létrehozni, minden adatot módosítani (kivéve a résztvevőt), törölni.

### Nem-funkcionális követelmények

Klasszikus háromrétegű alkalmazás, MariaDB adatbázissal,
Java Spring backenddel, REST webszolgáltatásokkal.

Követelmények tételesen:

* SQL adatbázis kezelő réteg megvalósítása Spring Data JPA-val (`Repository`)
* Flyway - a scriptek a funkciókkal együtt készüljenek, szóval ahogy bekerül az entitás, úgy kerüljön be egy
  plusz script is, ami a táblát létrehozza
* Üzleti logika réteg megvalósítása `@Service` osztályokkal
* Integrációs tesztek megléte (elég TestRestTemplate tesztek), legalább 80%-os tesztlefedettség
* Controller réteg megvalósítása, RESTful API implementálására. Az API végpontoknak a `/api` címen kell elérhetőeknek lenniük.
* Hibakezelés, validáció
* Swagger felület
* HTTP fájl a teszteléshez
* Dockerfile
* Új repository-ba kell dolgozni, melynek címe `mentor-tools`
* Commitolni legalább entitásonként, és hozzá tartozó REST végpontonként

Cheat sheet: https://github.com/Training360/strukt-val-java-public/blob/master/annotations%20-%20cheat%20sheet.md