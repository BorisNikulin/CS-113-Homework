
package edu.miracosta.cs113.test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import edu.miracosta.cs113.dataStructures.SimpleArrayList;

public class SimpleArrayListTest
{

	private SimpleArrayList<Integer>	intList;
	private SimpleArrayList<String>		stringList;
	private SimpleArrayList<Double>		emptyList;

	@Before
	public void setUp () throws Exception
	{
		intList = new SimpleArrayList<> ();
		stringList = new SimpleArrayList<> ();
		emptyList = new SimpleArrayList<> ();

		for (int i = 0; i < 10; ++i)
		{
			intList.add (i);
		}
		stringList.add ("a");
		stringList.add ("b");
		stringList.add ("c");
		stringList.add ("d");
		stringList.add ("e");
	}

	@Test
	public void testSize ()
	{
		assertThat (intList.size (), is (10));
		assertThat (stringList.size (), is (5));
	}

	@Test
	public void testIsEmpty ()
	{
		assertThat (intList.isEmpty (), is (false));
		assertThat (stringList.isEmpty (), is (false));
		assertThat (emptyList.isEmpty (), is (true));
	}

	@Test
	public void testAddAtIndex ()
	{
		intList.add (0, 10);
		stringList.add (0, "f");
		assertThat (intList.size (), is (11));
		assertThat (stringList.size (), is (6));
		assertThat (intList.get (0), is (10));
		assertThat (stringList.get (0), is ("f"));
		assertThat (intList.get (1), is (0));
		assertThat (stringList.get (1), is ("a"));

		intList.add (4, 11); // [10, 0, 1, 2, 3, 4...
		stringList.add (4, "g"); // [f, a ,b, c, d, e...
		assertThat (intList.size (), is (12));
		assertThat (stringList.size (), is (7));
		assertThat (intList.get (4), is (11));
		assertThat (stringList.get (4), is ("g"));
		assertThat (intList.get (5), is (3));
		assertThat (stringList.get (5), is ("d"));

		emptyList.add (0, 1.0);
		assertThat (emptyList.size (), is (1));
		assertThat (emptyList.get (0), is (1.0));
		emptyList.add (0, 2.0);
		assertThat (emptyList.size (), is (2));
		assertThat (emptyList.get (0), is (2.0));
		assertThat (emptyList.get (1), is (1.0));
		emptyList.add (1, 3.0); // [2 , 1] -> [2, 3, 1]
		assertThat (emptyList.size (), is (3));
		assertThat (emptyList.get (1), is (3.0));
		assertThat (emptyList.get (2), is (1.0));
	}

	@Test
	public void testAddAtEnd ()
	{
		intList.add (10);
		stringList.add ("f");
		assertThat (intList.size (), is (11));
		assertThat (stringList.size (), is (6));
		assertThat (intList.get (10), is (10));
		assertThat (stringList.get (5), is ("f"));
	}

	@Test
	public void testSet ()
	{
		assertThat (intList.set (0, 100), is (0));
		assertThat (stringList.set (0, "z"), is ("a"));
		assertThat (intList.size (), is (10));
		assertThat (stringList.size (), is (5));
		assertThat (intList.get (0), is (100));
		assertThat (stringList.get (0), is ("z"));
		assertThat (intList.get (1), is (1));
	}

	@Test
	public void testRemoveAtIndex ()
	{
		assertThat (intList.remove (0), is (0));
		assertThat (stringList.remove (2), is ("c"));
		assertThat (intList.size (), is (9));
		assertThat (stringList.size (), is (4));
		assertThat (intList.get (0), is (1));
		assertThat (stringList.get (2), is ("d"));
		assertThat (intList.get (1), is (2));
		assertThat (stringList.get (3), is ("e"));
	}

	@Test
	public void testRemoveObject ()
	{
		assertThat (intList.remove (new Integer (2)), is (true));
		assertThat (stringList.remove ("c"), is (true));
		assertThat (intList.size (), is (9));
		assertThat (stringList.size (), is (4));
		assertThat (intList.get (2), is (3));
		assertThat (stringList.get (2), is ("d"));
		assertThat (intList.get (3), is (4));
		assertThat (stringList.get (3), is ("e"));
		assertThat (intList.remove (new Integer (99)), is (false));
		assertThat (stringList.remove ("cat"), is (false));
		assertThat (emptyList.remove (null), is (false));
		emptyList.add (null);
		assertThat (emptyList.remove (null), is (true));

	}

	@Test
	public void testRemoveAll ()
	{
		assertThat (intList.removeAll (Arrays.asList (2, 3, 8)), is (true));
		// [0 1 4 5 6 7 9]
		assertThat (stringList.removeAll (Arrays.asList ("b", "c", "e")), is (true));
		// [a d]
		assertThat (intList.size (), is (7));
		assertThat (stringList.size (), is (2));
		assertThat (intList.get (1), is (1));
		assertThat (stringList.get (0), is ("a"));
		assertThat (intList.get (2), is (4));
		assertThat (stringList.get (1), is ("d"));
		assertThat (intList.removeAll (Arrays.asList (2, 3, 8)), is (false));
		assertThat (stringList.removeAll (Arrays.asList ("b", "c", "e")), is (false));
		assertThat (intList.removeAll (Arrays.asList (0, 3, 8)), is (true));
		assertThat (stringList.removeAll (Arrays.asList ("a", "c", "e")), is (true));
	}

	@Test
	public void testAddAllAtEnd ()
	{
		assertThat (intList.addAll (Arrays.asList (2, 3, 8)), is (true));
		// [0 1 2 3 4 5 6 7 8 9 0 2 3 8]
		assertThat (stringList.addAll (Arrays.asList ("b", "c", "e")), is (true));
		// [a b c d e b c e]
		assertThat (intList.size (), is (13));
		assertThat (stringList.size (), is (8));
		assertThat (intList.get (9), is (9));
		assertThat (stringList.get (4), is ("e"));
		assertThat (intList.get (10), is (2));
		assertThat (stringList.get (5), is ("b"));
	}

	@Test
	public void testAddAllAtIndex ()
	{
		assertThat (intList.addAll (0, Arrays.asList (2, 3, 8)), is (true));
		// [2 3 8 0 1 2 3 4 5 6 7 8 9 0]
		assertThat (stringList.addAll (0, Arrays.asList ("b", "c", "e")), is (true));
		// [b c e a b c d e]
		assertThat (intList.size (), is (13));
		assertThat (stringList.size (), is (8));
		assertThat (intList.get (0), is (2));
		assertThat (stringList.get (0), is ("b"));
		assertThat (intList.get (3), is (0));
		assertThat (stringList.get (3), is ("a"));
	}

	@Test
	public void testContains ()
	{
		// [0 1 2 3 4 5 6 7 8 9]
		// [a b c d e]
		assertThat (intList.contains (0), is (true));
		assertThat (stringList.contains ("a"), is (true));
		assertThat (intList.contains (8), is (true));
		assertThat (stringList.contains ("e"), is (true));
		assertThat (intList.contains (99), is (false));
		assertThat (stringList.contains ("z"), is (false));
		assertThat (emptyList.contains (null), is (false));
		emptyList.add (null);
		assertThat (emptyList.contains (null), is (true));
	}

	@Test
	public void testContainsAll ()
	{
		// [0 1 2 3 4 5 6 7 8 9]
		// [a b c d e]
		assertThat (intList.containsAll (Arrays.asList (2, 3, 8)), is (true));
		assertThat (stringList.containsAll (Arrays.asList ("b", "c", "e")), is (true));
		assertThat (intList.containsAll (Arrays.asList (2, 3, 9)), is (true));
		assertThat (stringList.containsAll (Arrays.asList ("b", "c", "a")), is (true));
		assertThat (intList.containsAll (Arrays.asList (2, 3, 11)), is (false));
		assertThat (stringList.containsAll (Arrays.asList ("b", "c", "z")), is (false));
	}
	
	@Test
	public void testRetainAll ()
	{
		// [0 1 2 3 4 5 6 7 8 9]
		// [a b c d e]
		assertThat (intList.retainAll (intList), is (false));
		assertThat (stringList.retainAll (stringList), is (false));
		assertThat (intList.size (), is (10));
		assertThat (stringList.size (), is (5));
		assertThat (intList.retainAll (Arrays.asList (2, 3, 9)), is (true));
		assertThat (stringList.retainAll (Arrays.asList ("b", "c", "a")), is (true));
		assertThat (intList.size (), is (3));
		assertThat (stringList.size (), is (3));
		assertThat (intList.get (0), is (2));
		assertThat (stringList.get (0), is ("a"));
		assertThat (intList.get (1), is (3));
		assertThat (stringList.get (1), is ("b"));
		assertThat (intList.get (2), is (9));
		assertThat (stringList.get (2), is ("c"));
		assertThat (intList.retainAll (Arrays.asList (2, 3, 11)), is (true));
		assertThat (stringList.retainAll (Arrays.asList ("b", "c", "z")), is (true));
		assertThat (intList.size (), is (2));
		assertThat (stringList.size (), is (2));
		assertThat (intList.get (0), is (2));
		assertThat (stringList.get (0), is ("b"));
		assertThat (intList.get (1), is (3));
		assertThat (stringList.get (1), is ("c"));
	}
}
