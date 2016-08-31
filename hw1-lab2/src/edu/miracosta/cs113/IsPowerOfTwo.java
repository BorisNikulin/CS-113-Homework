package edu.miracosta.cs113;

public class IsPowerOfTwo
{

	/**
	 * @param num
	 *            number to check if power of two
	 * @return True if num is a power of 2. Falser otherwise.
	 */
//	public static boolean isPowerOf2 (long num)
//	{
//		return Math.sqrt ((double) num) == (long) Math.sqrt ((double) num);
//		// It's too late for this...
//	}
	
	/**
	 * @param num
	 *            number to check if power of two
	 * @return True if num is a power of 2. Falser otherwise.
	 */
	public static boolean isPowerOf2 (long num)
	{
		return Math.pow ((double) num, .5) == (long) Math.pow ((double) num, .5);
		// It's too late for this...
	}

}
