package edu.luxoft.datastructures.queue;

import java.util.Arrays;
import java.util.stream.IntStream;

public class ArrayQueue implements Queue {
    private static final int INITIAL_CAPACITY=4;
    private Object[] array;

    private int start;
    private int end;
    private int size;

    // [A, B, C, null, null] -> dequeue return A
    // [B, C, null, null, null]


    public ArrayQueue() {
        array = new Object[INITIAL_CAPACITY];
        start = 0;
        end = 0;
        size = 0;
    }

    private int cycleIndex(int index,int delta){
        if (index+delta>=array.length){
            return index+delta-array.length;
        }
        return index+delta;
    }

    @Override
    public void enqueue(Object value) {
        if (value == null)
            return;
        if (size+1 == array.length){
            resizeArray();
        }

        array[end] = value;
        end = cycleIndex(end,1);
        size++;
    }

    private void resizeArray() {
        int newSize = array.length*2;
        Object[] newArray = Arrays.copyOf(array, newSize);

        array = newArray;
    }

    @Override
    public Object dequeue() {
        Object res = null;
        res = peek();
        if (res != null){
            array[start]=null;
            start = cycleIndex(start,1);
            size--;
        }
        return res;
    }

    @Override
    public Object peek() {
        Object res = null;
        res = array[start];
        if(res==null && start!=end){
            start = cycleIndex(start,1);
            return peek();
        }
        return res;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean remove(Object value) {
        for (int i = 0; i < size; i++) {
            if (array[cycleIndex(start+i,0)].equals(value)){
                array[cycleIndex(start+i,0)] = null;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(Object value) {
        boolean result = IntStream.rangeClosed(start,end)
                .mapToObj(t->array[t])
                .anyMatch(t->t.equals(value));
        return result;
    }
}