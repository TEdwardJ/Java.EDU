package edu.luxoft.socketserver;

import java.io.*;
import java.net.Socket;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class HttpServlet {

    private static final String LINE_END = "\n";
    private static final String DEFAULT_RESOURCE = "index.html";

    private HTTPRequest request;
    private StringBuilder requestContent;
    private Socket socket;

    private BufferedReader socketReader;

    private static final Pattern methodPattern = Pattern.compile("^([A-Z]+)");
    private static final Pattern requestPattern = Pattern.compile("^(?<method>[A-Z]+) (?<resource>[^ ]+)");
    private static final Pattern hostPattern = Pattern.compile("Host: (?<host>[^:]+):(?<port>[0-9]+)");


    public HttpServlet(Socket  socket) {
        this.socket = socket;
    }

    public static String pathResolver(String path){
        if (path.equals("/")){
            return "resources\\webapp\\"+DEFAULT_RESOURCE;
        }
        //String fileName =
        if (path.contains("css")){
            return "resources\\webapp\\"+path;
        }
        if (path.contains(".htm")){
            return "resources\\webapp\\"+path;
        }
        return "resources\\webapp\\"+path;
    }

    public void process() throws IOException {
        try(BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedOutputStream socketWriter = new BufferedOutputStream(socket.getOutputStream()); ){
        readSocket(socketReader);
        request = parseRequest(requestContent.toString());
        if (request.getMethod().equals("GET")){
            doGet(request);
        }
        if (request.getMethod().equals("POST")){
            doPost(request);
        }
        sendResponse(socketWriter);
        socket.close();}
    }

    private void readSocket(BufferedReader socketReader) throws IOException {
            boolean processRequest = true;
            requestContent = new StringBuilder();
            while (processRequest) {
                String lineFromClient = socketReader.readLine();
                requestContent.append(lineFromClient+"\n");
                    if (lineFromClient.isEmpty()) {
                    processRequest = false;
                }
            }
    }

    private static HTTPRequest parseRequest(String request) {
        Matcher methodMatcher = requestPattern.matcher(request);

        HTTPRequest hr = new HTTPRequest();
        if (methodMatcher.find()){
            hr.setMethod(methodMatcher.group("method"));
            hr.setResource(methodMatcher.group("resource"));
        }

        Matcher hostMatcher = hostPattern.matcher(request);
        if (hostMatcher.find()){
            hr.setHost(hostMatcher.group("host"));
            hr.setPort(hostMatcher.group("port"));
        }

        System.out.println(hr);
        return hr;

    }

    private void openResourceAndSend(String path, OutputStream bufferedWriter) {
        File file = new File(pathResolver(path));
        if (file.exists()) {
            try (FileInputStream reader = new FileInputStream(file)) {
                int chars;
                int totalBytes = 0;
                byte[] array = new byte[32];
                while ((chars = reader.read(array)) != -1) {
                    totalBytes += chars;
                    bufferedWriter.write(array, 0, chars);
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
    }


    private void sendResponse(OutputStream socketWriter) throws IOException {
            StringBuilder response = new StringBuilder();
            response.append("HTTP/1.1 200 OK");
            response.append(LINE_END);
            response.append(LINE_END);
            response.append(Optional.ofNullable(request.getResponse()).orElse(""));
            socketWriter.write(response.toString().getBytes());
            openResourceAndSend(request.getResource(),socketWriter);
            socketWriter.flush();

    }

    public abstract void doGet(HTTPRequest request);
    public abstract void doPost(HTTPRequest request);

}
