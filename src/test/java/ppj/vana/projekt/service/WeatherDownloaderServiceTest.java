package ppj.vana.projekt.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ppj.vana.projekt.Main;
import ppj.vana.projekt.data.Measurement;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Main.class)
@ActiveProfiles({"test"})
public class WeatherDownloaderServiceTest {

    @Autowired
    private WeatherDownloaderService weatherDownloaderService;

    @Test
    public void testDownload() {
        Measurement measurement1 = weatherDownloaderService.getWeatherByCityID(3067696); // 3067696 = cityID of Prague
        assertNull(measurement1.getId());
        assertNotNull(measurement1.getCityID());
        assertNotNull(measurement1.getTemperature());
        assertNotNull(measurement1.getHumidity());
        assertNotNull(measurement1.getPressure());
        assertNotNull(measurement1.getSunrise());
        assertNotNull(measurement1.getSunset());
        assertNotNull(measurement1.getWind());
        assertNotNull(measurement1.getTimeOfMeasurement());
    }


}
