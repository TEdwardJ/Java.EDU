package edu.luxoft.datastructures.map;

import edu.luxoft.datastructures.list.ArrayList;
import edu.luxoft.datastructures.list.List;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class HashMap<K,V> implements Map<K, V>, Iterable<HashMap.Entry> {

    private static final double LOAD_RATIO = 0.75d;
    private static final int INITIAL_CAPACITY = 8;

    private ArrayList[] chunkBucket;

    private class HashMapIterator<K, V> implements Iterator<HashMap.Entry<K, V>> {
        private int hashIdx=0, chunkIdx=0, index=0;

        @Override
        public boolean hasNext() {
            return index < HashMap.this.size();
        }

        @Override
        public HashMap.Entry<K, V> next() {
            HashMap.Entry<K, V> current;
            while(chunkBucket[hashIdx] == null||chunkIdx == chunkBucket[hashIdx].size()){
                hashIdx++;
                chunkIdx=0;
            }
            current = (HashMap.Entry<K, V>) chunkBucket[hashIdx].get(chunkIdx);
            chunkIdx++;
            index++;
            return current;
        }
    }

    public static class Entry<K, V>{
        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Entry<K ,V> entry = (Entry) o;
            return Objects.equals(getKey(), entry.getKey());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getKey());
        }

        @Override
        public String toString() {
            return "Entry{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }


    public HashMap() {
        //hash can be less than zero!!!!!
        chunkBucket = new ArrayList[INITIAL_CAPACITY];
    }

    private void putEntry(Entry<K, V> e){
        List currentList = getCurrentList(e.getKey());
        currentList.add(e);
    }

    private V putInternal(K key, V value, boolean changeFlag){
        Entry<K, V> oldEntry = getEntry(key);
        if(oldEntry != null){
            V result = oldEntry.getValue();
            if(changeFlag){
                oldEntry.setValue(value);
            }
            return result;
        }else {
            resize();
            Entry newEntry = new Entry(key, value);
            putEntry(newEntry);
            return null;
        }
    }

    @Override
    public V putIfAbsent(K key, V value) {
        return putInternal(key,value, false);
    }

    @Override
    public V put(K key, V value) {
        return putInternal(key,value, true);
    }

    private int getCurrentChunkIndex(Object key, int size) {
        int hash = key.hashCode();
        return (int) (hash%size*Math.signum(hash));
    }

    private List getCurrentList(K key) {
        int chunkIndex = getCurrentChunkIndex(key, chunkBucket.length);
        if(chunkBucket[chunkIndex]==null){
            chunkBucket[chunkIndex] = new ArrayList();
        }
        return chunkBucket[chunkIndex];
    }

    private Entry getEntry(K key){
        List currentList = getCurrentList(key);
        int idx = currentList.indexOf(new Entry(key,""));
        if (idx!=-1) {
            return (Entry) currentList.get(idx);
        }
        return null;
    }

    @Override
    public V get(K key) {
        Entry<K, V> tmpEntry = getEntry(key);
        if (tmpEntry!=null) {
            return tmpEntry.getValue();
        }
        return null;
    }

    @Override
    public V remove(K key) {
        List currentList = getCurrentList(key);
        int idx = currentList.indexOf(new Entry(key,""));
        V objToRemove;
        if (idx!=-1) {
            objToRemove = ((Entry<K, V>)currentList.get(idx)).getValue();
            currentList.remove(idx);
            return objToRemove;
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        List currentList = getCurrentList(key);
        if ((currentList.contains(new Entry(key,"")))){
            return true;
        }
        return false;
    }


    @Override
    public void putAll(HashMap<K, V> map) {
        List<K> keysList = map.keys();
        for (K key : keysList) {
            this.put(key,map.get(key));
        }
    }

    private Stream<Entry> getEntriesStream(){
        return Arrays
                .stream(chunkBucket)
                .filter(t->t!=null)
                .flatMap(b -> IntStream
                        .range(0,b.size())
                        .mapToObj(i->(Entry)b.get(i)));
    }

    public List getEntries() {
        ArrayList alResult =
                getEntriesStream()
                        .collect(ArrayList::new,(al,e)->{al.add(e);},(al,al2)->{});
        return alResult;
    }

    @Override
    public List keys() {
        ArrayList alResult =
                getEntriesStream()
                .map(t->t.getKey())
                .collect(ArrayList::new,(al,e)->{al.add(e);},(al,al2)->{});
        return alResult;
    }

    @Override
    public List values() {
        ArrayList alResult =
                getEntriesStream()
                        .map(t->t.getValue())
                        .collect(ArrayList::new,(al,e)->{al.add(e);},(al,al2)->{});
        return alResult;
    }

    @Override
    public int size() {
        return Arrays
                .stream(chunkBucket)
                .filter(t->t!=null)
                .mapToInt(t->t.size())
                .sum();
    }

    private void resize(){
        if(!validateSize()){
            return;
        }
        ArrayList[] newChunkBucket = new ArrayList[chunkBucket.length<<1];

        List entriesList = getEntries();

        chunkBucket = newChunkBucket;

        for (Object o : entriesList) {
            Entry e = (Entry)o;
            this.putEntry(e);
        }
    }

    private boolean validateSize(){
        return size()+1 >= chunkBucket.length * LOAD_RATIO;
    }

    @Override
    public Iterator iterator() {
        return new HashMapIterator();
    }
}
