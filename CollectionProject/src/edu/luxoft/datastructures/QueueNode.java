package edu.luxoft.datastructures;

public interface QueueNode {

    public void setLinked(QueueNode prev);

    public QueueNode getLinked();

    public Object getValue();

}
