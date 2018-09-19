package edu.luxoft.datastructures.queue;

import edu.luxoft.datastructures.Node;
import edu.luxoft.datastructures.QueueNode;

import java.util.NoSuchElementException;
import java.util.function.BiFunction;

public class LinkedQueue implements Queue {

    private int size;
    private QueueNode head;
    private QueueNode tail;

    @Override
    public void enqueue(Object value) {
        if (value == null){
            throw new NullPointerException();
        }
        QueueNode newNode = new Node(value);
        if (size > 0){
            tail.setLinked(newNode);
        } else{
            head = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public Object dequeue() {
        if(size==0){
            return new NoSuchElementException();
        }
        QueueNode res = head;
        head = res.getLinked();
        size--;

        if (size == 0){
            tail = head = null;
        }
        return res.getValue();
    }

    @Override
    public Object peek() {
        return head.getValue();
    }

    @Override
    public int size() {
        return size;
    }

    private Boolean removeCondition(QueueNode curNode,  QueueNode curLinkedNode, Object val) {
        if (curLinkedNode.getValue().equals(val)){
            reLinkNode(curNode);
            return true;
        }
        return false;
    }
    @Override
    public boolean remove(Object value) {
         return goThroughQueue((node1,node2)->removeCondition(node1, node2, value));
    }

    private void reLinkNode(QueueNode curNode){
        curNode.setLinked(curNode.getLinked().getLinked());
        size--;
    }

    private boolean goThroughQueue(BiFunction<QueueNode, QueueNode, Boolean> function){
        if(size==0){
            return false;
        }
        QueueNode currentNode;
        QueueNode  nextNode = new Node(null);
        nextNode.setLinked(head);

        boolean exitLooping = false;
        do {
            currentNode = nextNode;
            nextNode = nextNode.getLinked();
        }while (nextNode.getLinked() != null & !(exitLooping = function.apply(currentNode, nextNode)));
        return exitLooping;
    }

    private Boolean containsCondition(QueueNode node1, QueueNode node2, Object val) {
        return node2.getValue().equals(val);
    }

    @Override
    public boolean contains(Object value) {
        return goThroughQueue((node1,node2)->containsCondition(node1, node2, value));
    }

}
