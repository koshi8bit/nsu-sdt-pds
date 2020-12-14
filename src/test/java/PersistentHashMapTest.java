import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class PersistentHashMapTest {
    PersistentHashMap<String, Integer> phm = new PersistentHashMap<>();

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

    @Test
    public void testPersistentHashMapForEach() {
        addABC();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (Map.Entry<String, Integer> entry : phm.entrySet()) {
            stringBuilder.append(entry);
            stringBuilder.append(" ");
        }
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(" "));
        stringBuilder.append("]");
        System.out.println(stringBuilder.toString());
    }

    @Test
    public void testPersistentHashMapContainsKey() {
        addABC();

        assertTrue(phm.containsKey("A"));
        assertTrue(phm.containsKey("B"));
        assertTrue(phm.containsKey("C"));

        assertFalse(phm.containsKey("D"));
        assertFalse(phm.containsKey("E"));
        assertFalse(phm.containsKey("F"));
    }

    @Test
    public void testPersistentHashMapContainsValue() {
        addABC();

        assertTrue(phm.containsValue(1));
        assertTrue(phm.containsValue(2));
        assertTrue(phm.containsValue(3));

        assertFalse(phm.containsValue(4));
        assertFalse(phm.containsValue(5));
        assertFalse(phm.containsValue(6));
    }

    @Test
    public void testPersistentHashMapAPI() {
        addABC();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        phm.forEach((k, v) -> stringBuilder.append(k).append(":").append(v).append(" "));
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(" "));
        stringBuilder.append("]");
        assertEquals("[C:3 B:2 A:1]", stringBuilder.toString());
    }
}