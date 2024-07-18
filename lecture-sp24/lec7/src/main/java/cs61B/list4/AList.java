package cs61B.list4;

public class AList {
    private int[] items;
    private int size;
    
    public AList() {
        items = new int[100];
        size = 0;
    }
    
    /* Resize the underlying array to the target capacity.*/
    public void resize(int capacity) {
        int[] tmp = new int[capacity];
        System.arraycopy(items, 0, tmp, 0, size);
        items = tmp;
    }
    
    public void addLast(int x) {
        if (size == items.length) {
            resize(size + 1);
        }
        items[size] = x;
        size += 1;
    }
    
    public int getLast() {
        return items[size - 1];
    }
    
    public int getSize() {
        return size;
    }
    
    public int removeLast() {
        int x = items[size - 1];
        items[size - 1] = 0;
        size -= 1;
        return x;
    }
}
