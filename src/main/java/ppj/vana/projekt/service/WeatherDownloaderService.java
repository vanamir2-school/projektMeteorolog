package ppj.vana.projekt.service;

import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import ppj.vana.projekt.Main;
import ppj.vana.projekt.model.City;
import ppj.vana.projekt.model.Measurement;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//import org.json.JSONException;
//import org.json.JSONObject;


/**
 * WeatherDownloaderService je třída na stažení počasí ze stránky https://openweathermap.org přes poskytované API.
 * Umožňuje konfiguraci dle požadavků v zadání PPJ projektu - TODO
 * Konfigurace se natahuje z .properties souborů
 */
@Service
public class WeatherDownloaderService {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final String CURRENT_WEATHER_API_URL = "http://api.openweathermap.org/data/2.5/weather";
    private static final String UNITS = "metric";
    private static SimpleDateFormat simpleDateFormat = null;
    @Autowired
    private MongoMeasurementService measurementService;
    private Date queryStartDate = null;
    private int queryCounter = 0;
    @NotNull
    @Value("${app.appid}")
    private String appid;
    @NotNull
    @Value("${app.apilimit}")
    private Integer apilimit;

    /**
     * Converts UNIX time to String with pattern "dd-MM-yyyy HH:mm:ss".
     */
    public static String timestampToStringSeconds(Long value) {
        if (simpleDateFormat == null)
            simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return simpleDateFormat.format(value * 1000L);
    }

    public static String timestampToStringMilliSeconds(Long value) {
        if (simpleDateFormat == null)
            simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return simpleDateFormat.format(value);
    }

    public Integer getApilimit() {
        return apilimit;
    }

    public String getAppid() {
        return appid;
    }

    /**
     * Zkontroluje, zda nebyl překročen nastavený limit v počtu dotazů za minutu.
     */
    private boolean limitExceeded() {
        if (apilimit == null)
            throw new NullPointerException("Applimit konfigurovatelný v souboru: aplication.properties nebyl nalezen");
        if (queryStartDate == null)
            queryStartDate = new Date();
        // pokud je queryStartDate mensi o vice jak 60 000ms - uz je pryc minuta a muzeme ho vynulovat
        Long casovyRozdil = new Date().getTime() - queryStartDate.getTime();
        if (casovyRozdil > 60000L) {
            queryStartDate = new Date();
            queryCounter = 0;
            return false;
        }
        ++queryCounter;
        logger.info("Za poslední minutu je toto " + queryCounter + ". dotaz na OpenWeatherAPI.");
        if (queryCounter > apilimit)
            return true;
        return false;
    }

    public void loadWeatherToDatabase(List<City> cityList) {
        cityList.forEach((city) -> loadWeatherToDatabase(city));
    }

    public void loadWeatherToDatabase(City city) {
        if (city == null || city.getOpenWeatherMapID() == null)
            throw new NullPointerException("Entity City must be initilized with opeanWeatherMap cityID.");
        Measurement measurement = this.getWeatherByCityID(city.getOpenWeatherMapID());
        measurementService.add(measurement);
        logger.info("Measurement INSERTION: " + measurement.toString());
    }

    public Measurement getWeatherByCityID(int cityID) {
        if (limitExceeded()) {
            logger.error("Byl překročen limit v počtu dorazů. Bylo voláno více než " + apilimit + " dotazů za minutu");
            return null;
        }

        // stazeni dat do JSON formatu
        String urlString = CURRENT_WEATHER_API_URL + String.format("?id=%d&APPID=%s&units=%s", cityID, appid, UNITS);
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
            Integer sunrise = json.getJSONObject("sys").getInt("sunrise");
            Integer sunset = json.getJSONObject("sys").getInt("sunset");
            measurement = new Measurement(new ObjectId(), cityID, timeOfMeasurement.longValue(), temperature, humidity, pressure, sunrise.longValue(), sunset.longValue(), windSpeed);
            //measurement.setMultiple();

            // TODO - dodelat LOGOVANI
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return measurement;
    }

    @PostConstruct
    private void postConstructLogg() {
        logger.info("Appid: " + getAppid());
    }

}
