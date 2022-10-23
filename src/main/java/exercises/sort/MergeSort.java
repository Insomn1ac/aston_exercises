package exercises.sort;

import exercises.linkedlist.LinkedList;
import exercises.linkedlist.LinkedListImpl;

import java.util.Comparator;

public class MergeSort<T> {
    public LinkedList<T> sort(LinkedList<T> list, Comparator<T> comp) {
        if (list.size() == 1) {
            return list;
        }
        int middle = list.size() / 2;
        LinkedList<T> left = new LinkedListImpl<>();
        LinkedList<T> right = new LinkedListImpl<>();
        for (int i = 0; i < middle; i++) {
            left.add(list.get(i));
        }
        for (int i = middle; i < list.size(); i++) {
            right.add(list.get(i));
        }
        sort(left, comp);
        sort(right, comp);
        return merge(left, right, middle, list.size() - middle, comp);
    }

    private LinkedList<T> merge(LinkedList<T> leftList,
                                LinkedList<T> rightList,
                                int left, int right, Comparator<T> comp) {
        int indexLeft = 0;
        int indexRight = 0;
        LinkedList<T> outputList = new LinkedListImpl<>();
        while (indexLeft < left && indexRight < right) {
            int compareRsl = comp.compare(leftList.get(indexLeft), rightList.get(indexRight));
            if (compareRsl < 0) {
                outputList.add(leftList.get(indexLeft++));
            } else {
                outputList.add(rightList.get(indexRight++));
            }
        }
        while (indexLeft < left) {
            outputList.add(leftList.get(indexLeft++));
        }
        while (indexRight < right) {
            outputList.add(rightList.get(indexRight++));
        }
        return outputList;
    }
}
