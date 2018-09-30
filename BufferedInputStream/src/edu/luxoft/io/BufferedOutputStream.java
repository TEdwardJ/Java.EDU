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
    public void write(byte[] b, int offset, int length) throws IOException {
        validateParameters(b, offset, length);
        int bytesToWrite = Math.min(length,b.length-offset);

        if (bytesToWrite>(DEFAULT_BUFFER_SIZE-index)){
            flush();
            outputStream.write(b,offset,length);
        }else if (bytesToWrite <= (DEFAULT_BUFFER_SIZE-index) && bytesToWrite > 1){
              System.arraycopy(b,offset,buffer,index,bytesToWrite);
              index += bytesToWrite;
              checkIfFlushNeeded();
            }else{
                for (int i = offset; i < offset + length; i++) {
                    write(b[i]);
                }
            }
    }

    private void validateParameters(byte[] b, int offset, int length) {
        if (length>b.length - offset || length < 0|| offset < 0){
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
        checkIfFlushNeeded();
        buffer[index++] = (byte) b;
    }

    private void checkIfFlushNeeded() throws IOException {
        if(index == DEFAULT_BUFFER_SIZE){
            flush();
            index = 0;
        }
    }

}