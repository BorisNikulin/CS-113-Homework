
package edu.miracosta.cs113;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Queue;
import java.util.stream.Collector;

/**
 * Represents a thread safe priority queue for print jobs. Priority is
 * determined by page count (<10, <20, and >=20) and then insertion order.
 * 
 * @author Boris
 * 
 */
public class PriorityPrintQueue extends AbstractQueue<PrintJob>
{
	// TODO eventually finish making this class efficiently thread safe
	// was left off at commit efc3e55a6fde6b

	// TODO make my own queue (my own heap + priority queue eventually)
	Queue<PrintJob>	lessThan10Pages	= new LinkedList<> ();
	Queue<PrintJob>	lessThan20Pages	= new LinkedList<> ();
	Queue<PrintJob>	moreThan20Pages	= new LinkedList<> ();

	/**
	 * Gets the queue from which the next element will be retrieved
	 * 
	 * @return the to retrieve next element from or null if empty
	 */
	private Queue<PrintJob> getHeadQueue ()
	{
		if (lessThan10Pages.size () > 0)
		{
			return lessThan10Pages;
		}
		else if (lessThan20Pages.size () > 0)
		{
			return lessThan20Pages;
		}
		else if (moreThan20Pages.size () > 0)
		{
			return moreThan20Pages;
		}
		else
		{
			return null;
		}
	}

	/**
	 * Gets the queue for the a PrintJob to be inserted based on page count
	 * 
	 * @param pageCount
	 *            - The page count on which queue to return
	 * @return the queue in which a PrintJob with page count would go
	 */
	private Queue<PrintJob> getQueue (int pageCount)
	{
		if (pageCount < 10)
		{
			return lessThan10Pages;
		}
		else if (pageCount < 20)
		{
			return lessThan20Pages;
		}
		else // >= 20
		{
			return moreThan20Pages;
		}
	}

	@Override
	public boolean offer (PrintJob e)
	{
		Objects.requireNonNull (e, "PrintJobs can not be null");
		return getQueue (e.getPageCount ()).offer (e);

	}

	@Override
	public PrintJob peek ()
	{
		Queue<PrintJob> headQueue = getHeadQueue ();
		return headQueue != null ? headQueue.peek () : null;
	}

	@Override
	public PrintJob poll ()
	{
		Queue<PrintJob> headQueue = getHeadQueue ();
		return headQueue != null ? headQueue.poll () : null;
	}

	@Override
	public Iterator<PrintJob> iterator ()
	{
		return new PriorityPrintQueueItr ();
	}

	@Override
	public int size ()
	{
		return lessThan10Pages.size () + lessThan20Pages.size () + moreThan20Pages.size ();
	}

	private class PriorityPrintQueueItr implements Iterator<PrintJob>
	{

		Iterator<PrintJob>	lessThan10Itr	= lessThan10Pages.iterator ();
		Iterator<PrintJob>	lessThan20Itr	= lessThan20Pages.iterator ();
		Iterator<PrintJob>	moreThan20Itr	= moreThan20Pages.iterator ();

		@Override
		public boolean hasNext ()
		{
			return lessThan10Itr.hasNext () || lessThan20Itr.hasNext () || moreThan20Itr.hasNext ();
		}

		@Override
		public PrintJob next ()
		{
			if (!hasNext ())
			{
				throw new NoSuchElementException ();
			}

			if (lessThan10Itr.hasNext ())
			{
				return lessThan10Itr.next ();
			}
			else if (lessThan20Itr.hasNext ())
			{
				return lessThan20Itr.next ();
			}
			else // moreThan20Itr.hasNext()
			{
				return moreThan20Itr.next ();
			}
		}

	}

	public static Collector<PrintJob, PriorityPrintQueue, PriorityPrintQueue> collector ()
	{
		return Collector.of (PriorityPrintQueue::new,
				PriorityPrintQueue::offer,
				(queue1, queue2) ->
				{
					queue1.addAll (queue2);
					return queue1;
					// that boolean return messing this up -_-
				},
				Collector.Characteristics.IDENTITY_FINISH);
	}

}
