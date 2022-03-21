# Projet de gestion de portefeuilles financier
Le projet comporte comme fonctionnalités de base une application serveur et deux applications clientes : une permettant la gestion de portefeuille(s) financier par un client d'institution financière ; l'autre permettant la gestion des produits financier par une institution financière et l'application serveur qui fourni des services web pour les applications clientes.
À cela seront rattachés les extensions de chaque membre du projet.


## Structure générale du projet
Le projet dans sa globalité comporte les sous projets suivants:

### 1) financial_portfolio_server: 
Application serveur fournissant des services Web pour toutes les autres applications clientes.
C'est un projet gradle basé sur le framework Spring Boot du fait de sa complémentarité pour la création d'API sécurisées qui nous fournit une structuration en modèle MVC (Model View Controller) avec lequel nous définissons la couche de persistance avec Spring Data JPA pour nos modèles et opérations de CRUD Repositories, ainsi que le RestController pour la definition de nos ressources web.
La base de données est basée sur DerbyServerEmbedded, donc embarquée dans le projet serveur et qui est lancée au démarrage de l'application. La base de données est créée et placée dans le dossier "database" du répertoire courrant et est automatiquement mise à jour à chaque démarrage du serveur par rapport aux modèles.
L'auhentification est basé sur JWT (Json Web Token).

### 2) financial_portfolio_customer:
Application cliente permettant à un client d'institution financière de gerer ses portefeuilles financier.
C'est un projet qui utilise JavaFX et qui sollicite ses services de l'application serveur

### 3) financial_portfolio_server:
Application cliente permettant à une institution financière de gerer ses produits financier.
C'est un projet qui utilise JavaFX et qui sollicite ses services de l'application serveur


## Membres Groupe 2 :
    - Frank NYATCHO TONYA 213349
    - Nicolas DELPLANQUE 200419
    - Pierre-Louis D’AGOSTINO 200197
    - Arsène MUJYABWAMI 181954
