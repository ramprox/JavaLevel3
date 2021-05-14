import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.ramil.homeworkLesson6.testing.WorkWithArrays;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class WorkWithArraysTest {
    private static WorkWithArrays workWithArrays;

    @BeforeAll
    public static void initBeforeAll() {
        workWithArrays = new WorkWithArrays();
    }

    @Test
    public void testGetArrayAfterLastNumber() {
        int[] array = {1, 2, 4, 4, 2, 3, 4, 1, 7};
        int[] result = {1, 7};
        Assertions.assertArrayEquals(result,
                workWithArrays.getArrayAfterLastNumber(array, 4));

        array = new int[] {4, 1, 4, 2, 3, 5};
        result = new int[] { 2, 3, 5};
        Assertions.assertArrayEquals(result,
                workWithArrays.getArrayAfterLastNumber(array, 4));

        array = new int[] {4, 1, 4, 2, 3, 5, 2, 3, 4};
        result = new int[] {};
        Assertions.assertArrayEquals(result,
                workWithArrays.getArrayAfterLastNumber(array, 4));

        int[] array1 = new int[] {0, 1, 2, 2, 3, 5};
        Assertions.assertThrows(RuntimeException.class,
                () -> workWithArrays.getArrayAfterLastNumber(array1, 4));

        Assertions.assertThrows(NullPointerException.class,
                () -> workWithArrays.getArrayAfterLastNumber(null, 4));
    }

    @ParameterizedTest
    @MethodSource("dataForGetArrayAfterLastNumber")
    public void testIsContainsNumbers(int[] array, int number1, int number2, boolean result) {
        Assertions.assertEquals(result, workWithArrays.isContainsNumbers(array, number1, number2));
    }

    public static Stream<Arguments> dataForGetArrayAfterLastNumber() {
        List<Arguments> out = new LinkedList<>();
        out.add(Arguments.arguments(new int[] {1, 1, 1, 4, 4, 1, 4, 4}, 1, 4, true));
        out.add(Arguments.arguments(new int[] {1, 1, 1, 1, 1, 1}, 1, 4, false));
        out.add(Arguments.arguments(new int[] {4, 4, 4, 4}, 1, 4, false));
        out.add(Arguments.arguments(new int[] {1, 4, 4, 1, 1, 4, 3}, 1, 4, false));
        out.add(Arguments.arguments(new int[] {}, 1, 4, false));
        out.add(Arguments.arguments(null, 1, 4, false));
        return out.stream();
    }
}
