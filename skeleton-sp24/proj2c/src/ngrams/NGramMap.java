package ngrams;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {
    
    public static final int MIN_YEAR = 1900;
    public static final int MAX_YEAR = 2100;
    
    private Map<String, TimeSeries> wordCountHistory = new TreeMap<>();
    private TimeSeries totalCountHistory = new TimeSeries();
    
    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        In wordsIn = new In(wordsFilename);
        In countsIn = new In(countsFilename);
        
        while (!wordsIn.isEmpty()) {
            String nextLine = wordsIn.readLine();
            String[] splitLine = nextLine.split("\t");
            String word = splitLine[0];
            Integer year = Integer.valueOf(splitLine[1]);
            Double count = Double.valueOf(splitLine[2]);
            wordCountHistory.computeIfAbsent(word, k -> new TimeSeries()).put(year, count);
        }
        
        while (!countsIn.isEmpty()) {
            String nextLine = countsIn.readLine();
            String[] splitLine;
            if (nextLine.contains(",")) {
                splitLine = nextLine.split(",");
            } else {
                splitLine = nextLine.split("\t");
            }
            int year = Integer.valueOf(splitLine[0]);
            double totalCount = Double.valueOf(splitLine[1]);
            totalCountHistory.put(year, totalCountHistory.getOrDefault(year, 0.0) + totalCount);
        }
        wordsIn.close();
        countsIn.close();
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        TimeSeries result = new TimeSeries();
        if (wordCountHistory.containsKey(word)) {
            result = new TimeSeries(wordCountHistory.get(word), startYear, endYear);
        }
        return result;
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        return countHistory(word, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        TimeSeries result = new TimeSeries();
        for (Map.Entry<Integer, Double> entry : totalCountHistory.entrySet()) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries countHistory = new TimeSeries(wordCountHistory.get(word), startYear, endYear);
        return countHistory.dividedBy(totalCountHistory);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        return weightHistory(word, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries summedWeightHistory = new TimeSeries();
        for (String word : words) {
            TimeSeries wordWeightHistory = weightHistory(word, startYear, endYear);
            summedWeightHistory = summedWeightHistory.plus(wordWeightHistory);
        }
        return summedWeightHistory;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        return summedWeightHistory(words, MIN_YEAR, MAX_YEAR);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, TimeSeries> entry : wordCountHistory.entrySet()) {
            sb.append(entry.getKey()).append("：")
                    .append(entry.getValue().toString()).append("\n");
        }
        return sb.toString();
    }
}
