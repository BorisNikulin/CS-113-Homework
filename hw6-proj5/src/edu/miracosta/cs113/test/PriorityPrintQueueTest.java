
package edu.miracosta.cs113.test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import edu.miracosta.cs113.PrintJob;
import edu.miracosta.cs113.PriorityPrintQueue;

public class PriorityPrintQueueTest
{
	PriorityPrintQueue several;
	PriorityPrintQueue empty;
	
	@Before
	public void setUp () throws Exception
	{
		several = new PriorityPrintQueue ();
		empty = new PriorityPrintQueue ();
		
		several.add (new PrintJob (13));
		several.add (new PrintJob (14));
		several.add (new PrintJob (3));
		several.add (new PrintJob (20));
		several.add (new PrintJob (8));
		several.add (new PrintJob (17));
		several.add (new PrintJob (25));
		several.add (new PrintJob (11));
	}

	@Test
	public void testSize ()
	{
		assertThat (several.size (), is(8));
		assertThat (empty.size (), is(0));
	}

	@Test
	public void testOffer ()
	{
		assertThat (empty.offer (new PrintJob (23)), is (true));
		assertThat (empty.peek ().getPageCount (), is(23));
		assertThat (empty.offer (new PrintJob (25)), is (true));
		assertThat (empty.peek ().getPageCount (), is(23));
		
		assertThat (empty.offer (new PrintJob (13)), is (true));
		assertThat (empty.peek ().getPageCount (), is(13));
		assertThat (empty.offer (new PrintJob (15)), is (true));
		assertThat (empty.peek ().getPageCount (), is(13));
		
		assertThat (empty.offer (new PrintJob (5)), is (true));
		assertThat (empty.peek ().getPageCount (), is(5));
		assertThat (empty.offer (new PrintJob (3)), is (true));
		assertThat (empty.peek ().getPageCount (), is(5));
		
		assertThat (empty.poll ().getPageCount (), is(5));
		assertThat (empty.poll ().getPageCount (), is(3));
		assertThat (empty.poll ().getPageCount (), is(13));
		assertThat (empty.poll ().getPageCount (), is(15));
		assertThat (empty.poll ().getPageCount (), is(23));
		assertThat (empty.poll ().getPageCount (), is(25));
	}

	@Test
	public void testPeek ()
	{
		
		assertThat (several.peek ().getPageCount (), is(3));
	}

	@Test
	public void testPoll ()
	{
		assertThat (several.poll ().getPageCount (), is(3));
		assertThat (several.poll ().getPageCount (), is(8));
		assertThat (several.poll ().getPageCount (), is(13));
	}

	@Test
	public void testIterator ()
	{
		Iterator<PrintJob> severalItr = several.iterator ();
		Iterator<PrintJob> emptyItr = empty.iterator ();
		
		assertThat (severalItr.hasNext(), is(true));
		
		assertThat (severalItr.next().getPageCount (), is(3));
		assertThat (severalItr.next().getPageCount (), is(8));
		assertThat (severalItr.next().getPageCount (), is(13));
		assertThat (severalItr.next().getPageCount (), is(14));
		assertThat (severalItr.next().getPageCount (), is(17));
		assertThat (severalItr.next().getPageCount (), is(11));
		assertThat (severalItr.next().getPageCount (), is(20));
		assertThat (severalItr.next().getPageCount (), is(25));
		
		assertThat (severalItr.hasNext(), is(false));
		
		assertThat (emptyItr.hasNext(), is(false));
		
	}

}
