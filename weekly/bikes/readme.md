# Feladat

A mai feladatban bicikli sharing alkalmazást készítünk.

Adott a bikes.csv állomány, melyben egy-egy bicikli adatai találhatók:
* A bicikli azonosítója
* Az utolsó felhasználó egyedi azonosítója
* Az utolsó leadás pontos ideje
* Az utolsó úton megtett távolság kilométerben

Legyen egy `BikeService` nevű osztályod ami beolvassa a fájlt és eltárolja egy listában.
A beolvasás ne a program indulásakor, hanem az első kérés alkalmával valósuljon meg.
Azaz a listát visszaadó hívás esetén ellenőrizzük, hogyha a lista üres akkor beolvasunk,
ha nem akkor visszaadjuk a listát.

A BikeController osztály a `/history` végponton kersztül érje el a lista minden elemét
minden adattal együtt.

A `/users` végponton keresztül kapjuk meg a userek azonosítóit

Ne felejts el teszteket írni, unit és integrációs tesztet egyaránt!
