package edu.luxoft.socketserver;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;

public class ResponseWriter {
    private static final String HTTP_OK = "HTTP/1.1 200 OK";
    private static final String HTTP_BAD = "HTTP/1.1 404 Not Found";
    private static final String LINE_END = "\n";
    HttpRequest request;


    public ResponseWriter(HttpRequest request) {
        this.request = request;
    }

    private void sendResponse(String answer, OutputStream socketWriter, ResourceReader resource) throws IOException {
        StringBuilder response = new StringBuilder();
        response.append(answer);
        response.append(LINE_END);
        response.append(LINE_END);
        response.append(Optional.ofNullable(request.getResponse()).orElse(""));
        socketWriter.write(response.toString().getBytes());
        if (answer.equals(HTTP_OK)){
            resource.getResource(socketWriter);
        }
        socketWriter.flush();
        socketWriter.close();

    }

    public void writeSuccessResponse(OutputStream output, ResourceReader resource) throws IOException {
        sendResponse(HTTP_OK,output,resource);
    }

    public void writeBadResponse(OutputStream output) throws IOException {
        sendResponse(HTTP_BAD,output,null);
    }
}
