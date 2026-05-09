# Système de Gestion de Restaurant

Application Java de gestion d'un restaurant, organisée en architecture MVC avec accès aux données via DAO et interface graphique Swing.

## Aperçu

Le projet couvre trois rôles principaux :
- Client : consulter le menu et passer commande
- Serveuse : suivre les commandes et générer les factures
- Cuisinier : gérer les plats et suivre la préparation

La base de données MySQL contient les tables principales suivantes :
- `utilisateur`
- `menu`
- `plat`
- `commande`
- `lignecommande`
- `platscommande`
- `facture`

## Structure du projet

- `src/controller` : contrôleurs métier
- `src/dao` : accès aux données
- `src/model` : entités métier
- `src/view` : interfaces Swing
- `src/util` : utilitaires et connexion
- `database/schema.sql` : schéma SQL

## Documentation

La documentation complète du projet est disponible dans [docs/mds](docs/mds) :
- [README_PRINCIPAL.md](docs/mds/README_PRINCIPAL.md)
- [RESUME_EXECUTIF.md](docs/mds/RESUME_EXECUTIF.md)
- [RAPPORT_COMPLET.md](docs/mds/RAPPORT_COMPLET.md)
- [GUIDE_INSTALLATION.md](docs/mds/GUIDE_INSTALLATION.md)
- [REFERENCE_API.md](docs/mds/REFERENCE_API.md)
- [INDEX_DOCUMENTATION.md](docs/mds/INDEX_DOCUMENTATION.md)
- [DOSSIER_REMISE.md](docs/mds/DOSSIER_REMISE.md)

## Lancement rapide

Prérequis : Java 8+, MySQL 5.7+ et le connecteur JDBC MySQL.

1. Créer la base de données et importer `database/schema.sql`
2. Vérifier `src/conf.properties`
3. Compiler le projet
4. Lancer la classe principale `view.Choisir` ou directement la classe principale `App.Main`

## Identifiants de test

- Client : `client1` / `client123`
- Serveuse : `serveuse1` / `serveuse123`
- Chef : `admin` / `admin123`

## Notes

Ce dépôt contient aussi des fichiers de remise et de navigation pour faciliter la consultation du rapport et la présentation du projet.

## Licence

Ce projet est distribué sous licence MIT. Voir le fichier [LICENSE](LICENSE).
