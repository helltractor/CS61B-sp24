import deque.ArrayDeque61B;
import deque.Deque61B;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static com.google.common.truth.Truth.assertThat;

public class ArrayDeque61BTest {

    private static class StringLengthComparator implements Comparator<String> {
        public int compare(String a, String b) {
            return a.length() - b.length();
        }
    }

    @Test
    public void testEqualDeques61B() {
        Deque61B<String> lld1 = new ArrayDeque61B<>();
        Deque61B<String> lld2 = new ArrayDeque61B<>();
        
        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");
        
        lld2.addLast("front");
        lld2.addLast("middle");
        lld2.addLast("back");
        
        assertThat(lld1).isEqualTo(lld2);
    }
}
