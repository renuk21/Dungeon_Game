package random;

import java.util.Random;

/**
 * Random number generator class to generate random values for Player and Dungeon components.
 */
public class RandomNumberGenerator implements RandomNumGeneratorInterface {
  int rand;
  Random random;

  /**
   * Construct random number generator.
   */
  public RandomNumberGenerator() {
    random = new Random();
  }

  /**
   * Construct random number generator with seed.
   *
   * @param seed seed to make random values predictable.
   */
  public RandomNumberGenerator(int seed) {
    random = new Random(seed);
  }

  @Override
  public int getRandomNumber(int min, int max) {
    this.rand = random.nextInt((max - min) + 1) + min;
    return this.rand;
  }
}
