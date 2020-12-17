import org.junit.Test;

import static org.junit.Assert.*;

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

    private <E> String valuesToString(PersistentLinkedList<E> list) {
        StringBuilder stringBuilder = new StringBuilder();
//        for (E e : list) {
//            stringBuilder.append(e.toString());
//        }
        for (int i = 0; i < list.size(); i++)
            stringBuilder.append(list.get(i));

        return stringBuilder.toString();
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
        assertEquals("3", valuesToString(pl));


        pl.add(4);
        assertEquals(2, pl.getUniqueLeafsSize());
        assertEquals(3, pl.getVersionCount());
        assertEquals(0, pl.getCurrentHead().first);
        assertEquals(1, pl.getCurrentHead().last);
        assertEquals(2, pl.size());
        assertEquals("34", valuesToString(pl));

        pl.add(6);
        assertEquals(3, pl.getUniqueLeafsSize());
        assertEquals(4, pl.getVersionCount());
        assertEquals(0, pl.getCurrentHead().first);
        assertEquals(2, pl.getCurrentHead().last);
        assertEquals(3, pl.size());
        assertEquals("346", valuesToString(pl));

        pl.add(9);
        assertEquals(4, pl.getUniqueLeafsSize());
        assertEquals(5, pl.getVersionCount());
        assertEquals(0, pl.getCurrentHead().first);
        assertEquals(3, pl.getCurrentHead().last);
        assertEquals(4, pl.size());
        assertEquals("3469", valuesToString(pl));

        pl.undo();
        assertEquals(4, pl.getUniqueLeafsSize());
        assertEquals(5, pl.getVersionCount());
        assertEquals(0, pl.getCurrentHead().first);
        assertEquals(2, pl.getCurrentHead().last);
        assertEquals(3, pl.size());
        assertEquals("346", valuesToString(pl));

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
        assertEquals("34607", valuesToString(pl));

        pl.add(3, 9);
        assertEquals(6, pl.getUniqueLeafsSize());
        assertEquals(7, pl.getVersionCount());
        assertEquals(0, pl.getCurrentHead().first);
        assertEquals(4, pl.getCurrentHead().last);
        assertEquals(6, pl.size());
        assertEquals("346907", valuesToString(pl));


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
        assertEquals("346", valuesToString(pl));

        pl.add(1, 9);
        assertEquals(4, pl.getUniqueLeafsSize());
        assertEquals(5, pl.getVersionCount());
        assertEquals(0, pl.getCurrentHead().first);
        assertEquals(2, pl.getCurrentHead().last);
        assertEquals(4, pl.size());
        assertEquals("3946", valuesToString(pl));

        pl.add(1, 7);
        assertEquals(5, pl.getUniqueLeafsSize());
        assertEquals(6, pl.getVersionCount());
        assertEquals(0, pl.getCurrentHead().first);
        assertEquals(2, pl.getCurrentHead().last);
        assertEquals(5, pl.size());
        assertEquals("37946", valuesToString(pl));

        pl.add(8);
        assertEquals(6, pl.getUniqueLeafsSize());
        assertEquals(7, pl.getVersionCount());
        assertEquals(0, pl.getCurrentHead().first);
        assertEquals(5, pl.getCurrentHead().last);
        assertEquals(6, pl.size());
        assertEquals("379468", valuesToString(pl));


    }
}