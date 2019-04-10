package ppj.vana.projekt.data;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document(collection = "meteorolog")
public class Measurement {

    @Id
    private ObjectId id;

    @Indexed
    private Integer cityID;

    private Integer timeOfMeasurement;

    private Integer temperature;

    private Integer humidity;

    private Integer pressure; // tlak

    private Integer sunrise;

    private Integer sunset;

    private int wind; // rychlost vetru

    public Measurement(Integer cityID, Integer timeOfMeasurement, Integer temperature, Integer humidity, Integer pressure, Integer sunrise, Integer sunset, int wind) {
        this.cityID = cityID;
        this.timeOfMeasurement = timeOfMeasurement;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.wind = wind;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Integer getCityID() {
        return cityID;
    }

    public void setCityID(Integer cityID) {
        this.cityID = cityID;
    }

    public Integer getTimeOfMeasurement() {
        return timeOfMeasurement;
    }

    public void setTimeOfMeasurement(Integer timeOfMeasurement) {
        this.timeOfMeasurement = timeOfMeasurement;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public Integer getSunrise() {
        return sunrise;
    }

    public void setSunrise(Integer sunrise) {
        this.sunrise = sunrise;
    }

    public Integer getSunset() {
        return sunset;
    }

    public void setSunset(Integer sunset) {
        this.sunset = sunset;
    }

    public int getWind() {
        return wind;
    }

    public void setWind(int wind) {
        this.wind = wind;
    }
}
