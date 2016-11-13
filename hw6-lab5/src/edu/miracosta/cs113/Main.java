
package edu.miracosta.cs113;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Main
{

	public static final int	SEED				= 46525;
	public static final int	PRINT_JOB_LENGTH	= 4;

	public static void main (String[] args)
	{
		Duration printDuration = Duration.ZERO;

		// synthetic way to receive print jobs
		// to get insertion order a wrapper on the queue
		// or an extra layer of code when a print job is received can be done
		// id like to try using ReactFX or RxJava for that react paradigm
		// stream/subscriber thing ...
		// but ill just do generic uninspired code
		PriorityPrintQueue ppq = generatePrintJobs (PriorityPrintQueue.collector (), SEED, PRINT_JOB_LENGTH);
		List<PrintJob> printJobInsertionOrder = generatePrintJobs (Collectors.toList (), SEED, PRINT_JOB_LENGTH);
		// 10 pages per minute
		Printer printer = new Printer (Duration.ofMinutes (1).dividedBy (10));
		List<Printer> printers = Arrays.asList (printer, printer, printer);

		// synthetic add since print jobs were added synthetically
		// 1 minute wait between each print job request
		printDuration = printDuration.plusMinutes (PRINT_JOB_LENGTH);
		
		System.out.println (ppq.toString ().replaceAll (",", ",\n"));
		System.out.println ("\n----------------------\n");
		System.out.println (printJobInsertionOrder.toString ().replaceAll (",", ",\n"));
		
		printDuration = printDuration.plus (printDuration(ppq, printers.subList (0, 3)));
		
		System.out.println ("\n");
		System.out.println (printDuration);
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

	/**
	 * A fucntion that caluculates the duration of time to print a sequence of
	 * {@link PrintJob} s using the per page print duration of the given
	 * sequence of {@link Printer} s.
	 * 
	 * @param printJobs
	 *            - the PrintJobs to fake print
	 * @param printers
	 *            - the printers doing the fake printing
	 * @param numberOfPrinters
	 *            - best guess at the provided number of printers
	 * @return the duration of time to print the provided print jobs using the
	 *         provided printers or null if there are no printers
	 * 
	 * @see #printDuration(Iterable, Collection)
	 */
	public static Duration printDuration (Iterable<PrintJob> printJobs, Iterable<Printer> printers,
			int numberOfPrinters)
	{
		class PrinterAndPrintDurationWrapper
		{
			public Printer	printer;
			public Duration	totalPrintDuration;

			public PrinterAndPrintDurationWrapper (Printer printer, Duration totalPrintDuration)
			{
				this.printer = printer;
				this.totalPrintDuration = totalPrintDuration;
			}
			
			public String toString()
			{
				return printer.toString () + "; " + totalPrintDuration.toString (); 
			}
		}

		if (!printers.iterator ().hasNext () || numberOfPrinters <= 0)
		{
			return null;
		}

		PriorityQueue<PrinterAndPrintDurationWrapper> printDurations = new PriorityQueue<> (
				numberOfPrinters,
				Comparator.comparing (wrapper -> wrapper.totalPrintDuration));

		for (Printer printer : printers)
		{
			printDurations.offer (new PrinterAndPrintDurationWrapper (printer, Duration.ZERO));
		}

		for (PrintJob pj : printJobs)
		{
			PrinterAndPrintDurationWrapper firstAvailblePrinter = printDurations.poll ();
			firstAvailblePrinter.totalPrintDuration = firstAvailblePrinter.totalPrintDuration.plus (
					firstAvailblePrinter.printer.getPrintTimePerPage ().multipliedBy (pj.getPageCount ()));
			printDurations.offer (firstAvailblePrinter);
		}

		return printDurations.stream ()
				.map (wrapper -> wrapper.totalPrintDuration)
				.max (Comparator.naturalOrder ())
				.orElse (Duration.ZERO);
	}

	/**
	 * A wrapper for {@link #printDuration(Iterable, Iterable, int)} where the
	 * {@code numberOfPrinters} is the size of the printers collection.
	 * 
	 * @param printJobs
	 *            - the PrintJobs to fake print
	 * @param printers
	 *            - the printers doing the fake printing
	 * @return the duration of time to print the provided print jobs using the
	 *         provided printers or null if there are no printers
	 * 
	 * @see #printDuration(Iterable, Iterable, int)
	 */
	public static Duration printDuration (Iterable<PrintJob> printJobs, Collection<Printer> printers)
	{
		return printDuration (printJobs, printers, printers.size ());
	}

}
