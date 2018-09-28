package edu.luxoft.io;

import java.io.IOException;
import java.io.InputStream;

public class BufferedInputStream extends InputStream {
    private static final int DEFAULT_BUFFER_SIZE = 5;

    private InputStream inputStream;
    private byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
    private int index;
    private int count;

    public BufferedInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    private byte getByteFromBuffer(){
        byte value = buffer[index];
        index++;
        return value;
    }

    @Override
    public int read() throws IOException {
        if (index == count) {
            count = inputStream.read(buffer);
            index = 0;
        }
        if (count == -1) {
            return -1;
        }
        return getByteFromBuffer();
    }

    @Override
    public int read(byte[] b) throws IOException {
        return read(b,0,b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        validateParameters(b, off, len);
        int total = 0;
        byte curByte;
        for (int i = off; i < off+len; i++){
            if ((curByte = (byte)read()) != -1){
                b[i] = curByte;
                total++;
            }
            else{
                return (total==0)?-1:total;
            }
        }
        return total;
    }

    private void validateParameters(byte[] b, int off, int len) {
        if (len>b.length - off || len < 0|| off < 0){
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
    }
}