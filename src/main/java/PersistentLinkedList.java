import javafx.util.Pair;

import java.util.*;

public class PersistentLinkedList<E> extends AbstractPersistentCollection<PLLE<E>> implements List<E>{

    protected final Stack<HeadList<PLLE<E>>> redo = new Stack<>();
    protected final Stack<HeadList<PLLE<E>>> undo = new Stack<>();


    public PersistentLinkedList() {
        this(6, 5);
    }

    public PersistentLinkedList(int maxSize) {
        this((int)Math.ceil(log(maxSize, (int)Math.pow(2, 5))), 5);
    }

    public PersistentLinkedList(int depth, int bit_na_pu) {

        super(depth, bit_na_pu);

        HeadList<PLLE<E>> head = new HeadList<>();
        undo.push(head);
        redo.clear();
    }

    public PersistentLinkedList(PersistentLinkedList<E> other) {
        super(other.depth, other.bit_na_pu);
        this.undo.addAll(other.undo);
        this.redo.addAll(other.redo);
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

    public int getUniqueLeafsSize()
    {
        LinkedList<Node<PLLE<E>>> list = new LinkedList<>();
        getUniqueLeafsSize(list, undo);
        getUniqueLeafsSize(list, redo);

        return list.size();
    }

    private void getUniqueLeafsSize(LinkedList<Node<PLLE<E>>> list, Stack<HeadList<PLLE<E>>> undo1) {
        for (HeadList<PLLE<E>> head : undo1)
        {
            for (int i=0; i<head.size; i++)
            {
                Node<PLLE<E>> leaf = getLeaf(head, i).getKey();
                if (!list.contains(leaf))
                    list.add(leaf);
            }
        }

    }


    protected HeadList<PLLE<E>> getCurrentHead() {
        return this.undo.peek();
    }

    public int size(HeadList<PLLE<E>> head) {
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
        return new PersistentListIterator<>();
    }

    public Iterator<E> iterator(HeadList<PLLE<E>> head) {
        return new PersistentListIterator<>(head);
    }

    @Override
    public String toString() {
        return toString(getCurrentHead());
    }

    private String toString(HeadList<PLLE<E>>  head) {
        return Arrays.toString(toArray(head));
    }

    @Override
    public Object[] toArray() {
        return toArray(getCurrentHead());
    }

    private Object[] toArray(HeadList<PLLE<E>> head) {
        Object[] objects = new Object[head.size];
        Iterator<E> iterator = iterator(head);
        for (int i = 0; i < objects.length; i++) {
            objects[i] = iterator.next();
        }
        return objects;
    }



    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }


    public void checkIndex(int index)
    {
        checkIndex(index, getCurrentHead());
    }

    public void checkIndex(int index, HeadList<PLLE<E>> head)
    {
        //if (!((index>=0) && (index<head.size)))
        if (!((index>=0) && (index<head.sizeTree)))
            throw new IndexOutOfBoundsException("Invalid index");
    }

    public boolean isFull()
    {
        return isFull(getCurrentHead(), 0);
    }

    public boolean isFull(int extra)
    {
        return isFull(getCurrentHead(), extra);
    }

    public boolean isFull(HeadList<PLLE<E>> head)
    {
        return isFull(head, 0);
    }

    public boolean isFull(HeadList<PLLE<E>> head, int extra)
    {
        return head.sizeTree + extra > maxSize;
    }

    @Override
    public void add(int index, E value) {
        if (isFull()) {
            throw new IllegalStateException("array is full");
        }


        HeadList<PLLE<E>> prevHead = getCurrentHead();
        HeadList<PLLE<E>> newHead = null;

        checkIndex(index, prevHead);

        int indexBefore = -1;
        int indexAfter = -1;

        if (prevHead.sizeTree == 0) { //todo size or sizeTree
            newHead = new HeadList<>(prevHead);
        } else {
            if (index != 0) {
                indexBefore = getTreeIndex(index - 1);
                CopyResult<PLLE<E>, HeadList<PLLE<E>>> before = copyLeaf(prevHead, indexBefore);
                PLLE<E> beforeE = new PLLE<>(before.leaf.value.get(before.leafInnerIndex));
                beforeE.next = prevHead.sizeTree;
                before.leaf.value.set(before.leafInnerIndex, beforeE);
                newHead = before.head;
            }

            if (index != prevHead.size - 1) {
                indexAfter = getTreeIndex(index);
                HeadList<PLLE<E>> prevHead2 = newHead != null ? newHead : prevHead;
                CopyResult<PLLE<E>, HeadList<PLLE<E>>> after = copyLeaf(prevHead2, indexAfter);
                PLLE<E> afterE = new PLLE<>(after.leaf.value.get(after.leafInnerIndex));
                afterE.prev = prevHead.sizeTree;
                after.leaf.value.set(after.leafInnerIndex, afterE);
                newHead = after.head;
            }


        }

        undo.push(newHead);
        redo.clear();

        PLLE<E> element = new PLLE<>(value, indexBefore, indexAfter);

        if (indexBefore == -1)
        {
            newHead.first = newHead.sizeTree;
        }

        if (indexAfter == -1)
        {
            newHead.last = newHead.sizeTree;
        }

        findLeafForNewElement(newHead).value.add(element);


    }



    private int getTreeIndex(int listIndex)
    {
        return getTreeIndex(getCurrentHead(), listIndex);
    }

    private int getTreeIndex(HeadList<PLLE<E>> head, int listIndex)
    {
        //O(N) 100%

        //todo need to test
        checkIndex(listIndex, head);

        if (head.size == 0)
            return -1;

        int result = head.first;

//        Pair<Node<PLLE<E>>, Integer> pair = getLeaf(head, head.first);
//        Node<PLLE<E>> current = pair.getKey();
        PLLE<E> current;


        for (int i=0; i<listIndex; i++)
        {
            Pair<Node<PLLE<E>>, Integer> pair = getLeaf(head, result);
            current = pair.getKey().value.get(pair.getValue());
            result = current.next;
//
//            result = pair.getKey().value.get(pair.getValue()).next;
//            pair = getLeaf(head, result);
//            current = getLeaf(head, current.).getKey()
        }

        return result;

    }

    @Override
    public boolean add(E newValue) {
        if (isFull()) {
            return false;
        }

        PLLE<E> element;

        HeadList<PLLE<E>> prevHead = getCurrentHead();
        HeadList<PLLE<E>> newHead;

        if (getCurrentHead().size == 0)
        {
            newHead = new HeadList<>(prevHead);
            element = new PLLE<>(newValue, -1, -1);
            newHead.first = newHead.sizeTree;
        }
        else
        {
            element = new PLLE<>(newValue, prevHead.last, -1);
            CopyResult<PLLE<E>, HeadList<PLLE<E>>> tmp
                    = copyLeaf(prevHead, prevHead.last);
            newHead = tmp.head;
            PLLE<E> prev = new PLLE<>(tmp.leaf.value.get(tmp.leafInnerIndex));
            prev.next = newHead.sizeTree;
            tmp.leaf.value.set(tmp.leafInnerIndex, prev);
        }
        newHead.last = newHead.sizeTree;

        undo.push(newHead);
        redo.clear();

        findLeafForNewElement(newHead).value.add(element);

        return true;
    }



    protected Node<PLLE<E>> findLeafForNewElement(HeadList<PLLE<E>> head)
    {
        if (isFull(head)) {
            throw new IndexOutOfBoundsException("collection is full");
        }

        head.size += 1;
        head.sizeTree += 1;

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

        //currentNode.value.add(new PLLE<>(newValue));
        //System.out.println();
        return currentNode;
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
    public E get(int index) {
        return get(getCurrentHead(), index);
    }

    private PLLE<E> getPLLE(HeadList<PLLE<E>> head, int index)
    {
        //O(log(width, N)) 100%
        checkIndex(index);

        int treeIndex = getTreeIndex(index);
        if (treeIndex == -1)
            throw new IndexOutOfBoundsException("getTreeIndex == -1");

        return getLeaf(head, treeIndex).getKey().value.get(treeIndex & mask);
    }

    private E get(HeadList<PLLE<E>> head, int index)
    {
        return getPLLE(head, index).value;
    }

    protected Pair<Node<PLLE<E>>, Integer> getLeaf(HeadList<PLLE<E>> head, int index)
    {
        //O(log(width, N)) 100%
        checkIndex(index, head);

//        if (index >= head.size)
//            throw new IndexOutOfBoundsException();

        int level = bit_dlya_rasc_ur - bit_na_pu;
        Node<PLLE<E>> node = head.root;

        while (level > 0) {
            int tempIndex = (index >> level) & mask;
            node = node.child.get(tempIndex);
            level -= bit_na_pu;
        }

        return new Pair<>(node, index & mask);
    }

    private CopyResult<PLLE<E>, HeadList<PLLE<E>>> copyLeaf(HeadList<PLLE<E>> head, int index)
    {
        //O(log(width, N)) 100%
        if (isFull()) {
            throw new IllegalStateException("array is full");
            //return null;
        }
        checkIndex(index, head);

        HeadList<PLLE<E>> newHead = new HeadList<>(head, 0);
        Node<PLLE<E>> currentNode = newHead.root;
        int level = bit_na_pu * (depth - 1);

        while (level > 0)
        {
            int widthIndex = (index >> level) & mask;
            Node<PLLE<E>> tmp, newNode;

            tmp = currentNode.child.get(widthIndex);
            newNode = new Node<>(tmp);
            currentNode.child.set(widthIndex, newNode);

            currentNode = newNode;
            level -= bit_na_pu;
        }

        return new CopyResult<>(currentNode, index & mask, newHead);
    }

    public int getVersionCount()
    {
        return undo.size() + redo.size();
    }

    public String drawGraph() {
        return drawGraph(true);
    }

    public String drawGraph(boolean printAllData) {

        return (printAllData?(toString() + "\nunique:" + getUniqueLeafsSize() + "; ver:" + getVersionCount()+ "\n"):"")
                + getCurrentHead() + "\n" + getCurrentHead().root.drawGraph() + "\n";
    }

//    public String drawGraphClear() {
//        return getCurrentHead() + "\n" + getCurrentHead().root.drawGraph() + "\n";
//    }




    @Override
    public E set(int index, E element) {
        return null;
    }


    @Override
    public E remove(int index) {
        return remove(getCurrentHead(), index);
    }

    private E remove(HeadList<PLLE<E>> head, int index)
    {

        if (isFull(2)) {
            throw new IllegalStateException("array is full");
        }

        HeadList<PLLE<E>> prevHead = head;
        HeadList<PLLE<E>> newHead = null;

        checkIndex(index, prevHead);

        E result = get(index);

        if (prevHead.size == 1)
        {
            undo.push(new HeadList<>());
            redo.clear();
            return result;
        }


        PLLE<E> mid = getPLLE(prevHead, index);
        //System.out.println(drawGraph(false));

        if(mid.prev == -1)
        {
            int nextIndex = index+1;
            CopyResult<PLLE<E>, HeadList<PLLE<E>>> nextLeaf
                    = copyLeaf(prevHead, nextIndex);
            newHead = nextLeaf.head;

            PLLE<E> nextPLLE = getPLLE(newHead, nextIndex);
            PLLE<E> newNextPLLE = new PLLE<>(nextPLLE);

            nextLeaf.leaf.value.set(nextLeaf.leafInnerIndex, newNextPLLE);
            newNextPLLE.prev = -1;

            newHead.first = getTreeIndex(nextIndex);
        }

        if (mid.next == -1)
        {
            int prevIndex = index-1;
            CopyResult<PLLE<E>, HeadList<PLLE<E>>> prevLeaf
                    = copyLeaf(newHead==null?prevHead:newHead, prevIndex);
            newHead = prevLeaf.head;

            PLLE<E> prevPLLE = getPLLE(newHead, prevIndex);
            PLLE<E> newPrevPLLE = new PLLE<>(prevPLLE);

            prevLeaf.leaf.value.set(prevLeaf.leafInnerIndex, newPrevPLLE);
            newPrevPLLE.next = -1;

            newHead.last = getTreeIndex(prevIndex);
        }






//        if (index != 0) {
//            indexBefore = getTreeIndex(index - 1);
//            CopyResult<PLLE<E>, HeadList<PLLE<E>>> beforeLeaf = copyLeaf(prevHead, indexBefore);
//            PLLE<E> beforePLLE = new PLLE<>(beforeLeaf.leaf.value.get(beforeLeaf.leafInnerIndex));
//            beforePLLE.next = prevHead.sizeTree;
//            beforeLeaf.leaf.value.set(beforeLeaf.leafInnerIndex, beforePLLE);
//            newHead = beforeLeaf.head;
//        }
//
//        if (index != prevHead.size - 1) {
//            indexAfter = getTreeIndex(index);
//            HeadList<PLLE<E>> prevHead2 = newHead != null ? newHead : prevHead;
//            CopyResult<PLLE<E>, HeadList<PLLE<E>>> afterLeaf = copyLeaf(prevHead2, indexAfter);
//            PLLE<E> afterPLLE = new PLLE<>(afterLeaf.leaf.value.get(afterLeaf.leafInnerIndex));
//            afterPLLE.prev = prevHead.sizeTree;
//            afterLeaf.leaf.value.set(afterLeaf.leafInnerIndex, afterPLLE);
//            newHead = afterLeaf.head;
//        }
//
//
//
//
//
//        PLLE<E> element = new PLLE<>(value, indexBefore, indexAfter);
//
//        if (indexBefore == -1)
//        {
//            newHead.first = newHead.sizeTree;
//        }
//
//        if (indexAfter == -1)
//        {
//            newHead.last = newHead.sizeTree;
//        }
//
//        findLeafForNewElement(newHead).value.add(element);

        //assert (newHead != null);
        //assert newHead != null;
        newHead.size--;
        undo.push(newHead);
        redo.clear();

        //System.out.println(drawGraph(false));


        return result;
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

    public class PersistentListIterator<E2> implements java.util.Iterator<E2> {

        PLLE<E> current;
        HeadList<PLLE<E>> head;
        int i=0;

        public PersistentListIterator(HeadList<PLLE<E>> head) {
            this.head = head;
            Pair<Node<PLLE<E>>, Integer> tmp = getLeaf(head, head.first);
            current = tmp.getKey().value.get(tmp.getValue());
        }

        public PersistentListIterator() {
            this(getCurrentHead());
        }

        @Override
        public boolean hasNext() {
            return head.size > i;
        }

        @Override
        public E2 next()
        {
            //O(log(width, N)) 100%
            E2 result = (E2) current.value; // TODO WTF cast err

            i++;
            if (!hasNext())
                return result;

            Pair<Node<PLLE<E>>, Integer> tmp = getLeaf(head, current.next);
            current = tmp.getKey().value.get(tmp.getValue());
            return result;
        }

        @Override
        public void remove() {

        }
    }
}
