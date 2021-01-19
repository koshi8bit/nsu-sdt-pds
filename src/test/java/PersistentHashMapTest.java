import org.junit.Test;

import java.util.HashSet;
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
        assertEquals(Integer.valueOf(1), phm.get("A"));
        assertEquals(Integer.valueOf(2), phm.get("B"));
        assertEquals(Integer.valueOf(3), phm.get("C"));
    }

    @Test
    public void testPersistentHashMapValues() {
        addABC();
        assertEquals("[1, 2, 3]", phm.values().toString());
    }

    @Test
    public void testPersistentHashMapKeySet() {
        addABC();

        HashSet<String> hs = new HashSet<>();
        hs.add("A");
        hs.add("B");
        hs.add("C");

        assertEquals(hs, phm.keySet());
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
        assertTrue(stringBuilder.toString().contains("A=1"));
        assertTrue(stringBuilder.toString().contains("B=2"));
        assertTrue(stringBuilder.toString().contains("C=3"));
    }

    @Test
    public void testPersistentHashMapUndoRedo() {
        addABC();

        phm.undo();
        assertEquals(Integer.valueOf(1), phm.get("A"));
        assertEquals(Integer.valueOf(2), phm.get("B"));
        assertFalse(phm.containsKey("C"));

        phm.undo();
        assertEquals(Integer.valueOf(1), phm.get("A"));
        assertFalse(phm.containsKey("B"));
        assertFalse(phm.containsKey("C"));

        phm.redo();
        assertEquals(Integer.valueOf(1), phm.get("A"));
        assertEquals(Integer.valueOf(2), phm.get("B"));
        assertFalse(phm.containsKey("C"));

        phm.redo();
        assertEquals(Integer.valueOf(1), phm.get("A"));
        assertEquals(Integer.valueOf(2), phm.get("B"));
        assertEquals(Integer.valueOf(3), phm.get("C"));

        phm.undo();
        phm.undo();
        phm.undo();
        assertEquals(0, phm.size());

        phm.put("Alone", 1);
        assertEquals(Integer.valueOf(1), phm.get("Alone"));
    }

    @Test
    public void testPersistentHashMapContainsKey() {
        addABC();

        assertEquals(3, phm.size());

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

        assertEquals(3, phm.size());

        assertTrue(phm.containsValue(1));
        assertTrue(phm.containsValue(2));
        assertTrue(phm.containsValue(3));

        assertFalse(phm.containsValue(4));
        assertFalse(phm.containsValue(5));
        assertFalse(phm.containsValue(6));
    }

    @Test
    public void testPersistentHashMapAPIForEach() {
        addABC();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        phm.forEach((k, v) -> stringBuilder.append(k).append(":").append(v).append(" "));
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(" "));
        stringBuilder.append("]");

        assertTrue(stringBuilder.toString().contains("A:1"));
        assertTrue(stringBuilder.toString().contains("B:2"));
        assertTrue(stringBuilder.toString().contains("C:3"));
    }

    @Test
    public void testPersistentHashMapClear() {
        addABC();
        assertEquals(3, phm.size());
        phm.clear();
        assertEquals(0, phm.size());
    }

    @Test
    public void testPersistentHashMapRemove() {
        addABC();

        assertEquals(3, phm.size());
        assertEquals(Integer.valueOf(1), phm.get("A"));
        assertEquals(Integer.valueOf(2), phm.get("B"));
        assertEquals(Integer.valueOf(3), phm.get("C"));

        phm.remove("A");
        assertFalse(phm.containsKey("A"));
        assertEquals(2, phm.size());

        phm.remove("C");
        assertFalse(phm.containsKey("C"));
        assertEquals(1, phm.size());
    }

    @Test
    public void testPersistentHashMapModifyAndUndoRedo() {
        phm.put("Gosha", 12);
        assertEquals(1, phm.size());
        assertEquals(Integer.valueOf(12), phm.get("Gosha"));

        phm.put("Gosha", 1000);
        assertEquals(1, phm.size());
        assertEquals(Integer.valueOf(1000), phm.get("Gosha"));

        phm.undo();
        assertEquals(1, phm.size());
        assertEquals(Integer.valueOf(12), phm.get("Gosha"));
    }

    @Test
    public void testPersistentHashMapToString() {
        addABC();
        assertTrue(phm.toString().contains("A=1"));
        assertTrue(phm.toString().contains("B=2"));
        assertTrue(phm.toString().contains("C=3"));

        // "[C=3 B=2 A=1]" - содержит 13 символов
        assertEquals(13, phm.toString().length());
    }

    @Test
    public void testPersistentHashMapCascade() {
        PersistentHashMap<String, Integer> v1 = new PersistentHashMap<>();
        v1.put("Vasya", 1);
        PersistentHashMap<String, Integer> v2 = v1.conj("Cooper", 2);

        assertEquals(Integer.valueOf(1), v1.get("Vasya"));
        assertFalse(v1.containsKey("Cooper"));
        assertEquals(1, v1.size());

        assertEquals(Integer.valueOf(1), v2.get("Vasya"));
        assertEquals(Integer.valueOf(2), v2.get("Cooper"));
        assertEquals(2, v2.size());

        //assoc
        PersistentHashMap<String, Integer> v3 = v2.conj("Vasya", 999);

        assertEquals(Integer.valueOf(1), v1.get("Vasya"));
        assertFalse(v1.containsKey("Cooper"));
        assertEquals(1, v1.size());

        assertEquals(Integer.valueOf(1), v2.get("Vasya"));
        assertEquals(Integer.valueOf(2), v2.get("Cooper"));
        assertEquals(2, v2.size());

        assertEquals(Integer.valueOf(999), v3.get("Vasya"));
        assertEquals(Integer.valueOf(2), v3.get("Cooper"));
        assertEquals(2, v3.size());

        v3.put("Maks", 3);
        v3.put("Anna", 4);
        assertEquals(4, v3.size());

        v3.remove("Maks");
        assertFalse(v3.containsKey("Maks"));
        assertEquals(3, v3.size());
    }

    @Test
    public void testPersistentPersistentHashMapInsertedUndoRedo() {
        PersistentHashMap<String, PersistentHashMap<String, Integer>> parent = new PersistentHashMap<>();
        PersistentHashMap<String, Integer> child1 = new PersistentHashMap<>();
        PersistentHashMap<String, Integer> child2 = new PersistentHashMap<>();
        PersistentHashMap<String, Integer> child3 = new PersistentHashMap<>();

        parent.get("child1").put("One", 1);

        parent.put("child1", child1);
        parent.put("child2", child2);
        parent.put("child3", child3);

        parent.get("child1").put("One", 1);
        parent.get("child1").put("Two", 2);
        //parent.get("child1").put("Three", 3);

        parent.get("child2").put("One", 11);
        parent.get("child2").put("Two", 22);
        //parent.get("child2").put("Three", 33);

        parent.get("child3").put("One", 111);
        parent.get("child3").put("Two", 222);
        //parent.get("child3").put("Three", 333);

        //assertEquals(Integer.valueOf(333), parent.get("child3").get("Three"));
        //parent.undo();
        //assertFalse(parent.get("child3").containsKey("Three"));

        //System.out.println(parent.toString());
        System.out.println(parent);
        parent.undo();
        System.out.println(parent);
        parent.undo();
        System.out.println(parent);
        parent.undo();
        System.out.println(parent);
        parent.undo();
        System.out.println(parent);
        parent.undo();
        System.out.println(parent);
        parent.undo();
        System.out.println(parent);
        parent.undo();
        System.out.println(parent);
        parent.undo();
        System.out.println(parent);
        parent.undo();
        System.out.println(parent);

        parent.redo();
        System.out.println(parent);
        parent.redo();
        System.out.println(parent);
        parent.redo();
        System.out.println(parent);
        parent.redo();
        System.out.println(parent);
        parent.redo();
        System.out.println(parent);
        parent.redo();
        System.out.println(parent);
        parent.redo();
        System.out.println(parent);
        //parent.insertedRedo.pop().redo();
        //parent.redo();



        /*System.out.println("[child1=[One=1]] = " + parent.toString());
        System.out.println(parent.insertedRedo + "Это внутр реду");
        System.out.println("делаем два анду");
        parent.undo();
        parent.undo();
        System.out.println("[] = " + parent);
        System.out.println(parent.insertedRedo + "Это внутр реду");
        parent.redo();
        // parent.table.get(parent.redo.pop()).redo(); - святой грааль
        System.out.println("[child1=[]] = " + parent);*/


        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println(parent.toString());
        parent.undo();
        System.out.println(parent.toString());
        parent.undo();
        System.out.println(parent.toString());
        parent.undo();
        System.out.println(parent.toString());

/*
        child4.put("Меня выпилят :(", 666);

        assertEquals(Integer.valueOf(666), parent.get("child4").get("Меня выпилят :("));
        System.out.println(parent.toString());
        parent.undo();
        assertFalse(parent.get("child4").containsKey("Меня выпилят :("));
        System.out.println(parent.toString());
        parent.undo();
        System.out.println(parent.toString());
        assertFalse(parent.containsKey("child4"));*/
    }
}