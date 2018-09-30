package edu.luxoft.io;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class BufferedInputStreamTest {
    byte[] data;
    MyTestByteArrayInputStream byteArrayInputStream;
    BufferedInputStream bufferedInputStream;


    @Before
    public void prepareStream(){
        data = new byte[]{1, 2, 3, 4, 5};
        byteArrayInputStream = new MyTestByteArrayInputStream(data);
        bufferedInputStream = new BufferedInputStream(byteArrayInputStream);
    }

    @Test
    public void testReadEmpty() throws IOException {
        data = new byte[]{};
        byteArrayInputStream = new MyTestByteArrayInputStream(data);
        bufferedInputStream = new BufferedInputStream(byteArrayInputStream);
        assertEquals(-1,bufferedInputStream.read());
        assertEquals(-1,bufferedInputStream.read());
    }

    @Test
    public void testRead() throws IOException {

        assertEquals(1, bufferedInputStream.read());
        byte[] byteArray = new byte[2];
        bufferedInputStream.read(byteArray);
        assertEquals(2, byteArray.length);
        assertArrayEquals(new byte[]{2,3},byteArray);
        assertEquals(4, bufferedInputStream.read());
        assertEquals(5, bufferedInputStream.read());
        assertEquals(-1, bufferedInputStream.read());

        assertEquals(2, byteArrayInputStream.calledCount);

    }

    @Test
    public void testReadWithOffsetandLength() throws IOException {
        byte[] byteArray = new byte[3];
        assertEquals(2,bufferedInputStream.read(byteArray,0,2));
        assertArrayEquals(new byte[]{1,2,0},byteArray );
        assertEquals(2,bufferedInputStream.read(byteArray,1,2));
        assertArrayEquals(new byte[]{1,3,4}, byteArray);
        assertEquals(1,bufferedInputStream.read(byteArray,0,3));
        assertArrayEquals(new byte[]{5,3,4}, byteArray);
        assertEquals(-1, bufferedInputStream.read());
        assertEquals(2, byteArrayInputStream.calledCount);
        prepareStream();
        byte[] byteArrayFull = new byte[5];
        assertEquals(5, bufferedInputStream.read(byteArrayFull,0,5));
        assertArrayEquals(new byte[]{1,2,3,4,5}, byteArrayFull);
        assertEquals(-1, bufferedInputStream.read());
    }

    private static class MyTestByteArrayInputStream extends ByteArrayInputStream {
        private int calledCount;

        public MyTestByteArrayInputStream(byte[] buf) {
            super(buf);
        }

        @Override
        public int read(byte[] b) throws IOException {
            calledCount++;
            return super.read(b);
        }
    }
}

