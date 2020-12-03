import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        PersistentArray<Integer> pa = new PersistentArray<>();
        /*System.out.println(pa.root.getChildren());
        System.out.println(pa.root.getChildren().get(0).getChildren());
        System.out.println(pa.root.getChildren().get(0).getChildren().get(0).getChildren());
        System.out.println(pa.root.getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren());
        */

        System.out.println(pa.root.getChildren().get(0).getChildren().get(0).data);
        pa.add(1);
        pa.add(2);
        System.out.println(pa.root.getChildren().get(0).getChildren().get(0).data);
    }
}
