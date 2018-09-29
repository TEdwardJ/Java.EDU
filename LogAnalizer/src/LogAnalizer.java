import java.io.*;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogAnalizer {
    private static final String DATE_PATTERN = "d/MMM/yyyy:HH:mm:ss";

    private File logFile;

    private FileReader fileReader;
    private BufferedReader logReader;

    private String dateTimePattern = new String ("\\[(?<datetime>.+) .+\\]");
    private String messagePattern = new String ("["+'"'+"](?<method>GET|POST) (?<message>[^"+'"'+"]+)");
    private Pattern log = Pattern.compile(dateTimePattern+" "+messagePattern);


    public List<LogToken> scan(String fileName, LocalDateTime from, LocalDateTime to){
        List<LogToken> logList = new ArrayList<>();
        try {
            openFile(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        try {
            String line;
            LogToken token;
            while((line = logReader.readLine())!=null&&!(token = parseLine(line)).getTime().isAfter(to)) {
                if(from.isBefore(token.getTime())||
                   from.isEqual(token.getTime())){
                    logList.add(token);
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try {
                logReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            fileReader.close();
        }
        return logList;
    }

    private LogToken parseLine(String logLine) {
        Matcher m = log.matcher(logLine);
        String method;
        HttpMethod methodType;
        String message;
        String dateString;
        LocalDateTime date;
        if(m.find()){
            method = m.group("method");
            methodType = HttpMethod.valueOf(method);
            dateString = m.group("datetime");
            date = parseDate(dateString);
            message = m.group("message");
            LogToken currentToken = new LogToken(methodType,message);
            currentToken.setTime(date);
            return currentToken;
        }
        return null;
    }

    private void openFile(String fileName) throws FileNotFoundException {
        logFile = new File(fileName);
        if(!logFile.exists()){
            throw new FileNotFoundException("File "+fileName+" has not been found");
        }
        try{
            fileReader = new FileReader(logFile);
            logReader = new BufferedReader(fileReader);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    public LocalDateTime parseDate(String dateString){
        DateTimeFormatter sf = DateTimeFormatter.ofPattern(DATE_PATTERN, Locale.ENGLISH);
        return LocalDateTime.parse(dateString,sf);
    }
}
