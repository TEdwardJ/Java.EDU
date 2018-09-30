package edu.luxoft.log;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogAnalizer {
    private static final String DATE_PATTERN = "d/MMM/yyyy:HH:mm:ss";

    LogDataSourceFactory logDataSourceFactory = new LogDataSourceFactory();

    LogDataSource fileSource;

    private String dateTimePattern = "\\[(?<datetime>.+) .+\\]";
    private String messagePattern = "["+'"'+"](?<method>GET|POST) (?<message>[^"+'"'+"]+)";
    private Pattern log = Pattern.compile(dateTimePattern+" "+messagePattern);

    public List<LogToken> scan(String fileName, LocalDateTime from, LocalDateTime to) throws FileNotFoundException {
        fileSource = logDataSourceFactory.getLogDataSource(fileName);
        List<LogToken> logList = new ArrayList<>();
        String line;
        LogToken token;

        try {
            while((line = fileSource.nextLogLine()) != null) {
                token = parseLogLine(line);
                if (validateLogLine(token,from,to)){
                    logList.add(token);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();//("Something wrong happend with File "+fileName);
        }
        return logList;
    }

    private boolean validateLogLine(LogToken token, LocalDateTime from, LocalDateTime to) {
        return token!=null&&
                ((token.getTime().isAfter(from)||
                token.getTime().isEqual(from))&&
                        (token.getTime().isBefore(to)||
                         token.getTime().isEqual(to)));
    }

    private LogToken parseLogLine(String logLine) {
        Matcher m = log.matcher(logLine);
        String method;
        HttpMethod methodType;
        String message;
        String dateString;
        LocalDateTime date;
        if(m.find()){
            method = m.group("method");
            dateString = m.group("datetime");
            message = m.group("message");
            methodType = HttpMethod.valueOf(method);
            date = parseDate(dateString);
            LogToken currentToken = new LogToken(methodType,message);
            currentToken.setTime(date);
            return currentToken;
        }
        return null;
    }

    public LocalDateTime parseDate(String dateString){
        DateTimeFormatter sf = DateTimeFormatter.ofPattern(DATE_PATTERN, Locale.ENGLISH);
        return LocalDateTime.parse(dateString,sf);
    }
}
