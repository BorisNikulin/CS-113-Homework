
package edu.miracosta.cs113.dataStructures;

import java.util.function.Consumer;
import java.util.function.ObjIntConsumer;

/**
 * @author Boris
 *
 */
public class BinaryTree<E>
{

	protected Node<? extends E> root;

	public BinaryTree ()
	{
		root = null;
	}

	protected BinaryTree (Node<? extends E> root)
	{
		this.root = root;
	}

	public BinaryTree (E data, BinaryTree<? extends E> left, BinaryTree<? extends E> right)
	{
		root = new Node<E> (
				data,
				left != null ? left.root : null,
				right != null ? right.root : null);
	}

	public BinaryTree<E> getLeftSubTree ()
	{
		return root.left != null ? new BinaryTree<E> (root.left) : null;
	}

	public BinaryTree<E> getRightSubTree ()
	{
		return root.right != null ? new BinaryTree<E> (root.right) : null;
	}

	public E getData ()
	{
		return root != null ? root.data : null;
	}

	public boolean isLeaf ()
	{
		return root.left == null && root.right == null;
	}

	public void forEach (Consumer<? super E> consumer)
	{
		forEach (consumer, TRAVERSE.PRE_ORDER);
	}
	
	public void forEachWithDepth (ObjIntConsumer<? super E> consumer)
	{
		forEachWithDepth (consumer, TRAVERSE.PRE_ORDER);
	}
	
	public void forEach (Consumer<? super E> consumer, TRAVERSE traverseOrder)
	{
		forEachWithDepth ((element , depth) -> consumer.accept(element), traverseOrder);
	}
	
	public void forEachWithDepth (ObjIntConsumer<? super E> consumer, TRAVERSE traverseOrder)
	{
		switch (traverseOrder)
		{
		case PRE_ORDER:
			traversePreOrder (consumer, root, 0);
			break;
		case IN_ORDER:
			traverseInOrder (consumer, root, 0);
			break;
		case POST_ORDER:
			traversePostOrder (consumer, root, 0);
			break;
		default:
			throw new UnsupportedOperationException(traverseOrder.toString () + " is not supported yet");
			//although if one added an enum then he/she would of added the other necessary additions...
		}
	}
	
	private void traversePreOrder(ObjIntConsumer<? super E> consumer, Node<? extends E> root, int depth)
	{
		consumer.accept (root.data, depth);
		if(root.left != null)
		{
			traversePreOrder(consumer, root.right, depth + 1);
		}
		if(root.right != null)
		{
			traversePreOrder(consumer, root.right, depth + 1);
		}	
	}
	
	private void traverseInOrder(ObjIntConsumer<? super E> consumer, Node<? extends E> root, int depth)
	{
		if(root.left != null)
		{
			traversePreOrder(consumer, root.right, depth + 1);
		}
		consumer.accept (root.data, depth);
		if(root.right != null)
		{
			traversePreOrder(consumer, root.right, depth + 1);
		}	
	}
	
	private void traversePostOrder(ObjIntConsumer<? super E> consumer, Node<? extends E> root, int depth)
	{
		if(root.left != null)
		{
			traversePreOrder(consumer, root.right, depth + 1);
		}
		if(root.right != null)
		{
			traversePreOrder(consumer, root.right, depth + 1);
		}
		consumer.accept (root.data, depth);
	}

	public enum TRAVERSE
	{
		PRE_ORDER, IN_ORDER, POST_ORDER, // TODO depth or breadth first;
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
}
