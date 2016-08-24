
public class main
{

	public static void main(String[] args)
	{
		//FizzBuzz
		for(int i = 1; i <= 100; ++i)
		{
			if(i % 3 == 0 || i % 5 == 0)
			{
				String output = "";
				output += (i % 3 == 0) ? "Fizz" : "";
				output += (i % 5 == 0) ? "Buzz" : "";
				System.out.println(output);
			}
			else
			{
				System.out.println(i);
			}
		}
	}

}