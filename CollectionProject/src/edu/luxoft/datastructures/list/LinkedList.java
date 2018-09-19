package edu.luxoft.datastructures.list;

import java.util.stream.Stream;

public class LinkedList implements List {
    private Node head;
    private Node tail;
    private int size;

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
        checkIndex(index);
        Node newNode = new Node(value);
        Node oldNode = getNode(index);
        reLink(oldNode, newNode, index);

        size++;
    }

    private void checkIndex(int index) {
        if (index > size - 1 || index < 0) {
            throw new IndexOutOfBoundsException();
        }
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
        checkIndex(index);
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
        checkIndex(index);
        return getNode(index).getValue();
    }

    @Override
    public Object set(Object value, int index) {
        checkIndex(index);
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
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size != 0;
    }

    @Override
    public boolean contains(Object value) {
        return indexOf(value) != -1;
    }

    @Override
    public int indexOf(Object value) {
        int cnt = 0;
        for (Node iNode = head; iNode != null; iNode = iNode.getNext()) {
            if (iNode.getValue().equals(value)) {
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
            if (curNode.getValue().equals(value)){
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
        StringBuilder str = Stream
                .iterate(head, t -> t.getNext())
                .limit(size)
                .collect(StringBuilder::new, (t,s)->t.append(", ").append(s.getValue()), StringBuilder::append);
        return str
                .delete(0,2)
                .insert(0,"LinkedList{")
                .append('}')
                .toString();
    }

}
