package wordnet;

class Vertex implements Comparable<Vertex> {
    private final String id;
    private final Map61B<Vertex, Integer> neighbors;
    
    public Vertex(String id) {
        this.id = id;
        this.neighbors = new BSTMap<>();
    }
    
    public String getId() {
        return id;
    }
    
    public Map61B<Vertex, Integer> getNeighbors() {
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
    
    @Override
    public int compareTo(Vertex o) {
        return this.id.compareTo(o.id);
    }
}