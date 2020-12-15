import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PersistentArrayTest {

    PersistentArray<String> pa = new PersistentArray<>(32);

    private void addABC() {
        pa.add("A");
        pa.add("B");
        pa.add("C");
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
        assertEquals("A", pa.get(0));
        assertEquals("B", pa.get(1));
        assertEquals("C", pa.get(2));
    }

    @Test
    public void testPersistentArrayToArray() {
        addABC();
        String[] strings = new String[pa.size()];
        pa.toArray(strings);
        assertEquals("[A, B, C]", Arrays.toString(strings));
    }

    @Test
    public void testPersistentArraySize() {
        assertEquals(pa.size(), 0);
        addABC();
        assertEquals(pa.size(), 3);
    }

    @Test
    public void testPersistentArrayIsEmpty() {
        assertTrue(pa.isEmpty());
        pa.add("A");
        assertFalse(pa.isEmpty());
    }

    @Test
    public void testPersistentArrayUndoRedo() {
        addABC();
        pa.undo();
        pa.undo();
        assertEquals("A", valuesToString(pa));

        pa.redo();
        assertEquals("AB", valuesToString(pa));

        pa.undo();
        pa.undo();
        assertEquals("", valuesToString(pa));

        pa.redo();
        pa.redo();
        pa.redo();
        assertEquals("ABC", valuesToString(pa));
    }

    @Test
    public void testPersistentArrayIterator() {
        addABC();
        Iterator<String> i = pa.iterator();
        assertEquals("A", i.next());
        assertEquals("B", i.next());
        assertEquals("C", i.next());
        assertFalse(i.hasNext());
    }

    @Test
    public void testPersistentArrayForEach() {
        addABC();
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : pa) {
            stringBuilder.append(s);
        }
        assertEquals("ABC", stringBuilder.toString());


        stringBuilder = new StringBuilder();
        pa.forEach(stringBuilder::append);
        assertEquals("ABC", stringBuilder.toString());
    }

    @Test
    public void testPersistentArrayPop() {
        addABC();
        assertEquals("C", pa.pop());
        assertEquals("B", pa.pop());
        pa.undo();
        pa.undo();
        assertEquals("C", pa.pop());
    }

    @Test
    public void testPersistentArraySet() {
        addABC();
        assertEquals("ABC", valuesToString(pa));
        pa.set(0, "Q");
        pa.set(1, "W");
        assertEquals("QWC", valuesToString(pa));
        pa.undo();
        pa.undo();
        assertEquals("ABC", valuesToString(pa));
    }

    @Test
    public void testPersistentArrayCascade() {
        pa.add("A");

        PersistentArray<String> v2 = pa.conj("B");

        assertEquals("A", valuesToString(pa));
        assertEquals("AB", valuesToString(v2));

        PersistentArray<String> v3 = v2.assoc(0, "C");

        assertEquals("CB", valuesToString(v3));
    }

    @Test
    public void testPersistentArrayStream() {
        PersistentArray<Integer> pa = new PersistentArray<>();
        pa.add(4);
        pa.add(5);
        pa.add(6);
        pa.add(7);

        assertEquals("[12, 14]", Arrays.toString(
                pa.stream().map(i -> i * 2).filter(x -> x > 10).toArray()));

        pa.undo();

        assertEquals("[12]", Arrays.toString(
                pa.stream().map(i -> i * 2).filter(x -> x > 10).toArray()));

    }

    @Test
    public void testPersistentArrayConstructor() {
        PersistentArray<String> pa0 = new PersistentArray<>();
        assertEquals(1073741824, pa0.maxSize);
        assertEquals(6, pa0.depth);
        assertEquals(32, pa0.width);

        PersistentArray<String> pa1 = new PersistentArray<>(27);
        assertEquals(32, pa1.maxSize);
        assertEquals(1, pa1.depth);
        assertEquals(32, pa1.width);

        PersistentArray<String> pa2 = new PersistentArray<>(32);
        assertEquals(32, pa2.maxSize);
        assertEquals(1, pa2.depth);
        assertEquals(32, pa2.width);

        PersistentArray<String> pa3 = new PersistentArray<>(33);
        assertEquals(1024, pa3.maxSize);
        assertEquals(2, pa3.depth);
        assertEquals(32, pa3.width);

        PersistentArray<String> pa4 = new PersistentArray<>(3, 1);
        assertEquals(8, pa4.maxSize);
        assertEquals(3, pa4.depth);
        assertEquals(2, pa4.width);
    }

    @Test
    public void testPersistentArrayAddInTheMiddle() {
        pa = new PersistentArray<>(3, 1);
        pa.add("3");
        pa.add("7");
        pa.add("6");
        pa.add("9");
        pa.add("1");
        assertEquals("37691", valuesToString(pa));
        pa.add(3, "8");
        assertEquals("376891", valuesToString(pa));
        assertThrows(IndexOutOfBoundsException.class, () -> pa.add(9999, "8"));
        assertThrows(IndexOutOfBoundsException.class, () -> pa.add(-1, "8"));
    }

    @Test
    public void testPersistentArrayToString() {
        addABC();
        assertEquals("size: 3; unique leafs: 3; array: [A, B, C]", pa.toString());
    }
}