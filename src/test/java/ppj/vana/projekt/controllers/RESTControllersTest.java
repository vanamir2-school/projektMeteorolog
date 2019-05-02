package ppj.vana.projekt.controllers;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ppj.vana.projekt.Main;
import ppj.vana.projekt.controller.ServerAPI;
import ppj.vana.projekt.model.City;
import ppj.vana.projekt.model.Country;
import ppj.vana.projekt.model.Measurement;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


// https://www.baeldung.com/spring-boot-testing
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Main.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"test"})
@TestPropertySource(locations = "classpath:app_test.properties")
public class RESTControllersTest {

    private static ServerAPI serverAPI;

    @LocalServerPort
    private int port;

    @Before
    public void init() {
        String URL = "http://localhost:" + this.port;

        // GsonConverterFactory  does not work
        // MUST BE JacksonConverterFactory, OTHERWISE CUSTOM SERIALIZATION/DESERIALIZATION DOES NOT WORK
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(JacksonConverterFactory.create()).build();
        serverAPI = retrofit.create(ServerAPI.class);

    }

    @Test
    public void CountryOperations() throws IOException {
        // INSERT COUNTRY
        String FINSKO = "Finsko";
        List<Country> countryArrayList = new ArrayList<>();
        countryArrayList.add(new Country(FINSKO));
        countryArrayList.add(new Country("Irsko"));
        countryArrayList.add(new Country("Chorvatsko"));
        assertEquals("Test return CODE - should be HTTP OK - 200", serverAPI.addCountry(countryArrayList.get(0)).execute().raw().code(), HttpStatus.OK.value());
        assertEquals("Test return CODE - should be HTTP OK - 200", serverAPI.addCountry(countryArrayList.get(1)).execute().raw().code(), HttpStatus.OK.value());
        assertEquals("Test return CODE - should be HTTP OK - 200", serverAPI.addCountry(countryArrayList.get(2)).execute().raw().code(), HttpStatus.OK.value());

        // GET ALL
        List<Country> receivedCountryArrayList = serverAPI.getCountries().execute().body();
        assert receivedCountryArrayList != null;
        assertTrue("Test returned values from getCountries()", countryArrayList.size() == receivedCountryArrayList.size() && countryArrayList.containsAll(receivedCountryArrayList));

        //GET SPECIFIC COUNTRIES
        Response<Country> countryResponse = serverAPI.getCountryByID(FINSKO).execute();
        assertEquals(countryResponse.code(), HttpStatus.OK.value());
        assert countryResponse.body() != null;
        assertEquals(countryResponse.body().getName(), FINSKO);
        assertEquals(countryResponse.body(), new Country(FINSKO));

        countryResponse = serverAPI.getCountryByID("Nonsence").execute();
        assertEquals(countryResponse.code(), HttpStatus.NOT_FOUND.value());
        assertNull(countryResponse.body());

        // DELETE COUNTRY
        serverAPI.deleteCountry(FINSKO).execute();
        receivedCountryArrayList = serverAPI.getCountries().execute().body();
        countryArrayList.remove(new Country(FINSKO));
        assert receivedCountryArrayList != null;
        assertTrue("Test returned values from getCountries()", countryArrayList.size() == receivedCountryArrayList.size() && countryArrayList.containsAll(receivedCountryArrayList));
    }

    @Test
    public void CityOperations() throws IOException {

        // V DB musi byt zeme pod kterou budeme pridata zeme
        String PANDARIA = "Pandaria";
        Country country = new Country(PANDARIA);
        assertEquals("Test return CODE - should be HTTP OK - 200", serverAPI.addCountry(country).execute().raw().code(), HttpStatus.OK.value());

        // INSERT CITY
        City city1 = new City("Krakov", country, 2564);
        City city2 = new City("Vidlákov", country, 2564);// ublic City(String name, Country country, Integer id) {
        List<City> cityList = new ArrayList<>();
        cityList.add(city1);
        cityList.add(city2);
        assertEquals("Test return CODE - should be HTTP OK - 200", serverAPI.addCity(cityList.get(0)).execute().raw().code(), HttpStatus.OK.value());
        assertEquals("Test return CODE - should be HTTP OK - 200", serverAPI.addCity(cityList.get(1)).execute().raw().code(), HttpStatus.OK.value());

        // GET ALL
        List<City> cityListReceived = serverAPI.getCities().execute().body();
        assert cityListReceived != null;
        assertTrue("Test returned values from getCountries()", cityList.size() == cityListReceived.size() && cityList.containsAll(cityListReceived));

        //GET SPECIFIC CITY
        Response<City> cityResponse = serverAPI.getCityByID("Krakov").execute();
        assertEquals(cityResponse.code(), HttpStatus.OK.value());
        assert cityResponse.body() != null;
        assertEquals(cityResponse.body().getName(), "Krakov");
        assertEquals(cityResponse.body(), new City("Krakov", new Country(PANDARIA), 2564));

        // DELETE CITY
        serverAPI.deleteCity("Krakov").execute();
        cityListReceived = serverAPI.getCities().execute().body();
        cityList.remove(new City("Krakov", new Country(PANDARIA), 2564));
        assert cityListReceived != null;
        assertTrue("Test returned values from getCountries()", cityList.size() == cityListReceived.size() && cityList.containsAll(cityListReceived));


        cityResponse = serverAPI.getCityByID("Krakov").execute();
        assertEquals(cityResponse.code(), HttpStatus.NOT_FOUND.value());
        assertNull(cityResponse.body());

        // UPDATE CITY
        assertEquals("Test return CODE - should be HTTP OK - 200", serverAPI.updateCity("Vidlákov", new City("Vidlákov", country, 42)).execute().raw().code(), HttpStatus.OK.value());
        cityResponse = serverAPI.getCityByID("Vidlákov").execute();
        assertEquals(cityResponse.code(), HttpStatus.OK.value());
        assert cityResponse.body() != null;
        assertEquals(cityResponse.body().getName(), "Vidlákov");
        assertEquals(cityResponse.body(), new City("Vidlákov", new Country(PANDARIA), 42));

        // return countries to previous state
        serverAPI.deleteCountry("Pandaria").execute();

    }

    @Test
    public void MeasurementOperations() throws IOException {
        final Measurement measurement1 = new Measurement(new ObjectId(), 3077929, 20L, 20.0, 40, 40, 40.0);
        final Measurement measurement2 = new Measurement(new ObjectId(), 3077925, 20L, 20.0, null, null, null);
        final Measurement measurement3 = new Measurement(new ObjectId(), 3077929, 20L, 20.0, null, null, null);

        // ADD
        assertEquals(serverAPI.addMeasurement(measurement1).execute().raw().code(), HttpStatus.OK.value());
        assertEquals(serverAPI.addMeasurement(measurement2).execute().raw().code(), HttpStatus.OK.value());
        assertEquals(serverAPI.addMeasurement(measurement3).execute().raw().code(), HttpStatus.OK.value());

        // UPDATE
        measurement1.setCityID(42);
        assertEquals("Test return CODE - should be HTTP OK - 200", serverAPI.updateMeasurement(measurement1.getId().toHexString(), measurement1).execute().raw().code(), HttpStatus.OK.value());

        //GET
        Response<Measurement> measurementResponse = serverAPI.getMeasurementByID(measurement1.getId().toHexString()).execute();
        assertEquals(measurementResponse.code(), HttpStatus.OK.value());
        assert measurementResponse.body() != null;
        assertEquals(measurementResponse.body(), measurement1);
        assertEquals(measurementResponse.body().getCityID().longValue(), 42);

        // DELETE CITY
        serverAPI.deleteMeasurement(measurement1.getId().toHexString()).execute();

        // GET  DELETED - ERROR
        measurementResponse = serverAPI.getMeasurementByID(measurement1.getId().toHexString()).execute();
        assertEquals(measurementResponse.code(), HttpStatus.NOT_FOUND.value());
        assertNull(measurementResponse.body());

    }

}
