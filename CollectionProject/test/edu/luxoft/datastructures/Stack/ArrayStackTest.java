package edu.luxoft.datastructures.Stack;

import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;

public class ArrayStackTest  extends AbstractStackTest {

    @Override
    public Stack getStack() {
        return new ArrayStack();
    }
}