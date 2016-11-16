
package edu.miracosta.cs113;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Change
{
	// width of quarter + (s) + 1
	private static final int TABLE_COLUMN_WIDTH = Stream.of (COIN.values ())
			.map (Object::toString)
			.mapToInt (String::length)
			.max ()
			.orElse (0)
			+ "(s)".length ()
			+ 1;

	public enum COIN
	{

		// least to greatest required
		// QUARTER(25), DIME(10), NICKEL(5), PENNY(1);
		PENNY(1), NICKEL(5), DIME(10), QUARTER(25);

		private int centWorth;

		COIN (int worth)
		{
			this.centWorth = worth;
		}

		public double getWorth ()
		{
			return centWorth / 100.0;
		}

		public int getCents ()
		{
			return centWorth;
		}
	}

	private Change ()
	{
	}

	public static void printChangeCombinations (int change)
	{
		System.out.printf ("The change combinations for %d cents:\n", change);
		for(COIN coin : COIN.values ())
		{
			String coinName = coin.toString ();
			coinName = coinName.substring (0, 1) + coinName.substring (1).toLowerCase ();
			System.out.printf ("%" + (TABLE_COLUMN_WIDTH - 3) + "s(s)", coinName);
		}
		System.out.println ();
		printChangeCombinationsRecursive (new int[COIN.values ().length], change, Arrays.asList (COIN.values ()));
	}

	private static void printChangeCombinationsRecursive (int[] count, int change, List<COIN> coins)
	{
		for (int i = 0; i < coins.size (); ++i)
		{
			COIN coin = coins.get (i);
			int reducedChange = change - coin.getCents ();
			if (reducedChange >= 0)
			{
				// i could copy the array instead of incrementing and then
				// decrementing
				// but nah and also in a single threaded environment that's a
				// lot of copies
				// all for what? half the number of writes (more like 1/4 in
				// retrospect)
				// so what? instead of 1 write (decrement) you get 4 writes and
				// allocation (the copy)
				// yeah no thanks (unless it's multi threaded (which it isn't))
				count[i]++;
				if (reducedChange > 0)
				{
					printChangeCombinationsRecursive (count, reducedChange, coins.subList (0, i + 1));
				}
				else if (reducedChange == 0)
				{
					printCoinCounts (count);
				}
				count[i]--;
			}
		}
	}

	private static void printCoinCounts (int[] count)
	{
		StringBuilder coinCounts = new StringBuilder ();
		COIN[] coins = COIN.values ();
		// spot check that the COINS enum is in ascending order
		assert coins[0] == COIN.PENNY;

		for (int i = 0; i < coins.length; ++i)
		{
			String coinName = coins[i].toString ();
			coinName = coinName.substring (0, 1) + coinName.substring (1).toLowerCase ();
			coinCounts.append (String.format ("%" + TABLE_COLUMN_WIDTH + "d", count[i], coinName));

		}
		System.out.println (coinCounts);
	}

}
