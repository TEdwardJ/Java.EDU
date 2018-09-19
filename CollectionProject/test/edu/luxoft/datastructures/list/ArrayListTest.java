package edu.luxoft.datastructures.list;

import static org.junit.Assert.assertEquals;

public class ArrayListTest extends AbstractListTest {

    @Override
    public List getList() {
        return new ArrayList();
    }
}