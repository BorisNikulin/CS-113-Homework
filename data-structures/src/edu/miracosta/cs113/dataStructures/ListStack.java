
package edu.miracosta.cs113.dataStructures;

public class ListStack<E>
{
	// TODO make a double ended singly linked list and use it
	public SimpleLinkedList<E> data;

	public ListStack ()
	{
		data = new SimpleLinkedList<> ();
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
