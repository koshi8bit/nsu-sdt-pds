import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        /*PersistentArray<Integer> pa = new PersistentArray<>();

        System.out.println(pa.root.getChildren().get(0).getChildren().get(0).data);
        for (int i = 0; i < 18; i++) {
            pa.add(i);
        }
        System.out.println(pa.root.getChildren().get(0).getChildren().get(0).data);
        System.out.println(pa.root.getChildren().get(0).getChildren().get(1).data);
        System.out.println(pa.root.getChildren().get(0).getChildren().get(2).data);
        System.out.println(pa.root.getChildren().get(0).getChildren().get(3).data);
        System.out.println(pa.root.getChildren().get(1).getChildren().get(0).data);

        System.out.println(pa.get(0));
        System.out.println(pa.get(1));*/

        PersistentArray<Test> pa2 = new PersistentArray<>();

        Test test1 = new Test(100);
        Test test2 = new Test(200);
        Test test3 = new Test(300);

        pa2.add(test1);
        pa2.add(test2);
        pa2.add(test3);
        pa2.add(test1);
        pa2.add(test2);

        System.out.println(pa2.root.getChildren().get(0).getChildren().get(0).data);
        System.out.println(pa2.get(0).d);
        System.out.println(pa2.get(1).d);
        System.out.println(pa2.get(2).d);
        System.out.println(pa2.get(3).d);
        System.out.println(pa2.get(4).d);

        ArrayList<String> listArray = new ArrayList<>();

        listArray.add("Germany");
        listArray.add("Holland");
        listArray.add("Sweden");

        String[] strArray = new String[3];
        System.out.println(Arrays.toString(strArray));
        listArray.toArray(strArray);
        System.out.println(Arrays.toString(strArray));
    }
}
