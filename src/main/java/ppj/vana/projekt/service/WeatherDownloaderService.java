package ppj.vana.projekt.service;

import org.apache.commons.io.IOUtils;
//import org.json.JSONException;
//import org.json.JSONObject;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import ppj.vana.projekt.Main;
import ppj.vana.projekt.data.Measurement;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;


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

    private Date queryStartDate = null;

    private int queryCounter = 0;

    private SimpleDateFormat simpleDateFormat = null;

    @NotNull
    @Value("${app.appid}")
    private String appid;
    @NotNull
    @Value("${app.apilimit}")
    private Integer apilimit;

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
            Double temperature = (Double) json.getJSONObject("main").get("temp");
            Double windSpeed = (Double) json.getJSONObject("wind").get("speed");
            Integer timeOfMeasurement = (Integer) json.get("dt");
            Integer humidity = (Integer) json.getJSONObject("main").get("humidity");
            Integer pressure = (Integer) json.getJSONObject("main").get("pressure");
            Integer sunrise = (Integer) json.getJSONObject("sys").get("sunrise");
            Integer sunset = (Integer) json.getJSONObject("sys").get("sunset");
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

    /**
     * Converts UNIX time to String with pattern "dd-MM-yyyy HH:mm:ss".
     */
    public String timestampToString(Long value) {
        if (simpleDateFormat == null)
            simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return simpleDateFormat.format(value * 1000L);
    }

    @PostConstruct
    private void postConstructLogg() {
        logger.info("Appid: " + getAppid());
    }

}
