
import com.sun.xml.internal.ws.util.StringUtils;

import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        PersistentArray<Integer> pa = new PersistentArray<>();

//        System.out.println(pa.head.root.getChild().get(0).getChild().get(0).data);
        for (int i = 0; i < 5; i++) {
            pa.add(i);
        }
//        System.out.println(pa.head.root.getChild().get(0).getChild().get(0).data);
//        System.out.println(pa.head.root.getChild().get(0).getChild().get(1).data);
//        System.out.println(pa.head.root.getChild().get(1).getChild().get(0).data);

        System.out.println(pa.get(0));
        System.out.println(pa.get(1));
        System.out.println(pa.get(2));

//        PersistentArray<Integer> persistentArray = new PersistentArray<>();
//
//        for (int i = 0; i < 22; i++) {
//            persistentArray.add(i);
//        }
//
//        printArray(persistentArray);
//
//
//
//        List<Integer> list = new LinkedList<>();
        //Проверка работы пул реквеста
    }

    private static void printArray(PersistentArray<Integer> array)
    {
        System.out.print("size: "+ array.size() + "   ");
        for (int i = 0; i < array.size(); i++) {
            System.out.print(array.get(i) + " ");
        }
    }
}
