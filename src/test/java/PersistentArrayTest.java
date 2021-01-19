import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PersistentArrayTest {
    PersistentArray<String> pa;


    private void addABC() {
        pa = new PersistentArray<>(32);
        pa.add("A");
        pa.add("B");
        pa.add("C");
    }

    private void addABC(int depth, int bit_na_pu) {
        pa = new PersistentArray<>(depth, bit_na_pu);
        pa.add("A");
        pa.add("B");
        pa.add("C");
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
        pa = new PersistentArray<>(32);
        assertEquals(pa.size(), 0);
        pa.add("A");
        pa.add("B");
        pa.add("C");
        assertEquals(pa.size(), 3);
    }

    @Test
    public void testPersistentAdd() {
        pa = new PersistentArray<>(1, 1);
        assertEquals(2, pa.maxSize);

        assertTrue(pa.add("A"));
        assertTrue(pa.add("B"));
        assertFalse(pa.add("C"));
    }

    @Test
    public void testPersistentArrayIsEmpty() {
        pa = new PersistentArray<>(32);
        assertTrue(pa.isEmpty());
        pa.add("A");
        assertFalse(pa.isEmpty());
    }

    @Test
    public void testPersistentArrayUndoRedo() {
        addABC();
        assertEquals(4, pa.getVersionCount());
        pa.undo();
        assertEquals(4, pa.getVersionCount());
        pa.undo();
        assertEquals(4, pa.getVersionCount());
        assertEquals("[A]", pa.toString());

        pa.redo();
        assertEquals(4, pa.getVersionCount());
        assertEquals("[A, B]", pa.toString());

        pa.undo();
        assertEquals(4, pa.getVersionCount());
        pa.undo();
        assertEquals(4, pa.getVersionCount());
        assertEquals("[]", pa.toString());

        pa.redo();
        assertEquals(4, pa.getVersionCount());
        pa.redo();
        assertEquals(4, pa.getVersionCount());
        pa.redo();
        assertEquals(4, pa.getVersionCount());
        assertEquals("[A, B, C]", pa.toString());
    }

    @Test
    public void testPersistentArrayInsertedUndoRedo() {
        PersistentArray<PersistentArray<String>> parent = new PersistentArray<>();
        PersistentArray<String> child1 = new PersistentArray<>();
        PersistentArray<String> child2 = new PersistentArray<>();
        PersistentArray<String> child3 = new PersistentArray<>();
        parent.add(child1);
        parent.add(child2);
        parent.add(child3);

        parent.get(0).add("1");
        parent.get(0).add("2");
        parent.get(0).add("3");

        parent.get(1).add("11");
        parent.get(1).add("22");
        parent.get(1).add("33");

        parent.get(2).add("111");
        parent.get(2).add("222");
        parent.get(2).add("333");

        assertEquals("[[1, 2, 3], [11, 22, 33], [111, 222, 333]]", parent.toString());
        parent.undo();
        assertEquals("[[1, 2, 3], [11, 22, 33], [111, 222]]", parent.toString());

        PersistentArray<String> child4 = new PersistentArray<>();
        parent.add(1, child4);
        child4.add("Меня выпилят :(");
        assertEquals("[[1, 2, 3], [Меня выпилят :(], [11, 22, 33], [111, 222]]", parent.toString());
        parent.undo();
        assertEquals("[[1, 2, 3], [], [11, 22, 33], [111, 222]]", parent.toString());

        parent.get(0).set(0, "Я выживу!");
        parent.get(0).set(1, "А я нет...");
        assertEquals("[[Я выживу!, А я нет..., 3], [], [11, 22, 33], [111, 222]]", parent.toString());
        parent.undo();
        assertEquals("[[Я выживу!, 2, 3], [], [11, 22, 33], [111, 222]]", parent.toString());
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
        assertEquals("[A, B, C]", pa.toString());
        pa.set(0, "Q");
        pa.set(1, "W");
        assertEquals("[Q, W, C]", pa.toString());
        pa.undo();
        pa.undo();
        assertEquals("[A, B, C]", pa.toString());
    }

    @Test
    public void testPersistentArrayCascade() {
        pa = new PersistentArray<>(32);
        pa.add("A");

        PersistentArray<String> v2 = pa.conj("B");

        assertEquals("[A]", pa.toString());
        assertEquals("[A, B]", v2.toString());

        PersistentArray<String> v3 = v2.assoc(0, "C");

        assertEquals("[C, B]", v3.toString());
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
        assertEquals("[3, 7, 6, 9, 1]", pa.toString());
        pa.add(3, "8");
        assertEquals("[3, 7, 6, 8, 9, 1]", pa.toString());
        assertThrows(IndexOutOfBoundsException.class, () -> pa.add(-1, "8"));
        assertThrows(IndexOutOfBoundsException.class, () -> pa.add(6, "8"));
        assertThrows(IndexOutOfBoundsException.class, () -> pa.add(9999, "8"));
    }

    @Test
    public void testPersistentArrayToString() {
        addABC();
        assertEquals("[A, B, C]", pa.toString());
    }

    @Test
    public void testPersistentArrayRemove() {
        addABC(3, 1);

        assertEquals(3, pa.calcUniqueLeafs());
        assertThrows(IndexOutOfBoundsException.class, () -> pa.remove(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> pa.remove(3));
        assertThrows(IndexOutOfBoundsException.class, () -> pa.remove(999));

        assertEquals("B", pa.remove(1));
        assertEquals("[A, C]", pa.toString());
        assertEquals(4, pa.calcUniqueLeafs());

        assertEquals("C", pa.remove(1));
        assertEquals("[A]", pa.toString());
        assertEquals(5, pa.calcUniqueLeafs());

        assertEquals("A", pa.remove(0));
        assertEquals("[]", pa.toString());
        assertEquals(5, pa.calcUniqueLeafs());
        assertThrows(IndexOutOfBoundsException.class, () -> pa.remove(0));
    }

    @Test
    public void testPersistentArrayClear() {
        addABC();
        pa.clear();
        assertEquals("[]", pa.toString());
        pa.undo();
        assertEquals("[A, B, C]", pa.toString());
    }

    @Test
    public void testPersistentArrayUniqueLeafs() {
        pa = new PersistentArray<>(3, 1);
        assertEquals(0, pa.calcUniqueLeafs());
        pa.add("A");
        assertEquals(1, pa.calcUniqueLeafs());
        pa.add("B");
        assertEquals(2, pa.calcUniqueLeafs());
    }


    @Test
    public void testPersistentArrayPop2() {
        addABC(3, 1);
        assertEquals("C", pa.pop());
        assertEquals("B", pa.pop());
        assertEquals("A", pa.pop());
    }


}