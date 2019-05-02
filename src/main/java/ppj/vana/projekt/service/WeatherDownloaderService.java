package ppj.vana.projekt.service;

import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import ppj.vana.projekt.model.City;
import ppj.vana.projekt.model.Measurement;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;

/**
 * WeatherDownloaderService provides data download from https://openweathermap.org via their API.
 * Configuration is received from .properties files
 */
@Service
public class WeatherDownloaderService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherDownloaderService.class);
    private static final String UNITS = "metric";

    private final MeasurementService measurementService;
    private Date queryStartDate = null;
    private int queryCounter = 0;

    @NotNull
    @Value("${app.apiUrl}")
    private String currentWeatherApiUrl;
    @NotNull
    @Value("${app.appid}")
    private String appid;
    @NotNull
    @Value("${app.apilimit}")
    private Integer apilimit;

    public WeatherDownloaderService(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    public Integer getApilimit() {
        return apilimit;
    }

    public String getAppid() {
        return appid;
    }

    /**
     * Check if Query limit does not exceeded.
     */
    private boolean limitExceeded() {
        if (queryStartDate == null)
            queryStartDate = new Date();
        // if queryStartDate is smaller by more then 60 000ms - one minute is gone and we can reset it
        Long timeDiff = new Date().getTime() - queryStartDate.getTime();
        if (timeDiff > 60000L) {
            queryStartDate = new Date();
            queryCounter = 0;
            return false;
        }
        ++queryCounter;
        logger.debug("This is request number " + queryCounter + " in the last minute on OpenWeatherAPI.");
        return queryCounter > apilimit;
    }

    public void saveWeatherToDatabase(List<City> cityList) {
        cityList.forEach(this::saveWeatherToDatabase);
    }

    public void saveWeatherToDatabase(City city) {
        if (city == null || city.getOpenWeatherMapID() == null)
            throw new NullPointerException("Entity City must be initilized with opeanWeatherMap cityID.");
        Measurement measurement = this.getWeatherByCityID(city.getOpenWeatherMapID());
        measurementService.add(measurement);
        logger.info("Measurement INSERTION: " + measurement.toString());
    }

    public Measurement getWeatherByCityID(int cityID) {
        if (limitExceeded()) {
            logger.error("Number of request to weather API was exceeded. There was more then " + apilimit + " request in a minute.");
            return null;
        }

        // stazeni dat do JSON formatu
        String urlString = currentWeatherApiUrl + String.format("?id=%d&APPID=%s&units=%s", cityID, appid, UNITS);
        String jsonData = null;
        Measurement measurement = null;
        try {
            jsonData = IOUtils.toString(new URL(urlString), "utf-8");

            JSONObject json = new JSONObject(jsonData);

            // inicializace Measurement
            Double temperature = json.getJSONObject("main").getDouble("temp");
            Double windSpeed = json.getJSONObject("wind").getDouble("speed");
            Integer timeOfMeasurement = json.getInt("dt");
            Integer humidity = json.getJSONObject("main").getInt("humidity");
            Integer pressure = json.getJSONObject("main").getInt("pressure");
            measurement = new Measurement(new ObjectId(), cityID, timeOfMeasurement.longValue(), temperature, humidity, pressure, windSpeed);

        } catch (IOException | JSONException e) {
            logger.error(e.getMessage());
        }

        return measurement;
    }

    @PostConstruct
    private void postConstructLogg() {
        logger.info("Appid: " + getAppid());
    }

}
