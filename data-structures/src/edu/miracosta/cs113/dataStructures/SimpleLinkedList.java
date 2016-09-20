
package edu.miracosta.cs113.dataStructures;

import java.util.AbstractSequentialList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

public class SimpleLinkedList<E> extends AbstractSequentialList<E> implements Collection<E>, List<E>, Iterable<E>
{
	private Node<E>	head;
	private Node<E>	tail;
	private int		size;

	{
		head = new Node<> (null, null, null);
		tail = new Node<> (null, null, null);
		head.next = tail;
		tail.prev = head;
	}

	private Node<E> getNode (int index)
	{

	}

	/**
	 * Throws on out of bounds, otherwise returns nothing.
	 * 
	 * @param index
	 *            index to check
	 * @throws IndexOutOfBoundsException
	 */
	private void boundsCheck (int index)
	{
		if (index >= size || index < 0)
		{
			throw new IndexOutOfBoundsException (index + "is not in bounds of [0, " + size + "]");
		}
	}

	@Override
	public ListIterator<E> listIterator (int index)
	{
		/**
		 * This Class only exists and is not anonymous because damn shadowing.
		 * -_-
		 * 
		 * @author Boris
		 *
		 */
		class ListItr implements ListIterator<E>
		{
			private Node<E>	next			= head;
			private Node<E>	lastReturned	= null;
			private int		index;

			public ListItr (int index)
			{
				this.index = index;
			}

			@Override
			public void add (E e)
			{
				// TODO Auto-generated method stub
				++modCount;
			}

			@Override
			public boolean hasNext ()
			{
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean hasPrevious ()
			{
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public E next ()
			{
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int nextIndex ()
			{
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public E previous ()
			{
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int previousIndex ()
			{
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public void remove ()
			{
				// TODO Auto-generated method stub
				++modCount;
			}

			@Override
			public void set (E e)
			{
				// TODO Auto-generated method stub

			}

		}

		return new ListItr (index);
	}

	@Override
	public int size ()
	{
		return size;
	}

	private static class Node<E>
	{
		public E		data;
		public Node<E>	next;
		public Node<E>	prev;

		public Node (E data, Node<E> prev, Node<E> next)
		{
			this.data = data;
			next = null;
			prev = null;
		}
	}

	/**
	 * Is the power house of the list. Used by {@link AbstractSequentialList} to
	 * back all SimpleLinkedList list methods
	 * 
	 * <p>
	 * This Class only exists and is not anonymous because damn shadowing. -_-
	 * </p>
	 * 
	 * @author Boris
	 *
	 */
	class ListItr implements ListIterator<E>
	{
		private Node<E>	next			= head;
		private Node<E>	lastReturned	= null;
		private int		index;

		public ListItr (int index)
		{
			this.index = index;
		}

		@Override
		public void add (E e)
		{
			// TODO Auto-generated method stub
			++modCount;
		}

		@Override
		public boolean hasNext ()
		{
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean hasPrevious ()
		{
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public E next ()
		{
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int nextIndex ()
		{
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public E previous ()
		{
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int previousIndex ()
		{
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void remove ()
		{
			// TODO Auto-generated method stub
			++modCount;
		}

		@Override
		public void set (E e)
		{
			// TODO Auto-generated method stub

		}
	}

}
