public class PLLE<E> {
    int next = -1;
    int prev = -1;
    E value;

    public PLLE(E value) {
        this.value = value;
    }

    public PLLE(E value, int prev, int next) {
        this.next = next;
        this.prev = prev;
        this.value = value;
    }

    @Override
    public String toString() {
        return "{P" + prev + ", " + value + ", N" + next + "}";
    }
}
