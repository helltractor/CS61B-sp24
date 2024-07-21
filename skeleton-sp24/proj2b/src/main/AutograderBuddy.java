package main;

import browser.NgordnetQueryHandler;
import handler.HyponymsHandler;
import wordnet.WordNet;


public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymsHandler(
            String wordFile, String countFile,
            String synsetFile, String hyponymFile) {
        WordNet wng = new WordNet(synsetFile, hyponymFile);
        return new HyponymsHandler(wng);
    }
}
