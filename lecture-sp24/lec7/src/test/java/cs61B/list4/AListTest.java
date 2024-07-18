package cs61B.list4;

import org.junit.jupiter.api.Test;

public class AListTest {
    
    @Test
    public void test() {
        AList a = new AList();
        a.addLast(5);
        a.addLast(10);
        a.addLast(15);
        a.addLast(20);
        a.addLast(25);
        System.out.println(a);
        System.out.println(a.getLast());
        System.out.println(a.getSize());
    }
}
