package ppj.vana.projekt.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ppj.vana.projekt.Main;
import ppj.vana.projekt.model.Measurement;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Main.class)
@ActiveProfiles({"test"})
@TestPropertySource(locations = "classpath:app_test.properties")
public class WeatherDownloaderServiceTest {

    @Autowired
    private WeatherDownloaderService weatherDownloaderService;

    // test API call limit
    @Test
    public void testLimitandDownload() {
        for (int i = 0; i < weatherDownloaderService.getApilimit(); ++i)
            cheackMeasurementLoaded(weatherDownloaderService.getWeatherByCityID(3077929));
        Measurement measurement2 = weatherDownloaderService.getWeatherByCityID(3071961);
        assertNull(measurement2);
    }

    // check if model download was completely done
    private void cheackMeasurementLoaded(Measurement measurement1) {
        assertNotNull(measurement1.getId());
        assertNotNull(measurement1.getCityID());
        assertNotNull(measurement1.getTemperature());
        assertNotNull(measurement1.getHumidity());
        assertNotNull(measurement1.getPressure());
        assertNotNull(measurement1.getWind());
        assertNotNull(measurement1.getTimeOfMeasurement());
    }


}
