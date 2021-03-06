package edu.luxoft.datastructures.list;

import java.util.Arrays;
import java.util.StringJoiner;
import java.util.stream.IntStream;

// TDD
// Ctrl + Shift + T
public class ArrayList implements List {
    private static final int INITIAL_CAPACITY = 8;
    private Object[] array = new Object[INITIAL_CAPACITY];
    private int size;

    @Override
    public void add(Object value) {
        if (checkBounds()){
            array[size] = value;
            size++;
        }
    }

    @Override
    public void add(Object value, int index) {
        if(index == size){
            add(value);
            return;
        }
        checkIndex(index);
        checkBounds();
        shiftArray(index,-1);
        array[index] = value;
        size++;
    }

    private void checkIndex(int index){
        if (index > size-1 || index < 0){
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public Object remove(int index) {
        checkIndex(index);
        Object objForRemove = array[index];
        shiftArray(index+1,1);
        array[size-1] = null;
        size--;
        return objForRemove;
    }

    @Override
    public Object get(int index) {
        checkIndex(index);
        return array[index];
    }

    @Override
    public Object set(Object value, int index) {
        checkIndex(index);
        Object objToReplace = array[index];
        array[index] = value;
        return objToReplace;
    }

    @Override
    public void clear() {
        array = new Object[array.length];
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object value) {
        return indexOf(value)!=-1;
    }

    @Override
    public int indexOf(Object value) {
        return IntStream
                .range(0,size)
                .filter(t->array[t].equals(value))
                .findFirst()
                .orElse(-1);
    }

    @Override
    public int lastIndexOf(Object value) {
        return IntStream
                .range(0,size)
                .map(t->size-1-t)
                .filter(t->array[t].equals(value))
                .findFirst()
                .orElse(-1);
    }

    private boolean checkBounds() {
        if (size<array.length){
            return true;
        }else{
            resizeArray();
            return checkBounds();
        }
    }

    private void resizeArray() {
        int newSize = array.length*2;
        Object[] newArray = Arrays.copyOf(array, newSize);

        array = newArray;
    }

    private void shiftArray(int position, int shiftSize) {
        System.arraycopy(array, position, array,position-shiftSize,size-position);
    }

    @Override
    public String toString() {

        StringJoiner joiner = Arrays.stream(array)
                .limit(size())
                .map(Object::toString)
                .collect(()->new StringJoiner(", ","ArrayList{","}"), StringJoiner::add, StringJoiner::merge);
        return joiner.toString();
    }
}