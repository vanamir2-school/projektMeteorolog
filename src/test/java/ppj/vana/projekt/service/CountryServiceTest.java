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
import ppj.vana.projekt.data.Country;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Main.class)
@ActiveProfiles({"test"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CountryServiceTest {


    @Autowired
    private CountryService countryService;

    private Country country1 = new Country("Austrálie");
    private Country country2 = new Country("Estonsko");
    private Country country3 = new Country("Norsko");
    private Country country4 = new Country("Brazílie");
    private Country country5 = new Country("Česká republika");

    @Before
    public void init() {
        countryService.deleteAll();
    }


    @Test
    public void testCreateRetrieve() {
        countryService.save(country1);
        List<Country> countries = countryService.getAll();
        System.out.println(countries);

        assertEquals("One field should have been created and retrieved", 1, countries.size());
        assertEquals("Inserted field should match retrieved", country1, countries.get(0));

        countryService.save(country2);
        countryService.save(country3);
        countryService.save(country4);

        countries = countryService.getAll();
        assertEquals("Should be four retrieved fields.", 4, countries.size());
    }

    @Test
    public void testExists() {
        countryService.save(country2);
        countryService.save(country3);
        countryService.save(country4);
        countryService.save(country5);

        assertTrue("Field should exist.", countryService.exists(country2.getName()));
        assertFalse("Field should not exist.", countryService.exists("xkjhsfjlsjf"));
        assertEquals("Česká republika", countryService.getById("Česká republika").get().getName());
    }
}
