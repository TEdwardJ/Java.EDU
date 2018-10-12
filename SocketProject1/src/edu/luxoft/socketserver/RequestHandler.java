package edu.luxoft.socketserver;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.*;
import java.net.Socket;

public abstract class RequestHandler implements Runnable{

    private static final String LINE_END = "\n";
    private static final String DEFAULT_RESOURCE = "index.html";
    private static final String ROOT = "/";
    private String webAppPath;


    private HttpRequest request;

    private Socket socket;

    public RequestHandler(Socket socket, String webAppPath) {
        this.socket = socket;
        this.webAppPath = webAppPath;
    }

    public void handle() throws IOException {
        try (BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedOutputStream socketWriter = new BufferedOutputStream(socket.getOutputStream());){

        if((request = RequestParser.parseRequest(socketReader))== null){
            return;
        }
        if (request.getMethod().equals(HttpMethod.GET)) {
            doGet(request);
        } else if (request.getMethod().equals(HttpMethod.POST)) {
            doPost(request);
        }
        ResourceReader resource = new ResourceReader(webAppPath,request.getResource().equals(ROOT)?DEFAULT_RESOURCE:request.getResource());
        ResponseWriter responseWriter = new ResponseWriter(request);
        if (resource.isResourceExist()) {
            responseWriter.writeSuccessResponse(socketWriter, resource);
        }else{
            responseWriter.writeBadResponse(socketWriter);
        }
        }
    }


    public abstract void doGet(HttpRequest request);
    public abstract void doPost(HttpRequest request);

    @Override
    public void run() {
        try {
            System.out.println("Thread "+Thread.currentThread().getId()+" is run ");
            handle();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
