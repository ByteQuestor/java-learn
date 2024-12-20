package school;
import java.util.Scanner;
public class Utility 
{
    public static String getInput(String prompt) 
    {
        try (Scanner scanner = new Scanner(System.in)) 
        {
            System.out.print(prompt);
            return scanner.nextLine();
        }
    }
}