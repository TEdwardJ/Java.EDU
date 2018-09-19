package edu.luxoft.datastructures.Stack;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ArrayStack implements Stack {
    private Object[] array;
    private int size;
    private final int initialCapacity = 5;


    public ArrayStack() {
        array = new Object[initialCapacity];
    }

    @Override
    public void push(Object value) {
        if (checkBounds()){
            array[size] = value;
            size++;
        }
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

    @Override
    public Object pop() {
        if (size == 0){
            throw new NoSuchElementException("Stack has zero size");
        } else {
            Object result = array[size - 1];
            array[size-1] = null;
            size--;
            return result;
        }
    }

    @Override
    public Object peek() {
        return array[size-1];
    }

    @Override
    public int size() {
        return size;
    }

    private void shiftArray(int position, int shiftSize) {
        for (int j = position; j < size-1; j++) {
            if (j < size-shiftSize){
                array[j]=array[j+shiftSize];
            }else{
                array[j] = null;
            }
        }
        size-=shiftSize;
    }

    @Override
    public boolean remove(Object value) {
        for (int i = 0; i < size; i++) {
            if (array[i] == value){
                shiftArray(i,1);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(Object value) {
        boolean result = Arrays
                .stream(array)
                .anyMatch(t->t.equals(value));
        return result;
    }
}