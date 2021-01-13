import java.util.Arrays;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        //arrayPresentation();
        listPresentation();



    }

    private static void listPresentation() {
        System.out.println("\n\nlist");
        PersistentLinkedList<Integer> pl = new PersistentLinkedList<>(3, 1);

        pl.add(3);
        pl.add(4);
        pl.add(6);
        pl.add(0);
        pl.add(7);
        System.out.println("filled " + pl);
        System.out.println(pl.drawGraph());

        pl.add(3, 9);
        System.out.println(pl.drawGraph());
        System.out.println("add(3,9) " + pl);
        System.out.println(pl.drawGraph());

        pl.add(0, 1);
        System.out.println("add(0,1) " + pl);
        System.out.println(pl.drawGraph());

        pl.undo();
        System.out.println("undo " + pl);
        System.out.println(pl.drawGraph());

        //todo add to test
        System.out.println("Iterator");
        Iterator<Integer> i = pl.iterator();
        System.out.println(i.hasNext());
        System.out.println(i.next());
        System.out.println(i.next());
//        System.out.println(i.next());
//        System.out.println(i.next());
//        System.out.println(i.next());
//        System.out.println(i.next());
//        System.out.println(i.next());

//        pl.add(5, 2);
//        System.out.println("add(6,2)" + pl);
//        System.out.println(pl.drawGraph());
//TODO tests results are different, wtf?



    }

    private static void arrayPresentation() {
        simple();
        vasyaCooperAbdula();
        vlojennoct();

    }

    private static void vlojennoct() {
        System.out.println("\n\nVlojennost");

        PersistentArray<PersistentArray<Integer>> pa = new PersistentArray<>(3, 1);

        PersistentArray<Integer> pa1 = new PersistentArray<>(3, 1);
        pa1.add(1);
        pa1.add(2);

        PersistentArray<Integer> pa2 = new PersistentArray<>(3, 1);
        pa2.add(3);
        pa2.add(4);

        pa.add(pa1);
        pa.add(pa2);

        System.out.println(pa.getCurrentHead().root.drawGraph());
        pa1.set(1, 9);
        System.out.println(pa.getCurrentHead().root.drawGraph());
        pa.undo();
        System.out.println(pa.getCurrentHead().root.drawGraph());
    }

    private static void vasyaCooperAbdula() {
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

    private static void simple() {
        System.out.println("\n\narray");
        PersistentArray<String> pa = new PersistentArray<>(28);
        System.out.println("maxSize = " + pa.maxSize);

        pa.add("1");
        pa.add("2");
        System.out.println(pa);
        System.out.println("pop=" + pa.pop());
        System.out.println(pa);
        pa.undo();
        System.out.println(pa);
    }


}
