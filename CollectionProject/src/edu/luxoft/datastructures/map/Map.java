package edu.luxoft.datastructures.map;


import edu.luxoft.datastructures.list.List;

import java.util.Iterator;

public interface Map {
    Object put(Object key, Object value);

    Object get(Object key);

    Object remove(Object key);

    boolean containsKey(Object key);

    Object putIfAbsent(Object key, Object value);

    void putAll(HashMap map);

    List keys();
    List values();


    int size();

    Iterator iterator();
}