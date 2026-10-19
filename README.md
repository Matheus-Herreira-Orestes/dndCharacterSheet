# D&D Character Sheet API

A Spring Boot REST API for a Dungeons & Dragons character-sheet application. It provides JWT-based authentication, a local catalog of official D&D 5e content, homebrew weapons, and character-photo uploads.

On its first successful startup, the application imports official spells, weapons, classes, subclasses, and class levels from [D&D 5e API](https://www.dnd5eapi.co/). If that service is unavailable, the application still starts and retries the import on a later startup.

## Stack

- Java 21 and Spring Boot 3.5
- Spring Web, Spring Data JPA, Spring Security, and WebClient
- PostgreSQL 16
- JWT authentication using `java-jwt`
- Docker Compose and pgAdmin

## Prerequisites

Choose one of the following:

- Java 21 and Docker (for the database), or
- Docker and Docker Compose (for the full stack).

## Run locally

Start PostgreSQL:

```bash
docker compose up -d postgres
```

Set the database connection and a strong JWT signing secret. In PowerShell:

```powershell
$env:SPRING_DATASOURCE_URL = "jdbc:postgresql://localhost:5432/dnd_db"
$env:SPRING_DATASOURCE_USERNAME = "postgres"
$env:SPRING_DATASOURCE_PASSWORD = "123"
$env:JWT_SECRET = "replace-with-a-long-random-secret"
```

Then start the application:

```powershell
.\mvnw.cmd spring-boot:run
```

The API runs at `http://localhost:8080`.

## Run with Docker Compose

The Docker image expects an already-built JAR in `target/`, so build it first:

```powershell
.\mvnw.cmd clean package -DskipTests
docker compose up --build
```

This starts:

| Service | Address |
| --- | --- |
| API | `http://localhost:8080` |
| PostgreSQL | `localhost:5432` |
| pgAdmin | `http://localhost:5050` |

The bundled development database credentials are `postgres` / `123`; pgAdmin uses `admin@admin.com` / `admin`. Change these values before deploying anywhere other than local development. Set `JWT_SECRET` to a strong value in the `app` service environment for production use.

## Configuration

| Variable | Required | Description |
| --- | --- | --- |
| `SPRING_DATASOURCE_URL` | Yes | PostgreSQL JDBC URL |
| `SPRING_DATASOURCE_USERNAME` | Yes | Database username |
| `SPRING_DATASOURCE_PASSWORD` | Yes | Database password |
| `JWT_SECRET` | Recommended | JWT signing secret; defaults to an insecure development value |
| `CHARACTER_PHOTOS_DIR` | No | Directory for uploaded photos; defaults to `uploads/character-photos` |

Uploaded photos are limited to 5 MB per file/request.

## Authentication

All endpoints require a bearer token except `POST /auth/login` and `POST /auth/register`.

Register a user:

```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{"Login":"adventurer","Password":"change-me","Role":"USER"}'
```

Log in and copy the returned token:

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"Login":"adventurer","Password":"change-me"}'
```

Use it on protected requests:

```bash
curl http://localhost:8080/weapon \
  -H "Authorization: Bearer <token>"
```

`GET /weapon/all` is restricted to users with the `ADMIN` role.

## API overview

| Method | Route | Purpose |
| --- | --- | --- |
| `POST` | `/auth/register` | Create a user (`USER` or `ADMIN`) |
| `POST` | `/auth/login` | Authenticate and receive a JWT |
| `POST` | `/weapon` | Create a homebrew weapon |
| `GET` | `/weapon` | List official/public weapons and the authenticated user's weapons |
| `GET` | `/weapon/{id}` | Get an available weapon by ID |
| `GET` | `/weapon/all` | List every weapon (admin only) |
| `POST` | `/characters/{characterId}/photo` | Upload or replace an owned character's photo (`multipart/form-data`, field: `photo`) |
| `GET` | `/characters/{characterId}/photo` | Download an owned character's photo |
| `GET` | `/api/dnd/spells/{index}` | Fetch a spell from the upstream D&D 5e API |
| `GET` | `/api/dnd/classes/{index}` | Fetch a class from the upstream D&D 5e API |
| `GET` | `/api/dnd/classes/{index}/levels` | Fetch class levels from the upstream D&D 5e API |

### Create a homebrew weapon

```bash
curl -X POST http://localhost:8080/weapon \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Stormblade",
    "isPublic": false,
    "weight": "3 lb",
    "cost": "75 gp",
    "properties": ["VERSATILE"],
    "proficiencies": ["MARTIAL_WEAPONS"],
    "weaponDamage": [
      { "quantity": 1, "diceType": "D8", "damageType": "SLASHING" }
    ]
  }'
```

## Tests and build

```powershell
.\mvnw.cmd test
.\mvnw.cmd clean package
```

## License

This project is released under [CC0 1.0 Universal](LICENSE).
