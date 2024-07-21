import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

    @Test
    @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
    void noNonTrivialFields() {
     List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
             .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
             .toList();
    
     assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
    }
    
    @Test
    @DisplayName("ArrayDeque61B has no static fields")
    void noStaticFields() {
        List<Field> staticFields = Reflection.getFields(ArrayDeque61B.class)
                .filter(Field::isSynthetic)
                .toList();

        assertThat(staticFields).isEmpty();
    }
    
    @Test
    void isEmpty() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        assertThat(deque.isEmpty()).isTrue();
        deque.addFirst(1);
        assertThat(deque.isEmpty()).isFalse();
    }
    
    @Test
    void addFirst() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        assertThat(deque.toList()).containsExactly(3, 2, 1).inOrder();
    }
    
    @Test
    void addLast() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        assertThat(deque.toList()).containsExactly(1, 2, 3).inOrder();
    }
    
    @Test
    void removeFirst() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        assertThat(deque.removeFirst()).isEqualTo(3);
        assertThat(deque.removeFirst()).isEqualTo(2);
        assertThat(deque.removeFirst()).isEqualTo(1);
    }
    
    @Test
    void removeLast() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        assertThat(deque.removeLast()).isEqualTo(3);
        assertThat(deque.removeLast()).isEqualTo(2);
        assertThat(deque.removeLast()).isEqualTo(1);
    }
    
    @Test
    void removeLasts() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        for (int i = 0; i < 10000; i++) {
            deque.addLast(i);
        }
        for (int i = 0; i < 10000; i++) {
            deque.addLast(i);
            deque.removeFirst();
            assertThat(deque.get(0)).isEqualTo((i + 1) % 10000);
        }
    }
}
