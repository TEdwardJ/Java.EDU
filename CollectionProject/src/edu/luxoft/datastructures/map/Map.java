package edu.luxoft.datastructures.map;


import edu.luxoft.datastructures.list.List;

import java.util.Iterator;

public interface Map<K, V> {
    V put(K key, V value);

    V get(K key);

    V remove(K key);

    boolean containsKey(K key);

    V putIfAbsent(K key, V value);

    void putAll(HashMap<K, V> map);

    List keys();
    List values();


    int size();

    Iterator iterator();
}