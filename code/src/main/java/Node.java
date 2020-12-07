import java.util.ArrayList;
import java.util.List;

public class Node<E> {
    public static int bit_na_pu = 1;
    public static int width;
    public List<E> data;
    public List<Node<E>> child = new ArrayList<>(); // TODO make as data: null as default

    static {
        width = (int) Math.pow(2, bit_na_pu);
    }

    public Node() {

    }

    /// Копирование содержимого при копировании пути
    public Node(Node<E> prevRoot) {
        //TODO check
        if (prevRoot.child != null)
            child.addAll(prevRoot.child);

        if (prevRoot.data != null) {
            data = new ArrayList<>();
            data.addAll(prevRoot.data);
        }
    }

    public void createChildren() {
        Node<E> node = new Node<E>();

        if (child.size() < width)
            child.add(node);
    }

    public List<Node<E>> getChild() {
        return child;
    }
}
