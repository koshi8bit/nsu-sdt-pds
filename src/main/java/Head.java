import nodes.PU;

public class Head<E> {
    public PU<E> root;
    public int size;

    public Head() {
        this.root = new PU<>();
        this.size = 0;
    }

    public Head(Head<E> prevHead, Integer sizeDelta) {
        this.root = new PU<>(prevHead.root);
        this.size = prevHead.size + sizeDelta;
    }
}
