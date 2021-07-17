Írjatok a NAV-hoz egy időpontfoglaló rendszert! A `/api/types` címen
le lehessen kérdezni az ügytípusokat, melyek kódok, és hozzá tartozó értékek!
Pl.: 001 - Adóbevallás, 002 - Befizetés, stb. Ezeket egy `NavService`
listájában tárold el (két attribútummal rendelkező objektumok)!

Írjátok meg a controller részét az időpontfoglalásnak! Az `/api/appointments/` címen legyen elérhető.
A következő adatokat várja (command, ellenőrzésekkel):

* adóazonosító jel (CDV ellenőrzés: pontosan tíz számjegyet tartalmaz. Fogni kell az első kilenc számjegyet,
  és megszorozni rendre 1, 2, ..., 9 számmal. Az eredményt kell összegezni,
  majd maradékos osztani 11-el. A 10. számjegynek meg kell egyeznie
  ezzel a számmal (maradékkal).)
* időpont kezdete: jövőbe kell mutatnia
* időpont vége: jövőbe kell mutatnia, az időpont végének a kezdete után kell lennie
* ügytípus azonosítója (service-ben lévő lista alapján kell ellenőrizni)

Trükkök:

* Az időpontoknál két attribútum összefüggését kell vizsgálni. Vagy csinálj belől egy osztályt,
  két attribútummal, és arra tegyél annotációt! Vagy az egész commandra tegyél annotációt!
  (Valós alkalmazásban az első jobb, ez két össztartozó adat, amit jó egyben kezelni, de a többitől
  elválasztva.)
* A ügytípusnál a validátorból kell elérni a service-t. Próbáld az `@Autowired` annotációval field injektálni!