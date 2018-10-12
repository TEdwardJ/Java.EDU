package edu.luxoft.socketserver;

import java.net.Socket;

public class MyRequestHandler extends RequestHandler {


    public MyRequestHandler(Socket socket, String webAppPath) {
        super(socket, webAppPath);
    }

    @Override
    public void doGet(HttpRequest request) {
        if (request.getResource().equals("/")){
            //request.setResponse("It is GET response");
        }
    }


    @Override
    public void doPost(HttpRequest request) {
        if (request.getResource().equals("/")){
            //request.setResponse("It is POST response");
        }
    }
}
