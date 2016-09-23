
package edu.miracosta.cs113.dataStructures;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;

public class SimpleArrayList<E> extends AbstractList<E>
		implements
			Collection<E>,
			List<E>,
			Iterable<E>,
			RandomAccess,
			Serializable
{
	private static final long	serialVersionUID	= 3707361473005116102L;

	private static double		GROWTH_MULTIPLIER	= 3.0 / 2.0;

	private E[]					data;
	private int					size;

	static
	{
		assert GROWTH_MULTIPLIER > 1 : "GROWTH_MULTIPLIER (" + GROWTH_MULTIPLIER + ") needs to be greater than 1";
	}

	public SimpleArrayList ()
	{
		this (10);
	}

	public SimpleArrayList (Collection<? extends E> c)
	{
		this (c.size ());
		addAll (c);
	}

	@SuppressWarnings("unchecked")
	public SimpleArrayList (int initialCapacity)
	{
		data = (E[]) new Object[initialCapacity];
		size = 0;
	}

	/**
	 * Call before size changes.
	 * 
	 * @return whether reallocation took place
	 */
	private boolean checkReallocation ()
	{
		if (size >= data.length)
		{
			reallocate ();
			return true;
		}
		return false;

	}

	/**
	 * Grows the data array by {@link #GROWTH_MULTIPLIER} and copies the data
	 * over to the new array using
	 * {@link System#arraycopy(Object, int, Object, int, int)}.
	 * 
	 * @see #reallocate(int)
	 */
	private void reallocate ()
	{
		reallocate ((int) (size * GROWTH_MULTIPLIER));
	}

	/**
	 * Grows the data array to newSize and copies the data over to the new array
	 * using {@link System#arraycopy(Object, int, Object, int, int)}
	 * 
	 * <p>
	 * If newSize is less than size then the extra elements are truncated
	 * </p>
	 * 
	 * @param newSize
	 *            the new data array size
	 */
	@SuppressWarnings("unchecked")
	private void reallocate (int newSize)
	{
		E[] temp = data;
		data = (E[]) new Object[newSize];

		System.arraycopy (temp, 0, data, 0, Math.min (newSize, size));
		// TODO note optimizations like leaving a gap for space to add and thus
		// not re copying that stuff again
		// maybe for later
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
	public void add (int index, E e)
	{
		if (index != size)
		{
			boundsCheck (index);
		}

		checkReallocation ();

		if (index != size)
		{
			System.arraycopy (data, index, data, index + 1, size - index);
		}
		data[index] = e;

		++size;
		++modCount;
	}

	@Override
	public boolean addAll (int index, Collection<? extends E> c)
	{
		ensureCapcity (size + c.size ());
		// using copyLeft and data[]= over just add goes from O(n^2) to O(n) :D
		if (index != size)
		{
			boundsCheck (index);
			System.arraycopy (data, index, data, index  + c.size (), size - index);
		}

		Iterator<? extends E> iterator = c.iterator ();
		for (int i = 0; iterator.hasNext (); ++i)
		{
			data[index + i] = iterator.next ();
		}
		size += c.size ();
		++modCount;
		return true;
	}

	@Override
	public E get (int index)
	{
		return data[index];
	}

	// TODO maybe override these methods to take advantage of internals and not
	// create iterators though that's not much of a speedup
	// @Override
	// public int indexOf (Object e)
	// {
	//
	// throw new UnsupportedOperationException ("I'll get around to this at some
	// point if I need it. :D");
	// }

	// @Override
	// public int lastIndexOf (Object e)
	// {
	//
	// throw new UnsupportedOperationException ("I'll get around to this at some
	// point if I need it. :D");
	// }

	@Override
	public E remove (int index)
	{
		E temp = data[index];
		System.arraycopy (data, index + 1, data, index, size - index - 1);
		--size;
		++modCount;
		return temp;
	}

	@Override
	public E set (int index, E e)
	{
		boundsCheck (index);
		E temp = data[index];
		data[index] = e;
		return temp;
	}

	@Override
	public boolean add (E e)
	{
		add (size, e);
		return true;
	}

	@Override
	public boolean addAll (Collection<? extends E> c)
	{
		addAll (size, c);
		return true;
	}

	@Override
	public boolean contains (Object o)
	{
		return indexOf (o) > -1;
	}

	@Override
	public boolean containsAll (Collection<?> c)
	{
		boolean result = true;
		for (Object e : c)
		{
			result = result && contains (e);
		}
		return result;
	}

	@Override
	public boolean isEmpty ()
	{
		return size == 0;
	}

	@Override
	public boolean remove (Object o)
	{
		int indexOf = indexOf (o);
		if (indexOf > -1)
		{
			remove (indexOf (o));
			return true;
		}
		return false;
	}

	@Override
	protected void removeRange (int fromIndex, int toIndex)
	{
		if (toIndex - fromIndex == size)
		{
			size = 0;
		}
		else
		{
			System.arraycopy (data, toIndex, data, fromIndex, size - toIndex);
			size -= (toIndex - fromIndex);
		}
	}

	@Override
	public boolean removeAll (Collection<?> c)
	{
		// TODO fun optimization maybe?
		// continuous removal blocks can be done at once instead of one by one
		// so maybe record indices of all elements to remove and then for
		// continuous blocks, remove all at once that would require going
		// through a list and also probably sorting it before hand, and for that
		// you get that in the case of removing several items in a row you dont
		// do a shuffle of later elements for each element in the removal block,
		// otherwise you simply wasted space recording, sorting and checking
		boolean didRemove = false;
		for (Object e : c)
		{
			if (remove (e) && !didRemove)
			{
				didRemove = true;
			}
		}
		return didRemove;
	}

	@Override
	public boolean retainAll (Collection<?> c)
	{
		// TODO replace restrained with a linked list since index is not needed,
		// but fast end insertion
		SimpleArrayList<E> retained = new SimpleArrayList<> (size);
		for (int i = 0; i < size; ++i)
		{
			if (c.contains (data[i]))
			{
				retained.add (data[i]);
			}
		}
		int oldSize = size;
		data = retained.data;
		size = retained.size;
		return retained.size != oldSize;
	}

	@Override
	public int size ()
	{
		return size;
	}

	@Override
	public Object[] toArray ()
	{
		return data;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T[] toArray (T[] a)
	{
		if (a.length >= size)
		{
			System.arraycopy (data, 0, a, 0, size);
			if (a.length > size)
			{
				a[a.length - 1] = null;
			}
			return a;
		}
		T[] returnArray =  (T[]) new Object[size];
		System.arraycopy (data, 0, returnArray, 0, size);
		return returnArray;
	}

	public void ensureCapcity (int minCapacity)
	{
		if (minCapacity > data.length)
		{
			reallocate (minCapacity);
		}
	}

	public void trimToSize ()
	{
		if (data.length > size)
		{
			reallocate (size);
		}
	}

	// toString is already inherited and is fine and so is equals

}
