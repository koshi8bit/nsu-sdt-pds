import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        PersistentArray<Integer> pa = new PersistentArray<>(5);
        System.out.println("Max count: " + pa.maxSize);

        int count = 7;
        for (int i = 0; i < count; i++) {
            pa.add(i);
        }

        testUndoRedo(pa);
        testClear(pa);
        testIterator(pa);
    }

    private static void testUndoRedo(PersistentArray<Integer> pa) {
        System.out.println("testUndoRedo");
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

    private static void testClear(PersistentArray<Integer> pa) {
        System.out.println("testClear");
        pa.clear();
        printArray(pa);
    }

    private static void testIterator(PersistentArray<Integer> pa) {
        System.out.println("testIterator");
        pa.add(7);
        pa.add(3);
        pa.add(9);
        printArray(pa);
        Iterator<Integer> i = pa.iterator();
        System.out.println(i.next());
        System.out.println(i.next());
        System.out.println(i.hasNext());
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
