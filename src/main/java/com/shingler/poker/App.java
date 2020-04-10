package com.shingler.poker;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println("Type exit to end program.");
        System.out.println("Please enter black and whites poker hand.");
        System.out.println("Example: Black: 2H 3D 5S 9C KD  White: 2C 3H 4S 8C AH");
        Scanner scanner = new Scanner(System.in);
        String userIn = "";
        do {
            userIn = scanner.nextLine();
            if (!userIn.equals("exit")){
                System.out.println(HandEvaluator.evaluate(userIn));
            }
        } while(!userIn.equals("exit"));
        scanner.close();
        return;
    }
}
