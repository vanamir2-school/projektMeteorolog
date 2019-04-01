package ppj.vana.projekt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ppj.vana.projekt.configs.AppConfiguration;

// USEFULL TIPS:
// COMMIT... CTRL+K
// PUSH  ... CTRL+K+SHIFT
// MAVEN OKNO ... View-Tools-Maven


// -------------------------------- DB COMMAND LINE CONNECTION AND TEST
// https://stackoverflow.com/questions/44481917/mysql-shell-is-not-able-to-connect-to-mysql-server?rq=1
// \sql
// \connect root@localhost
// USE dbname;
// -------------------------------- ------------------------------------------------

// ------------------------------------------------ PATH to project folder( temp personal usage )
// C:\Users\mirav\IdeaProjects\projektMeteorolog

// ------------------------------------------------ BUILD + RUN (v POMku je spusteni ihned po compile)
// mvn compile

// ------------------------------------------------ DEPLOY
// mvn deploy
// cesta k JAR (C:\Users\mirav\Desktop\TUL\Semestr 2\PPJ\SEMESTRALKA\projektVana\meteorolog)
// RUN: java -jar .\meteorolog-0.1_BETA.jar args

@SpringBootApplication
public class Main
{

    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    //@Autowired
    //private NamedParameterJdbcOperations namedParameterJdbcOperations;

  /*  @Bean
    public Foo offersDao() {
        return new Foo("Duck duck");
    }*/

    //@Profile({"devel", "test"})
    //@Bean(initMethod = "doProvision")
    //public Provisioner provisioner() {
    //    return new Provisioner();
    //}

    public static void main(String [] args)
    {
        logger.info("Entering application.");
        System.out.println("This is my package! Those are args: " + (args != null ? String.join(", ", args) : "null") );

        SpringApplication app = new SpringApplication(Main.class);
        ApplicationContext ctx = app.run(args);

        Foo foo = ctx.getBean(Foo.class);
        foo.makeSound();

        // TOTO se predela na
        //Provisioner provisioner = new Provisioner();
        //provisioner.testDB();

        logger.info("Exiting application. - INFO LEVEL");
        logger.debug("Debug LEVEL");
        logger.error("ERROR LEVEL");
        logger.trace("TRACE LEVEL");

        AppConfiguration cfg = ctx.getBean(AppConfiguration.class);
        System.out.println(cfg.toString());
    }

}