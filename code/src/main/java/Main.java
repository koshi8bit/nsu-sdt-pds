import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        PersistentArray<Integer> pa = new PersistentArray<>();
        System.out.println(pa.head.tree.root.getChildren());
        System.out.println(pa.head.tree.root.getChildren().get(0).getChildren());
        System.out.println(pa.head.tree.root.getChildren().get(0).getChildren().get(0).getChildren());
        System.out.println(pa.head.tree.root.getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren());
    }
}
