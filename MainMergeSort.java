package project01;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author aristeidis
 * Noulis Aristeidis 2390 arisnoul@csd.auth.gr
 */
public class Project01 {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     * My MergeSort is a Top-down implementation so it is recursive, 
     * the complexity nlogn and the codeis mine as it the way of the implementantion.
     */
    public static void main(String[] args) throws FileNotFoundException {
        //Opening of the file so as to read it with the scanner
        String fileName = args[0];
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(fileName));
        } catch (FileNotFoundException fe) {
            System.out.println("File not found!");
        }

        //Initialization of the arraylist where i will save the numbers and
        //the variables for cost and the total number of numbers.
        ArrayList<Integer> numbersList = new ArrayList<>();
        long  cost = 0;
        int totalNumbers = 0;

        //Reading the file and save the numbers in the arraylist,
        //and increase the totalNumbers variable.
        while (scanner.hasNextInt()) {
            numbersList.add(scanner.nextInt());
            totalNumbers++;
        }

        //Initialization of the class where i implement the mergesort 
        //and i compute the cost and finally print it.
        MergeSort ms = new MergeSort(numbersList);
        numbersList = ms.getSortedArrayList();
        cost = ms.getCost();
        System.out.println("Total Cost: " + cost);
    }
}
