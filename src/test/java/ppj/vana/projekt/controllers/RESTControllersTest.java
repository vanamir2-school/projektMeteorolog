package ppj.vana.projekt.controllers;

import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ppj.vana.projekt.Main;
import ppj.vana.projekt.data.Country;
import ppj.vana.projekt.server.ServerAPI;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

    private static final GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(new GsonBuilder().setLenient().create());
    private static ServerAPI serverAPI;
    @LocalServerPort
    private int port;

    @Before
    public void init() {
        String URL = "http://localhost:" + this.port;
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(gsonConverterFactory).build();
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

}
