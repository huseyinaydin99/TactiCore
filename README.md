# ⚽ TactiCore

> *Bir futbol liginin veri akışını, gerçek zamanlı maç takibini ve katmanlı iş mantığını taşıyacak şekilde tasarlanmış, iki modüllü bir yazılım sistemidir. Sıradan bir "CRUD uygulaması" değildir; her servis sınırı, her önbellek kararı ve her soyutlama katmanı ayrı bir niyetin mahsulüdür.*

---

## 🚫 Bu Sistem Nedir? Ne Değildir?

Önce bunu netleştirmek gerekir.

TactiCore; bir demo projesi, bir portföy süsü ya da hızla üretilmiş bir iskelet değildir. Bir lig sezonunu uçtan uca modelleyen, canlı maç akışını işleyen, takım, oyuncu, fikstür, puan durumu ve haber verilerini tutarlı bir mimari disiplin içinde yöneten, **üretime yakın bir sistem tasarımının somutlaşmasıdır.** 🏗️ Ne eksik bir MVP, ne de içi boş bir "enterprise mimari" taklididir.

---

## 🎯 Problemin Tanımı

Bir lig sezonunu temsil eden verinin doğası, çoğu yazılım örneğinde göründüğünden çok daha karmaşıktır.

- Maçlar **zamansal bir durum makinesidir** ⏱️ (`UPCOMING` → `LIVE` → `FINISHED`). Bu geçişler veri katmanına dağılıp orada kaybolmamalıdır.
- Maç olayları (gol 🥅, kart 🟨, değişiklik 🔄) maç verisine **sıkıca bağlı değil, ama ayrıştırılabilir şekilde ilişkili** olmak zorundadır.
- Puan durumu hesaplaması; ayrı bir belge olarak saklanır 📊, çünkü anlık sorgu bazlı türetme, farklı sorgularda tutarsız sonuçlar üretir.
- Arayüz katmanı 🖥️, API'den bağımsız olarak kendi iş mantığını, sayfa modellerini ve hata yönetimini taşımalıdır.

Bu gereksinimler bir arada ele alındığında, hem backend hem frontend'in ayrı kaygıları olan, **birbirinden bağımsız iki uygulama** olarak inşa edilmesi bir tercih değil, bir zorunluluk olarak belirmiştir.

---

## 📸 Tanıtım Görselleri:

![19](ekran_goruntuleri/19.png)

![20](ekran_goruntuleri/20.png)

![1](ekran_goruntuleri/1.png)

![2](ekran_goruntuleri/2.png)

![3](ekran_goruntuleri/3.png)

![4](ekran_goruntuleri/4.png)

![5](ekran_goruntuleri/5.png)

![6](ekran_goruntuleri/6.png)

![7](ekran_goruntuleri/7.png)

![8](ekran_goruntuleri/8.png)

![9](ekran_goruntuleri/9.png)

![10](ekran_goruntuleri/10.png)

![11](ekran_goruntuleri/11.png)

![12](ekran_goruntuleri/12.png)

![13](ekran_goruntuleri/13.png)

![14](ekran_goruntuleri/14.png)

![15](ekran_goruntuleri/15.png)

![16](ekran_goruntuleri/16.png)

![17](ekran_goruntuleri/17.png)

![18](ekran_goruntuleri/18.png)

![21](ekran_goruntuleri/21.png)

![22](ekran_goruntuleri/22.png)

![23](ekran_goruntuleri/23.png)

![24](ekran_goruntuleri/24.png)

![25](ekran_goruntuleri/25.png)

![26](ekran_goruntuleri/26.png)

![27](ekran_goruntuleri/27.png)

![28](ekran_goruntuleri/28.png)

![29](ekran_goruntuleri/29.png)

![30](ekran_goruntuleri/30.png)

![13](ekran_goruntuleri/31.png)

![32](ekran_goruntuleri/32.png)

![33](ekran_goruntuleri/33.png)

![34](ekran_goruntuleri/34.png)

## 🏛️ Mimari Genel Bakış

```
┌──────────────────────────────────────────────────────────┐
│                     TactiCore System                      │
│                                                          │
│  ┌────────────────┐    HTTP/JSON    ┌──────────────────┐ │
│  │  TactiCoreWebUI│ ─────────────► │TactiCoreWebAPI   │ │
│  │  (Port: 8081)  │                │  (Port: 8080)    │ │
│  │  Spring MVC    │                │  Spring REST     │ │
│  │  Thymeleaf     │                │  MongoDB         │ │
│  │  Observer      │                │  Redis Cache     │ │
│  └────────────────┘                └──────────────────┘ │
│                                           │              │
│                           ┌───────────────┼────────┐     │
│                           │               │        │     │
│                      ┌────▼───┐    ┌──────▼──┐     │     │
│                      │MongoDB │    │  Redis  │     │     │
│                      │ 7.0    │    │7.2-alpine│     │     │
│                      └────────┘    └─────────┘     │     │
└──────────────────────────────────────────────────────────┘
```

Sistem iki ana modülden mürekkeptir 🔩:

| Modül | Rol | Port | Teknoloji |
|---|---|---|---|
| `TactiCoreWebAPI` 🔧 | REST API, iş mantığı, veri saklama | 8080 | Spring Boot, MongoDB, Redis |
| `TactiCoreWebUI` 🎨 | Sunum katmanı, HTTP tüketimi, sayfa modelleri | 8081 | Spring MVC, Thymeleaf |

---

## 🗄️ Veri Modeli

Altı belge koleksiyonundan müteşekkildir. Her biri `BaseEntity` üzerinden denetimli zaman damgası taşır (`@CreatedDate`, `@LastModifiedDate`) 🕐.

```
tacticore (MongoDB)
├── teams           → Takım kimliği, logo, stat, saha bilgisi
├── players         → Oyuncu kaydı, mevki, gol/asist, takım ilişkisi
├── matches         → Maç durumu, skor, hafta, öne çıkan bayrağı
├── match_events    → Olay tipi (GOAL, YELLOW_CARD, RED_CARD, SUBSTITUTION)
├── standings       → Puan durumu satırı (oynan, kazanılan, form...)
└── news            → Haber kaydı, kategori, ana sayfa bayrağı
```

### ⚙️ Durum Makinesi

```
      ┌─────────┐
      │UPCOMING │  ──►  ┌──────┐  ──►  ┌──────────┐
      └─────────┘       │ LIVE │       │ FINISHED │
                        └──────┘       └──────────┘
```

`MatchStatus` enum'u bu geçişin tip güvenli temsilcisidir 🛡️. Tüm filtreleme, sıralama ve UI ayrımı bu tiple yürür.

---

## 📐 Mühendislik Prensipleri

```
[✓] Her katmanın sorumluluğu tek ve açıktır
[✓] Servis sınırları arayüzle tanımlanır, implementasyonla değil
[✓] Önbellekleme, servis katmanında şeffaf bir şekilde uygulanır
[✓] Hata yönetimi merkezi bir kaideden yürür
[✓] DTO'lar, domain nesneleriyle API sözleşmesini birbirinden koparır
[✓] Veri zenginleştirme (enrichment), iş mantığından ayrı bir katmanda tutulur
[✓] MapStruct ile derleme zamanında doğrulanan, yansımasız tip dönüşümleri
[✓] Her cache key semantik olarak adlandırılmıştır
```

---

## 🧩 Tasarım Şablonları ve Neden Tercih Edildikleri

Şablon kullanımı burada bir prestij meselesi değil, somut bir iş yükünü hafifletme meselesidir.

### 👁️ Observer (Gözlemci)

**Kullanıldığı yer:** `TactiCoreWebUI` — `MatchServiceImpl`

Canlı maçların durum değişiklikleri 📡, servis çağrısı tamamlandıktan sonra ilgili bileşenlere bildirilmek istenir. Bunun için servis sınıfını abone listesiyle doğrudan bağlamak yerine, `MatchStatusObserver` arayüzü üzerinden loosely-coupled bir bildirim zinciri kurulmuştur.

```java
public interface MatchStatusObserver {
    void onMatchStatusChange(ResultMatchDto match);
}
```

Bu sayede, servis ileride farklı aboneler (istatistik güncelleyici 📈, bildirim motoru vb.) kazandığında, mevcut kod tek satır değişmez.

---

### 💎 Enricher (Zenginleştirici)

**Kullanıldığı yer:** `TactiCoreWebAPI` — `FixtureEnricher`, `MatchEnricher`, `StandingEnricher` vb.

MongoDB bir ilişkisel veritabanı değildir; `JOIN` yoktur. Bir `Match` belgesi yalnızca `homeTeamId` ve `awayTeamId` saklar 🔗. API'nin istemciye takım adı ve logosuyla birlikte zenginleştirilmiş bir DTO döndürmesi gerektiğinde, bu sorumluluğu `ServiceImpl` içine gömmek servis sınıfını hem şişirir hem de test edilemez kılar.

Bunun yerine her domain için ayrı bir `*Enricher` sınıfı çıkarılmıştır. Servis, ham DTO'yu alır; Enricher onu zenginleştirir 🪄; servis zengin DTO'yu döner.

---

### 🏗️ Builder (İnşacı)

**Kullanıldığı yer:** `FixtureSummaryBuilder`, `DashboardSummaryBuilder`

Özet verilerin (hafta özeti 📅, dashboard istatistikleri) hesaplanması 🧮, birden fazla koleksiyon üzerinden yürüyen birleşik işlemler gerektirir. Bu hesaplamayı servis katmanına bırakmak hem karmaşıklığı artırır hem de tekrar kullanımı engeller. Builder sınıflar; ham liste verilerini alır, istatistiksel hesaplamayı yapar ve doğrudan view'da kullanılacak özet DTO'yu üretir.

---

### 📋 Template Method

**Kullanıldığı yer:** `AbstractApiService<T>`, `AbstractBaseController`

HTTP istemci tekrarını ortadan kaldırmak için `AbstractApiService` temel sınıfı `getForObject()`, `getForList()`, `postForObject()`, `put()`, `delete()` şablonlarını barındırır. Her servis implementasyonu yalnızca kendi endpoint'ini ve dönüş tipini bildirir 🎯. Aynı disiplin controller katmanında da `AbstractBaseController` ile uygulanmıştır: hata yakalama 🚨, model yönetimi ve günlük loglama bu katmandan miras alınır.

---

### 🗃️ Repository

**Kullanıldığı yer:** `TactiCoreWebAPI` — tüm veri erişim katmanı

Spring Data MongoDB'nin `MongoRepository` soyutlaması, kendi içinde zaten bu şablonu uygular. Sistem bunu doğrudan benimser; özel sorgu yöntemleri ise interface'e eklenen `findBy*` imzalarıyla derleme zamanında kontrol edilir 🔍. Veri katmanına ham sorgu yazmak en son çaredir.

---

### 🗺️ MapStruct — Tip Güvenli Dönüşüm

`reflection`-tabanlı kütüphanelerin çalışma zamanı başarısızlıklarına karşı derleme zamanında dönüşüm doğrulaması tercih edilmiştir. MapStruct 1.5.5, her domain için ayrı bir `*Mapper` arayüzü üretir. Dönüşüm kodu, derleme sırasında oluşturulur ⚡; çalışma zamanında kör bir `BeanUtils.copyProperties` çağrısı yoktur.

---

## ⚡ Önbellek Mimarisi

> *Bir sistemin performansı yalnızca ne kadar hızlı veri ürettiğiyle değil, ne kadar akıllıca veri yeniden kullandığıyla ölçülür.*

Redis 🔴, `spring-boot-starter-data-redis` ve `spring-boot-starter-cache` kombinasyonuyla entegre edilmiştir. Varsayılan TTL **10 dakikadır** ⏳. JSON serileştirme `GenericJackson2JsonRedisSerializer` ile sağlanır; `null` değerler saklanmaz.

```
Cache Key Nizamı:
─────────────────────────────────────────────
  teams:all              → Tüm takım listesi
  teams:{id}             → Tekil takım
  matches:all            → Tüm maçlar
  matches:{id}           → Tekil maç
  fixtures:all           → Tüm fikstür
  fixtures:{week}        → Haftalık fikstür
  fixtures:live          → Canlı fikstür
  fixtures:featured      → Öne çıkan fikstürler
  fixtures:week_summary  → Hafta özeti
─────────────────────────────────────────────
```

Veri mutasyonlarında ilgili cache scope'u `@CacheEvict` ile temizlenir 🧹. Maç güncellemesi hem `matches` hem `fixtures` önbelleklerini boşaltır; bu, `@Caching` ile birleşik kural olarak tanımlanmıştır.

---

## 🌐 REST API Özeti

API, `http://localhost:8080` adresinde çalışır 🚀. OpenAPI 3.0 belgesi `/swagger-ui.html` üzerinden erişilebilir.

| Prefix | Varlık | Temel İşlemler |
|---|---|---|
| `/team` 🏟️ | Team | CRUD, tekil okuma |
| `/player` 👤 | Player | Listeleme |
| `/match` ⚽ | Match | CRUD + durum bazlı filtreler + canlı sayım |
| `/matchevent` 📋 | MatchEvent | CRUD + maça göre filtreleme + dashboard özeti |
| `/standing` 🏆 | Standing | CRUD |
| `/fixture` 📅 | Fixture | Listeleme, hafta bazlı filtreleme, özet |
| `/news` 📰 | News | CRUD + ana haber |

Tüm yanıtlar `ApiResponse<T>` zarfına sarılır 📦:

```json
{
  "success": true,
  "message": "İşlem başarılı",
  "data": { ... },
  "timestamp": "2026-05-08T10:00:00"
}
```

---

## 🖼️ Sunum Katmanı

`TactiCoreWebUI`, sunucu taraflı Thymeleaf render motoruyla çalışır. API ile tüm iletişim, arayüz uygulamasının servis katmanındaki HTTP istemcileri üzerinden yürür. Hiçbir frontend JavaScript çatısı bağımlılığı yoktur 🚫.

### 📐 Sayfa Modelleri (ViewModel)

Her sayfa için bir `*ViewModel` nesnesi oluşturulur. Thymeleaf template'i doğrudan entity veya DTO listesiyle değil, bu model üzerinden bağlanır 🔗. Bu ayrımın faydası şudur: API sözleşmesi değiştiğinde template dokunulmaz; yalnızca controller güncellenir.

```
DashboardViewModel     → liveCount, finishedCount, recentMatches, topScorers
FixturePageViewModel   → fixtures, currentWeek, previousWeek, nextWeek, summary
MatchDetailViewModel   → match, events
```

### 🎨 Tema Sistemi

Arayüz, CSS custom properties tabanlı kendi tasarım sistemini taşır. Dış bir UI kütüphanesine bağımlı değildir 💪.

```css
/* Temel değişkenler */
--bg-dark:    #080d18   /* Ana zemin */
--bg-card:    #111827   /* Kart yüzeyi */
--green:      #00e676   /* Birincil aksan */
--red:        #ff1744   /* Uyarı, canlı göstergeler */
--gold:       #ffd600   /* Yıldız oyuncu, öne çıkan */
--text:       #e8eaed   /* Birincil metin */
--text-muted: #9aa0a6   /* İkincil metin */
```

---

## 🛠️ Teknoloji Yığını

Her seçim için bir gerekçe bulunur; yalnızca popülerlik yeterli kaide değildir 🧐.

| Teknoloji | Versiyon | Vazifesi | Neden |
|---|---|---|---|
| **Spring Boot** ☕ | 3.5.14 | Uygulama çerçevesi | Olgunluğu ve ekosistemi kanıtlanmıştır; konfigürasyonu minimize eder |
| **Java** ☕ | 17 (LTS) | Çalışma ortamı | Uzun dönem destek, modern dil özellikleri, güvenilir tooling |
| **MongoDB** 🍃 | 7.0 | Birincil veri saklama | Belge modeli, futbol verisinin doğal yapısına uygundur; ilişkisel şemada join yükü taşımaz |
| **Redis** 🔴 | 7.2-alpine | Önbellek katmanı | Düşük gecikmeli okuma; session verisi yoktur, salt önbellek olarak kullanılır |
| **MapStruct** 🗺️ | 1.5.5 | DTO ↔ Entity dönüşümü | Derleme zamanı kod üretimi; reflection zafiyeti yoktur |
| **Thymeleaf** 🌿 | 3.1.5 | Şablon motoru | Sunucu taraflı render; bağımlılık minimizasyonu |
| **Thymeleaf Layout Dialect** 🧱 | — | Şablon mirası | Tekrar eden layout kodunu tek noktaya çeker |
| **springdoc-openapi** 📖 | 2.6.0 | API belgeleme | Swagger UI entegrasyonu; sözleşme dokümantasyonu |
| **Docker Compose** 🐳 | — | Altyapı | MongoDB ve Redis'i izole, tekrar üretilebilir ortamda ayağa kaldırır |
| **Lombok** 🏷️ | — | Tekrar kodu azaltma | Boilerplate getter/setter/constructor üretimi; iş mantığına odaklanmayı destekler |

---

## 📁 Kod Organizasyonu

```
TactiCore/
├── Server/
│   └── TactiCoreWebAPI/
│       └── src/main/java/tr/com/huseyinaydin/
│           ├── common/          → ApiResponse<T> genel yanıt zarfı
│           ├── config/          → MongoDB, Redis, OpenAPI, CORS yapılandırması
│           ├── controller/      → 7 REST controller (ince katman; iş mantığı taşımaz)
│           ├── dto/             → Create/Update/Result DTO aileleri (6 domain × 3)
│           ├── entity/          → 6 MongoDB belgesi + BaseEntity + enum'lar
│           ├── exception/       → GlobalExceptionHandler, ResourceNotFoundException
│           ├── mapper/          → 7 MapStruct arayüzü
│           ├── repository/      → 6 MongoRepository arayüzü
│           └── service/
│               ├── *.java       → 7 servis arayüzü
│               ├── impl/        → 7 servis implementasyonu
│               └── business/    → Enricher sınıfları + Summary Builder'lar
│
├── Client/
│   └── TactiCoreWebUI/
│       └── src/main/java/tr/com/huseyinaydin/
│           ├── common/          → ApiResponse<T> istemci taraflı kopya
│           ├── config/          → RestTemplate, ObjectMapper, Thymeleaf
│           ├── constant/        → API endpoint sabitleri
│           ├── controller/      → 4 MVC controller + AbstractBaseController
│           ├── dto/             → API yanıtlarına karşılık gelen DTO'lar
│           ├── enums/           → Durum enum kopyaları (API ile uyumlu)
│           ├── exception/       → GlobalExceptionHandler
│           ├── model/           → ViewModel sınıfları (sayfa bazlı)
│           └── service/
│               ├── MatchStatusObserver.java  → Observer arayüzü
│               ├── AbstractApiService.java   → HTTP istemci tabanı
│               ├── ApiServiceFactory.java    → Servis fabrikası
│               ├── *.java                    → Servis arayüzleri
│               └── impl/                     → Servis implementasyonları
│
├── Server/TactiCoreWebAPI/src/main/resources/
│   └── docker-compose.yml       → Altyapı tanımı
│
├── seed.js                      → Tohum verisi (38 hafta, 380 maç, 80 oyuncu)
└── README.md
```

---

## 🧱 Katmanlı Mimari Disiplini

Sistemin iki modülü de aynı katman disiplinini izler 🎯:

```
┌─────────────────────────────────────────┐
│           Presentation Layer            │  ← Controller (ince katman)
├─────────────────────────────────────────┤
│             Service Layer               │  ← İş mantığı, önbellek, Observer
├─────────────────────────────────────────┤
│         Business / Enrichment Layer     │  ← Enricher, Builder (bağımsız)
├─────────────────────────────────────────┤
│            Mapping Layer                │  ← MapStruct (entity ↔ DTO)
├─────────────────────────────────────────┤
│           Repository Layer              │  ← Veri erişimi (Spring Data)
└─────────────────────────────────────────┘
```

Controller'lar iş mantığı **taşımaz** ⛔; yalnızca isteği servis katmanına iletir, modeli doldurur ve view adını döner. Bu kaidenin çiğnenmesi, ilk revizyonda ciddi teknik borç doğurur 💸.

---

## 🔒 Güvenlik ve Sürdürülebilirlik Esasları

### 🛡️ Güvenlik

- CORS konfigürasyonu `WebConfig` üzerinden merkezi yönetilir 🌐
- DTO düzeyinde `@Valid` anotasyonları ile girdi doğrulaması; ham entity asla dışarıya açılmaz 🚪
- `GlobalExceptionHandler` yığın izlerini istemciye sızdırmaz 🔐; anlamlı ve kontrollü yanıtlar üretir
- `null` değerler önbelleğe alınmaz; boş sonuçlar cache'i kirletmez 🧹

### ♻️ Sürdürülebilirlik

- Servis arayüzleri değiştirilmeden implementasyon değiştirilebilir 🔄
- Zenginleştirme mantığı domain servislerinden koparılmıştır; domain değişikliği enricher'ı etkilemez ✂️
- MapStruct mapper'ları derleme zamanında üretilir; çalışma zamanı hatası riski minimumdur ⚡
- Her katman bağımsız olarak test edilebilir 🧪

### 📈 Ölçeklenebilirlik

- Önbellek katmanı yatay ölçeklemeye hazır; Redis bağımsız bir servis olarak çalışır 🔀
- MongoDB şemasız yapısı, belge modelini yeniden derlemeden genişletmeye imkân verir 📂
- İki modülün bağımsız deployment'ı, talep altında ayrı ölçeklenmeyi mümkün kılar 🚀

### 🔧 Bakım Kolaylığı

- Endpoint sabitleri `ApiConstants` içinde tek noktada muhafaza edilir 📌
- Cache key'leri semantik ve kural temelli adlandırılmıştır; gelecekte debug yapılabilir 🔍
- Docker Compose ile altyapı bağımlılıkları versiyonlanmış ve yeniden üretilebilir 🐳

---

## 🌱 Tohum Verisi

Uygulama; gerçekçi bir lig ortamını simüle eden hazır bir tohum betiği ile birlikte gelir 📦.

```
seed.js kapsamı
────────────────────────────────────────────────────
  20  takım          (Türkiye Süper Ligi formasyonu)
  80  oyuncu         (her takımdan 4 oyuncu)
  380 maç            (38 haftalık tam tur-rövanş takvim)
  ~3000 maç olayı   (gol, kart, değişiklik dağılımı)
  20  puan durumu    (W/D/L hesaplamalı, form dizisi dahil)
  20  haber kaydı    (kategori ve ana sayfa bayrağı)
────────────────────────────────────────────────────
```

Takvim üretimi **yuvarlak turnuva (round-robin) algoritmasıyla** üretilmiştir 🔁. 20 takım × 38 hafta, hiçbir takımın aynı haftada iki kez karşılaşmadığı tutarlı bir program oluşturur.

---

## 🚀 Başlangıç

**Ön koşullar:** Java 17+, Maven 3.6+, Docker 🐳

```bash
# 1. Altyapıyı ayağa kaldır
docker-compose up -d

# 2. Veri tabanını tohumla
mongosh "mongodb://localhost:27017/tacticore" seed.js

# 3. API'yi başlat
cd Server/TactiCoreWebAPI
mvn spring-boot:run
# → http://localhost:8080

# 4. Arayüzü başlat
cd Client/TactiCoreWebUI
mvn spring-boot:run
# → http://localhost:8081
```

| Adres | İçerik |
|---|---|
| `http://localhost:8081` 🏠 | Dashboard |
| `http://localhost:8081/fixtures` 📅 | Fikstür |
| `http://localhost:8081/teams` 🏆 | Puan Durumu |
| `http://localhost:8081/matches` ⚽ | Maç Arşivi |
| `http://localhost:8080/swagger-ui.html` 📖 | API Belgesi |

---

## 💡 Vizyon ve Teknik Karakter

TactiCore; futbol verisinin kendi karmaşıklığını taşıyabilecek, içi boş bir scaffold değil, **gerçekten düşünülmüş** bir sistem olmak üzere inşa edilmiştir 🧠.

Buradaki her soyutlama bir yükü taşır. Observer, canlı veri akışını servis sınırlarına dağıtmadan yönetir 👁️. Enricher, MongoDB'nin yapısal sınırlılığını temiz bir yöntemle aşar 💎. Builder, karmaşık özet hesaplamalarını tek bir sorumluluk noktasında toplar 🏗️. Önbellek stratejisi, erişim örüntüsünü okuyarak şekillendirilmiştir ⚡.

Kullanılan her teknoloji, her şablon ve her mimari karar; ileride bu sisteme dokunan bir mühendise **neden** sorusunun yanıtını borçlu olmayan bir yapı bırakmayı amaçlar 🎯.

---

*Bu sistemin her satırı, bir mühendislik sorununun karşısında durarak ve geri adım atmadan yazılmıştır.* 🔥
