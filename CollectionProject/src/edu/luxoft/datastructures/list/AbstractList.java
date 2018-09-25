package edu.luxoft.datastructures.list;

import java.util.Iterator;

public abstract class AbstractList implements List, Iterable{
    protected int size;

    protected class ListIterator implements Iterator{

        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public Object next() {
            return get(index++);
        }
    }

    @Override
    public boolean contains(Object value) {
        return indexOf(value)!=-1;
    }
    protected void validateIndex(int index){
        if (index > size-1 || index < 0){
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public abstract void clear();

    @Override
    public Iterator iterator() {
        return new ListIterator();
    }
}
