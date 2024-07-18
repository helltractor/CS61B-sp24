package cs61B.list4;

public class AListGeneric<T> {
    private T[] items;
    private int size;
    
    public AListGeneric() {
        items = (T[]) new Object[100];
        size = 0;
    }
    
    /* Resize the underlying array to the target capacity.*/
    public void resize(int capacity) {
        T[] tmp = (T[]) new Object[capacity];
        System.arraycopy(items, 0, tmp, 0, size);
        items = tmp;
    }
    
    public void addLast(T x) {
        if (size == items.length) {
            // resize(size + 1);
            resize(size * 2);
        }
        items[size] = x;
        size += 1;
    }
    
    public T getLast() {
        return items[size - 1];
    }
    
    public int getSize() {
        return size;
    }
    
    public T removeLast() {
        T x = items[size - 1];
        items[size - 1] = null;
        size -= 1;
        return x;
    }
}
