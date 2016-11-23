
package edu.miracosta.cs113.test;

import static org.hamcrest.Matchers.nullValue; //not in junit
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.miracosta.cs113.dataStructures.BinaryTree;

public class BinaryTreeTest
{
	public BinaryTree<Integer>	intTree;
	public BinaryTree<Integer>	empty;

	// ooo rules
	@Rule
	// ooo junit >= 4.7 stuff for exceptions
	// i might start testing exceptions (but that's more work :c)
	public ExpectedException	exceptionGrabber	= ExpectedException.none();

	@Before
	public void setUp() throws Exception
	{
		//@formatter:off
		intTree = new BinaryTree<>(11,
				new BinaryTree<>(21,
						new BinaryTree<>(31, null, null),
						null),
				new BinaryTree<>(22,
						null,
						new BinaryTree<>(32, null, null)));
		//11
		//   21
		//      31
		//      null
		//   22
		//      null
		//      32
		//@formatter:on

		empty = new BinaryTree<>();
	}

	@Test
	public void testGetDataAndGetSubTree()
	{
		assertThat(intTree.getData(), is(11));
		assertThat(intTree.getLeftSubTree().getData(), is(21));
		assertThat(intTree.getRightSubTree().getData(), is(22));
		assertThat(intTree.getRightSubTree().getRightSubTree().getData(), is(32));
		assertThat(intTree.getRightSubTree().getRightSubTree().getRightSubTree(), is(nullValue()));
		assertThat(empty.getData(), is(nullValue()));
	}

	@Test
	public void testIsLeaf()
	{
		assertThat(intTree.getRightSubTree().getRightSubTree().isLeaf(), is(true));

		exceptionGrabber.expect(NullPointerException.class);
		assertThat(empty.isLeaf(), is(false));
		assertThat(intTree.getRightSubTree().getRightSubTree().getRightSubTree().isLeaf(), is(false));
	}

	@Test
	public void testForEachConsumer()
	{
		intTree.forEach(System.out::println);
	}

	@Test
	public void testForEachWithDepth()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testCollect()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testCollectWithDepth()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testToString()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testReadBinaryTree()
	{
		fail("Not yet implemented");
	}

}
