# PROJET DE FIN D'ANNEE 2024 Bachelor 3
# Route convention
  GET|HEAD   jobs .......................................................... JobController@index
  POST       jobs .......................................................... JobController@store
  GET|HEAD   jobs/create .................................................. JobController@create
  GET|HEAD   jobs/{job} ..................................................... JobController@show
  PATCH      jobs/{job} ................................................... JobController@update
  DELETE     jobs/{job} ................................................... JobController@delete
  GET|HEAD   jobs/{job}/edit ................................................ JobController@edit
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

# Fonctionnement de service
    - on inject la dépendance de JdbcClient
        - pour crée des requette on appelle la fonction sql de jdbcClient
            jdbcClient.sql("select * from ville where ville_id= :villeId ")
        - pour mettre un parametre on on appelle la fonction param de jdbcClient
            .param("villeId", id)
        - pour mettre plusieur parametres, on on appelle la fonction params de jdbcClient
            .params(List.of(ville.getNom()))
        - s'il s'agit d'un optional on leve une xception
             .optional().orElseThrow(()-> new ObjectNotFoundException("ville", id));
        - puis on return jdbcClient


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


# LEST TESTS DE SERVICE  tests unitaires avec le framework Mockito
    - On génère la classe de test avec intelij idea avec control+enter.
    
      - on importe cette anotation  sur la classe de test
        @ExtendWith(MockitoExtension.class).
    
      - dans la classe de test on inject le service et le repository en charge
        des transaction via la base de donée dans notre cas
        @Mock
        VilleRepository villeRepository;

        @InjectMocks
        VilleService villeService;

      - s'il s'agit d'une liste, on initialiste la liste
        List<Ville> villeList = new ArrayList<>();
    
      - on crée un fake liste dans la fonction setup comme suit
        @BeforeEach
        void setUp() {
         Ville paris = new Ville("Paris");
         villeList.add(paris);
        }
    
  # tester findAll doit etre suivie de l'anotation @Test,
     ce test se fait en trois phase.
    - Given, c'est la phase numéro un,Lorqu'on appelle villeService dans notre context doit retourner villeList
      given(this.villeRepository.findAll()).willReturn(villeList);
    - When,ici on  Appelle findAll de villeService
      List<Ville> actualVilles = villeService.findAll();
    - Then, Vérifie si la valeur de actualVilles == villeList, et on Invoque une fois findAll() de villeService
      verify(this.villeRepository,times(1)).findAll();, on vérify le given
  
  # tester findByIdNotFound
    - Given, si on cherche un object qui nexiste pas, on remplace id par Mockito.any() et prend 
      en paramètre l'id rechercher dans notre cas le type est en Long
      given(this.villeRepository.findById(Mockito.any(Long.class))).willReturn(Optional.empty());
    - when, la recherche de id 12 lèvera une exception et on la récupère
      Throwable throwable = catchThrowable(() -> villeService.findById(Long.valueOf("12")));
    - Then, on s'assure que l'exception levé est une instance de ObjectNotFoundException, et 
      qu'il renvoie le message " Nous ne retrouvons pas l'entité Ville not found with id 12 "
        assertThat(throwable)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Nous ne retrouvons pas l'entité Ville not found with id 12");
        verify(this.villeRepository,Mockito.times(1)).findById(Long.valueOf("12"));

  # tester deleteById
    - on crée un fake data
      Ville paris = new Ville(Long.valueOf("22"),"POLO");
    - Given, on vérifie si d'abord un object avec id=22 nous renvoie un Optional de cet object,
      puis on appelle la fonction static doNothin() pour quelle fasse rien lorsqu'on demande à supprimer
      lobject par id
        given(this.villeRepository.findById(1L)).willReturn(Optional.of(paris));
        doNothing().when(this.villeRepository).deleteById(1L);
    - When, 
        this.villeService.deleteById(1L);
    - Then
        verify(this.villeRepository,times(1)).deleteById(1L);
    NB:
    un petit problème lors de test, javais oublié de de rechercher d'abord lexistance 
    de lobject par id a supprimé dans le service et j'ai eu une erreur ....lenient()




# CONCEPT DE TEST

# @Mock: 
    Cette annotation est utilisée pour créer un mock (une simulation) d'une dépendance
    externe d'une classe que vous testez. Par exemple, si votre classe dépend d'un service 
    externe, vous pouvez utiliser @Mock pour créer un mock de ce service, ce qui vous permet 
    de simuler son comportement pendant le test.
# @InjectMocks: 
    Cette annotation est utilisée pour injecter les mocks créés avec @Mock dans la classe que 
    vous testez. Elle est utilisée pour injecter les dépendances simulées dans la classe sous test.

# Test controller
    - on importe ces annotations au dessus de la classe
    @SpringBootTest, et @ActiveProfiles(value = "dev")
    @AutoConfigureMockMvc(addFilters = false)// false désactive la sécurité
    NB: En résumé, @Autowired est utilisé pour injecter les dépendances réelles dans votre application,
    tandis que @MockBean est utilisé pour créer des mocks de beans pour les tests unitaires.
    @MockBean
    WizardService wizardService;
    
    
    @Autowired
    MockMvc mockMvc;
    MockMvc est un outil fourni par Spring pour tester les contrôleurs web de manière unitaire,
    sans démarrer un serveur web réel. Cela permet de simuler des requêtes HTTP et d'évaluer 
    les réponses de manière simple et rapide dans un contexte de test.

    @Mock
    IdWorker idWorker; simule id

    @Value("${api.endpoint.base-url}"), récupère la valeur crée dans application.properties
    String baseUrl;

    @Autowired
    ObjectMapper mapper;
    ObjectMapper est une classe centrale de la bibliothèque Jackson pour le traitement 
    des données JSON en Java. Jackson est une bibliothèque populaire pour la sérialisation 
    et la désérialisation d'objets Java vers et depuis JSON. ObjectMapper fournit des méthodes 
    pour convertir des objets Java en représentations JSON et vice versa.

# test getVille in controlleur
        @Test
    void getVilles() throws Exception {
        //Given
        given(villeService.findAll()).willReturn(villeList);

        //When Then
        this.mockMvc.perform(MockMvcRequestBuilders.get(url+"/ville")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("toutes les villes"))
                .andExpect(jsonPath("$.sata", Matchers.hasSize(villeList.size())))
                .andExpect(jsonPath("$.data[0].name").value("Paris"))
                .andExpect(jsonPath("$.data[1].name").value("mossaka"));
    }
    - le Given, on test  le service injecter dans le controller
    - When and Then on simule le request
# test updateVilleByIdSuccess in controller
    ça se passe ne trois étape
    - 1 on simule un object à metre à jour
    //VilleDto ready to update
    VilleDto villeDto = new VilleDto(1L,"Paris update");

    - 2 on convertis cet object en json pour l'ajouter dans le contenue afin de l'envoyé 
      sous forme de json dans notre controlleur
    //Convert VilleDto to json
    String jsonMapper = objectMapper.writeValueAsString(villeDto);

    - 3 on convertis cet object qui est en dto en en objet initial
    //Convert Villedto to ville
    Ville villeUpdated = new Ville(villeDto.villeId(), villeDto.nom());

    - 4 puis on test le service avec le given. 
    dans ce given, on simule la classe avec Mockito.any(Entity.class) et le id on met eq(valeur de id)
    //Given
    given(this.villeService.update(Mockito.any(Ville.class),eq(1L))).willReturn(villeUpdated);

# differencre entre :
    doThrow(...).when(...) et given(...).willThrow(...):
    les deux, Configurent une méthode mockée pour qu'elle lance une exception lorsqu'elle 
    est appelée avec un argument spécifique.
    - doThrow(...).when(...):Utilisé pour des méthodes (**`void`**) qui doivent lancer une exception.
    - given(...).willThrow(...):Utilisé pour des méthodes qui retournent une **_`valeur`_** ou sont vérifiées 
        dans une condition given.
# test save in controller
        //VilleDto ready to update
        VilleDto villeDto = new VilleDto(null,"Paris save new ville");

        //Convert VilleDto to json
        String jsonMapper = objectMapper.writeValueAsString(villeDto);

        //Convert Villedto to ville
        Ville villesaved = new Ville(villeDto.villeId(), villeDto.nom());

        //Given, on utilise Mockito.any(Ville.class)), pour qu'on est pas de problème avec l'id en testant
        given(this.villeService.save(Mockito.any(Ville.class))).willReturn(villesaved);

        //When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.post(url+"/villes")
                        .content(jsonMapper)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("ville ajoutée"))
                .andExpect(jsonPath("$.data.nom").value("Paris save new ville"));

# rename column
    alter table client_user rename column rason_social to raison_sociale;
# NOTE BIEN pour le test controller update
    - 4 puis on test le service avec le given.
      dans ce given, on simule la classe avec Mockito.any(Entity.class) et le id on met eq(valeur de id)

# sql COLLATE
    c'est-à-dire un ensemble de règles qui déterminent comment les chaînes de caractères sont comparées et ordonnées.
# CHARSET
     fait référence à l'ensemble de caractères utilisé pour stocker et interpréter les données textuelles dans une base
      de données
# NB GIt
    les branches doient être crée au fure et à messure que le projet avances

# GESTION DE BASE DE DONNEE TWO pour la premier base de donnée
1.       on importe sur la classe configuration
        @Configuration(proxyBeanMethods = false)
        - proxyBeanMethods = false,Quand proxyBeanMethods est défini à true, Spring crée un proxy CGLIB
          pour la classe de configuration. Cela permet de garantir que les appels de méthode au sein de 
          la classe de configuration qui retournent des beans utilisent le proxy, et donc retournent le même 
          instance de bean plutôt que de créer une nouvelle instance.
        - Quand proxyBeanMethods est défini à false, Spring n'utilise pas de proxy pour la classe de configuration.
          Les appels de méthode à l'intérieur de la classe ne seront pas interceptés par le proxy, ce qui peut 
          améliorer les performances en éliminant l'overhead du proxy.
          Cependant, cela signifie que si une méthode de bean est appelée directement dans la classe de configuration,
          elle ne sera pas gérée par Spring, ce qui peut mener à la création de plusieurs instances de beans si la méthode
          est appelée plusieurs fois.

2.        on configure le custom propertie, avec l'anotation de primary,
           @Bean
           @Primary
           @ConfigurationProperties("app.datasource.afrimeta")
           public DataSourceProperties dataSourceProperties() {
            return new DataSourceProperties();
           }

          ce que fait la méthode
          Elle définit un bean de type DataSourceProperties.
          La méthode retourne une nouvelle instance de DataSourceProperties, 
          qui sera configurée avec les propriétés définies sous le préfixe app.datasource.afrimeta

           - @ConfigurationProperties est une annotation en Spring Boot utilisée pour la gestion de la configuration
             externe. Elle permet de lier les propriétés définies dans les fichiers de configuration 
             (comme application.properties ou application.yml) directement à des objets Java.
            @Bean :
                  Cette annotation indique que la méthode dataSourceProperties produit un bean 
                  à gérer par le conteneur Spring. Un bean est un objet qui est instancié, assemblé
                  et géré par le conteneur Spring.
            @Primary:
                   indique que c'st la source principale

3.         On initialise la base de donné
           @Bean
           @Primary
           public HikariDataSource afrimetaDataSource(DataSourceProperties afrimetaDataSourceProperties) {
           return afrimetaDataSourceProperties.initializeDataSourceBuilder()
                                               .type(HikariDataSource.class)
                                              .build();
           }
           explication:
                      initializeDataSourceBuilder() utilise les propriétés encapsulées pour initialiser 
                       un constructeur de source de données.
                      .type(HikariDataSource.class): indique que nous voulons que la source de données
                        soit de type HikariDataSource. HikariDataSource est une implémentation de DataSource
                          qui offre des performances et une efficacité élevées grâce à un pool de connexions léger.
                      .build(): construit l'instance de HikariDataSource en utilisant les propriétés fournies.

4.          @Bean
            public DataSourceScriptDatabaseInitializer afrimetaDataSourceScriptDatabaseInitializer(
            @Qualifier("afrimetaDataSource")DataSource dataSource) {
              var settings = new DatabaseInitializationSettings();
              settings.setSchemaLocations(List.of("classpath:afrimeta-schema.sql"));
              settings.setMode(DatabaseInitializationMode.EMBEDDED);
              return new DataSourceScriptDatabaseInitializer(dataSource, settings);
            }   
            explication:
            DataSourceScriptDatabaseInitializer qui initialise une base de données en exécutant un script SQL spécifié,
            en utilisant la source de données afrimetaDataSource. Cette initialisation est configurée pour s'appliquer 
            uniquement aux bases de données embarquées(ce qui signifie qu'elle ne sera pas exécutée pour des bases de 
                                                        données externes comme MySQL, PostgreSQL, etc.).

# GESTION DE BASE DE DONNEE TWO pour la deuxieme base de donnée
1.          comme la premiere base de donné, la datasource utilisé pour ce bean  
            sera app.datasource.afrimeta.client.shop ,
            @Bean
            @ConfigurationProperties("app.datasource.afrimeta.client.shop")
            public DataSourceProperties afrimetaClientDataSourceProperties() {return new DataSourceProperties();}
            
            explication:
            Ce code crée un bean DataSourceProperties configuré avec des propriétés spécifiques définies dans
            les fichiers de configuration de l'application, en le marquant comme la source de données principale.


2.           @Bean
             public DataSource afrimetaClientDataSource(DataSourceProperties afrimetaClientDataSourceProperties) {
             return DataSourceBuilder
               .create()
               .url(afrimetaClientDataSourceProperties.getUrl())
               .username(afrimetaClientDataSourceProperties.getUsername())
               .password(afrimetaClientDataSourceProperties.getPassword())
               .build();
             }

             explication:
                Ce code définit un bean DataSource nommé afrimetaClientDataSource dans le contexte Spring. 
                Il utilise les propriétés (url, username, password) fournies par un objet DataSourceProperties 
                pour configurer et construire le DataSource à l'aide de DataSourceBuilder.

3.           on utilise la meme methode que la configuration de la premiere Bd
            
            @Bean
            public DataSourceScriptDatabaseInitializer afrimetaDataSourceScriptDatabaseInitializer(
            @Qualifier("afrimetaDataSource")DataSource dataSource) {
              var settings = new DatabaseInitializationSettings();
              settings.setSchemaLocations(List.of("classpath:afrimeta-schema.sql"));
              settings.setMode(DatabaseInitializationMode.EMBEDDED);
              return new DataSourceScriptDatabaseInitializer(dataSource, settings);
            }   
            explication:
            DataSourceScriptDatabaseInitializer qui initialise une base de données en exécutant un script SQL spécifié,
            en utilisant la source de données afrimetaDataSource. Cette initialisation est configurée pour s'appliquer 
            uniquement aux bases de données embarquées(ce qui signifie qu'elle ne sera pas exécutée pour des bases de 
                                                        données externes comme MySQL, PostgreSQL, etc.).



## NB
       - par défault si le username n'est pas définit, il prend le nom de l'ordinateur
       dans mon cas c'est esprit,
       ** Access denied for user 'esprit'@'localhost' (using password: YES)**
       or le username dans mon application propertie est root
       ** app.datasource.afrimeta.client.shop.username=root **

      -  le repository utilise le nom de la méthode crée dans AfrimetaApplication,
         dans notre cas c'est afrimetaClientJdbcClient
                           @Bean
                           JdbcClient afrimetaClientJdbcClient(@Qualifier("afrimetaClientDataSource") DataSource afrimeClienttaDataSource){
                           return JdbcClient.create(afrimeClienttaDataSource);
                           }
      @Repository
      @Qualifier("afrimetaClientJdbcClient")
      public interface MagasinRepository extends JpaRepository<Magasin, Long> {
      }















# comprehension des anotations    
#  @InjectMocks
      @Mock
      private DependencyService dependencyService;

      @InjectMocks
      private MyService myService;
      # Explication:
      Ici, @InjectMocks va injecter le mock dependencyService dans l'instance de myService.
- Bibliothèque : Utilisée avec Mockito.
- Portée : Locale au test.
- Usage : Injecte les mocks créés avec @Mock ou @Spy dans l'objet testé.
- Contexte : Utilisée pour préparer l'objet sous test et injecter les dépendances nécessaires

# @MockBean
      @MockBean
      private DependencyService dependencyService;
      
      @Autowired
      private MyService myService;
      
      Explication:
      @MockBean va enregistrer dependencyService comme un bean mock dans le contexte Spring,
      et Spring injectera ce mock dans myService

- Bibliothèque : Utilisée avec Spring Boot (et le module Spring Boot Test).
- Portée : Context global de l'application.
- Usage : Crée et enregistre un bean mock dans le contexte de Spring Application.
- Contexte : Utilisée pour les tests d'intégration où un contexte Spring est chargé

# SQL 
## utilitie
            SET FOREIGN_KEY_CHECKS = 0; //Désactiver la vérification des clés étrangères
            SET FOREIGN_KEY_CHECKS = 1; // Réactiver la vérification des clés étrangères
## ManyToMany
      @OneToMany(fetch = FetchType.LAZY,
         cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
      @JoinTable(
         name="lier",
         joinColumns = @JoinColumn(name = "taille_id"),
         inverseJoinColumns = @JoinColumn(name = "type_produit_id")
      )
      private List<TypeProduit> typeProduits;
1. dans les deux sens on crée une liste de l'inverse de lentité
      private List<TypeProduit> typeProduits
2. puis on joint la table qui portera les deux clé
   - on nomme cette table
   - on précise le nom de la collonne de la table ou on se trouve
   - on précise le nom de la collonne de la table inverse
     @JoinTable(
     name="lier",
     joinColumns = @JoinColumn(name = "taille_id"),

# définition de BDDMockito
      Behavior Driven Development style of writing tests uses 
      // given // when // then comments as fundamental parts of
      your test methods. This is exactly how we write our tests
      and we warmly encourage you to do so!
# test
      Throwable throwable = assertThrows(ObjectNotFoundException.class, () -> service.findById(1L));

# GESTION D'IMAGE avec insertion de plusieur images pour un produit
-  créé lentité image avec ces attibuts et l'anotation @Embeddable , 
   sur la class Image ainsi que ces constructeurs et getters, setters
- dans la class produit, on cree une liste set de images avec ces anotations 
  - @ElementCollection : Indique que la collection d'éléments doit être gérée comme une entité intégrée.
  - @CollectionTable : Spécifie le nom de la table qui contiendra les éléments intégrés. joinColumns définit la clé étrangère qui lie cette table à l'entité principale.
  - @AttributeOverrides : Utilisé pour redéfinir les noms des colonnes pour les champs de la classe embeddable.

# gestion affichage image
on coverti en string
