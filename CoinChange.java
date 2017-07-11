package project02;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

/**
 *
 * @author Noulis Aristeidis 2390 arisnoul@csd.auth.gr
 *  I implement the project based on the minimum coin change algorithm which is a subproblem of knapsack problem.
 *  In the first part of the project i just used the coin change algorithm to solve the problem.
 *  In the second part i didn't used the type given type but i make some changes in the original coin change algorithm so as
 *  to keep track the quantities and find the optimal solution in considering them.
 */
public class Main {

    /**
     *
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        
        //At this point i initialize some variables,ArrayLists that i will need to save the elements that i will read from the file.
        //Also here make the reading of the file.
        if (args.length == 0) {
            System.out.println("No Command Line arguments");
        } else {
            String filename;
            filename = args[0];
            Scanner scanner = new Scanner(new File(filename));
            int boxes = 0, i = 0, total = 0, number, x;
            ArrayList<Integer> volume = new ArrayList<>();
            ArrayList<Integer> quantity = new ArrayList<>();
            scanner.useLocale(Locale.ENGLISH);
            while (scanner.hasNext()) {
                switch (i) {
                    case 0:
                        total = scanner.nextInt();
                        i++;
                        break;
                    case 1:
                        number = scanner.nextInt();
                        i++;
                        break;
                    default:
                        x = scanner.nextInt();
                        x = scanner.nextInt();
                        volume.add(x);
                        x = scanner.nextInt();
                        quantity.add(x);
                        break;
                }
            }
            //Here i call these functions so as to print the elements and implement every part of the project.
            basicPrint(volume, quantity, total);
            operationA(total, volume);
            operationB(volume, quantity, total);

        }
    }

    /**
     *
     * @param total
     * @param volumes
     */// In this function i implement the first part of the project.It is a Bottom up way of solving this problem.
    public static void operationA(int total, ArrayList<Integer> volumes) {
        
         //Initialize an arraylist  to keep the minimum boxes needed for the volume and one for them that i will finally keep.
        ArrayList<Integer> Temp = new ArrayList<>();
        ArrayList<Integer> TakenBox = new ArrayList<>();
        
         // initialize the first value to 0 as i dont need any box if the volume  is zero.
        Temp.add(0, 0);
        
        //Initialize the TakenBox with -1 so as to know when i take an element and Temp  with the Integer.MAX_VALUE - 1
        //so as to make the comparisons.
        TakenBox.add(0, -1);
        for (int i = 1; i <= total; i++) {
            Temp.add(i, Integer.MAX_VALUE - 1);
            TakenBox.add(i, -1);
        }

        //Here i make the comparisons and i keep the right boxes so as to have the desired result.
        for (int j = 0; j < volumes.size(); j++) {
            for (int i = volumes.get(j); i <= total; i++) {
                    if (Temp.get(i - volumes.get(j)) + 1 < Temp.get(i)) {
                        Temp.set(i, 1 + Temp.get(i - volumes.get(j)));
                        TakenBox.set(i, j);
                }
            }
        }
        
        //If there is not result i print the right message and terminate the function.
        if(Temp.get(total)==Integer.MAX_VALUE - 1){
            System.out.println("There is no solution.");
             System.out.println("");
            return;
        }
        
        //Here i initialize an ArraList where i will save for every element how many times it appears in solution.
        ArrayList<Integer> countBox = new ArrayList<>();
        for (int i = 0; i < volumes.size(); i++) {
            countBox.add(i, 0);
        }
        
        //In this part of the code i write down the elements that apperas in final solution and how many times.
        int start = total;
        while (start != 0) {
            int j = TakenBox.get(start);
            countBox.set(j, countBox.get(j) + 1);
            start = start - volumes.get(j);
        }
        
        //At the end i print the solution.
        print(countBox, volumes, Temp.get(total), 1);
    }

    /**
     *
     * @param volumes
     * @param quantities
     * @param total
     *///At this funtcion i implement the second part of the project.
    public static void operationB(ArrayList<Integer> volumes, ArrayList<Integer> quantities, int total) {
        
       
        //Initialize an arraylist  to keep the minimum boxes needed for the volume and i save the amount of elements in a variable.
         ArrayList<Integer> Temp = new ArrayList<>();
        int n = volumes.size();

        // initialize the first value to 0 as i dont need any box if the volume  is zero.
        Temp.add(0);

        // Initialize an 2D array of total+1 rows and n+1 columns equal to number of
        // denominations. Where in keep track of number of boxes utilized to make
        // change for the respective value
        int[][] elementsTrack = new int[total + 1][n+ 1];

        // Initially for value 0 i don't need to use any boxes.
        for (int i = 0; i < n; i++) {
             elementsTrack[0][i] = quantities.get(i);
        }

        // Compute the minimum number of boxes required for each value from 1 to N
        for (int j = 1; j <= total; j++) {
            // Initialize the value to be infinity.
            Temp.add(j, Integer.MAX_VALUE - 1);

            for (int k = 0; k < n; k++) {
                // If the value is greater than the denomination value
                // AND if there is a value can be computed adding one volumes.get(k) denomination
                // AND if the there is volumes.get(k) available(which we get to know from the
                // track table)
                // THEN We shall compute the minimum number of boxes required
                // OR we just re-initialize the track table for the volumes.get(k) considered.
                if (j >= volumes.get(k)
                        && (Temp.get(j - volumes.get(k)) < Integer.MAX_VALUE - 1)
                        && (elementsTrack[j - volumes.get(k)][k] > 0)) {

                    if (Temp.get(j - volumes.get(k)) + 1 < Temp.get(j)) {
                        Temp.set(j, 1 + Temp.get(j - volumes.get(k)));
                        // Update the track table
                        update( elementsTrack,j - volumes.get(k),j,n,k);
                    }
                } // re-initialize the availability of the volume.get(k)  in the track table
                else if (j < volumes.get(k)) {
                     elementsTrack[j][k] =  elementsTrack[0][k];
                }
            }
        }
        //If there is not result i print the right message and terminate the function.
        if(Temp.get(total)==Integer.MAX_VALUE - 1){
            System.out.println("There is no solution.");
             System.out.println("");
            return;
        }
        //Create the last arraylist where i save the number of each box that used for the result.
        ArrayList<Integer> coinsUsed = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            coinsUsed.add(quantities.get(i) -  elementsTrack[ elementsTrack.length - 1][i]);
        }
        //Call the print function.
        print(coinsUsed, volumes, Temp.get(total), 2);
        System.out.println();
    }
    
    /**
     *
     * @param Temp
     * @param value
     * @param pos
     * @param limit
     * @param key
     *///A simple update function for a dimensional array so as to update its columns.
    public static void update(int [][] Temp,int value,int pos,int limit,int key){
         for (int i= 0; i < limit; i++) {
                            if (i == key) {
                                Temp[pos][i] =Temp[value][i] - 1;
                            } else {
                                Temp[pos][i] = Temp[value][i];
                            }
                        }
    }

    /**
     *
     * @param volumes
     * @param quantities
     * @param total
     *///Here i make the basic print of the elements of the file that i have read.
    public static void basicPrint(ArrayList<Integer> volumes, ArrayList<Integer> quantities, int total) {
        System.out.println("");
        System.out.println("The total container capacity is " + total + " and there are " + volumes.size() + " box types as follows:)");
        System.out.println("id           volume           quantity");
        System.out.println("============================================================================");
        for (int i = 1; i <= volumes.size(); i++) {
            System.out.println(i + "              " + volumes.get(i - 1) + "              " + quantities.get(i - 1));
        }
        System.out.println();
    }
    
    /**
     *
     * @param countBox
     * @param volumes
     * @param boxes
     * @param flag
     *///The main print function that i call in both of the parts of the project so as to print the reuslts.
    public static void print(ArrayList<Integer> countBox, ArrayList<Integer> volumes, int boxes, int flag) {
        if (flag == 1) {
            System.out.println("Unbounded case (i.e., ignoring quantities)");
        } else {
            System.out.println("Bounded case (i.e., considering quantities)");
        }
        System.out.println("================================");
        System.out.println("The minimum number of boxes is " + boxes);
        for (int i = 1; i <= volumes.size(); i++) {
            System.out.println("Object " + i + " is used  " + countBox.get(i - 1) + " times.");
        }
        System.out.println();
    }

}
