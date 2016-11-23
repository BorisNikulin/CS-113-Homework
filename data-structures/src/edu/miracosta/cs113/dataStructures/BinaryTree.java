
package edu.miracosta.cs113.dataStructures;

import java.util.Scanner;
import java.util.StringJoiner;
import java.util.function.Consumer;
import java.util.function.ObjIntConsumer;
import java.util.stream.Collector;

/**
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

	public BinaryTree<E> getLeftSubTree ()
	{
		return root.left != null ? new BinaryTree<E>(root.left) : null;
	}

	public BinaryTree<E> getRightSubTree ()
	{
		return root.right != null ? new BinaryTree<E>(root.right) : null;
	}

	public E getData ()
	{
		return root != null ? root.data : null;
	}

	public boolean isLeaf ()
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
	public void forEach (Consumer<? super E> consumer)
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
	public void forEachWithDepth (ObjIntConsumer<? super E> consumer)
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
	public void forEach (Consumer<? super E> consumer, TRAVERSE traverseOrder)
	{
		forEachWithDepth( (element, depth) -> consumer.accept(element), traverseOrder);
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
	public void forEachWithDepth (ObjIntConsumer<? super E> consumer, TRAVERSE traverseOrder)
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
	public <A, R> R collect (Collector<E, A, R> collector)
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
	public <A, R> R collectWithDepth (Collector<ElementDepthPair<E>, A, R> collector)
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
	public <A, R> R collect (Collector<E, A, R> collector, TRAVERSE traverseOrder)
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
	public <A, R> R collectWithDepth (Collector<ElementDepthPair<E>, A, R> collector, TRAVERSE traverseOrder)
	{
		A container = collector.supplier().get();

		forEachWithDepth( (element, depth) -> collector.accumulator()
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

	private void traversePreOrder (ObjIntConsumer<? super E> consumer, Node<? extends E> root, int depth)
	{
		consumer.accept(root.data, depth);
		if (root.left != null)
		{
			traversePreOrder(consumer, root.right, depth + 1);
		}
		if (root.right != null)
		{
			traversePreOrder(consumer, root.right, depth + 1);
		}
	}

	private void traverseInOrder (ObjIntConsumer<? super E> consumer, Node<? extends E> root, int depth)
	{
		if (root.left != null)
		{
			traversePreOrder(consumer, root.right, depth + 1);
		}
		consumer.accept(root.data, depth);
		if (root.right != null)
		{
			traversePreOrder(consumer, root.right, depth + 1);
		}
	}

	private void traversePostOrder (ObjIntConsumer<? super E> consumer, Node<? extends E> root, int depth)
	{
		if (root.left != null)
		{
			traversePreOrder(consumer, root.right, depth + 1);
		}
		if (root.right != null)
		{
			traversePreOrder(consumer, root.right, depth + 1);
		}
		consumer.accept(root.data, depth);
	}

	public String toString ()
	{
		return collect(Collector.of( () -> new StringJoiner(", ", "[", "]"),
				(strJnr, element) -> strJnr.add(element.toString()),
				StringJoiner::merge,
				Collector.Characteristics.IDENTITY_FINISH)).toString();
	}

	/**
	 * Reads a scanner, next by next, and constructs a tree in pre-order
	 * 
	 * @param scn - the scanner to read from
	 * @return a {@link BinaryTree} in pre-order
	 */
	public static BinaryTree<String> readBinaryTree(Scanner scn)
	{
		String data = scn.next();
		if(data.equals("null"))
		{
			return null;
		}
		BinaryTree<String> left = readBinaryTree(scn);
		BinaryTree<String> right = readBinaryTree(scn);
		return new BinaryTree<String>(data, left, right);
	}
	
	public enum TRAVERSE
	{
		PRE_ORDER, IN_ORDER, POST_ORDER, // TODO depth and breadth first;
	}

	private static class Node<E>
	{
		public E					data;
		public Node<? extends E>	left;
		public Node<? extends E>	right;

		public Node (E data)
		{
			this.data = data;
			this.left = null;
			this.right = null;
		}

		public Node (E data, Node<? extends E> left, Node<? extends E> right)
		{
			this.data = data;
			this.left = left;
			this.right = right;
		}
	}

	/**
	 * Tuple for an element and depth pair. Contains accessors for both.
	 * 
	 * @see ElementDepthPair#getElement()
	 * @see ElementDepthPair#getDepth()
	 * 
	 * @author Boris
	 */
	private static class ElementDepthPair<E>
	{
		private E	element;
		private int	depth;

		public ElementDepthPair (E element, int depth)
		{
			this.element = element;
			this.depth = depth;
		}

		public E getElement ()
		{
			return element;
		}

		public int getDepth ()
		{
			return depth;
		}
	}
}
