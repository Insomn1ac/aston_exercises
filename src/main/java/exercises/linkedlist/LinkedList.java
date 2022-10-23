package exercises.linkedlist;

public interface LinkedList<E> extends Iterable<E> {
    void add(E element);
    E get(int index);
    boolean remove(E element);
    boolean contains(E element);
    int size();
    String toString();
}
