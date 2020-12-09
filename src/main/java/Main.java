import java.util.Iterator;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        PersistentArray<Integer> pa = new PersistentArray<>(5);
        System.out.println("Max count: " + pa.maxSize);

        testUndoRedo(pa);
        testIterator(pa);
        testPop(pa);
    }

    private static void testPop(PersistentArray<Integer> pa) {
        System.out.println("testPop");
        clearAndFill(pa, 3);
        System.out.println(pa.pop());
        System.out.println(pa.pop());
        printArray(pa);
        pa.undo();
        printArray(pa);
        pa.redo();
        printArray(pa);
    }

    private static void testUndoRedo(PersistentArray<Integer> pa) {
        System.out.println("testUndoRedo");
        clearAndFill(pa, 5);
        printArray(pa);
        pa.undo();
        pa.undo();
        printArray(pa);
        pa.add(999);
        printArray(pa);
        pa.redo();
        printArray(pa);
        System.out.println("undo() undo() redo() redo()");
        pa.undo();
        pa.undo();
        printArray(pa);
        pa.redo();
        pa.redo();
        printArray(pa);
    }

    private static void testIterator(PersistentArray<Integer> pa) {
        System.out.println("testIterator");
        clearAndFill(pa, 5);
        printArray(pa);
        Iterator<Integer> i = pa.iterator();
        System.out.println(i.next());
        System.out.println(i.next());
        System.out.println(i.hasNext());
    }

    private static void clearAndFill(PersistentArray<Integer> pa, int count)
    {
        pa.clear();
        for (int i = 0; i < count; i++) {
            pa.add(i);
        }
        printArray(pa);
    }

    private static void printArray(PersistentArray<Integer> array)
    {
        System.out.print("size: "+ array.size() + "   ");

        for (Integer integer : array) {
            System.out.print(integer + " ");
        }

//        for (int i = 0; i < array.size(); i++) {
//            System.out.print(array.get(i) + " ");
//        }

        System.out.println();
    }
}
