import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/******************************************************************************
 *  Name:    Jeyakumar Thuwarakan
 *  Uow ID:  W1790265
 *  IIT ID:  2019795
 *
 *  Description: this is a algorithm CW
 *
 *  Written:       01/04/2021
 *  Last updated:  07/04/2021
 *****************************************************************************/


public class Main {

    public static void main(String[] args) {
	    // creating a scanner variable for read files
        Scanner input = null;
        try {
            input = new Scanner(new File("ladder_2.txt"));
        }catch (FileNotFoundException e) {
            System.out.println(" This file is not exist ");
        }

        // get the node count
        int node = input.nextInt();

        Flow_Network flow = new Flow_Network(node);

        while (input.hasNextInt()) {
            int fromNode = input.nextInt(); // get the first node
            int toNode = input.nextInt(); // get the second node
            int capacity = input.nextInt(); // get the capacity

            // creating a edge in the flow network
            flow.addEdge(fromNode, toNode, capacity);
        }

        flow.printFlow();
        System.out.println(flow.toString());
        //blow code is use calculate the time
        /*************************************************************
        *long Start_time = System.nanoTime();
        *System.out.println(" Maximum flow is : " + flow.getMaxFlow());
        *long End_time = System.nanoTime();
        *double timeInMs = (End_time - Start_time)/1000000.0;
        *System.out.println(" Total time in ms :" + timeInMs);
        *****************************************************************/



    }
}
