package edu.luxoft.socketserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Pattern;

public class Server {
    private static final String LINE_END = "\n";
    private static final String DEFAULT_RESOURCE = "index.html";
    private static final Pattern methodPattern = Pattern.compile("^([A-Z]+)");
    private static final Pattern requestPattern = Pattern.compile("^(?<method>[A-Z]+) (?<resource>[^ ]+)");
    private static final Pattern hostPattern = Pattern.compile("Host: (?<host>[^:]+):(?<port>[0-9]+)");



    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(3000);) {
            while (true){
            try (Socket socket = serverSocket.accept();  ) {
                HttpServlet servlet = new MyHttpServlet(socket);
                servlet.process();
                }

            }
        }
        }



}
