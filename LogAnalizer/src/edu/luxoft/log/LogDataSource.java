package edu.luxoft.log;

import java.io.IOException;

public interface LogDataSource {
    public String nextLogLine() throws IOException;

 }
