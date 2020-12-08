import nodes.Node;

import java.util.List;

public abstract class AbstractPersistentCollection<E> implements UndoRedo, List<E> {
    public int depth = 2;
    public int bit_dlya_rasc_ur = Node.bit_na_pu * depth;
    public int mask = (int) Math.pow(2, Node.bit_na_pu) - 1;

    private int maxCount() {
        return (int) Math.pow(2, bit_dlya_rasc_ur);
    }
}
