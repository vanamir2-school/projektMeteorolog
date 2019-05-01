package ppj.vana.projekt.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ppj.vana.projekt.model.MesHistory;
import ppj.vana.projekt.service.CityService;
import ppj.vana.projekt.service.MesHistoryService;
import ppj.vana.projekt.service.WeatherDownloaderService;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class WeatherDownloadScheduler {

    private final Logger log = LoggerFactory.getLogger(WeatherDownloadScheduler.class);
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private final String READ_ONLY_ERROR = "READ-ONLY state is ON! You can not save new measurements";
    @Autowired
    MesHistoryService mesHistoryService;
    @Autowired
    private CityService cityService;
    @Autowired
    private WeatherDownloaderService weatherDownloaderService;
    @NotNull
    @Value("${app.readonly}")
    private Boolean readonly;
    @NotNull
    @Value("${app.refreshIntervalMinutes}")
    private Integer refreshIntervalMinutes;

    // true -> LAST_TIME < CURR_TIME +(INTERVAL/2)
    private boolean isDownloadPeriodGone() {
        MesHistory mesHistory = mesHistoryService.getLatest();
        if (mesHistory == null)
            return true;

        Timestamp latestMeasurement = mesHistoryService.getLatest().getTimestamp();
        int halfOfPeriod = (int) Math.ceil(refreshIntervalMinutes.floatValue() / 2);
        Timestamp currTimeMinusHalfPeriod = Timestamp.valueOf(LocalDateTime.now().minusMinutes(halfOfPeriod));
        return latestMeasurement.before(currTimeMinusHalfPeriod);
    }

    @Scheduled(fixedDelayString = "#{getConfigRefreshValue}")
    public void downloadMeasurement() {
        if (readonly) {
            log.error(READ_ONLY_ERROR);
            throw new IllegalStateException(READ_ONLY_ERROR);
        }
        // prevention of repeated measurement on server (to prevent downloading more then twice in download period)
        else if (!isDownloadPeriodGone())
            return;


        log.info("Measurement download started {}", dateFormat.format(new Date()));
        mesHistoryService.add(new MesHistory(Timestamp.valueOf(LocalDateTime.now())));
        weatherDownloaderService.saveWeatherToDatabase(cityService.getAll());
        log.info("Measurement download finished {}", dateFormat.format(new Date()));
    }
}
