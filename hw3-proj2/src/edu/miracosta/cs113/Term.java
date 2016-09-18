
package edu.miracosta.cs113;

import java.util.Comparator;

/**
 * Class that represents a polynomial term like 2x<sup>2</sup> or
 * -21y<sup>34.5</sup>
 * 
 * <p>
 * Is immutable
 * </p>
 * 
 * <p>
 * Compares using {@link #COMPARE_BY_VAR_THEN_EXPONENT}
 * </p>
 * 
 * @author Boris
 */
public class Term implements Comparable<Term>
{
	/**
	 * Compares by variable then exponent.
	 * <p>
	 * Suitable for checking whether two terms can be combined as this
	 * comparator returns 0 in that case and not 0 in others.
	 * </p>
	 * <p>
	 * Returns natural String order. If String order is 0 then returns
	 * natural double order of exponent.
	 * </p>
	 */
	public static final Comparator<Term>	COMPARE_BY_VAR_THEN_EXPONENT;

	/**
	 * Like {@link #COMPARE_BY_VAR_THEN_EXPONENT} but checks all fields starting
	 * with the variable, using natural String ordering, to exponent and then
	 * coefficient, both using natural double ordering.
	 */
	public static final Comparator<Term>	COMPARE_BY_VAR_EXPONENT_THEN_COEFFICIENT;

	private double							coefficient;
	private String							variable;
	private double							exponent;

	static
	{
		COMPARE_BY_VAR_THEN_EXPONENT = Comparator.comparing (Term::getVariable)
				.thenComparingDouble (Term::getExponent);

		COMPARE_BY_VAR_EXPONENT_THEN_COEFFICIENT = Comparator.comparing (Term::getVariable)
				.thenComparingDouble (Term::getExponent)
				.thenComparingDouble (Term::getCoefficient);
	}

	/**
	 * Shorthand constructor for an x term like 2x<sup>2</sup> or
	 * -21x<sup>34.5</sup>
	 * 
	 * @param coefficient
	 * @param exponent
	 * @see #Term(boolean, long, String, long)
	 */
	public Term (double coefficient, double exponent)
	{
		this (coefficient, "x", exponent);
	}

	/**
	 * Full constructor for things like 2x<sup>2</sup> or -21y<sup>34.5</sup>
	 * 
	 * @param isPositive
	 *            sign on the coefficient
	 * @param coefficient
	 * @param variable
	 *            like x or y
	 * @param exponent
	 */
	public Term (double coefficient, String variable, double exponent)
	{
		this.coefficient = coefficient;
		this.variable = variable;
		this.exponent = exponent;
	}

	/**
	 * Copy constructor
	 * 
	 * @param other
	 */
	public Term (Term other)
	{
		this (other.coefficient, other.variable, other.exponent);
	}

	@Override
	public int compareTo (Term other)
	{
		return COMPARE_BY_VAR_THEN_EXPONENT.compare (this, other);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode ()
	{
		// this is like the String hashCode java does?
		// neat I haven't used eclipse auto generated hashCode and equals before
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits (coefficient);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits (exponent);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((variable == null) ? 0 : variable.hashCode ());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public final boolean equals (Object obj)
	{
		if (obj == null)
		{
			return false;
		}
		// the instanceof vs .getClass discussion is interesting
		// http://stackoverflow.com/questions/596462/any-reason-to-prefer-getclass-over-instanceof-when-generating-equals
		if (!(obj instanceof Term))
		{
			return false;
		}
		Term other = (Term) obj;
		// also neat that the generated equals with doubles uses
		// .doubleToLongBits
		// http://stackoverflow.com/questions/23438530/meaning-of-double-doubletolongbitsx
		// however why not the raw version as NaNs are converted to a single
		// "canonical" NaN
		// is there a point? and what other version of NaN are there?
		if (Double.doubleToRawLongBits (coefficient) != Double.doubleToLongBits (other.coefficient))
		{
			return false;
		}
		if (Double.doubleToLongBits (exponent) != Double.doubleToLongBits (other.exponent))
		{
			return false;
		}
		if (variable == null)
		{
			if (other.variable != null)
			{
				return false;
			}
		}
		else if (!variable.equals (other.variable))
		{
			return false;
		}
		return true;
	}

	/**
	 * Adds two polynomial terms together if it can.
	 * 
	 * @param term1
	 * @param term2
	 * @return The terms added together as a {@link Term} or null if not able to be combined.
	 */
	public static Term add (Term term1, Term term2)
	{
		if (COMPARE_BY_VAR_THEN_EXPONENT.compare (term1, term2) != 0)
		{
			return null;
		}
		return new Term (term1.coefficient + term2.coefficient, term1.variable, term1.exponent);
	}

	/**
	 * @return the coefficient
	 */
	public double getCoefficient ()
	{
		return coefficient;
	}

	/**
	 * @return the variable like x or y
	 */
	public String getVariable ()
	{
		return variable;
	}

	/**
	 * @return the exponent
	 */
	public double getExponent ()
	{
		return exponent;
	}
}
