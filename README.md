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

# gestion de exception handler avec exception handler advice
    - on crée une classe avec le nom ExceptionHandler
    - on met l'anotation @RestControllerAdvice en important
        springframework.bin.anotation.RestControllerAdvice
    - pour captuer des érreurs des arguments,
        - on return notre reponse customisée (Result)avec comme nom de fonction
            handleValidationException qui prend en paramètre le nom de l'exception
            dans notre cas MethodArgumentNotValidException on le nomme exception
        - on met l'anotation pour renvoyé le status de cette réponse
                @ResponseStatus(HttpStatus.BAD_REQUEST)
        - dans la fonction, on récupère toutes les erreurs dans une liste des erreurs
            List<ObjectError> errors = exception.getBindingResult().getAllErrors();
        - on initialise une map pour préparer le stockage des erreurs en clé, valeur
            Map<String, String> map = new HashMap();
        - on boucles les erreurs pour les stocker dans map
              errors.forEach(error->{
                    String key = ((FieldError) error).getField();
                    String val = error.getDefaultMessage();
                    map.put(key,val);
                });
        - on retourne la réponse 
            return new Result(false,StatusCode.INVALID_ARGUMENTS,"arguments invalid",map);





# illustraction de récupération des erreur dans la fonction handleValidationException
     class ObjectErreur {
            public String key;
            public String value;
            public ObjectErreur(String key, String value) {
                this.key = key;
                this.value = value;
            }

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            @Override
            public String toString() {
                return "ObjectErreur{" +
                        "key='" + key + '\'' +
                        ", value='" + value + '\'' +
                        '}';
            }
        }
        class GestionOb {
            public List<ObjectErreur> erreurList = new ArrayList<>();

            public void setErreurs (ObjectErreur objErreur)
            {
                 this.erreurList.add(objErreur);
            }

            public List<ObjectErreur> getErreurList() {
                return erreurList;
            }
            public int getSizeErreur() {
                return erreurList.size();
            }

            @Override
            public String toString() {
                return "GestionOb{" +
                        "erreurList=" + erreurList +
                        '}';
            }
        }

        ObjectErreur objectErreur1 = new ObjectErreur("nom", "nom est requis");
        ObjectErreur objectErreur2 = new ObjectErreur("prenom", "prenom est requis");
        ObjectErreur objectErreur3 = new ObjectErreur("age", "age est requis");
        ObjectErreur objectErreur4 = new ObjectErreur("text", "text est requis");
        ObjectErreur objectErreur5 = new ObjectErreur("taille", "taille est requise");
        ObjectErreur objectErreur6 = new ObjectErreur("password", "password est requis");
        ObjectErreur objectErreur7 = new ObjectErreur("email", "email est requis");

        GestionOb gestionOb = new GestionOb();
        gestionOb.setErreurs(objectErreur1);
        gestionOb.setErreurs(objectErreur2);
        gestionOb.setErreurs(objectErreur3);
        gestionOb.setErreurs(objectErreur4);
        gestionOb.setErreurs(objectErreur5);
        gestionOb.setErreurs(objectErreur6);
        gestionOb.setErreurs(objectErreur7);

        List<ObjectErreur> errors = gestionOb.getErreurList();

        Map<String, String> map = new HashMap<>(6);
        errors.forEach(error->{
            String key =  error.getKey();
            String val = error.getValue();
            map.put(key,val);
        });
        System.out.println(map);


