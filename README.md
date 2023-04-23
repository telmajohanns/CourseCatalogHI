# Course Catalog HÍ
---

## Sækja gagnagrunn
- Til þess að ná í gagnagrunn þarf að clone-a [þessa slóð](https://github.com/telmajohanns/coursebackend)
- Búa þarf til gagnagrunn í postgreSQL með skipuninni `CREATEDB <nafn>` eða `CREATE DATABASE <nafn>` og tengjast honum
- Búa þarf til `.env` skrá fyrir utan `src` möppu og eftirfarandi þarf að koma fram:
```javascript
DB_USER= //Notandanafnið þitt á postgres
DB_PASSWORD= //Lykilorðið þitt á postgres
DB_HOST=localhost //Vanalega localhost
DB_PORT=5432 //Vanalega 5432
DB_DATABASE= //Nafnið á gagnagrunninum sem þú bjóst til
```
- Síðan þarf að keyra eftirfarandi skipanir í réttri röð:
```javascript
npm install // Til þess að ná í alla þá pakka sem forritið þarf til þess að keyra
npm run setup // Til þess að búa til töflur í gagnagrunninn
npm run dev // Til þess að hafa verkefnið keyrt í bakgrunn meðan appið er notað
```
