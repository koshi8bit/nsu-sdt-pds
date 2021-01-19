public class Main {


    public static void main(String[] args) {
        hashMapPresentationCascade();
        //arrayPresentation();
        //listPresentation();
        //hashMapPresentation();
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

    private static void listPresentation()
    {
        listPresentationBasic();
        listPresentationMemReuse();
        //listGetTimeTest();
        //listAddTimeTest();
    }


    private static void listPresentationBasic() {
        System.out.println("\n\n-----LIST-----");
        PersistentLinkedList<Integer> pl = new PersistentLinkedList<>(4, 1);

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

        pl.remove(1);
        System.out.println("remove(1)");
        System.out.println(pl.drawGraph());

        pl.add(2);
        System.out.println("add 2");
        System.out.println(pl.drawGraph());

        pl.remove(1);
        System.out.println("remove(1)");
        System.out.println(pl.drawGraph());

    }

    private static void listPresentationMemReuse() {
        PersistentLinkedList<Integer> pl = new PersistentLinkedList<>(4, 1);

        pl.add(3);
        pl.add(4);
        pl.add(5);
        System.out.println("add 3,4,5");
        System.out.println(pl.drawGraph());
        System.out.println(pl.getCurrentHead().sizeTree == 3); //do assert on it

        pl.remove(1);
        System.out.println("remove(1)");
        System.out.println(pl.drawGraph());
        System.out.println(pl.getCurrentHead().sizeTree == 3);

        pl.add(6);
        System.out.println("add(6)");
        System.out.println(pl.drawGraph());
        System.out.println(pl.getCurrentHead().sizeTree == 3);

        pl.undo();
        System.out.println("undo");
        System.out.println(pl.drawGraph());
        System.out.println(pl.getCurrentHead().sizeTree == 3);

        pl.add(7);
        System.out.println("add(7)");
        System.out.println(pl.drawGraph());
        System.out.println(pl.getCurrentHead().sizeTree == 3);
    }

    interface Express {
        String doIt();
    }

    private static void measureTime(Express f, int N) {
        double avg = 0;
        for (int i =0; i<N; i++)
        {
            long start = System.nanoTime();
            f.doIt();
            long end = System.nanoTime();
            long delta = end-start;
            //System.out.println(delta);
            avg +=  delta;
        }
        System.out.println("avg=" + avg/N + "; result=" + f.doIt());
    }

    private static void listGetTimeTest() {
        System.out.println("\n---Get time test---");
        PersistentLinkedList<Integer> pl = new PersistentLinkedList<>(1000);

        for(int i=0; i<1000; i++)
        {
            pl.add(i);
        }

        int N = 100000;

//        new Thread(() -> measureTime(() ->  "pl.get(0) " + pl.get(0).toString(),  N)).start();
//        new Thread(() -> measureTime(() ->  "pl.get(200) " + pl.get(200).toString(),  N)).start();
//        new Thread(() -> measureTime(() ->  "pl.get(800) " + pl.get(800).toString(),  N)).start();
//        new Thread(() -> measureTime(() ->  "pl.get(999) " + pl.get(999).toString(),  N)).start();
        measureTime(() ->  "pl.get(0)   " + pl.get(0).toString(),  N);
        measureTime(() ->  "pl.get(200) " + pl.get(200).toString(),  N);
        measureTime(() ->  "pl.get(800) " + pl.get(800).toString(),  N);
        measureTime(() ->  "pl.get(999) " + pl.get(999).toString(),  N);
    }


    private static void listAddTimeTest() {
        System.out.println("\n---Add time test---");
        PersistentLinkedList<Integer> pl = new PersistentLinkedList<>(1000000);

        int N = 100000;
        measureTime(() ->  { pl.add(5); return "pl.add(5)"; },N);
        pl.clear();

        for(int i=0; i<1000; i++)
        {
            pl.add(i);
        }
        measureTime(() ->  { pl.add(6); return "pl.add(6)"; },N);

//        measureTime(() ->  { pl.add(5); return "pl.add(5)"; },N);
//        measureTime(() ->  { pl.add(1, 888); return "pl.add(1, 888)"; },N);
//        measureTime(() ->  { pl.add(10000, 999); return "pl.add(10000, 999)"; },N);
//        measureTime(() ->  { pl.add(0); return "pl.add(0)"; },N);
//        measureTime(() ->  { pl.add(1); return "pl.add(1)"; },N);
        //System.out.println(pl);
    }




    private static void hashMapPresentation() {
        hashMapPresentationSimple();
        hashMapPresentationCascade();
    }



    private static void hashMapPresentationSimple() {
        System.out.println("\n\n-----HashMap-----");
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

    private static void hashMapPresentationCascade() {
        System.out.println("\n\n-----HashMapCascade-----");
        PersistentHashMap<String, PersistentHashMap<String, Integer>> parent = new PersistentHashMap<>();

        PersistentHashMap<String, Integer>  child1 = new PersistentHashMap<>();
        PersistentHashMap<String, Integer>  child2 = new PersistentHashMap<>();
        PersistentHashMap<String, Integer>  child3 = new PersistentHashMap<>();

        parent.put("Child1", child1);
        parent.put("Child2", child2);
        parent.put("Child3", child3);

        child1.put("F", 1);
        child1.put("S", 2);

        child2.put("F", 11);
        child2.put("S", 22);

        child3.put("F", 111);
        child3.put("S", 222);

        System.out.println("parent = " + parent.toString());
        parent.undo();
        System.out.println("undo = " + parent.toString());
        parent.undo();
        System.out.println("undo = " + parent.toString());

        parent.redo();
        System.out.println("redo = " + parent.toString());
        parent.undo();
        System.out.println("undo = " + parent.toString());

        parent.undo();
        parent.undo();
        parent.undo();
        parent.undo();
        System.out.println("4 undo = " + parent.toString());

        parent.undo();
        System.out.println("undo = " + parent.toString());
        parent.redo();
        System.out.println("redo = " + parent.toString());

        parent.undo();
        parent.undo();
        parent.undo();
        System.out.println("3 undo = " + parent.toString());

        parent.redo();
        parent.redo();
        parent.redo();
        System.out.println("3 redo = " + parent.toString());

        PersistentHashMap<String, Integer>  child4 = new PersistentHashMap<>();
        parent.put("Child4",child4);
        System.out.println("put in parent child4 = " +parent.toString());

        parent.redo();
        parent.redo();
        System.out.println("2 redo = " + parent.toString());

        parent.undo();
        parent.undo();
        System.out.println("2 undo = " + parent.toString());

        parent.remove("Child1");
        System.out.println("remove child1 = " + parent.toString());
        parent.undo();
        System.out.println("undo = " + parent.toString());
    }
}