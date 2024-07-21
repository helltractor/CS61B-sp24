package handler;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import plotting.Plotter;

import java.util.ArrayList;
import java.util.List;

public class HistoryHandler extends NgordnetQueryHandler {
    private NGramMap ngm;
    
    public HistoryHandler(NGramMap ngm) {
        this.ngm = ngm;
    }
    
    @Override
    public String handle(NgordnetQuery q) {
        List<TimeSeries> timeSeries = new ArrayList<>();
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        for (String word : words) {
            TimeSeries ts = ngm.countHistory(word, startYear, endYear);
            timeSeries.add(ts);
        }
        return Plotter.encodeChartAsString(Plotter.generateTimeSeriesChart(words, timeSeries));
    }
}
