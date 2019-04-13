package ppj.vana.projekt.data;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.bson.types.ObjectId;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import ppj.vana.projekt.serializer.ObjectIdSerializer;

import javax.persistence.Id;
import java.io.IOException;

@Document(collection = "meteorolog")
public class Measurement {

    @Id
    @JsonSerialize(using = ObjectIdSerializer.class)
    private ObjectId id;

    @Indexed
    private Integer cityID;

    private Long timeOfMeasurement;

    private Double temperature;

    private Integer humidity;

    private Integer pressure; // tlak

    private Long sunrise;

    private Long sunset;

    private Double wind; // rychlost vetru

    public Measurement(Integer cityID, Long timeOfMeasurement, Double temperature) {
        this.cityID = cityID;
        this.timeOfMeasurement = timeOfMeasurement;
        this.temperature = temperature;
    }

    public void setMultiple(Integer humidity, Integer pressure, Long sunrise, Long sunset, Double wind) {
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

    public Long getTimeOfMeasurement() {
        return timeOfMeasurement;
    }

    public void setTimeOfMeasurement(Long timeOfMeasurement) {
        this.timeOfMeasurement = timeOfMeasurement;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
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

    public Long getSunrise() {
        return sunrise;
    }

    public void setSunrise(Long sunrise) {
        this.sunrise = sunrise;
    }

    public Long getSunset() {
        return sunset;
    }

    public void setSunset(Long sunset) {
        this.sunset = sunset;
    }

    public Double getWind() {
        return wind;
    }

    public void setWind(Double wind) {
        this.wind = wind;
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "id=" + id +
                ", cityID=" + cityID +
                ", timeOfMeasurement=" + timeOfMeasurement +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", pressure=" + pressure +
                ", sunrise=" + sunrise +
                ", sunset=" + sunset +
                ", wind=" + wind +
                '}';
    }


}
