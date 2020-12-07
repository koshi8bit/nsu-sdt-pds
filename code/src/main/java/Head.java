public class Head<E> {
    public Node<E> root;
    public int count = 0;

    public Head() {
        this.root = new Node<>();
        root.parent = null;
        this.count = 0;
    }

    public Head(Head<E> prevHead) {
        this.root = new Node<>(prevHead.root);
        this.root.parent = null;
        this.count = prevHead.count + 1;
    }
}
