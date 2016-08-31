
package test;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.miracosta.cs113.IsPowerOfTwo;

public class IsPowerOfTwoTest
{

	@Test
	public void testIsPowerOf2 ()
	{
		assertEquals (true, IsPowerOfTwo.isPowerOf2 (4));
		assertEquals (true, IsPowerOfTwo.isPowerOf2 (16));
		assertEquals (true, IsPowerOfTwo.isPowerOf2 (64));
		assertNotEquals (true, IsPowerOfTwo.isPowerOf2 (3));
		assertNotEquals (true, IsPowerOfTwo.isPowerOf2 (5));
		assertNotEquals (true, IsPowerOfTwo.isPowerOf2 (32));
	}

}
