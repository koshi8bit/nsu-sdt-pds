import java.util.ArrayList;
import java.util.List;

public class Node<E> {
    public static int bit_na_pu = 1;
    public static int width;
    public List<E> data;
    public List<Node<E>> children = new ArrayList<>();  // пусть изначально равно null

    static {
        width = (int) Math.pow(2, bit_na_pu);
    }

    public Node() {

    }

    public Node(Node<E> prevRoot) {
        //TODO check
        if (prevRoot.children == null) // TODO
        children.addAll(prevRoot.children);
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
