package ppj.vana.projekt;

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
import ppj.vana.projekt.model.MesHistory;
import ppj.vana.projekt.providers.ContextProvider;
import ppj.vana.projekt.providers.SqlProvider;
import ppj.vana.projekt.service.MeasurementService;
import ppj.vana.projekt.service.MesHistoryService;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

/**
 * extends SpringBootServletInitializer must be declared. otherwise TomCat server does not work
 */
@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories("ppj.vana.projekt")
public class Main extends SpringBootServletInitializer {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    @Autowired
    private MeasurementService measurementService;

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Main.class, args);

        MeasurementService mongo = ContextProvider.getContext().getBean(MeasurementService.class);
       // mongo.deleteAll();

        // docasna testovaci class
        ctx.getBean(Foo.class).makeSound();
    }


    @Profile({"devel"})
    @Bean(initMethod = "doProvision")
    public SqlProvider provisioner() {
        return new SqlProvider();
    }

}