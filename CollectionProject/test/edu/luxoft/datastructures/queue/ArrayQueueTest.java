package edu.luxoft.datastructures.queue;

import org.junit.Assert;
import org.junit.Test;

public class ArrayQueueTest {

    @Test
    public void testQueue(){
    ArrayQueue aq = new ArrayQueue();
        aq.enqueue("aaa");
        aq.enqueue("bbb");
        aq.enqueue("ccc");
        aq.enqueue("ddd");
        aq.enqueue("eee");
        aq.enqueue("fff");
        Assert.assertEquals("aaa",aq.dequeue());
        Assert.assertEquals("bbb",aq.dequeue());
        aq.remove("ccc");
        Assert.assertEquals("ddd",aq.dequeue());
    }
}
