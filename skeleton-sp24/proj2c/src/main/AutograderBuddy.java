package main;

import browser.NgordnetQueryHandler;
import graph.WordNetGraph;
import ngrams.NGramMap;


public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymsHandler(
            String wordFile, String countFile,
            String synsetFile, String hyponymFile) {
        
        NGramMap ngm = new NGramMap(wordFile, countFile);
        WordNetGraph wng = new WordNetGraph(synsetFile, hyponymFile);
        return new HyponymsHandler(ngm, wng);
    }
}
