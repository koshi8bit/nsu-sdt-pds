import javafx.util.Pair;

import java.util.*;

public class PersistentArray<E> extends AbstractPersistentCollection<E> {

    private Stack<Head<E>> undo = new Stack<>();
    private Stack<Head<E>> redo = new Stack<>();

    public PersistentArray() {
        this(6, 5);
    }

    public PersistentArray(int maxSize) {
        this((int)Math.ceil(log(maxSize, (int)Math.pow(2, 5))), 5);
    }

    public PersistentArray(int depth, int bit_na_pu) {
        super(depth, bit_na_pu);
        Head<E> head = new Head<>();
        undo.push(head);
    }

    public PersistentArray(PersistentArray<E> other)
    {
        this(other.depth, other.bit_na_pu);

        this.undo.addAll(other.undo);
        this.redo.addAll(other.redo);
    }


    public int getVersionCount()
    {
        return undo.size() + redo.size();
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

        //E result = get(getCurrentHead().size - 1);

        Head<E> newHead = new Head<>(getCurrentHead(), -1);
        undo.push(newHead);
        redo.clear();
        LinkedList<Pair<Node<E>, Integer>> path = new LinkedList<>();
        path.add(new Pair<>(newHead.root, 0));
        int level = bit_na_pu * (depth - 1);

        //System.out.print("index=" + getCurrentHead().size + "   ");
        while (level > 0)
        {
            int index = (newHead.size >> level) & mask;
            //System.out.print(index);
            Node<E> tmp, newNode;

            tmp = path.getLast().getKey().child.get(index);
            newNode = new Node<>(tmp);
            path.getLast().getKey().child.set(index, newNode);

            path.add(new Pair<>(newNode, index));
            level -= bit_na_pu;
        }

        int index = newHead.size & mask;
        //System.out.println(index);

        E result = path.getLast().getKey().value.remove(index);

        for (int i=path.size()-1; i>=1; i--)
        {
            Pair<Node<E>, Integer> elem = path.get(i);
            if (elem.getKey().isEmpty())
            {
                path.get(i-1).getKey().child.set(elem.getValue(), null);
            }
            else
                break;
        }

        return result;
    }

//    public boolean add(int index, E value)
//    {
//        return assoc(index, value);
//    }




    private void printLeafs(Head<E> head)
    {
        for (int i=0; i<head.size; i++)
        {
            System.out.print(i + ":" + String.format("%09d", getLeaf(head, i).hashCode()) + "; ");
        }
        System.out.println();
    }

    private String toString(Head<E> head) {
        return "size: " + size(head) + "; unique leafs: "
                + calcUniqueLeafs() + "; array: " +  Arrays.toString(toArray(head));
    }

    @Override
    public String toString() {
        return toString(getCurrentHead());
    }

    @Override
    public void add(int index, E value)
    {
        if (index >= getCurrentHead().size) {
            throw new IndexOutOfBoundsException();
        }

        Head<E> oldHead = getCurrentHead();
        //printLeafs(oldHead);
        System.out.println(oldHead.root.drawGraph());
        System.out.println("---");

        Pair<Node<E>, Integer> copedNodeP = copyLeaf(oldHead, index, true);
        Head<E> newHead = getCurrentHead();
        //printLeafs(newHead);
        System.out.println(newHead.root.drawGraph());

        int leafIndex = copedNodeP.getValue();
        Node<E> copedNode = copedNodeP.getKey();

        copedNode.value.set(leafIndex, value);
//        System.out.println(this);
//        int count = width - leafIndex - 1;
//        for (int i=0; i<count; i++) {
//            newHead.size--;
//            copedNode.value.remove(copedNode.value.size() - 1);
//        }

        for (int i = index; i<oldHead.size; i++)
        {
            add(newHead, get(oldHead, i));
        }

        //printLeafs(newHead);

    }

    private Pair<Node<E>, Integer> copyLeaf(Head<E> head, int index, boolean insert)
    {
        if (getCurrentHead().size == maxSize) {
            throw new IllegalStateException("array is full");
            //return null;
        }

        Head<E> newHead = new Head<>(head, 0);
        if (insert)
            newHead.size = index+1;
        undo.push(newHead);
        redo.clear();
        Node<E> currentNode = newHead.root;
        int level = bit_na_pu * (depth - 1);

        //System.out.print(newElement + "   ");
        while (level > 0)
        {
            int widthIndex = (index >> level) & mask;
            int widthIndexNext = (index >> (level - bit_na_pu)) & mask;
            //System.out.print(index);
            Node<E> tmp, newNode;

            tmp = currentNode.child.get(widthIndex);
            if (insert)
                newNode = new Node<>(tmp, widthIndexNext);
            else
                newNode = new Node<>(tmp);
            currentNode.child.set(widthIndex, newNode);

            currentNode = newNode;
            level -= bit_na_pu;
        }

        return new Pair<>(currentNode, index & mask);
    }

    public PersistentArray<E> conj(E newElement)
    {
        PersistentArray<E> result = new PersistentArray<>(this);
        result.add(newElement);
        return result;
    }


    private boolean add(Head<E> head, E newElement)
    {
        if (head.size == maxSize) {
            return false;
        }

        head.size += 1;

        Node<E> currentNode = head.root;
        int level = bit_na_pu * (depth - 1);

        //System.out.print(newElement + "   ");
        while (level > 0)
        {
            int index = ((head.size - 1) >> level) & mask;
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
            level -= bit_na_pu;
        }

        if (currentNode.value == null)
            currentNode.value = new ArrayList<>();

        currentNode.value.add(newElement);
        //System.out.println();

        return true;
    }

    @Override
    public boolean add(E newElement) {
        Head<E> newHead = new Head<>(getCurrentHead(), 0);
        undo.push(newHead);
        redo.clear();

        return add(newHead, newElement);
    }

    private Node<E> getLeaf(Head<E> head, int index)
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

    private E get(Head<E> head, int index)
    {
        if (index >= head.size) {
            throw new IndexOutOfBoundsException();
        }
        return getLeaf(head, index).value.get(index & mask);
    }

    @Override
    public E get(int index) {
        return get(getCurrentHead(), index);
    }

    public Head<E> getCurrentHead() {
        return this.undo.peek();
    }


    /////////////////////////////////////////////////////

    public int size(Head<E> head) {
        return head.size;
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
        return new PersistentArrayIterator<E>();
    }

    private Object[] toArray(Head<E> head) {
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

    public PersistentArray<E> assoc(int index, E element)
    {
        PersistentArray<E> result = new PersistentArray<>(this);
        result.set(index, element);
        return result;
    }

    @Override
    public E set(int index, E element) {
        Pair<Node<E>, Integer> pair = copyLeaf(getCurrentHead(), index, false);
        pair.getKey().value.set(pair.getValue(), element);
        return get(index);
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
        public E next()
        {
            //if (index == getC)
            return (E) get(index++); // TODO WTF
        }

        @Override
        public void remove() {

        }
    }

}
