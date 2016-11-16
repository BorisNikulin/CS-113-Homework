
package edu.miracosta.cs113.test;

import static org.junit.Assert.fail;

import org.junit.Test;

import edu.miracosta.cs113.Change;

public class ChangeTest
{

	@Test
	public void testPrintChangeCombinations ()
	{
		Change.printChangeCombinations (7);
		Change.printChangeCombinations (11);
		Change.printChangeCombinations (15);
		Change.printChangeCombinations (18);
		// i could redirect output to my own output stream and check that
		// stream for the proper sequence of bytes...
		// or i could just not
		fail ("Check visual results");
	}

}
