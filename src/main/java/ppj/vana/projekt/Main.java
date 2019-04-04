package ppj.vana.projekt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import ppj.vana.projekt.provisioning.Provisioner;
import ppj.vana.projekt.service.CityService;
import ppj.vana.projekt.service.CountryService;

@SpringBootApplication
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public Main(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Main.class);
        ApplicationContext ctx = app.run(args);

        Foo foo = ctx.getBean(Foo.class);
        foo.makeSound();
        logger.error("ERROR LEVEL");
        logger.warn("WARN LEVEL");
    }

    @Bean
    public CityService cityService() {
        return new CityService();
    }

    @Bean
    public CountryService countryService() {
        return new CountryService();
    }

    @Profile({"devel", "test", "prod"})
    @Bean(initMethod = "doProvision")
    public Provisioner provisioner() {
        return new Provisioner();
    }

}