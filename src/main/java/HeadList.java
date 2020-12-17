
public class HeadList<E> extends HeadArray<E> {
    public int first = 0;
    public int last = 0;
    public int sizeTree = 0;

    public HeadList() {
        super();
    }

    public HeadList(HeadList<E> other) {
        super(other);
        this.first = other.first;
        this.last = other.last;
        this.sizeTree = other.sizeTree;
    }

    public HeadList(HeadList<E> other, Integer sizeDelta) {
        super(other, sizeDelta);
        this.first = other.first;
        this.last = other.last;
        this.sizeTree = other.sizeTree;
    }

    public HeadList(HeadList<E> other, Integer newSize, Integer maxIndex) {
        super(other, newSize, maxIndex);
        this.first = other.first;
        this.last = other.last;
        this.sizeTree = other.sizeTree;
    }

    @Override
    public String toString() {
        //return String.format("%09x", root.hashCode());
//        return String.format("%09x s: %d; S: %d; F: %d; L: %d",
//                root.hashCode(),
//                size,
//                sizeTree,
//                first,
//                last
//        );
        return String.format("s:%d; S:%d; F:%d; L:%d",
                size,
                sizeTree,
                first,
                last
        );
    }



}
