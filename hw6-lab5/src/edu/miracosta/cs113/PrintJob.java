
package edu.miracosta.cs113;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 
 * A mock object for a print job to implemented in the future (or never :D)
 * 
 * @author Boris
 */
public class PrintJob
{
	private long id;
	private int pageCount;
	
	public PrintJob(int pageCount)
	{
		this (ThreadLocalRandom.current ().nextLong (), pageCount);
	}
	
	public PrintJob(long id, int pageCount)
	{
		this.id = id;
		this.pageCount = pageCount;
	}

	public long getId ()
	{
		return id;
	}

	public int getPageCount ()
	{
		return pageCount;
	}
	
	public String toString ()
	{
		return "Id: " + id + ", Page Count: " + pageCount;
	}

}
