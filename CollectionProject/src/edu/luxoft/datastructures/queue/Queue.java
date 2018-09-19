package edu.luxoft.datastructures.queue;

public interface Queue {
    void enqueue(Object value);

    Object dequeue();

    Object peek();

    int size();

    boolean remove(Object value);


    boolean contains(Object value);

    // =================================

     //Object max();
}