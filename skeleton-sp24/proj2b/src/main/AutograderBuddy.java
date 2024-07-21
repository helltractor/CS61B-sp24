package main;

import browser.NgordnetQueryHandler;
import graph.WordNetGraph;


public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymsHandler(
            String wordFile, String countFile,
            String synsetFile, String hyponymFile) {
        WordNetGraph wng = new WordNetGraph(synsetFile, hyponymFile);
        return new HyponymsHandler(wng);
    }
}
