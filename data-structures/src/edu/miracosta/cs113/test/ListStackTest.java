
package edu.miracosta.cs113.test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import edu.miracosta.cs113.dataStructures.ListStack;

public class ListStackTest
{

	public ListStack<Integer>	intStack;
	public ListStack<String>	emptyStack;

	@Before
	public void setUp () throws Exception
	{
		intStack = new ListStack<> ();
		for (int i = 0; i < 10; ++i)
		{
			intStack.push (i);
		}
		emptyStack = new ListStack<> ();
	}

	@Test
	public void testEmpty ()
	{
		assertThat (intStack.empty (), is (false));
		assertThat (emptyStack.empty (), is (true));
	}

	@Test
	public void testPeek ()
	{
		assertThat (intStack.peek (), is (9));
		assertThat (intStack.peek (), is (9));
	}

	@Test
	public void testPop ()
	{
		assertThat (intStack.pop (), is (9));
		assertThat (intStack.pop (), is (8));
	}

	@Test
	public void testPush ()
	{
		assertThat (intStack.push (20), is (20));
		assertThat (intStack.pop (), is (20));
		assertThat (intStack.pop (), is (9));
		assertThat (intStack.pop (), is (8));
		assertThat (emptyStack.push ("a"), is ("a"));
		assertThat (emptyStack.push ("b"), is ("b"));
		assertThat (emptyStack.pop (), is ("b"));
		assertThat (emptyStack.pop (), is ("a"));
	}

	@Test
	public void testSearch ()
	{
		assertThat (intStack.search (5), is (6));
		assertThat (intStack.search (23), is (-1));
	}

}
