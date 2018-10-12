package edu.luxoft.socketserver;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class ResourceReaderTest {

    @Test
    public void getResourceTest() {
        String webAppPath = "resources\\webapp\\";
        File resource = new File(webAppPath,"index.html");
        int resourceSize = (int) resource.length();
        ResourceReader reader = new ResourceReader(webAppPath,"index.html");
        ByteOutputStream testStream = reader.getResourceStream();
        assertEquals(resourceSize,testStream.size());
        ResourceReader reader2 = new ResourceReader(webAppPath,"index2.html");
        ByteOutputStream testStream2 = reader2.getResourceStream();
        assertNull(testStream2);
    }
}