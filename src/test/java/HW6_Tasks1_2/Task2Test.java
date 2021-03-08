package HW6_Tasks1_2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class Task2Test {
    private Task2 task2;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new int[]{1, 1, 1, 4, 4, 1, 4, 4}, true},
                {new int[]{1, 1, 1, 1, 1, 1}, false},
                {new int[]{4, 4, 4, 4}, false},
                {new int[]{1, 4, 4, 1, 1, 4, 3}, false}
        });
    }

    private int[] in;
    private boolean out;

    public Task2Test(int[] in, boolean out) {
        this.in = in;
        this.out = out;
    }

    @Before
    public void startTest() {
        task2 = new Task2();
    }

    @Test
    public void checkMethodTest1() {
        Assert.assertEquals(task2.check(in), out);
    }

}