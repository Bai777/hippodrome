import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;


class HorseTest {

    @Test
    void constructor_ShouldThrowException_WhenNameIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Horse(null, 1.0, 1));
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n", "\r", "   "})
    void constructor_ShouldThrowException_WhenNameIsBlank(String name) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Horse(name, 1.0, 1));
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    void constructor_ShouldThrowException_WhenSpeedIsNegative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Horse("Name", -1.0));
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    void constructor_ShouldThrowException_WhenDistanceIsNegative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Horse("Name", 1.0, -1.0));
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    void getName_ShouldReturnCorrectName() {
        Horse horse = new Horse("Bucephalus", 2.4, 5);
        assertEquals("Bucephalus", horse.getName());
    }

    @Test
    void getSpeed_ShouldReturnCorrectSpeed() {
        Horse horse = new Horse("Bucephalus", 2.4, 7);
        assertEquals(2.4, horse.getSpeed());
    }

    @Test
    void getDistance_ShouldReturnCorrectDistance() {
        Horse horse = new Horse("Bucephalus", 2.4, 10.0);
        assertEquals(10.0, horse.getDistance());
    }

    @Test
    void getDistance_ShouldReturnZero_WhenTwoParameterConstructor() {
        Horse horse = new Horse("Bucephalus", 2.4);
        assertEquals(0.0, horse.getDistance());
    }

    @Test
    void move_ShouldCallGetRandomDouble() {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            Horse horse = new Horse("Bucephalus", 2.4);
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(0.5);

            horse.move();

            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.2, 0.5, 0.9})
    void move_ShouldUpdateDistanceCorrectly(double randomValue) {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            Horse horse = new Horse("Bucephalus", 2.4, 10.0);
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(randomValue);

            double expectedDistance = 10.0 + 2.4 * randomValue;

            horse.move();

            assertEquals(expectedDistance, horse.getDistance(), 0.001);
        }
    }
}