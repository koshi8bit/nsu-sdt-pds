import javafx.util.Pair;

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

    ////////////
//    @Override
//    public void add(int index, E value)
//    {
//        if (!isIndexValid(index)) {
//            throw new IndexOutOfBoundsException();
//        }
//
//        Head<E> oldHead = getCurrentHead();
//
//        Pair<Node<E>, Integer> copedNodeP = copyLeafInsert(oldHead, index);
//        Head<E> newHead = getCurrentHead();
//
//        int leafIndex = copedNodeP.getValue();
//        Node<E> copedNode = copedNodeP.getKey();
//
//        copedNode.value.set(leafIndex, value);
//
//        for (int i = index; i<oldHead.size; i++)
//        {
//            add(newHead, get(oldHead, i));
//        }
//
//    }
//
    ///////////



    @Override
    public boolean add(E e) {
        if (isFull()) {
            return false;
        }
        Head<PLLE<E>> newHead = new Head<>(getCurrentHead());
        undo.push(newHead);
        redo.clear();

        return add(newHead, e);
    }

    private boolean add(Head<PLLE<E>> head, E newValue)
    {
        PLLE<E> element = new PLLE<>(newValue);
        add2(head).value.add(element);
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
