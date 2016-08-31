

public class IsPowerOfTwo
{

	public static void main (String[] args)
	{
		// TODO Auto-generated method stub
	}
	
	/**
	 * @param num
	 *            number to check if power of two
	 * @return True if num is a power of 2. Falser otherwise.
	 */
	public static boolean isPowerOf2 (long num)
	{
		return Math.sqrt ((double) num) == (long) Math.sqrt ((double) num);
	}

}
