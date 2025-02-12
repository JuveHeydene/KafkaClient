# Producer Service API

## Introduction
Cette API REST permet aux microservices d'envoyer des messages dans des topics spécifiques.

Cette API expose un unique endpoint permettant de publier des messages dans un topic (Kafka dans notre cas d'espèce) en envoyant des données sous format JSON.

## Authentification
Toutes les requêtes nécessitent une clé API (api_key) incluse dans l'URL. Assurez-vous de protéger votre clé API pour éviter tout usage non autorisé.

## Routes

### 1. Publier un message dans un topic Kafka

- **Méthode :** `POST`
- **URL :** `/api/producer/push-content`
- **Description :** Cet endpoint permet d'envoyer un message JSON dans un topic Kafka spécifié.
- **Paramètres :**
  - `topic` (Query Param) : Le nom du topic Kafka où le message sera envoyé.
  - `request` (Body) : L'objet JSON contenant les données à publier.

#### Exemple de requête
```bash
curl -X POST "http://localhost:8080/api/producer/push-content?topic=mon-topic&apiKey=MA_CLE_SECRETTE" \
     -H "Content-Type: application/json" \
     -d '{"id": 123, "message": "Bonjour Kafka!"}'
```

#### Exemple de requête JSON (Body)
```json
{
  "id": 123,
  "message": "Bonjour Kafka!"
}
```

#### Réponse en cas de succès (`200 OK`)
```json
"Message envoyé au topic : mon-topic"
```

#### Réponse en cas d'erreur (`500 Internal Server Error`)
```json
"Erreur lors de l'envoi du message : <détails de l'erreur>"
```

## Remarque
- L'API utilise **Jackson** pour la sérialisation de l'objet en JSON.
- Il est recommandé de valider les données d'entrée avant l'envoi.
- Si le topic Kafka spécifié n'existe pas, un échec peut se produire.

