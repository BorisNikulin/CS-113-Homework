
package edu.miracosta.cs113;

import java.util.Arrays;
import java.util.List;

public class Change
{

	public enum COIN
	{
		
		//least to greatest required
//		QUARTER(25), DIME(10), NICKEL(5), PENNY(1);
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
		printChangeCombinationsRecursive (new int[COIN.values ().length], change, Arrays.asList (COIN.values ()));
	}

	private static void printChangeCombinationsRecursive (int[] count, int change, List<COIN> coins)
	{
		for (int i = 0; i < coins.size (); ++i)
		{
			COIN coin = coins.get (i);
			int reducedChange = change - coin.getCents ();
			if (reducedChange > 0)
			{
				count[i]++;
				printChangeCombinationsRecursive (count, reducedChange, coins.subList (0, i + 1));
				count[i]--;
			}
			else if (reducedChange == 0)
			{
				count[i]++;
				printCoinCounts (count);
				count[i]--;
			}
		}
	}

	private static void printCoinCounts (int[] count)
	{
		System.out.println (Arrays.toString (count));
	}

}
