
package edu.miracosta.cs113;

import java.util.List;
import java.util.Random;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main
{

	public static void main (String[] args)
	{
		Random rand = new Random (24745658);
		// [1, 50] random page nums
		IntSupplier pageCountSupplier = () -> rand.nextInt (50) + 1;
		
		PriorityPrintQueue ppq = generatePriorityPrintQueue (pageCountSupplier, 100);
		List<PrintJob> printJobInsertionOrder = Stream
				.generate ( () -> new PrintJob (pageCountSupplier.getAsInt ()))
				.limit (100)
				.collect (Collectors.toList ());

		System.out.println (ppq.toString ().replaceAll (",", ",\n"));
	}

	public static PriorityPrintQueue generatePriorityPrintQueue (IntSupplier randomPageLengths, long numPrintJobs)
	{
		PriorityPrintQueue ppq = new PriorityPrintQueue ();
		for (long i = 0; i < numPrintJobs; ++i)
		{
			ppq.add (new PrintJob (randomPageLengths.getAsInt ()));
		}
		return ppq;
	}

}
