package edu.luxoft.datastructures.stack;

import edu.luxoft.datastructures.Node;
import edu.luxoft.datastructures.StackNode;

import java.util.function.BiFunction;

public class LinkedStack implements Stack {
    private int size;
    private StackNode tail;

    @Override
    public void push(Object value) {
        StackNode newNode = new Node(value);
        if (size != 0) {
            newNode.setPrev(tail);
        }
        tail = newNode;
        size++;
    }

    @Override
    public Object pop() {
        Object result = tail.getValue();
        tail = tail.getPrev();
        size--;
        return result;
    }

    @Override
    public Object peek() {
        Object result = tail.getValue();
        return result;
    }

    @Override
    public int size() {
        return size;
    }

    private Boolean removeCondition(StackNode curNode, StackNode curLinkedNode, Object val) {
        if (curLinkedNode.getValue().equals(val)){
            reLinkNode(curNode);
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(Object value) {
        return goThrough((node1,node2)->removeCondition(node1, node2, value));
    }

    private void reLinkNode(StackNode curNode){
        curNode.setPrev(curNode.getPrev().getPrev());
        size--;
    }

    private boolean goThrough(BiFunction<StackNode, StackNode, Boolean> function){
        StackNode pointerNode = tail;
        for(StackNode curNode = tail; curNode != null; curNode = (pointerNode = curNode).getPrev()){
            if (function.apply(pointerNode, curNode)){
                return true;
            }
        }
        return false;
    }

    private Boolean containsCondition(StackNode node1, StackNode node2, Object val) {
        return node2.getValue().equals(val);
    }

    @Override
    public boolean contains(Object value) {
        return goThrough((node1, node2)->containsCondition(node1, node2, value));
    }
}