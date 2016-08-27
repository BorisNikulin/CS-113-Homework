
/**
 * RandomClue.java : Random solver for the Clue problem
 * 
 * @author Nery Chapeton-Lamas (material from Kevin Lewis) and Boris Nikulin
 * @version 2.0
 *
 */

import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;
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
		int answerSet, solution;
		// taken from http://stackoverflow.com/a/22829036
		// I could of used a for loop but streams allow much nicer ways to
		// generate stuff especially the IntStream's rangedClose (learned
		// something)
		// (j8 streams are cool)
		List<Integer> plausibleWeapons = IntStream.rangeClosed (1, TheoryItem.TOTAL_WEAPONS + 1).boxed ()
				.collect (Collectors.toList ());
		List<Integer> plausibleLocations = IntStream.rangeClosed (1, TheoryItem.TOTAL_LOCATIONS + 1).boxed ()
				.collect (Collectors.toList ());
		List<Integer> plausibleMurderers = IntStream.rangeClosed (1, TheoryItem.TOTAL_MURDERS + 1).boxed ()
				.collect (Collectors.toList ());
		Scanner keyboard = new Scanner (System.in);
		Theory answer = null;
		AssistantJack jack;

		System.out.print ("Which theory would like you like to test? (1, 2, 3[random]): ");
		answerSet = keyboard.nextInt ();
		keyboard.close ();

		jack = new AssistantJack (answerSet);

//		Random random = new Random ();

		do
		{
			// murder = random.nextInt (6) + 1;
			// location = random.nextInt (10) + 1;
			// weapon = random.nextInt (6) + 1;
			answer = guessTheory (plausibleWeapons, plausibleLocations, plausibleMurderers);
			solution = jack.checkAnswer (answer);
			
			if (solution == 1) // incorrect weapon
			{
				plausibleWeapons.remove (answer.getWeapon () - 1);
			}
			else if (solution == 2) // incorrect location
			{
				plausibleLocations.remove (answer.getLocation () - 1);
			}
			else if (solution == 3) // incorrect murderer
			{
				plausibleMurderers.remove (answer.getPerson () - 1);
			}
			
		} while (solution != 0);
		// answer = new Theory(weapon, location, murder);
		System.out.println ("Total Checks = " + jack.getTimesAsked () + ", Solution = " + answer);

		if (jack.getTimesAsked () > 20)
		{
			System.out.println ("FAILED!! You're a horrible Detective...");
		}
		else
		{
			System.out.println ("WOW! You might as well be called Batman!");
		}

	}

	public static Theory guessTheory (List<Integer> plausibleWeapons, List<Integer> plausibleLocations,
			List<Integer> plausibleMurderers)
	{
		int weapon, location, murderer;
		Random random = new Random ();
		weapon = plausibleWeapons.get (random.nextInt (plausibleWeapons.size ()));
		location = plausibleLocations.get (random.nextInt (plausibleLocations.size ()));
		murderer = plausibleMurderers.get (random.nextInt (plausibleMurderers.size ()));
		return new Theory (weapon, location, murderer);
	}

}