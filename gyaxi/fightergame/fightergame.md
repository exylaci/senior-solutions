## Fighter game

Hozz létre egy alkalmazást, ami egy egyszerű "verekedős" játékot fog szimulálni! 
Az alkalmazásban harcosokat és meccseket lehet rögzíteni.

### Harcosok

Minden harcosnak van:

- id-ja
- neve
- életereje
- sebzése
- pontja (nyert meccsek száma - elveszített meccsek száma)

Hozd létre a `FighterController`-t, amiben az alábbi műveleteket kell támogatnod:

- harcos létrehozása (az id és a pont kivételével minden adatot a felhasználó ad meg)
- összes harcos listázása
- harcos megjelenítése ID alapján
- harcos módosítása (név, életerő és sebzés módosítható)
- harcos törlése

### Meccs

Minden meccset pontosan két harcos vív, akik körönként egyet-egyet ütnek egymásra. 
Ha egy kör végén valamelyik harcosnak elfogyott az életereje, akkor a másik fél nyert. 
Az ütéseket "egyszerre" viszik be a harcosok, így előfordulhat, hogy mindkettejük életereje elfogy ugyanabban 
a körben, ebben az esetben a meccs eredménye döntetlen.  
A meccs végén mindkét harcos életereje visszaáll az eredeti értékre, két meccs között nem visznek át sebzést.

Minden meccshez el van mentve:

- id
- a két harcos neve
- a harc eredménye (első harcos nyert, második harcos nyert, döntetlen)

Hozd létre a `ContestController`-t, amiben az alábbi műveleteket kell támogatnod:

- meccs indítása (várja a két harcos ID-ját, és térjen vissza a lejátszott meccs adataival)
- összes meccs listázása
- meccs keresése ID alapján