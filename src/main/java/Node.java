import java.util.ArrayList;
import java.util.List;

public class Node<E> {
    public static int bit_na_pu = 5;
    public static int width = (int) Math.pow(2, bit_na_pu);

    public List<Node<E>> child;
    public List<E> value;

    public Node() {    }

    public Node(Node<E> other) {
        if (other.child != null) {
            child = new ArrayList<>();
            child.addAll(other.child);
        }

        if (other.value != null) {
            value = new ArrayList<>();
            value.addAll(other.value);
        }
    }

}
