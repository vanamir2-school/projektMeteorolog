package ppj.vana.projekt.service;

import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;

class TestUtils {

    static <Entity, Key> void serviceTest(List<Entity> entityList, IService<Entity, Key> service, Key key1, Key key2) {
        if (entityList.size() != 3)
            throw new IllegalStateException("There must be list of length 3.");

        service.deleteAll();
        service.add(entityList.get(0));
        assertEquals(service.get(key1), entityList.get(0));
        assertEquals(service.count(), 1);
        service.add(entityList.get(1));
        assertEquals(service.get(key2), entityList.get(1));
        assertEquals(service.count(), 2);

        service.add(entityList.get(2));
        assertEquals(service.count(), 3);
        service.delete(entityList.get(2));
        assertEquals(service.get(key1), entityList.get(0));
        assertEquals(service.get(key2), entityList.get(1));
        assertEquals(service.count(), 2);
        assertTrue(service.exists(entityList.get(0)));
        assertTrue(service.exists(entityList.get(1)));

        service.deleteAll();
        assertEquals(service.count(), 0);
        assertNull(service.get(key1));
        assertFalse(service.exists(entityList.get(0)));
    }
}
