package graph;

import java.util.HashMap;
import java.util.Map;

class Vertex {
    private String id;
    private Map<Vertex, Integer> neighbors;
    
    public Vertex(String id) {
        this.id = id;
        this.neighbors = new HashMap<>();
    }
    
    public String getId() {
        return id;
    }
    
    public Map<Vertex, Integer> getNeighbors() {
        return neighbors;
    }
    
    public void addNeighbor(Vertex neighbor, int weight) {
        neighbors.put(neighbor, weight);
    }
    
    public boolean hasNeighbor(Vertex neighbor) {
        return neighbors.containsKey(neighbor);
    }
    
    public int getWeight(Vertex neighbor) {
        return neighbors.get(neighbor);
    }
    
    @Override
    public String toString() {
        return id;
    }
}