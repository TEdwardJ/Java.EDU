package edu.luxoft.datastructures.list;

import java.util.Iterator;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Stream;

public class LinkedList<T> extends AbstractList<T> {
    private Node head;
    private Node tail;


    public static class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        private T value;

        public Node(T value) {
            this.value = value;
        }

        public void setPrev(Node<T> prev) {
            this.prev = prev;
        }

        public Node<T> getPrev() {
            return prev;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node newNode = new Node(value);
        if (size == 0) {
            head = tail = newNode;
        } else {
            reLinkLast(newNode);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index==size){
            add(value);
            return;
        }
        validateIndex(index);
        Node newNode = new Node(value);
        Node oldNode = getNode(index);
        reLink(oldNode, newNode, index);

        size++;
    }

    private void reLink(Node nodeAfter, Node newNode, int index) {
        if(index==0){
            reLinkFirst(newNode);
            return;
        }
        if(index==size){
            reLinkLast(newNode);
            return;
        }
        Node nodeBefore = nodeAfter.getPrev();
        newNode.setNext(nodeAfter);
        nodeAfter.setPrev(newNode);
        newNode.setPrev(nodeBefore);
        nodeBefore.setNext(newNode);
    }

    private void reLinkLast(Node newNode) {
        newNode.setPrev(tail);
        tail.setNext(newNode);
        tail = newNode;
    }

    private void reLinkFirst(Node newNode) {
        head.setPrev(newNode);
        newNode.setNext(head);
        head = newNode;
    }

    private void unLinkFirst() {
        (head = head.getNext()).setPrev(null);
    }

    private void unLinkLast() {
        (tail = tail.getPrev()).setNext(null);
    }

    private void unLink(Node node) {
        node.getPrev().setNext(node.getNext());
        node.getNext().setPrev(node.getPrev());
    }

    @Override
    public T remove(int index) {
        validateIndex(index);
        Node objToRemove = getNode(index);
        if(index==0){
            unLinkFirst();
            size--;
            return (T)objToRemove.getValue();
        }
        if(index==size-1){
            unLinkLast();
            size--;
            return (T)objToRemove.getValue();
        }
        unLink(objToRemove);
        size--;
        return (T)objToRemove.getValue();
    }


    @Override
    public T get(int index) {
        validateIndex(index);
        return getNode(index).getValue();
    }

    @Override
    public T set(T value, int index) {
        validateIndex(index);
        int cnt = 0;
        T tmp;
        for (Node<T> iNode = head; iNode != null; iNode = iNode.getNext()) {
            if (index == cnt) {
                tmp = iNode.getValue();
                iNode.setValue(value);
                return tmp;
            }
            cnt++;
        }
        return null;
    }

    @Override
    public void clear() {
        head = tail = null;
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size != 0;
    }



    @Override
    public int indexOf(Object value) {
        int cnt = 0;
        for (Node iNode = head; iNode != null; iNode = iNode.getNext()) {
            if (( value == null && iNode.getValue() == null) ||iNode.getValue().equals(value) ) {
                return cnt;
            }
            cnt++;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object value) {
        Node curNode = tail;
        for (int i=size-1;i>=0;i--){
            if (curNode.getValue().equals(value) || (curNode.getValue() == null && value == null)){
                return i;
            }else{
                curNode = curNode.getPrev();
            }
        }
        return -1;
    }

    private Node<T> getNode(int index) {
        Node<T> start;
        if (index + 1 <= size >> 1) {
            start = head;
            for (int i = 0; i < index; i++) {
                start = start.getNext();
            }
        } else {
            start = tail;
            for (int i = size - 1; i > index; i--) {
                start = start.getPrev();
            }
        }
        return start;
    }
    @Override
    public String toString() {
        StringJoiner joiner = Stream
                .iterate(head, Node::getNext)
                .limit(size())
                .map(Node::getValue)
                .map(Objects::toString)
                .collect(()->new StringJoiner(", ","ArrayList{","}"),StringJoiner::add, StringJoiner::merge);
        return joiner.toString();
    }

    @Override
    public Iterator iterator() {
        return new ListIterator();
    }
}
