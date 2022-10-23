package exercises.linkedlist;

import java.util.*;

public class LinkedListImpl<E> implements LinkedList<E> {
    private int size;
    private Node<E> firstNode;
    private Node<E> lastAdded;
    private int modCount;

    private static class Node<E> {
        E element;
        Node<E> next;

        Node(E element) {
            this.element = element;
        }
    }

    @Override
    public void add(E element) {
        Node<E> newNode = new Node<>(element);
        if (size == 0) {
            firstNode = newNode;
            lastAdded = firstNode;
        } else {
            lastAdded.next = newNode;
            lastAdded = newNode;
        }
        ++size;
        ++modCount;
    }

    @Override
    public E get(int index) {
        Objects.checkIndex(index, size);
        Node<E> resultNode = firstNode;
        for (int i = 0; i < index; i++) {
            resultNode = resultNode.next;
        }
        return resultNode.element;
    }

    @Override
    public boolean remove(E element) {
        Node<E> previousNode = null;
        Node<E> currentNode = firstNode;
        while (currentNode != null) {
            if (currentNode.element.equals(element)) {
                if (previousNode != null) {
                    previousNode.next = currentNode.next;
                    if (currentNode.next == null) {
                        lastAdded = previousNode;
                    }
                } else {
                    firstNode = firstNode.next;
                    if (firstNode == null) {
                        lastAdded = null;
                    }
                }
                --size;
                ++modCount;
                return true;
            } else {
                previousNode = currentNode;
                currentNode = currentNode.next;
            }
        }
        return false;
    }

    @Override
    public boolean contains(E element) {
        Node<E> currentNode = firstNode;
        while (currentNode != null) {
            if (currentNode.element.equals(element)) {
                return true;
            } else {
                currentNode = currentNode.next;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private Node<E> nextNode = firstNode;
            private int point = 0;
            private final int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return point < size;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Node<E> resultNode = nextNode;
                nextNode = nextNode.next;
                ++point;
                return resultNode.element;
            }
        };
    }

    public String toString() {
        Node<E> currentNode = firstNode;
        int arrayIndex = 0;
        String[] array = new String[size];
        while (currentNode != null) {
            array[arrayIndex++] = currentNode.element.toString();
            currentNode = currentNode.next;
        }
        return Arrays.toString(array);
    }
}
