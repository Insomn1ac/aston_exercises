package exercises.sort;

import exercises.linkedlist.LinkedList;
import exercises.linkedlist.LinkedListImpl;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MergeSortTest {

    @Test
    public void whenMergeSort() {
        LinkedList<Integer> inputList = new LinkedListImpl<>();
        inputList.add(3);
        inputList.add(5);
        inputList.add(1);
        inputList.add(2);
        inputList.add(4);
        LinkedList<Integer> expected = new LinkedListImpl<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        expected.add(4);
        expected.add(5);
        LinkedList<Integer> output = MergeSort.sort(inputList);
        for (Integer el : output) {
            System.out.println(el);
        }
        for (int i = 0; i < output.size(); i++) {
            assertEquals(expected.get(i), output.get(i));
        }
    }

    @Test
    public void whenAllElementsInInputListAre1() {
        LinkedList<Integer> inputList = new LinkedListImpl<>();
        inputList.add(1);
        inputList.add(1);
        inputList.add(1);
        LinkedList<Integer> expected = new LinkedListImpl<>();
        expected.add(1);
        expected.add(1);
        expected.add(1);
        LinkedList<Integer> output = MergeSort.sort(inputList);
        assertEquals(expected.get(2), output.get(2));
    }

    @Test
    public void whenInputListLengthIs1() {
        LinkedList<Integer> inputList = new LinkedListImpl<>();
        inputList.add(3);
        LinkedList<Integer> output = MergeSort.sort(inputList);
        assertThat(output.get(0)).isEqualTo(3);
    }

    @Test
    public void whenInputListLengthIs2() {
        LinkedList<Integer> inputList = new LinkedListImpl<>();
        inputList.add(3);
        inputList.add(1);
        LinkedList<Integer> output = MergeSort.sort(inputList);
        assertThat(output.get(1)).isEqualTo(3);
    }
}