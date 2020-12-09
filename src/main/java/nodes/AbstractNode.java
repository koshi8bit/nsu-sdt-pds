package nodes;

import java.util.ArrayList;
import java.util.List;

public class AbstractNode<E> {
    public static int bit_na_pu = 1;
    public static int width = (int) Math.pow(2, bit_na_pu);

    public List<AbstractNode<E>> child;
    public List<E> value;

    public AbstractNode() {    }

    public AbstractNode(AbstractNode<E> other) {
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
