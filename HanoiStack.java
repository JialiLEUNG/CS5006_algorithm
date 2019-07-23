/**
 * The HanoiStack class  implement the iterative algorithm of hanoi(int ndisks, int src, int dest).
 *
 * The hanoi() function uses an array of three integer stacks to represent the poles,
 * and small integers between 1 and ndisks to represent the disks,
 * with the largest disk numbered 1 and the smallest as ndisks.
 *
 * moveDisk() is a local supporting function that
 * takes the pole array and the src and dest pole indexes.
 * The function determines the move direction by comparing the top disks on the indexed stacks,
 * then moves the disk and prints a message "Move disk from pole %d to pole %d\n" to standard out.
 *
 * The algorithm is complete when all ndisks disks have been moved to the dest pole.
 *
 * The main() function tests ndisks from 1 to 5, and can be tested to any ndisks.
 */

import java.util.HashMap;
import java.util.Stack;

public class HanoiStack {
    // declare ndisks (total number of disks)
    static int NDISKS;
    // declare the index for destination.
    static int DEST;
    // declare three poles (A,  B and C)
    static Stack<Integer> poleA;
    static Stack<Integer> poleB;
    static Stack<Integer> poleC;
    // delcare a hashmap called poles that takes a key-value pair of Integer-Stack.
    // key is the index for a pole, and value is the stack of disks in the pole.
    static HashMap<Integer, Stack<Integer>> poles;
    // declare a hashmap called polesName that takes a key-value pair of Integer-Stack.
    // Key is the user defined number for the pole, and value of the operational indexes for the pole (0, 1, 2).
    // This hashmap polesName helps to normalize any user input, instead of limiting the indexes from 0 to 2.
    static HashMap<Integer, Integer> polesName;

    public static void main(String[] args){
        hanoi(1, 0, 2);
        System.out.println("------------------------------");
        hanoi(2, 0, 2);
        System.out.println("------------------------------");
        hanoi(3, 0, 2);
        System.out.println("------------------------------");
        hanoi(4, 0, 2);
        System.out.println("------------------------------");
        hanoi(5, 0, 2);
    }

    /**
     * see doc design for description.
     * @param ndisks total number of disks in the game.
     * @param src index of the source pole.
     * @param dest index of the destination pole
     */

    public static void hanoi(int ndisks, int src, int dest){
        NDISKS = ndisks;
        DEST = dest;
        poleA = new Stack<>();
        poleB = new Stack<>();
        poleC = new Stack<>();
        poles = new HashMap<>();
        polesName = new HashMap<>();
        polesName.put(0, src);
        polesName.put(2, dest);
        polesName.put(1, 3 - src - dest);
        poles.put(0, poleA);
        poles.put(1, poleB);
        poles.put(2, poleC);

        // initialize the stack of ndisks in poleA.
        for (int i = ndisks; i >= 1; i--){
            poleA.push(i);
        }

        // if ndisk is 1, move disk from 0 to 2.
        if (ndisks == 1){
            moveDisk(0, 2);
            return;
        }

        // use an 2d array to specify the move actions depending on the number of the ndisk.

        // For an even number of disks:
        //make the legal move between pegs A and B (in either direction)
        //make the legal move between pegs A and C (in either direction)
        //make the legal move between pegs B and C (in either direction)

        //For an odd number of disks:
        //make the legal move between pegs A and C (in either direction)
        //make the legal move between pegs A and B (in either direction)
        //make the legal move between pegs B and C (in either direction)
        //make the legal move between pegs A and C (in either direction)
        //repeat steps 1-3 until complete
        int[][] pairOdd = {{0,1}, {0, 2}, {1,2}};
        int[][] pairEven = {{0,2}, {0,1}, {1,2}};
        var pickedPairs = (ndisks%2 == 0)? pairOdd : pairEven;

        // when the destination is not full, do the moveDisk action.
        while (poles.get(DEST).size() != NDISKS){
            for(var aVar:pickedPairs){
                moveDisk(aVar[0], aVar[1]);
            }
        }
    }

    /**
     * prints the moveDisk actions.
     * @param srcName the index for the source pole
     * @param destName the index for the destination pole
     */
    public static void printMove(int srcName, int destName){
        System.out.printf("Move disk from pole %d to pole %d\n", polesName.get(srcName), polesName.get(destName));
    }

    /**
     * check if the stack of a specified pole is empty.
     * @param poles hashmap of the three poles.
     * @param key which pole is being checked.
     * @return boolean. True if the stack of the specified pole is empty, and otherwise false.
     */
    public static Boolean valueIsEmpty(HashMap<Integer, Stack<Integer>> poles, Integer key) {
        Stack<Integer> value = poles.get(key);
        return value.isEmpty();
    }

    /**
     * Move a disk between src and dest (in either direction) [see doc design and main() for details].
     * @param src a pole that needs to move a disk
     * @param dest another pole that needs to move a disk
     */
    public static void moveDisk(int src, int dest){
        // if poleC (destination) consists of all the disks, no move action is required.
        if (poles.get(DEST).size()== NDISKS)
            return;
        // if both the destination and src poles are empty, no move action is required.
        if (valueIsEmpty(poles, src) && valueIsEmpty(poles, dest) ) {
            return;
        }
        // if the disk tack is empty in the src pole, move a destination disk over to the src pole.
        if (valueIsEmpty(poles, src)){
            poles.get(src).push(poles.get(dest).pop());
            printMove(dest, src);
            return;
        }
        // if the disk stack if empty in the destination, move a src disk over to the destination pole.
        if (valueIsEmpty(poles, dest)){
            poles.get(dest).push(poles.get(src).pop());
            printMove(src, dest);
            return;
        }
        // if the disk size of the src pole is smaller, move it over to the destination pole.
        if (poles.get(src).peek() < poles.get(dest).peek()){
            poles.get(dest).push(poles.get(src).pop());
            printMove(src, dest);
            return;
        }
        //if the disk size of the destination pole is smaller, move it over to the src pole.
        else{
            poles.get(src).push(poles.get(dest).pop());
            printMove(dest, src);
            return;
        }
    }
}


