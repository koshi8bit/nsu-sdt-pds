import java.util.List;

public abstract class AbstractPersistentCollection implements UndoRedo, List<E> {
    public final int depth;
    public final int bit_dlya_rasc_ur;
    public final int mask;
    public final int maxSize;

    public AbstractPersistentCollection(int depth) {
        this.depth = depth;
        bit_dlya_rasc_ur = Node.bit_na_pu * depth;
        mask = (int) Math.pow(2, Node.bit_na_pu) - 1;
        maxSize = (int) Math.pow(2, bit_dlya_rasc_ur);
    }


}
