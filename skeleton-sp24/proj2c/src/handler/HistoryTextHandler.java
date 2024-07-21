package handler;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
    private NGramMap ngm;
    
    public HistoryTextHandler(NGramMap ngm) {
        this.ngm = ngm;
    }
    
    @Override
    public String handle(NgordnetQuery q) {
//        String response = "You entered the following info into the browser:\n";
        StringBuilder response = new StringBuilder();
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        for (String word : words) {
            response.append(word).append(": ").append(ngm.weightHistory(word, startYear, endYear).toString()).append("\n");
        }
        return response.toString();
    }
}
