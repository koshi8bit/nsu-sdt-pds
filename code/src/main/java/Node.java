import java.util.ArrayList;
import java.util.List;

public class Node<E> {

    public int width = 4;
    public List<E> data;
    public Node<E> parent;
    public List<Node<E>> children = new ArrayList<>();

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
