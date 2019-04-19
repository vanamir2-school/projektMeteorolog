package ppj.vana.projekt.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ppj.vana.projekt.model.City;
import ppj.vana.projekt.model.Measurement;
import ppj.vana.projekt.service.CityService;
import ppj.vana.projekt.service.MongoMeasurementService;
import ppj.vana.projekt.service.UtilService;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Component
public class DataExpirationScheduler {

    private static final long deleteExpireTimer = 600000L; // 10 minutes ... 10*60*1000
    private final Logger log = LoggerFactory.getLogger(DataExpirationScheduler.class);
    @NotNull
    @Value("${app.daysToExpire}")
    private Integer daysToExpire;
    @Autowired
    private MongoMeasurementService measurementService;
    @Autowired
    private CityService cityService;

    @Scheduled(fixedDelay = deleteExpireTimer)
    public void downloadMeasurement() {
        if (daysToExpire < 1 || daysToExpire > 365) {
            log.info("You can calculate average back to 1-365 days. You requested: {}", daysToExpire);
            return;
        }

        log.info("Expired measurement was deleted.");
        Long timestampXDaysBack = UtilService.getTimestampXDaysBackInSeconds(daysToExpire);
        List<Measurement> measurementsToDelete = measurementService.getMeasurementBeforeTimestamp(timestampXDaysBack);
        String deletedMeasurementsString = "";
        Map<Integer, City> idToCityMap = cityService.getIdToCityMap();
        for (Measurement measurement : measurementsToDelete){
            deletedMeasurementsString += measurement.toStringReadable(idToCityMap) + System.lineSeparator();
            measurementService.delete(measurement);
        }

        // TODO DELETE
        log.warn("Those expired measurement were deleted: " + System.lineSeparator() + deletedMeasurementsString);
    }
}
