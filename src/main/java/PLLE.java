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

    public PLLE(PLLE<E> other) {
        this.next = other.next;
        this.prev = other.prev;
        this.value = other.value;
    }

    @Override
    public String toString() {
        return String.format("%09x", hashCode()) + "{P" + prev + ", " + value + ", N" + next + "}";
    }
}
