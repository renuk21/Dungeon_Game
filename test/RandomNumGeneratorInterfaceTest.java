import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import org.junit.Test;
import random.RandomNumberGenerator;

/**
 * Test class for Random Number Generator.
 */
public class RandomNumGeneratorInterfaceTest {

  @Test
  public void getRandomNumber() {
    RandomNumberGenerator r = new RandomNumberGenerator(50);
    int[] arr = new int[10];
    for (int i = 0; i < 10; i++) {
      arr[i] = r.getRandomNumber(1, 10);
    }
    assertEquals("[8, 9, 4, 3, 2, 2, 7, 9, 7, 9]", Arrays.toString(arr));
  }

  @Test
  public void getRandomNumber1() {
    RandomNumberGenerator r = new RandomNumberGenerator(-1);
    int[] arr = new int[2];
    for (int i = 0; i < 2; i++) {
      arr[i] = r.getRandomNumber(1, 2);
    }
    assertEquals("[1, 1]", Arrays.toString(arr));
  }

  @Test
  public void getRandomNumber2() {
    RandomNumberGenerator r = new RandomNumberGenerator(1);
    int[] arr = new int[2];
    for (int i = 0; i < 2; i++) {
      arr[i] = r.getRandomNumber(1, 2);
    }
    assertEquals("[2, 1]", Arrays.toString(arr));
  }
}