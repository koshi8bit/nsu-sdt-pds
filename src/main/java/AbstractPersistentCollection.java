import nodes.AbstractNode;

import java.util.List;

public abstract class AbstractPersistentCollection<E> implements UndoRedo, List<E> {
    public int depth = 3;
    public int bit_dlya_rasc_ur = AbstractNode.bit_na_pu * depth;
    public int mask = (int) Math.pow(2, AbstractNode.bit_na_pu) - 1;

    public int maxSize() {
        return (int) Math.pow(2, bit_dlya_rasc_ur);
    }
}
