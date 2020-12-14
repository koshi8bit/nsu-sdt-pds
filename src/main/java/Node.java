import com.sun.org.apache.xpath.internal.objects.XString;

import java.lang.reflect.Array;
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

    @Override
    public String toString() {
        return "N" + hashCode() + "[\n"
                + "  child=" + child + "\n"
                + "  value=" + value + "\n]";
    }

    private String drawTab(int count)
    {
        String s = "";
        for(int i=0; i<count; i++)
        {
            s += " ";
        }
        return s;
    }

    private String drawGraph(Node<E> node, int level)
    {
        String hash =  String.format("%09x", node.hashCode()) + " ";
        StringBuilder result = new StringBuilder();
        if (node.child == null)
        {
            if (node.value == null)
                return drawTab(level) + hash + "\n";
            else
                return drawTab(level) + hash + node.value.toString() + "\n";
        }
        else
        {
            for (Node<E> n : node.child)
            {
                result
                        .append(drawTab(level))
                        .append(hash)
                        .append("\n")
                        .append(drawGraph(n, level + 1));
            }
        }
        return result.toString();
    }

    public String drawGraph()
    {
        return  drawGraph(this, 0);
    }


}
