public class Head<E> {
    public Node<E> root;
    public int count = 0;

    public Head() {
        this.root = new Node<>();
        this.count = 0;
    }

    public Head(Head<E> prevHead) {
        this.root = new Node<>(prevHead.root);
        this.count = prevHead.count + 1;
    }
}
