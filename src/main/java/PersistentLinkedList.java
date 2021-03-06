import javafx.util.Pair;

import java.util.*;

public class PersistentLinkedList<E> extends AbstractPersistentCollection<PLLE<E>> implements List<E>{

    private PersistentLinkedList<PersistentLinkedList<?>> parent;
    private Stack<PersistentLinkedList<?>> insertedUndo = new Stack<>();
    private Stack<PersistentLinkedList<?>> insertedRedo = new Stack<>();
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
        if (!insertedUndo.empty()) {
            insertedUndo.peek().undo();
            insertedRedo.push(insertedUndo.pop());
        } else {
            if (!undo.empty()) {
                redo.push(undo.pop());
            }
        }
    }

    public void redo() {
        if (!insertedRedo.empty()) {
            insertedRedo.peek().redo();
            insertedUndo.push(insertedRedo.pop());
        } else {
            if (!redo.empty()) {
                undo.push(redo.pop());
            }
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

    private void tryParentUndo(E value) {
        if (value instanceof PersistentLinkedList) {
            ((PersistentLinkedList) value).parent = this;
        }

        if (parent != null) {
            parent.onEvent(this);
        }
    }

    private void onEvent(PersistentLinkedList<?> persistentLinkedList) {
        insertedUndo.push(persistentLinkedList);
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
        if(head.size == 0)
            return "[]";
        else
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


    public void checkListIndex(int index)
    {
        checkListIndex(index, getCurrentHead());
    }

    public void checkListIndex(int index, HeadList<PLLE<E>> head)
    {
        if (!((index>=0) && (index<head.size)))
            throw new IndexOutOfBoundsException("Invalid index: " + index);
    }

    public void checkTreeIndex(int index, HeadList<PLLE<E>> head)
    {
        if (!((index>=0) && (index<head.sizeTree)))
            throw new IndexOutOfBoundsException("Invalid index: " + index);
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
        return head.sizeTree + extra >= maxSize;
    }

    private Pair<Integer, Boolean> getNextIndex(HeadList<PLLE<E>> head)
    {
        if (head.deadList == null)
            return new Pair<>(head.sizeTree, false);

        if (head.deadList.size() == 0)
            return new Pair<>(head.sizeTree, false);

        head.deadList = new ArrayDeque<>(head.deadList);
        return new Pair<>(head.deadList.pop(), true);

    }

    @Override
    public boolean add(E newValue) {
        //O(2*log(width, N)) 100%
        if (isFull()) {
            return false;
        }

        PLLE<E> element = null;

        HeadList<PLLE<E>> prevHead = getCurrentHead();
        HeadList<PLLE<E>> newHead = null;

        Pair<Integer, Boolean> next = null;// = getNextIndex(prevHead);

        if (getCurrentHead().size == 0)
        {
            //newHead = new HeadList<>(prevHead);
            newHead = new HeadList<>(); //todo check may be prev line
            element = new PLLE<>(newValue, -1, -1);
            newHead.first = 0;
            newHead.last = 0;

            findLeafForNewElement(newHead).value.add(element);
        }
        else
        {
            element = new PLLE<>(newValue, prevHead.last, -1);
            CopyResult<PLLE<E>, HeadList<PLLE<E>>> tmp
                    = copyLeaf(prevHead, prevHead.last);
            newHead = tmp.head;
            next = getNextIndex(newHead);
            PLLE<E> last = new PLLE<>(tmp.leaf.value.get(tmp.leafInnerIndex));
            tmp.leaf.value.set(tmp.leafInnerIndex, last);

            if (!next.getValue()) {
                last.next = newHead.sizeTree;
                newHead.last = newHead.sizeTree;
            }
            else
            {
                last.next = next.getKey();
                PLLE<E> oldOne = new PLLE<>(getValueFromLeaf(newHead, next.getKey()));

                Pair<Node<PLLE<E>>, Integer>  oldLeaf = getLeaf(newHead, next.getKey());
                oldLeaf.getKey().value.set(oldLeaf.getValue(), oldOne);

                oldOne.value = newValue;
                oldOne.next = -1;
                oldOne.prev = prevHead.last;
                newHead.last = last.next;
                newHead.size++;
            }

            if (!next.getValue()) {
                findLeafForNewElement(newHead).value.add(element);
            }
        }

        undo.push(newHead);
        redo.clear();

        return true;
    }

    @Override
    public void add(int index, E value) {
        if (isFull()) {
            throw new IllegalStateException("array is full");
        }


        HeadList<PLLE<E>> prevHead = getCurrentHead();
        HeadList<PLLE<E>> newHead = null;

        checkListIndex(index, prevHead);

        int indexBefore = -1;
        PLLE<E> beforeE = null;

        int indexAfter = -1;
        PLLE<E> afterE = null;

        //int freeIndex = getNextIndex(prevHead).getKey();
        int freeIndex = prevHead.sizeTree;

        if (prevHead.size == 0) { //todo size or sizeTree: size чистить скопившийся мусор
            newHead = new HeadList<>(prevHead);
        } else {
            if (index != 0) {
                indexBefore = getTreeIndex(index - 1);
                CopyResult<PLLE<E>, HeadList<PLLE<E>>> before = copyLeaf(prevHead, indexBefore);
                beforeE = new PLLE<>(before.leaf.value.get(before.leafInnerIndex));
                //beforeE.next = prevHead.sizeTree;
                beforeE.next = freeIndex;
                before.leaf.value.set(before.leafInnerIndex, beforeE);
                newHead = before.head;
            }

            if (index != prevHead.size - 1) {
                indexAfter = getTreeIndex(index);
                HeadList<PLLE<E>> prevHead2 = newHead != null ? newHead : prevHead;
                CopyResult<PLLE<E>, HeadList<PLLE<E>>> after = copyLeaf(prevHead2, indexAfter);
                afterE = new PLLE<>(after.leaf.value.get(after.leafInnerIndex));
                //afterE.prev = prevHead.sizeTree;
                afterE.prev = freeIndex;
                after.leaf.value.set(after.leafInnerIndex, afterE);
                newHead = after.head;
            }
        }

        undo.push(newHead);
        redo.clear();
        tryParentUndo(value);

        PLLE<E> element = new PLLE<>(value, indexBefore, indexAfter);

        if (indexBefore == -1)
        {
            newHead.first = freeIndex;
        }

        if (indexAfter == -1)
        {
            newHead.last = freeIndex;
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
        checkListIndex(listIndex, head);

        if (head.size == 0)
            return -1;

        int result = head.first;

        PLLE<E> current;


        for (int i=0; i<listIndex; i++)
        {
            Pair<Node<PLLE<E>>, Integer> pair = getLeaf(head, result);
            current = pair.getKey().value.get(pair.getValue());
            result = current.next;
        }

        return result;

    }

    public PersistentLinkedList<E> conj(E newElement) {
        PersistentLinkedList<E> result = new PersistentLinkedList<>(this);
        result.add(newElement);
        return result;
    }





    protected Node<PLLE<E>> findLeafForNewElement(HeadList<PLLE<E>> head)
    {
        //O(log(width, N)) 100%
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
            int index = ((head.sizeTree - 1) >> level) & mask;
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
        HeadList<PLLE<E>> head = new HeadList<>();
        undo.push(head);
        redo.clear();
    }

    private PLLE<E> getValueFromLeaf(HeadList<PLLE<E>> head, int index)
    {
        return getLeaf(head, index).getKey().value.get(index & mask);
    }

    @Override
    public E get(int index) {
        return get(getCurrentHead(), index);
    }

    private PLLE<E> getPLLE(HeadList<PLLE<E>> head, int index)
    {
        //O(N) 100%
        checkListIndex(index);

        int treeIndex = getTreeIndex(index);
        if (treeIndex == -1)
            throw new IndexOutOfBoundsException("getTreeIndex == -1");

        return getLeaf(head, treeIndex).getKey().value.get(treeIndex & mask);
    }




    private E get(HeadList<PLLE<E>> head, int index)
    {
        if (index == 0)
            return getValueFromLeaf(head, head.first).value;

        if (index == head.size-1)
            return getValueFromLeaf(head, head.last).value;


        return getPLLE(head, index).value;
    }

    protected Pair<Node<PLLE<E>>, Integer> getLeaf(HeadList<PLLE<E>> head, int index)
    {
        //O(log(width, N)) 100%
        checkTreeIndex(index, head);

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
        checkTreeIndex(index, head);

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

    public PersistentLinkedList<E> assoc(int index, E element) {
        PersistentLinkedList<E> result = new PersistentLinkedList<>(this);
        result.set(index, element);
        return result;
    }

    @Override
    public E set(int index, E element) {
        return set(getCurrentHead(), index, element);
    }

    private E set(HeadList<PLLE<E>> prevHead, int index, E element) {

        E oldResult = get(index);

        checkListIndex(index, prevHead);

        CopyResult<PLLE<E>, HeadList<PLLE<E>>> copyResult
                = copyLeaf(prevHead, getTreeIndex(prevHead, index));

        HeadList<PLLE<E>> newHead = copyResult.head;

        PLLE<E> newNode = new PLLE<>(copyResult.leaf.value.get(copyResult.leafInnerIndex));
        newNode.value = element;

        copyResult.leaf.value.set(copyResult.leafInnerIndex, newNode);

        undo.push(newHead);
        redo.clear();
        tryParentUndo(element);

        return oldResult;
    }


    @Override
    public E remove(int index) {
        return remove(getCurrentHead(), index);
    }

    private E remove(HeadList<PLLE<E>> prevHead, int index)
    {

        if (isFull(2)) {
            throw new IllegalStateException("array is full");
        }

        HeadList<PLLE<E>> newHead = null;

        checkListIndex(index, prevHead);

        E result = get(index);

        if (prevHead.size == 1)
        {
            undo.push(new HeadList<>());
            redo.clear();
            return result;
        }

        int treeIndex = getTreeIndex(prevHead, index);
        PLLE<E> mid = getLeaf(prevHead, treeIndex).getKey().value.get(treeIndex & mask);
        //System.out.println(drawGraph(false));

        if(mid.prev == -1)
        {
            int nextIndex = index+1;
            int treeNextIndex = getTreeIndex(nextIndex);

            newHead = copyLeaf(prevHead, nextIndex).head;

            PLLE<E> nextPLLE = getPLLE(newHead, nextIndex);
            PLLE<E> newNextPLLE = new PLLE<>(nextPLLE);
            newNextPLLE.prev = -1;

            Pair<Node<PLLE<E>>, Integer> leafNext = getLeaf(newHead, treeNextIndex);
            leafNext.getKey().value.set(treeNextIndex & mask, newNextPLLE);

            newHead.first = treeNextIndex;

            finishRemove(newHead);
            return result;

        }

        if (mid.next == -1)
        {
            int prevIndex = index-1;
            int treePrevIndex = getTreeIndex(prevIndex);

            newHead = copyLeaf(prevHead, prevIndex).head;

            PLLE<E> prevPLLE = getPLLE(newHead, prevIndex);
            PLLE<E> newPrevPLLE = new PLLE<>(prevPLLE);
            newPrevPLLE.next = -1;

            Pair<Node<PLLE<E>>, Integer> leafPrev = getLeaf(newHead, treePrevIndex);
            leafPrev.getKey().value.set(treePrevIndex & mask, newPrevPLLE);

            newHead.last = treePrevIndex;

            finishRemove(newHead);
            return result;

        }

        int nextIndex = index+1;
        int treeNextIndex = getTreeIndex(nextIndex);

        newHead = copyLeaf(prevHead, nextIndex).head;

        PLLE<E> nextPLLE = getPLLE(newHead, nextIndex);
        PLLE<E> newNextPLLE = new PLLE<>(nextPLLE);
        newNextPLLE.prev = mid.prev;

        Pair<Node<PLLE<E>>, Integer> leafNext = getLeaf(newHead, treeNextIndex);
        leafNext.getKey().value.set(treeNextIndex & mask, newNextPLLE);


        int prevIndex = index-1;
        int treePrevIndex = getTreeIndex(prevIndex);

        newHead = copyLeaf(newHead, prevIndex).head;

        PLLE<E> prevPLLE = getPLLE(newHead, prevIndex);
        PLLE<E> newPrevPLLE = new PLLE<>(prevPLLE);
        newPrevPLLE.next = mid.next;

        Pair<Node<PLLE<E>>, Integer> leafPrev = getLeaf(newHead, treePrevIndex);
        leafPrev.getKey().value.set(treePrevIndex & mask, newPrevPLLE);


        if (newHead.deadList == null)
        {
            newHead.deadList = new ArrayDeque<>();
        }
        else
        {
            newHead.deadList = new ArrayDeque<>(newHead.deadList);
        }

        newHead.deadList.push(treeIndex);


        finishRemove(newHead);
        return result;
    }

    private void finishRemove(HeadList<PLLE<E>> newHead)
    {
        newHead.size--;
        undo.push(newHead);
        redo.clear();
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
            if (head.size == 0)
                return;

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
        @SuppressWarnings("unchecked")
        public E2 next()
        {
            //O(log(width, N)) 100%
            E2 result = (E2) current.value;

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
