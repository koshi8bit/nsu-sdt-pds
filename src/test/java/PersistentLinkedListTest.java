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

    private void fill(int size)
    {
        for(int i=0; i<size; i++)
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

        pl.add(4);
        assertEquals(2, pl.getUniqueLeafsSize());
        assertEquals(3, pl.getVersionCount());
        assertEquals(0, pl.getCurrentHead().first);
        assertEquals(1, pl.getCurrentHead().last);
        assertEquals(2, pl.size());

        pl.add(6);
        assertEquals(3, pl.getUniqueLeafsSize());
        assertEquals(4, pl.getVersionCount());
        assertEquals(0, pl.getCurrentHead().first);
        assertEquals(2, pl.getCurrentHead().last);
        assertEquals(3, pl.size());

        pl.add(9);
        assertEquals(4, pl.getUniqueLeafsSize());
        assertEquals(5, pl.getVersionCount());
        assertEquals(0, pl.getCurrentHead().first);
        assertEquals(3, pl.getCurrentHead().last);
        assertEquals(4, pl.size());

        pl.undo();
        assertEquals(4, pl.getUniqueLeafsSize());
        assertEquals(5, pl.getVersionCount());
        assertEquals(0, pl.getCurrentHead().first);
        assertEquals(2, pl.getCurrentHead().last);
        assertEquals(3, pl.size());

    }
}