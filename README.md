# 🏨 Agence Hôtelière – Backend

API REST de gestion d'une agence hôtelière, développée avec **Spring Boot**.
Elle permet de gérer les hôtels, leurs chambres, les clients et les réservations.

---

## 📋 Fonctionnalités

- **Hôtels** : création, consultation, modification et suppression (nom, adresse, ville, étoiles, contact)
- **Chambres** : gestion des chambres par hôtel (numéro, type, prix par nuit, capacité, disponibilité)
- **Clients** : gestion des clients avec email unique et date d'inscription automatique
- **Réservations** : réservation d'une chambre par un client (dates d'arrivée/départ, nombre de personnes, montant, statut)
- **Services** : services hôteliers associés aux réservations
- **Paiements** : suivi des paiements liés à une réservation
- **Validation** des données en entrée (Bean Validation)

---

## 🛠️ Technologies

| Technologie | Rôle |
|---|---|
| Java 17+ | Langage |
| Spring Boot 3 | Framework backend |
| Spring Data JPA / Hibernate | Persistance des données |
| Jakarta Validation | Validation des données |
| MySQL (XAMPP) | Base de données |
| Lombok | Réduction du code répétitif |
| Maven | Gestion des dépendances |
| Postman | Tests de l'API |

---

## 🗂️ Modèle de données

```
Hotel 1 ──── * Chambre 1 ──── * Reservation * ──── 1 Client
                                   │
                                   ├── * ──── * ServiceHotel
                                   └── 1 ──── * Paiement
```

- **TypeChambre** : `SIMPLE`, `DOUBLE`, `FAMILIALE`, `SUITE`
- **StatutReservation** : `EN_ATTENTE` (par défaut), …

---

## 📁 Structure du projet

```
src/main/java/com/agence_hoteliere/agence_hoteliere_backend/
├── controllers/   # Points d'entrée REST
├── dto/           # Objets de requête (ex. ChambreRequest)
├── models/        # Entités JPA et énumérations
├── repositories/  # Interfaces Spring Data JPA
└── services/      # Logique métier
```

---

## ⚙️ Installation et lancement

### Prérequis
- JDK 17 ou plus
- Maven (ou le wrapper `mvnw` inclus)
- MySQL (par exemple via XAMPP)

### 1. Cloner le projet
```bash
git clone https://github.com/Asma256/agence_hoteliere_BackEnd.git
cd agence_hoteliere_BackEnd
```

### 2. Configurer la base de données
Démarrer MySQL, puis adapter `src/main/resources/application.properties` :
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/agence_hoteliere?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=${DB_PASSWORD:}
spring.jpa.hibernate.ddl-auto=update
server.port=8081
```

### 3. Lancer l'application
```bash
./mvnw spring-boot:run
```
Sous Windows : `mvnw.cmd spring-boot:run`, ou lancer la classe `AgenceHoteliereBackEndApplication` depuis IntelliJ.

L'API est accessible sur : `http://localhost:8081`

---

## 🔗 Endpoints principaux

| Méthode | URL | Description |
|---|---|---|
| GET / POST | `/api/hotels` | Lister / créer des hôtels |
| GET / PUT / DELETE | `/api/hotels/{id}` | Consulter / modifier / supprimer un hôtel |
| GET / POST | `/api/chambres` | Lister / créer des chambres |
| GET / PUT / DELETE | `/api/chambres/{id}` | Consulter / modifier / supprimer une chambre |
| GET / POST | `/api/clients` | Lister / créer des clients |
| GET / PUT / DELETE | `/api/clients/{id}` | Consulter / modifier / supprimer un client |
| GET / POST | `/api/reservations` | Lister / créer des réservations |
| GET / PUT / DELETE | `/api/reservations/{id}` | Consulter / modifier / supprimer une réservation |

---

## 🧪 Exemples de requêtes

**Créer un hôtel** – `POST /api/hotels`
```json
{
  "nom": "Movenpick Resort Sousse",
  "adresse": "Boulevard du 14 Janvier",
  "ville": "Sousse",
  "etoiles": 5,
  "telephone": "+216 73 202 000",
  "email": "reservation@movenpick-sousse.tn"
}
```

**Créer une chambre** – `POST /api/chambres`
```json
{
  "numero": "204",
  "type": "DOUBLE",
  "prixParNuit": 320.50,
  "capacite": 2,
  "disponible": true,
  "hotelId": 1
}
```

**Créer un client** – `POST /api/clients`
```json
{
  "nom": "Trabelsi",
  "prenom": "Mariem",
  "email": "mariem.trabelsi@yahoo.fr",
  "telephone": "+216 98 765 432"
}
```

**Créer une réservation** – `POST /api/reservations`
```json
{
  "dateArrivee": "2026-11-21",
  "dateDepart": "2026-11-23",
  "nombrePersonnes": 2,
  "clientId": 1,
  "chambreId": 3
}
```

---

## 👩‍💻 Auteur

**Asma Boumaiza** – Étudiante en 3e année de Licence en Informatique de Gestion (BIS), ESPRIT School of Business

- GitHub : [Asma256](https://github.com/Asma256)
- LinkedIn : [asma-boumaiza](https://www.linkedin.com/in/asma-boumaiza-47bb03446)
