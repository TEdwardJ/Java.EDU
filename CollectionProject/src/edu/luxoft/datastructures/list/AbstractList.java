package edu.luxoft.datastructures.list;

import java.util.Iterator;

public abstract class AbstractList<T> implements List<T>, Iterable<T>{
    protected int size;

    protected class ListIterator<T> implements Iterator<T>{

        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public T next() {
            return (T)get(index++);
        }
    }

    @Override
    public boolean contains(T value) {
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
    public Iterator<T> iterator() {
        return new ListIterator();
    }
}
