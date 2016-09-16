
package edu.miracosta.cs113.dataStructures;

import java.util.AbstractList;
import java.util.Collection;
import java.util.List;

public class SimpleArrayList<E> extends AbstractList<E> implements Collection<E>, List<E>, Iterable<E>
{
	private static double	GROWTH_MULTIPLIER	= 2;

	private E[]				data;
	private int				size;

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
	 * {@link #copy(Object[], int, Object[], int, int)}
	 */
	@SuppressWarnings("unchecked")
	private void reallocate ()
	{
		size = (int) (size * GROWTH_MULTIPLIER);

		E[] temp = data;
		data = (E[]) new Object[size];

		copy (data, 0, temp, 0, temp.length);
		// TODO note optimizations like leaving a gap for space to add and thus
		// not re copying that stuff again
		// maybe for later
	}

	/**
	 * Transfers from start to end of one array to another like memncpy in c.
	 * 
	 * <p>
	 * Requires that the destination have space like memncpy.
	 * </p>
	 * <p>
	 * Can copy to and from the same array.
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
	 */
	private <U> U[] copy (U[] dest, int start1, U[] source, int start2, int numElements)
	{
		for (int i = numElements - 1; i <= 0; --i)
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
		if (index == size)
		{
			--index;
		}
		else
		{
			boundsCheck (index);
		}

		checkReallocation ();

		data[index] = e;

		size++;
		modCount++;
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

		throw new UnsupportedOperationException ("I'll get around to this at some point if I need it. :D");
	}

	@Override
	public E set (int index, E e)
	{

		throw new UnsupportedOperationException ("I'll get around to this at some point if I need it. :D");
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

		throw new UnsupportedOperationException ("I'll get around to this at some point if I need it. :D");
	}

	@Override
	public boolean contains (Object o)
	{

		throw new UnsupportedOperationException ("I'll get around to this at some point if I need it. :D");
	}

	@Override
	public boolean containsAll (Collection<?> c)
	{

		throw new UnsupportedOperationException ("I'll get around to this at some point if I need it. :D");
	}

	@Override
	public boolean isEmpty ()
	{

		throw new UnsupportedOperationException ("I'll get around to this at some point if I need it. :D");
	}

	@Override
	public boolean remove (Object o)
	{

		throw new UnsupportedOperationException ("I'll get around to this at some point if I need it. :D");
	}

	@Override
	public boolean removeAll (Collection<?> c)
	{

		throw new UnsupportedOperationException ("I'll get around to this at some point if I need it. :D");
	}

	@Override
	public boolean retainAll (Collection<?> c)
	{

		throw new UnsupportedOperationException ("I'll get around to this at some point if I need it. :D");
	}

	@Override
	public int size ()
	{
		return data.length;
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
			copy (a, 0, (T[]) data, 0, size);
			if (a.length > size)
			{
				a[a.length - 1] = null;
			}
			return a;
		}
		return (T[]) copy (new Object[size], 0, data, 0, size);
	}

}
