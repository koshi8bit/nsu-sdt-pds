import java.util.List;

public abstract class AbstractPersistentCollection<E> implements UndoRedo, List<E> {
    public final int depth;
    public final int bit_dlya_rasc_ur;
    public final int mask;
    public final int maxSize;
    public final int bit_na_pu;
    public final int width;


    public AbstractPersistentCollection(int depth, int bit_na_pu) {
        this.depth = depth;
        this.bit_na_pu = bit_na_pu;

        bit_dlya_rasc_ur = bit_na_pu * depth;
        mask = (int) Math.pow(2, bit_na_pu) - 1;
        maxSize = (int) Math.pow(2, bit_dlya_rasc_ur);

        width = (int) Math.pow(2, bit_na_pu);
    }

    public static double log(int N, int newBase)
    {

        // calculate log2 N indirectly
        // using log() method

        return (Math.log(N) / Math.log(newBase));
    }


}
