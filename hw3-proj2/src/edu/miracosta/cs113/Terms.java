
package edu.miracosta.cs113;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.miracosta.cs113.dataStructures.SimpleArrayList;

/**
 * Helper class for {@link Term}
 * 
 * @author Boris
 *
 */
public class Terms
{
	private Terms ()
	{
	}

	/**
	 * Alias for {@code term1.add(term2)}
	 * 
	 * @param term1
	 * @param term2
	 * @return The terms added together as a Term or null if not able to be
	 *         combined.
	 * 
	 * @see Term#add(Term)
	 */
	public static Term add (Term term1, Term term2)
	{
		return term1.add (term2);
	}

	@SuppressWarnings("unchecked")
	public static List<Term> add (List<Term> terms1, List<Term> terms2)
	{
		// hmm I cant figure out how to get the two methods to play together nicely (generics pls -_-)
		return combineAdjecentTerms((List<Term>) merge (terms1, terms2, Term.COMPARE_BY_VAR_THEN_EXPONENT));
	}

	public static <T> List<? super T> merge (List<? extends T> list1, List<? extends T> list2,
			Comparator<? super T> comparator)
	{
		// TODO now since size is known a linked list is not as much of an
		// improvement as was the original add method however it's still
		// probably better to use one... once i make one :D
		// although the next step of add (joining locally adjacent like terms
		// will benefit a lot from a linked list)
		List<? super T> result = new ArrayList<> (list1.size () + list2.size ());
		ListIterator<? extends T> leftItr = list1.listIterator ();
		ListIterator<? extends T> rightItr = list2.listIterator ();
		while (leftItr.hasNext () || rightItr.hasNext ())
		{
			if (leftItr.hasNext () && rightItr.hasNext ())
			{
				T left = leftItr.next ();
				T right = rightItr.next ();
				int comparisonResult = comparator.compare (left, right);
				if (comparisonResult == 0)
				{
					result.add (left);
					result.add (right);
				}
				else if (comparisonResult > 0)
				{
					result.add (left);
					rightItr.previous ();
				}
				else
				{
					result.add (right);
					leftItr.previous ();
				}
			}
			else if (leftItr.hasNext ())
			{
				result.add (leftItr.next ());
			}
			else
			{
				result.add (rightItr.next ());

			}
		}
		return result;
	}

	public static List<Term> combineAdjecentTerms (List<Term> list)
	{
		List<Term> result = new ArrayList<> (); // TODO make and use linked list
		ListIterator<Term> itr = list.listIterator ();
		if (list.size () > 0)
		{
			result.add (itr.next ());
			while (itr.hasNext ())
			{
				Term nextTerm = itr.next ();
				Term addResult = result.get (result.size () - 1).add (nextTerm);
				if (addResult != null)
				{
					result.set (result.size () - 1, addResult);
				}
				else
				{
					result.add (nextTerm);
				}

			}
		}
		return result;
	}

//	@formatter:off
//	public static List<Term> add (List<Term> terms1, List<Term> terms2)
//	{
//		ListIterator<Term> leftItr = terms1.listIterator ();
//		ListIterator<Term> rightItr = terms2.listIterator ();
//		ListIterator<Term> resultItr = (new SimpleArrayList<Term> (
//				(int) ((terms1.size () + terms2.size ()) * 2.0 / 3.0)))
//						.listIterator ();
//		// TODO probably should use a list here since for terms it's more about
//		// iterating over and adding to an end (but I don't have a list yet
//		// soooooooooooooooooooooooo yeah)
//		// now that I made result a list iterator and that a list iterator .add
//		// adds right after its position, a linked list is really really
//		// necessary (them copies (shudder))
//		// also I accidentally turned this into a 3 way merge because (of the
//		// versatile term class)
//		// I discovered that multiple identical var + exponent in one of the
//		// lists does not aggregate properly
//		// [-23.00(x)^+2.00, +12.00(x)^+1.00, +0.50(x)^+0.00] +
//		// [-23.00(x)^+2.00, +12.00(x)^+2.00, +0.50(x)^+0.00]
//		// = [-46.00(x)^+2.00, +12.00(x)^+2.00, +12.00(x)^+1.00, +1.00(x)^+0.00]
//		while (leftItr.hasNext () || rightItr.hasNext ())
//		{
//			if (leftItr.hasNext () && rightItr.hasNext ())
//			{
//				Term left = leftItr.next ();
//				Term right = rightItr.next ();
//				int comparisonResult = Term.COMPARE_BY_VAR_THEN_EXPONENT.compare (left, right);
//				if (comparisonResult == 0)
//				{
//					Term addedTerm = left.add (right);
//					if (addedTerm)
//						resultItr.add (left.add (right));
//				}
//				else if (comparisonResult > 0)
//				{
//					resultItr.add (left);
//					rightItr.previous ();
//				}
//				else
//				{
//					resultItr.add (right);
//					leftItr.previous ();
//				}
//			}
//			else if (leftItr.hasNext ())
//			{
//				resultItr.add (leftItr.next ());
//			}
//			else
//			{
//				resultItr.add (rightItr.next ());
//			}
//		}
//		return resultItr;
//	}
//	@formatter:on

	public static List<Term> parse (String text)
	{
		return parse (text, true);
	}

	public static List<Term> parse (String text, boolean requireCarret)
	{
		// TODO probably should use a list here since for terms it's more about
		// iterating over and adding to an end (but I don't have a list yet
		// soooooooooooooooooooooooo yeah)
		List<Term> terms = new SimpleArrayList<> ();
		Pattern termPattern = Pattern
				.compile ("(?:\\(([-+]?\\.?\\d+)\\)|([-+]?\\.?\\d+))(?:\\(([a-zA-Z]+)\\)|([a-zA-Z]+))"
						+ (requireCarret ? "\\^" : "\\^?")
						+ "(?:\\(([-+]?\\.?\\d+)\\)|([-+]?\\.?\\d+))[^0-9-+(.,]*");
		// group 1 or 2 is coefficient (which ever is not null)
		// group 3 or 4 is variable (which ever is not null)
		// group 5 or 6 is exponent (which ever is not null)
		Matcher matcher = termPattern.matcher (text);

		while (matcher.find ())
		{
			double coefficient = Double.parseDouble (matcher.group (1) != null ? matcher.group (1) : matcher.group (2));
			String variable = matcher.group (3) != null ? matcher.group (3) : matcher.group (4);
			double exponent = Double.parseDouble (matcher.group (5) != null ? matcher.group (5) : matcher.group (6));
			terms.add (new Term (coefficient, variable, exponent));
		}
		return terms;
	}
}
