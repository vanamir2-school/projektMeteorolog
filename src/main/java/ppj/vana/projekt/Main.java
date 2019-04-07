package ppj.vana.projekt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ppj.vana.projekt.provisioning.Provisioner;
import ppj.vana.projekt.service.CityService;

@SpringBootApplication
@EnableJpaRepositories("ppj.vana.projekt.repositories")
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Main.class);
        ApplicationContext ctx = app.run(args);

        CityService cs = ctx.getBean(CityService.class);
        System.out.println(cs.getAll().toString());

        Foo foo = ctx.getBean(Foo.class);
        foo.makeSound();

        logger.error("ERROR LEVEL");
        logger.warn("WARN LEVEL");
    }

    @Profile({"devel"})
    @Bean(initMethod = "doProvision")
    public Provisioner provisioner() {
        return new Provisioner();
    }

}