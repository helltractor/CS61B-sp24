package wordnet;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>{
    
    private final BST root;
    
    public BSTMap() {
        root = new BST();
    }
    
    /**
     * Associates the specified value with the specified key in this map.
     * If the map already contains the specified key, replaces the key's mapping
     * with the value specified.
     */
    @Override
    public void put(K key, V value) {
        root.insert(key, value);
    }
    
    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return (V) root.search(key);
    }
    
    /**
     * Returns whether this map contains a mapping for the specified key.
     */
    @Override
    public boolean containsKey(K key) {
        return root.containsKey(key);
    }
    
    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return root.size();
    }
    
    /**
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {
        root.clear();
    }
    
    /**
     * Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
        return root.getKeys();
    }
    
    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public V remove(K key) {
        return (V) root.delete(key);
    }
    
    /**
     * Returns an iterator over elements of type {@code T}.
     */
    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
    
    /**
     * Prints out your BSTMap in order of increasing Key.
     */
    public void printInOrder() {
        root.inorderTraversal();
    }
}
