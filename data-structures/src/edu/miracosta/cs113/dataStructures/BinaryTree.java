
package edu.miracosta.cs113.dataStructures;

import java.util.Scanner;
import java.util.StringJoiner;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.ObjIntConsumer;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * A Binary Tree implementation.
 * 
 * @author Boris
 *
 */
public class BinaryTree<E>
{

	protected Node<? extends E> root;

	/**
	 * Creates a binary tree with a null root.
	 */
	public BinaryTree ()
	{
		root = null;
	}

	protected BinaryTree (Node<? extends E> root)
	{
		this.root = root;
	}

	/**
	 * Creates a tree with root being data and having passed left and right
	 * subtrees.
	 * 
	 * @param data
	 *            - the root
	 * @param left
	 *            - left subtree
	 * @param right
	 *            - right subtree
	 */
	public BinaryTree (E data, BinaryTree<? extends E> left, BinaryTree<? extends E> right)
	{
		root = new Node<E>(
				data,
				left != null ? left.root : null,
				right != null ? right.root : null);
	}

	public BinaryTree<E> getLeftSubTree()
	{
		return root.left != null ? new BinaryTree<E>(root.left) : null;
	}

	public BinaryTree<E> getRightSubTree()
	{
		return root.right != null ? new BinaryTree<E>(root.right) : null;
	}

	public E getData()
	{
		return root != null ? root.data : null;
	}

	/**
	 * Returns whether the local root of this tree is a leaf. If root is null
	 * then a null pointer exception is thrown.
	 * 
	 * @return true if and only if left and right subtrees are null
	 */
	public boolean isLeaf()
	{
		return root.left == null && root.right == null;
	}

	/**
	 * Performs the given action for each remaining element in pre-order until
	 * all elements have been processed or the action throws an exception.
	 * 
	 * @param consumer
	 *            - the action to be performed for each element
	 * 
	 * @see #forEach(Consumer, TRAVERSE)
	 */
	public void forEach(Consumer<? super E> consumer)
	{
		forEach(consumer, TRAVERSE.PRE_ORDER);
	}

	/**
	 * Performs the given action for each remaining element and depth of the
	 * element in pre-order until all elements have been processed or the action
	 * throws an exception.
	 * 
	 * @param consumer
	 *            - the action to be performed for each element where the second
	 *            argument is the depth of the element
	 */
	public void forEachWithDepth(ObjIntConsumer<? super E> consumer)
	{
		forEachWithDepth(consumer, TRAVERSE.PRE_ORDER);
	}

	/**
	 * 
	 * Performs the given action for each remaining element in the given order
	 * until all elements have been processed or the action throws an exception.
	 * 
	 * @param consumer
	 *            - the action to be performed for each element
	 * @param traverseOrder
	 *            - the order to traverse in
	 * 
	 * @see #forEach(Consumer)
	 */
	public void forEach(Consumer<? super E> consumer, TRAVERSE traverseOrder)
	{
		forEachWithDepth((element, depth) -> consumer.accept(element), traverseOrder);
	}

	/**
	 * Performs the given action for each remaining element and depth of the
	 * element in given order until all elements have been processed or the
	 * action throws an exception.
	 * 
	 * @param consumer
	 *            - the action to be performed for each element where the second
	 *            argument is the depth of the element
	 * @param traverseOrder
	 *            - the order to traverse in
	 * 
	 * @see #forEachWithDepth(ObjIntConsumer)
	 */
	public void forEachWithDepth(ObjIntConsumer<? super E> consumer, TRAVERSE traverseOrder)
	{
		if (root != null)
		{
			switch (traverseOrder)
			{
			case PRE_ORDER:
				traversePreOrder(consumer, root, 0);
				break;
			case IN_ORDER:
				traverseInOrder(consumer, root, 0);
				break;
			case POST_ORDER:
				traversePostOrder(consumer, root, 0);
				break;
			default:
				throw new UnsupportedOperationException(traverseOrder.toString() + " is not supported yet");
				// although if one added an enum then he/she would of added the
				// other necessary additions...
			}
		}
	}

	/**
	 * Performs a mutable reduction operation on the elements of this tree using
	 * a Collector in pre-order.
	 * 
	 * @param <A>
	 *            - the intermediate accumulation type of the Collector
	 * @param <R>
	 *            - the type of the result
	 * 
	 * @param collector
	 *            - the Collector describing the reduction
	 * 
	 * @return the result of the reduction
	 * 
	 * @see Collector
	 * @see #collect(Collector, TRAVERSE)
	 */
	public <A, R> R collect(Collector<E, A, R> collector)
	{
		return collect(collector, TRAVERSE.PRE_ORDER);
	}

	/**
	 * Performs a mutable reduction operation on {@link ElementDepthPair}, which
	 * are composed of the element and the depth of that element, of this tree
	 * using a Collector in pre-order.
	 * 
	 * @param <A>
	 *            - the intermediate accumulation type of the Collector
	 * @param <R>
	 *            - the type of the result
	 * 
	 * @param collector
	 *            - the Collector describing the reduction
	 * 
	 * @return the result of the reduction
	 * 
	 * @see Collector
	 * @see #collectWithDepth(Collector, TRAVERSE)
	 */
	public <A, R> R collectWithDepth(Collector<ElementDepthPair<E>, A, R> collector)
	{
		return collectWithDepth(collector, TRAVERSE.PRE_ORDER);
	}

	/**
	 * Performs a mutable reduction operation on the elements of this tree using
	 * a Collector in post-order.
	 * 
	 * @param <A>
	 *            - the intermediate accumulation type of the Collector
	 * @param <R>
	 *            - the type of the result
	 * 
	 * @param collector
	 *            - the Collector describing the reduction
	 * @param traverseOrder
	 *            - the order to traverse in
	 * 
	 * @return the result of the reduction
	 * 
	 * @see Collector
	 * @see #collect(Collector)
	 */
	public <A, R> R collect(Collector<E, A, R> collector, TRAVERSE traverseOrder)
	{
		Collector<ElementDepthPair<E>, A, R> modifiedCollector = Collector.of(
				collector.supplier(),
				(container, elementDepthTuple) -> collector.accumulator()
						.accept(container, elementDepthTuple.getElement()),
				collector.combiner(),
				collector.finisher(),
				// may type erasure genetics feel the pain i've felt for these
				// couple hours
				collector.characteristics().toArray(
						new Collector.Characteristics[collector.characteristics().size()]));

		return collectWithDepth(modifiedCollector, traverseOrder);
	}

	/**
	 * 
	 * Performs a mutable reduction operation on {@link ElementDepthPair}, which
	 * are composed of the element and the depth of that element, of this tree
	 * using a Collector in the given order.
	 * 
	 * @param <A>
	 *            - the intermediate accumulation type of the Collector
	 * @param <R>
	 *            - the type of the result
	 * 
	 * @param collector
	 *            - the Collector describing the reduction
	 * @param traverseOrder
	 * 
	 * @return the result of the reduction
	 * 
	 * @see Collector
	 * @see #collectWithDepth(Collector)
	 */
	@SuppressWarnings("unchecked")
	public <A, R> R collectWithDepth(Collector<ElementDepthPair<E>, A, R> collector, TRAVERSE traverseOrder)
	{
		A container = collector.supplier().get();

		forEachWithDepth((element, depth) -> collector.accumulator()
				.accept(container, new ElementDepthPair<E>(element, depth)));

		if (collector.characteristics().contains(Collector.Characteristics.IDENTITY_FINISH))
		{
			return (R) container;
		}
		else
		{
			return collector.finisher().apply(container);
		}
	}

	private void traversePreOrder(ObjIntConsumer<? super E> consumer, Node<? extends E> root, int depth)
	{
		consumer.accept(root.data, depth);
		if (root.left != null)
		{
			traversePreOrder(consumer, root.left, depth + 1);
		}
		if (root.right != null)
		{
			traversePreOrder(consumer, root.right, depth + 1);
		}
	}

	private void traverseInOrder(ObjIntConsumer<? super E> consumer, Node<? extends E> root, int depth)
	{
		if (root.left != null)
		{
			traverseInOrder(consumer, root.left, depth + 1);
		}
		consumer.accept(root.data, depth);
		if (root.right != null)
		{
			traverseInOrder(consumer, root.right, depth + 1);
		}
	}

	private void traversePostOrder(ObjIntConsumer<? super E> consumer, Node<? extends E> root, int depth)
	{
		if (root.left != null)
		{
			traversePostOrder(consumer, root.left, depth + 1);
		}
		if (root.right != null)
		{
			traversePostOrder(consumer, root.right, depth + 1);
		}
		consumer.accept(root.data, depth);
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		return 31 + collect(Collectors.summingInt(element -> prime * element.hashCode()));
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof BinaryTree)) return false;
		@SuppressWarnings("unchecked") BinaryTree<E> other = (BinaryTree<E>) obj;
		if (root == null && other.root != null) return false;
		if (!recursiveEquals(root, other.root)) return false;
		return true;
	}

	/**
	 * Recursively compare both trees
	 * 
	 * @param root
	 *            - not null
	 * @param otherRoot
	 * @return
	 */
	private boolean recursiveEquals(Node<? extends E> root, Node<? extends E> otherRoot)
	{
		// TODO some early exit that stops the other branch
		// exceptions? or a global stop var like with a private interal class
		boolean isEqual = root.equals(otherRoot);
		if (isEqual)
		{
			return false;
		}

		if ((root.left != null ^ otherRoot.left != null) || (root.right != null ^ otherRoot.right != null))
		{
			return false;
		}

		if (root.left != null && otherRoot.left != null)
		{
			return recursiveEquals(root.left, otherRoot.left);
		}

		if (root.right != null && otherRoot.right != null)
		{
			return recursiveEquals(root.right, otherRoot.right);
		}

		// both roots are null
		return true;
	}

	public String toString()
	{
		return collect(Collector.of(() -> new StringJoiner(", ", "[", "]"),
				(strJnr, element) -> strJnr.add(element.toString()),
				StringJoiner::merge,
				Collector.Characteristics.IDENTITY_FINISH)).toString();
	}

	/**
	 * Reads a scanner, next by next, and constructs a tree in pre-order with
	 * the string "null" signifying null. If nulls are not properly inserted
	 * (not enough nulls to end the tree leafs), the Scanner will throw no such
	 * element exception
	 * 
	 * @param scn
	 *            - the scanner to read from
	 * @return a {@link BinaryTree} in pre-order
	 */
	public static BinaryTree<String> readBinaryTree(Scanner scn)
	{
		String data = scn.next();
		if (data.equals("null"))
		{
			return null;
		}
		BinaryTree<String> left = readBinaryTree(scn);
		BinaryTree<String> right = readBinaryTree(scn);
		return new BinaryTree<String>(data, left, right);
	}

	/**
	 * Eagerly maps this tree using the mapper function. The resultant tree is a
	 * shallow copy with the map applied (unless the mapper function performs a
	 * deep copy).
	 * 
	 * <p>
	 * Works great with {@link #readBinaryTree(Scanner)} for reading in a
	 * {@code BinaryTree<String>} and mapping to a potentially more useful type.
	 * </p>
	 * 
	 * @param mapper
	 *            - the function mapping from E to R
	 * @return a new map with each element having the mapper function applied
	 */
	public <R> BinaryTree<R> map(Function<E, R> mapper)
	{
		if (root != null)
		{
			return map(mapper, root);
		}
		return new BinaryTree<R>();
	}

	private <R> BinaryTree<R> map(Function<E, R> mapper, Node<? extends E> root)
	{
		R mappedData = mapper.apply(root.data);
		BinaryTree<R> left = null;
		BinaryTree<R> right = null;
		if (root.left != null)
		{
			left = map(mapper, root.left);
		}
		if (root.right != null)
		{
			right = map(mapper, root.right);
		}
		return new BinaryTree<R>(mappedData, left, right);
	}

	public enum TRAVERSE
	{
		PRE_ORDER, IN_ORDER, POST_ORDER, // TODO depth and breadth first;
	}

	protected static class Node<E>
	{
		public E					data;
		public Node<? extends E>	left;
		public Node<? extends E>	right;

		public Node (E data, Node<? extends E> left, Node<? extends E> right)
		{
			this.data = data;
			this.left = left;
			this.right = right;
		}

		public String toString()
		{
			return data != null ? data.toString() : "null";
		}
	}

	/**
	 * Tuple for an element and depth pair. Contains accessors for both. All
	 * fields are non null.
	 * 
	 * @see ElementDepthPair#getElement()
	 * @see ElementDepthPair#getDepth()
	 * 
	 * @author Boris
	 */
	public static class ElementDepthPair<E>
	{
		private E	element;
		private int	depth;

		public ElementDepthPair (E element, int depth)
		{
			this.element = element;
			this.depth = depth;
		}

		public E getElement()
		{
			return element;
		}

		public int getDepth()
		{
			return depth;
		}
	}
}
