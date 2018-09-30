package edu.luxoft.log;


import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class LogAnalizerTest {

    private LogAnalizer la = new LogAnalizer();

    LocalDateTime from;//
    LocalDateTime to;//

    @Test
    public void prepare() throws FileNotFoundException {
        from = la.parseDate("07/Mar/2004:16:33:53");
        to = la.parseDate("07/Mar/2004:16:56:39");
        la.scan("C:\\JavaCourse\\\\apach.log",from,to).stream()
        .forEach(System.out::println);

    }

    @Test(expected = FileNotFoundException.class)
    public void unexistedFileTest() throws FileNotFoundException {
        from = la.parseDate("07/Mar/2004:16:33:53");
        to = la.parseDate("07/Mar/2004:16:56:39");
        la.scan("C:\\JavaCourse\\\\apach.log22",from,to).stream()
                .forEach(System.out::println);

    }
}