import java.util.*;

public class PersistentList<E> implements List<E> {

    public static int depth = 2;
    public static int bit_dlya_rasc_ur = Node.bit_na_pu * depth;
    public static int mask = (int) Math.pow(2, Node.bit_na_pu) - 1;

    public Node<Data<E>> root;
    private int count = 0;

    public Data<E> first;
    public Data<E> last;

    public PersistentList() {
        root = new Node<>();
        root.parent = null;
        createBranch(root, depth);
    }

    private Data<E> addFirst(E e) {
        Data<E> oldFirst = first;
        Data<E> newFirst = new Data<E>(e, oldFirst, null);
        first = newFirst;
        if (oldFirst == null) {
            last = newFirst;
        } else {
            oldFirst.prev = newFirst;
        }
        return newFirst;
    }

    private Data<E> addLast(E e) {
        Data<E> oldLast = last;
        Data<E> newLast = new Data<E>(e, null, oldLast);
        last = newLast;
        if (oldLast == null) {
            first = newLast;
        } else {
            oldLast.next = newLast;
        }
        return newLast;
    }

    @Override
    public boolean add(E element) {
        int level = bit_dlya_rasc_ur - Node.bit_na_pu;
        Node<Data<E>> node = root;

        while (level > 0) {
            int index = (count >> level) & mask;
            if (node.children.size() - 1 != index) {
                node.createChildren();
            }
            node = node.children.get(index);
            level -= Node.bit_na_pu;
        }

        int index = count & mask;

        if (node.data == null) {
            node.data = new ArrayList<>();
        }

        node.data.add(index, addLast(element));
        count++;
        return true;
    }


    @Override
    public E get(int index) {
        if (index == 0) {
            return first.data;
        } else if (index == count) {
            return last.data;
        } else if ((count / 2) > index) {
            Data<E> currentData = first;
            for (int i = 0; i < index; i++) {
                currentData = currentData.getNext();
            }
            return currentData.data;
        } else {
            Data<E> currentData = last;
            for (int i = count; i > index; i--) {
                currentData = currentData.getPrev();
            }
            return currentData.data;
        }
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean isEmpty() {
        return count <= 0;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        Object[] objects = new Object[count];
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

    public void createBranch(Node<Data<E>> node, int depth) {
        node.createChildren();
        if (depth > 0) {
            createBranch(node.getChildren().get(0), --depth);
        }
    }

    private static class Data<E> {
        public E data;
        public Data<E> next;
        public Data<E> prev;

        public Data(E data, Data<E> next, Data<E> prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }

        public Data<E> getNext() {
            return next;
        }

        public Data<E> getPrev() {
            return prev;
        }
    }
}
