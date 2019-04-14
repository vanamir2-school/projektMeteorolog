package ppj.vana.projekt.data;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import ppj.vana.projekt.serializer.ObjectIdDeserializer;
import ppj.vana.projekt.serializer.ObjectIdSerializer;

import javax.persistence.Id;

@Document(collection = "meteorolog")
public class Measurement {

    @Id
    @Expose
    //@JsonDeserialize(using = ObjectIdDeserializer.class)
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

    public Measurement() {
    }

    public Measurement(ObjectId id, Integer cityID, Long timeOfMeasurement, Double temperature, Integer humidity, Integer pressure, Long sunrise, Long sunset, Double wind) {
        this.id = id;
        this.cityID = cityID;
        this.timeOfMeasurement = timeOfMeasurement;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.wind = wind;
    }

  /*  public Measurement(Integer cityID, Long timeOfMeasurement, Double temperature) {
        this.cityID = cityID;
        this.timeOfMeasurement = timeOfMeasurement;
        this.temperature = temperature;
    }*/

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Measurement that = (Measurement) o;

        if (!id.equals(that.id)) return false;
        if (cityID != null ? !cityID.equals(that.cityID) : that.cityID != null) return false;
        if (timeOfMeasurement != null ? !timeOfMeasurement.equals(that.timeOfMeasurement) : that.timeOfMeasurement != null)
            return false;
        if (temperature != null ? !temperature.equals(that.temperature) : that.temperature != null) return false;
        if (humidity != null ? !humidity.equals(that.humidity) : that.humidity != null) return false;
        if (pressure != null ? !pressure.equals(that.pressure) : that.pressure != null) return false;
        if (sunrise != null ? !sunrise.equals(that.sunrise) : that.sunrise != null) return false;
        if (sunset != null ? !sunset.equals(that.sunset) : that.sunset != null) return false;
        return wind != null ? wind.equals(that.wind) : that.wind == null;

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (cityID != null ? cityID.hashCode() : 0);
        result = 31 * result + (timeOfMeasurement != null ? timeOfMeasurement.hashCode() : 0);
        result = 31 * result + (temperature != null ? temperature.hashCode() : 0);
        result = 31 * result + (humidity != null ? humidity.hashCode() : 0);
        result = 31 * result + (pressure != null ? pressure.hashCode() : 0);
        result = 31 * result + (sunrise != null ? sunrise.hashCode() : 0);
        result = 31 * result + (sunset != null ? sunset.hashCode() : 0);
        result = 31 * result + (wind != null ? wind.hashCode() : 0);
        return result;
    }
}
