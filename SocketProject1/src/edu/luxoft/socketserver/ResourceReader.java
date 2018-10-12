package edu.luxoft.socketserver;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.*;

public class ResourceReader {

    private String webAppPath;
    private String fileName;

    public ResourceReader(String webAppPath, String fileName) {
        this.webAppPath = webAppPath;
        this.fileName = fileName;
    }

    private String pathResolver() {
        return webAppPath + fileName;
    }

    protected boolean isResourceExist(){
        File file = new File(pathResolver());
        return file.exists();
    }

    protected ByteOutputStream getResourceStream() {
        File file = new File(pathResolver());
        if (file.exists()) {
            try (ByteOutputStream byteResource = new ByteOutputStream()) {
                getResource(byteResource);
                return byteResource;
            }
        } else {
            System.out.println("Resource " + file + " not found");
        }
        return null;
    }

    public void getResource(OutputStream socketWriter) {
        File file = new File(pathResolver());
        if (file.exists()) {
            try (FileInputStream reader = new FileInputStream(file);) {
                int chars;
                int totalBytes = 0;
                byte[] array = new byte[128];
                while ((chars = reader.read(array)) != -1) {
                    totalBytes += chars;
                    socketWriter.write(array, 0, chars);
                }
                System.out.println("Total bytes sent " + totalBytes);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Resource " + file + " not found");
        }
        return;
    }
}
