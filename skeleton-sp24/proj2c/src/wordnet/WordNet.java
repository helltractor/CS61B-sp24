package wordnet;

import edu.princeton.cs.algs4.In;

import java.util.*;

public class WordNet extends Graph{
    
    private final Map<String, List<String>> synsetToIdList = new HashMap<>();
    private final Map<String, List<String>> idToSynsetList = new HashMap<>();
//    private final Map<String, String> synsetDescription = new HashMap<>();
    private Boolean isAncestor;
    
    public WordNet(String synsetFilename, String hyponymFilename) {
        super();
        
        // Read in the synset file.
        In synsetIn = new In(synsetFilename);
        while (synsetIn.hasNextLine()) {
            String line = synsetIn.readLine();
            String[] parts = line.split(",");
            String id = parts[0];
            String synset = parts[1];
            String[] synsets = synset.split(" ");
//            String description = parts[2];
            addVertexIfAbsent(id);
            for (String syn : synsets) {
                synsetToIdList.computeIfAbsent(syn, k -> new ArrayList<>()).add(id);
            }
            idToSynsetList.putIfAbsent(id, List.of(synsets));
//            synsetDescription.put(synset, description);
        }
        
        // Read in the hyponym file.
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
    
    /** Adds a vertex to the graph if it is not already present. */
    private void addVertexIfAbsent(String id) {
        if (!this.hasVertex(id)) {
            this.addVertex(id);
        }
    }
    
    /** Returns the set of all hyponyms of the given synset. */
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
            Map61B<Vertex, Integer> hyponyms = getNeighbors(id);
            for (Vertex v : hyponyms.keySet()) {
                List<String> neighborSynsets = idToSynsetList.get(v.getId());
                if (neighborSynsets != null) {
                    result.addAll(neighborSynsets);
                }
            }
        }
        return result;
    }
    
    /**
     * Returns the set of all hyponyms of the given synsets.
     * @param synsets the synsets to find hyponyms of
     * @param isAncestor true if ancestors are desired, false if hyponyms are desired
     * @return the set of all hyponyms of the given synsets
     */
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
    
    /**
     * Returns the neighbors of the vertex with the given id. Use BFS to find all neighbors.
     * @param id the id of the vertex to find neighbors of
     * @return the neighbors of the vertex with the given id
     */
    @Override
    public Map61B<Vertex, Integer> getNeighbors(String id) {
        Vertex v = getVertex(id);
        if (v == null) {
            throw new IllegalArgumentException("Vertex not found.");
        }
        Map61B<Vertex, Integer> neighbors = new BSTMap<>();
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
