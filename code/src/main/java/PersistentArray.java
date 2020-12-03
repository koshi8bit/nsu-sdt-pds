import java.util.List;

public class PersistentArray<E> {
    public Node<E> root;
    public int depth = 2;
    private int count = 0;

    public PersistentArray() {
        root = new Node<>();
        root.parent = null;
        createBranch(root, depth);
    }

    public void add(List<E> list) {

    }

    public void createBranch(Node<E> node, int depth) {
        node.createChildren();
        if (depth > 0) {
            createBranch(node.getChildren().get(0), --depth);
        }
    }

    public PersistentArray<E> append(E element) {
        return new PersistentArray<E>();
    }
}
