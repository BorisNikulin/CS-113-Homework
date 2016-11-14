
package edu.miracosta.cs113;

import java.time.Duration;

/**
 * 
 * A mock object for a printer to implemented in the future (or never :D)
 * 
 * @author Boris
 */
public class Printer
{
	private Duration printTimePerPage;
	
	public Printer (Duration printTimePerPage)
	{
		this.printTimePerPage = printTimePerPage;
	}

	public Duration getPrintTimePerPage ()
	{
		return printTimePerPage;
	}

	public void setPrintTimePerPage (Duration printTimePerPage)
	{
		this.printTimePerPage = printTimePerPage;
	}
	
	public String toString()
	{
		return "Print Speed Per Page: " + printTimePerPage.toString ();
	}
}
