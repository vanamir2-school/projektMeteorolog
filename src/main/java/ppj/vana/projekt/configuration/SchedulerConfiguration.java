package ppj.vana.projekt.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ppj.vana.projekt.service.UtilService;

import javax.validation.constraints.NotNull;

@Configuration
public class SchedulerConfiguration {

    private final Logger log = LoggerFactory.getLogger(SchedulerConfiguration.class);
    @NotNull
    @Value("${app.refreshIntervalMinutes}")
    private int refreshIntervalMinutes;

    @Bean
    public String getConfigRefreshValue() {
        log.info("Measurement interval in minutes: " + refreshIntervalMinutes);
        return Long.toString(refreshIntervalMinutes * UtilService.ONE_MINUTE_MILLISECONDS);
    }
}
