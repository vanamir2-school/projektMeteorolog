package ppj.vana.projekt.model;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "city")
public class City {

    @Id
    @NotNull
    @Column(name = "name")
    @Length(max = 50)
    private String name;

    @ManyToOne
    @NotNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "country")
    private Country country;

    @Basic
    @Column(name = "openweathermapid")
    @Range(min = 0)
    private Integer openWeatherMapID;


    public City() {
        this.country = new Country();
    }

    public City(String name, Country country) {
        this.name = name;
        this.country = country;
    }

    public City(String name, Country country, Integer id) {
        this.openWeatherMapID = id;
        this.name = name;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOpenWeatherMapID() {
        return openWeatherMapID;
    }

    public void setOpenWeatherMapID(Integer openWeatherMapID) {
        this.openWeatherMapID = openWeatherMapID;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        if (!name.equals(city.name)) return false;
        return country != null ? country.equals(city.country) : city.country == null;

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", openWeatherMapID=" + openWeatherMapID +
                ", country=" + country +
                '}';
    }
}
