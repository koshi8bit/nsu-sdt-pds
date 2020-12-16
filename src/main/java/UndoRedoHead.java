import java.util.LinkedList;
import java.util.Stack;

public abstract class UndoRedoHead<E> extends AbstractPersistentCollection<E, Head<E>> {
    public UndoRedoHead(int depth, int bit_na_pu) {
        super(depth, bit_na_pu);
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
}
