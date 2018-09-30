package edu.luxoft.log;

import java.io.*;

public class FileLogDataSource implements LogDataSource{

    private File logFile;

    private FileReader fileReader;
    private BufferedReader logReader;

    @Override
    public String nextLogLine() throws IOException {
        String line;
        line = logReader.readLine();
        if (line == null){
            close();
        }
        return line;
    }

    public boolean open(String fileName) throws FileNotFoundException {
        logFile = new File(fileName);
        if(!logFile.exists()){
            throw new FileNotFoundException("File "+fileName+" has not been found");
        }
        try{
            fileReader = new FileReader(logFile);
            logReader = new BufferedReader(fileReader);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            throw new FileNotFoundException();
        }
    }

    public void close(){
        try {
            logReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
