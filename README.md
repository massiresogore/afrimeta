# PROJET DE FIN D'ANNEE 2024 Bachelor 3
## Afriméta

# cette commande
    SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
veut dire de ne pas insérer une valeur 0 dans les collonne auto incrémentée

# START TRANSACTION;
    Cette instruction commence une transaction SQL. Une transaction est un ensemble
    d'opérations SQL qui sont traitées comme une seule unité. Elle garantit que toutes
    les opérations à l'intérieur de la transaction sont exécutées avec succès ou aucune
    d'entre elles n'est exécutée du tout. Cela assure l'intégrité des données en cas 
    d'erreurs ou de pannes.

# SET time_zone = "+00:00";
    Cette instruction définit le fuseau horaire pour la session de base de données.
    En définissant time_zone sur "+00:00", elle indique que les timestamps stockés 
    et récupérés dans la base de données seront interprétés comme étant dans le fuseau 
    horaire UTC (temps universel coordonné). Cela peut être utile pour garantir une
    cohérence dans les enregistrements temporels, en particulier dans les systèmes 
    distribués ou internationaux.
