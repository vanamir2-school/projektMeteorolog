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
import ppj.vana.projekt.model.MesHistory;

import java.sql.Timestamp;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({"test"})
@SpringBootTest(classes = Main.class)
@TestPropertySource(locations = "classpath:app_test.properties")
public class MesHistoryServiceTest {

    private final MesHistory mesHistory1 = new MesHistory(Timestamp.valueOf("2018-11-15 15:30:14.332"));
    private final MesHistory mesHistory2 = new MesHistory(Timestamp.valueOf("2017-11-15 15:35:14.332"));
    private final MesHistory mesHistory3 = new MesHistory(Timestamp.valueOf("2016-11-17 15:22:14.332"));

    @Autowired
    MesHistoryService service;

    @Before
    public void init(){
        service.deleteAll();
    }

    @Test
    public void completeTest() {
        service.add(mesHistory1);
        assertEquals(service.count().longValue(), 1L);
        service.add(mesHistory2);
        assertEquals(service.count().longValue(), 2L);
        service.add(mesHistory3);
        assertEquals(service.getLatest().getTimestamp(), Timestamp.valueOf("2018-11-15 15:30:14.332"));
        assertEquals(service.count().longValue(), 3L);
    }

}
