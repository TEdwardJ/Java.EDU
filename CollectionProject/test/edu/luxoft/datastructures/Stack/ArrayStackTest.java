package edu.luxoft.datastructures.stack;

import static org.junit.Assert.assertEquals;

public class ArrayStackTest  extends AbstractStackTest {

    @Override
    public Stack getStack() {
        return new ArrayStack();
    }
}