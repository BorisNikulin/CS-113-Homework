
package edu.miracosta.cs113.test;

import java.util.Arrays;

import org.junit.Test;

import edu.miracosta.cs113.Change;

public class ChangeTest
{

	@Test
	public void testPrintChangeCombinations ()
	{
		System.out.println (Arrays.toString (Change.COIN.values()));
		Change.printChangeCombinations (10);
	}

}
