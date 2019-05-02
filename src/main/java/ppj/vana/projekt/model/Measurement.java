package ppj.vana.projekt.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.Expose;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import ppj.vana.projekt.model.serialization.ObjectIdSerializer;
import ppj.vana.projekt.service.CityService;
import ppj.vana.projekt.service.UtilService;

import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

@Document(collection = "meteorolog")
public class Measurement {

    @Id
    @Expose
    @JsonSerialize(using = ObjectIdSerializer.class)
    private ObjectId id;

    @Indexed
    private Integer cityID;

    // MongoDB stores all Date values in UTC (which is 2 hours behind SELC - Prague timezone]
    // All dates in MongoDB are shifted 2 hours backwards
    // https://docs.mongodb.com/v3.2/tutorial/model-time-data/
    // https://www.compose.com/articles/understanding-dates-in-compose-mongodb/
    @Indexed(expireAfterSeconds = 0)
    private Date ttl;

    private Long timeOfMeasurement;

    private Double temperature;
    private Integer humidity;
    private Integer pressure;
    private Double wind; // wind speed in m/s

    public Measurement() {
    }

    public Measurement(ObjectId id, Integer cityID, Long timeOfMeasurement, Double temperature, Integer humidity, Integer pressure, Double wind) {
        this.id = id;
        this.cityID = cityID;
        this.timeOfMeasurement = timeOfMeasurement;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.wind = wind;
    }

    public Date getTtl() {
        return ttl;
    }

    public void setTtl(Date ttl) {
        this.ttl = ttl;
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
                ", wind=" + wind +
                '}';
    }

    public String timeOfMeasurementReadable() {
        return UtilService.timestampToStringSeconds(timeOfMeasurement);
    }

    public String cityName() {
        return CityService.getCityById(cityID);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Measurement that = (Measurement) o;

        if (!id.equals(that.id)) return false;
        if (!Objects.equals(cityID, that.cityID)) return false;
        if (!Objects.equals(timeOfMeasurement, that.timeOfMeasurement))
            return false;
        if (!Objects.equals(temperature, that.temperature)) return false;
        if (!Objects.equals(humidity, that.humidity)) return false;
        if (!Objects.equals(pressure, that.pressure)) return false;
        return Objects.equals(wind, that.wind);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (cityID != null ? cityID.hashCode() : 0);
        result = 31 * result + (timeOfMeasurement != null ? timeOfMeasurement.hashCode() : 0);
        result = 31 * result + (temperature != null ? temperature.hashCode() : 0);
        result = 31 * result + (humidity != null ? humidity.hashCode() : 0);
        result = 31 * result + (pressure != null ? pressure.hashCode() : 0);
        result = 31 * result + (wind != null ? wind.hashCode() : 0);
        return result;
    }
}
