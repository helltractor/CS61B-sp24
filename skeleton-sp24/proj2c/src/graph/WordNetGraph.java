package graph;

import edu.princeton.cs.algs4.In;

import java.util.*;

public class WordNetGraph extends Graph{
    
    private Map<String, List<String>> synsetToIdList = new HashMap<>();
    private Map<String, List<String>> idToSynsetList = new HashMap<>();
    private Map<String, String> synsetDescription = new HashMap<>();
    private Boolean isAncestor;
    
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
                String toId = parts[i];
                addVertexIfAbsent(toId);
                addEdge(fromId, toId, i);
                addEdge(toId, fromId, -i);
            }
        }
    }
    
    private void addVertexIfAbsent(String id) {
        if (!this.hasVertex(id)) {
            this.addVertex(id);
        }
    }
    
    public Set<String> getNeighbors(String syn, boolean isAncestor) {
        this.isAncestor = isAncestor;
        List<String> idList = synsetToIdList.get(syn);
        Set<String> result = new TreeSet<>();
        if (idList == null) {
            return result;
        }
        for (String id : idList) {
            List<String> synsets = idToSynsetList.get(id);
            if (synsets != null) {
                result.addAll(synsets);
            }
            Map<Vertex, Integer> hyponyms = getNeighbors(id);
            for (Vertex v : hyponyms.keySet()) {
                List<String> neighborSynsets = idToSynsetList.get(v.getId());
                if (neighborSynsets != null) {
                    result.addAll(neighborSynsets);
                }
            }
        }
        return result;
    }
    
    public Set<String> getAdjacentNeighbors(List<String> synsets, boolean isAncestor) {
        this.isAncestor = isAncestor;
        Set<String> result = new TreeSet<>();
        boolean firstSet = true;
        for (String synset : synsets) {
            Set<String> neighbors = getNeighbors(synset, isAncestor);
            if (firstSet) {
                result.addAll(neighbors);
                firstSet = false;
            } else {
                result.retainAll(neighbors);
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
        Map<Vertex, Integer> neighbors = new HashMap<>();
        Deque<Vertex> deque = new ArrayDeque<>();
        Set<Vertex> visited = new HashSet<>();
        deque.addLast(v);
        while (!deque.isEmpty()) {
            Vertex current = deque.removeFirst();
            for (Vertex neighbor : current.getNeighbors().keySet()) {
                if (!visited.contains(neighbor)) {
                    if ((isAncestor && current.getWeight(neighbor) < 0) ||
                            (!isAncestor && current.getWeight(neighbor) > 0)) {
                        deque.addLast(neighbor);
                        neighbors.put(neighbor, current.getWeight(neighbor));
                    }
                    visited.add(neighbor);
                }
            }
        }
        return neighbors;
    }
}
