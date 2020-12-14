import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        arrayPresentation();
    }

    private static void arrayPresentation() {
        System.out.println("\n" + "array");
        PersistentArray<String> v1 = new PersistentArray<>(30);

        v1.add("1");
        v1.add("2");
        System.out.println(v1);
        System.out.println("pop=" + v1.pop());
        System.out.println(v1);
        v1.undo();

        v1.clear();
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
