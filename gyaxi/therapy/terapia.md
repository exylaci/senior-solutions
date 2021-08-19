## Terápiás foglalkozás szervező

### Domain objektumok

#### Terápiás foglalkozás

Az alkalmazásba fel tudunk venni terápiás foglalkozásokat, az alábbi adatokkal:
- id (int, a szerver osztja ki sorban)
- terapeuta (String)
- helyszín (String)
- időpont (LocalDateTime)

#### Résztvevők

Lehessen jelentkezni terápiás foglalkozásokra, amihez az alábbi adatokat mentjük el:
- id (int, a szerver osztja ki sorban)
- név (String)

A jelentkezőt tehát fel kell venni az adott teráőiás foglalkozás résztvevői közé.

### Feladatok

#### Terápiás foglalkozás mentése

Terápiás foglalkozást lehessen menteni!  

Validálj arra, hogy sem a terapeuta, sem a helyszín nem null, üres vagy csak üres karaktereket tartalmazó String, és hogy az időpont nem null és jövőbeli!

#### Jelentkezés mentése

Jelentkezést lehessen menteni!

Validálj arra, hogy a név nem null, üres vagy üres karaktereket tartalmazó String!

#### Terápiás foglalkozások listázása

Lehessen listázni a terápiás foglalkozásokat! Az adatok között jelenítsd meg az adott foglalkozásra leadott jelentkezéseket is!

#### Terápiás foglalkozás keresése id alapján

Lehessen keresni ID alapján a foglalkozások között!

#### Jelentkezés törlése

Lehessen törölni a jelentkezést egy terápiás foglalkozásra!
Ellenőrizz arra, hogy a kapott jelentkező valóban a kapott terápiás foglalkozásra volt-e feliratkozva, és ha nem, akkor dobj 400 BAD REQUEST hibát a kliens számára!
