package cs61B;

import java.util.Comparator;

public class Dog<T> extends Animal implements Comparable<Dog<T>> {
    
    public String name;
    public int size;
    
    public Dog(String n, int s) {
        name = n;
        size = s;
    }
    
    public void bark() {
        System.out.println(name + " says: bark");
    }
    
    @Override
    public int compareTo(Dog<T> t) {
        return this.size - t.size;
    }
    
    public static class NameComparator implements Comparator<Dog> {
        @Override
        public int compare(Dog d1, Dog d2) {
            return d1.name.compareTo(d2.name);
        }
    }
}