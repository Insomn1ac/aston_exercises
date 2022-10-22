package exercises.sort;

import exercises.linkedlist.LinkedList;
import exercises.linkedlist.LinkedListImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MergeSortTest {
    private LinkedList<SimpleClass> inputList;

    @BeforeEach
    public void init() {
        inputList = new LinkedListImpl<>();
    }

    @Test
    public void whenTestingComparatorOfSimpleClass() {
        SimpleClass first = new SimpleClass(1, "1");
        SimpleClass second = new SimpleClass(2, "2");
        assertThat(first.compareTo(second)).isLessThan(0);
        assertThat(second.compareTo(first)).isGreaterThanOrEqualTo(1);
    }

    @Test
    public void whenMergeSort() {
        inputList.add(new SimpleClass(3, "3"));
        inputList.add(new SimpleClass(5, "5"));
        inputList.add(new SimpleClass(1, "1"));
        inputList.add(new SimpleClass(2, "2"));
        inputList.add(new SimpleClass(4, "4"));
        LinkedList<SimpleClass> expected = new LinkedListImpl<>();
        expected.add(new SimpleClass(1, "1"));
        expected.add(new SimpleClass(2, "2"));
        expected.add(new SimpleClass(3, "3"));
        expected.add(new SimpleClass(4, "4"));
        expected.add(new SimpleClass(5, "5"));
        LinkedList<SimpleClass> output = MergeSort.sort(inputList);
        for (int i = 0; i < output.size(); i++) {
            assertEquals(expected.get(i), output.get(i));
        }
    }

    @Test
    public void whenAllElementsInInputListAre1() {
        inputList.add(new SimpleClass(1, "2"));
        inputList.add(new SimpleClass(1, "1"));
        inputList.add(new SimpleClass(1, "3"));
        LinkedList<SimpleClass> expected = new LinkedListImpl<>();
        expected.add(new SimpleClass(1, "1"));
        expected.add(new SimpleClass(1, "2"));
        expected.add(new SimpleClass(1, "3"));
        LinkedList<SimpleClass> output = MergeSort.sort(inputList);
        assertEquals(expected.get(2), output.get(2));
    }

    @Test
    public void whenInputListLengthIs1() {
        inputList.add(new SimpleClass(3, "3"));
        LinkedList<SimpleClass> output = MergeSort.sort(inputList);
        assertThat(output.get(0)).isEqualTo(new SimpleClass(3, "3"));
    }

    @Test
    public void whenInputListLengthIs2() {
        inputList.add(new SimpleClass(3, "3"));
        inputList.add(new SimpleClass(1, "1"));
        LinkedList<SimpleClass> output = MergeSort.sort(inputList);
        assertThat(output.get(1)).isEqualTo(new SimpleClass(3, "3"));
    }
}