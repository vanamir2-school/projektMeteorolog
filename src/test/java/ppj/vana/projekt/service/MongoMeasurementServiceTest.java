package ppj.vana.projekt.service;

import org.bson.types.ObjectId;
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
import ppj.vana.projekt.model.Measurement;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Main.class)
@ActiveProfiles({"test"})
@TestPropertySource(locations = "classpath:app_test.properties")
public class MongoMeasurementServiceTest {

    private static final long ONE_DAY_SECONDS = 86400;
    private final Measurement measurement1 = new Measurement(new ObjectId(), 3077929, 1554370113855L, 25.0, 55, 56, 56L, 50L, 50.0);
    private final Measurement measurement2 = new Measurement(new ObjectId(), 3077925, 1554370113851L, 20.0, null, null, null, null, null);
    private final Measurement measurement3 = new Measurement(new ObjectId(), 3077929, 1554370113951L, 20.0, 10, 10, 10L, 10L, 10.0);
    // private final Measurement measurement2 = new Measurement(3077925, 124L, 20.0);
    // private final Measurement measurement3 = new Measurement(3077929, 999L, 28.0);

    @Autowired
    private MongoMeasurementService measurementService;
    private final Measurement measurement4 = new Measurement(new ObjectId(), 3077929, 1554370113850L, 20.0, 100, 100, 100L, 100L, 100.0);
    @Autowired
    private CityService cityService;
    @Autowired
    private CountryService countryService;

    @Before
    public void init() {
        measurementService.deleteAll();
    }

    @Test
    public void testCreateRetrieve() {
        assertEquals("Should be 0 retrieved fields.", 0, measurementService.count());
        measurementService.add(measurement1);
        measurementService.add(measurement2);
        measurementService.add(measurement3);
        assertEquals("Should be 3 retrieved fields.", 3, measurementService.count());
        measurementService.remove(measurement2);
        assertEquals("Should be 2 retrieved fields.", 2, measurementService.count());
        assertEquals("Should be equal", 20, measurementService.getByID(measurement3.getId()).getTemperature().intValue());

        List<Measurement> measurementList = measurementService.findAllRecordForCityID(3077929);
        assertEquals("Should be equal", 2, measurementList.size());
    }

    @Test
    public void testMapReduce() {
        measurementService.deleteAll();
        measurementService.add(measurement1);
        measurementService.add(measurement2);
        measurementService.add(measurement3);

        List<Measurement> measurementList = measurementService.findAllRecordForCityID(3077929);
        assertEquals("Should be equal", 2, measurementList.size());
        measurementList = measurementService.findAllRecordForCityID(3077925);
        assertEquals("Should be equal", 1, measurementList.size());

        Map<Integer, Integer> measurementCnt = measurementService.numOfRecordsUsingMapReduce();
        assertEquals("Should be equal", 2, measurementCnt.get(3077929).intValue());
        assertEquals("Should be equal", 1, measurementCnt.get(3077925).intValue());
    }

    @Test
    public void averageValuesForCityTest() {
        Country country = new Country("Česká republika");
        countryService.save(country);
        cityService.save(new City("Praha", country, 3077929)); //     public City(String name, Country country, Integer id) {
        int days = 10;
        Long timestamp = new Date().getTime()/1000 - ONE_DAY_SECONDS * days; // 10 dnu zpatky - tyto udaje chceme
        Long timestampPlusOneDay = timestamp + ONE_DAY_SECONDS; // toto je OK, ty chceme v mereni, stalo se to o den pozdeji
        Long timestampMinusOneDay = timestamp - ONE_DAY_SECONDS; // toto je stary udaj, ten nechceme

        measurement1.setTimeOfMeasurement(timestampPlusOneDay); // ma se zapocitat - mesto i cas OK
        measurement2.setTimeOfMeasurement(timestampPlusOneDay);  // nema se zapocitat - mesto nesedi
        measurement3.setTimeOfMeasurement(timestampPlusOneDay);  // ma se zapocitat - mesto i cas OK
        measurement4.setTimeOfMeasurement(timestampMinusOneDay); // nema se zapocitat - cas nesedi
        measurementService.add(measurement1);
        measurementService.add(measurement2);
        measurementService.add(measurement3);
        measurementService.add(measurement4);

        String incOutput = String.format("Averaged values for city %s in last %d days:", "Praha", days) + System.lineSeparator();
        incOutput += String.format("Temperature: %s", 22.5) + System.lineSeparator();
        incOutput += String.format("Humidity: %s", 32.5) + System.lineSeparator();
        incOutput += String.format("Pressure: %s", 33.0) + System.lineSeparator();
        incOutput += String.format("Wind speed: %s", 30.0) + System.lineSeparator();

        String output = measurementService.averageValuesForCity("Praha", days);

        assertEquals(incOutput, output);
    }

}
