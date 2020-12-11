import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

public class Main {
    final static Random random = new Random();

    public static void main(String[] args) {



        testUndoRedo();
        testIterator();
        testPop();
        testAPI();
        testCascades();
        testAssoc();
        testUniqueLeafs();
        testString();
        testIntAsString();
        testIntAsString2();

    }

    private static void testIntAsString2() {
        System.out.println("\n" + "testIntAsString2");
        PersistentArray<Integer> pa = new PersistentArray<>(20);
        pa.add(1);
        pa.add(2);
        pa.add(3);
        printArray(pa);
        pa.undo();
        printArray(pa);
        pa.add(4);
        printArray(pa);
        pa.assoc(0, 5);
        printArray(pa);
    }

    private static void testIntAsString() {
        System.out.println("\n" + "testIntAsString");
        PersistentArray<Integer> pa = new PersistentArray<>(20);
        pa.add(1);
        pa.add(2);
        pa.add(3);
        Iterator<Integer> i = pa.iterator();
        System.out.println(i.next());
        System.out.println(i.next());
        System.out.println(i.next());
//        printArray(pa);
//        pa.undo();
//        printArray(pa);
//        pa.add(4);
//        printArray(pa);
//        pa.add(0, 5);
//        printArray(pa);
    }

    private static void testString() {
        System.out.println("\n" + "testString");
        PersistentArray<String> pa = new PersistentArray<>(20);
        pa.add("A");
        pa.add("B");
        pa.add("C");
        Iterator<String> i = pa.iterator();
        System.out.println(i.next());
        System.out.println(i.next());
        System.out.println(i.next());
//        printArray2(pa);
//        pa.undo();
//        printArray2(pa);
//        pa.add("D");
//        printArray2(pa);
//        pa.add(0, "E");
//        printArray2(pa);
    }

    private static void testUniqueLeafs() {
        PersistentArray<Integer> pa = testBegin("testUniqueLists", 4, 3);
        pa.add(4);
        printArray(pa);
        pa.add(5);
        printArray(pa);


    }

    private static PersistentArray<Integer> testBegin(String section, int fillSize, int depth)
    {
        System.out.println("\n" + section);
        PersistentArray<Integer> pa = new PersistentArray<>(depth, false);
        System.out.println("Max count: " + pa.maxSize);
        fill(pa, fillSize);
        printArray(pa);
        return pa;
    }

    private static PersistentArray<Integer> testBegin(String section, int fillSize)
    {
        System.out.println("\n" + section);
        PersistentArray<Integer> pa = new PersistentArray<>(100);
        System.out.println("Max count: " + pa.maxSize);
        fill(pa, fillSize);
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
        System.out.print("size: " + array.size() + "; unique leafs: "
                + array.calcUniqueLeafs() + "; array: ");

        for (Integer e : array) {
            System.out.print(e + " ");
        }

//        for (int i = 0; i < array.size(); i++) {
//            System.out.print(array.get(i) + " ");
//        }

        System.out.println();
    }

    private static void printArray2(PersistentArray<String> array)
    {
        System.out.print("size: " + array.size() + "; unique leafs: "
                + array.calcUniqueLeafs() + "; array: ");

        for (String e : array) {
            System.out.print(e + " ");
        }

//        for (int i = 0; i < array.size(); i++) {
//            System.out.print(array.get(i) + " ");
//        }

        System.out.println();
    }
}
