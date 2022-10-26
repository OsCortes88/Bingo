import java.util.*;

public class MyProgram extends ConsoleProgram
{
    static ArrayList<Integer> numsUsed = new ArrayList<Integer>();
    static ArrayList<Integer> compUsed = new ArrayList<Integer>();
    static ArrayList<Integer> used = new ArrayList<Integer>();
    static int ballot[][] = new int [5][5];
    static int compBallot[][] = new int [5][5];
    static int round = 1;
    static boolean game = true;
    
    public void run()
    {
        System.out.println("\t\t\tBINGO");
        System.out.println("\nInstructions: Welcome to the game of Bingo, you will be given\na ballot with unique numbers and random numbers will be presented\nthroughout the game."
        + " Your task is to indicate the location in which\nthe number is in your ballot and the value will change to a 0 in\nthe ballot. When a vetical, horizontal, or diagonal line is acheived\nyou win.");
        System.out.println("\nNOTE: You will be competing against a computer. Good Luck :)");
        
        declare(ballot, numsUsed);
        declare(compBallot, compUsed);
        
        while(game)
        {
            System.out.println("\nUser");
            display(ballot);
            System.out.println("\nComputer");
            display(compBallot);
            System.out.println();
            chosen();
            compCheck(used.get(used.size()-1));
            boolean valid = readBoolean("Is the number in your ballot (true or false): ");
            if(valid)
            {
                int row = readInt("\nEnter the row in which the number is in: ");
                int col = readInt("\nEnter the column in whcih the numeber is in: ");
                checkSlot(row, col, used.get(used.size()-1));
            }
            lookForBingo(round);
            round++;
        }
    }
    
    public void lookForBingo(int round)
    {
        if(round >= 5)
        {
            boolean bingo = readBoolean("Do you have a Bingo (true or false): ");
            userBingo(bingo);
            compBingo();
        }
    }
    
    public void userBingo(boolean bingo)
    {
        if(bingo)
        {
            System.out.println("\nUser:");
            display(ballot);
            String direction = readLine("\nIs the bingo vertical, horizontal, or diagonal? ");
            if(findBingo(direction, ballot))
            {
                System.out.println("\nUser:");
                display(ballot);
                System.out.println("Congradulations you WON!!!!!");
                game = false;
            }
            else
            {
                System.out.println("\nYOU CAN\'T FOOL ME. KEEP PLAYING YOU CHEATER");
            }
        }
    }
    
    public void compBingo()
    {
        if(findBingo("horizontal", compBallot) || findBingo("vertical", compBallot) || findBingo("diagonal", compBallot))
        {
            System.out.println("\nComputer:");
            display(compBallot);
            System.out.println("\nToo bad. You lost and the computer won. GOOD LUCK NEXT TIME!! :)");
            game = false;
        }
    }
    public void declare(int array[][], ArrayList a)
    {
        for(int row = 0; row < array.length; row++)
        {
            for(int col = 0; col < array[row].length; col++)
            {
                array[row][col] = value(a);
                a.add(array[row][col]);
            }
        }
    }
    
    public void display(int array[][])
    {
         for(int i = 0; i < array.length; i++)
         {
             System.out.println(Arrays.toString(array[i]));
         }
    }
    
    public int value(ArrayList a)
    {   
        int num = Randomizer.nextInt(1,99);
        if(a.contains(num))
        {
            return value(a);
        }
        else
        {
            return num;
        }
    }
    
    public void chosen()
    {
        int num = value(used);
        used.add(num);
        System.out.println("Chosen Number: " + num);
    }
    
    public void checkSlot(int row, int col, int random)
    {
        if(ballot[row-1][col-1] == random)
        {
            ballot[row-1][col-1] = 0;
        }
        else
        {
            System.out.println("\nYou can't fool me. That number isn't in your ballot.\n");
        }
    }
    
    public void compCheck(int random)
    {
        for(int row = 0; row < compBallot.length; row++)
        {
            for( int col = 0; col < compBallot[row].length; col++)
            {
                if(compBallot[row][col] == random)
                {
                    compBallot[row][col] = 0;
                }
            }
        }
    }
    
    public boolean findBingo(String direct, int arr[][])
    {
        boolean result = false;
        if(direct.equalsIgnoreCase("horizontal"))
        {
            for(int i = 0; i < arr.length; i++)
            {
                for(int j = 0; j < arr[i].length-1; j++)
                {
                    if(arr[i][j] == 0 && arr[i][j+1] == arr[i][j])
                    {
                        result = true;
                    }
                    else
                    {
                        result = false;
                        j = 99;
                    }
                }
                if(result)
                {
                    return result;
                }
            }
        }
        else if(direct.equalsIgnoreCase("vertical"))
        {
            for(int i = 0; i < arr.length; i++)
            {
                for(int j = 0; j < arr[i].length-1; j++)
                {
                    if(arr[j][i] == 0 && arr[j+1][i] == arr[j][i])
                    {
                        result = true;
                    }
                    else
                    {
                        result = false;
                        j = 99;
                    }
                }
                if(result)
                {
                    return result;
                }
            }
        }
        else
        {
            for(int i = 0; i < arr.length-1; i++)
            {
                if(arr[i][i] == 0 && arr[i][i] == arr[i+1][i+1])
                {
                    result = true;
                }
                else
                {
                    result = false;
                    i = 99;
                }
            }
            if(result)
            {
                return result;
            }
            else
            {
                for(int i = 4; i > 0; i--)
                {
                    if(arr[Math.abs(i-4)][i] == 0 && arr[Math.abs(i-4)][i] == arr[Math.abs(i-4)+1][i-1])
                    {
                        result = true;
                    }
                    else
                    {
                        result = false;
                        i = -99;
                    }
                }
                return result;
            }
        }
        return false;
    }
}