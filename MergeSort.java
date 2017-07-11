package project01;

import java.util.ArrayList;

/**
 *
 * @author aristeidis Noulis Aristeidis 2390 arisnoul@csd.auth.gr
 * This is where i implement the mergesort and i compute the
 * final cost.
 */
public final class MergeSort {

    //Initialization of the local variable for the total cost,the number of
    //the invarsions and the ArrayList.
    private long totalCost = 0;
    private int numInversions = 0,pos=0;
    private final ArrayList<Integer> myArrayList;

    /**
     *
     * @param inputArrayList This is where i make copy my arrayList from the
     * main class so as to use it more efficient in the mergesort class and here
     * i start the mergesort by calling the division function.
     */
    public MergeSort(ArrayList<Integer> inputArrayList) {
        myArrayList = inputArrayList;
        divideArrayList(0, myArrayList.size() - 1);
    }

    /**
     *
     * @return I use this method so as to return the sorted arrayList as it is
     * private.
     */
    public ArrayList<Integer> getSortedArrayList() {
        return myArrayList;
    }

    /**
     *
     * @return I return the cost of the sorted Arraylist.
     */
    public long getCost() {
        return totalCost;
    }

    /**
     *
     * @param mAL
     * @param left
     * @param right
     * @param size In this function i increase the total cost as demanded in
     * each situation,by examining in every mergesort's inversion.I search for all the inversions of cost 2
     * and the rest i just multiply with 3.
     */
    public void countTotalCost(ArrayList<Integer> mAL, int left, int right, int size) {
        while(pos<=size){
             if (mAL.get(left) == mAL.get(right) + 1) {
                totalCost += 2;
            } else {
                totalCost += 3*(size-pos);
                pos=0;
                break;
            }
             pos++;
             left++;
        }
        
    }

    /**
     *
     * @param startIndex
     * @param endIndex Here i make the divisions and i call the mergeProcedure
     * so as to do the merging.I do it in a recursion way.
     */
    public void divideArrayList(int startIndex, int endIndex) {

        //I divide the arraylist with the numbers until i reach a single element.
        if (startIndex < endIndex && (endIndex - startIndex) >= 1) {
            int middle = startIndex + (endIndex - startIndex) / 2;
            divideArrayList(startIndex, middle);
            divideArrayList(middle + 1, endIndex);

            //I call the function which is responsible for merging.
            mergeProcedure(startIndex, middle, endIndex);
        }
    }

    /**
     *
     * @param startIndex
     * @param midIndex
     * @param endIndex Here i implement the main merge procedure me examining 
     * the elements of the sub-arrays in each situation,compute cost of 
     * the inversions when i have one by calling the countTotalCost function and
     * finally sort the elements between my left and right index limits..
     */
    public void mergeProcedure(int startIndex, int midIndex, int endIndex) {

        //This is an arraylistas that i use as a temporary where i make 
        //the changesa nad cave the sorted arrayList from the initial ArrayList.
        ArrayList<Integer> tempSortedArrayList = new ArrayList<>();

        //Here i set the limits.
        int leftIndex = startIndex;
        int rightIndex = midIndex + 1;

        //Here i make the comparisons,count the total cost 
        //and save the elements to temporary sorted Arraylist.
        while (leftIndex <= midIndex && rightIndex <= endIndex) {
            if (myArrayList.get(leftIndex) <= myArrayList.get(rightIndex)) {
                tempSortedArrayList.add(myArrayList.get(leftIndex));
                leftIndex++;
            } else {
                tempSortedArrayList.add(myArrayList.get(rightIndex));
                numInversions = (midIndex + 1 - leftIndex);
                countTotalCost(myArrayList, leftIndex, rightIndex, numInversions);
                rightIndex++;
            }

        }

        //I call the function fillRestOfArrayList so as when the leftIndex 
        //or the rightIndex reach their limits to fill the rest arrayList,
        //with the remaining numbers.
        if (leftIndex <= midIndex) {
            fillRestOfArrayList(tempSortedArrayList, leftIndex, midIndex);
        } else {
            fillRestOfArrayList(tempSortedArrayList, rightIndex, endIndex);
        }

        //Finally i copy the sorted elements to the initial ArrayList.
        finalArrayListCreation(tempSortedArrayList, startIndex);

    }

    /**
     *
     * @param tempArrayList
     * @param leftBorder
     * @param rightBorder Here i fill the rest temporary sorted arraylist with
     * the remaing elements
     */
    public void fillRestOfArrayList(ArrayList<Integer> tempArrayList, int leftBorder, int rightBorder) {
        while (leftBorder <= rightBorder) {
            tempArrayList.add(myArrayList.get(leftBorder));
            leftBorder++;
        }
    }

    /**
     *
     * @param tempArrayList
     * @param start Here i copy the sorted elements to the main - initial
     * arraylist.
     */
    public void finalArrayListCreation(ArrayList<Integer> tempArrayList, int start) {
        int i = 0;
        //Setting sorted array to original one
        while (i < tempArrayList.size()) {
            myArrayList.set(start, tempArrayList.get(i++));
            start++;
        }
    }
}
