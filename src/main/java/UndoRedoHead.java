import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public abstract class UndoRedoHead<E> extends AbstractPersistentCollection<E, Head<E>> implements List<E> {

    public UndoRedoHead() {
        this(6, 5);
    }

    public UndoRedoHead(int maxSize) {
        this((int)Math.ceil(log(maxSize, (int)Math.pow(2, 5))), 5);
    }

    public UndoRedoHead(int depth, int bit_na_pu) {
        super(depth, bit_na_pu);
        Head<E> head = new Head<>();
        undo.push(head);
        redo.clear();
    }

    public UndoRedoHead(PersistentLinkedList<E> other)
    {
        this(other.depth, other.bit_na_pu);

        this.undo.addAll(other.undo);
        this.redo.addAll(other.redo);
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
