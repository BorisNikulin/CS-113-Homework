
package edu.miracosta.cs113.dataStructures;

public class ArrayStack<E>
{
	public SimpleArrayList<E> data;

	public ArrayStack ()
	{
		// default size will depend on SimpleArrayList default capacity;
		data = new SimpleArrayList<> ();
	}

	public ArrayStack (int initCapacity)
	{
		data = new SimpleArrayList<> (initCapacity);
	}

	public boolean empty ()
	{
		return data.isEmpty ();
	}

	public E peek ()
	{
		return data.get (data.size () - 1);
	}

	public E pop ()
	{
		return data.remove (data.size () - 1);
	}

	public E push (E item)
	{
		data.add (item);
		return item;
	}

	public int search (Object o)
	{
		int index = data.indexOf (o);
		return (index != -1) ? index + 1 : index;
	}
}
