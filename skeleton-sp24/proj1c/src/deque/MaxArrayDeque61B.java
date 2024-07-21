package deque;

import java.util.Comparator;


public class MaxArrayDeque61B<T> extends ArrayDeque61B<T> {

    private Comparator<T> comparator;
    
    public MaxArrayDeque61B(Comparator<T> comparator) {
        super();
        this.comparator = comparator;
    }
    
    public T max() {
        return max(comparator);
    }
    
    public T max(Comparator<T> comparator) {
        if (super.isEmpty()) {
            return null;
        }
        T maxItem = this.get(0);
        for (T item : this) {
            if (comparator.compare(item, maxItem) > 0) {
                maxItem = item;
            }
        }
        return maxItem;
    }
    
    public T min() {
        return min(comparator);
    }
    
    public T min(Comparator<T> comparator) {
        if (super.isEmpty()) {
            return null;
        }
        T minItem = this.get(0);
        for (T item : this) {
            if (comparator.compare(item, minItem) < 0) {
                minItem = item;
            }
        }
        return minItem;
    }
}
