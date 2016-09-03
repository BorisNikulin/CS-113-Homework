import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FizBuzz
{

	public static void main (String[] args)
	{
		// fizzBuzz ();
		// System.out.println ();
		// System.out.println ("--------------------");
		// System.out.println ();
		// fizzBuzzThatAnybodyWouldDo ();

		// after deadline (but still within extended deadline)

		Supplier<Integer> ascendingByOneFromOne = new Supplier<Integer> ()
		{
			int num = 0;

			@Override
			public Integer get ()
			{
				++num;
				return num;
			}

		};
		Predicate<Integer> isStop = (testNum) -> testNum <= 100 && testNum > 0;
		Function<Integer, String> func = FizBuzz::fizzBuzzFunc;
		Consumer<String> output = System.out::println;

		getDoFuncAndOutputWhile (ascendingByOneFromOne, isStop, func, output);

	}

	/**
	 * Prints FizzBuzz from 1 to 100 inclusive to standard out
	 * 
	 * @see #fizzBuzz(int)
	 */
	public static void fizzBuzz ()
	{
		fizzBuzz (101, System.out::println);
	}

	/**
	 * Override of {@link #fizzBuzz(int, Consumer)} that defaults the consumer
	 * to {@code System.out::println}
	 * 
	 * @param end
	 * 
	 * @see #fizzBuzz(int, Consumer)
	 */
	public static void fizzBuzz (int end)
	{
		fizzBuzz (end, System.out::println);
	}

	/**
	 * Prints from 1 to end exclusive the current number, or Fizz if divisible
	 * by 3, or Buzz if divisible by 5, or FizzBuzz if divisible by both 3 and 5
	 * <p>
	 * Ex. 0 1 2 Fizz 4 Buzz Fizz 7 8 Fizz
	 * <p>
	 * 15 would be Fizzbuzz
	 * 
	 * @param end
	 *            exclusive end point
	 * @param fizzBuzzConsumer
	 *            Consumer that handles the resulting ouput (a string being
	 *            either a number, Fizz, Buzz, or FizzBuzz
	 */
	public static void fizzBuzz (int end, Consumer<String> fizzBuzzConsumer)
	{
		for (int i = 1; i < end; i++)
		{
			// Inspired by
			// http://twistedoakstudios.com/blog/Post5273_how-to-read-nested-ternary-operators
			// which I read a long time ago
			// then I re glanced at the top to finalize the style (after I had a
			// go at it from memory)
			// the site does have at the bottom have the FizzBuzz in nested
			// ternary but I did not look at that
			// (I did, but a very long time ago (mostly why I remembered it))

			//@formatter:off
			String out = i % 3 == 0 ? 
			        	  	  i % 5 == 0 ? "FizzBuzz"
			        	      : "Fizz"
			        	 : i % 5 == 0 ? "Buzz"
			        	 : Integer.toString (i);
			//@formatter:on

			fizzBuzzConsumer.accept (out);
		}
	}

	public static void fizzBuzzThatAnybodyWouldDo ()
	{
		for (int i = 1; i <= 100; ++i)
		{
			String out;

			if (i % 3 == 0)
			{
				out = "Fizz";
				if (i % 5 == 0)
				{
					out = "FizzBuzz";
				}
			}
			else if (i % 5 == 0)
			{
				out = "Buzz";
			}
			else
			{
				out = Integer.toString (i);
			}

			System.out.println (out);
		}
	}

	// after deadline (but still within extended deadline)
	public static String fizzBuzzFunc (int input)
	{
		//@formatter:off
		return input % 3 == 0 ? 
      	  	       input % 5 == 0 ? "FizzBuzz"
	        	   : "Fizz"
	           : input % 5 == 0 ? "Buzz"
	           : Integer.toString (input);
		//@formatter:on
	}

	/**
	 * Because why not
	 * 
	 * P.S If i'm going to fizzBuzz, you will too :P
	 * 
	 * P.P.S Also I just simply wanted to play with the functional package and
	 * j8 functional programming additions
	 * 
	 * @param supplier
	 * @param isStop
	 * @param func
	 * @param output
	 */
	public static <T, R> void getDoFuncAndOutputWhile (Supplier<T> supplier, Predicate<? super T> isStop,
			Function<? super T, R> func, Consumer<? super R> output)
	{
		T i = supplier.get ();
		while (isStop.test (i))
		{
			i = supplier.get ();
			output.accept (func.apply (i));
		}
	}
}
