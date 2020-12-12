import org.junit.After;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;

import static org.junit.Assert.*;

public class PersistentArrayTest {

    PersistentArray<String> stringPersistentArray = new PersistentArray<>(32);

    @After
    public void clear() {
        stringPersistentArray.clear();
    }

    private void addABC() {
        stringPersistentArray.add("A");
        stringPersistentArray.add("B");
        stringPersistentArray.add("C");
    }

    private <E> String valuesToString(PersistentArray<E> array) {
        StringBuilder stringBuilder = new StringBuilder();
        for (E e : array) {
            stringBuilder.append(e.toString());
        }
        return stringBuilder.toString();
    }

    @Test
    public void testPersistentArrayAddAndGet() {
        addABC();
        assertEquals("A", stringPersistentArray.get(0));
        assertEquals("B", stringPersistentArray.get(1));
        assertEquals("C", stringPersistentArray.get(2));
    }

    @Test
    public void testPersistentArrayToArray() {
        addABC();
        String[] strings = new String[stringPersistentArray.size()];
        stringPersistentArray.toArray(strings);
        assertEquals("[A, B, C]", Arrays.toString(strings));
    }

    @Test
    public void testPersistentArraySize() {
        assertEquals(stringPersistentArray.size(), 0);
        addABC();
        assertEquals(stringPersistentArray.size(), 3);
    }

    @Test
    public void testPersistentArrayIsEmpty() {
        assertTrue(stringPersistentArray.isEmpty());
        stringPersistentArray.add("A");
        assertFalse(stringPersistentArray.isEmpty());
    }

    @Test
    public void testPersistentArrayUndoRedo() {
        addABC();
        stringPersistentArray.undo();
        stringPersistentArray.undo();
        assertEquals(valuesToString(stringPersistentArray), "A");

        stringPersistentArray.redo();
        assertEquals(valuesToString(stringPersistentArray), "AB");

        stringPersistentArray.undo();
        stringPersistentArray.undo();
        assertEquals(valuesToString(stringPersistentArray), "");

        stringPersistentArray.redo();
        stringPersistentArray.redo();
        stringPersistentArray.redo();
        assertEquals(valuesToString(stringPersistentArray), "ABC");
    }

    @Test
    public void testPersistentArrayIterator() {
        addABC();
        Iterator<String> i = stringPersistentArray.iterator();
        assertEquals("A", i.next());
        assertEquals("B", i.next());
        assertEquals("C", i.next());
        assertFalse(i.hasNext());
    }

    @Test
    public void testPersistentArrayForEach() {
        addABC();
        assertEquals("ABC", valuesToString(stringPersistentArray));
    }

    @Test
    public void testPersistentArrayPop() {
        addABC();
        assertEquals("C", stringPersistentArray.pop());
        assertEquals("B", stringPersistentArray.pop());
        stringPersistentArray.undo();
        stringPersistentArray.undo();
        assertEquals("C", stringPersistentArray.pop());
    }

    @Test
    public void testPersistentArraySet() {
        addABC();
        assertEquals("ABC", valuesToString(stringPersistentArray));
        stringPersistentArray.set(0, "Q");
        stringPersistentArray.set(1, "W");
        stringPersistentArray.set(2, "E");
        assertEquals("QWE", valuesToString(stringPersistentArray));
        stringPersistentArray.undo();
        stringPersistentArray.undo();
        stringPersistentArray.undo();
        assertEquals("ABC", valuesToString(stringPersistentArray));
    }

    @Test
    public void testPersistentArrayCascade() {
        addABC();
        PersistentArray<String> v2 = stringPersistentArray.conj("D");
        assertEquals("ABC", valuesToString(stringPersistentArray));
        assertEquals("ABCD", valuesToString(v2));

        PersistentArray<String> v3 = v2.assoc(0, "G");

        assertEquals("GBCD", valuesToString(v3));
    }
}