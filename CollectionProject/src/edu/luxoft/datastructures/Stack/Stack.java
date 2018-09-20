package edu.luxoft.datastructures.stack;

public interface Stack {

    // add to the end of the stack
    void push(Object value);

    // remove element from the end return
    Object pop();

    // return element from the end
    Object peek();

    int size();

    // ===============================

    // remove from stack
    boolean remove(Object value);

    // check if element present in stack
    boolean contains(Object value);

}