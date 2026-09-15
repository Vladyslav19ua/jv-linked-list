package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node first = null;
    private Node last = null;
    private int size = 0;

    @Override
    public void add(T value) {
        Node nextElement = new Node(value);
        if (size == 0) {
            first = last = nextElement;
            size++;
            return;
        }
        last.next = nextElement;
        nextElement.prev = last;
        last = nextElement;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        Node nextElement = new Node(value);
        if (index == 0) {
            if (first == null) {
                first = last = nextElement;
                size++;
                return;
            }
            nextElement.next = first;
            first.prev = nextElement;
            first = nextElement;
        } else if (index == size) {
            nextElement.prev = last;
            last.next = nextElement;
            last = nextElement;
        } else {
            getIndexFrom(index).prev.next = nextElement;
            nextElement.prev = getIndexFrom(index).prev;
            nextElement.next = getIndexFrom(index);
            getIndexFrom(index).prev = nextElement;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list.size() == 0) {
            return;
        }
        Node nextElement;
        int cellNumber = 0;
        if (size == 0) {
            last = first = new Node(list.get(0));
            cellNumber = 1;
            size++;
        }
        for (int i = cellNumber; i < list.size(); i++) {
            nextElement = new Node(list.get(i));
            nextElement.prev = last;
            last.next = nextElement;
            last = nextElement;
            size++;
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return getIndexFrom(index).item;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        T oldCurrent = getIndexFrom(index).item;
        getIndexFrom(index).item = value;
        return oldCurrent;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        if (size == 1) {
            final T oldCurrent = first.item;
            first = last = null;
            size--;
            return oldCurrent;
        }
        if (index == 0) {
            final T oldFirst = first.item;
            first = first.next;
            first.prev = null;
            size--;
            return oldFirst;
        }
        if (index == size - 1) {
            final T oldLast = last.item;
            last = last.prev;
            last.next = null;
            size--;
            return oldLast;
        }
        final T oldCurrent = getIndexFrom(index).item;
        getIndexFrom(index).prev.next = getIndexFrom(index).next;
        getIndexFrom(index).next.prev = getIndexFrom(index).prev;
        size--;
        return oldCurrent;
    }

    @Override
    public boolean remove(T object) {
        if (first == null) {
            return false;
        }
        Node current = first;
        if (size == 1 && equalsCurrent(object, current)) {
            first = last = null;
            size--;
            return true;
        }
        for (int i = 0; i < size; i++) {
            if (i == 0 && equalsCurrent(object, current)) {
                first = first.next;
                first.prev = null;
                size--;
                return true;
            } else if (i == size - 1 && equalsCurrent(object, current)) {
                last = last.prev;
                last.next = null;
                size--;
                return true;
            } else if (equalsCurrent(object, current)) {
                current.prev.next = current.next;
                current.next.prev = current.prev;
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private class Node {
        private T item;
        private Node next;
        private Node prev;

        public Node(T item) {
            this.item = item;
        }
    }

    private Node getIndexFrom(int index) {
        Node current;
        if (index < size / 2) {
            current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = last;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private boolean equalsCurrent(T object, Node current) {
        return current.item == null && object == null
                || current.item != null && current.item.equals(object);
    }
}
