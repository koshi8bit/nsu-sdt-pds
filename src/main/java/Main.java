import java.util.Arrays;
import java.util.Iterator;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        PersistentArray<Integer> pa = new PersistentArray<>(5);
        System.out.println("Max count: " + pa.maxSize);

        testUndoRedo(pa);
        testIterator(pa);
        testPop(pa);
        testAPI(pa);


    }

    private static void testAPI(PersistentArray<Integer> pa) {
        System.out.println("testAPI");
        clearAndFill(pa, 5);
        System.out.println(Arrays.toString(
                pa.stream().map(i -> i * 2).filter(x -> x>10).toArray()));

        System.out.println(Arrays.toString(
                pa.undo().stream().map(i -> i * 2).filter(x -> x>10).toArray()));

        for (Integer integer : pa) {
            System.out.print(integer + " ");
        }

    }

    private static void testPop(PersistentArray<Integer> pa) {
        System.out.println("testPop");
        clearAndFill(pa, 5);
        System.out.println("pop=" + pa.pop());
        printArray(pa);
        System.out.println("pop=" + pa.pop());
        printArray(pa);
        pa.undo();
        pa.undo();
        printArray(pa);
        pa.redo();
        pa.redo();
        printArray(pa);
//        pa.clear();
//        pa.pop();
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
            pa.add((count - i)+2);
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
