
package edu.miracosta.cs113;

import java.util.List;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Main
{

	public static final int SEED = 46525;

	public static void main (String[] args)
	{
		PriorityPrintQueue ppq = generatePrintJobs (PriorityPrintQueue.collector (), SEED, 100);
		List<PrintJob> printJobInsertionOrder = generatePrintJobs (Collectors.toList (), SEED, 100);

		System.out.println (ppq.toString ().replaceAll (",", ",\n"));
		System.out.println ("\n----------------------\n");
		System.out.println (printJobInsertionOrder.toString ().replaceAll (",", ",\n"));
	}

	@SuppressWarnings("unchecked")
	public static <A, R> R generatePrintJobs (Collector<PrintJob, A, R> accumulator, int seed,
			long numPrintJobs)
	{
		Random rand = new Random (seed);

		A container = accumulator.supplier ().get ();
		for (long i = 0; i < numPrintJobs; ++i)
		{
			accumulator.accumulator ().accept (container, new PrintJob (rand.nextLong (), rand.nextInt (50) + 1));
		}
		if (accumulator.characteristics ().contains (Collector.Characteristics.IDENTITY_FINISH))
		{
			return (R) container;
		}
		else
		{
			return accumulator.finisher ().apply (container);
		}
	}

}
