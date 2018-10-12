package edu.luxoft.socketserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestParser {

    private static final Pattern REQUEST_PATTERN = Pattern.compile("^(?<method>[A-Z]+) (?<resource>[^ ]+)");
    private static final Pattern HEADER_PATTERN = Pattern.compile("(?<header>[^:]+): (?<value>.+)$");

    private static void injectUrlAndHttpMethod(HttpRequest hr,String request){
        Matcher methodMatcher = REQUEST_PATTERN.matcher(request);

        if (methodMatcher.find()){
            hr.setMethod(HttpMethod.valueOf(methodMatcher.group("method")));
            hr.setResource(methodMatcher.group("resource"));
        }
    }

    private static void injectHeaders(HttpRequest httpRequest,String request){
        Arrays.stream(request.split("\\n"))
                .forEach(t->addHeader(httpRequest,t));
    }

    private static void addHeader(HttpRequest httpRequest, String line) {
        Matcher headerMatcher = HEADER_PATTERN.matcher(line);
        if (headerMatcher.find()){
            httpRequest.setHeader(headerMatcher.group("header"),
                                  headerMatcher.group("value"));

        }
    }

    static HttpRequest parseRequest(BufferedReader socketReader) throws IOException {
        String request = readRequest(socketReader);
        if (request.trim().equals("")){
            return null;
        }
        HttpRequest httpRequest = new HttpRequest();
        injectUrlAndHttpMethod(httpRequest, request);
        injectHeaders(httpRequest, request);
        return httpRequest;

    }

    private static String readRequest(BufferedReader socketReader) throws IOException {
        StringBuilder requestContent;
        boolean processRequest = true;
        requestContent = new StringBuilder();
        while (processRequest) {
            String lineFromClient = socketReader.readLine();
            if (lineFromClient == null || lineFromClient.isEmpty()) {
                processRequest = false;
            }
            else{
                requestContent.append(lineFromClient+"\n");

            }
        }
        return requestContent.toString();
    }
}
