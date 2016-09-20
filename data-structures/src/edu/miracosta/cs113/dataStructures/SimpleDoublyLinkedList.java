
package edu.miracosta.cs113.dataStructures;

import java.util.AbstractSequentialList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

public class SimpleDoublyLinkedList<E> extends AbstractSequentialList<E> implements Collection<E>, List<E>, Iterable<E>
{
	private Node<E> head = new Node<>(null);
	private Node<E> tail = new Node<>(null);
	private int size;
	
	@Override
	public ListIterator<E> listIterator (int index)
	{

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
