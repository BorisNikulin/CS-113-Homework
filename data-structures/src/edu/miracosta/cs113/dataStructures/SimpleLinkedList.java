
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
		// hmm
		// to dummy
		// or not to dummy ...
		head = new Node<> (null, null, null);
		tail = new Node<> (null, null, null);
		head.next = tail;
		tail.prev = head;
	}

	/**
	 * @param index
	 * @return the node at the index or the tail if size is 0
	 */
	private Node<E> node (int index)
	{
		if(index == size)
		{
			return tail;
		}
		
		Node<E> node;
		if (index <= (size >> 1))
		{
			node = head.next;
			for (int i = 0; i < index; ++i)
			{
				node = node.next;
			}
		}
		else
		{
			node = tail.prev; // header.prev <- smart java
			// I did check out the actual linked list implementation in java
			// (that's where the bitshift came from (instead of using divide by
			// 2)) but anyway the whole single dummy and single ended/marked
			// node was pretty clever and allows some nice code...
			// but I'm just going to make it dumb and simple...
			// also going to take the name simplification and instead of using
			// getNode I'll call it just node
			// although the java8 linked list seems to not be circular anymore
			// (in the backend) hmmm... anyway neat either way
			for (int i = size - 1; i > index; --i)
			{
				node = node.prev;
			}
		}
		return node;
	}

	private void addAfter (Node<E> pred, E element)
	{
		Node<E> succ = pred.next;
		Node<E> newNode = new Node<> (pred, element, succ);
		pred.next = newNode;
		succ.prev = newNode;
		++size;
		++modCount;
	}

	@Override
	public ListIterator<E> listIterator (int index)
	{
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

		public Node (Node<E> prev, E data, Node<E> next)
		{
			this.data = data;
			this.next = next;
			this.prev = prev;
		}

		public String toString ()
		{
			return (data != null) ? data.toString () : "null";
		}
	}

	/**
	 * Is the power house of the list. Used by {@link AbstractSequentialList} to
	 * back all SimpleLinkedList list methods
	 * 
	 * <p>
	 * This Class only (well mostly in jest (but still >:| )) exists and is not
	 * anonymous because damn shadowing. -_-
	 * </p>
	 * 
	 * @author Boris
	 *
	 */
	private class ListItr implements ListIterator<E>
	{
		private Node<E>	lastReturned;
		private int		index;

		public ListItr (int index)
		{
			lastReturned = node (index).prev;
			this.index = index;
		}

		@Override
		public void add (E e)
		{
			addAfter (lastReturned, e); // modCount is already incremented
			next ();
		}

		@Override
		public boolean hasNext ()
		{
			return index != size;
		}

		@Override
		public boolean hasPrevious ()
		{
			return index != 0;
		}

		@Override
		public E next ()
		{
			// TODO bounds check
			lastReturned = lastReturned.next;
			++index;
			return lastReturned.data;
		}

		@Override
		public int nextIndex ()
		{
			return index + 1;
		}

		@Override
		public E previous ()
		{
			// TODO bounds check
			lastReturned = lastReturned.prev;
			--index;
			return lastReturned.data;
		}

		@Override
		public int previousIndex ()
		{
			return index - 1;
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
			lastReturned.data = e;
		}
	}
}
