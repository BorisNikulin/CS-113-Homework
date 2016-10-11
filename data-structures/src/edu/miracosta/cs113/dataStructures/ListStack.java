
package edu.miracosta.cs113.dataStructures;

import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class ListStack<E> implements Iterable<E>
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
		if(obj instanceof Collection)
		{
			@SuppressWarnings("rawtypes") Collection other = (Collection) obj;
			if(data.size () != other.size ())
			{
				return false;
			}
			@SuppressWarnings("rawtypes") Iterator cItr = other.iterator();
			for(int i = data.size() - 1; i >= 0; --i)
			{
				if(!data.get (i).equals (cItr.next()))
				{
					return false;
				}
			}
		}
		if (getClass () != obj.getClass ())
		{
			return false;
		}
		@SuppressWarnings("rawtypes") ArrayStack other = (ArrayStack) obj;
		if (data == null)
		{
			if (other.data != null)
			{
				return false;
			}
		}
		else if (!data.equals (other.data))
		{
			return false;
		}
		return true;
	}
	
	public String toString()
	{
		return data.stream ()
				.map (Object::toString)
				.collect (Collectors.joining (",", "[", "]"));
	}
	
	@Override
	public Iterator<E> iterator ()
	{
		return new StackItr();
	}
	
	private class StackItr implements Iterator<E>
	{
		ListIterator<E> dataItr = data.listIterator (data.size ());
		@Override
		public boolean hasNext ()
		{
			return dataItr.hasPrevious ();
		}

		@Override
		public E next ()
		{
			return dataItr.previous ();
		}
	}
	
}
