package edu.luxoft.datastructures.map;

import edu.luxoft.datastructures.list.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HashMapTest {

    Map map = new HashMap();
    Map map2 = new HashMap();
    Map emptyMap = new HashMap();
    Map twoValueMap = new HashMap();

    @Before
    public void prepare(){
        map.put("A","aa");
        map.put("B","bb");
        map.put("C","cc");
        map.put("D","dd");
        map.put("E","ee");
        map2.put("X","xx");
        map2.put("Y","yy");
        map2.put("Z","zz");
        map2.put("B","bbb");
        twoValueMap.put("0","qqq");
        twoValueMap.put("7","qzzzqq");
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

    @Test
    public void testKeys(){
        List keysList = map.keys();
        System.out.println(keysList);
        Assert.assertEquals(5,map.size());
        Assert.assertEquals(5,keysList.size());
        Assert.assertTrue(keysList.contains("A"));
        Assert.assertTrue(keysList.contains("B"));
        Assert.assertTrue(keysList.contains("C"));
        Assert.assertTrue(keysList.contains("D"));
        Assert.assertTrue(keysList.contains("E"));
        Assert.assertFalse(keysList.contains("EE"));
    }

    @Test
    public void testValues(){
        List valuesList = map.values();
        System.out.println(valuesList);
        Assert.assertEquals(5,map.size());
        Assert.assertEquals(5,valuesList.size());
        Assert.assertTrue(valuesList.contains("aa"));
        Assert.assertTrue(valuesList.contains("bb"));
        Assert.assertTrue(valuesList.contains("cc"));
        Assert.assertTrue(valuesList.contains("dd"));
        Assert.assertTrue(valuesList.contains("ee"));
        Assert.assertFalse(valuesList.contains("EE"));
    }

    @Test
    public void testPutAll(){
        map.putAll((HashMap)map2);
        Assert.assertEquals(8,map.size());
        Assert.assertEquals("bbb",map.get("B"));
    }

    @Test
    public void testWithResize(){
        map.putAll((HashMap)map2);
        map.put("T","ttt");
        map.put("R","rrr");
        map.put("W","www");

        Assert.assertEquals(11,map.size());
        Assert.assertEquals("www",map.get("W"));
    }

    @Test
    public void testMapIterator(){
        for (HashMap.Entry e: (HashMap)map) {
            System.out.println(e);
        }

        for (HashMap.Entry e: (HashMap)emptyMap) {
            System.out.println(e);
        }

        for (HashMap.Entry e: (HashMap) twoValueMap) {
            System.out.println(e);
        }
    }
}