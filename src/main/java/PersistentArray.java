import javax.naming.SizeLimitExceededException;
import java.util.*;

public class PersistentArray<E> extends AbstractPersistentCollection<E> {

    private Stack<Head<E>> undo = new Stack<>();
    private Stack<Head<E>> redo = new Stack<>();

    public PersistentArray(int depth) {
        super(depth);
        Head<E> head = new Head<>();
        undo.push(head);
    }

    @Override
    public void undo() {
        if (!undo.empty()) {
            redo.push(undo.pop());
        }
    }

    @Override
    public void redo() {
        if (!redo.empty()) {
            undo.push(redo.pop());
        }
    }

    public E pop() throws NoSuchElementException
    {
        if (getCurrentHead().size == 0)
            throw new NoSuchElementException("Array is empty");

        E result = get(getCurrentHead().size - 1);
        Head<E> newHead = new Head<>(getCurrentHead(), -1);
        undo.push(newHead);
        redo.clear();
        Node<E> currentNode = newHead.root;
        int level = Node.bit_na_pu * (depth - 1);

        System.out.print("[" + getCurrentHead().size + "]   ");
        while (level > 0)
        {
            int index = (newHead.size >> level) & mask;
            System.out.print(index);
            Node<E> tmp, newNode;

            tmp = currentNode.child.get(index);
            newNode = new Node<>(tmp);
            currentNode.child.set(index, newNode);


            currentNode = newNode;
            level -= Node.bit_na_pu;
        }

        int index = newHead.size & mask;
        currentNode.value.remove(index);
        System.out.println(index);


        return result;
    }

    public boolean conj(E newElement)
    {
        if (getCurrentHead().size == maxSize) {
            return false;
        }

        Head<E> newHead = new Head<>(getCurrentHead(), +1);
        undo.push(newHead);
        redo.clear();
        Node<E> currentNode = newHead.root;
        int level = Node.bit_na_pu * (depth - 1);

        //System.out.print(newElement + "   ");
        while (level > 0)
        {
            int index = ((newHead.size - 1) >> level) & mask;
            //System.out.print(index);
            Node<E> tmp, newNode;

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
            level -= Node.bit_na_pu;
        }

        if (currentNode.value == null)
            currentNode.value = new ArrayList<>();

        currentNode.value.add(newElement);
        //System.out.println();

        return true;
    }

    @Override
    public boolean add(E newElement) {
        return conj(newElement);
    }


    @Override
    public E get(int index) {
        int level = bit_dlya_rasc_ur - Node.bit_na_pu;
        Node<E> node = getCurrentHead().root;

        while (level > 0) {
            int tempIndex = (index >> level) & mask;
            node = node.child.get(tempIndex);
            level -= Node.bit_na_pu;
        }

        return node.value.get(index & mask);
    }

    private Head<E> getCurrentHead() {
        return this.undo.peek();
    }


    /////////////////////////////////////////////////////

    @Override
    public int size() {
        return getCurrentHead().size;
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
        return new PersistentArrayIterator<E>();
    }

    @Override
    public Object[] toArray() {
        Object[] objects = new Object[getCurrentHead().size];
        for (int i = 0; i < objects.length; i++) {
            objects[i] = this.get(i);
        }
        return objects;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] a) {
        for (int i = 0; i < a.length; i++) {
            a[i] = (T) this.get(i);
        }
        return a;
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
        undo.clear();
        redo.clear();
        Head<E> head = new Head<>();
        undo.push(head);
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

    public class PersistentArrayIterator<E> implements java.util.Iterator<E> {

        int index = 0;

        @Override
        public boolean hasNext() {
            return index < size();
        }

        @Override
        public E next() {
            return (E) get(index++); // TODO WTF
        }

        @Override
        public void remove() {

        }
    }

}
