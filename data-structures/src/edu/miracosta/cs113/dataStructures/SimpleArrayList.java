
package edu.miracosta.cs113.dataStructures;

import java.util.AbstractList;
import java.util.Collection;
import java.util.List;

public class SimpleArrayList<E> extends AbstractList<E> implements Collection<E>, List<E>, Iterable<E>
{
	private static double	GROWTH_MULTIPLIER	= 3.0 / 2.0;

	private E[]				data;
	private int				size;

	static
	{
		assert GROWTH_MULTIPLIER > 1 : "GROWTH_MULTIPLIER (" + GROWTH_MULTIPLIER + ") needs to be greater than 1";
	}

	public SimpleArrayList ()
	{
		this (10);
	}

	@SuppressWarnings("unchecked")
	public SimpleArrayList (int initialCapacity)
	{
		data = (E[]) new Object[initialCapacity];
		size = 0;
	}

	/**
	 * Call before size changes
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
	 * {@link #copyLeft(Object[], int, Object[], int, int)}
	 * 
	 * @see #reallocate(int)
	 */
	private void reallocate ()
	{
		reallocate ((int) (size * GROWTH_MULTIPLIER));
	}

	/**
	 * Grows the data array to newSize and copies the data over to the new array
	 * using {@link #copyLeft(Object[], int, Object[], int, int)}
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

		copyLeft (data, 0, temp, 0, Math.min (newSize, size));
		// TODO note optimizations like leaving a gap for space to add and thus
		// not re copying that stuff again
		// maybe for later
	}

	/**
	 * Transfers from start to end of one array to another like memncpy in c
	 * from the left ie. from the end to the start.
	 * 
	 * <p>
	 * Requires that the destination and source have the space to take and set
	 * to.
	 * </p>
	 * <p>
	 * Can copy to and from the same array if start1 >= start2
	 * </p>
	 * <p>
	 * The pre-reqs formally:
	 * {@code assert dest.length >= numElements - start1 && source.length >= numElements - start2;}
	 * and {@code if (dest == source) assert start1 >= start2;}
	 * </p>
	 * 
	 * @param dest
	 *            destination array (must have sufficient space)
	 * @param start1
	 *            where to start copying to
	 * @param source
	 *            source array
	 * @param start2
	 *            where to start copying from
	 * @param numElements
	 *            the number of elements to copy
	 * 
	 * @return the destination array
	 * @see #copyRight(Object[], int, Object[], int, int)
	 */
	private <T> T[] copyLeft (T[] dest, int start1, T[] source, int start2, int numElements)
	{
		assert (dest.length >= numElements - start1
				&& source.length >= numElements - start2) : "exceeded start and or dest array";
		if (dest == source) assert start1 >= start2 : "use copyRight version";

		for (int i = numElements - 1; i >= 0; --i)
		{
			dest[start1 + i] = source[start2 + i];
		}
		return dest;
	}

	/**
	 * Transfers from start to end of one array to another like memncpy in c
	 * from the right ie. from the start to the end.
	 * 
	 * <p>
	 * Requires that the destination has enough space like memncpy.
	 * </p>
	 * <p>
	 * Can copy to and from the same array if start1 <= start2
	 * </p>
	 * 
	 * @param dest
	 *            destination array (must have sufficient space)
	 * @param start1
	 *            where to start copying to
	 * @param source
	 *            source array
	 * @param start2
	 *            where to start copying from
	 * @param numElements
	 *            the number of elements to copy
	 * 
	 * @return the destination array
	 * @see #copyLeft(Object[], int, Object[], int, int)
	 */
	private <T> T[] copyRight (T[] dest, int start1, T[] source, int start2, int numElements)
	{
		assert (dest.length >= numElements - start1
				&& source.length >= numElements - start2) : "exceeded start and or dest array";
		if (dest == source) assert start1 <= start2 : "use copyLeft version";

		for (int i = 0; i < numElements; ++i)
		{
			dest[start1 + i] = source[start2 + i];
		}
		return dest;
	}

	/**
	 * Throws on out of bounds, otherwise returns nothing
	 * 
	 * @param index
	 *            index to check
	 * @throws IndexOutOfBoundsException
	 */
	private void boundsCheck (int index)
	{
		if (index < 0 || index >= size)
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
			copyLeft (data, index + 1, data, index, size - index);
		}
		data[index] = e;

		++size;
		++modCount;
	}

	@Override
	public boolean addAll (int index, Collection<? extends E> c)
	{

		throw new UnsupportedOperationException ("I'll get around to this at some point if I need it. :D");
	}

	@Override
	public E get (int index)
	{
		return data[index];
	}

	// TODO maybe overide these method to take advantage of internals and not
	// create iterators but that's not much of a speedup
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
		copyRight (data, index, data, index + 1, size - index - 1);
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
		ensureCapcity (size + c.size ());
		c.forEach (this::add);
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
	public boolean removeAll (Collection<?> c)
	{
		// continuous removal blocks can be done at once instead of one by one
		// so maybe record indices of all elements to remove and then for
		// continuous blocks, remove all at once that would require going
		// through a list and also probably sorting it before hand, and for that
		// you get that in the case of removing several items in a row you dont
		// do a shuffle of later elements for each element in the removal block,
		// otherwise you simply wasted space recording, sorting and checking
		boolean didRemove = false;
		for(Object e : c)
		{
			if(remove (e) && !didRemove)
			{
				didRemove = true;
			}
		}
		return didRemove;
	}

	@Override
	public boolean retainAll (Collection<?> c)
	{
		throw new UnsupportedOperationException ("I'll get around to this at some point if I need it. :D");
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
			copyLeft (a, 0, (T[]) data, 0, size);
			if (a.length > size)
			{
				a[a.length - 1] = null;
			}
			return a;
		}
		return (T[]) copyLeft (new Object[size], 0, data, 0, size);
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
	
	// toString is already inherited and is fine

}
