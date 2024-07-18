package cs61B;

import org.junit.jupiter.api.Test;

public class DogTest {
    
    @Test
    public void testDog() {
        Dog d1 = new Dog("Fido", 21);
        Dog d2 = new Dog("Spot", 10);
        if (d1.compareTo(d2) > 0) {
            d1.bark();
        } else {
            d2.bark();
        }
        
        Dog.NameComparator nc = new Dog.NameComparator();
        if (nc.compare(d1, d2) > 0) {
            d1.bark();
        } else {
            d2.bark();
        }
        
        if (d1 instanceof Comparable) {
            System.out.println("d1 is Comparable");
        }
        
        if (d1 instanceof Dog d) {
            System.out.println("d1 is Dog");
        }
        
        if (d1 instanceof Animal a) {
            System.out.println("d1 is Animal");
            if (d1.equals(a)) {
                System.out.println("d1 equals a");
            }
        }
    }
}
