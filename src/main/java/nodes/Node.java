package nodes;

import java.util.ArrayList;
import java.util.List;

public class Node<E> extends AbstractNode{
    public List<Node<E>> child;// = new ArrayList<>(); // TODO make as data: null as default

    public Node() {

    }

    /// Копирование содержимого при копировании пути


    public List<Node<E>> getChild() {
        return child;
    }
}
