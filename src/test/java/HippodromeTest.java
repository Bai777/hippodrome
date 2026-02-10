import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class HippodromeTest {
    @Test
    void constructor_ShouldThrowException_WhenHorsesIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    void constructor_ShouldThrowException_WhenHorsesIsEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(new ArrayList<>()));
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    void getHorses_ShouldReturnSameList() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse("Horse" + i, 2.0 + i * 0.1, i));
        }

        Hippodrome hippodrome = new Hippodrome(horses);

        assertEquals(horses, hippodrome.getHorses());
    }

    @Test
    void move_ShouldCallMoveOnAllHorses() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(mock(Horse.class));
        }

        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();

        for (Horse horse : horses) {
            verify(horse).move();
        }
    }

    @Test
    void getWinner_ShouldReturnHorseWithMaxDistance() {
        List<Horse> horses = new ArrayList<>();
        horses.add(new Horse("Horse1", 2.0, 10));
        horses.add(new Horse("Horse2", 2.5, 15));
        horses.add(new Horse("Horse3", 3.0, 10));

        Hippodrome hippodrome = new Hippodrome(horses);

        horses.get(0).move();
        horses.get(1).move();
        horses.get(1).move();
        horses.get(2).move();

        Horse winner = hippodrome.getWinner();
        assertEquals("Horse2", winner.getName());
    }

}