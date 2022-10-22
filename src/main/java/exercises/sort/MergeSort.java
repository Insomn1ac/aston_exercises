package exercises.sort;

import exercises.linkedlist.LinkedList;
import exercises.linkedlist.LinkedListImpl;

public class MergeSort {
    public static LinkedList<SimpleClass> sort(LinkedList<SimpleClass> list) {
        if (list.size() == 1) {
            return list;
        }
        int middle = list.size() / 2;
        LinkedList<SimpleClass> left = new LinkedListImpl<>();
        LinkedList<SimpleClass> right = new LinkedListImpl<>();
        for (int i = 0; i < middle; i++) {
            left.add(list.get(i));
        }
        for (int i = middle; i < list.size(); i++) {
            right.add(list.get(i));
        }
        sort(left);
        sort(right);
        return merge(left, right, middle, list.size() - middle);
    }

    private static LinkedList<SimpleClass> merge(LinkedList<SimpleClass> leftList,
                                     LinkedList<SimpleClass> rightList,
                                     int left, int right) {
        int indexLeft = 0;
        int indexRight = 0;
        LinkedList<SimpleClass> outputList = new LinkedListImpl<>();
        while (indexLeft < left && indexRight < right) {
            if (leftList.get(indexLeft).compareTo(rightList.get(indexRight)) <= 0) {
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
