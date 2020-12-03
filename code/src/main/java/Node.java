import java.util.List;

public class Node<E> {

    private int width = 4;
    private E[] data;
    public Node<E> parent;
    private List<Node<E>> children;

    public void createChildren() {
        Node<E> node = new Node<E>();
        node.parent = this;

        if (children.size() < width)
        children.add(node);


    }
}
