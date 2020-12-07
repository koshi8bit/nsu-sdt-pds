import java.util.ArrayList;
import java.util.List;

public class Node<E> {
    public int iii = 999;
    public static int bit_na_pu = 2;
    public static int width;
    public List<E> data;
    public List<Node<E>> child;// = new ArrayList<>(); // TODO make as data: null as default

    static {
        width = (int) Math.pow(2, bit_na_pu);
    }

    public Node() {

    }

    /// Копирование содержимого при копировании пути
    public Node(Node<E> other) {
        //TODO check
        if (other.child != null) {
            if (child == null)
                child = new ArrayList<>();
            child.addAll(other.child);
        }

        if (other.data != null) {
            if (data == null)
                data = new ArrayList<>();
            data.addAll(other.data);
        }
    }

    public List<Node<E>> getChild() {
        return child;
    }
}
