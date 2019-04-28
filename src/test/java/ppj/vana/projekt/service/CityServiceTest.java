package ppj.vana.projekt.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ppj.vana.projekt.Main;
import ppj.vana.projekt.model.City;
import ppj.vana.projekt.model.Country;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Main.class)
@ActiveProfiles({"test"})
@TestPropertySource(locations = "classpath:app_test.properties")
public class CityServiceTest {

    private final List<Country> countryList = new ArrayList<Country>(
            Arrays.asList(
                    new Country("Austrálie"),
                    new Country("Estonsko"),
                    new Country("Norsko"),
                    new Country("Brazílie"),
                    new Country("Česká republika"),
                    new Country("Kanál"),
                    new Country("Kalimdor"),
                    new Country("Estonsko"),
                    new Country("Northrend")));
    private final Country country5 = new Country("Česká republika");
    private final Country country6 = new Country("Kanál");
    private final Country country7 = new Country("Kalimdor");
    private final Country country8 = new Country("Northrend");
    private final City city1 = new City("Sloup v Čechách", country5);
    private final City city2 = new City("Janov", country5);
    private final City city3 = new City("Ostrava", country5);
    private final City city4 = new City("Nový Bor", country5);
    private final City city5 = new City("Krysoň", country6);
    private final City city6 = new City("AlianceHood", country7);
    private final City city7 = new City("HordePub", country8);
    @Autowired
    private CityService cityService;
    @Autowired
    private CountryService countryService;

    @Before
    public void init() {
        countryService.deleteAll();
        cityService.deleteAll();
        countryService.saveList(countryList);
    }

    @Test
    public void testCreateRetrieve() {
        cityService.add(city1);

        List<City> cities = cityService.getAll();

        System.out.println(cities);

        assertEquals("One field should have been created and retrieved", 1, cities.size());
        assertEquals("Inserted field should match retrieved", city1, cities.get(0));

        cityService.add(city2);
        cityService.add(city3);
        cityService.add(city4);

        cities = cityService.getAll();
        assertEquals("Should be four retrieved fields.", 4, cities.size());
    }

    @Test
    public void testExists() {
        cityService.add(city2);
        cityService.add(city3);
        cityService.add(city4);

        assertTrue("Entity should exist.", cityService.exists(city2));
        assertFalse("Entity should not exist.", cityService.existsById("xkjhsfjlsjf"));
        assertEquals("Česká republika", cityService.get("Ostrava").getCountry().getName());
    }

    @Test
    public void testSave() {
        cityService.deleteAll();
        cityService.add(city1);
        cityService.add(city2);
        cityService.add(city3);
        cityService.add(city4);
        cityService.add(city5);
        cityService.add(city6);
        assertTrue("Entity should exist.", cityService.exists(city1));
        assertTrue("Entity should exist.", cityService.exists(city2));
        assertTrue("Entity should exist.", cityService.exists(city3));
        assertTrue("Entity should exist.", cityService.exists(city4));
        assertTrue("Entity should exist.", cityService.exists(city5));
        assertTrue("Entity should exist.", cityService.exists(city6));
        assertFalse("Entity should NOT exist.", cityService.exists(city7));

        cityService.delete(city4);
        List<City> cities1 = cityService.getAll();
        assertEquals("Should be 5 cities.", 5, cities1.size());

    }

    @Test
    public void basicOperationTest() {
        List<City> citiesList = new ArrayList<>(Arrays.asList(city1, city2, city3));
        TestUtils.serviceTest(citiesList, cityService, city1.getName(), city2.getName());
    }
}
