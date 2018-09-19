package edu.luxoft.datastructures.list;

public class Node {
    private Node   prev;
    private Node   next;
    private Object value;

    public Node(Object value) {
        this.value = value;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public Node getPrev() {
        return prev;
    }

    public Object getValue() {
        return value;
    }
    public void setValue(Object value) {
        this.value = value;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
