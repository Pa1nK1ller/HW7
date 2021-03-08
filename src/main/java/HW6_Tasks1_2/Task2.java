package HW6_Tasks1_2;

public class Task2 {
    public boolean check(int[] input) {

        final int REQUIRED_VALUE_FIRST = 1;
        final int REQUIRED_VALUE_SECOND = 4;

        for (int element : input) {
            if (element == REQUIRED_VALUE_FIRST || element == REQUIRED_VALUE_SECOND) {
                return true;
            }
        }
        return false;
    }
}
