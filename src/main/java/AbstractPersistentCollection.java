import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public abstract class AbstractPersistentCollection<E> {
    public final int depth;
    public final int bit_dlya_rasc_ur;
    public final int mask;
    public final int maxSize;
    public final int bit_na_pu;
    public final int width;

    protected final Stack<Head<E>> undo = new Stack<>();
    protected final Stack<Head<E>> redo = new Stack<>();

    public AbstractPersistentCollection() {
        this(6, 5);
    }

    public AbstractPersistentCollection(int maxSize) {
        this((int)Math.ceil(log(maxSize, (int)Math.pow(2, 5))), 5);
    }

    public AbstractPersistentCollection(int depth, int bit_na_pu) {
        this.depth = depth;
        this.bit_na_pu = bit_na_pu;

        bit_dlya_rasc_ur = bit_na_pu * depth;
        mask = (int) Math.pow(2, bit_na_pu) - 1;
        maxSize = (int) Math.pow(2, bit_dlya_rasc_ur);

        width = (int) Math.pow(2, bit_na_pu);

        Head<E> head = new Head<>();
        undo.push(head);
        redo.clear();
    }



    public void undo() {
        if (!undo.empty()) {
            redo.push(undo.pop());
        }
    }

    public void redo() {
        if (!redo.empty()) {
            undo.push(redo.pop());
        }
    }

    public static double log(int N, int newBase)
    {

        // calculate log2 N indirectly
        // using log() method

        return (Math.log(N) / Math.log(newBase));
    }

    public String drawGraph() {
        return getCurrentHead().root.drawGraph();
    }

    protected Node<E> getLeaf(Head<E> head, int index)
    {
        if (index >= head.size)
            throw new IndexOutOfBoundsException();

        int level = bit_dlya_rasc_ur - bit_na_pu;
        Node<E> node = head.root;

        while (level > 0) {
            int tempIndex = (index >> level) & mask;
            node = node.child.get(tempIndex);
            level -= bit_na_pu;
        }

        return node;
    }

    public int calcUniqueLeafs()
    {
        LinkedList<Node<E>> list = new LinkedList<>();
        calcUniqueLeafs(list, undo);
        calcUniqueLeafs(list, redo);

        return list.size();
    }



    private void calcUniqueLeafs(LinkedList<Node<E>> list, Stack<Head<E>> undo1) {
        for (Head<E> head : undo1)
        {
            for (int i=0; i<head.size; i++)
            {
                Node<E> leaf = getLeaf(head, i);
                if (!list.contains(leaf))
                    list.add(leaf);
            }
        }

    }

    public int size(Head<E> head) {
        return head.size;
    }

    protected Head<E> getCurrentHead() {
        return this.undo.peek();
    }

}
