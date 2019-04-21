package ppj.vana.projekt;

import com.mongodb.DBObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import ppj.vana.projekt.providers.ContextProvider;
import ppj.vana.projekt.providers.SqlProvider;
import ppj.vana.projekt.service.CountryService;
import ppj.vana.projekt.service.MongoMeasurementService;

import java.util.List;

/**
 * extends SpringBootServletInitializer must be declared. otherwise TomCat server does not work
 */
@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories("ppj.vana.projekt")
public class Main extends SpringBootServletInitializer {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    @Autowired
    private MongoMeasurementService mongoMeasurementService;

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Main.class, args);
        // ctxProvider
        System.out.println(ContextProvider.getContext().getBean(CountryService.class).getAll().toString());


        // TODO - začít používat tuto krásnou funkci
        List<DBObject> measurements = ContextProvider.getContext().getBean(MongoMeasurementService.class).readAverage(2825297);
        System.out.println(measurements);


        // docasna testovaci class
        ctx.getBean(Foo.class).makeSound();
    }


    @Profile({"devel"})
    @Bean(initMethod = "doProvision")
    public SqlProvider provisioner() {
        return new SqlProvider();
    }

}