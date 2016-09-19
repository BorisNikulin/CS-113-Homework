
package edu.miracosta.cs113;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class SelfCheckProblems
{

	public static void main (String[] args)
	{
	}

	// pg69 self-check programming #1, 2

	/**
	 * Replaces all occurrences of oldItem with newItem in the list viewed by
	 * the list iterator.
	 * 
	 * @param itr
	 *            a listIterator
	 * @param oldItem
	 *            item to look for
	 * @param newItem
	 *            the replacement if oldItem is found
	 */
	public static <T> void replace (ListIterator<T> itr, T oldItem, T newItem)
	{
		if (itr.hasNext ())
		{
			for (T curItem = itr.next (); itr.hasNext (); curItem = itr.next ())
			{
				if (curItem.equals (oldItem))
				{
					itr.set (newItem);
				}
			}
		}
	}

	/**
	 * Removes all occurrences of oldItem in the list viewed by the list
	 * iterator.
	 * 
	 * @param itr
	 *            an iterator
	 * @param target
	 *            item to look for
	 * @param newItem
	 *            the replacement if oldItem is found
	 */
	public static <T> void delete (Iterator<T> itr, T target)
	{
		if (itr.hasNext ())
		{
			for (T curItem = itr.next (); itr.hasNext (); curItem = itr.next ())
			{
				if (curItem.equals (target))
				{
					itr.remove ();
				}
			}
		}
	}

	// pg71 self-check programming #1, 2
	private class DirectoryEntry
	{
		private String	name;
		private String	number;

		public DirectoryEntry (String name, String number)
		{
			this.name = name;
			this.number = number;
		}

		/**
		 * @return the name
		 */
		public String getName ()
		{
			return name;
		}

		/**
		 * @param name
		 *            the name to set
		 */
		@SuppressWarnings("unused")
		public void setName (String name)
		{
			this.name = name;
		}

		/**
		 * @return the number
		 */
		public String getNumber ()
		{
			return number;
		}

		/**
		 * @param number
		 *            the number to set
		 */
		public void setNumber (String number)
		{
			this.number = number;
		}
	}

	private ArrayList<DirectoryEntry> theDirectory = new ArrayList<DirectoryEntry> ();

	public String addOrChangeEntry (String aName, String newNumber)
	{
		String oldNum = null;
		for (DirectoryEntry entry : theDirectory)
		{
			if (aName == entry.getName ())
			{
				entry.setNumber (newNumber);
				oldNum = entry.getNumber ();
			}
		}
		if (oldNum == null)
		{
			theDirectory.add (new DirectoryEntry (aName, newNumber));
		}
		return oldNum;
	}

	public DirectoryEntry removeEntry(String aName)
	{
		DirectoryEntry removed = null;
		for (int i = 0; i < theDirectory.size(); i++)
		{
			if(theDirectory.get (i).getName () == aName)
			{
				removed = theDirectory.get (i);
				theDirectory.remove (i);
			}
		}
		return removed;
	}
	
}
