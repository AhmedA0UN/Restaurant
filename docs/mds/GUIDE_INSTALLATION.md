# GUIDE D'INSTALLATION ET D'UTILISATION
## Système de Gestion de Restaurant en Java

---

## 📋 TABLE DES MATIÈRES

1. [Configuration Système](#configuration-système)
2. [Installation Base de Données](#installation-base-de-données)
3. [Configuration du Projet](#configuration-du-projet)
4. [Compilation](#compilation)
5. [Exécution](#exécution)
6. [Guide d'Utilisation](#guide-dutilisation)
7. [Dépannage](#dépannage)
8. [FAQ](#faq)

---

## 🖥️ CONFIGURATION SYSTÈME

### Prérequis Minimums

**Système d'Exploitation:**
- Windows 7 ou supérieur
- macOS 10.12 ou supérieur
- Linux (Ubuntu 16.04 LTS ou équivalent)

**Logiciels Requis:**

| Logiciel | Version | Téléchargement |
|----------|---------|----------------|
| Java JDK | 8 ou supérieur | java.oracle.com |
| MySQL Server | 5.7 ou supérieur | mysql.com |
| IDE (optionnel) | Eclipse / NetBeans / IntelliJ | - |

**Ressources Matérielles:**
- RAM : 4 GB minimum (8 GB recommandé)
- Disque : 500 MB espace libre
- Processeur : 2 GHz minimum

### Vérification Installation Java

```bash
# Ouvrir terminal/cmd et taper:
java -version
javac -version

# Résultat attendu:
java version "1.8.0_XXX"
```

### Vérification Installation MySQL

```bash
mysql --version

# Résultat attendu:
mysql  Ver 14.14 Distrib 5.7.x
```

---

## 🗄️ INSTALLATION BASE DE DONNÉES

### Étape 1: Démarrer le Service MySQL

**Windows:**
```bash
# Ouvrir Services Windows
# Trouver "MySQL80" ou "MySQL Server"
# Clic droit → Démarrer
```

**macOS:**
```bash
# Terminal:
mysql.server start
```

**Linux:**
```bash
sudo service mysql start
```

### Étape 2: Créer la Base de Données

```bash
# Ouvrir MySQL
mysql -u root -p

# Prompt mot de passe (défaut: "pass" ou rien)
# Puis exécuter:

CREATE DATABASE IF NOT EXISTS mydatabase 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

USE mydatabase;
```

### Étape 3: Importer le Schéma

```bash
# Depuis le répertoire du projet:
mysql -u root -p mydatabase < database/schema.sql

# Ou manuellement:
# 1. Copier contenu schema.sql
# 2. Coller dans MySQL client
# 3. Exécuter
```

### Étape 4: Vérifier l'Installation

```bash
# Se connecter à la base:
mysql -u root -p mydatabase

# Vérifier les tables:
SHOW TABLES;

# Résultat attendu:
+-------------------+
| Tables_in_mydatabase |
+-------------------+
| commande          |
| facture           |
| lignecommande     |
| menu              |
| plat              |
| platscommande     |
| utilisateur       |
+-------------------+

# Quitter:
EXIT;
```

### Étape 5: Vérifier les Données de Test

```bash
mysql -u root -p mydatabase

SELECT * FROM utilisateur;
# Affiche: admin (chef), serveuse1, client1

SELECT * FROM plat;
# Affiche: Salade Cesar, Tarte aux pommes
```

---

## ⚙️ CONFIGURATION DU PROJET

### Configuration JDBC (Fichier conf.properties)

**Localisation:** `src/conf.properties`

**Contenu par défaut:**
```properties
jdbc.url=jdbc:mysql://localhost:3306/mydatabase
jdbc.user=root
jdbc.password=pass
```

**Si MySQL est installé différemment:**

```properties
# Avec port personnalisé:
jdbc.url=jdbc:mysql://localhost:3307/mydatabase

# Machine distante:
jdbc.url=jdbc:mysql://192.168.1.100:3306/mydatabase

# Avec paramètres SSL:
jdbc.url=jdbc:mysql://localhost:3306/mydatabase?useSSL=true
```

### Télécharger le Connecteur MySQL

1. **Télécharger:** mysql-connector-java-8.0.x.jar
   - Site: dev.mysql.com/downloads/connector/j/
   - Placer dans: `lib/` ou classe path

2. **Alternative - Maven:**
```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.33</version>
</dependency>
```

---

## 🔨 COMPILATION

### Compilation Simple

```bash
# Depuis le répertoire du projet:

# Windows:
javac -cp ".;lib/mysql-connector-java-8.0.33.jar" ^
      src/controller/*.java ^
      src/dao/*.java ^
      src/model/*.java ^
      src/view/*.java ^
      src/util/*.java ^
      -d bin/

# Linux/macOS:
javac -cp ".:lib/mysql-connector-java-8.0.33.jar" \
      src/controller/*.java \
      src/dao/*.java \
      src/model/*.java \
      src/view/*.java \
      src/util/*.java \
      -d bin/
```

### Compilation Récursive

```bash
# Linux/macOS:
javac -cp ".:lib/mysql-connector-java-8.0.33.jar" \
      src/**/*.java \
      -d bin/

# Windows PowerShell:
Get-ChildItem -Path src -Filter *.java -Recurse | 
ForEach-Object {
    javac -cp ".;lib\mysql-connector-java-8.0.33.jar" `
          $_.FullName -d bin/
}
```

### Utiliser un IDE

**IntelliJ IDEA / NetBeans / Eclipse:**

1. Ouvrir le projet
2. Configurer SDK Java 8+
3. Ajouter mysql-connector-java.jar au ClassPath
4. Build → Compiler

---

## ▶️ EXÉCUTION

### Lancer l'Application

```bash
# Depuis le répertoire du projet:

# Linux/macOS:
java -cp "bin:lib/mysql-connector-java-8.0.33.jar" \
     view.Choisir

# Windows:
java -cp "bin;lib\mysql-connector-java-8.0.33.jar" ^
     view.Choisir
```

### Résultat Attendu

1. **Fenêtre de Sélection Rôle**
   - Trois boutons: Client / Serveuse / Chef
   - Interface graphique SWING

2. **Écran de Connexion**
   - Champs: Username / Password
   - Boutons: Connexion / Inscription / Retour

### Créer un Script de Lancement

**Windows (run.bat):**
```batch
@echo off
set PATH=%PATH%;C:\Program Files\Java\jdk1.8.0_XXX\bin
cd /d "%~dp0"
java -cp "bin;lib\mysql-connector-java-8.0.33.jar" view.Choisir
pause
```

**Linux/macOS (run.sh):**
```bash
#!/bin/bash
cd "$(dirname "$0")"
java -cp "bin:lib/mysql-connector-java-8.0.33.jar" view.Choisir
```

---

## 📖 GUIDE D'UTILISATION

### 1️⃣ PREMIÈRE CONNEXION - CLIENT

**Identifiants de Test:**
```
Username: client1
Password: client123
```

**Procédure:**
1. Lancer application
2. Clic "Client"
3. Saisir identifiants
4. Clic "Connexion"

**Interface Client:**
- Menu: Consulter tous les plats
- Sélectionner plats et ajouter au panier
- Voir panier → Modifier quantités
- Clic "Commander" → Commande enregistrée

---

### 2️⃣ CONNEXION - SERVEUSE

**Identifiants de Test:**
```
Username: serveuse1
Password: serveuse123
```

**Interface Serveuse:**
1. **Parcourir Menu** → Consulter plats
2. **Commandes en Cours** → Voir préparations
3. **Commandes Reçues** → Voir prêtes
4. **Générer Facture**:
   - Sélectionner commande
   - Clic "Facturiser"
   - Affichage détails et total
   - Options: Imprimer/Exporter

---

### 3️⃣ CONNEXION - CHEF

**Identifiants de Test:**
```
Username: admin
Password: admin123
```

**Interface Chef:**

**A. Gestion Menu:**
- **Ajouter Plat:**
  - Clic "Ajouter"
  - Saisir: Nom, Prix, Type Menu, Type Plat, Description
  - Clic "Enregistrer"

- **Modifier Plat:**
  - Sélectionner plat
  - Clic "Modifier"
  - Changer prix/description
  - Clic "Valider"

- **Supprimer Plat:**
  - Sélectionner plat
  - Clic "Supprimer"
  - Confirmer

**B. Gestion Commandes:**
1. **Voir Commandes Demandées:**
   - Nouvelle commande → Clic "En Cours"
   - Ou → Clic "Annuler"

2. **Commandes En Cours:**
   - Voir liste préparations
   - Quand prêt → Clic "Prête"
   - Notification serveuse

3. **Commandes Servies:**
   - Historique complète

---

## 🎮 CAS D'USAGE COMPLETS

### Scénario 1: Commande Client Complète

```
1. Client1 se connecte
   └─ Username: client1, Password: client123

2. Consulte menu
   └─ Voit: Salade Cesar (12.50€), Tarte pommes (9€)

3. Ajoute au panier
   └─ Ajoute: 2× Salade + 1× Tarte

4. Voir panier
   └─ Total: 34€

5. Commander
   └─ Reçoit numéro commande #1234

6. État commande visible:
   - Chef: "Commandes Demandées"
   - Serveuse: "Commandes en Cours"
```

### Scénario 2: Chef Prépare et Facture

```
1. Chef (admin) se connecte

2. Ajoute nouveau plat "Steak"
   ├─ Nom: Steak Saignant
   ├─ Prix: 25.50€
   ├─ Type: Menu Principal
   └─ Enregistré en BD

3. Voit commande client1
   ├─ 2× Salade + 1× Tarte
   └─ Clic "En Cours"

4. Prépare les plats

5. Quand prêt → Clic "Prête"
   └─ Notification: Serveuse alertée

6. Serveuse facturise
   ├─ Sélectionne commande
   ├─ Calcul: (2×12.50) + (1×9) = 34€
   ├─ Crée facture
   └─ Imprime/Archive
```

---

## 🔧 DÉPANNAGE

### Erreur: "Cannot find a class-path resource"

**Cause:** Fichier `conf.properties` manquant ou chemin incorrect

**Solution:**
```bash
# Vérifier que le fichier existe:
ls src/conf.properties

# Vérifier contenu:
cat src/conf.properties

# Re-compiler avec -d bin/:
javac -cp ".;lib/mysql-connector-java-8.0.33.jar" \
      src/**/*.java -d bin/
```

### Erreur: "Unable to load driver class: com.mysql.jdbc.Driver"

**Cause:** MySQL JDBC Driver non dans le classpath

**Solution:**
1. Télécharger: mysql-connector-java-8.0.33.jar
2. Placer dans `lib/`
3. Ajouter à classpath:
```bash
java -cp "bin:lib/*" view.Choisir
```

### Erreur: "Connection refused" à la base de données

**Cause:** MySQL Server n'est pas démarré ou port incorrect

**Solution:**
```bash
# Vérifier MySQL démarré:
sudo service mysql status

# Si arrêté, démarrer:
sudo service mysql start

# Vérifier port (défaut 3306):
netstat -an | grep 3306

# Ou vérifier dans conf.properties:
cat src/conf.properties
# Vérifier: jdbc.url et port
```

### Erreur: "Access denied for user 'root'"

**Cause:** Mot de passe MySQL incorrect

**Solution:**
1. Éditer `src/conf.properties`
2. Vérifier: `jdbc.password=pass`
3. Ou utiliser mot de passe correct:
```properties
jdbc.url=jdbc:mysql://localhost:3306/mydatabase
jdbc.user=root
jdbc.password=VOTRE_MOT_DE_PASSE
```

### Erreur: "Database 'mydatabase' does not exist"

**Cause:** Base de données non créée

**Solution:**
```bash
# Créer la base:
mysql -u root -p -e "CREATE DATABASE mydatabase;"

# Importer schéma:
mysql -u root -p mydatabase < database/schema.sql
```

### Application se ferme immédiatement

**Cause:** Exception non attrapée (vérifier console)

**Solution:**
1. Lancer depuis terminal pour voir erreur
2. Vérifier MySQL est démarré
3. Vérifier config JDBC
4. Vérifier fichier schéma.sql compilé

---

## ❓ FAQ

### Q: Je ne vois pas les interfaces graphiques

**R:** Vérifier que vous lancez `view.Choisir` et non une autre classe.
```bash
# Correct:
java -cp "bin:lib/*" view.Choisir

# Incorrect:
java -cp "bin:lib/*" model.Client
```

### Q: Comment réinitialiser la base de données?

**R:** Supprimer et recréer:
```bash
mysql -u root -p -e "DROP DATABASE mydatabase;"
mysql -u root -p -e "CREATE DATABASE mydatabase;"
mysql -u root -p mydatabase < database/schema.sql
```

### Q: Puis-je utiliser un IDE?

**R:** Oui! IntelliJ, NetBeans, Eclipse supportent Java.
- Importer le projet
- Configurer SDK Java 8+
- Ajouter mysql-connector-java.jar aux librairies
- Build et Run

### Q: Comment créer un nouvel utilisateur?

**R:** Via Inscription ou directement en BD:
```bash
mysql -u root -p mydatabase

INSERT INTO utilisateur (nomU, mdpU, role)
VALUES ('nouveau_user', 'password123', 'client');
```

### Q: Comment changer le port MySQL?

**R:** Éditer `src/conf.properties`:
```properties
# Port 3307:
jdbc.url=jdbc:mysql://localhost:3307/mydatabase
```

### Q: Puis-je utiliser PostgreSQL au lieu de MySQL?

**R:** Oui, mais il faudra:
1. Télécharger driver PostgreSQL
2. Modifier SQL dialect (schema.sql)
3. Modifier conf.properties:
```properties
jdbc.url=jdbc:postgresql://localhost:5432/mydatabase
```

### Q: Comment déployer sur un serveur?

**R:** Empaqueter en JAR et configurer:
```bash
# Créer JAR:
jar cvfm Restaurant.jar MANIFEST.MF -C bin .

# Lancer sur serveur:
java -jar Restaurant.jar
```

---

## 📞 SUPPORT

**En cas de problème:**

1. **Vérifier la Console:**
   - Erreurs MySQL
   - Exceptions Java
   - Stack traces

2. **Consulter la Documentation:**
   - RAPPORT_COMPLET.md
   - RESUME_EXECUTIF.md

3. **Vérifier les Prérequis:**
   - Java 8+
   - MySQL 5.7+
   - MySQL JDBC Driver

4. **Forum/Aide:**
   - Stack Overflow (tag: java, mysql)
   - Documention officielle Java/MySQL

---

## ✅ CHECKLIST INSTALLATION

- [ ] Java JDK 8+ installé et configuré
- [ ] MySQL Server 5.7+ démarré
- [ ] mysql-connector-java-8.0.33.jar téléchargé
- [ ] Base de données créée
- [ ] Schéma importé (schema.sql)
- [ ] conf.properties configuré
- [ ] Projet compilé sans erreurs
- [ ] Application lance avec view.Choisir
- [ ] Connexion avec client1/client123 réussie
- [ ] Interface graphique affichée correctement

---

**Document Généré :** 6 Mai 2026  
**Pour :** Système de Gestion de Restaurant - CPI2  

