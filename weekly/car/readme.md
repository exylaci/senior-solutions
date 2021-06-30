## Feladat

Ebben a feladatban egy használtautó weboldal szolgáltatásait modellezük.

Legyen egy KmState osztály melyben egy dátum és egy aktuális kilométer van.  

Készíts egy `Car` osztály, melynek attribútumai a márkája, azon belül a típusa,
a kora, illetve legyen még egy Enum az állapotáról, mely lehet
kiváló, normális vagy rossz, ezen felül legyen egy KmState Lista.


Legyen egy `CarService` osztály mely legyen már Spring komponens. Ebben legyen a kocsik listája néhány beégetett adattal.

Legyen egy `CarController` osztály mely szintén Spring komponens.

Legyen még egy `HelloController` és `HelloService` osztályod is, mely a kezdő oldal feladatit oldja meg. 

Ezen struktúra segítségével oldd meg a következő feladatokat:
+ Alkalmazás indításakor a böngészőben jelenjen meg az "Üdvölünk az oldalon" szöveg!
+ /cars url-n keresztül jelenjen meg az összes autó.
+ /types végponton keresztül jelenjenek meg a listában található márkák. 


## Tesztelés

+ Készíts unit tesztet a CarService osztály tesztelésre!
+ A CarController osztályt kétféle képpen tesztelted:
    1. Unit teszt ahol a CarService osztályt mockolod
    2. Integrációs teszt ahol beindítod a Springet és úgy teszteled a működést.
