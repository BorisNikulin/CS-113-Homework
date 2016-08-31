
/**
 * RandomClue.java : Random solver for the Clue problem
 * 
 * @author Nery Chapeton-Lamas (material from Kevin Lewis) and Boris Nikulin
 * @version 2.0
 *
 */

import java.util.Iterator;
import java.util.Scanner;
import java.util.stream.IntStream;

public class RandomClue
{

	/**
	 * Tester for random assistant theory checker
	 * 
	 * @param args
	 *            command line arguments (unnused)
	 */
	public static void main (String[] args)
	{
		int answerSet, answer, weapon, location, murderer;
		Theory solution;

		// taken from http://stackoverflow.com/a/22829036
		// I could of used a for loop but streams allow much nicer ways to
		// generate stuff especially the IntStream's rangedClose (learned
		// something)
		// (j8 streams are cool)
		// now changed to an iterator so not as plagarizy
		Iterator<Integer> plausibleWeapons = IntStream.rangeClosed (1, TheoryItem.TOTAL_WEAPONS).iterator ();
		Iterator<Integer> plausibleLocations = IntStream.rangeClosed (1, TheoryItem.TOTAL_LOCATIONS).iterator ();
		Iterator<Integer> plausibleMurderers = IntStream.rangeClosed (1, TheoryItem.TOTAL_MURDERS).iterator ();
		Scanner keyboard = new Scanner (System.in);	
		AssistantJack jack;

		weapon = plausibleWeapons.next ();
		location = plausibleLocations.next ();
		murderer = plausibleMurderers.next ();
		
		System.out.print ("Which theory would like you like to test? (1, 2, 3[random]): ");
		answerSet = keyboard.nextInt ();
		keyboard.close ();

		jack = new AssistantJack (answerSet);

		do
		{
			answer = jack.checkAnswer (weapon, location, murderer);

			if (answer == 1) // incorrect weapon
			{
				weapon = plausibleWeapons.next ();
			}
			else if (answer == 2) // incorrect location
			{
				location = plausibleLocations.next ();
			}
			else if (answer == 3) // incorrect murderer
			{
				murderer = plausibleMurderers.next ();
			}

		} while (answer != 0);
		
		solution = new Theory (weapon, location, murderer);

		System.out.println ("Total Checks = " + jack.getTimesAsked () + ", Solution = " + solution);

		if (jack.getTimesAsked () > 20)
		{
			System.out.println ("FAILED!! You're a horrible Detective...");
		}
		else
		{
			System.out.println ("WOW! You might as well be called Batman!");
		}

	}

}