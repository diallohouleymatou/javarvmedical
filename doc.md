# Système de Gestion de Rendez-vous Médicaux - Documentation

## 1. Architecture du Système

### 1.1 Gestion des Threads et Concurrence

Le système utilise une architecture moderne basée sur la gestion concurrentielle des rendez-vous médicaux. Voici les points clés :

#### ReservationTask (Nouvelle implémentation)
- Utilise l'interface `Callable<String>` pour gérer les réservations de manière asynchrone
- Permet de retourner un résultat (succès ou échec) de manière thread-safe
- Gère les transactions de base de données de façon isolée

#### RendezVousService
- Utilise un `ExecutorService` avec un pool de 4 threads
- Permet de traiter plusieurs réservations simultanément
- Implémente un système de timeout de 5 secondes
- Gère proprement la fermeture des ressources

### 1.2 Avantages de l'Architecture

1. **Performance**
   - Traitement parallèle des demandes
   - Meilleure réactivité de l'interface utilisateur
   - Optimisation des ressources système

2. **Fiabilité**
   - Gestion des erreurs robuste
   - Isolation des transactions
   - Timeouts pour éviter les blocages

3. **Scalabilité**
   - Pool de threads configurable
   - Architecture extensible
   - Séparation claire des responsabilités

## 2. Flux de Réservation

1. L'utilisateur demande une réservation
2. Le système vérifie la disponibilité du créneau
3. Une tâche de réservation est créée et soumise au pool de threads
4. La réservation est traitée de manière asynchrone
5. Le résultat est retourné à l'utilisateur

## 3. Sécurité et Validation

- Vérification des données obligatoires
- Validation des dates (pas de dates dans le passé)
- Vérification de la disponibilité des créneaux
- Gestion des transactions avec rollback en cas d'erreur

## 4. Points Techniques Importants

### Gestion des Transactions
```java
em.getTransaction().begin();
// Opérations de réservation
em.getTransaction().commit();
```

### Gestion des Erreurs
```java
catch (Exception e) {
    if (em.getTransaction().isActive()) {
        em.getTransaction().rollback();
    }
    return "Erreur lors de la réservation : " + e.getMessage();
}
```

### Shutdown Propre
```java
public void shutdown() {
    executorService.shutdown();
    // Attente de la fin des tâches en cours
    if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
        executorService.shutdownNow();
    }
}
```
