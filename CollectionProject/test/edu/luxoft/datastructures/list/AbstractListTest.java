package edu.luxoft.datastructures.list;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public abstract class AbstractListTest {

    List listWithZeroElements;
    List listWithFiveElements;
    List listWithTenElements;

    public abstract List getList();

    @Before
    public void before() {
        listWithZeroElements = getList();
        listWithFiveElements = getList();
        listWithTenElements = getList();

        listWithFiveElements.add("A");
        listWithFiveElements.add("B");
        listWithFiveElements.add("C");
        listWithFiveElements.add("D");
        listWithFiveElements.add("E");

        for (int i = 0; i < 10; i++) {
            listWithTenElements.add(i);
        }
    }

    @Test
    public void testGetFromListWithFiveElements() {
        assertEquals("A", listWithFiveElements.get(0));
        assertEquals("B", listWithFiveElements.get(1));
        assertEquals("C", listWithFiveElements.get(2));
        assertEquals("D", listWithFiveElements.get(3));
        assertEquals("E", listWithFiveElements.get(4));
    }

    @Test
    public void testGetFromListWithTenElements() {
        for (int i = 0; i < 10; i++) {
            assertEquals(i, listWithTenElements.get(i));
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetFromIndexLessThanZero() {
        listWithFiveElements.get(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetFromIndexGreaterThanSize() {
        listWithFiveElements.get(5);
    }

    @Test
    public void testSize() {
        assertEquals(5, listWithFiveElements.size());
        assertEquals(0, listWithZeroElements.size());
    }

    @Test
    public void testIndexOf() {
        assertEquals(2, listWithFiveElements.indexOf("C"));
        assertEquals(-1, listWithFiveElements.indexOf("X"));
    }

    @Test
    public void testLastIndexOf() {
        //assertEquals(2, listWithFiveElements.lastIndexOf("C"));
        assertEquals(4, listWithFiveElements.lastIndexOf("E"));
        assertEquals(1, listWithFiveElements.lastIndexOf("B"));
        assertEquals("B", listWithFiveElements.set("BB",1));
        assertEquals("BB", listWithFiveElements.get(1));
        assertEquals(0, listWithFiveElements.lastIndexOf("A"));
        String idx = "fdfdfd";
        idx.lastIndexOf("e");
    }

    @Test
    public void testClear()
    {
        listWithFiveElements.clear();
        Assert.assertEquals(0,listWithFiveElements.size());
    }

    @Test
    public void show(){
        System.out.println(listWithFiveElements);
    }

    @Test
    public void testRemove(){
        Assert.assertEquals("C",listWithFiveElements.remove(2));
        Assert.assertEquals(4,listWithFiveElements.size());
        Assert.assertEquals("A",listWithFiveElements.remove(0));
        Assert.assertEquals(3,listWithFiveElements.size());
        Assert.assertEquals("E",listWithFiveElements.remove(listWithFiveElements.size()-1));
        Assert.assertEquals(2,listWithFiveElements.size());
    }
    @Test
    public void testInsertBetween(){
        Assert.assertEquals(5,listWithFiveElements.size());
        listWithFiveElements.add("0000",0);
        Assert.assertEquals(6,listWithFiveElements.size());
        Assert.assertEquals("0000",listWithFiveElements.get(0));
        listWithFiveElements.add("VFVF",2);
        Assert.assertEquals(7,listWithFiveElements.size());
        Assert.assertEquals("VFVF",listWithFiveElements.get(2));
        Assert.assertEquals("C",listWithFiveElements.get(4));
        Assert.assertEquals("D",listWithFiveElements.get(5));
        listWithFiveElements.add("rerer",listWithFiveElements.size());
        Assert.assertEquals(8,listWithFiveElements.size());
        Assert.assertEquals("rerer",listWithFiveElements.get(listWithFiveElements.size()-1));

    }



}
