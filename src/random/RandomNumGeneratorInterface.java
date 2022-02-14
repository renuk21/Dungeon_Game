package random;

/**
 * Random Number Generator interface to generate random values between a minimum and maximum.
 */
public interface RandomNumGeneratorInterface {

  /**
   * Get the generated random number between the given maximum and minimum values.
   *
   * @param min minimum value of the range of random numbers
   * @param max maximum value of the range of random numbers
   * @return random number
   */
  int getRandomNumber(int min, int max);
}
