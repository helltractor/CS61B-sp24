package main;

import browser.NgordnetQueryHandler;
import handler.HyponymsHandler;
import wordnet.WordNet;
import ngrams.NGramMap;


public class AutoGradeBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymsHandler(
            String wordFile, String countFile,
            String synsetFile, String hyponymFile) {
        
        NGramMap ngm = new NGramMap(wordFile, countFile);
        WordNet wng = new WordNet(synsetFile, hyponymFile);
        return new HyponymsHandler(ngm, wng);
    }
}