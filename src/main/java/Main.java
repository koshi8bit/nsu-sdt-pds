public class Main {
    public static void main(String[] args) {
        arrayPresentation();
        listPresentation();
        hashMapPresentation();
    }

    private static void arrayPresentation() {
        simple();
        cascade();
        arrayInArray();
    }

    private static void simple() {
        System.out.println("\n\n-----ARRAY-----");
        PersistentArray<String> pa = new PersistentArray<>(28);
        System.out.println("maxSize = " + pa.maxSize);

        pa.add("1");
        pa.add("2");
        System.out.println("add 1,2,3 \t\t" + pa);
        System.out.println("pop=" + pa.pop());
        System.out.println("after pop \t\t" + pa);
        pa.undo();
        System.out.println("undo \t\t\t" + pa);
    }



    private static void cascade() {
        System.out.println("\n\n---Vasya-Cooper-Abdula---");
        PersistentArray<String> v1 = new PersistentArray<>(3, 1);
        v1.add("Vasya");
        PersistentArray<String> v2 = v1.conj("Cooper");

        System.out.println("v1 Vasya\t\t\t\t\t" + v1);
        System.out.println("v2 Vasya, Cooper\t\t\t" + v2);

        PersistentArray<String> v3 = v2.assoc(0, "Abdula");

        System.out.println();
        System.out.println("v1 Vasya\t\t\t\t\t" + v1);
        System.out.println("v2 Vasya, Cooper\t\t\t" + v2);
        System.out.println("v3 Abdula, Cooper \t\t\t" + v3);


        v3.add("3");
        v3.add("4");
        System.out.println("\nv3.add 3,4");
        System.out.println(v3.drawGraph());

        v3.remove(2);
        System.out.println("v3.remove(2)");
        System.out.println(v3.drawGraph());
    }

    private static void arrayInArray() {
        System.out.println("\n---Array in array---");

        PersistentArray<PersistentArray<Integer>> pa = new PersistentArray<>(3, 1);

        PersistentArray<Integer> pa1 = new PersistentArray<>(3, 1);
        pa1.add(1);
        pa1.add(2);

        PersistentArray<Integer> pa2 = new PersistentArray<>(3, 1);
        pa2.add(3);
        pa2.add(4);

        pa.add(pa1);
        pa.add(pa2);

        System.out.println("Begin");
        System.out.println(pa.drawGraph());

        pa1.set(1, 9);
        System.out.println("\npa1.set(1, 9)");
        System.out.println(pa.drawGraph());

        pa.undo();
        System.out.println("\npa.undo()");
        System.out.println(pa.getCurrentHead().root.drawGraph());
    }

    private static void listPresentation() {
        System.out.println("\n\n-----LIST-----");
        PersistentLinkedList<Integer> pl = new PersistentLinkedList<>(3, 1);

        pl.add(3);
        pl.add(4);
        pl.add(6);
        pl.add(0);
        pl.add(7);
        System.out.println("add 3,4,6,0,7");
        System.out.println(pl.drawGraph());

        pl.add(3, 9);
        System.out.println("add(3, 9)");
        System.out.println(pl.drawGraph());

        pl.add(0, 1);
        System.out.println("add(0, 1)");
        System.out.println(pl.drawGraph());

        pl.undo();
        System.out.println("undo");
        System.out.println(pl.drawGraph());

        //TODO FAILS! ?tests results are different, wtf?
        pl.add(5, 2);
        System.out.println("add(5, 2)");
        System.out.println(pl.drawGraph(false));


    }



    private static void hashMapPresentation() {
        PersistentHashMap<String, Integer> phm = new PersistentHashMap<>();
        phm.put("Vasya",10);
        phm.put("Petya", 11);
        System.out.println("Vasya=10 Petya=11\t" + phm.toString());
        phm.undo();
        System.out.println("undo\t\t\t\t" + phm.toString());
        phm.redo();
        System.out.println("redo\t\t\t\t"+ phm.toString());

        System.out.println();
        phm.put("Gosha", 12);
        System.out.println("add Gosha=12\t\t" + phm.toString());
        phm.put("Gosha", 1000);
        System.out.println("modify Gosha=1000\t" + phm.toString());
        phm.undo();
        System.out.println("undo\t\t\t\t" + phm.toString());

        System.out.println();
        phm.put("Vova", -99);
        System.out.println("add Vova=-99\t\t"+ phm.toString());
        phm.remove("Vova");
        System.out.println("remove Vova\t\t\t"+ phm.toString());
        phm.undo();
        System.out.println("undo\t\t\t\t" + phm.toString());
    }

}
