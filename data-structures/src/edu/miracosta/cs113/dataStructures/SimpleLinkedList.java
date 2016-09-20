
package edu.miracosta.cs113.dataStructures;

import java.util.AbstractSequentialList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

public class SimpleLinkedList<E> extends AbstractSequentialList<E> implements Collection<E>, List<E>, Iterable<E>
{
	private Node<E> head = new Node<>(null);
	private Node<E> tail = new Node<>(null);
	private int size;
	
	@Override
	public ListIterator<E> listIterator (int index)
	{
		return new ListIterator<E>()
		{

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
		};
	}

	@Override
	public int size ()
	{
		return size;
	}
	
	private static class Node<E>
	{
		public Node<E> next;
		public Node<E> prev;
		public E data;
		
		public Node(E data)
		{
			this (data, null, null);
		}
		
		public Node(E data, Node<E> prev, Node<E> next)
		{
			this.data = data;
			next = null;
			prev = null;
		}
	}

}
