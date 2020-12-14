import java.util.ArrayList;
import java.util.List;

public class Node<E> {

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

    public Node(Node<E> other, int maxIndex) {
        if (other.child != null) {
            child = new ArrayList<>();
            for (int i=0; i<=maxIndex; i++)
                child.add(other.child.get(i));

        }

        if (other.value != null) {
            value = new ArrayList<>();
            for (int i=0; i<=maxIndex; i++)
                value.add(other.value.get(i));
        }
    }

    public boolean isEmpty()
    {
        if ((child == null) && (value == null))
            return true;

        boolean result = true;

        if ((child != null) && (!child.isEmpty()))
            result = false;

        if ((value != null) && (!value.isEmpty()))
            result = false;

        return result;
    }

}
