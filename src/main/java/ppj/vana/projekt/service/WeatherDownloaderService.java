package ppj.vana.projekt.service;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ppj.vana.projekt.data.Measurement;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;


/**
 * WeatherDownloaderService je třída na stažení počasí ze stránky https://openweathermap.org přes poskytované API.
 * Umožňuje konfiguraci dle požadavků v zadání PPJ projektu - TODO
 * Konfigurace se natahuje z .properties souborů
 */
@Service
public class WeatherDownloaderService {

    private static final String CURRENT_WEATHER_API_URL = "http://api.openweathermap.org/data/2.5/weather";

    private static final String UNITS = "metric";

    private SimpleDateFormat simpleDateFormat = null;

    @NotNull
    @Value("${app.appid}")
    private String appid;

    public String getAppid() {
        return appid;
    }


    public Measurement getWeatherByCityID(int cityID) {
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
            measurement = new Measurement(cityID, timeOfMeasurement.longValue(), temperature);
            measurement.setMultiple(humidity, pressure, sunrise.longValue(), sunset.longValue(), windSpeed);

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

}
