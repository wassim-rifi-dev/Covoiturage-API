# Covoiturage API

API REST de covoiturage développée avec **Spring Boot**. Elle permet aux conducteurs de gérer leurs voitures et de publier des trajets (avec étapes intermédiaires), et aux passagers de rechercher et réserver des places.

## Sommaire

- [Stack technique](#stack-technique)
- [Fonctionnalités](#fonctionnalités)
- [Prérequis](#prérequis)
- [Installation et lancement](#installation-et-lancement)
- [Documentation de l'API](#documentation-de-lapi)
- [Endpoints](#endpoints)
- [Règles métier](#règles-métier)
- [Structure du projet](#structure-du-projet)
- [Tests](#tests)

## Stack technique

| Outil | Rôle |
|---|---|
| Java 17 | Langage |
| Spring Boot 4 (Web MVC, Data JPA, Validation) | Framework |
| PostgreSQL | Base de données |
| Docker Compose | Lancement de la base en local |
| MapStruct + Lombok | Mapping DTO / entités et réduction du code répétitif |
| springdoc-openapi | Documentation Swagger UI |
| JUnit / Mockito | Tests unitaires |

## Fonctionnalités

- **Conducteurs** : inscription, mise à jour, recherche (nom, email, téléphone, numéro de permis)
- **Voitures** : ajout d'une voiture à un conducteur, consultation, modification, suppression
- **Trajets** : publication avec étapes (villes, horaires, prix), annulation, clôture, recherche multicritère
- **Passagers** : inscription, mise à jour, recherche
- **Réservations** : réservation de places entre deux étapes d'un trajet, annulation, historique
- Pagination et tri sur toutes les listes (`?page=0&size=10&sort=id,asc`)
- Gestion centralisée des erreurs (`GlobalExceptionHandler`)

## Prérequis

- JDK 17+
- Docker et Docker Compose
- Maven (ou le wrapper `./mvnw` fourni)

## Installation et lancement

1. **Cloner le dépôt**

   ```bash
   git clone git@github.com:wassim-rifi-dev/Covoiturage-API.git
   cd Covoiturage-API
   ```

2. **Configurer les variables d'environnement** à partir du modèle :

   ```bash
   cp .env.example .env
   ```

   ```properties
   DB_NAME=covoiturage
   DB_USER=postgres
   DB_PASSWORD=postgres
   ```

3. **Lancer l'application**

   ```bash
   ./mvnw spring-boot:run
   ```

   Grâce à `spring-boot-docker-compose`, le conteneur PostgreSQL défini dans `compose.yaml` démarre automatiquement. Vous pouvez aussi le lancer à la main avec `docker compose up -d`.

L'API est disponible sur `http://localhost:8080`. Le schéma de la base est créé automatiquement (`ddl-auto: update`). Les logs sont écrits dans `logs/rideflow.log`.

## Documentation de l'API

Une fois l'application lancée :

- Swagger UI : <http://localhost:8080/swagger-ui.html>
- Spécification OpenAPI (JSON) : <http://localhost:8080/v3/api-docs>

## Endpoints

### Conducteurs — `/api/drivers`

| Méthode | Route | Description |
|---|---|---|
| POST | `/api/drivers` | Créer un conducteur |
| GET | `/api/drivers` | Rechercher des conducteurs (`fullName`, `email`, `phone`, `licenseNumber`) |
| GET | `/api/drivers/{id}` | Détails d'un conducteur |
| PUT | `/api/drivers/{id}` | Modifier un conducteur |
| POST | `/api/drivers/{driverId}/cars` | Ajouter une voiture |
| GET | `/api/drivers/{driverId}/cars` | Voitures d'un conducteur |
| POST | `/api/drivers/{driverId}/trips` | Publier un trajet |
| PATCH | `/api/drivers/{driverId}/trips/{tripId}/cancel` | Annuler un trajet |
| PATCH | `/api/drivers/{driverId}/trips/{tripId}/complet` | Marquer un trajet comme terminé |
| GET | `/api/drivers/{driverId}/bookings` | Réservations reçues par le conducteur |
| GET | `/api/drivers/{driverId}/trips/{tripId}/bookings` | Réservations d'un trajet |

### Voitures — `/api/cars`

| Méthode | Route | Description |
|---|---|---|
| GET | `/api/cars/{id}` | Détails d'une voiture |
| PUT | `/api/cars/{id}` | Modifier une voiture |
| DELETE | `/api/cars/{id}` | Supprimer une voiture (impossible si elle a des trajets) |

### Trajets — `/api/trips`

| Méthode | Route | Description |
|---|---|---|
| GET | `/api/trips` | Rechercher des trajets (`departureCity`, `arrivalCity`, `departureDate`, `maxPrice`, `minSeats`, `waypointCity`) |
| GET | `/api/trips/{id}` | Détails d'un trajet |

### Passagers — `/api/passengers`

| Méthode | Route | Description |
|---|---|---|
| POST | `/api/passengers` | Créer un passager |
| GET | `/api/passengers` | Rechercher des passagers (`fullName`, `email`, `phone`) |
| PUT | `/api/passengers/{id}` | Modifier un passager |
| POST | `/api/passengers/{passengerId}/bookings` | Réserver des places |
| GET | `/api/passengers/{passengerId}/bookings` | Réservations d'un passager |
| PATCH | `/api/passengers/{passengerId}/bookings/{bookingId}/cancel` | Annuler une réservation |

## Règles métier

**Trajets**
- La date de départ doit être dans le futur.
- Un trajet a au moins 2 étapes, et une ville ne peut apparaître qu'une fois.
- Le prix de la première étape vaut `0`, puis il augmente strictement d'une étape à l'autre.
- Le nombre de places proposées ne dépasse pas la capacité de la voiture, et la voiture doit appartenir au conducteur.
- Statuts : `PUBLISHED`, `FULL`, `COMPLETED`, `CANCELLED`.

**Réservations**
- Seul un trajet `PUBLISHED`, futur et avec des places restantes peut être réservé.
- Le nombre de places demandé doit être disponible.
- L'annulation est possible jusqu'à **24 h** avant l'heure de passage au point de prise en charge.
- Annuler une réservation sur un trajet `FULL` le repasse à `PUBLISHED`.
- Statuts : `CONFIRMED`, `CANCELLED`.

**Utilisateurs**
- L'email est unique, ainsi que le numéro de permis (conducteur) et la plaque d'immatriculation (voiture).

## Structure du projet

Le code est organisé par fonctionnalité (*package by feature*) :

```
src/main/java/ma/youcode/covoiturage
├── common/            # Exceptions partagées et GlobalExceptionHandler
├── config/            # Configuration OpenAPI
└── feature/
    ├── booking/       # Réservations
    ├── car/           # Voitures
    ├── driver/        # Conducteurs
    ├── passenger/     # Passagers
    ├── trip/          # Trajets et étapes (waypoints)
    └── user/          # Entité User commune (héritage JOINED)
```

Chaque fonctionnalité suit la même découpe : `controller`, `dto` (request / response), `mapper`, `model` (entity / enums), `repository`, `service`, et selon le cas `exception` et `specification`.

## Tests

```bash
./mvnw test
```

Les tests unitaires couvrent la logique des services de réservation et de trajets (`src/test/java/.../feature`).

## Auteur

**Wassim Rifi** — <https://wassimrifi.vercel.app/>
