import java.util.ArrayList;
import java.util.List;

public class Node<E> {
    public static int bit_na_pu = 2;
    public static int width;
    public List<E> data;
    public Node<E> parent;
    public List<Node<E>> children = new ArrayList<>();

    static {
        width = (int) Math.pow(2, bit_na_pu);
    }

    public void createChildren() {
        Node<E> node = new Node<E>();
        node.parent = this;

        if (children.size() < width)
            children.add(node);
    }

    public List<Node<E>> getChildren() {
        return children;
    }
}
