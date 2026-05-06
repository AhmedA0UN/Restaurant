# RÉFÉRENCE API ET CLASSES
## Système de Gestion de Restaurant

---

## 📦 STRUCTURE DES PACKAGES

```
Restaurant/
├── controller/      # Contrôleurs métier
├── dao/            # Pattern DAO - Accès données
├── model/          # Entités métier
├── view/           # Interfaces SWING
└── util/           # Utilitaires (Connexion, Tests)
```

---

## 🏛️ COUCHE MÉTIER (Model)

### Classe: `Utilisateur`

```java
public class Utilisateur {
    // Propriétés
    private int id;
    private String nom;
    private String prenom;
    private String username;
    private String password;
    private String role;
    
    // Constructeurs
    Utilisateur()
    Utilisateur(int idU, String nomU, String mdpU, String role)
    Utilisateur(int id, String nom, String prenom, String username, 
                String password, String role)
    
    // Getters
    int getIdU()
    int getId()
    String getNomU()
    String getNom()
    String getPrenom()
    String getUsername()
    String getPassword()
    String getRole()
    
    // Setters
    void setId(int id)
    void setNom(String nom)
    void setPrenom(String prenom)
    void setUsername(String username)
    void setPassword(String password)
    void setRole(String role)
}
```

**Rôles Possibles:**
- `client` : Client du restaurant
- `serveuse` : Serveur
- `chef` : Chef cuisinier

---

### Classe: `Client extends Utilisateur`

```java
public class Client extends Utilisateur {
    // Fonctionnalités spécifiques
    List<Plat> parcourrirMenu()
    Commande commander(List<Plat> plats)
    List<Commande> voirHistorique()
    void consulterPanier()
}
```

---

### Classe: `Serveuse extends Utilisateur`

```java
public class Serveuse extends Utilisateur {
    // Fonctionnalités spécifiques
    List<Plat> parcourrirMenu()
    Commande commander(List<Plat> plats)
    List<Commande> voirCommandesEnCours()
    List<Commande> voirCommandesRecues()
    Facture genererFacture(int idCommande)
    void marquerServie(int idCommande)
}
```

---

### Classe: `Cuisinier extends Utilisateur`

```java
public class Cuisinier extends Utilisateur {
    // Propriété
    String specialite;
    
    // Fonctionnalités spécifiques
    void ajouterPlat(Plat plat)
    void modifierPlat(Plat plat)
    void supprimerPlat(int idPlat)
    List<Commande> afficherCommandesDemandees()
    void marquerEnCours(int idCommande)
    void annulerCommande(int idCommande)
    void notifierServeuse(int idCommande)
    List<Plat> consulterMenu()
}
```

---

### Classe: `Plat`

```java
public class Plat {
    // Propriétés
    private int idPlat;
    private String nomPlat;
    private double prix;
    private String typeMenu;      // ex: "Menu Salé"
    private String typePlat;      // ex: "Entrée", "Plat"
    private String description;
    private boolean disponible;
    private int idMenu;
    
    // Constructeurs
    Plat()
    Plat(int id, String nom, double p, String typeMenu, String typePlat)
    Plat(int id, String nom, double p, String typeMenu, String typePlat, 
         String description)
    Plat(int idPlat, String nomPlat, double prix, String typeMenu, 
         String typePlat, String description, boolean disponible, int idMenu)
    
    // Getters
    int getIdPlat()
    String getNomPlat()
    double getPrix()
    String getTypeMenu()
    String getTypePlat()
    String getDescription()
    boolean isDisponible()
    int getIdMenu()
    
    // Setters
    void setIdPlat(int id)
    void setNomPlat(String nom)
    void setPrix(double prix)
    void setTypeMenu(String typeMenu)
    void setTypePlat(String typePlat)
    void setDescription(String description)
    void setDisponible(boolean disponible)
    void setIdMenu(int idMenu)
}
```

---

### Classe: `Menu`

```java
public class Menu {
    // Propriétés
    private int idMenu;
    private String nomMenu;
    private String description;
    
    // Constructeurs
    Menu()
    Menu(int id, String nom, String description)
    
    // Getters
    int getIdMenu()
    String getNomMenu()
    String getDescription()
    
    // Setters & Méthodes
    void setIdMenu(int id)
    void setNomMenu(String nom)
    void setDescription(String description)
    void ajouterPlat(Plat plat)
    void modifierPlat(Plat plat)
    void supprimerPlat(int idPlat)
}
```

---

### Classe: `Commande`

```java
public class Commande {
    // Propriétés
    private int idCommande;
    private int idClient;
    private String etat;              // "reçue", "en_cours", "servie"
    private Date date;
    private double prixC;
    private List<LigneCommande> lignesCommande;
    
    // Constructeurs
    Commande()
    Commande(int idCommande, int idClient, String etat, Date date)
    
    // Getters
    int getIdCommande()
    int getIdClient()
    String getEtat()
    Date getDate()
    double getPrixC()
    List<LigneCommande> getLignesCommande()
    
    // Setters
    void setIdCommande(int idCommande)
    void setIdClient(int idC)
    void setEtat(String etat)
    void setDate(Date date)
    void setPrixC(double prix)
    void setLignesCommande(List<LigneCommande> lignes)
}

// États possibles:
// - "reçue"    : Commande nouvelle reçue
// - "en_cours" : En préparation par chef
// - "servie"   : Servie au client
// - "annulée"  : Annulée par chef
```

---

### Classe: `LigneCommande`

```java
public class LigneCommande {
    // Propriétés
    private int idLigne;
    private int idCommande;
    private int idPlat;
    private int quantite;
    private double prixUnitaire;
    
    // Constructeurs
    LigneCommande()
    LigneCommande(int idLigne, int idCommande, int idPlat, 
                  int quantite, double prixUnitaire)
    
    // Getters
    int getIdLigne()
    int getIdCommande()
    int getIdPlat()
    int getQuantite()
    double getPrixUnitaire()
    
    // Setters
    void setIdLigne(int id)
    void setIdCommande(int id)
    void setIdPlat(int id)
    void setQuantite(int quantite)
    void setPrixUnitaire(double prix)
    
    // Méthodes métier
    double getSousTotal()  // Retourne: quantite × prixUnitaire
}
```

---

### Classe: `Platscommande`

```java
public class Platscommande {
    // Propriétés
    private int idPlat;
    private String nomPlat;
    private int quantite;
    private int idU;
    
    // Constructeurs
    Platscommande()
    Platscommande(int idPlat, String nomPlat, int quantite, int idU)
    
    // Getters
    int getIdPlat()
    String getNomPlat()
    int getQuantite()
    int getIdU()
    
    // Setters
    void setIdPlat(int id)
    void setNomPlat(String nom)
    void setQuantite(int quantite)
    void setIdU(int idU)
    
    // Rôle: Panier temporaire avant validation
}
```

---

### Classe: `Facture`

```java
public class Facture {
    // Propriétés
    private int idFacture;
    private int idCommande;
    private Date dateFacture;
    private double montantTotal;
    
    // Constructeurs
    Facture()
    Facture(int idFacture, int idCommande, Date dateFacture, 
            double montantTotal)
    
    // Getters
    int getIdFacture()
    int getIdCommande()
    Date getDateFacture()
    double getMontantTotal()
    
    // Setters
    void setIdFacture(int id)
    void setIdCommande(int id)
    void setDateFacture(Date date)
    void setMontantTotal(double montant)
    
    // Méthodes métier
    void calculerTotal()
    void generer()
    void imprimer()
    void exporter(String format)  // "PDF", "Excel"
}
```

---

## 🗂️ COUCHE DONNÉES (DAO)

### Interface: `UtilisateurDAO`

```java
public interface UtilisateurDAO {
    // CRUD
    boolean ajouterUtilisateur(Utilisateur user)
    Utilisateur getUtilisateur(int idU)
    List<Utilisateur> getAllUtilisateurs()
    boolean modifierUtilisateur(Utilisateur user)
    boolean supprimerUtilisateur(int idU)
    
    // Métier
    boolean verifierLoginExistant(String username, String password)
    String getRoleUtilisateur(String username)
    
    // Exemple d'usage:
    // UtilisateurDAO dao = new UtilisateurDAO();
    // if (dao.verifierLoginExistant("client1", "client123")) {
    //     Utilisateur user = dao.getUtilisateur(1);
    // }
}
```

---

### Interface: `PlatDAO`

```java
public interface PlatDAO {
    // CRUD
    boolean ajouterPlat(Plat plat)
    Plat getPlat(int idPlat)
    List<Plat> getAllPlats()
    boolean modifierPlat(Plat plat)
    boolean supprimerPlat(int idPlat)
    
    // Requêtes métier
    List<Plat> getPlatsByMenu(int idMenu)
    List<Plat> getPlatsByType(String typeMenu)
    List<Plat> getPlatsByTypePlat(String typePlat)
    
    // Exemple:
    // PlatDAO dao = new PlatDAO();
    // List<Plat> entrees = dao.getPlatsByTypePlat("Entrée");
}
```

---

### Interface: `CommandeDAO`

```java
public interface CommandeDAO {
    // CRUD
    boolean creerCommande(Commande commande)
    Commande getCommande(int idC)
    List<Commande> getAllCommandes()
    
    // Requêtes métier
    List<Commande> getCommandesEnCours()           // etat='en_cours'
    List<Commande> getCommandesRecues()            // etat='reçue'
    List<Commande> getCommandesServies()           // etat='servie'
    List<Commande> getCommandesByUtilisateur(int idU)
    List<Commande> getCommandesParEtat(String etat)
    
    // Modification
    boolean mettreAJourStatutCommande(int idC, String status)
    
    // Calculs
    double calculerPrixCommande(int idC)
    int getNombreCommandesEnCours()
    
    // Exemple:
    // CommandeDAO dao = new CommandeDAO();
    // List<Commande> enCours = dao.getCommandesEnCours();
    // dao.mettreAJourStatutCommande(123, "prête");
}
```

---

### Interface: `PlatscommandeDAO`

```java
public interface PlatscommandeDAO {
    // Panier
    boolean ajouterAuPanier(int idPlat, String nomPlat, int quantite, int idU)
    boolean retirerDuPanier(int idPlat, int idU)
    List<Platscommande> getPanier(int idU)
    boolean viderPanier(int idU)
    boolean modifierQuantite(int idPlat, int idU, int quantite)
    
    // Vérification
    boolean platExisteDansPanier(int idPlat, int idU)
    int getNombreArticlesPanier(int idU)
    
    // Exemple:
    // PlatscommandeDAO dao = new PlatscommandeDAO();
    // dao.ajouterAuPanier(5, "Salade Cesar", 2, 3);
    // List<Platscommande> panier = dao.getPanier(3);
}
```

---

### Interface: `FactureDAO`

```java
public interface FactureDAO {
    // CRUD
    boolean creerFacture(Facture facture)
    Facture getFacture(int idFacture)
    List<Facture> getFacturesByCommande(int idC)
    List<Facture> getAllFactures()
    
    // Requêtes
    double getTotalFacturesParPeriode(Date debut, Date fin)
    List<Facture> getFacturesDuJour()
    
    // Exemple:
    // FactureDAO dao = new FactureDAO();
    // Facture f = new Facture(0, 123, new Date(), 50.00);
    // dao.creerFacture(f);
}
```

---

### Interface: `LigneCommandeDAO`

```java
public interface LigneCommandeDAO {
    // CRUD
    boolean ajouterLigneCommande(LigneCommande ligne)
    List<LigneCommande> getLignesCommande(int idCommande)
    
    // Calculs
    double calculerTotalCommande(int idCommande)
    int getNombrePlatsCommande(int idCommande)
    
    // Exemple:
    // LigneCommandeDAO dao = new LigneCommandeDAO();
    // List<LigneCommande> lignes = dao.getLignesCommande(123);
}
```

---

## 🔗 CONNEXION

### Classe: `SingletonConnection`

```java
public class SingletonConnection {
    // Pattern Singleton - Une seule instance
    
    // Méthode d'accès
    public static Connection getConnection()
    
    // Propriétés de configuration
    // Lues depuis: src/conf.properties
    // - jdbc.url
    // - jdbc.user
    // - jdbc.password
    
    // Exemple:
    // Connection conn = SingletonConnection.getConnection();
    // if (conn != null) {
    //     // Connexion réussie
    // }
}
```

**Fichier de Configuration:**
```properties
# src/conf.properties
jdbc.url=jdbc:mysql://localhost:3306/mydatabase
jdbc.user=root
jdbc.password=pass
```

---

## 🖥️ COUCHE PRÉSENTATION (Vue)

### Interfaces Principales

| Classe | Rôle | Parent |
|--------|------|--------|
| `Choisir.java` | Écran choix rôle | JFrame |
| `LoginC.java` | Connexion client | JFrame |
| `LoginS.java` | Connexion serveuse | JFrame |
| `LoginCH.java` | Connexion chef | JFrame |
| `ClientS.java` | Interface client | JFrame |
| `Serveur.java` | Interface serveuse | JFrame |
| `Chef.java` | Interface chef | JFrame |
| `Commander.java` | Écran commande | JFrame |
| `Facture.java` | Affichage facture | JFrame |
| `Menu.java` | Affichage menu | JFrame |
| `AjoutPlat.java` | Ajouter plat | JDialog |
| `ModifPlat.java` | Modifier plat | JDialog |

---

## 📊 COMPOSANTS SWING COURANTS

### Conteneurs
```java
JFrame frame = new JFrame("Titre");           // Fenêtre principale
JPanel panel = new JPanel();                   // Panneau
JDialog dialog = new JDialog(frame);          // Dialogue modal
```

### Saisie
```java
JTextField textField = new JTextField(20);    // Champ texte
JPasswordField pwdField = new JPasswordField(20);  // Mot de passe
JComboBox<String> combo = new JComboBox<>();  // Dropdown
JSpinner spinner = new JSpinner();            // Nombre
```

### Affichage
```java
JLabel label = new JLabel("Texte");           // Étiquette
JTable table = new JTable(data, columns);     // Tableau
JList<String> list = new JList<>();           // Liste
JTextArea area = new JTextArea();             // Texte multi-ligne
```

### Action
```java
JButton btn = new JButton("Clic");            // Bouton
btn.addActionListener(e -> { /* code */ });   // Événement

JMenuBar menuBar = new JMenuBar();            // Menu
JMenu menu = new JMenu("Fichier");            // Menu item
```

### Disposition
```java
panel.setLayout(new BorderLayout());          // Bordure
panel.setLayout(new GridLayout(3, 2));        // Grille
panel.setLayout(new FlowLayout());            // Flux
```

---

## ⚙️ CONTRÔLEURS (Controllers)

### Exemple: `CommandeController`

```java
public class CommandeController {
    // Méthodes de gestion métier
    
    public Commande creerCommande(int idClient, List<Plat> plats) {
        // Logique création commande
        // 1. Valider données
        // 2. Appeler DAO
        // 3. Retourner résultat
    }
    
    public void marquerEnCours(int idCommande) {
        // Marquer commande en cours de préparation
    }
    
    public void marquerPrete(int idCommande) {
        // Marquer commande prête
        // Notifier serveuse
    }
    
    public List<Commande> getCommandes() {
        // Récupérer toutes commandes
    }
}
```

---

## 🔀 FLUX DE DONNÉES

```
VIEW (Swing Interface)
    │ (Événement utilisateur)
    ↓
CONTROLLER (Validation + Métier)
    │ (Appel métier)
    ↓
MODEL (Entités Java)
    │ (Utilisé par)
    ↓
DAO (Requêtes SQL)
    │ (Exécute)
    ↓
DATABASE (MySQL)
    │ (Retourne données)
    ↓
DAO (Crée objets Model)
    │ (Retourne à)
    ↓
CONTROLLER (Traite résultat)
    │ (Retourne à)
    ↓
VIEW (Affiche résultat)
```

---

## 🔍 EXEMPLE COMPLET: Ajouter un Plat

```java
// 1. VUE (AjoutPlat.java)
JButton btnAjouter = new JButton("Ajouter");
btnAjouter.addActionListener(e -> {
    String nom = textFieldNom.getText();
    double prix = Double.parseDouble(textFieldPrix.getText());
    
    // Appeler contrôleur
    PlatController controller = new PlatController();
    controller.ajouterPlat(nom, prix, /* ... */);
});

// 2. CONTRÔLEUR (PlatController.java)
public class PlatController {
    public void ajouterPlat(String nom, double prix, ...) {
        // Valider
        if (nom.isEmpty()) throw new Exception("Nom vide");
        if (prix <= 0) throw new Exception("Prix invalide");
        
        // Créer objet
        Plat plat = new Plat(0, nom, prix, typeMenu, typePlat, desc);
        
        // Appeler DAO
        PlatDAO dao = new PlatDAO();
        dao.ajouterPlat(plat);
    }
}

// 3. MODÈLE (Plat.java)
public class Plat {
    // Données métier
    private int idPlat;
    private String nomPlat;
    private double prix;
    // ...
}

// 4. DAO (PlatDAO.java)
public class PlatDAO {
    public boolean ajouterPlat(Plat plat) {
        String sql = "INSERT INTO plat (nomPlat, prix, ...) VALUES (?, ?, ...)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, plat.getNomPlat());
        stmt.setDouble(2, plat.getPrix());
        // ...
        return stmt.executeUpdate() > 0;
    }
}

// 5. BASE DE DONNÉES
// INSERT INTO plat (nomPlat, prix, typeMenu, typePlat, description)
// VALUES ('Steak', 25.50, 'Menu Principal', 'Plat', 'Steak bien cuit');
```

---

## 📈 STATISTIQUES DE CODE

| Élément | Nombre |
|---------|--------|
| Classes métier | 10 |
| DAO/Interfaces | 5 |
| Vues SWING | 20+ |
| Contrôleurs | 4 |
| Tables BD | 7 |
| Lignes de code | 2000-3000+ |

---

## 🎯 POINTS D'ENTRÉE PRINCIPAUX

### Démarrer l'Application
```bash
java -cp "bin:lib/*" view.Choisir
```

### Classes Principales
- **View:** `view.Choisir` (point d'entrée)
- **Model:** `model.Utilisateur` (superclasse)
- **DAO:** `dao.*DAO` interfaces
- **Connexion:** `util.SingletonConnection`

---

**Référence Générée:** 6 Mai 2026

