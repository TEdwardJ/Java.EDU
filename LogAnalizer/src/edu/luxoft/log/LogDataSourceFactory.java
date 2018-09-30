package edu.luxoft.log;

import java.io.FileNotFoundException;

public class LogDataSourceFactory {
    public LogDataSource getLogDataSource(String source) throws FileNotFoundException {
        if(source.startsWith("http")) {
            return getHttpLogDataSource(source);
        }else{
            return getFileLogDataSource(source);
        }
    }

    private LogDataSource getFileLogDataSource(String source) throws FileNotFoundException {
        FileLogDataSource fileDataSource = new FileLogDataSource();
        if (fileDataSource.open(source)) {
            return fileDataSource;
        }
        return null;
    }

    private LogDataSource getHttpLogDataSource(String source) throws FileNotFoundException {

        return null;
    }
}
