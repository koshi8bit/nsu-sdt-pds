import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {/*
        PersistentArray<Integer> pa = new PersistentArray<>();

        System.out.println(pa.root.getChildren().get(0).getChildren().get(0).data);
        for (int i = 0; i < 5; i++) {
            pa.add(i);
        }
        System.out.println(pa.root.getChildren().get(0).getChildren().get(0).data);
        System.out.println(pa.root.getChildren().get(0).getChildren().get(1).data);
        System.out.println(pa.root.getChildren().get(1).getChildren().get(0).data);

        System.out.println(pa.get(0));
        System.out.println(pa.get(1));
        System.out.println(pa.get(2));*/

        PersistentList<Integer> persistentList = new PersistentList<>();

        for (int i = 0; i < 15; i++) {
            persistentList.add(i);
        }

        for (int i = 0; i < persistentList.size(); i++) {
            System.out.println(persistentList.get(i));
        }

        List<Integer> list = new LinkedList<>();
        //Проверка работы пул реквеста
    }
}
