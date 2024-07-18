package cs61B;

public interface List61B<T> {
    
    default public void print() {
        for (int i = 0; i < size(); i++) {
            System.out.print(get(i) + " ");
        }
        System.out.println();
    }
    
    public void addLast(T x);
    
    public int getLast() ;
    
    public T get(int i) ;
    
    public int size() ;
    
    public T removeLast() ;
    
}
