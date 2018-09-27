package edu.luxoft.datastructures.list;

import java.util.Iterator;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Stream;

public class LinkedList extends AbstractList {
    private Node head;
    private Node tail;


    public static class Node {
        private Node prev;
        private Node next;
        private Object value;

        public Node(Object value) {
            this.value = value;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }

        public Node getPrev() {
            return prev;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

    @Override
    public void add(Object value) {
        Node newNode = new Node(value);
        if (size == 0) {
            head = tail = newNode;
        } else {
            reLinkLast(newNode);
        }
        size++;
    }

    @Override
    public void add(Object value, int index) {
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
    public Object remove(int index) {
        validateIndex(index);
        Node objToRemove = getNode(index);
        if(index==0){
            unLinkFirst();
            size--;
            return objToRemove.getValue();
        }
        if(index==size-1){
            unLinkLast();
            size--;
            return objToRemove.getValue();
        }
        unLink(objToRemove);
        size--;
        return objToRemove.getValue();
    }


    @Override
    public Object get(int index) {
        validateIndex(index);
        return getNode(index).getValue();
    }

    @Override
    public Object set(Object value, int index) {
        validateIndex(index);
        int cnt = 0;
        Object tmp;
        for (Node iNode = head; iNode != null; iNode = iNode.getNext()) {
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

    private Node getNode(int index) {
        Node start;
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
