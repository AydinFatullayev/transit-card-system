# 🚇 Transit Card System

Микросервисная система для управления транспортными картами, поездками и электронным кошельком пассажира.

Проект разработан как учебный **pet-project** для практики Java, Spring Boot, микросервисной архитектуры, REST API, PostgreSQL, Docker и взаимодействия между сервисами.

---

## 📌 О проекте

Система моделирует работу транспортной карты.

Пассажир использует карту для входа и выхода со станции:

```text
Пассажир
   │
   │ прикладывает карту
   ↓
┌──────────────┐
│  Trip Service│
└──────┬───────┘
       │
       ↓
   Wallet Service
       │
       ↓
 Payment Service
```

При входе система фиксирует станцию и время начала поездки.

При выходе определяется количество пройденных станций и рассчитывается стоимость поездки.

Также система поддерживает электронный кошелёк, пополнение, снятие средств и перевод денег между картами.

---

# 🏗 Архитектура

Проект состоит из четырёх независимых микросервисов:

```text
┌───────────────────┐
│   card-service    │
│       :8081       │
└─────────┬─────────┘
          │
          │ card information
          ↓
┌───────────────────┐
│   trip-service    │
│       :8082       │
└─────────┬─────────┘
          │
          │ REST
          ↓
┌───────────────────┐
│  wallet-service   │
│       :8083       │
└─────────┬─────────┘
          │
          │ REST
          ↓
┌───────────────────┐
│ payment-service   │
│       :8084       │
└───────────────────┘
```

Каждый микросервис имеет собственную PostgreSQL database:

```text
card-service    → card_db
trip-service    → trip_db
wallet-service  → wallet_db
payment-service → payment_db
```

Сервисы запускаются в Docker и взаимодействуют между собой внутри Docker-сети.

---

# ⚙️ Возможности

## 💳 Управление картами

Поддерживаются:

* создание транспортной карты;
* получение карты по UUID;
* поиск карты по номеру;
* получение списка карт;
* блокировка карты;
* разблокировка карты;
* статусы карты:

  * `ACTIVE`
  * `BLOCKED`

Заблокированная карта не должна использоваться для совершения поездок.

---

## 🚇 Поездки

Поддерживаются:

* вход на станцию;
* выход со станции;
* автоматическое определение количества остановок;
* расчёт стоимости поездки;
* бесплатная поездка при входе и выходе на одной станции;
* максимальная стоимость поездки;
* принудительное завершение незакрытой поездки.

Пример:

```text
Станция 2 → Станция 5

Количество остановок: 3

Стоимость:
3 × 0.30 = 0.90
```

Максимальная стоимость поездки ограничена установленным тарифом.

Если пассажир вошёл в систему, но не выполнил выход, поездку можно принудительно завершить с максимальным тарифом.

---

# 💰 Электронный кошелёк

Каждая транспортная карта может иметь собственный кошелёк.

Поддерживаются:

* создание кошелька;
* просмотр баланса;
* пополнение;
* снятие средств;
* перевод денег между картами;
* списание стоимости поездки;
* история операций кошелька.

Перевод между картами:

```text
Card A
Balance: 10.00
     │
     │ transfer 3.00
     ↓
Card B

Card A: 7.00
Card B: +3.00
```

Система не позволяет выполнить обычный перевод или снятие средств при недостаточном балансе.

---

# 💸 Платежи

`payment-service` отвечает за хранение информации о финансовых операциях.

Поддерживаемые типы:

```text
TOP_UP
TRIP_PAYMENT
TRANSFER_OUT
TRANSFER_IN
WITHDRAW
```

Для каждого платежа сохраняются:

* UUID;
* UUID карты;
* тип операции;
* сумма;
* статус;
* дата и время создания.

Поддерживаемые статусы:

```text
COMPLETED
FAILED
```

---

# 🔄 Взаимодействие сервисов

Сейчас для синхронного взаимодействия используется REST.

Например, завершение поездки:

```text
trip-service
      │
      │ calculate fare
      ↓
wallet-service
      │
      │ charge trip
      ↓
wallet database
```

Финансовые операции wallet-service также взаимодействуют с:

```text
wallet-service
      │
      │ REST
      ↓
payment-service
```

Таким образом, каждый сервис отвечает за свою область данных.

---

# 🗄 Базы данных

Используется PostgreSQL.

В проекте создан отдельный database для каждого микросервиса:

| Service         | Database     |
| --------------- | ------------ |
| card-service    | `card_db`    |
| trip-service    | `trip_db`    |
| wallet-service  | `wallet_db`  |
| payment-service | `payment_db` |

Микросервисы не используют общую таблицу или общую бизнес-модель базы данных.

---

# 🐳 Docker

Весь проект можно запускать через Docker Compose.

Docker используются для:

* PostgreSQL;
* card-service;
* trip-service;
* wallet-service;
* payment-service.

Каждый Spring Boot сервис имеет собственный `Dockerfile`.

Сервисы собираются в Docker images и запускаются как отдельные containers.

Структура:

```text
transit-card-system/
│
├── card-service/
│   └── Dockerfile
│
├── trip-service/
│   └── Dockerfile
│
├── wallet-service/
│   └── Dockerfile
│
├── payment-service/
│   └── Dockerfile
│
├── docker-compose.yml
├── start.bat
├── build.bat
└── stop.bat
```

### Запуск

Для обычного запуска:

```bash
start.bat
```

Для пересборки после изменения Java-кода:

```bash
build.bat
```

Для остановки системы:

```bash
stop.bat
```

Docker Compose не удаляет PostgreSQL volume при обычном `docker compose down`, поэтому данные базы сохраняются.

---

# 🛠 Технологии

## Backend

* Java 25
* Spring Boot
* Spring Web
* Spring Data JPA
* Hibernate
* Bean Validation
* Lombok

## Database

* PostgreSQL

## Communication

* REST API
* Spring `RestClient`

## DevOps / Environment

* Docker
* Docker Compose
* Maven
* Git
* GitHub

## Development

* IntelliJ IDEA
* Postman

---

# 📡 Основные API

## Card Service — `8081`

```text
POST   /api/cards
GET    /api/cards
GET    /api/cards/{id}
GET    /api/cards/number/{cardNumber}
PATCH  /api/cards/{id}/block
```

---

## Trip Service — `8082`

```text
POST /api/stations
GET  /api/stations

POST /api/trips/entry
POST /api/trips/exit

POST /api/trips/{cardId}/force-complete

GET /api/trips/{id}
```

---

## Wallet Service — `8083`

```text
POST /api/wallets
GET  /api/wallets/{cardId}

POST /api/wallets/{cardId}/top-up
POST /api/wallets/{cardId}/withdraw
POST /api/wallets/{cardId}/transfer

POST /api/wallets/{cardId}/charge-trip
```

---

## Payment Service — `8084`

```text
POST /api/payments

GET /api/payments/{id}

GET /api/payments/card/{cardId}
```

---

# 📜 История разработки

## Commit 1 — Initial version

Создана базовая структура проекта.

Добавлены четыре Spring Boot микросервиса:

```text
card-service
trip-service
wallet-service
payment-service
```

Создана базовая структура PostgreSQL databases.

---

## Commit 2 — Card Service

Реализован `card-service`.

Добавлены:

* сущность транспортной карты;
* UUID карты;
* номер карты;
* статусы карты;
* создание карты;
* получение карты;
* поиск по номеру;
* получение списка карт;
* блокировка карты.

---

## Commit 3 — Trip Service

Реализован `trip-service`.

Добавлены:

* станции;
* последовательность станций;
* начало поездки;
* завершение поездки;
* расчёт количества остановок;
* расчёт стоимости;
* максимальный тариф;
* принудительное завершение поездки;
* сохранение поездок в PostgreSQL.

---

## Commit 4 — Wallet Service

Реализован `wallet-service`.

Добавлены:

* кошельки карт;
* баланс;
* пополнение;
* снятие средств;
* перевод между картами;
* история операций кошелька;
* списание стоимости поездки.

---

## Commit 5 — Service Integration

Реализовано взаимодействие микросервисов.

Добавлены REST-вызовы:

```text
trip-service
      ↓
wallet-service
```

и:

```text
wallet-service
      ↓
payment-service
```

Теперь поездка может автоматически взаимодействовать с кошельком карты.

---

## Commit 6 — Payment Service

Реализован `payment-service`.

Добавлены:

* сущность платежа;
* типы финансовых операций;
* статусы платежей;
* сохранение платежей;
* получение платежа;
* получение истории платежей по карте.

---

## Commit 7 — Dockerization

Проект переведён на Docker.

Добавлены:

* Dockerfile для каждого микросервиса;
* Docker Compose;
* PostgreSQL container;
* отдельные databases для сервисов;
* Docker networking;
* автоматическая сборка Spring Boot приложений;
* запуск всего проекта одной командой.

Также добавлены:

```text
start.bat
build.bat
stop.bat
```

для удобного управления проектом.

---

# 🎯 Цели проекта

Основная цель проекта — получить практический опыт разработки распределённого backend-приложения.

В процессе разработки изучаются и применяются:

* микросервисная архитектура;
* разделение ответственности между сервисами;
* REST API;
* межсервисное взаимодействие;
* PostgreSQL;
* JPA/Hibernate;
* Docker;
* Docker Compose;
* работа с Git;
* обработка бизнес-логики;
* валидация данных;
* работа с финансовыми операциями.

---

# 🚧 Планируемое развитие

Проект находится в разработке.

Возможные следующие этапы:

* полноценная проверка статусов карт между сервисами;
* единая обработка ошибок;
* история действий пользователя;
* интеграция RabbitMQ для асинхронных событий;
* API Gateway;
* более подробное логирование;
* автоматические тесты;
* мониторинг микросервисов.

Новые технологии будут добавляться только там, где они имеют практический смысл для архитектуры проекта.

---

# 👨‍💻 Автор

**Aydın Fatullayev**

GitHub:
https://github.com/AydinFatullayev

Проект создан в образовательных целях как практический pet-project для изучения Java, Spring Boot и микросервисной архитектуры.
