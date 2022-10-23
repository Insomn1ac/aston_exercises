package exercises.linkedlist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LinkedListImplTest {
    private LinkedList<Integer> list;

    @BeforeEach
    public void initAndAddData() {
        list = new LinkedListImpl<>();
        list.add(1);
        list.add(2);
    }

    @Test
    public void checkListSizeSimple() {
        assertThat(list.size()).isEqualTo(2);
        list.add(3);
        list.add(4);
        assertThat(list.size()).isEqualTo(4);
    }

    @Test
    public void checkIteratorSimple() {
        assertThat(list).hasSize(2);
        list.add(3);
        list.add(4);
        assertThat(list).hasSize(4);
    }

    @Test
    public void checkToStringMethod() {
        assertThat(list.toString()).contains("2");
        assertThat(list.toString()).containsWhitespaces();
        assertThat(list.toString()).doesNotContain("3");
        list.add(3);
        assertThat(list.toString()).contains("3");
    }

    @Test
    public void whenAddAndGet() {
        list.add(3);
        list.add(4);
        assertThat(list.get(0)).isEqualTo(1);
        assertThat(list.get(1)).isEqualTo(2);
        assertThat(list.get(2)).isEqualTo(3);
        assertThat(list.get(3)).isEqualTo(4);
    }

    @Test
    public void whenAddAndRemove() {
        list.add(3);
        list.add(4);
        assertThat(list.get(3)).isEqualTo(4);
        list.remove(4);
        assertThat(list.size()).isEqualTo(3);
        list.remove(2);
        assertThat(list.get(1)).isEqualTo(3);
        assertThat(list.size()).isEqualTo(2);
        list.remove(1);
        assertThat(list.remove(3)).isTrue();
    }

    @Test
    public void whenListContainsAndNotContainsElements() {
        assertThat(list.contains(2)).isTrue();
        assertThat(list.contains(3)).isFalse();
    }

    @Test
    public void whenGetOutOfBoundsThenExceptionThrown() {
        assertThatThrownBy(() -> list.get(2))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    public void whenAddIterHasNextTrue() {
        Iterator<Integer> iter = list.iterator();
        assertThat(iter.hasNext()).isTrue();
    }

    @Test
    public void whenAddIterNextOne() {
        Iterator<Integer> iter = list.iterator();
        assertThat(iter.next()).isEqualTo(1);
    }

    @Test
    public void whenEmptyIterHasNextFalse() {
        LinkedList<Integer> list = new LinkedListImpl<>();
        Iterator<Integer> iter = list.iterator();
        assertThat(iter.hasNext()).isFalse();
    }

    @Test
    public void whenAddIterNextOneNextTwo() {
        Iterator<Integer> iter = list.iterator();
        assertThat(iter.next()).isEqualTo(1);
        assertThat(iter.next()).isEqualTo(2);
    }

    @Test
    public void whenGetIteratorTwiceThenEveryFromBegin() {
        Iterator<Integer> first = list.iterator();
        assertThat(first.hasNext()).isTrue();
        assertThat(first.next()).isEqualTo(1);
        assertThat(first.hasNext()).isTrue();
        assertThat(first.next()).isEqualTo(2);
        assertThat(first.hasNext()).isFalse();
        Iterator<Integer> second = list.iterator();
        assertThat(second.hasNext()).isTrue();
        assertThat(second.next()).isEqualTo(1);
        assertThat(second.hasNext()).isTrue();
        assertThat(second.next()).isEqualTo(2);
        assertThat(second.hasNext()).isFalse();
    }
}