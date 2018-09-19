package edu.luxoft.datastructures.queue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LinkedQueueTest {

    Queue aq = new LinkedQueue();

    @Before
    public void prepareData(){
        aq.enqueue("aaa");
        aq.enqueue("bbb");
        aq.enqueue("ccc");
        aq.enqueue("ddd");
        aq.enqueue("eee");
        aq.enqueue("fff");
    }

    @Test
    public void testQueueRemoving() {
        System.out.println("Removing----");
        Assert.assertTrue(aq.remove("fff"));
        Assert.assertEquals(5,aq.size());
        Assert.assertTrue(aq.remove("eee"));
        Assert.assertEquals(4,aq.size());
        Assert.assertTrue(aq.remove("aaa"));
        Assert.assertEquals(3,aq.size());
        Assert.assertFalse(aq.remove("bbbs"));
        Assert.assertEquals("aaa",aq.dequeue());
        System.out.println("size="+aq.size());
    }

    @Test
    public void testQueue(){

        Assert.assertTrue(aq.contains("eee"));
        Assert.assertFalse(aq.contains("ee"));
        Assert.assertEquals(6,aq.size());
        Assert.assertEquals("aaa",aq.dequeue());
        Assert.assertEquals(5,aq.size());
        Assert.assertEquals("bbb",aq.dequeue());
        Assert.assertEquals(4,aq.size());
        Assert.assertEquals(true,aq.remove("fff"));
        Assert.assertEquals(3,aq.size());
        Assert.assertEquals("ccc",aq.dequeue());
        Assert.assertEquals(2,aq.size());
        Assert.assertEquals("ddd",aq.dequeue());
        Assert.assertEquals(1,aq.size());
        Assert.assertEquals("eee",aq.dequeue());
        System.out.println(aq.size());
    }
}
