package edu.luxoft.datastructures.Stack;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStackTest {


    private Stack st = getStack();

    public abstract Stack getStack();

    @Before
    public void prepare(){
        st = new LinkedStack();
        st.push("aaa");
        st.push("bbbb");
        st.push("ccc");
        st.push("ddd");
        st.push("eee");
    }

    @Test
    public void push() {
        st.push("zzz");
        Assert.assertEquals(6,st.size());
        Assert.assertEquals("zzz",st.peek());
        Assert.assertEquals("zzz",st.pop());
        Assert.assertEquals(5,st.size());
    }

    @Test
    public void pop() {
        Assert.assertEquals("eee",st.pop());
        Assert.assertEquals("ddd",st.pop());
        Assert.assertEquals("ccc",st.pop());
    }

    @Test
    public void contains() {
        Assert.assertTrue(st.contains("bbbb"));
        Assert.assertFalse(st.contains("asas"));
    }

    @Test
    public void remove() {
        Assert.assertTrue(st.remove("bbbb"));
        Assert.assertTrue(st.remove("ccc"));
        Assert.assertEquals(3,st.size());
        Assert.assertEquals("eee",st.pop());
        Assert.assertEquals("ddd",st.pop());
        Assert.assertEquals("aaa",st.pop());
    }

    @Test
    public void testPushAndPop() {
        Stack stack = new ArrayStack();

        stack.push("A");
        stack.push("B");
        stack.push("C");

        // good test!!!!
        assertEquals(3, stack.size());
        assertEquals("C", stack.pop());
        assertEquals("B", stack.pop());
        assertEquals("A", stack.pop());
        assertEquals(0, stack.size());
    }

    @Test
    public void testPushAndPopWithGrow() {
        Stack stack = new ArrayStack();


        for (int i = 0; i < 10; i++) {
            char valueToAdd = (char) ('A' + i);
            stack.push(valueToAdd);
        }

        // F7 (step into), F8 (step over), F9 (resume)
        // good test!!!!
        assertEquals(10, stack.size());
        for (int i = 9; i >= 0; i--) {
            char expectedValue = (char) ('A' + i);

            assertEquals(expectedValue, stack.pop());
        }

        assertEquals(0, stack.size());
    }

    @Test
    public void testPushAndPeek() {
        Stack stack = new ArrayStack();

        stack.push("A");
        stack.push("B");
        stack.push("C");

        // good test!!!!
        assertEquals(3, stack.size());
        assertEquals("C", stack.peek());
        assertEquals(3, stack.size());
        assertEquals("C", stack.peek());
    }

    @Test(expected = NoSuchElementException.class)
    public void testPushThrowNoSuchElementIfSizeIsZero() {
        Stack stack = new ArrayStack();

        stack.pop();
    }
}
