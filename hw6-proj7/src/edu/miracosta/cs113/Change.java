
package edu.miracosta.cs113;

public class Change
{
	
	public enum COIN
	{
		QUARTER(0.25), DIME(0.10), NICKEL(0.05), PENNY(0.01);
		
		private double worth;

		COIN(double worth)
		{
			this.worth = worth;
		}
		
		public double getWorth()
		{
			return worth;
		}
		
		public int getCents()
		{
			return (int) (worth * 100);
		}
	}
	
	private Change () {}
	
	
	public static void printChangeCombinations(double change)
	{
		printChangeCombinationsRecursive(new int[COIN.values ().length], change);
	}
	
	private static void printChangeCombinationsRecursive(int[] count, double change)
	{
		COIN[] coins = COIN.values ();
		for(int i = 0; i < coins.length; ++i)
		{
			COIN coin = coins[i];
			if((change - coin.getWorth ()) > 0)
			{
				count[i]++;
				printChangeCombinationsRecursive(count, change);
			}
			else
			{
				printCoinCounts(count);
			}
		}
	}
	
	private static void printCoinCounts(int[] count)
	{
		System.out.println (count);
	}

}
