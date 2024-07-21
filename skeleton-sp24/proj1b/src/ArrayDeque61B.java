import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T> {
    
    /** The sentinel points to the first element of the deque. */
    private int sentinel;
    private int capacity;
    private int currentCapacity;
    private T[] items;
    
    public ArrayDeque61B() {
        items = (T[]) new Object[8];
        sentinel = 0;
        capacity = 8;
        currentCapacity = 0;
    }
    
    private void checkCapacity() {
        if (currentCapacity == capacity) {
            setCapacity(capacity * 2);
        } else if (capacity >= 16) {
            if (4 * currentCapacity < capacity) {
                setCapacity(capacity / 2);
            }
        }
    }
    
    private void setCapacity(int capacity) {
        T[] a = (T[]) new Object[capacity];
        int head = Math.floorMod(sentinel, this.capacity);
        int tail = Math.floorMod(sentinel + currentCapacity - 1, this.capacity);
        if (tail > head) {
            System.arraycopy(items, head, a, 0, currentCapacity);
        } else {
            System.arraycopy(items, head, a, 0, this.capacity - head);
            System.arraycopy(items, 0, a, this.capacity - head, tail + 1);
        }
        items = a;
        sentinel = 0;
        this.capacity = capacity;
    }
    
    @Override
    public void addFirst(T x) {
        sentinel--;
        int cur = Math.floorMod(sentinel, capacity);
        items[cur] = x;
        currentCapacity++;
        checkCapacity();
    }
    
    @Override
    public void addLast(T x) {
        int cur = Math.floorMod(sentinel + currentCapacity, capacity);
        items[cur] = x;
        currentCapacity++;
        checkCapacity();
    }
    
    @Override
    public List<T> toList() {
        if (currentCapacity == 0) {
            return List.of();
        }
        T[] a = (T[]) new Object[currentCapacity];
        int head = Math.floorMod(sentinel, capacity);
        int tail = Math.floorMod(sentinel + currentCapacity - 1, capacity);
        if (tail > head) {
            System.arraycopy(items, head, a, 0, currentCapacity);
        } else {
            System.arraycopy(items, head, a, 0, capacity - head);
            System.arraycopy(items, 0, a, capacity - head, tail + 1);
        }
        return List.of(a);
    }
    
    @Override
    public boolean isEmpty() {
        return currentCapacity == 0;
    }
    
    @Override
    public int size() {
        return currentCapacity;
    }
    
    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        int cur = Math.floorMod(sentinel, capacity);
        T result = items[cur];
        items[cur] = null;
        sentinel++;
        currentCapacity--;
        checkCapacity();
        return result;
    }
    
    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        int cur = Math.floorMod(sentinel + currentCapacity - 1, capacity);
        T result = items[cur];
        items[cur] = null;
        currentCapacity--;
        checkCapacity();
        return result;
    }
    
    @Override
    public T get(int index) {
        if (index < 0 || index >= capacity) {
            return null;
        }
        return items[Math.floorMod(sentinel + index, capacity)];
    }
    
    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }
}