package ppj.vana.projekt.data;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "city")
public class City {

    @Id
    @Column(name = "name")
    private String name;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "country")
    private Country country;

    @Basic
    @Column(name = "openweathermapid")
    private Integer openWeatherMapID;


    public City() {
        this.country = new Country();
    }

    public City(String name, Country country) {
        this.name = name;
        this.country = country;
    }

    public City(String name, Country country, int id) {
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

    public int getOpenWeatherMapID() {
        return openWeatherMapID;
    }

    public void setOpenWeatherMapID(int openWeatherMapID) {
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
        return country.equals(city.country);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + country.hashCode();
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
