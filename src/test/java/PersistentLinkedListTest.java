import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PersistentLinkedListTest {
    PersistentLinkedList<Integer> pl;

    private void init(int fillSize) {
        pl = new PersistentLinkedList<>(100);
        fill(fillSize);
    }

    private void init(int fillSize, int maxSize) {
        pl = new PersistentLinkedList<>(maxSize);
        fill(fillSize);
    }

    private void init(int fillSize, int depth, int bit_na_pu) {
        pl = new PersistentLinkedList<>(depth, bit_na_pu);
        fill(fillSize);
    }

    private void fill(int size) {
        for (int i = 0; i < size; i++)
            pl.add(i);
    }

    @Test
    public void add() {
        init(0);

        pl.add(3);
        assertEquals(1, pl.getUniqueLeafsSize());
        assertEquals(2, pl.getVersionCount());
        assertEquals(0, pl.getCurrentHead().first);
        assertEquals(0, pl.getCurrentHead().last);
        assertEquals(1, pl.size());
        assertEquals("[3]", pl.toString());


        pl.add(4);
        assertEquals(2, pl.getUniqueLeafsSize());
        assertEquals(3, pl.getVersionCount());
        assertEquals(0, pl.getCurrentHead().first);
        assertEquals(1, pl.getCurrentHead().last);
        assertEquals(2, pl.size());
        assertEquals("[3, 4]", pl.toString());

        pl.add(6);
        assertEquals(3, pl.getUniqueLeafsSize());
        assertEquals(4, pl.getVersionCount());
        assertEquals(0, pl.getCurrentHead().first);
        assertEquals(2, pl.getCurrentHead().last);
        assertEquals(3, pl.size());
        assertEquals("[3, 4, 6]", pl.toString());

        pl.add(9);
        assertEquals(4, pl.getUniqueLeafsSize());
        assertEquals(5, pl.getVersionCount());
        assertEquals(0, pl.getCurrentHead().first);
        assertEquals(3, pl.getCurrentHead().last);
        assertEquals(4, pl.size());
        assertEquals("[3, 4, 6, 9]", pl.toString());

        pl.undo();
        assertEquals(4, pl.getUniqueLeafsSize());
        assertEquals(5, pl.getVersionCount());
        assertEquals(0, pl.getCurrentHead().first);
        assertEquals(2, pl.getCurrentHead().last);
        assertEquals(3, pl.size());
        assertEquals("[3, 4, 6]", pl.toString());

    }

    @Test
    public void insertMemCopy() {
        init(0);
        pl.add(3);
        pl.add(4);
        pl.add(6);
        pl.add(0);
        pl.add(7);
        assertEquals(5, pl.getUniqueLeafsSize());
        assertEquals(6, pl.getVersionCount());
        assertEquals(0, pl.getCurrentHead().first);
        assertEquals(4, pl.getCurrentHead().last);
        assertEquals(5, pl.size());
        assertEquals("[3, 4, 6, 0, 7]", pl.toString());

        pl.add(3, 9);
        assertEquals(6, pl.getUniqueLeafsSize());
        assertEquals(7, pl.getVersionCount());
        assertEquals(0, pl.getCurrentHead().first);
        assertEquals(4, pl.getCurrentHead().last);
        assertEquals(6, pl.size());
        assertEquals("[3, 4, 6, 9, 0, 7]", pl.toString());


    }

    @Test
    public void insert() {
        init(0);
        pl.add(3);
        pl.add(4);
        pl.add(6);
        assertEquals(3, pl.getUniqueLeafsSize());
        assertEquals(4, pl.getVersionCount());
        assertEquals(0, pl.getCurrentHead().first);
        assertEquals(2, pl.getCurrentHead().last);
        assertEquals(3, pl.size());
        assertEquals("[3, 4, 6]", pl.toString());

        pl.add(1, 9);
        assertEquals(4, pl.getUniqueLeafsSize());
        assertEquals(5, pl.getVersionCount());
        assertEquals(0, pl.getCurrentHead().first);
        assertEquals(2, pl.getCurrentHead().last);
        assertEquals(4, pl.size());
        assertEquals("[3, 9, 4, 6]", pl.toString());

        pl.add(1, 7);
        assertEquals(5, pl.getUniqueLeafsSize());
        assertEquals(6, pl.getVersionCount());
        assertEquals(0, pl.getCurrentHead().first);
        assertEquals(2, pl.getCurrentHead().last);
        assertEquals(5, pl.size());
        assertEquals("[3, 7, 9, 4, 6]", pl.toString());

        pl.add(8);
        assertEquals(6, pl.getUniqueLeafsSize());
        assertEquals(7, pl.getVersionCount());
        assertEquals(0, pl.getCurrentHead().first);
        assertEquals(5, pl.getCurrentHead().last);
        assertEquals(6, pl.size());
        assertEquals("[3, 7, 9, 4, 6, 8]", pl.toString());
    }

    @Test
    public void insertIntoBeginAndEnd() {
        init(0, 3, 1);
        pl.add(1);
        pl.add(2);
        pl.add(3);
        assertEquals(4, pl.getUniqueLeafsSize());
        assertEquals(4, pl.getVersionCount());
        assertEquals(0, pl.getCurrentHead().first);
        assertEquals(2, pl.getCurrentHead().last);
        assertEquals(3, pl.size());
        assertEquals("[1, 2, 3]", pl.toString());

        pl.add(0, 4);
        assertEquals(6, pl.getUniqueLeafsSize());
        assertEquals(5, pl.getVersionCount());
        assertEquals(3, pl.getCurrentHead().first);
        assertEquals(2, pl.getCurrentHead().last);
        assertEquals(4, pl.size());
        assertEquals("[4, 1, 2, 3]", pl.toString());

        pl.add(0, 5);
        assertEquals(8, pl.getUniqueLeafsSize());
        assertEquals(6, pl.getVersionCount());
        assertEquals(4, pl.getCurrentHead().first);
        assertEquals(2, pl.getCurrentHead().last);
        assertEquals(5, pl.size());
        assertEquals("[5, 4, 1, 2, 3]", pl.toString());

        assertThrows(IndexOutOfBoundsException.class, () -> pl.add(5, 6));

//TODO fix me

//        pl.add(4, 7);
//        assertEquals(10, pl.getUniqueLeafsSize());
//        assertEquals(7, pl.getVersionCount());
//        assertEquals(4, pl.getCurrentHead().first);
//        assertEquals(5, pl.getCurrentHead().last);
//        assertEquals(6, pl.size());
//        assertEquals("541273", valuesToString(pl));
    }

    @Test
    public void testPersistentLinkedListIterator() {
        init(3);
        Iterator<Integer> i = pl.iterator();
        assertTrue(i.hasNext());
        assertEquals(Integer.valueOf(0), i.next());
        assertEquals(Integer.valueOf(1), i.next());
        assertEquals(Integer.valueOf(2), i.next());
        assertFalse(i.hasNext());
        pl.add(3);
        assertFalse(i.hasNext());

        i = pl.iterator();
        assertTrue(i.hasNext());
        assertEquals(Integer.valueOf(0), i.next());
        assertEquals(Integer.valueOf(1), i.next());
        assertEquals(Integer.valueOf(2), i.next());
        assertEquals(Integer.valueOf(3), i.next());
        assertFalse(i.hasNext());

        pl = new PersistentLinkedList<>(3, 1);
        pl.add(3);
        pl.add(4);
        pl.remove(0);
        assertEquals("[4]", pl.toString());
        i = pl.iterator();
        assertTrue(i.hasNext());
        assertEquals(Integer.valueOf(4), i.next());
        assertFalse(i.hasNext());
    }

    @Test
    public void testPersistentLinkedListRemove() {
        init(3);
        assertEquals("[0, 1, 2]", pl.toString());
        pl.remove(0);
        assertEquals("[1, 2]", pl.toString());
        pl.remove(0);
        assertEquals("[2]", pl.toString());
        assertEquals(1, pl.size());
        pl.add(3);
        pl.add(4);
        pl.add(5);
        assertEquals("[2, 3, 4, 5]", pl.toString());
        pl.remove(2);
        assertEquals("[2, 3, 5]", pl.toString());
        assertEquals(3, pl.size());
        assertThrows(IndexOutOfBoundsException.class, () -> pl.set(10, 10));
    }

    @Test
    public void testPersistentLinkedListSet() {
        init(3);
        assertEquals("[0, 1, 2]", pl.toString());
        pl.set(1, -1);
        assertEquals("[0, -1, 2]", pl.toString());
        pl.set(2, -2);
        assertEquals("[0, -1, -2]", pl.toString());
        assertThrows(IndexOutOfBoundsException.class, () -> pl.set(10, 10));
    }

    @Test
    public void testPersistentLinkedListUndoRedo() {
        init(3);
        assertEquals("[0, 1, 2]", pl.toString());

        pl.add(3);
        assertEquals("[0, 1, 2, 3]", pl.toString());
        pl.undo();
        assertEquals("[0, 1, 2]", pl.toString());
        pl.redo();
        assertEquals("[0, 1, 2, 3]", pl.toString());

        pl.set(1, -1);
        assertEquals("[0, -1, 2, 3]", pl.toString());
        pl.undo();
        assertEquals("[0, 1, 2, 3]", pl.toString());
        pl.redo();
        assertEquals("[0, -1, 2, 3]", pl.toString());

        pl.remove(2);
        assertEquals("[0, -1, 3]", pl.toString());
        pl.undo();
        assertEquals("[0, -1, 2, 3]", pl.toString());
        pl.redo();
        assertEquals("[0, -1, 3]", pl.toString());
    }
}