import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {

        arrayPresentation();
//        listPresentation();

        PersistentArray<PersistentArray<Integer>> pa = new PersistentArray<>();

        PersistentArray<Integer> pa1 = new PersistentArray<>();
        pa1.add(1);
        pa1.add(2);

        PersistentArray<Integer> pa2 = new PersistentArray<>();
        pa2.add(3);
        pa2.add(4);

        pa.add(pa1);
        pa.add(pa2);

        pa1.set(1, 9);

        //System.out.println(pa.getCurrentHead().root.drawGraph());

    }

    private static void listPresentation() {
        System.out.println("\n" + "list");
        PersistentLinkedList<Integer> pl = new PersistentLinkedList<>(3, 1);

        pl.add(3);
        pl.add(4);
        pl.add(6);
        pl.add(0);
        pl.add(7);
        System.out.println(Arrays.toString(pl.toArray()) + " fill");
        System.out.println(pl.drawGraph());

        pl.add(3, 9);
        System.out.println(Arrays.toString(pl.toArray()) + " add(3,9)");
        System.out.println(pl.drawGraph());

        pl.add(0, 1);
        System.out.println(Arrays.toString(pl.toArray()) + " add(0,1)");
        System.out.println(pl.drawGraph());

        pl.undo();
        System.out.println(Arrays.toString(pl.toArray()) + " undo");
        System.out.println(pl.drawGraph());

//        pl.add(5, 2);
//        System.out.println(Arrays.toString(pl.toArray()) + " add(6,2)");
//        System.out.println(pl.drawGraph());
//TODO tests results are different, wtf?

    }

    private static void arrayPresentation() {
        System.out.println("\n" + "array");
        PersistentArray<String> pa = new PersistentArray<>(28);
        System.out.println("maxSize = " + pa.maxSize);

        pa.add("1");
        pa.add("2");
        System.out.println(pa);
        System.out.println("pop=" + pa.pop());
        System.out.println(pa);
        pa.undo();
        System.out.println(pa);


        System.out.println("\n\n---Vasya-Cooper-Abdula---");
        PersistentArray<String> v1 = new PersistentArray<>(3, 1);
        System.out.println("maxSize = " + v1.maxSize);
        v1.add("Vasya");
        PersistentArray<String> v2 = v1.conj("Cooper");

        System.out.println(v1);
        System.out.println(v2);

        PersistentArray<String> v3 = v2.assoc(0, "Abdula");

        System.out.println();
        System.out.println(v1);
        System.out.println(v2);
        System.out.println(v3);

        v3.add("3");
        v3.add("4");
        System.out.println(v3.drawGraph());
        v3.remove(2);
        System.out.println(v3.drawGraph());

    }




}
