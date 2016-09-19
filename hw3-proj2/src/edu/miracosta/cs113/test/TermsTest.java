
package edu.miracosta.cs113.test;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.miracosta.cs113.Term;
import edu.miracosta.cs113.Terms;
import edu.miracosta.cs113.dataStructures.SimpleArrayList;

public class TermsTest
{
	private List<Term>	terms1;
	private List<Term>	terms2;
	private List<Term>	terms3;

	@Before
	public void setUp () throws Exception
	{
		terms1 = new SimpleArrayList<> ();
		terms2 = new SimpleArrayList<> ();
		terms3 = new SimpleArrayList<> ();
		terms1.addAll (Arrays.asList (new Term (-23, 2), new Term (12, 1), new Term (0.5, 0)));
		terms2.addAll (Arrays.asList (new Term (-23, 2), new Term (12, 1), new Term (0.5, 0)));
		terms3.addAll (Arrays.asList (new Term (-23, 2), new Term (12, 2), new Term (0.5, 0)));
	}

	@Test
	public void testAddTermsList ()
	{
		assertThat (Terms.add (terms1, terms2), contains (new Term (-46, 2), new Term (24, 1), new Term (1, 0)));
		assertThat (Terms.add (terms2, terms3), contains (new Term (-34, 2), new Term (12, 1), new Term (1, 0)));
	}

	@Test
	public void testParseString ()
	{
		assertThat (Terms.parse ("-22x^5 (23)(y)^(-2) .2(x)^-.9"),
				contains (new Term (-22, 5), new Term (23, "y", -2), new Term (.2, -.9)));
	}

}
