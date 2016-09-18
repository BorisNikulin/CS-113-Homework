
package edu.miracosta.cs113.test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

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
		intList.set (0, 100);
		stringList.set (0, "z");
		assertThat (intList.size (), is (10));
		assertThat (stringList.size (), is (5));
		assertThat (intList.get (0), is (100));
		assertThat (stringList.get (0), is ("z"));
		assertThat (intList.get (1), is (1));
	}

	@Test
	public void testRemoveIndex ()
	{
		intList.remove (0);
		stringList.remove (2);
		assertThat (intList.size (), is (9));
		assertThat (stringList.size (), is (4));
		assertThat (intList.get (0), is (1));
		assertThat (stringList.get (2), is ("d"));
		assertThat (intList.get (1), is (2));
		assertThat (stringList.get (3), is ("e"));

	}
}
