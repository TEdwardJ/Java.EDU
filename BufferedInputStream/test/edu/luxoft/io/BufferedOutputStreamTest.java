package edu.luxoft.io;

import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class BufferedOutputStreamTest {

    //private byte[] data = new byte[10];
    private MyTestByteArrayOutputStream byteArrayOutputStream;
    private BufferedOutputStream bufferedOutputStream;


    @Before
    public void prepareStream(){
        byteArrayOutputStream = new MyTestByteArrayOutputStream(20);
        bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
    }

    @Test
    public void write() throws IOException {
        bufferedOutputStream.write(new byte[]{1,2,3});
        assertArrayEquals(new byte[]{},byteArrayOutputStream.toByteArray());
        bufferedOutputStream.write(new byte[]{4,5,6});
        assertArrayEquals(new byte[]{1,2,3,4,5,6},byteArrayOutputStream.toByteArray());
        bufferedOutputStream.flush();
        assertArrayEquals(new byte[]{1,2,3,4,5,6},byteArrayOutputStream.toByteArray());
        bufferedOutputStream.write(new byte[]{1,2,3});
        assertArrayEquals(new byte[]{1,2,3,4,5,6},byteArrayOutputStream.toByteArray());
        bufferedOutputStream.flush();
        assertArrayEquals(new byte[]{1,2,3,4,5,6,1,2,3},byteArrayOutputStream.toByteArray());
    }

    @Test
    public void writeWithOffsetAndLength() throws IOException {
        byte[] bArray = new byte[]{1,2,3,4,5,6};
        bufferedOutputStream.write(bArray,0,2);
        bufferedOutputStream.flush();
        assertArrayEquals(new byte[]{1,2},byteArrayOutputStream.toByteArray());
        bufferedOutputStream.write(bArray,2,2);
        bufferedOutputStream.flush();
        assertArrayEquals(new byte[]{1,2,3,4},byteArrayOutputStream.toByteArray());


    }

    @Test
    public void linkedInputOutputStreams(){
            File f1 = new File("c:\\JavaCourse\\FA.txt");
            File f2 = new File("c:\\JavaCourse\\FA2.txt");
            if (f2.exists()){
                f2.delete();
            }
        FileInputStream inputFile;
        BufferedInputStream bufferedInput;
        FileOutputStream outputFile;
        BufferedOutputStream bufferedOutput;
        try{
            inputFile = new FileInputStream(f1);
            bufferedInput = new BufferedInputStream(inputFile);
            outputFile = new FileOutputStream(f2);
            bufferedOutput = new BufferedOutputStream(outputFile);
            byte[] b = new byte[5];
            int res;
            while((res = (byte)bufferedInput.read(b)) != -1){
                bufferedOutput.write(b,0,res);
            }
            inputFile.close();
            bufferedInput.close();
            bufferedOutput.close();
            outputFile.close();
             assertEquals(f1.length(),f2.length());
            assertTrue(f1.length()>0&&f2.length()>0);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void flush() throws IOException {
        bufferedOutputStream.write(new byte[]{1,2,3});
        assertArrayEquals(new byte[]{},byteArrayOutputStream.toByteArray());
        bufferedOutputStream.flush();
        assertArrayEquals(new byte[]{1,2,3},byteArrayOutputStream.toByteArray());
    }

    @Test
    public void writeOneByte() throws IOException {
        bufferedOutputStream.write(50);
        assertArrayEquals(new byte[]{},byteArrayOutputStream.toByteArray());
        bufferedOutputStream.flush();
        bufferedOutputStream.write(55);
        bufferedOutputStream.flush();
        assertArrayEquals(new byte[]{50,55},byteArrayOutputStream.toByteArray());
    }


    private static class MyTestByteArrayOutputStream extends ByteArrayOutputStream {
        private int calledCount;

        public MyTestByteArrayOutputStream(int buf) {
            super(buf);
        }

        @Override
        public void write(byte[] b) throws IOException {
            calledCount++;
            super.write(b);
        }
    }
}