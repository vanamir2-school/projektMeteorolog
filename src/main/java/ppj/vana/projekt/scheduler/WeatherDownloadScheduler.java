package ppj.vana.projekt.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ppj.vana.projekt.service.CityService;
import ppj.vana.projekt.service.WeatherDownloaderService;

import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class WeatherDownloadScheduler {

    private final Logger log = LoggerFactory.getLogger(WeatherDownloadScheduler.class);
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    // private Long msBetweenMeasurements = (long)refreshIntervalMinutes * 60 * 1000;  // 11 minutes... 11*60*1000... 660 000
    private final String READ_ONLY_ERROR = "READ-ONLY state is ON! You can not save new measurements";
    @Autowired
    private CityService cityService;
    @Autowired
    private WeatherDownloaderService weatherDownloaderService;
    @NotNull
    @Value("${app.readonly}")
    private Boolean readonly;


    @Scheduled(fixedDelayString = "#{getConfigRefreshValue}")
    public void downloadMeasurement() {
        if (readonly) {
            log.error(READ_ONLY_ERROR);
            throw new IllegalStateException(READ_ONLY_ERROR);
        }
        log.info("Measurement download started {}", dateFormat.format(new Date()));
        weatherDownloaderService.saveWeatherToDatabase(cityService.getAll());
        log.info("Measurement download finished {}", dateFormat.format(new Date()));
    }
}
