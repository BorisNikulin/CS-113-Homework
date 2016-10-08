
package edu.miracosta.cs113.test;

import static org.junit.Assert.*;

import java.util.function.BiPredicate;

import static org.hamcrest.core.Is.*;
import org.junit.Test;

import static edu.miracosta.cs113.Palindrome.*;

public class PalindromeTest
{

	@Test
	public void testIsPalindrome ()
	{
		BiPredicate<Character, Character> charEquals = (c1, c2) -> c1 == c2;
		assertThat (isPalindrome (""), is (true));
		assertThat (isPalindrome ("aa"), is (true));
		assertThat (isPalindrome ("aba"), is (true));
		assertThat (isPalindrome ("abba"), is (true));
		assertThat (isPalindrome ("ab"), is (false));
		assertThat (isPalindrome ("aab"), is (false));
		assertThat (isPalindrome ("a a", charEquals, Character::isWhitespace), is (true));
		assertThat (isPalindrome ("a b a", charEquals, Character::isWhitespace), is (true));
		assertThat (isPalindrome ("a ba", charEquals, Character::isWhitespace), is (true));
	}
}
