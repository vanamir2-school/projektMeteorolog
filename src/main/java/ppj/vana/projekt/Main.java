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
import ppj.vana.projekt.service.MongoMeasurementService;
import ppj.vana.projekt.service.WeatherDownloaderService;

import java.util.Map;

@SpringBootApplication
@EnableJpaRepositories("ppj.vana.projekt.repositories")
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    @Autowired
    private MongoTemplate mongo;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Main.class);
        ApplicationContext ctx = app.run(args);

        // some temporal testing/outputs
        Map<Integer, Integer> measurementCnt = ctx.getBean(MongoMeasurementService.class).numOfRecordsUsingMapReduce();
        measurementCnt.forEach((k, v) -> System.out.println(String.format("cityID: %d, number of records: %d", k, measurementCnt.get(k))));
        ctx.getBean(Foo.class).makeSound();
        logger.error("ERROR LEVEL");
        System.out.println("Appid: " + ctx.getBean(WeatherDownloaderService.class).getAppid());


        // TODO - předělat na klasické JUnit testy
        // otestovani API
        WeatherDownloaderService weatherDownloaderService = ctx.getBean(WeatherDownloaderService.class);
        Measurement measurement1 = weatherDownloaderService.getWeatherByCityID(3077929);
        Measurement measurement2 = weatherDownloaderService.getWeatherByCityID(3071961);
        Measurement measurement3 = weatherDownloaderService.getWeatherByCityID(3067696);
        Measurement measurement4 = weatherDownloaderService.getWeatherByCityID(3067696);
        Measurement measurement5 = weatherDownloaderService.getWeatherByCityID(3067696);
        System.out.println(measurement1.toString());
        System.out.println(measurement2.toString());
        System.out.println(measurement3.toString());
        System.out.println(measurement4.toString());
        System.out.println(measurement5.toString());
        System.out.println(weatherDownloaderService.timestampToString(measurement1.getTimeOfMeasurement()));
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