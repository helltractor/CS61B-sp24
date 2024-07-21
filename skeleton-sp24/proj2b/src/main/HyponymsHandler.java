package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import graph.WordNetGraph;

import java.util.List;
import java.util.Set;

public class HyponymsHandler extends NgordnetQueryHandler {
    
    private WordNetGraph wng;
    
    public HyponymsHandler(WordNetGraph wng) {
        this.wng = wng;
    }
    
    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        StringBuilder result = new StringBuilder();
        Set<String> togetherHyponyms = wng.getAdjacentHyponyms(words);
        result.append("[");
        for (String hyponym : togetherHyponyms) {
            result.append(hyponym).append(", ");
        }
        if (result.length() > 1) {
            result.delete(result.length() - 2, result.length());
        }
        result.append("]");
        return result.toString();
    }
}
