package edu.luxoft.socketserver;

import java.io.*;
import java.net.ServerSocket;
import java.util.regex.Pattern;

public class Server {
    private static final String LINE_END = "\n";
    private static final String DEFAULT_RESOURCE = "index.html";
    private static final Pattern methodPattern = Pattern.compile("^([A-Z]+)");
    private static final Pattern requestPattern = Pattern.compile("^(?<method>[A-Z]+) (?<resource>[^ ]+)");
    private static final Pattern hostPattern = Pattern.compile("Host: (?<host>[^:]+):(?<port>[0-9]+)");


    public static void main(String[] args) throws IOException {
        if (args.length !=2) {
            return;
        }
        boolean multithreading = args[0].equals("1");
        String webAppPath = args[1];
        if(multithreading){
            System.out.println("Multithreading env");
        }
        try (ServerSocket serverSocket = new ServerSocket(3000);) {
            while (true) {
                RequestHandler servlet = new MyRequestHandler(serverSocket.accept(), webAppPath);
                if (multithreading) {
                    (new Thread(servlet)).start();
                } else {
                    servlet.run();
                }
            }
        }
    }
}
