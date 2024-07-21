package graph;

import edu.princeton.cs.algs4.In;

import java.util.*;

public class WordNetGraph extends Graph{
    
    private Map<String, List<String>> synsetToIdList = new HashMap<>();
    private Map<String, List<String>> idToSynsetList = new HashMap<>();
    private Map<String, String> synsetDescription = new HashMap<>();
    
    public WordNetGraph(String synsetFilename, String hyponymFilename) {
        super();
        // Read in the synset file
        In synsetIn = new In(synsetFilename);
        while (synsetIn.hasNextLine()) {
            String line = synsetIn.readLine();
            String[] parts = line.split(",");
            String id = parts[0];
            String synset = parts[1];
            String[] synsets = synset.split(" ");
            String description = parts[2];
            addVertexIfAbsent(id);
            for (String syn : synsets) {
                synsetToIdList.computeIfAbsent(syn, k -> new ArrayList<>()).add(id);
            }
            idToSynsetList.putIfAbsent(id, List.of(synsets));
            synsetDescription.put(synset, description);
        }
        
        // Read in the hyponym file
        In hyponymIn = new In(hyponymFilename);
        while (hyponymIn.hasNextLine()) {
            String line = hyponymIn.readLine();
            String[] parts = line.split(",");
            String fromId = parts[0];
            addVertexIfAbsent(fromId);
            for (int i = 1; i < parts.length; i++) {
                addVertexIfAbsent(parts[i]);
                String toId = parts[i];
                addEdge(fromId, toId, i);
            }
        }
    }
    
    private void addVertexIfAbsent(String id) {
        if (!this.hasVertex(id)) {
            this.addVertex(id);
        }
    }
    public Set<String> getHyponyms(String syn) {
        List<String> idList = synsetToIdList.get(syn);
        Set<String> result = new TreeSet<>();
        if (idList == null) {
            return result;
        }
        for (String id : idList) {
            List<String> synsets = idToSynsetList.get(id);
            for (String synset : synsets) {
                result.add(synset);
            }
            Map<Vertex, Integer> hyponyms = getNeighbors(id);
            for (Vertex v : hyponyms.keySet()) {
                result.addAll(idToSynsetList.get(v.getId()));
            }
        }
        return result;
    }
    
    public Set<String> getAdjacentHyponyms(List<String> synsets) {
        Set<String> result = new TreeSet<>();
        boolean flag = true;
        for (String synset : synsets) {
            Set<String> hyponyms = getHyponyms(synset);
            if (flag && result.isEmpty()) {
                result.addAll(hyponyms);
                flag = false;
            } else {
                result.retainAll(hyponyms);
            }
        }
        return result;
    }
    
    @Override
    public Map<Vertex, Integer> getNeighbors(String id) {
        Vertex v = getVertex(id);
        if (v == null) {
            throw new IllegalArgumentException("Vertex not found.");
        }
        Map<Vertex, Integer> hyponyms = new HashMap<>();
        Deque<Vertex> deque = new ArrayDeque<>();
        deque.addLast(v);
        while (!deque.isEmpty()) {
            Vertex current = deque.removeFirst();
            for (Vertex neighbor : current.getNeighbors().keySet()) {
                if (!hyponyms.containsKey(neighbor)) {
                    deque.addLast(neighbor);
                    hyponyms.put(neighbor, current.getWeight(neighbor));
                }
            }
        }
        return hyponyms;
    }
}
