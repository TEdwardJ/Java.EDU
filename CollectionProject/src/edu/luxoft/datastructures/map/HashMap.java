package edu.luxoft.datastructures.map;

import edu.luxoft.datastructures.list.ArrayList;
import edu.luxoft.datastructures.list.List;

import java.util.Arrays;
import java.util.Objects;

public class HashMap implements Map {

    private ArrayList[] hashPartitions;

    public class Entry{
        private Object key;
        private Object value;

        public Entry(Object key, Object value) {
            this.key = key;
            this.value = value;
        }

        public Object getKey() {
            return key;
        }

        public void setKey(Object key) {
            this.key = key;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
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
            Entry entry = (Entry) o;
            return Objects.equals(getKey(), entry.getKey());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getKey());
        }
    }


    public HashMap() {
        hashPartitions = new ArrayList[5];
        for (int i = 0; i < hashPartitions.length; i++) {
            hashPartitions[i] = new ArrayList();
        }
    }

    @Override
    public Object put(Object key, Object value) {
        Entry newEntry = new Entry(key,value);
        Entry oldEntry = getEntry(key);
        if(oldEntry != null){
            Object result = oldEntry.getValue();
            oldEntry.setValue(value);
            return result;
        }else {
            List currentList = getCurrentList(key);
            currentList.add(newEntry);
            return null;
        }
    }

    private List getCurrentList(Object key) {
        int hash = key.hashCode();
        return hashPartitions[hash%hashPartitions.length];
    }

    private Entry getEntry(Object key){
        List currentList = getCurrentList(key);
        int idx = currentList.indexOf(new Entry(key,""));
        if (idx!=-1) {
            return (Entry) currentList.get(idx);
        }
        return null;
    }

    @Override
    public Object get(Object key) {
        Entry tmpEntry = getEntry(key);
        if (tmpEntry!=null) {
            return tmpEntry.getValue();
        }
        return null;
    }

    @Override
    public Object remove(Object key) {
        List currentList = getCurrentList(key);
        int idx = currentList.indexOf(new Entry(key,""));
        Object objToRemove;
        if (idx!=-1) {
            objToRemove = ((Entry)currentList.get(idx)).getValue();
            currentList.remove(idx);
            return objToRemove;
        }
        return null;
    }

    @Override
    public boolean containsKey(Object key) {
        List currentList = getCurrentList(key);
        if ((currentList.contains(new Entry(key,"")))){
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return Arrays
                .stream(hashPartitions)
                .mapToInt(t->t.size())
                .sum();
    }
}
