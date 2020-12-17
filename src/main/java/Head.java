public class Head<E> {
    public Node<E> root;
    public int size = 0;
    public int first = 0;
    public int last = 0;

    public Head() {
        this.root = new Node<>();
    }

    public Head(Head<E> other) {
        this.root = new Node<>(other.root);
        this.size = other.size;
        this.first = other.first;
        this.last = other.last;
    }


    public Head(Head<E> other, Integer sizeDelta) {
        this.root = new Node<>(other.root);
        this.size = other.size + sizeDelta;
        this.first = other.first;
        this.last = other.last;
    }

    public Head(Head<E> other, Integer newSize, Integer maxIndex) {
        this.root = new Node<>(other.root, maxIndex);
        this.size = newSize;
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
