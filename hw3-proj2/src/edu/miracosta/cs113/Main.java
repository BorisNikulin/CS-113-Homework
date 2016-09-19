
package edu.miracosta.cs113;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Main
{

	public static void main (String[] args)
	{
		// I could make it more flexible by asking for file paths or using
		// command line args but i'm already tired of doing thing and time is
		// pretty much gone so after this point it's just to get it done
		try
		{
			List<Term> list1 = Files.readAllLines (Paths.get ("test-add-terms-list1.txt")).stream ()
					.map (Terms::parse)
					.flatMap (List<Term>::stream)
					.collect (Collectors.toList ());
			List<Term> list2 = Files.readAllLines (Paths.get ("test-add-terms-list2.txt")).stream ()
					.map (Terms::parse)
					.flatMap (List<Term>::stream)
					.collect (Collectors.toList ());
			list1.sort (Term.COMPARE_BY_VAR_THEN_EXPONENT);
			list2.sort (Term.COMPARE_BY_VAR_THEN_EXPONENT);
			List<Term> addedList = Terms.add (list1, list2);
			System.out.println (list1);
			System.out.println ("+");
			System.out.println (list2);
			System.out.println ("=");
			System.out.println (addedList);
		}
		catch (IOException e)
		{
			e.printStackTrace ();
		}
	}

}
