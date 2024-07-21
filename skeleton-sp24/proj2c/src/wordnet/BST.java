package wordnet;

import java.util.HashSet;
import java.util.Set;

public class BST<K extends Comparable<K>, V>{
    
    // private int size = 0;
    private TreeNode<K, V> root = null;
    private final Set<K> keySet = new HashSet<>();
    
    private static class TreeNode<K, V> {
        K key;
        V val;
        TreeNode<K, V> left;
        TreeNode<K, V> right;
        
        TreeNode(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }
    
    /** Removes every mapping from this map. */
    public void clear() {
        root = null;
        keySet.clear();
    }
    
    /** Returns the number of key-value mappings in this map. */
    public int size() {
        return keySet.size();
    }
    
    /** Returns a Set view of the keys contained in this map. */
    public Set<K> getKeys() {
        return keySet;
    }
    
    /** Initialize your data structure here. */
    public void insert(K key, V val){
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        root = insert(root, key, val);
    }
    
    private TreeNode insert(TreeNode<K, V> node, K key, V val){
        if(node == null){
            // size++;
            keySet.add(key);
            return new TreeNode<>(key, val);
        }
        if (key.compareTo(node.key) < 0) {
            node.left = insert(node.left, key, val);
        } else if (key.compareTo(node.key) > 0) {
            node.right = insert(node.right, key, val);
        } else {
            node.val = val;
        }
        return node;
    }
    
    /** Returns true if the BST contains the specified element */
    public V search(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        return search(root, key);
    }
    
    private V search(TreeNode<K, V> node, K key) {
        if (node == null) {
            return null;
        }
        if (key.compareTo(node.key) == 0) {
            return node.val;
        } else if (key.compareTo(node.key) < 0) {
            return search(node.left, key);
        } else {
            return search(node.right, key);
        }
    }
    
    /** Delete the specified element in the BST */
    public V delete(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        if (containsKey(key)){
            V val = search(key);
            // size--;
            keySet.remove(key);
            root = delete(root, key);
            return val;
        }
        return null;
    }
    
    private TreeNode<K, V> delete(TreeNode<K, V> node, K key) {
        if (node == null) {
            return null;
        }
        if (key.compareTo(node.key) < 0) {
            node.left = delete(node.left, key);
        } else if (key.compareTo(node.key) > 0) {
            node.right = delete(node.right, key);
        } else {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }
            TreeNode<K, V> minNode = minValueNode(node.right);
            node.key = minNode.key;
            node.val = minNode.val;
            node.right = delete(node.right, minNode.key);
        }
        return node;
    }
    
    /** Returns true if the BST contains the specified element */
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        return containsKey(root, key);
    }
    
    private boolean containsKey(TreeNode<K, V> node, K key) {
        if (node == null) {
            return false;
        }
        if (key.compareTo(node.key) == 0) {
            return true;
        } else if (key.compareTo(node.key) < 0) {
            return containsKey(node.left, key);
        } else {
            return containsKey(node.right, key);
        }
    }
    
    /** Find the minimum key in the BST */
    private TreeNode<K, V> minValueNode(TreeNode<K, V> node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
    
    /** Print the BST in inorder traversal */
    public void inorderTraversal() {
        inorderTraversal(root);
    }
    
    private void inorderTraversal(TreeNode<K, V> node) {
        if (node != null) {
            inorderTraversal(node.left);
            System.out.println(node.key + " -> " + node.val);
            inorderTraversal(node.right);
        }
    }
}
