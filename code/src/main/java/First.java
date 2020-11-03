import java.util.*;
import java.util.stream.Collectors;


public class First {
    public static void main(String[] args)
    {
//        System.out.println("asd");
//
//        ArrayList<Integer> people = new ArrayList<>();
//        // добавим в список ряд элементов
//        people.add(3);
//        people.add(1);
//        people.add(2);
//        System.out.println(people);
//        Collections.sort(people);
//        System.out.println(people);
//
//        ArrayList<Integer> a = new ArrayList<>();
//        a.add(1);
//        ArrayList<Integer> b = new ArrayList<>();
//
//
//        StringBuilder stringBuilder1 = new StringBuilder();
//        stringBuilder1.append("a");
//        StringBuilder stringBuilder2 = new StringBuilder(stringBuilder1);
//        stringBuilder2.append("b");
//        System.out.println(stringBuilder1);
//        System.out.println(stringBuilder2);

        String name = "aa1";
        String invalidSymbols = "1234567890!@#$";



        System.out.println(!name.chars()
                .mapToObj(x -> ((char) x))
                .anyMatch(x -> invalidSymbols.contains("" + x))
        );

        System.out.println (name.matches ("[[:alpha:]]+"));
        System.out.println (name.matches ("[a-zA-Z]+"));



        String pass = "1q2w3e";
        ArrayList<String> easyPass = new ArrayList<>();
        easyPass.add("123qwe");
        easyPass.add("1q2w3e");

        boolean isSimple = easyPass.stream()
                .anyMatch(x -> x.contains(pass));
        System.out.println(isSimple);

        MyWrapper<Integer> a = new MyWrapper<Integer>();
        a.add(1);
        a.add(2);
        Collections.sort(a);
        System.out.println(Arrays.toString(a.toArray()));
        System.out.println(a.get(0));
        System.out.println(a.get(1));


    }

    public First() {
    }
}

