import java.util.ArrayList;
import java.util.List;

public class PersistentArray<E> {

    public static int depth = 3;
    public static int bit_dlya_rasc_ur = Node.bit_na_pu * depth;
    public static int mask = (int) Math.pow(2, Node.bit_na_pu) - 1;

    public Node<E> root;
    private int count = 0;


    public PersistentArray() {
        root = new Node<>();
        root.parent = null;
        createBranch(root, depth);
    }

    public void add(List<E> list) {

    }


    public void add(E element) {
        int level = bit_dlya_rasc_ur - Node.bit_na_pu;
        Node<E> node = root;
        while (level > 0) {
            int index = (count >> level) & mask;
            node = node.children.get(index);
            level -= Node.bit_na_pu;
        }
        int index = count & mask;

        if (node.data == null) {
            node.data = new ArrayList<>();
        }

        node.data.add(index, element);
        count++;
    }

    public void createBranch(Node<E> node, int depth) {
        node.createChildren();
        if (depth > 0) {
            createBranch(node.getChildren().get(0), --depth);
        }
    }

    public PersistentArray<E> append(E element) {
        return new PersistentArray<E>();
    }
}
