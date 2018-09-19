package edu.luxoft.datastructures.Stack;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class LinkedStackTest extends AbstractStackTest{

    @Override
    public Stack getStack() {
        return new LinkedStack();
    }
}