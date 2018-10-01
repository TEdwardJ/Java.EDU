package edu.luxoft.socketserver;

import java.net.Socket;

public class MyHttpServlet extends HttpServlet {

    public MyHttpServlet(Socket socket) {
        super(socket);
    }

    @Override
    public void doGet(HTTPRequest request) {
        if (request.getResource().equals("/")){
            //request.setResponse("It is GET response");
        }
    }

    @Override
    public void doPost(HTTPRequest request) {
        if (request.getResource().equals("/")){
            //request.setResponse("It is POST response");
        }

    }
}
