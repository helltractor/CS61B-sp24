import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import browser.NgordnetQueryType;
import main.AutoGradeBuddy;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class TestMultiWordKNot0Hypoyms {
    private static final String WORDS_FILE = "data/ngrams/top_14377_words.csv";
    private static final String TOTAL_COUNTS_FILE = "data/ngrams/total_counts.csv";
    private static final String HYPONYMS_FILE_SUBSET = "data/wordnet/hyponyms.txt";
    private static final String SYNSETS_FILE_SUBSET = "data/wordnet/synsets.txt";
    
    @Test
    public void testActKNot0() {
        NgordnetQueryHandler studentHandler = AutoGradeBuddy.getHyponymsHandler(
                WORDS_FILE, TOTAL_COUNTS_FILE, SYNSETS_FILE_SUBSET, HYPONYMS_FILE_SUBSET);
        List<String> words = List.of("food", "cake");
        
        NgordnetQuery nq = new NgordnetQuery(words, 1950, 1990, 5, NgordnetQueryType.HYPONYMS);
        String actual = studentHandler.handle(nq);
        String expected = "[cake, kiss, snap, wafer, cookie]";
        assertThat(actual).isEqualTo(expected);
    }
}
