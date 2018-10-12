package edu.luxoft.socketserver;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.Assert.*;

public class RequestParserTest {

    @Test
    public void parseRequestTest() throws IOException {
        String request = "GET / HTTP/1.1\n" +
                "Host: 127.0.0.1:3000\n" +
                "Connection: keep-alive\n" +
                "Cache-Control: max-age=0\n" +
                "Upgrade-Insecure-Requests: 1\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\n" +
                "Accept-Encoding: gzip, deflate, br\n" +
                "Accept-Language: ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7\n" +
                "Cookie: JSESSIONID=zz9k37uf9kmz1hkgo51rdu369";
        HttpRequest httpRequest = RequestParser.parseRequest(new BufferedReader(new StringReader(request)));
        assertEquals("/",httpRequest.getResource());
        assertEquals("127.0.0.1:3000",httpRequest.getHost());
        Assert.assertEquals(HttpMethod.GET,httpRequest.getMethod());
        assertEquals("keep-alive",httpRequest.getHeader("Connection"));
    }
}