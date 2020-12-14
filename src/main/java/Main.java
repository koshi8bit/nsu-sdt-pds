
public class Main {
    public static void main(String[] args) {

        //arrayPresentation();
        PersistentArray<Integer> pa = new PersistentArray<>(3, 1);
        pa.add(3);
        pa.add(7);
        pa.add(6);
        pa.add(9);
        pa.add(1);
        pa.add(2);
        System.out.println(pa.drawGraph());
        pa.add(2, 8);
        System.out.println(pa.drawGraph());
        System.out.println(pa);

        //System.out.println(pa.getCurrentHead().root.drawGraph());

//        Node{
//            child=[
//                    Node{
//                        child=[
//                                Node{
//                                    child=null,
//                                    value=[3, 7]
//                                },
//                                Node{
//                                    child=null,
//                                    value=[6, 9]
//                                }
//                        ],
//                        value=null
//                    },
//                    Node{
//                        child=[
//                                Node{
//                                    child=null,
//                                    value=[1]
//                                }
//                        ],
//                        value=null
//                    }],
//            value=null}

        //Node{child=[Node{child=[Node{child=null, value=[3, 7]}, Node{child=null, value=[6, 9]}], value=null}, Node{child=[Node{child=null, value=[1]}], value=null}], value=null}
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

    }




}
