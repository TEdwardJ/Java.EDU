package edu.luxoft.datastructures.list;


import org.junit.Assert;
import org.junit.Test;

public class LinkedListTest extends AbstractListTest{

    @Override
    public List getList() {
        return new LinkedList();
    }

    @Test
    public void testNull(){
        Assert.assertEquals(5,listWithFiveElements.size());
        this.listWithFiveElements.add(null);
        Assert.assertEquals(6,listWithFiveElements.size());
        Assert.assertNull(listWithFiveElements.get(listWithFiveElements.size()-1));
        Assert.assertTrue(listWithFiveElements.contains(null));
    }
}