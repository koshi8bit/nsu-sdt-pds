
public class Main {
    public static void main(String[] args) {

        //arrayPresentation();
        listPresentation();

        //System.out.println(pa.getCurrentHead().root.drawGraph());

    }

    private static void listPresentation() {
        System.out.println("\n" + "list");
        PersistentLinkedList<Integer> pl = new PersistentLinkedList<>(3, 1);

        //TODO tests results are different, wtf?
        pl.add(3);
        pl.add(4);
        pl.add(6);
        System.out.println(pl.drawGraph());

        pl.add(1, 9);
        System.out.println(pl.drawGraph());

//        pl.add(1, 7);
//        System.out.println(pl.drawGraph());
//
//        pl.add(8);



        //System.out.println(pl.calcUniqueLeafs());
//        System.out.println(pl.drawGraph());
//        pl.undo();
//        System.out.println(pl.drawGraph());
//        pl.add(4);
//        System.out.println(pl.drawGraph());

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


        System.out.println("\n---Vasya-Cooper-Abdula---");
        PersistentArray<String> v1 = new PersistentArray<>(3, 1);
        System.out.println("maxSize = " + v1.maxSize);
        v1.add("Vasya");
        PersistentArray<String> v2 = v1.conj("Cooper");

        System.out.println(v1);
        System.out.println(v2);

        PersistentArray<String> v3 = v2.assoc(0, "Abdula");

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
