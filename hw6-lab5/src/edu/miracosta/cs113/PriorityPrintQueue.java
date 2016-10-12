
package edu.miracosta.cs113;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Represents a thread safe priority queue for print jobs. Priority is
 * determined by page count (<10, <20, and >=20) and then insertion order.
 * 
 * @author Boris
 * 
 */
public class PriorityPrintQueue extends AbstractQueue<PrintJob> implements BlockingQueue<PrintJob>
{
	// TODO make my own queue (my own heap + priority queue eventually)
	private Queue<PrintJob>	lessThan10Pages	= new LinkedList<> ();
	private Queue<PrintJob>	lessThan20Pages	= new LinkedList<> ();
	private Queue<PrintJob>	moreThan20Pages	= new LinkedList<> ();

	private ReentrantLock	lessThan10lock	= new ReentrantLock ();
	private ReentrantLock	lessThan20lock	= new ReentrantLock ();
	private ReentrantLock	moreThan20lock	= new ReentrantLock ();

	@Override
	public boolean offer (PrintJob e)
	{
		Objects.requireNonNull (e, "Elements can not be null");
		
		boolean didAdd = false;
		if (e.getPageCount () < 10)
		{
			if (lessThan10lock.tryLock ())
			{
				try
				{
					lessThan10Pages.add (e);
					didAdd = true;
				}
				finally
				{
					lessThan10lock.unlock ();
				}
			}

		}
		else if (e.getPageCount () < 20)
		{
			if (lessThan20lock.tryLock ())
			{
				try
				{
					lessThan20Pages.add (e);
					didAdd = true;
				}
				finally
				{
					lessThan20lock.unlock ();
				}
			}
		}
		else
		{
			if (moreThan20lock.tryLock ())
			{
				try
				{
					moreThan20Pages.add (e);
					didAdd = true;
				}
				finally
				{
					moreThan20lock.unlock ();
				}
			}
		}

		return didAdd;
	}

	@Override
	public PrintJob peek ()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PrintJob poll ()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<PrintJob> iterator ()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size ()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int drainTo (Collection<? super PrintJob> c)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int drainTo (Collection<? super PrintJob> c, int maxElements)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean offer (PrintJob e, long timeout, TimeUnit unit) throws InterruptedException
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PrintJob poll (long timeout, TimeUnit unit) throws InterruptedException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void put (PrintJob e) throws InterruptedException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public int remainingCapacity ()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PrintJob take () throws InterruptedException
	{
		// TODO Auto-generated method stub
		return null;
	}

}
