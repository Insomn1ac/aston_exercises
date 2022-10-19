package exercises.sort;

import exercises.linkedlist.LinkedList;
import exercises.linkedlist.LinkedListImpl;

public class MergeSort {
    public static LinkedList<Integer> sort(LinkedList<Integer> list) {
        if (list.size() == 1) {
            return list;
        }
        int middle = list.size() / 2;
        LinkedList<Integer> left = new LinkedListImpl<>();
        LinkedList<Integer> right = new LinkedListImpl<>();
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

    private static LinkedList<Integer> merge(LinkedList<Integer> leftList,
                                     LinkedList<Integer> rightList,
                                     int left, int right) {
        int indexLeft = 0;
        int indexRight = 0;
        LinkedList<Integer> outputList = new LinkedListImpl<>();
        while (indexLeft < left && indexRight < right) {
            if (leftList.get(indexLeft) <= rightList.get(indexRight)) {
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
