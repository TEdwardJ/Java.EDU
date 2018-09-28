package edu.luxoft.io;

import java.io.*;

public class BufferedOutputStream extends OutputStream{

    private static final int DEFAULT_BUFFER_SIZE = 5;

    private OutputStream outputStream;
    private byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
    private int index;
    private int count;

    public BufferedOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void write(byte[] b) throws IOException {
        write(b,0,b.length);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        validateParameters(b, off, len);
        for (int i = off; i < off + len; i++) {
              write(b[i]);
        }
    }

    private void validateParameters(byte[] b, int off, int len) {
        if (len>b.length - off || len < 0|| off < 0){
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public void flush() throws IOException {
        outputStream.write(buffer,0,index);
        index=0;
        outputStream.flush();
    }

    @Override
    public void close() throws IOException {
        flush();
        super.close();
        outputStream.close();
    }

    @Override
    public void write(int b) throws IOException {
        if(index == DEFAULT_BUFFER_SIZE){
            flush();
            index = 0;
        }
        buffer[index++] = (byte) b;
    }

}