// TactiCore — MongoDB Örnek Veri
// Çalıştır: mongosh "mongodb://localhost:27017/tacticore" seed.js

db = db.getSiblingDB('tacticore');
['teams','players','matches','match_events','standings','news'].forEach(c => { try { db.getCollection(c).drop(); } catch(e) {} });

const NOW = new Date('2026-05-08T10:00:00Z');
// Week 33 = SEASON_START + 32*7 days → SEASON_START = 2025-09-26
const SEASON_START = new Date('2025-09-26T20:00:00Z');

function pseudo(n, max) {
    const x = Math.sin(n * 9301 + 49297) * 233280;
    return Math.floor((x - Math.floor(x)) * (max || 1));
}

// ============================================================
//  TEAMS
// ============================================================
const T = {
    gal:new ObjectId(), fen:new ObjectId(), bjk:new ObjectId(), ts:new ObjectId(),
    ibfk:new ObjectId(), svs:new ObjectId(), ala:new ObjectId(), ads:new ObjectId(),
    ant:new ObjectId(), kay:new ObjectId(), ksp:new ObjectId(), kon:new ObjectId(),
    smp:new ObjectId(), hat:new ObjectId(), rzs:new ObjectId(), pen:new ObjectId(),
    gfk:new ObjectId(), agu:new ObjectId(), eyu:new ObjectId(), bdr:new ObjectId()
};

db.teams.insertMany([
    {_id:T.gal,  name:"Galatasaray",      shortName:"GS",   logoUrl:"https://placehold.co/64x64/cc0000/ffd700?text=GS",   city:"İstanbul",  stadium:"RAMS Park",                    isActive:true, createdDate:NOW, updatedDate:NOW},
    {_id:T.fen,  name:"Fenerbahçe",       shortName:"FB",   logoUrl:"https://placehold.co/64x64/1a3c5e/f5c518?text=FB",   city:"İstanbul",  stadium:"Ülker Stadyumu",               isActive:true, createdDate:NOW, updatedDate:NOW},
    {_id:T.bjk,  name:"Beşiktaş",         shortName:"BJK",  logoUrl:"https://placehold.co/64x64/111111/ffffff?text=BJK",  city:"İstanbul",  stadium:"Tüpraş Stadyumu",              isActive:true, createdDate:NOW, updatedDate:NOW},
    {_id:T.ts,   name:"Trabzonspor",       shortName:"TS",   logoUrl:"https://placehold.co/64x64/8b0000/00aaff?text=TS",   city:"Trabzon",   stadium:"Papara Park",                  isActive:true, createdDate:NOW, updatedDate:NOW},
    {_id:T.ibfk, name:"Başakşehir",        shortName:"IBFK", logoUrl:"https://placehold.co/64x64/ff6600/003366?text=IBFK",city:"İstanbul",  stadium:"Başakşehir F.T. Stadyumu",     isActive:true, createdDate:NOW, updatedDate:NOW},
    {_id:T.svs,  name:"Sivasspor",         shortName:"SVS",  logoUrl:"https://placehold.co/64x64/dd0000/ffff00?text=SVS", city:"Sivas",     stadium:"4 Eylül Stadyumu",             isActive:true, createdDate:NOW, updatedDate:NOW},
    {_id:T.ala,  name:"Alanyaspor",        shortName:"ALA",  logoUrl:"https://placehold.co/64x64/ff6600/ffffff?text=ALA", city:"Alanya",    stadium:"Bahçeşehir Okul. Stadyumu",    isActive:true, createdDate:NOW, updatedDate:NOW},
    {_id:T.ads,  name:"Adana Demirspor",   shortName:"ADS",  logoUrl:"https://placehold.co/64x64/0033cc/ff0000?text=ADS", city:"Adana",     stadium:"Yeni Adana Stadyumu",          isActive:true, createdDate:NOW, updatedDate:NOW},
    {_id:T.ant,  name:"Antalyaspor",       shortName:"ANT",  logoUrl:"https://placehold.co/64x64/cc0000/ffffff?text=ANT", city:"Antalya",   stadium:"Antalya Stadyumu",             isActive:true, createdDate:NOW, updatedDate:NOW},
    {_id:T.kay,  name:"Kayserispor",       shortName:"KAY",  logoUrl:"https://placehold.co/64x64/bb0000/ffd700?text=KAY", city:"Kayseri",   stadium:"Kadir Has Stadyumu",           isActive:true, createdDate:NOW, updatedDate:NOW},
    {_id:T.ksp,  name:"Kasımpaşa",         shortName:"KSP",  logoUrl:"https://placehold.co/64x64/003399/ffffff?text=KSP", city:"İstanbul",  stadium:"Recep T. Erdoğan Stadyumu",    isActive:true, createdDate:NOW, updatedDate:NOW},
    {_id:T.kon,  name:"Konyaspor",         shortName:"KON",  logoUrl:"https://placehold.co/64x64/006633/ffffff?text=KON", city:"Konya",     stadium:"Konya Büyükşehir Stadyumu",    isActive:true, createdDate:NOW, updatedDate:NOW},
    {_id:T.smp,  name:"Samsunspor",        shortName:"SMP",  logoUrl:"https://placehold.co/64x64/cc0000/000000?text=SMP", city:"Samsun",    stadium:"Samsun 19 Mayıs Stadyumu",    isActive:true, createdDate:NOW, updatedDate:NOW},
    {_id:T.hat,  name:"Hatayspor",         shortName:"HAT",  logoUrl:"https://placehold.co/64x64/cc3300/ffff00?text=HAT", city:"Hatay",     stadium:"Hatay Stadyumu",               isActive:true, createdDate:NOW, updatedDate:NOW},
    {_id:T.rzs,  name:"Rizespor",          shortName:"RZS",  logoUrl:"https://placehold.co/64x64/006633/ffffff?text=RZS", city:"Rize",      stadium:"Çaykur Didi Stadyumu",         isActive:true, createdDate:NOW, updatedDate:NOW},
    {_id:T.pen,  name:"Pendikspor",        shortName:"PEN",  logoUrl:"https://placehold.co/64x64/003399/ff6600?text=PEN", city:"İstanbul",  stadium:"Pendik Stadyumu",              isActive:true, createdDate:NOW, updatedDate:NOW},
    {_id:T.gfk,  name:"Gaziantep FK",      shortName:"GFK",  logoUrl:"https://placehold.co/64x64/dd0000/ffffff?text=GFK", city:"Gaziantep", stadium:"Kalyon Stadyumu",              isActive:true, createdDate:NOW, updatedDate:NOW},
    {_id:T.agu,  name:"MKE Ankaragücü",    shortName:"AGU",  logoUrl:"https://placehold.co/64x64/cc9900/000000?text=AGU", city:"Ankara",    stadium:"Eryaman Stadyumu",             isActive:true, createdDate:NOW, updatedDate:NOW},
    {_id:T.eyu,  name:"Eyüpspor",          shortName:"EYU",  logoUrl:"https://placehold.co/64x64/333333/ffd700?text=EYU", city:"İstanbul",  stadium:"Eyüp Stadyumu",                isActive:true, createdDate:NOW, updatedDate:NOW},
    {_id:T.bdr,  name:"Bodrum FK",         shortName:"BDR",  logoUrl:"https://placehold.co/64x64/0099cc/ffffff?text=BDR", city:"Bodrum",    stadium:"Bodrum Stadyumu",              isActive:true, createdDate:NOW, updatedDate:NOW}
]);
print("Teams: " + db.teams.countDocuments());

// ============================================================
//  STANDINGS
// ============================================================
const SD = [
    {t:T.gal, pos:1,  p:32, w:23, d:5, l:4,  gf:74, ga:30, pts:74},
    {t:T.fen, pos:2,  p:32, w:22, d:6, l:4,  gf:68, ga:28, pts:72},
    {t:T.ts,  pos:3,  p:32, w:18, d:5, l:9,  gf:58, ga:42, pts:59},
    {t:T.bjk, pos:4,  p:32, w:17, d:7, l:8,  gf:55, ga:38, pts:58},
    {t:T.ibfk,pos:5,  p:32, w:16, d:5, l:11, gf:50, ga:40, pts:53},
    {t:T.ads, pos:6,  p:32, w:14, d:8, l:10, gf:47, ga:43, pts:50},
    {t:T.ala, pos:7,  p:32, w:13, d:8, l:11, gf:44, ga:42, pts:47},
    {t:T.svs, pos:8,  p:32, w:13, d:6, l:13, gf:41, ga:45, pts:45},
    {t:T.smp, pos:9,  p:32, w:12, d:7, l:13, gf:39, ga:44, pts:43},
    {t:T.ant, pos:10, p:32, w:11, d:9, l:12, gf:38, ga:46, pts:42},
    {t:T.kon, pos:11, p:32, w:11, d:8, l:13, gf:37, ga:47, pts:41},
    {t:T.hat, pos:12, p:32, w:10, d:9, l:13, gf:36, ga:48, pts:39},
    {t:T.ksp, pos:13, p:32, w:10, d:8, l:14, gf:38, ga:52, pts:38},
    {t:T.eyu, pos:14, p:32, w:10, d:7, l:15, gf:35, ga:50, pts:37},
    {t:T.gfk, pos:15, p:32, w:9,  d:9, l:14, gf:34, ga:50, pts:36},
    {t:T.kay, pos:16, p:32, w:8,  d:9, l:15, gf:33, ga:51, pts:33},
    {t:T.pen, pos:17, p:32, w:8,  d:8, l:16, gf:30, ga:54, pts:32},
    {t:T.agu, pos:18, p:32, w:7,  d:9, l:16, gf:29, ga:56, pts:30},
    {t:T.rzs, pos:19, p:32, w:6,  d:10,l:16, gf:28, ga:58, pts:28},
    {t:T.bdr, pos:20, p:32, w:5,  d:7, l:20, gf:24, ga:65, pts:22}
];
const FORMS = {
    top:   ["W","W","W","D","W"], mid: ["W","D","L","W","D"],
    bot:   ["L","L","D","L","W"]
};
db.standings.insertMany(SD.map(s => ({
    _id: new ObjectId(),
    teamId: s.t.toString(),
    position: s.pos, played: s.p, won: s.w, draw: s.d, lost: s.l,
    goalsFor: s.gf, goalsAgainst: s.ga, goalDifference: s.gf - s.ga, points: s.pts,
    form: (s.pos<=5 ? FORMS.top : s.pos<=13 ? FORMS.mid : FORMS.bot).slice().reverse().join(''),
    createdDate: NOW, updatedDate: NOW
})));
print("Standings: " + db.standings.countDocuments());

// ============================================================
//  PLAYERS  (4 per team = 80 total)
// ============================================================
const PD = {
    gal:  [{fn:"Mauro Icardi",       sn:"Icardi",      pos:"FORWARD",    g:22,a:8,  star:true},
           {fn:"Dries Mertens",      sn:"Mertens",     pos:"MIDFIELDER", g:10,a:14, star:true},
           {fn:"Fernando Muslera",   sn:"Muslera",     pos:"GOALKEEPER", g:0, a:0,  star:true},
           {fn:"Lucas Torreira",     sn:"Torreira",    pos:"MIDFIELDER", g:5, a:7,  star:false}],
    fen:  [{fn:"Edin Džeko",         sn:"Džeko",       pos:"FORWARD",    g:18,a:6,  star:true},
           {fn:"İrfan Can Kahveci",  sn:"İrfan Can",   pos:"MIDFIELDER", g:9, a:11, star:true},
           {fn:"Sebastian Szymański",sn:"Szymański",   pos:"MIDFIELDER", g:7, a:13, star:true},
           {fn:"Alexander Djiku",    sn:"Djiku",       pos:"DEFENDER",   g:1, a:2,  star:false}],
    bjk:  [{fn:"Ciro Immobile",      sn:"Immobile",    pos:"FORWARD",    g:16,a:4,  star:true},
           {fn:"Rafa Silva",         sn:"R.Silva",     pos:"MIDFIELDER", g:8, a:10, star:true},
           {fn:"Mert Günok",         sn:"M.Günok",     pos:"GOALKEEPER", g:0, a:0,  star:false},
           {fn:"Gedson Fernandes",   sn:"Gedson",      pos:"MIDFIELDER", g:4, a:8,  star:false}],
    ts:   [{fn:"Andreas Cornelius",  sn:"Cornelius",   pos:"FORWARD",    g:15,a:5,  star:true},
           {fn:"Anastasios Bakasetas",sn:"Bakasetas",  pos:"MIDFIELDER", g:9, a:9,  star:true},
           {fn:"Uğurcan Çakır",      sn:"U.Çakır",    pos:"GOALKEEPER", g:0, a:0,  star:true},
           {fn:"Paul Onuachu",       sn:"Onuachu",     pos:"FORWARD",    g:12,a:3,  star:false}],
    ibfk: [{fn:"Edin Višća",         sn:"Višća",       pos:"MIDFIELDER", g:8, a:10, star:true},
           {fn:"Stefano Okaka",      sn:"Okaka",       pos:"FORWARD",    g:11,a:4,  star:false},
           {fn:"Bolingoli",          sn:"Bolingoli",   pos:"DEFENDER",   g:2, a:5,  star:false},
           {fn:"Deniz Türüç",        sn:"D.Türüç",     pos:"MIDFIELDER", g:5, a:7,  star:false}],
    svs:  [{fn:"Max Gradel",         sn:"Gradel",      pos:"FORWARD",    g:9, a:6,  star:true},
           {fn:"Olarenwaju Kayode",  sn:"Kayode",      pos:"FORWARD",    g:8, a:3,  star:false},
           {fn:"Ziya Erdal",         sn:"Z.Erdal",     pos:"MIDFIELDER", g:4, a:6,  star:false},
           {fn:"Erdoğan Yeşilyurt",  sn:"E.Yeşilyurt", pos:"GOALKEEPER", g:0, a:0, star:false}],
    ala:  [{fn:"Burak Yılmaz",       sn:"B.Yılmaz",   pos:"FORWARD",    g:10,a:4,  star:true},
           {fn:"Petros Mantalos",    sn:"Mantalos",    pos:"MIDFIELDER", g:6, a:8,  star:false},
           {fn:"Ruben Ribeiro",      sn:"R.Ribeiro",   pos:"MIDFIELDER", g:5, a:9,  star:false},
           {fn:"Leandro Donnanzán",  sn:"Donnanzán",   pos:"DEFENDER",   g:1, a:3,  star:false}],
    ads:  [{fn:"Mario Balotelli",    sn:"Balotelli",   pos:"FORWARD",    g:13,a:5,  star:true},
           {fn:"Emre Demir",         sn:"E.Demir",     pos:"MIDFIELDER", g:7, a:9,  star:true},
           {fn:"Britt Assombalonga", sn:"Assombalonga",pos:"FORWARD",    g:9, a:4,  star:false},
           {fn:"Vincent Laban",      sn:"Laban",       pos:"MIDFIELDER", g:3, a:6,  star:false}],
    ant:  [{fn:"Fredy Guarín",       sn:"Guarín",      pos:"MIDFIELDER", g:5, a:7,  star:false},
           {fn:"Nazim Sangare",      sn:"Sangare",     pos:"FORWARD",    g:8, a:3,  star:false},
           {fn:"Naldo",              sn:"Naldo",       pos:"DEFENDER",   g:2, a:1,  star:false},
           {fn:"Şükrü Aydın",        sn:"Ş.Aydın",    pos:"GOALKEEPER", g:0, a:0,  star:false}],
    kay:  [{fn:"Marcos Antônio",     sn:"M.Antônio",   pos:"MIDFIELDER", g:6, a:8,  star:false},
           {fn:"Carlos Strandberg",  sn:"Strandberg",  pos:"FORWARD",    g:7, a:3,  star:false},
           {fn:"Amine Harit",        sn:"Harit",       pos:"MIDFIELDER", g:5, a:7,  star:false},
           {fn:"Rachid Bouhenna",    sn:"Bouhenna",    pos:"DEFENDER",   g:1, a:2,  star:false}],
    ksp:  [{fn:"Mbaye Diagne",       sn:"Diagne",      pos:"FORWARD",    g:11,a:4,  star:true},
           {fn:"Emilio Nsue",        sn:"Nsue",        pos:"MIDFIELDER", g:5, a:6,  star:false},
           {fn:"Gökhan Gönül",       sn:"G.Gönül",    pos:"DEFENDER",   g:1, a:4,  star:false},
           {fn:"Ferdi Kadıoğlu",     sn:"F.Kadıoğlu", pos:"DEFENDER",   g:2, a:5,  star:false}],
    kon:  [{fn:"Raheem Lawal",       sn:"Lawal",       pos:"MIDFIELDER", g:5, a:7,  star:false},
           {fn:"Artem Besedin",      sn:"Besedin",     pos:"FORWARD",    g:7, a:3,  star:false},
           {fn:"Ahmed Hassan",       sn:"A.Hassan",    pos:"DEFENDER",   g:1, a:2,  star:false},
           {fn:"Ömer Ali Şahiner",   sn:"Ö.Şahiner",  pos:"MIDFIELDER", g:3, a:5,  star:false}],
    smp:  [{fn:"Yaw Yeboah",         sn:"Yeboah",      pos:"MIDFIELDER", g:6, a:8,  star:false},
           {fn:"Mamadou Doumbouya",  sn:"Doumbouya",   pos:"FORWARD",    g:8, a:3,  star:false},
           {fn:"Sören Bertram",      sn:"Bertram",     pos:"FORWARD",    g:5, a:4,  star:false},
           {fn:"Batuhan Kör",        sn:"B.Kör",       pos:"MIDFIELDER", g:4, a:5,  star:false}],
    hat:  [{fn:"Trezeguet",          sn:"Trezeguet",   pos:"MIDFIELDER", g:6, a:5,  star:true},
           {fn:"Aleksandar Pesić",   sn:"Pesić",       pos:"FORWARD",    g:8, a:3,  star:false},
           {fn:"Mustapha Bundu",     sn:"Bundu",       pos:"MIDFIELDER", g:5, a:6,  star:false},
           {fn:"Fernando Boldrin",   sn:"Boldrin",     pos:"MIDFIELDER", g:4, a:7,  star:false}],
    rzs:  [{fn:"Mikael Ishak",       sn:"Ishak",       pos:"FORWARD",    g:6, a:2,  star:false},
           {fn:"Oghenekaro Etebo",   sn:"Etebo",       pos:"MIDFIELDER", g:3, a:5,  star:false},
           {fn:"Loïc Badé",          sn:"Badé",        pos:"DEFENDER",   g:2, a:2,  star:false},
           {fn:"Anass Salah-Eddine", sn:"Salah-E.",    pos:"DEFENDER",   g:1, a:3,  star:false}],
    pen:  [{fn:"Bafétimbi Gomis",    sn:"Gomis",       pos:"FORWARD",    g:7, a:2,  star:false},
           {fn:"Doğukan Sinik",      sn:"D.Sinik",     pos:"MIDFIELDER", g:3, a:5,  star:false},
           {fn:"Guilherme",          sn:"Guilherme",   pos:"GOALKEEPER", g:0, a:0,  star:false},
           {fn:"Hamit Altıntop",     sn:"H.Altıntop",  pos:"MIDFIELDER", g:2, a:4,  star:false}],
    gfk:  [{fn:"Titi Meza",          sn:"Meza",        pos:"FORWARD",    g:6, a:3,  star:false},
           {fn:"Raul Rustamov",      sn:"Rustamov",    pos:"FORWARD",    g:5, a:3,  star:false},
           {fn:"Eduardo Camavinga",  sn:"Camavinga",   pos:"MIDFIELDER", g:4, a:6,  star:false},
           {fn:"Silvio",             sn:"Silvio",      pos:"DEFENDER",   g:1, a:2,  star:false}],
    agu:  [{fn:"Musa Çağıran",       sn:"M.Çağıran",  pos:"MIDFIELDER", g:3, a:4,  star:false},
           {fn:"Steven Caulker",     sn:"Caulker",     pos:"DEFENDER",   g:2, a:1,  star:false},
           {fn:"Emre Belözoğlu",     sn:"E.Belözoğlu", pos:"MIDFIELDER", g:2, a:5, star:false},
           {fn:"Sakib Aytaç",        sn:"S.Aytaç",    pos:"DEFENDER",   g:1, a:2,  star:false}],
    eyu:  [{fn:"Cenk Tosun",         sn:"C.Tosun",    pos:"FORWARD",    g:8, a:4,  star:true},
           {fn:"Mame Diouf",         sn:"Diouf",       pos:"FORWARD",    g:7, a:3,  star:false},
           {fn:"Thibaut Courtois",   sn:"Courtois",    pos:"GOALKEEPER", g:0, a:0,  star:true},
           {fn:"Aroha Rangipeti",    sn:"Rangipeti",   pos:"DEFENDER",   g:1, a:1,  star:false}],
    bdr:  [{fn:"Roberto Soldado",    sn:"Soldado",     pos:"FORWARD",    g:5, a:2,  star:false},
           {fn:"Mehmet Topal",       sn:"M.Topal",     pos:"MIDFIELDER", g:1, a:3,  star:false},
           {fn:"Roman Neustädter",   sn:"Neustädter",  pos:"DEFENDER",   g:1, a:1,  star:false},
           {fn:"Gürkan Uzel",        sn:"G.Uzel",      pos:"GOALKEEPER", g:0, a:0,  star:false}]
};

const PLAYERS = [];
const playersByTeam = {};
for (const [key, arr] of Object.entries(PD)) {
    const tid = T[key].toString();
    playersByTeam[tid] = [];
    for (const p of arr) {
        const pid = new ObjectId();
        PLAYERS.push({
            _id: pid, fullName: p.fn, shortName: p.sn, position: p.pos,
            goals: p.g, assists: p.a,
            imageUrl: "https://placehold.co/80x80/1a1a2e/00e676?text=" + encodeURIComponent(p.sn.substring(0,3).toUpperCase()),
            isStarPlayer: p.star, isActive: true, teamId: tid,
            createdDate: NOW, updatedDate: NOW
        });
        playersByTeam[tid].push(pid.toString());
    }
}
db.players.insertMany(PLAYERS);
print("Players: " + db.players.countDocuments());

// ============================================================
//  MATCHES — Round-Robin 38 weeks
// ============================================================
const TEAM_IDS = [T.gal,T.fen,T.bjk,T.ts,T.ibfk,T.svs,T.ala,T.ads,T.ant,T.kay,
                  T.ksp,T.kon,T.smp,T.hat,T.rzs,T.pen,T.gfk,T.agu,T.eyu,T.bdr];
const N = TEAM_IDS.length; // 20
const fixed = TEAM_IDS[0];
const rot   = TEAM_IDS.slice(1); // 19

function getWeekPairs(r) {
    const pairs = [[fixed, rot[r % (N-1)]]];
    for (let i = 1; i < N/2; i++)
        pairs.push([rot[(r+i) % (N-1)], rot[(r-i+N-1) % (N-1)]]);
    return pairs;
}

const SCHEDULE = [];
for (let r = 0; r < N-1; r++) SCHEDULE.push(getWeekPairs(r));
for (let r = 0; r < N-1; r++) SCHEDULE.push(getWeekPairs(r).map(([h,a]) => [a,h]));

const STADIUMS = {
    [T.gal.toString()]:"RAMS Park", [T.fen.toString()]:"Ülker Stadyumu",
    [T.bjk.toString()]:"Tüpraş Stadyumu", [T.ts.toString()]:"Papara Park",
    [T.ibfk.toString()]:"Başakşehir F.T. Stadyumu", [T.svs.toString()]:"4 Eylül Stadyumu",
    [T.ala.toString()]:"Bahçeşehir Okul. Stadyumu", [T.ads.toString()]:"Yeni Adana Stadyumu",
    [T.ant.toString()]:"Antalya Stadyumu", [T.kay.toString()]:"Kadir Has Stadyumu",
    [T.ksp.toString()]:"Recep T. Erdoğan Stadyumu", [T.kon.toString()]:"Konya Büyükşehir Stadyumu",
    [T.smp.toString()]:"Samsun 19 Mayıs Stadyumu", [T.hat.toString()]:"Hatay Stadyumu",
    [T.rzs.toString()]:"Çaykur Didi Stadyumu", [T.pen.toString()]:"Pendik Stadyumu",
    [T.gfk.toString()]:"Kalyon Stadyumu", [T.agu.toString()]:"Eryaman Stadyumu",
    [T.eyu.toString()]:"Eyüp Stadyumu", [T.bdr.toString()]:"Bodrum Stadyumu"
};

// Score tables — indexed by pseudo(seed,10)
const SCORE_HW = [[1,0],[2,0],[2,1],[3,0],[3,1]];
const SCORE_AW = [[0,1],[0,2],[1,2],[0,3],[1,3]];
const SCORE_DR = [[0,0],[1,1],[2,2],[1,1],[0,0]];

const allMatches  = [];
const matchMeta   = []; // for event generation
const SLOT_DAY    = [0,0,0,1,1,1,2,2,2,1]; // day-of-week offset within week
const SLOT_HOUR   = [18,20,20,14,17,19,14,17,20,20];

let seed = 0;
SCHEDULE.forEach((pairs, wi) => {
    const week = wi + 1;
    const weekBase = new Date(SEASON_START.getTime() + wi * 7 * 24 * 3600 * 1000);
    pairs.forEach(([hOid, aOid], mi) => {
        seed++;
        const hId = hOid.toString(), aId = aOid.toString();
        const md  = new Date(weekBase.getTime() + SLOT_DAY[mi] * 86400000);
        md.setUTCHours(SLOT_HOUR[mi], 0, 0, 0);

        let status, hs, as2, minute, featured = false;

        if (week <= 32) {
            status = "FINISHED"; minute = 90;
            const r = pseudo(seed, 10);
            const [sh, sa] = r < 5 ? SCORE_HW[r] : r < 8 ? SCORE_AW[r-5] : SCORE_DR[r-8];
            hs = sh; as2 = sa;
            // Top 2 clubs win more at home
            if ((hId === T.gal.toString() || hId === T.fen.toString()) && pseudo(seed+100,3) < 2) {
                if (hs <= as2) { hs = as2 + 1; }
            }
        } else if (week === 33) {
            if (mi < 3) {
                status  = "LIVE";
                minute  = [23,67,88][mi];
                hs      = [0,1,2][mi];
                as2     = [1,1,0][mi];
                featured = mi === 0; // GS derby as featured live
            } else if (mi < 7) {
                status = "FINISHED"; minute = 90;
                const r = pseudo(seed,10);
                const [sh,sa] = r<5?SCORE_HW[r]:r<8?SCORE_AW[r-5]:SCORE_DR[r-8];
                hs=sh; as2=sa;
            } else {
                status = "UPCOMING"; minute = 0; hs = 0; as2 = 0;
                featured = mi === 7;
            }
        } else {
            status = "UPCOMING"; minute = 0; hs = 0; as2 = 0;
            featured = week === 34 && mi === 0;
        }

        const mid = new ObjectId();
        allMatches.push({
            _id:mid, homeTeamId:hId, awayTeamId:aId,
            homeScore:hs, awayScore:as2, matchDate:md,
            stadium: STADIUMS[hId] || "Stadyum",
            week, status, minute, isFeatured:featured,
            createdDate:NOW, updatedDate:NOW
        });
        if (status === "FINISHED" || status === "LIVE")
            matchMeta.push({id:mid, hId, aId, hs, as2, status});
    });
});

// Insert matches in 200-doc batches
for (let i = 0; i < allMatches.length; i += 200)
    db.matches.insertMany(allMatches.slice(i, i+200));
print("Matches: " + db.matches.countDocuments());

// ============================================================
//  MATCH EVENTS
// ============================================================
const EDESC = {
    GOAL:         ["Müthiş bir gol!","Harika vuruş!","Köşeden gol!","Kafa golü!","Serbest vuruştan gol!"],
    YELLOW_CARD:  ["Sert faulle sarı kart","Gecikmeyle sarı kart","İtirazla sarı kart","Haksız hareket sarı kart"],
    RED_CARD:     ["Kırmızı kart! Son oyuncuyu devirdi","İkinci sarı kart kırmızıya döndü","Sert müdahale kırmızı kart"],
    SUBSTITUTION: ["Taktik değişiklik","Sakatlık nedeniyle çıktı","Yıldız oyuncu sahaya girdi","Kondisyon değişikliği"]
};

const allEvents = [];
let eseed = 0;
for (const m of matchMeta) {
    const usedMin = new Set();
    function nextMin(base, range) {
        eseed++;
        let mn;
        let tries = 0;
        do { mn = base + pseudo(eseed + tries++ * 7, range); } while (usedMin.has(mn) && tries < 50);
        usedMin.add(mn);
        return mn;
    }
    function pickPlayer(tid) {
        const arr = playersByTeam[tid] || [];
        return arr.length ? arr[pseudo(eseed, arr.length)] : "";
    }

    // Goals
    for (let g = 0; g < m.hs + m.as2; g++) {
        eseed++;
        const mn     = nextMin(1, 89);
        const isHome = g < m.hs;
        const tid    = isHome ? m.hId : m.aId;
        allEvents.push({
            _id:new ObjectId(), matchId:m.id.toString(), teamId:tid,
            playerId:pickPlayer(tid), minute:mn, eventType:"GOAL",
            description:EDESC.GOAL[pseudo(eseed, EDESC.GOAL.length)],
            createdDate:NOW, updatedDate:NOW
        });
    }
    // Yellow cards (1–2)
    const yc = 1 + pseudo(eseed+1, 2);
    for (let y = 0; y < yc; y++) {
        eseed++;
        const mn  = nextMin(5, 85);
        const tid = pseudo(eseed+50,2)===0 ? m.hId : m.aId;
        allEvents.push({
            _id:new ObjectId(), matchId:m.id.toString(), teamId:tid,
            playerId:pickPlayer(tid), minute:mn, eventType:"YELLOW_CARD",
            description:EDESC.YELLOW_CARD[pseudo(eseed, EDESC.YELLOW_CARD.length)],
            createdDate:NOW, updatedDate:NOW
        });
    }
    // Red card ~12% chance
    if (pseudo(eseed+200, 8) === 0) {
        eseed++;
        const mn  = nextMin(50, 40);
        const tid = pseudo(eseed,2)===0 ? m.hId : m.aId;
        allEvents.push({
            _id:new ObjectId(), matchId:m.id.toString(), teamId:tid,
            playerId:pickPlayer(tid), minute:mn, eventType:"RED_CARD",
            description:EDESC.RED_CARD[pseudo(eseed, EDESC.RED_CARD.length)],
            createdDate:NOW, updatedDate:NOW
        });
    }
    // Substitutions (2 per finished match)
    if (m.status === "FINISHED") {
        for (let s = 0; s < 2; s++) {
            eseed++;
            const mn  = nextMin(60, 30);
            const tid = s===0 ? m.hId : m.aId;
            allEvents.push({
                _id:new ObjectId(), matchId:m.id.toString(), teamId:tid,
                playerId:pickPlayer(tid), minute:mn, eventType:"SUBSTITUTION",
                description:EDESC.SUBSTITUTION[pseudo(eseed, EDESC.SUBSTITUTION.length)],
                createdDate:NOW, updatedDate:NOW
            });
        }
    }
}

for (let i = 0; i < allEvents.length; i += 500)
    db.match_events.insertMany(allEvents.slice(i, i+500));
print("Match events: " + db.match_events.countDocuments());

// ============================================================
//  NEWS (20 articles)
// ============================================================
db.news.insertMany([
    {title:"Galatasaray Şampiyonluğa Koşuyor",      summary:"Liderliğini sürdüren sarı-kırmızılılar zirveyi bırakmıyor.",         content:"Galatasaray bu sezon 32 maçta 23 galibiyet alarak liderliğini sağlamlaştırdı. Icardi ve Mertens'in muhteşem uyumu takımı şampiyonluğa taşıyor. RAMS Park son 10 maçta yenilmezlik kalesi olmayı sürdürüyor.",                                        imageUrl:"https://placehold.co/400x220/cc0000/ffd700?text=Galatasaray",  category:"Takım Haberleri",    isMain:true,  isActive:true},
    {title:"Fenerbahçe Şampiyonluk Yarışında",       summary:"Sarı-lacivertliler liderlikten sadece 2 puan geride.",                content:"Fenerbahçe son haftalarda sergilediği performansla Galatasaray'ı büyük baskı altına aldı. Dzeko ve Szymański ikilisi gollerini sürüyor; final 6 haftada her şey değişebilir.",                                                                         imageUrl:"https://placehold.co/400x220/1a3c5e/f5c518?text=Fenerbahce",   category:"Takım Haberleri",    isMain:true,  isActive:true},
    {title:"Derbi Ateşi Yaklaşıyor",                 summary:"Galatasaray–Fenerbahçe kapışması için geri sayım başladı.",           content:"İki dev, Süper Lig tarihinin en kritik derbilerinden birine hazırlanıyor. RAMS Park 55 bin taraftarıyla yeniden nefes kesecek.",                                                                                                                       imageUrl:"https://placehold.co/400x220/111/00e676?text=Derbi",           category:"Özel Haberler",      isMain:false, isActive:true},
    {title:"Icardi 22. Golünü Attı",                 summary:"Arjantinli forvet gol krallığında tartışmasız lider.",                content:"Mauro Icardi, bu sezon attığı 22. golle gol krallığında öne geçti. 25 gol hedefinde kararlı görünen yıldız, son 8 maçta 9 gol kaydetti.",                                                                                                         imageUrl:"https://placehold.co/400x220/cc0000/ffffff?text=Icardi",       category:"Oyuncu Haberleri",   isMain:false, isActive:true},
    {title:"Trabzonspor Üçüncülük Peşinde",          summary:"Karadeniz fırtınası bu sezon da fark yarattı.",                      content:"Trabzonspor deplasman performansıyla göz dolduruyor. Cornelius'un 15 golü ve Bakasetas'ın yaratıcılığıyla üçüncülük yarışının en güçlü adayı konumunda.",                                                                                          imageUrl:"https://placehold.co/400x220/8b0000/00aaff?text=Trabzonspor",  category:"Takım Haberleri",    isMain:false, isActive:true},
    {title:"Beşiktaş'tan Büyük Transfer Hamlesi",    summary:"Kartal önümüzdeki sezon için kapıları araladı.",                     content:"Beşiktaş yönetimi, gelecek sezon kadrosunu güçlendirmek için üç Avrupalı isimle temasa geçtiğini duyurdu. Ciro Immobile'nin sözleşme uzatma müzakereleri de tüm hızıyla sürüyor.",                                                                  imageUrl:"https://placehold.co/400x220/111111/ffffff?text=Besiktas",     category:"Transfer Haberleri", isMain:false, isActive:true},
    {title:"Balotelli'den Sezonun Golü",             summary:"İtalyan forvetin muhteşem volley'ı sosyal medyayı salladı.",         content:"Adana Demirspor–Kayserispor maçında Mario Balotelli'nin 25 metreden attığı volley, bu sezonun en güzel golü seçildi. Görüntüler dünya genelinde milyonlarca kez izlendi.",                                                                       imageUrl:"https://placehold.co/400x220/0033cc/ff0000?text=Balotelli",    category:"Özel Haberler",      isMain:false, isActive:true},
    {title:"Uğurcan Çakır Milli Takımda",            summary:"Trabzonspor kalecisi A Milli Takım'a çağrıldı.",                    content:"Bu sezon Süper Lig'in en iyi kalecisi seçilen Uğurcan Çakır, teknik direktör tarafından milli kadroya dahil edildi. Trabzonspor taraftarları sosyal medyada kutlama mesajları paylaştı.",                                                               imageUrl:"https://placehold.co/400x220/8b0000/00aaff?text=Ugurcan",      category:"Milli Takım",        isMain:false, isActive:true},
    {title:"Küme Düşme Hattı Kızıştı",              summary:"Son 6 haftada 4 takım tehlike bölgesinde.",                          content:"Bodrum FK, Rizespor, MKE Ankaragücü ve Pendikspor arasındaki küme düşme yarışı finale taşındı. Her maç büyük önem kazanırken, hata payı sıfıra indi.",                                                                                             imageUrl:"https://placehold.co/400x220/333/ff4444?text=KumeDusme",       category:"Özel Haberler",      isMain:false, isActive:true},
    {title:"Adana Demirspor Avrupa Peşinde",         summary:"6. sırada yer alan mavi-lacivertliler Avrupa'yı hayal ediyor.",      content:"Balotelli ve Emre Demir liderliğinde sezona damga vuran Adana Demirspor, Avrupa Ligi bileti için son virajda. Teknik direktör kadro derinliğine güveniyor.",                                                                                         imageUrl:"https://placehold.co/400x220/0033cc/ff0000?text=ADS",          category:"Takım Haberleri",    isMain:false, isActive:true},
    {title:"Süper Lig'de Yabancı Oyuncu Rekoru",     summary:"Bu sezon lig tarihinin en fazla yabancı oyuncusu sahada.",           content:"2025-26 Süper Lig sezonunda 245 yabancı oyuncu forma giydi. Bu rakam, Türk futbol tarihinin en yüksek seviyesi olarak kayıtlara geçti.",                                                                                                        imageUrl:"https://placehold.co/400x220/1a1a2e/00e676?text=SuperLig",     category:"Genel",              isMain:false, isActive:true},
    {title:"Sivasspor Avrupa Hayalini Yaşatıyor",    summary:"İç Anadolu'nun gururu bu sezon da zirveye mücadele ediyor.",         content:"Gradel ve Kayode ikilisiyle sezona güçlü başlayan Sivasspor, 8. sırasıyla Konferans Ligi biletini gözüne kesti. Teknik direktör takım kimyasından memnun.",                                                                                       imageUrl:"https://placehold.co/400x220/dd0000/ffff00?text=Sivasspor",    category:"Takım Haberleri",    isMain:false, isActive:true},
    {title:"VAR Tartışmaları Gündemde",              summary:"Bu haftaki kararlar kulüp yöneticilerini kızdırdı.",                  content:"Birden fazla maçta verilen VAR kararları sosyal medyada büyük tartışma yarattı. TFF Tahkim Kurulu süreci inceleme altına alırken, kulüpler resmen itiraz hakkını kullandı.",                                                                       imageUrl:"https://placehold.co/400x220/333/ffffff?text=VAR",             category:"Genel",              isMain:false, isActive:true},
    {title:"Samsunspor Ligde Kalmayı Garantiledi",   summary:"Karadeniz'in köklü takımı bir sezon daha Süper Lig'de.",             content:"Samsunspor, son haftalardaki kritik galibiyetleriyle küme düşme endişelerini tamamen geride bıraktı. Taraftarlar Samsun sokaklarında sevinç yürüyüşü yaptı.",                                                                                    imageUrl:"https://placehold.co/400x220/cc0000/000000?text=Samsunspor",  category:"Takım Haberleri",    isMain:false, isActive:true},
    {title:"Şampiyonlar Ligi Biletine Son 5 Hafta",  summary:"Galatasaray ve Fenerbahçe arasındaki kıyasıya yarış son bölümde.",   content:"Türk futbolunun iki büyüğü, Şampiyonlar Ligi 1. tur eleme biletlerini garantilemek için mücadele veriyor. Puan farkı yalnızca 2 olan yarış son haftaya dek sürebilir.",                                                                            imageUrl:"https://placehold.co/400x220/1a1a2e/ffd700?text=UCL",          category:"Avrupa Futbolu",     isMain:false, isActive:true},
    {title:"Sezonun En İyi 11'i Açıklandı",          summary:"Galatasaray 4, Fenerbahçe 3 oyuncuyla listenin zirvesinde.",         content:"TFF tarafından belirlenen 2025-26 Süper Lig sezonunun en iyi 11'i oylamayla açıklandı. Icardi, Mertens, Dzeko, Szymański, Cornelius ve Muslera listede öne çıkan isimler oldu.",                                                                    imageUrl:"https://placehold.co/400x220/003366/ffd700?text=EnIyi11",      category:"Özel Haberler",      isMain:false, isActive:true},
    {title:"Alanyaspor'da Teknik Direktör Değişikliği",summary:"Akdeniz takımında sezonu bitirecek yeni teknik adam belirlendi.",  content:"Beklentilerin altında kalan sezonun ardından Alanyaspor, teknik direktörüyle yollarını ayırdı. Yeni isim göreve başlayarak kalan maçlarda sistemi değiştirmeyi planlıyor.",                                                                     imageUrl:"https://placehold.co/400x220/ff6600/ffffff?text=Alanyaspor",   category:"Teknik Direktörler", isMain:false, isActive:true},
    {title:"Kasımpaşa'nın Genç Yıldızı Dikkat Çekiyor",summary:"18 yaşındaki orta saha Avrupa devi kulüplerin radarında.",       content:"Kasımpaşa'da parlayan genç yetenek, sergilediği performansla Almanya ve İspanya'dan iki kulübün gündemine girdi. Kulüp yönetimi oyuncunun lisansını koruma altına almak için sözleşme görüşmelerine başladı.",                                          imageUrl:"https://placehold.co/400x220/003399/ffffff?text=Kasimpasa",    category:"Oyuncu Haberleri",   isMain:false, isActive:true},
    {title:"Türk Kulüpleri Altyapıya Yatırım Yapıyor",summary:"Büyük kulüpler genç oyuncu geliştirme projelerini açıkladı.",     content:"Galatasaray ve Fenerbahçe, önümüzdeki 5 yıl boyunca altyapı yatırımlarını iki katına çıkaracaklarını duyurdu. Her iki kulüp de Milli Takım'a daha fazla oyuncu yetiştirmeyi hedefliyor.",                                                              imageUrl:"https://placehold.co/400x220/1a1a2e/00e676?text=Altyapi",      category:"Genel",              isMain:false, isActive:true},
    {title:"Dzeko Fenerbahçe'de 100. Maçına Koşuyor", summary:"Bosna aslanı sarı-lacivertlilerde efsane olmaya devam ediyor.",   content:"Edin Džeko, Fenerbahçe formasıyla oynadığı 98. lig maçında 18 gol ve 6 asistlik istatistiğiyle taraftarların gönlüne taht kurdu. 100. maçını tarihî bir galibiyet ya da golle taçlandırmak istiyor.",                                                imageUrl:"https://placehold.co/400x220/1a3c5e/f5c518?text=Dzeko",        category:"Oyuncu Haberleri",   isMain:false, isActive:true}
].map((n, i) => ({
    _id: new ObjectId(), ...n,
    createdDate: new Date(NOW.getTime() - (i+1) * 18 * 3600 * 1000),
    updatedDate: NOW
})));
print("News: " + db.news.countDocuments());

print("==============================================");
print("  TactiCore Seed Tamamlandı!");
print("  Teams:        " + db.teams.countDocuments());
print("  Players:      " + db.players.countDocuments());
print("  Matches:      " + db.matches.countDocuments());
print("  MatchEvents:  " + db.match_events.countDocuments());
print("  Standings:    " + db.standings.countDocuments());
print("  News:         " + db.news.countDocuments());
print("==============================================");
