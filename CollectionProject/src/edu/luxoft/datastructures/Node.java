package edu.luxoft.datastructures;

public class Node implements StackNode, QueueNode {
    private Node   prev;
    private Object value;

    public Node(Object value) {
        this.value = value;
    }

    public void setPrev(StackNode prev) {
        this.prev = (Node)prev;
    }

    public Node getPrev() {
        return prev;
    }

    public void setLinked(QueueNode prev) {
        setPrev((Node)prev);
    }

    public Node getLinked() {
        return getPrev();
    }

    public Object getValue() {
        return value;
    }


}
