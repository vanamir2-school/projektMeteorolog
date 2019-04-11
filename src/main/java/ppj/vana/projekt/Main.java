package ppj.vana.projekt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.core.MongoTemplate;
import ppj.vana.projekt.data.Measurement;
import ppj.vana.projekt.provisioning.Provisioner;
import ppj.vana.projekt.service.CityService;
import ppj.vana.projekt.service.MongoMeasurementService;

import java.util.List;

@SpringBootApplication
@EnableJpaRepositories("ppj.vana.projekt.repositories")
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    @Autowired
    private MongoTemplate mongo;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Main.class);
        ApplicationContext ctx = app.run(args);

        CityService cs = ctx.getBean(CityService.class);
        System.out.println(cs.getAll().toString());

        MongoMeasurementService measurementService = ctx.getBean(MongoMeasurementService.class);
        List<Measurement> measurementList = measurementService.findAllRecordForCityID(3077929);
        measurementList.forEach(System.out::println);

        Foo foo = ctx.getBean(Foo.class);
        foo.makeSound();

        logger.error("ERROR LEVEL");
    }

    @Bean
    public MongoMeasurementService mongoUserService() {
        return new MongoMeasurementService(mongo);
    }

    @Profile({"devel"})
    @Bean(initMethod = "doProvision")
    public Provisioner provisioner() {
        return new Provisioner();
    }

}