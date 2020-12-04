import java.util.*;

public class PersistentList<E> implements List<E> {

    public static int depth = 3;
    public static int bit_dlya_rasc_ur = Node.bit_na_pu * depth;
    public static int mask = (int) Math.pow(2, Node.bit_na_pu) - 1;

    public Node<Data<E>> root;
    private int count = 0;

    public PersistentList() {
        root = new Node<>();
        root.parent = null;
        createBranch(root, depth);
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

        node.data.add(index, new Data<E>(element));
        count++;
        return true;
    }


    @Override
    public E get(int index) {
        int level = bit_dlya_rasc_ur - Node.bit_na_pu;
        Node<Data<E>> node = root;

        while (level > 0) {
            int tempIndex = (index >> level) & mask;
            node = node.children.get(tempIndex);
            level -= Node.bit_na_pu;
        }

        return node.data.get(index & mask).data;
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

    private class Data<E> {
        public E data;
        public Data<E> next;
        public Data<E> pref;

        public Data(E data) {
            this.data = data;
        }
    }
}
