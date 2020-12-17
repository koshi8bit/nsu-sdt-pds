import java.util.*;

public class PersistentLinkedList<E> extends AbstractPersistentCollection<PLLE<E>> implements List<E>{


    public PersistentLinkedList() {
        super();
    }

    public PersistentLinkedList(int maxSize) {
        super(maxSize);
    }

    public PersistentLinkedList(int depth, int bit_na_pu) {
        super(depth, bit_na_pu);
    }

    public PersistentLinkedList(PersistentLinkedList<E> other) {
        super(other.depth, other.bit_na_pu);
        this.undo.addAll(other.undo);
        this.redo.addAll(other.redo);
    }

    @Override
    public int size() {
        return size(getCurrentHead());
    }

    @Override
    public boolean isEmpty() {
        return getCurrentHead().size <= 0;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        //TODO
        return null;
    }

    private Object[] toArray(Head<PLLE<E>> head) {
        Object[] objects = new Object[head.size];
        for (int i = 0; i < objects.length; i++) {
            objects[i] = this.get(head, i);
        }
        return objects;
    }

    @Override
    public Object[] toArray() {
        return toArray(getCurrentHead());
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(E e) {
        Head<PLLE<E>> newHead = new Head<>(getCurrentHead());
        undo.push(newHead);
        redo.clear();

        return add(newHead, e);
    }

    private boolean add(Head<PLLE<E>> head, E newValue)
    {
        if (head.size+1 >= maxSize) {
            return false;
        }

        head.size += 1;

        Node<PLLE<E>> currentNode = head.root;
        int level = bit_na_pu * (depth - 1);

        //System.out.print(newElement + "   ");
        while (level > 0)
        {
            int index = ((head.size - 1) >> level) & mask;
            //System.out.print(index);
            Node<PLLE<E>> tmp, newNode;

            if (currentNode.child == null)
            {
                currentNode.child = new LinkedList<>();
                newNode = new Node<>();
                currentNode.child.add(newNode);
            }
            else
            {
                if (index == currentNode.child.size())
                {
                    newNode = new Node<>();
                    currentNode.child.add(newNode);
                }
                else
                {
                    tmp = currentNode.child.get(index);
                    newNode = new Node<>(tmp);
                    currentNode.child.set(index, newNode);
                }
            }

            currentNode = newNode;
            level -= bit_na_pu;
        }

        if (currentNode.value == null)
            currentNode.value = new ArrayList<>();

        currentNode.value.add(new PLLE<>(newValue));
        //System.out.println();

        return true;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    private E get(Head<PLLE<E>> head, int index)
    {
        if (!((index < head.size) && (index>=0))) {
            throw new IndexOutOfBoundsException();
        }
        return getLeaf(head, index).value.get(index & mask).value;
    }

    @Override
    public E get(int index) {
        return get(getCurrentHead(), index);
    }

    @Override
    public E set(int index, E element) {
        return null;
    }

    @Override
    public void add(int index, E element) {

    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }
}
