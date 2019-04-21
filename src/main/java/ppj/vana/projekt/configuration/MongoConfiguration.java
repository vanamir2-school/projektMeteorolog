package ppj.vana.projekt.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import ppj.vana.projekt.service.MongoMeasurementService;

@Configuration
public class MongoConfiguration {

    @Autowired
    private MongoTemplate mongo;

    @Bean
    public MongoMeasurementService mongoUserService() {
        return new MongoMeasurementService(mongo);
    }

}
