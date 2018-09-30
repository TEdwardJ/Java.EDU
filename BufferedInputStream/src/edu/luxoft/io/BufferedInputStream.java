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
        checkIfIndexResetNeeded();
        if (count == -1) {
            return -1;
        }
        return getByteFromBuffer();
    }

    private void checkIfIndexResetNeeded() throws IOException {
        if (index == count) {
            count = inputStream.read(buffer);
            index = 0;
        }
    }

    @Override
    public int read(byte[] b) throws IOException {
        return read(b,0,b.length);
    }

    @Override
    public int read(byte[] b, int offset, int length) throws IOException {
        validateParameters(b, offset, length);
        checkIfIndexResetNeeded();
        if(count<0){
            return -1;
        }
        int bytesToRead = Math.min(length,b.length-offset);
        int restInBuffer = count-index;
        int factBytes = 0;
        if(restInBuffer > 0){
            factBytes = Math.min(restInBuffer, bytesToRead);
            //Let`s suppose that reading 1 byte from buffer would be easier without arraycopy
            if(factBytes == 1){
                b[offset] = (byte)read();
            }else{
                System.arraycopy(buffer, index, b, offset, factBytes);
                index += factBytes;
            }
            bytesToRead -= factBytes;
        }

        //If buffer is already empty but the client still needs more bytes from stream
        if (bytesToRead>0){
            int res = 0;
            if(bytesToRead > DEFAULT_BUFFER_SIZE){
                res = inputStream.read(b,offset+factBytes,bytesToRead);
            } else{
                res = read(b,offset+factBytes,bytesToRead);
            }
            if (res != -1){
                factBytes+=res;
            }
        }

        return factBytes;
    }


    private void validateParameters(byte[] b, int offset, int length) {
        if (length>b.length - offset || length < 0|| offset < 0){
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
    }
}