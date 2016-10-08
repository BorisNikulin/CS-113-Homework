
package edu.miracosta.cs113;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

import edu.miracosta.cs113.dataStructures.ArrayStack;

public class Palindrome
{
	private Palindrome ()
	{
	}

	/**
	 * Tests if the giving String is a palindrome where case matters and no
	 * characters are skipped.
	 * 
	 * @param str
	 *            the string to test
	 * @return true if str is a palindrome, else false
	 * @see #isPalindrome(String, BiPredicate, Predicate)
	 */
	public static boolean isPalindrome (String str)
	{
		return isPalindrome (str, (c1, c2) -> c1 == c2);
	}

	/**
	 * Tests if the giving String is a palindrome where case matters and no
	 * characters are skipped. One can pass in a custom {@link BiPredicate} to
	 * define equality between two characters. One can also pass in a
	 * {@link Predicate} that will define if a passed in character should be
	 * ignored/skipped (returns true) when passing through the palindrome.
	 * 
	 * @param str
	 *            the string to test
	 * @param areCharsEqual
	 *            used to compare two characters for equality
	 * @return true if str is a palindrome, else false
	 * @see #isPalindrome(String, BiPredicate, Predicate)
	 */
	public static boolean isPalindrome (String str, BiPredicate<Character, Character> areCharsEqual)
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

		// the hard to read version (now out of date)
		// int i = 0;
		// while (i < str.length () && isPalindrome != false)
		// {
		// if (str.charAt (i) != firstHalf.pop ())
		// {
		// isPalindrome = false;
		// }
		// ++i;
		// }

		for (int i = HALF_START_POINT; i < str.length (); ++i)
		{

			if (!areCharsEqual.test (str.charAt (i), firstHalf.pop ()))
			{
				isPalindrome = false;
				break;
			}
		}
		return isPalindrome;
	}

	/**
	 * Tests if the giving String is a palindrome where case matters and no
	 * characters are skipped. One can pass in a custom {@link BiPredicate} to
	 * define equality between two characters. One can also pass in a
	 * {@link Predicate} that will define if a passed in character should be
	 * ignored/skipped (returns true) when passing through the palindrome.
	 * 
	 * @param str
	 *            the string to test
	 * @param areCharsEqual
	 *            used to compare two characters for equality
	 * @param doSkipChar
	 *            given a character, the predicate will return true to skip the
	 *            char
	 * @return true if str is a palindrome, else false
	 * @see #isPalindrome(String)
	 */
	public static boolean isPalindrome (String str, BiPredicate<Character, Character> areCharsEqual,
			Predicate<Character> doSkipChar)
	{
			str = str.chars ()
					.filter (c -> doSkipChar.negate ().test ((char) c))
					.mapToObj (c -> new Character((char) c)) //probably will be bad for non ascii
					.collect (StringBuilder::new, StringBuilder::append, StringBuilder::append)
					.toString ();
		return isPalindrome (str, areCharsEqual);
	}
}
