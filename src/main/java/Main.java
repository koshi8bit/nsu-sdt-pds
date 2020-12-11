import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import java.util.Stack;

public class Main {
    final static Random random = new Random();

    public static void main(String[] args) {

//
//        PersistentArray<Integer> pa = new PersistentArray<>(100);
//        pa.add(4);
//        pa.add(5);

        testUndoRedo();
        testIterator();
        testPop();
        testAPI();
        testCascades();
        testAssoc();
    }

    private static PersistentArray<Integer> testBegin(String section, int size)
    {
        System.out.println("\n" + section);
        PersistentArray<Integer> pa = new PersistentArray<>(100);
        System.out.println("Max count: " + pa.maxSize);
        fill(pa, size);
        printArray(pa);
        return pa;
    }

    private static PersistentArray<Integer> testBegin(String section)
    {
        System.out.println("\n" + section);
        PersistentArray<Integer> pa = new PersistentArray<>(100);
        System.out.println("Max count: " + pa.maxSize);
        return pa;
    }

    private static void testAssoc() {
        PersistentArray<Integer> pa = testBegin("testAssoc", 4);
//        PersistentArray<Integer> pa = new PersistentArray<>(4, false);
//        pa.add(2);
//        pa.add(3);
//        pa.add(4);
//        pa.add(4);
        pa.assoc(3, 999);
        printArray(pa);
        pa.undo();
        printArray(pa);

    }

    private static void testCascades() {
//        PersistentArray<PersistentArray<Integer>> parentPA = new PersistentArray<>(5);
//
//        PersistentArray<Integer> childPA1 = new PersistentArray<>(5);
//        childPA1.add(7);
//        childPA1.add(3);
//
//        PersistentArray<Integer> childPA2 = new PersistentArray<>(5);
//        childPA2.add(8);
//        childPA2.add(4);
//
//        parentPA.add(childPA1);
//        parentPA.add(childPA2);
//        parentPA.undo();

    }

    private static void testAPI() {
        PersistentArray<Integer> pa = testBegin("testAPI");
        pa.add(7);
        pa.add(6);
        pa.add(5);
        pa.add(4);
        pa.add(3);
        pa.add(8);
        printArray(pa);
        System.out.println(Arrays.toString(
                pa.stream().map(i -> i * 2).filter(x -> x>10).toArray()));
        pa.undo();

        System.out.println(Arrays.toString(
                pa.stream().map(i -> i * 2).filter(x -> x>10).toArray()));

        for (Integer integer : pa) {
            System.out.print(integer + " ");
        }
        System.out.println();

    }

    private static void testPop() {
        PersistentArray<Integer> pa = testBegin("testPop", 5);
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

    private static void testUndoRedo() {
        PersistentArray<Integer> pa = testBegin("testUndoRedo", 5);
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

    private static void testIterator() {
        PersistentArray<Integer> pa = testBegin("testIterator", 5);
        Iterator<Integer> i = pa.iterator();
        System.out.println(i.next());
        System.out.println(i.next());
        System.out.println(i.hasNext());
    }

    private static void fill(PersistentArray<Integer> pa, int count)
    {
        for (int i = 0; i < count; i++) {
            pa.add(i);
            //pa.add(random.nextInt(9));
        }
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
