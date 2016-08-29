
public class FizBuzz
{

	public static void main (String[] args)
	{

	}

	/**
	 * Prints from 0 to end exclusive the current number, or Fizz if divisible
	 * by 3, or Buzz if divisible by 5, or FizzBuzz if divisible by both 3 and 5
	 * <p>
	 * Ex. 0 1 2 Fizz 4 Buzz Fizz 7 8 Fizz
	 * <p>
	 * 15 would be Fizzbuzz
	 * 
	 * @param end
	 *            exclusive end point
	 */
	public static void fizzBuzz (int end)
	{
		for (int i = 0; i < end; i++)
		{
			if (i % 3 == 0)
			{
				// str = fizz
				if (i % 5 == 0)
				{
					// str += buzz
				}
			}
			else if (i % 5 == 0)
			{
				// str = buzz
			}
			else
			{
				// str = i
			}
		}
	}

}
