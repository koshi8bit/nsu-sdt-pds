package nodes;

import java.util.ArrayList;
import java.util.List;

public class Node<E> extends AbstractNode{
    public List<Node<E>> child;// = new ArrayList<>(); // TODO make as data: null as default

    public Node() {

    }

    /// Копирование содержимого при копировании пути
    public Node(Node<E> other) {
        //TODO check
        if (other.child != null) {
            child = new ArrayList<>();
            child.addAll(other.child);
        }

        if (other.data != null) {
            data = new ArrayList<>();
            data.addAll(other.data);
        }
    }

    public List<Node<E>> getChild() {
        return child;
    }
}
