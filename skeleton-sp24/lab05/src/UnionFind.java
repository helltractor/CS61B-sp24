public class UnionFind {
    
    private static int n;
    private int[] parent;
    private int[] size;
    
    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        n = N;
        parent = new int[N];
        size = new int[N];
        for (int i = 0; i < N; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }
    
    /* Checks if V is a valid vertex. */
    private boolean checkVertexes(int v) {
        if (v < 0 || v >= n) {
            throw new IllegalArgumentException("Invalid vertex!");
        }
        return true;
    }
    
    /* Checks if V1 and V2 are valid vertexes. */
    private boolean checkVertexes(int v1, int v2) {
        return checkVertexes(v1) && checkVertexes(v2);
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        checkVertexes(v);
        return size[find(v)];
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        checkVertexes(v);
        return find(v);
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        checkVertexes(v1, v2);
        return find(v1) == find(v2);
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        checkVertexes(v);
        if (parent[v] == v) {
            return v;
        }
        parent[v] = find(parent[v]);
        return parent[v];
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie-break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        checkVertexes(v1, v2);
        int parent1 = find(v1);
        int parent2 = find(v2);
        if (parent1 != parent2) {
            if (size[parent1] <= size[parent2]) {
                parent[parent1] = parent2;
                size[parent2] += size[parent1];
            } else {
                parent[parent2] = parent1;
                size[parent1] += size[parent2];
            }
        }
    }
}