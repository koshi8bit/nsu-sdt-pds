package nodes;

import java.util.ArrayList;
import java.util.List;

public class PU<E> extends AbstractNode<E> {
    public List<AbstractNode<E>> child;


    public PU() { }

    public PU(PU<E> other) {
        if (other.child != null) {
            child = new ArrayList<>();
            child.addAll(other.child);
        }

//        if (other.data != null) {
//            data = new ArrayList<>();
//            data.addAll(other.data);
//        }
    }
}
