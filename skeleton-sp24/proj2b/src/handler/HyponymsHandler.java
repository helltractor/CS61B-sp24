package handler;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import wordnet.WordNet;

import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

public class HyponymsHandler extends NgordnetQueryHandler {
    
    private WordNet wng;
    
    public HyponymsHandler(WordNet wng) {
        this.wng = wng;
    }
    
    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        StringBuilder result = new StringBuilder();
        StringJoiner joiner = new StringJoiner(", ");
        Set<String> togetherHyponyms = wng.getAdjacentHyponyms(words);
        result.append("[");
        for (String hyponym : togetherHyponyms) {
            joiner.add(hyponym);
        }
       return result.append(joiner).append("]").toString();
    }
}
