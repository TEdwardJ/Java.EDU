package edu.luxoft.socketserver;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private HttpMethod method;
    private String resource;

    private Map<String, String> headers = new HashMap<>();
    private String response;
    private String host;
    private String port;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String content;

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getHost() {
        return headers.get("Host");
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setHeader(String header,String value){
        headers.put(header,value);
    }

    @Override
    public String toString() {
        return "edu.luxoft.socketserver.HttpRequest{" +
                "method='" + method + '\'' +
                ", resource='" + resource + '\'' +
                ", host='" + host + '\'' +
                ", port='" + port + '\'' +
                '}';
    }
}
