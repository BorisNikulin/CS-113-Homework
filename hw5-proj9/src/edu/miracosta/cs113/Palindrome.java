
package edu.miracosta.cs113;

import edu.miracosta.cs113.dataStructures.ArrayStack;

public class Palindrome
{
	private Palindrome ()
	{
	}

	public static boolean isPalindrome (String str)
	{
		final int HALF_LEN = str.length () / 2;
		final int HALF_START_POINT;
		boolean isPalindrome = true;
		ArrayStack<Character> firstHalf = new ArrayStack<> (HALF_LEN);
		for (int i = 0; i < HALF_LEN; ++i)
		{
			firstHalf.push (str.charAt (i));
		}

		if (str.length () % 2 == 0)
		{
			HALF_START_POINT = HALF_LEN;
		}
		else
		{
			HALF_START_POINT = HALF_LEN + 1;
		}

		// the hard to read version
//		int i = 0;
//		while (i < str.length () && isPalindrome != false)
//		{
//			if (str.charAt (i) != firstHalf.pop ())
//			{
//				isPalindrome = false;
//			}
//			++i;
//		}
		
		for(int i = HALF_START_POINT; i < str.length (); ++i)
		{
			if (str.charAt (i) != firstHalf.pop ())
			{
				isPalindrome = false;
				break;
			}
		}
		return isPalindrome;
	}
}
