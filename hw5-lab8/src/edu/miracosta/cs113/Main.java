
package edu.miracosta.cs113;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Main
{

	public static void main (String[] args)
	{
		Stack<Integer> intStack = new Stack<> ();
		Stack<Integer> intStack2 = new Stack<> ();
		Queue<Integer> intQueue = new LinkedList<> ();
		;

		// All 3 are equivalent (I like the first)
		Arrays.asList (-1, 15, 23, 44, 4, 99).forEach (intStack::push);
		// for(Integer n : new Integer[]{-1, 15, 23, 44, 4, 99})
		// for(Integer n : Arrays.asList(-1, 15, 23, 44, 4, 99))
		// {
		// intStack.push(n);
		// }
		System.out.println ("Stack 1: " + intStack);
		while (!intStack.empty ())
		{
			Integer temp = intStack.pop ();
			intStack2.push (temp);
			intQueue.offer (temp);
		}
		System.out.println ("Stack 2: " + intStack2);
		System.out.println ("Queue 1: " + intQueue);
	}

}
