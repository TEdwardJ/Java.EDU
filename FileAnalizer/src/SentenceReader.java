import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SentenceReader extends BufferedReader {

    private StringBuffer sb = new StringBuffer();
    private Pattern sentencePattern = Pattern.compile("([^!?.]{1,}[!?.]+[ ]{0,})");
    private Matcher sentenceMatcher;

    BufferedReader in;

    public SentenceReader(BufferedReader in) {
        super(in);
        this.in = in;
    }

    private String getNextSentence(){
        String draft = sb.toString();
        sentenceMatcher = sentencePattern.matcher(draft);
        if(sentenceMatcher.find()){
            String sentence = sentenceMatcher.group();
            sb = new StringBuffer(sb.substring(sentence.length()));
            return sentence;
        }
        return null;
    }

    @Override
    public String readLine() throws IOException {
        String sentence = null;
        Pattern sentenceDelimiterPattern = Pattern.compile("[!?.]");
        String str;
        sentence = getNextSentence();
        if (sentence != null){
            return sentence;
        }
        while ((str = this.in.readLine()) != null) {
            sb.append(str);
            Matcher delimMatcher = sentenceDelimiterPattern.matcher(str);
            if (delimMatcher.find()) {
                return getNextSentence();
            }
        }
        return null;

    }

}
