
package edu.miracosta.cs113;

import java.util.List;

import edu.miracosta.cs113.dataStructures.SimpleArrayList;

/**
 * A Class for managing a list of students.
 * 
 * @author Boris
 *
 */
public class StudentList
{
	// Probably should make a student class with all the student data
	// but im kind of done -_- and short on time so the only data will be the
	// student name (also makes removing by name much easier)
	private List<String> students;

	public StudentList ()
	{
		students = new SimpleArrayList<> ();
	}

	public StudentList (StudentList other)
	{
		students = new SimpleArrayList<> (other.students.size ());
		other.students.forEach (students::add);
	}

	/**
	 * Adds a student name to the front of the list.
	 * 
	 * @param name
	 *            the name of the student
	 * @return true
	 */
	public boolean addToFront (String name)
	{
		// for this operation a linked list i much much preferred and since no
		// random access needed linked list should be the data structure used
		students.add (0, name);
		return true;
	}

	/**
	 * Adds a student name to the end of the list.
	 * 
	 * @param name
	 *            the name of the student
	 * @return true
	 */
	public boolean addToEnd (String name)
	{
		// for this operation a linked list i much much preferred
		// and since no random access (not much in other methods)
		// linked list should be the data structure used
		students.add (name);
		return true;
	}

	/**
	 * Removes a student from the start of the list.
	 * 
	 * @return the name of the student removed
	 */
	public String removeFromFront ()
	{
		// for this operation a linked list i much much preferred
		// and since no random access (not much in other methods)
		// linked list should be the data structure used
		return students.remove (0);
	}

	/**
	 * Removes the first occurrence of a students name from the list.
	 * 
	 * @param name
	 *            the name of the student
	 * @return whether a student was removed
	 */
	public boolean removeByName (String name)
	{
		// for this operation a linked list i much much preferred
		// and since no random access (not much in other methods)
		// linked list should be the data structure used
		return students.remove (name);
	}

	@Override
	public int hashCode ()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((students == null) ? 0 : students.hashCode ());
		return result;
	}

	@Override
	public boolean equals (Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass () != obj.getClass ())
		{
			return false;
		}
		StudentList other = (StudentList) obj;
		if (students == null)
		{
			if (other.students != null)
			{
				return false;
			}
		}
		else if (!students.equals (other.students))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return students.toString ();
	}
}
