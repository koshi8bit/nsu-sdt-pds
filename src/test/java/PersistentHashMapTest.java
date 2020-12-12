import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.*;

public class PersistentHashMapTest {
    PersistentHashMap<String, Integer> phm = new PersistentHashMap<>();

    @After
    public void clear() {
        phm = new PersistentHashMap<>(); //TODO: Исправить
    }

    private void addABC() {
        phm.put("A", 1);
        phm.put("B", 2);
        phm.put("C", 3);
    }

    @Test
    public void testPersistentHashMapPutAndGet() {
        addABC();
        assertEquals(1, phm.get("A").intValue());
        assertEquals(2, phm.get("B").intValue());
        assertEquals(3, phm.get("C").intValue());
    }

    @Test
    public void testPersistentHashMapValues() {
        addABC();
        assertEquals("[1, 2, 3]", phm.values().toString());
    }

    @Test
    public void testPersistentHashMapKeySet() {
        addABC();
        assertTrue(phm.keySet().toString().contains("A"));
        assertTrue(phm.keySet().toString().contains("B"));
        assertTrue(phm.keySet().toString().contains("C"));
    }

}