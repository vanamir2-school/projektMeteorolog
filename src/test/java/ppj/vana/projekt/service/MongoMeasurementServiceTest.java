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
import ppj.vana.projekt.data.Measurement;
import ppj.vana.projekt.repositories.MeasurementRepository;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Main.class)
@ActiveProfiles({"test"})
@TestPropertySource(locations = "classpath:app_test.properties")
public class MongoMeasurementServiceTest {

    private final Measurement measurement1 = new Measurement(new ObjectId(), 3077929, 20L, 20.0, null, null, null, null, null);
    private final Measurement measurement2 = new Measurement(new ObjectId(), 3077925, 20L, 20.0, null, null, null, null, null);
    private final Measurement measurement3 = new Measurement(new ObjectId(), 3077929, 20L, 20.0, null, null, null, null, null);
    // private final Measurement measurement2 = new Measurement(3077925, 124L, 20.0);
    // private final Measurement measurement3 = new Measurement(3077929, 999L, 28.0);

    @Autowired
    private MongoMeasurementService measurementService;

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

}
