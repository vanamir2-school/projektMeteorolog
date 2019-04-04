package ppj.vana.projekt.service;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ppj.vana.projekt.Main;
import ppj.vana.projekt.data.City;
import ppj.vana.projekt.data.Country;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Main.class)
@ActiveProfiles({"test"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CityServiceTest {


    @Autowired
    private CityService cityService;

    @Autowired
    private CountryService countryService;

    private Country country1 = new Country("Austrálie");
    private final Country country2 = new Country("Estonsko");
    private final Country country3 = new Country("Norsko");
    private final Country country4 = new Country("Brazílie");
    private final Country country5 = new Country("Česká republika");
    private final City city1 = new City("Sloup v Čechách", country5);
    private final City city2 = new City("Janov", country5);
    private final City city3 = new City("Ostrava", country5);
    private final City city4 = new City("Nový Bor", country5);

    @Before
    public void init() {
        countryService.deleteAll();
        cityService.deleteAll();
        countryService.save(country2);
        countryService.save(country3);
        countryService.save(country4);
        countryService.save(country5);
    }


    @Test
    public void testCreateRetrieve() {
        cityService.save(city1);

        List<City> cities = cityService.getAll();

        System.out.println(cities);

        assertEquals("One field should have been created and retrieved", 1, cities.size());
        assertEquals("Inserted field should match retrieved", city1, cities.get(0));

        cityService.save(city2);
        cityService.save(city3);
        cityService.save(city4);

        cities = cityService.getAll();
        assertEquals("Should be four retrieved fields.", 4, cities.size());
    }

    @Test
    public void testExists() {
        cityService.save(city2);
        cityService.save(city3);
        cityService.save(city4);

        assertTrue("Entity should exist.", cityService.exists(city2.getName()));
        assertFalse("Entity should not exist.", cityService.exists("xkjhsfjlsjf"));
        assertEquals("Česká republika", cityService.getById("Ostrava").get().getCountry().getName());
    }
}
