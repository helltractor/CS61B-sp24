package wordnet;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class Graph {
    
    private final Map<String, Vertex> vertices;
    
    public Graph() {
        this.vertices = new HashMap<>();
    }
    
    public void addVertex(String id) {
        vertices.putIfAbsent(id, new Vertex(id));
    }
    
    public void addEdge(String from, String to, int weight) {
        Vertex v1 = vertices.get(from);
        Vertex v2 = vertices.get(to);
        
        if (v1 == null || v2 == null) {
            throw new IllegalArgumentException("Both vertices must exist.");
        }
        
        v1.addNeighbor(v2, weight);
        // For an undirected graph, add the opposite edge as well
        // v2.addNeighbor(v1, weight);
    }
    
    public Vertex getVertex(String id) {
        return vertices.get(id);
    }
    
    public Set<String> getVertices() {
        return vertices.keySet();
    }
    
    public boolean hasVertex(String id) {
        return vertices.containsKey(id);
    }
    
    public boolean hasEdge(String from, String to) {
        Vertex v1 = vertices.get(from);
        Vertex v2 = vertices.get(to);
        
        if (v1 == null || v2 == null) {
            return false;
        }
        
        return v1.hasNeighbor(v2);
    }
    
    public int getWeight(String from, String to) {
        Vertex v1 = vertices.get(from);
        Vertex v2 = vertices.get(to);
        
        if (v1 == null || v2 == null || !v1.hasNeighbor(v2)) {
            throw new IllegalArgumentException("Edge does not exist.");
        }
        
        return v1.getWeight(v2);
    }
    
    public Map61B<Vertex, Integer> getNeighbors(String id) {
        Vertex vertex = vertices.get(id);
        
        if (vertex == null) {
            throw new IllegalArgumentException("Vertex does not exist.");
        }
        
        return vertex.getNeighbors();
    }
}