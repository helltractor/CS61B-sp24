package handler;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import wordnet.WordNet;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {
    
    private NGramMap ngm;
    private WordNet wng;
    
    public HyponymsHandler(NGramMap ngm, WordNet wng) {
        this.ngm = ngm;
        this.wng = wng;
    }
    
    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        int k = q.k();
        
        Set<String> adjacentNeighbors = new TreeSet<>();
        switch (q.ngordnetQueryType()) {
            case HYPONYMS: {
                adjacentNeighbors = wng.getAdjacentNeighbors(words, false);
                break;
            }
            case ANCESTORS: {
                adjacentNeighbors = wng.getAdjacentNeighbors(words, true);
                break;
            }
            default:
                return "[]";
        }
        
        if (adjacentNeighbors.isEmpty()) {
            return "[]";
        }
        
        StringBuilder result = new StringBuilder("[");
        StringJoiner joiner = new StringJoiner(", ");
        if (k == 0) {
            for (String neighbor : adjacentNeighbors) {
                joiner.add(neighbor);
            }
        } else {
            Map<Double, String> sumToNeighbor = new TreeMap<>((o1, o2) -> o2.compareTo(o1));
            for (String neighbor : adjacentNeighbors) {
                TimeSeries ts = ngm.countHistory(neighbor, startYear, endYear);
                Double sum = ts.sum();
                if (sum > 0) {
                    sumToNeighbor.put(sum, neighbor);
                }
            }
            
            List<String> resultWords = new ArrayList<>();
            for (String neighbor : sumToNeighbor.values()) {
                if (resultWords.size() == k) {
                    break;
                }
                resultWords.add(neighbor);
            }
            
            resultWords.sort(String::compareTo);
            for (String word : resultWords) {
                joiner.add(word);
            }
        }
        return result.append(joiner).append(']').toString();
    }
}
