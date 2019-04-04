package ppj.vana.projekt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import ppj.vana.projekt.configs.AppConfiguration;
import ppj.vana.projekt.provisioning.Provisioner;

@SpringBootApplication
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    @Autowired
    private NamedParameterJdbcOperations namedParameterJdbcOperations;

  /*  @Bean
    public Foo offersDao() {
        return new Foo("Duck duck");
    }*/

    @Profile({"devel", "test", "prod"})
    @Bean(initMethod = "doProvision")
    public Provisioner provisioner() {
        return new Provisioner();
    }

    public static void main(String[] args) {
        logger.info("Entering application.");
        System.out.println("This is my package! Those are args: " + (args != null ? String.join(", ", args) : "null"));

        SpringApplication app = new SpringApplication(Main.class);
        ApplicationContext ctx = app.run(args);

        Foo foo = ctx.getBean(Foo.class);
        foo.makeSound();

        logger.info("Exiting application. - INFO LEVEL");
        logger.debug("Debug LEVEL");
        logger.error("ERROR LEVEL");
        logger.trace("TRACE LEVEL");

        AppConfiguration cfg = ctx.getBean(AppConfiguration.class);
        System.out.println(cfg.toString());
    }

}