package nodes;

public abstract class AbstractNode {
    public static int bit_na_pu = 1;
    public static int width;

    //Можно же просто в пятой строке тоже самое написать, зачем нам статический блок инициализации?
    static {
        width = (int) Math.pow(2, bit_na_pu);
    }
}
