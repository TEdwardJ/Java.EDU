import com.sun.javaws.exceptions.InvalidArgumentException;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileTextAnalizer {

    private static File inputFile;
    private static String wordToFind;

    public static void main(String[] args) {
        if (args.length<2){
            throw new IllegalArgumentException("Not enough arguments");
        }
        String file = args[0];
        wordToFind = args[1];
        checkIfExists(file);
        readFile();
    }

    private static void readFile() {
        try( FileReader fr = new FileReader(inputFile);
             BufferedReader inn= new BufferedReader(fr);
             SentenceReader sr = new SentenceReader(inn)){

            Pattern wordPattern  = Pattern.compile(wordToFind,Pattern.CASE_INSENSITIVE);
            int totalCount = 0;
            String sentence;
            while ((sentence = sr.readLine())!=null){
                Matcher wordMatcher = wordPattern.matcher(sentence);
                int wordCount = 0;

                while (wordMatcher.find()) {
                    if (wordCount == 0){
                        System.out.println(sentence);
                    }
                    wordCount++;
                }
                totalCount += wordCount;
            }
            System.out.println("Total count of the word '"+wordToFind+"' is: "+totalCount);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void checkIfExists(String fileName) {
        File file = new File(fileName);
        if (!file.exists()){
            throw new IllegalArgumentException("Bad file");
        }
        inputFile = file;
    }
}
