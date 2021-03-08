package HW6_Tasks1_2;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Task1 {

    public int[] pullOut(int[] input) {

        if (input.length == 0)
            throw new RuntimeException("Массив должен содержать хотя бы один элемент");

        final int DELIMITER = 4;
        boolean hasDelimiter = false;
        List<Integer> output = new ArrayList<>();

        for (int i = input.length - 1; i >= 0; i--) {
            if (input[i] == DELIMITER) {
                hasDelimiter = true;
                break;
            }
            output.add(input[i]);
        }

        if (!hasDelimiter)
            throw new RuntimeException("В массиве нет элемента, содержащего значение " + DELIMITER);

        Collections.reverse(output);

        return output.stream().mapToInt(Integer::intValue).toArray();
    }

}

