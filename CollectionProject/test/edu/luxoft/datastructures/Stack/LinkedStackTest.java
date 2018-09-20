package edu.luxoft.datastructures.stack;

public class LinkedStackTest extends AbstractStackTest{

    @Override
    public Stack getStack() {
        return new LinkedStack();
    }
}