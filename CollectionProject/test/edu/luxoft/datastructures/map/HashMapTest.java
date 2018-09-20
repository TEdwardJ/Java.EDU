package edu.luxoft.datastructures.map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HashMapTest {

    Map map = new HashMap();

    @Before
    public void prepare(){
        map.put("A","aa");
        map.put("B","bb");
        map.put("C","cc");
        map.put("D","dd");
        map.put("E","ee");
    }

    @Test
    public void put() {
        Assert.assertNull(map.put("N","nn"));
        Assert.assertNotNull(map.put("N","xx"));
        Assert.assertEquals("xx",map.get("N"));
        Assert.assertEquals("xx",map.put("N",null));
        Assert.assertNull("xx",map.get("N"));
    }

    @Test
    public void get() {
        Assert.assertEquals("aa",map.get("A"));
        Assert.assertEquals("ee",map.get("E"));
    }

    @Test
    public void remove() {
        Assert.assertEquals("bb",map.remove("B"));
        Assert.assertNull(map.remove("B"));
        Assert.assertNull(map.get("B"));
    }

    @Test
    public void containsKey() {
        Assert.assertTrue(map.containsKey("A"));
        Assert.assertFalse(map.containsKey("bb"));
        map.remove("A");
        Assert.assertFalse(map.containsKey("A"));

    }

    @Test
    public void size() {
        Assert.assertEquals(5,map.size());
        map.remove("B");
        Assert.assertEquals(4,map.size());
    }
}