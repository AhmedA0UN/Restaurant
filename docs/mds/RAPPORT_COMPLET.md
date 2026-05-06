# RAPPORT DE PROJET : SYSTÈME DE GESTION DE RESTAURANT EN JAVA

---

## 📋 INFORMATIONS DU PROJET

**Titre du Projet :** Système de Gestion d'un Restaurant  
**Module :** Programmation Orientée Objet (POO)  
**Enseignant Cours :** Sofiane HACHICHA  
**Enseignante TP :** Nesrine Akrout  
**Auditoire :** CPI2  
**Année Universitaire :** 2025-2026  
**Date de Remise :** Semaine 15  
**Langages Utilisés :** Java, SQL, SWING GUI  
**Base de Données :** MySQL  

---

## 1. INTRODUCTION

### 1.1 Contexte du Projet
Le projet vise à développer un système de gestion complet pour un restaurant utilisant les technologies Java modernes. L'application gère les menus, les plats, les commandes des clients ainsi que les interactions entre trois types d'utilisateurs : clients, serveurs et cuisiniers.

### 1.2 Objectifs Principaux
- Développer une application robuste et scalable en architecture MVC
- Implémenter une base de données MySQL bien structurée
- Fournir des interfaces graphiques intuitives avec SWING
- Respecter les principes de programmation orientée objet
- Implémenter le pattern DAO pour l'accès aux données

### 1.3 Technologies Utilisées
- **Langage :** Java (JDK 8+)
- **Framework GUI :** Java SWING
- **Base de Données :** MySQL 5.7+
- **Architecture :** MVC (Modèle-Vue-Contrôleur) + DAO
- **Gestion des Dépendances :** JDBC pour MySQL

---

## 2. ARCHITECTURE GÉNÉRALE

### 2.1 Architecture en Couches MVC + DAO

```
┌─────────────────────────────────────────┐
│           COUCHE PRÉSENTATION           │
│         (Vue - Java SWING GUI)          │
│  • Interfaces graphiques utilisateur    │
│  • Gestion des événements               │
└─────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────┐
│        COUCHE MÉTIER (Contrôleur)       │
│      (Logique applicative)              │
│  • Validation des données               │
│  • Orchestration des opérations         │
└─────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────┐
│         COUCHE DONNÉES (Modèle)         │
│    (Classes métier + DAO)               │
│  • Entités du domaine                   │
│  • Accès à la base de données           │
└─────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────┐
│          BASE DE DONNÉES MYSQL          │
│    • Tables structurées                 │
│    • Contraintes d'intégrité            │
└─────────────────────────────────────────┘
```

### 2.2 Structure des Répertoires

```
Restaurant/
├── src/
│   ├── controller/          # Couche contrôleur
│   │   ├── CommandeController.java
│   │   ├── PlatController.java
│   │   ├── PlatscommandeController.java
│   │   └── UtilisateurController.java
│   ├── dao/                 # Pattern DAO
│   │   ├── CommandeDAO.java
│   │   ├── PlatDAO.java
│   │   ├── PlatscommandeDAO.java
│   │   └── UtilisateurDAO.java
│   ├── model/               # Entités métier
│   │   ├── Utilisateur.java
│   │   ├── Client.java
│   │   ├── Serveuse.java
│   │   ├── Cuisinier.java
│   │   ├── Plat.java
│   │   ├── Menu.java
│   │   ├── Commande.java
│   │   ├── LigneCommande.java
│   │   ├── Platscommande.java
│   │   └── Facture.java
│   ├── view/                # Interfaces graphiques SWING
│   │   ├── LoginC.java              # Login Client
│   │   ├── LoginS.java              # Login Serveuse
│   │   ├── LoginCH.java             # Login Chef/Cuisinier
│   │   ├── Menu.java
│   │   ├── Commander.java
│   │   ├── Facture.java
│   │   └── [autres interfaces...]
│   ├── util/                # Utilitaires
│   │   ├── SingletonConnection.java  # Gestion connexion BD
│   │   └── Test.java
│   └── conf.properties      # Configuration JDBC
├── database/
│   └── schema.sql           # Schéma de la base de données
├── bin/                     # Fichiers compilés
└── docs/                    # Documentation
```

---

## 3. MODÈLE DE DONNÉES

### 3.1 Schéma Relationnel

#### Table `utilisateur`
```sql
CREATE TABLE utilisateur (
  idU INT AUTO_INCREMENT PRIMARY KEY,
  nomU VARCHAR(100) NOT NULL,
  mdpU VARCHAR(255) NOT NULL,
  role VARCHAR(30) NOT NULL    -- 'client', 'serveuse', 'chef'
);
```

**Rôles :**
- `client` : Client du restaurant
- `serveuse` : Serveur de restaurant
- `chef` : Cuisinier/Chef

---

#### Table `menu`
```sql
CREATE TABLE menu (
  idMenu INT AUTO_INCREMENT PRIMARY KEY,
  nomMenu VARCHAR(100) NOT NULL,
  description TEXT
);
```

**Exemple :** Menu Salé, Menu Sucré, Menu Spécial

---

#### Table `plat`
```sql
CREATE TABLE plat (
  idPlat INT AUTO_INCREMENT PRIMARY KEY,
  nomPlat VARCHAR(120) NOT NULL,
  prix DECIMAL(10,2) NOT NULL,
  idMenu INT NULL,
  typeMenu VARCHAR(50) NOT NULL,
  typePlat VARCHAR(50) NOT NULL,
  description TEXT,
  FOREIGN KEY (idMenu) REFERENCES menu(idMenu)
    ON DELETE SET NULL
    ON UPDATE CASCADE
);
```

**Attributs :**
- `idPlat` : Identifiant unique du plat
- `nomPlat` : Nom du plat
- `prix` : Prix en format décimal (10,2)
- `typeMenu` : Catégorie (ex: "Menu Salé")
- `typePlat` : Type (ex: "Entrée", "Plat Principal", "Dessert")
- `description` : Description détaillée
- `idMenu` : Référence au menu associé

---

#### Table `commande`
```sql
CREATE TABLE commande (
  idC INT AUTO_INCREMENT PRIMARY KEY,
  etat VARCHAR(30) NOT NULL,      -- 'en cours', 'reçue', 'servie'
  idU INT NOT NULL,
  date_commande DATETIME NOT NULL,
  prixC DECIMAL(10,2) DEFAULT 0,
  FOREIGN KEY (idU) REFERENCES utilisateur(idU)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);
```

**États Possibles :**
- `en cours` : Commande en préparation
- `reçue` : Commande reçue du client
- `servie` : Commande servie au client

---

#### Table `lignecommande`
```sql
CREATE TABLE lignecommande (
  idLigne INT AUTO_INCREMENT PRIMARY KEY,
  idC INT NOT NULL,
  idPlat INT NOT NULL,
  quantite INT NOT NULL,
  prixUnitaire DECIMAL(10,2) NOT NULL,
  FOREIGN KEY (idC) REFERENCES commande(idC)
    ON DELETE CASCADE,
  FOREIGN KEY (idPlat) REFERENCES plat(idPlat)
    ON DELETE CASCADE,
  CHECK (quantite > 0)
);
```

**Rôle :** Représente les articles d'une commande (relation many-to-many)

---

#### Table `platscommande`
```sql
CREATE TABLE platscommande (
  idPlat INT NOT NULL AUTO_INCREMENT,
  nomPlat VARCHAR(120) NOT NULL,
  quantite INT NOT NULL,
  idU INT NOT NULL,
  PRIMARY KEY (idPlat, idU),
  FOREIGN KEY (idPlat) REFERENCES plat(idPlat)
    ON DELETE CASCADE,
  FOREIGN KEY (idU) REFERENCES utilisateur(idU)
    ON DELETE CASCADE,
  CHECK (quantite > 0)
);
```

**Rôle :** Panier temporaire du client

---

#### Table `facture`
```sql
CREATE TABLE facture (
  idFacture INT AUTO_INCREMENT PRIMARY KEY,
  idC INT NOT NULL,
  date_facture DATETIME DEFAULT CURRENT_TIMESTAMP,
  montantTotal DECIMAL(10,2) DEFAULT 0,
  FOREIGN KEY (idC) REFERENCES commande(idC)
    ON DELETE CASCADE
);
```

**Rôle :** Enregistrement de facturation pour les commandes

---

### 3.2 Diagramme Entité-Association

```
┌─────────────┐
│ Utilisateur │
├─────────────┤
│ idU (PK)    │
│ nomU        │
│ mdpU        │
│ role        │
└─────────────┘
      ↑
      │ 1
      │
      ├─────────┐
      │         │
      │1        │1
      ↓         ↓
   Client   Serveuse   Cuisinier
      │         │          │
      │         │          │
      │         │          ├──→ Plat
      │         │          │
      └─────────┼──────────┘
                │
              0..*
                │
            Commande ───────── Facture
                │ 1
                │
              0..*
                │
          LigneCommande
                │
              1 │
                ↓
              Plat ←──────── Menu
                ↑
                │ 0..1
          Platscommande
```

---

## 4. ENTITÉS ET CLASSES MÉTIER

### 4.1 Classe `Utilisateur`

```java
public class Utilisateur {
    private int id;              // idU
    private String nom;          // nomU
    private String prenom;
    private String username;
    private String password;     // mdpU
    private String role;         // 'client', 'serveuse', 'chef'
    
    // Constructeurs
    public Utilisateur()
    public Utilisateur(int idU, String nomU, String mdpU, String role)
    public Utilisateur(int id, String nom, String prenom, 
                      String username, String password, String role)
    
    // Getters/Setters
    // ...
}
```

**Hiérarchie :**
- `Utilisateur` (classe mère)
  - `Client` : Client du restaurant
  - `Serveuse` : Personnel serveur
  - `Cuisinier` : Personnel cuisine

---

### 4.2 Classe `Plat`

```java
public class Plat {
    private int idPlat;
    private String nomPlat;
    private double prix;
    private String typeMenu;     // Catégorie (ex: "Menu Salé")
    private String typePlat;     // Type (ex: "Entrée", "Plat")
    private String description;
    private boolean disponible;
    private int idMenu;
    
    // Constructeurs et méthodes...
}
```

**Propriétés :**
- Identifiant unique
- Prix avec 2 décimales
- Classification par menu et type
- État de disponibilité

---

### 4.3 Classe `Menu`

```java
public class Menu {
    private int idMenu;
    private String nomMenu;
    private String description;
    
    // Méthodes de gestion
    public void ajouterPlat(Plat plat)
    public void modifierPlat(Plat plat)
    public void supprimerPlat(int idPlat)
    // ...
}
```

---

### 4.4 Classe `Commande`

```java
public class Commande {
    private int idCommande;
    private int idClient;
    private String etat;           // 'en cours', 'reçue', 'servie'
    private Date date;
    private double prixC;
    private List<LigneCommande> lignesCommande;
    
    // Constructeurs et méthodes...
}
```

---

### 4.5 Classe `LigneCommande`

```java
public class LigneCommande {
    private int idLigne;
    private int idCommande;
    private int idPlat;
    private int quantite;
    private double prixUnitaire;
    
    public double getSousTotal() {
        return quantite * prixUnitaire;
    }
}
```

---

### 4.6 Classe `Facture`

```java
public class Facture {
    private int idFacture;
    private int idCommande;
    private Date dateFacture;
    private double montantTotal;
    
    public void calculerTotal()
    public void generer()
    // ...
}
```

---

### 4.7 Classe `Client` (hérite de `Utilisateur`)

```java
public class Client extends Utilisateur {
    // Fonctionnalités clients
    public List<Plat> parcourrirMenu()
    public Commande commander(List<Plat> plats)
    // ...
}
```

---

### 4.8 Classe `Serveuse` (hérite de `Utilisateur`)

```java
public class Serveuse extends Utilisateur {
    // Fonctionnalités serveur
    public List<Plat> parcourrirMenu()
    public Commande commander(List<Plat> plats)
    public List<Commande> voirCommandesEnCours()
    public List<Commande> voirCommandesRecues()
    public Facture genererFacture(int idCommande)
    // ...
}
```

---

### 4.9 Classe `Cuisinier` (hérite de `Utilisateur`)

```java
public class Cuisinier extends Utilisateur {
    private String specialite;
    
    // Fonctionnalités cuisinier
    public void ajouterPlat(Plat plat)
    public void modifierPlat(Plat plat)
    public void supprimerPlat(int idPlat)
    public List<Commande> afficherCommandesDemandees()
    public void marquerEnCours(int idCommande)
    public void annulerCommande(int idCommande)
    public void notifierServeuse(int idCommande)
    // ...
}
```

---

## 5. PATTERN DAO (Data Access Object)

### 5.1 Architecture DAO

Le pattern DAO fournit une couche d'abstraction pour l'accès aux données, séparant la logique métier de la logique d'accès à la base de données.

```
┌─────────────────────┐
│  Classe Métier      │
│  (ex: Commande)     │
└──────────┬──────────┘
           │
           ↓
┌─────────────────────┐
│  DAO Interface      │
│  (ex: CommandeDAO)  │
└──────────┬──────────┘
           │
           ↓
┌─────────────────────┐
│  Implémentation DAO │
│  (requêtes SQL)     │
└──────────┬──────────┘
           │
           ↓
┌─────────────────────┐
│   MySQL Database    │
└─────────────────────┘
```

### 5.2 Interfaces et Classes DAO

#### `UtilisateurDAO.java`
```java
public interface UtilisateurDAO {
    public boolean verifierLoginExistant(String user, String mdp);
    public boolean ajouterUtilisateur(Utilisateur user);
    public boolean modifierUtilisateur(Utilisateur user);
    public boolean supprimerUtilisateur(int idU);
    public Utilisateur getUtilisateur(int idU);
    public List<Utilisateur> getAllUtilisateurs();
    public String getRoleUtilisateur(String user);
}
```

**Opérations CRUD :**
- CREATE : Ajout utilisateur
- READ : Récupération utilisateur(s)
- UPDATE : Modification
- DELETE : Suppression

---

#### `PlatDAO.java`
```java
public interface PlatDAO {
    public boolean ajouterPlat(Plat plat);
    public boolean modifierPlat(Plat plat);
    public boolean supprimerPlat(int idPlat);
    public Plat getPlat(int idPlat);
    public List<Plat> getAllPlats();
    public List<Plat> getPlatsByMenu(int idMenu);
    public List<Plat> getPlatsByType(String typeMenu);
}
```

---

#### `CommandeDAO.java`
```java
public interface CommandeDAO {
    public boolean creerCommande(Commande commande);
    public boolean mettreAJourStatutCommande(int idC, String status);
    public List<Commande> getCommandesEnCours();
    public List<Commande> getCommandesRecues();
    public List<Commande> getCommandesByUtilisateur(int idU);
    public Commande getCommande(int idC);
    public double calculerPrixCommande(int idC);
}
```

---

#### `PlatscommandeDAO.java`
```java
public interface PlatscommandeDAO {
    public boolean ajouterAuPanier(int idPlat, String nomPlat, 
                                   int quantite, int idU);
    public boolean retirerDuPanier(int idPlat, int idU);
    public List<Platscommande> getPanier(int idU);
    public boolean viderPanier(int idU);
    public boolean modifierQuantite(int idPlat, int idU, int quantite);
}
```

---

### 5.3 Gestion de la Connexion

#### `SingletonConnection.java`
```java
public class SingletonConnection {
    private static Connection connection;
    
    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Lecture de conf.properties
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/mydatabase",
                    "root",
                    "pass"
                );
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
```

**Pattern Singleton :** Garantit une seule instance de connexion

---

## 6. COUCHE PRÉSENTATION (SWING GUI)

### 6.1 Interfaces Graphiques Principales

#### 6.1.1 Écran de Connexion

**Fichiers :**
- `LoginC.java` - Login Client
- `LoginS.java` - Login Serveuse
- `LoginCH.java` - Login Chef/Cuisinier

**Fonctionnalités :**
- Saisie username/password
- Authentification via DAO
- Redirection vers interface spécifique
- Gestion des erreurs (utilisateur/mot de passe invalide)

---

#### 6.1.2 Interface Client

**Fichiers :**
- `ClientS.java` - Vue Client (Statique)
- `ClientSu.java` - Vue Client (Service)

**Fonctionnalités :**
- Parcourir le menu des plats
- Ajouter plats au panier
- Consulter le panier (platscommande)
- Passer une commande
- Voir historique des commandes

---

#### 6.1.3 Interface Serveuse

**Fichier :** `Serveur.java`

**Fonctionnalités :**
- Parcourir le menu
- Enregistrer commandes pour clients
- Visualiser commandes en cours (`CommandeEnCours.java`)
- Visualiser commandes reçues (`CommandeRecu.java`)
- Générer facture (`Facture.java`, `Facturefinal.java`)
- Voir état de préparation

---

#### 6.1.4 Interface Chef/Cuisinier

**Fichier :** `Chef.java`

**Fonctionnalités :**
- Ajouter plat (`AjoutPlat.java`)
- Modifier plat (`ModifPlat.java`)
- Supprimer plat
- Consulter menu complet (`MenuAll.java`, `Menu.java`)
- Voir commandes demandées
- Marquer commande en cours de préparation
- Annuler commande
- Notifier serveur quand commande prête

---

#### 6.1.5 Autres Interfaces

| Fichier | Fonction |
|---------|----------|
| `Choisir.java` | Écran de choix de rôle (Client/Serveuse/Chef) |
| `Inscription.java` | Enregistrement nouveau compte |
| `Commander.java` | Interface de commande |
| `AfficherDescriptionPlat.java` | Détail plat |
| `ViewP.java` | Vue produit |
| `AfficherFacturequi.java` | Affichage facture |

---

### 6.2 Composants SWING Utilisés

```java
// Conteneurs
JFrame          // Fenêtre principale
JPanel          // Panneau de contenu

// Saisie
JTextField      // Champ texte (username, etc.)
JPasswordField  // Champ mot de passe
JComboBox       // Liste déroulante (menu types)
JSpinner        // Sélecteur quantité

// Affichage
JLabel          // Étiquettes
JTable          // Tableau (commandes, plats)
JList           // Liste (menu, panier)
JTextArea       // Texte multi-ligne (descriptions)

// Boutons et Dialogue
JButton         // Bouton (Connexion, Commander, etc.)
JDialog         // Dialogue modal
JFileChooser    // Sélecteur fichier

// Disposition
BorderLayout    // Disposition bordure
GridLayout      // Disposition grille
FlowLayout      // Disposition flux
```

---

## 7. FONCTIONNALITÉS DÉTAILLÉES

### 7.1 Fonctionnalités Client

**Case d'Usage :** Client

```
┌──────────┐
│  Client  │
└─────┬────┘
      │
      ├─→ 1. Parcourir Menu
      │      └─→ Afficher liste plats
      │      └─→ Filtrer par type
      │      └─→ Voir descriptions et prix
      │
      └─→ 2. Commander des Plats
             └─→ Ajouter plat au panier
             └─→ Modifier quantité
             └─→ Retirer plat
             └─→ Valider commande
             └─→ Voir confirmation
```

**Workflow Commande Client :**
1. Client se connecte
2. Consulte menu
3. Sélectionne plats (ajout au panier dans `platscommande`)
4. Confirme commande
5. Reçoit numéro commande
6. Peut consulter statut

---

### 7.2 Fonctionnalités Serveuse

**Case d'Usage :** Serveuse

```
┌──────────┐
│ Serveuse │
└─────┬────┘
      │
      ├─→ 1. Parcourir Menu & Commander
      │      (Idem client, mais enregistre pour tout client)
      │
      ├─→ 2. Voir Commandes en Cours
      │      └─→ Affiche commandes en préparation
      │      └─→ Détails: plats, quantités, client
      │
      ├─→ 3. Voir Commandes Reçues
      │      └─→ Affiche commandes prêtes à servir
      │      └─→ Notification du chef
      │      └─→ Marquer comme servie
      │
      └─→ 4. Générer Facture
             └─→ Sélectionner commande
             └─→ Calculer total avec taxes
             └─→ Imprimer/Exporter facture
             └─→ Enregistrer en BD
```

**Workflow Facture :**
1. Serveuse sélectionne commande
2. Système récupère tous `LigneCommande`
3. Calcul : somme(quantité × prixUnitaire)
4. Génération `Facture` record
5. Sauvegarde en BD

---

### 7.3 Fonctionnalités Cuisinier

**Case d'Usage :** Cuisinier

```
┌──────────┐
│ Cuisinier│
└─────┬────┘
      │
      ├─→ 1. Gestion Menu
      │      ├─→ Ajouter Plat
      │      │    └─→ Saisir: nom, prix, description
      │      │    └─→ Assigner type, menu
      │      │    └─→ Marquer disponible
      │      │
      │      ├─→ Modifier Plat
      │      │    └─→ Sélectionner plat
      │      │    └─→ Changer prix, description, dispo
      │      │    └─→ Mettre à jour BD
      │      │
      │      └─→ Supprimer Plat
      │           └─→ Confirmer suppression
      │           └─→ Cascade delete (BD)
      │
      ├─→ 2. Commandes Demandées
      │      └─→ Affiche nouvelles commandes
      │      └─→ Détails plats à préparer
      │      └─→ Marquer "En Cours"
      │      └─→ Marquer "Annuler"
      │
      ├─→ 3. Commandes En Cours
      │      └─→ Affiche préparations actuelles
      │      └─→ Quand prête: Notifier Serveuse
      │      └─→ Notification système
      │
      └─→ 4. Commandes Servies
             └─→ Historique des commandes complétées
             └─→ Stats production
```

**État Commande Cuisinier :**
```
REÇUE
  ↓
EN_COURS (Cuisinier commence)
  ↓
PRÊTE (Notification Serveuse)
  ↓
SERVIE (Serveuse valide)
```

---

## 8. FLUX DE DONNÉES ET CAS D'USAGE PRINCIPAUX

### 8.1 Scénario 1 : Passation de Commande

```
CLIENT
  │
  ├─ Se connecte (LoginC.java)
  │  └─ Vérifie credentials via UtilisateurDAO
  │
  ├─ Consulte menu (Menu.java)
  │  └─ PlatDAO.getAllPlats()
  │
  ├─ Ajoute plats au panier
  │  └─ PlatscommandeDAO.ajouterAuPanier()
  │
  └─ Confirme commande
     └─ Crée objet Commande
     └─ CommandeDAO.creerCommande()
     └─ Pour chaque ligne: LigneCommandeDAO.ajouter()
     └─ Affiche confirmation
```

**Flux BD :**
```
INSERT commande (etat, idU, date_commande, prixC)
  ↓
INSERT lignecommande (idC, idPlat, quantite, prixUnitaire) x N
  ↓
UPDATE platscommande SET quantite = 0 (panier vidé)
```

---

### 8.2 Scénario 2 : Préparation et Service

```
CUISINIER
  │
  ├─ Consulte "Commandes Demandées"
  │  └─ CommandeDAO.getCommandesParEtat("reçue")
  │
  ├─ Sélectionne commande
  │  └─ Affiche LigneCommande details
  │
  ├─ Marque "En Cours"
  │  └─ CommandeDAO.mettreAJourStatut(idC, "en_cours")
  │
  └─ Prépare plats
     └─ Marque "Prête"
     └─ CommandeDAO.mettreAJourStatut(idC, "prête")
     └─ notifierServeuse(idC) // Notification système


SERVEUSE
  │
  ├─ Reçoit notification
  │  └─ Consulte "Commandes en Cours"
  │
  └─ Valide et Serve
     └─ CommandeDAO.mettreAJourStatut(idC, "servie")
```

---

### 8.3 Scénario 3 : Génération Facture

```
SERVEUSE
  │
  ├─ Sélectionne commande à facturer
  │
  ├─ Récupère LigneCommandes
  │  └─ LigneCommandeDAO.getByCommande(idC)
  │
  ├─ Calcule total
  │  └─ Pour chaque ligne: qté × prixUnitaire
  │  └─ Somme totale
  │
  ├─ Crée Facture
  │  └─ new Facture(idC, total, DATE)
  │  └─ FactureDAO.creer()
  │
  └─ Génère & affiche
     └─ Facture.java / Facturefinal.java
     └─ Affiche détails client + articles + total
```

---

## 9. CONFIGURATION ET DÉPLOIEMENT

### 9.1 Configuration JDBC

**Fichier :** `src/conf.properties`

```properties
# Configuration MySQL
jdbc.url=jdbc:mysql://localhost:3306/mydatabase
jdbc.user=root
jdbc.password=pass

# Paramètres optionnels
jdbc.pool.initialSize=5
jdbc.pool.maxActive=10
```

### 9.2 Initialisation Base de Données

```sql
-- 1. Créer la base
CREATE DATABASE IF NOT EXISTS mydatabase 
  CHARACTER SET utf8mb4 
  COLLATE utf8mb4_unicode_ci;

-- 2. Exécuter schema.sql
mysql -u root -p mydatabase < database/schema.sql

-- 3. Insérer données de test
-- (voir schema.sql - INSERT statements)
```

### 9.3 Compilation et Exécution

```bash
# Compilation
javac -cp .:mysql-connector-java-8.x.x.jar src/**/*.java -d bin/

# Exécution (classe principale)
java -cp bin:mysql-connector-java-8.x.x.jar view.Choisir

# Ou si JAR assemblé
java -jar Restaurant.jar
```

### 9.4 Dépendances

```
MySQL JDBC Driver: mysql-connector-java-8.0.x.jar
Java Runtime: JRE 8+
MySQL Server: 5.7+
```

---

## 10. DIAGRAMMES UML

### 10.1 Diagramme de Classes Simplifié

```
┌──────────────────────┐
│    Utilisateur       │
├──────────────────────┤
│ - idU: int           │
│ - nomU: String       │
│ - mdpU: String       │
│ - role: String       │
├──────────────────────┤
│ + login()            │
│ + logout()           │
└──────────────────────┘
      △  △  △
      │  │  │
      ├──┼──┤
      │  │  │
      │  │  └─────────────────────┐
      │  │                         │
      │  │                    ┌────────────┐
      │  │                    │ Cuisinier  │
      │  │                    ├────────────┤
      │  │                    │+ ajouterP()│
      │  │                    │+ modifP()  │
      │  │                    │+ suppP()   │
      │  │                    └────────────┘
      │  │
      │  ┌──────────────┐
      │  │   Serveuse   │
      │  ├──────────────┤
      │  │+ facture()   │
      │  │+ commandes() │
      │  └──────────────┘
      │
      ┌────────────┐
      │   Client   │
      ├────────────┤
      │+ commander()│
      │+ panier()  │
      └────────────┘


┌──────────────────┐      ┌──────────────────┐
│     Commande     │      │    LigneCommande │
├──────────────────┤      ├──────────────────┤
│ - idC: int       │◄─────│ - idLigne: int   │
│ - idU: int       │ 1  * │ - idC: int       │
│ - etat: String   │      │ - idPlat: int    │
│ - date: Date     │      │ - quantite: int  │
│ - prixC: double  │      │ - prix: double   │
└──────────────────┘      └──────────────────┘
        │
        │ 0..*
        │
    ┌───────────────┐
    │   Facture     │
    ├───────────────┤
    │ - idFacture   │
    │ - montantTot  │
    └───────────────┘


┌─────────────────┐
│      Menu       │
├─────────────────┤
│ - idMenu: int   │
│ - nomMenu: Str  │
└─────────────────┘
        │
        │ 1  *
        ├─────────┐
        │         │
    ┌────────────────┐
    │      Plat      │
    ├────────────────┤
    │ - idPlat: int  │
    │ - nomPlat: Str │
    │ - prix: double │
    │ - typeMenu: Str│
    │ - typePlat: Str│
    └────────────────┘
```

---

## 11. TESTS ET VALIDATION

### 11.1 Points de Test

#### Test d'Authentification
```
✓ Utilisateur valide → Accès autorisé
✓ Utilisateur invalide → Rejet avec message
✓ Role client/serveuse/chef → Interface correcte
```

#### Test de Commande
```
✓ Ajouter plat → Panier mis à jour
✓ Confirmer → Enregistrement BD
✓ Récupérer → Affichage dans CommandeEnCours
```

#### Test Cuisinier
```
✓ Ajouter plat → Plat visible en BD
✓ Modifier prix → Mise à jour
✓ Supprimer → Cascade delete
```

#### Test Facture
```
✓ Calcul total → Correct (qté × prix)
✓ Génération → Enregistrement en BD
✓ Affichage → Toutes infos présentes
```

### 11.2 Classe Test

**Fichier :** `src/util/Test.java`

```java
public class Test {
    public static void main(String[] args) {
        // Tests connexion BD
        Connection conn = SingletonConnection.getConnection();
        System.out.println("Connexion: " + (conn != null ? "OK" : "FAIL"));
        
        // Tests DAO
        UtilisateurDAO userDao = new UtilisateurDAO();
        List<Utilisateur> users = userDao.getAllUtilisateurs();
        System.out.println("Utilisateurs: " + users.size());
        
        // Tests métier
        // ...
    }
}
```

---

## 12. POINTS FORTS ET AMÉLIORATIONS

### 12.1 Points Forts du Projet

✓ **Architecture MVC clean** - Séparation claire des responsabilités  
✓ **Pattern DAO** - Abstraction de l'accès données  
✓ **Héritage** - Classes Client/Serveuse/Cuisinier  
✓ **Interface graphique** - SWING intuitive  
✓ **Intégrité données** - Contraintes FK et CHECKs  
✓ **Scalabilité** - Structure extensible  

### 12.2 Améliorations Possibles

1. **Thread Safety** - Synchronisation pour accès BD concurrents
2. **Sécurité** - Hachage mots de passe (BCrypt)
3. **Cache** - Mise en cache requêtes fréquentes
4. **Logging** - Framework Log4J
5. **Transactions** - Gestion explicite
6. **Pagination** - Listes grandes tables
7. **Recherche avancée** - Filtres complexes
8. **Export** - PDF/Excel factures
9. **Notifications** - Système asynchrone
10. **Responsive UI** - Meilleure ergonomie

---

## 13. CONCLUSION

Ce projet démontre une implémentation complète d'un système de gestion de restaurant respectant les principes de POO et les bonnes pratiques d'architecture logicielle. 

**Points clés :**
- Architecture en couches bien organisée
- Modèle de données normalisé
- Interfaces utilisateur fonctionnelles pour 3 rôles distincts
- Séparation claire données/métier/présentation
- Extensibilité pour évolutions futures

Le système est prêt pour une utilisation en environnement éducatif et peut être étendu vers un produit commercial avec les améliorations suggérées.

---

**Document généré le :** 6 Mai 2026  
**Auteur :** Équipe CPI2 - Programmation Orientée Objet  
**Encadrement :** Sofiane HACHICHA & Nesrine Akrout

---
