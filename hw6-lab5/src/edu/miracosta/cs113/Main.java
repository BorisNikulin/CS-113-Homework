
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
	public static <A, R> R generatePrintJobs (Collector<PrintJob, A, R> collector, int seed,
			long numPrintJobs)
	{
		Random rand = new Random (seed);

		A container = collector.supplier ().get ();
		for (long i = 0; i < numPrintJobs; ++i)
		{
			collector.accumulator ().accept (container, new PrintJob (rand.nextLong (), rand.nextInt (50) + 1));
		}
		if (collector.characteristics ().contains (Collector.Characteristics.IDENTITY_FINISH))
		{
			return (R) container;
		}
		else
		{
			return collector.finisher ().apply (container);
		}
	}

}
