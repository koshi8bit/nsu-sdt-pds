import com.sun.xml.internal.ws.api.message.HeaderList;

public class HeadList<E> extends HeadArray<E> {
    public int first = 0;
    public int last = 0;

    public HeadList(HeadList<E> other) {
        super(other);
        this.first = other.first;
        this.last = other.last;
    }

    public HeadList(HeadList<E> other, Integer sizeDelta) {
        super(other, sizeDelta);
        this.first = other.first;
        this.last = other.last;
    }

    public HeadList(HeadList<E> other, Integer newSize, Integer maxIndex) {
        super(other, newSize, maxIndex);
        this.first = other.first;
        this.last = other.last;
    }

    @Override
    public String toString() {
        //return String.format("%09x", root.hashCode());
        return String.format("%09x F: %d, L: %d",
                root.hashCode(),
                first,
                last
        );
    }



}
