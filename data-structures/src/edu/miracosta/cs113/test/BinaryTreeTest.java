
package edu.miracosta.cs113.test;

import static org.hamcrest.Matchers.contains; //not in junit
import static org.hamcrest.Matchers.nullValue; //not in junit
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.miracosta.cs113.dataStructures.BinaryTree;
import edu.miracosta.cs113.dataStructures.BinaryTree.TRAVERSE;

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
		ArrayList<Integer> collectedTraversal = new ArrayList<>(5);
		intTree.forEach(collectedTraversal::add, TRAVERSE.PRE_ORDER);
		assertThat(collectedTraversal, contains(11, 21, 31, 22, 32));

		collectedTraversal = new ArrayList<>(5);
		intTree.forEach(collectedTraversal::add, TRAVERSE.IN_ORDER);
		assertThat(collectedTraversal, contains(31, 21, 11, 22, 32));

		collectedTraversal = new ArrayList<>(5);
		intTree.forEach(collectedTraversal::add, TRAVERSE.POST_ORDER);
		assertThat(collectedTraversal, contains(31, 21, 32, 22, 11));
	}

	@Test
	public void testForEachWithDepth()
	{
		intTree.forEachWithDepth((element, depth) -> assertThat((element / 10) - depth, is(1)), TRAVERSE.PRE_ORDER);
		intTree.forEachWithDepth((element, depth) -> assertThat((element / 10) - depth, is(1)), TRAVERSE.IN_ORDER);
		intTree.forEachWithDepth((element, depth) -> assertThat((element / 10) - depth, is(1)), TRAVERSE.POST_ORDER);
	}

	@Test
	public void testCollect()
	{
		int sum = 11 + 21 + 31 + 22 + 32;
		assertThat(intTree.collect(Collectors.summingInt(num -> num), TRAVERSE.PRE_ORDER), is(sum));
		assertThat(intTree.collect(Collectors.summingInt(num -> num), TRAVERSE.IN_ORDER), is(sum));
		assertThat(intTree.collect(Collectors.summingInt(num -> num), TRAVERSE.POST_ORDER), is(sum));
	}

	@Test
	public void testCollectWithDepth()
	{
		int sumDepth = 0 + 1 + 2 + 1 + 2;
		assertThat(intTree.collectWithDepth(Collectors.summingInt(tuple -> tuple.getDepth()), TRAVERSE.PRE_ORDER),
				is(sumDepth));
		assertThat(intTree.collectWithDepth(Collectors.summingInt(tuple -> tuple.getDepth()), TRAVERSE.IN_ORDER),
				is(sumDepth));
		assertThat(intTree.collectWithDepth(Collectors.summingInt(tuple -> tuple.getDepth()), TRAVERSE.POST_ORDER),
				is(sumDepth));
	}

	@Test
	public void testEquals()
	{
		//@formatter:off
		BinaryTree<Integer> otherIntTree = new BinaryTree<>(11,
						new BinaryTree<>(21,
								new BinaryTree<>(31, null, null),
								null),
						new BinaryTree<>(22,
								null,
								new BinaryTree<>(32, null, null)));
		//@formatter:on

		assertThat(intTree.equals(otherIntTree), is(true));
	}

	@Test
	public void testToString()
	{
		assertThat(intTree.toString(), is("[11, 21, 31, 22, 32]"));
		assertThat(empty.toString(), is("[]"));
	}

	@Test
	public void mapTest()
	{
		//@formatter:off
		BinaryTree<String> stringTree = new BinaryTree<>("11",
						new BinaryTree<>("21",
								new BinaryTree<>("31", null, null),
								null),
						new BinaryTree<>("22",
								null,
								new BinaryTree<>("32", null, null)));
		//@formatter:on

		BinaryTree<Integer> otherIntTree = stringTree.map(Integer::parseInt);
		assertThat(intTree.equals(otherIntTree), is(true));
	}

	@Test
	public void testReadBinaryTree()
	{
		Scanner scn = new Scanner("11 21 31 null null null 22 null 32 null null");
		BinaryTree<Integer> otherIntTree = BinaryTree.readBinaryTree(scn).map(Integer::parseInt);
		assertThat(intTree.equals(otherIntTree), is(true));

	}

}
