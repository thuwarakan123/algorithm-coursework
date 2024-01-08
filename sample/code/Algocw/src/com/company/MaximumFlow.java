package com.company;// Java program for implementation of Ford Fulkerson algorithm
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.lang.*;
import java.util.LinkedList;

class MaximumFlow {
    private static final Hashtable<String, Long> tasks = new Hashtable<String, Long>();

    //Start the Watch for a Task with Id
    public static void startTime(String taskId) {
        tasks.put(taskId, new Long(System.currentTimeMillis()));
    }

    //Stop the watch
    public static long stopTime(String taskId) {
        return System.currentTimeMillis()
                - ((Long) tasks.remove(taskId)).longValue();
    }

    // Reading the matrix from the file and adding them into a matrix
    int[][] createMatrix(int nodes, Scanner value) throws FileNotFoundException {

        int i, j, a, b;

        int graph[][] = new int[nodes][nodes];

        //Inserting the values into the graph
        while (value.hasNext()) {
            int x = value.nextInt();
            int y = value.nextInt();
            int z = value.nextInt();
            graph[x][y] = z;

        }

        //Printing out the graph
        System.out.println("Input Graph : ");
        System.out.print("\t|\t ");
        for (a = 0; a < nodes; a++) {
            System.out.print(a + "\t");
        }
        System.out.print("\n");
        for (b = 0; b <= nodes; b++) {
            System.out.print("-\t");
        }
        System.out.print("\n");
        for (i = 0; i < nodes; i++) {
            System.out.print(i + "\t|\t ");
            for (j = 0; j < nodes; j++)
                System.out.print(graph[i][j] + "\t");
            System.out.println();
        }

        //Return the graph
        return graph;
    }

    // Updating the matrix after deleting the selected node
    int[][] deleteNode(int n, int graph[][]) {
        int i, j;
        for (i = 0; i < graph.length; i++) {
            if (i == n) {
                for (j = 0; j < graph.length; j++) {
                    graph[n][j] = 0;
                    graph[j][n] = 0;
                }
            }
        }
        System.out.println("New Input Graph : ");
        System.out.print("\t|\t ");
        for (int a = 0; a < graph.length; a++) {
            System.out.print(a + "\t");
        }
        System.out.print("\n");
        for (int b = 0; b <= graph.length; b++) {
            System.out.print("-\t");
        }
        System.out.print("\n");
        for (i = 0; i < graph.length; i++) {
            System.out.print(i + "\t|\t ");
            for (j = 0; j < graph.length; j++)
                System.out.print(graph[i][j] + "\t");
            System.out.println();
        }
        //Return the new updated graph
        return graph;
    }


    /* Returns true if there is a path from source 's' to sink
    't' in residual graph. Also fills parent[] to store the
    path */
    boolean bfs(int nodes, int rGraph[][], int s, int t, int parent[]) {
        int V = nodes;
        // Create a visited array and mark all vertices as not visited
        boolean visited[] = new boolean[V];
        for (int i = 0; i < V; ++i) {
            visited[i] = false;
        }


        // Create a queue, enqueue source vertex and mark source vertex as visited
        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(s);
        visited[s] = true;
        parent[s] = -1;

        // Standard BFS Loop
        while (queue.size() != 0) {
            int u = queue.poll(); //removes the top value in the list

            for (int v = 0; v < V; v++) {
                if (visited[v] == false && rGraph[u][v] > 0) {
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }

        // If we reached sink in BFS starting from source, then return true, else false
        return (visited[t] == true);
    }

    // Returns tne maximum flow from s to t in the given graph
    int fordFulkerson(int nodes, int graph[][], int s, int t) {
//        MaximumFlow.startTime("task");
        int V = nodes;
        int u, v;

        // Create a residual graph and fill the residual graph with given capacities in the original graph as residual capacities in residual graph
        // Residual graph where rGraph[i][j] indicates residual capacity of edge from i to j (if there is an edge. If rGraph[i][j] is 0, then there is not)

        int rGraph[][] = new int[V][V];

        for (u = 0; u < V; u++)
            for (v = 0; v < V; v++)
                rGraph[u][v] = graph[u][v];

        // This array is filled by BFS and to store path
        int parent[] = new int[V];

        int max_flow = 0; // There is no flow initially

        // Augment the flow while there is path from source to sink
        while (bfs(nodes, rGraph, s, t, parent)) {
            // Find minimum residual capacity of the edges along the path filled by BFS. Or we can say find the maximum flow through the path found.
            int path_flow = Integer.MAX_VALUE;
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                path_flow = Math.min(path_flow, rGraph[u][v]);
            }

            // update residual capacities of the edges and reverse edges along the path
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                rGraph[u][v] -= path_flow;
                rGraph[v][u] += path_flow;
            }

            // Add path flow to overall flow
            max_flow += path_flow;
        }

//        long time = MaximumFlow.stopTime("task");

        //Printing the residual graph
        System.out.println("");
        System.out.println("Residual Graph : ");
        System.out.print("\t|\t");
        for (int a = 0; a < nodes; a++) {
            System.out.print(a + "\t");
        }
        System.out.print("\n");
        for (int b = 0; b <= nodes; b++) {
            System.out.print("-\t");
        }
        System.out.print("\n");
        for (int i = 0; i < nodes; i++) {
            System.out.print(i + "\t|\t");
            for (int j = 0; j < nodes; j++)
                System.out.print(rGraph[i][j] + "\t");
            System.out.println();
        }
        System.out.println("");

//        System.out.println("Time elapsed : " + time + "ms");
        // Return the overall flow
        return max_flow;

    }


    public static void main(String[] args) throws java.lang.Exception {
        int option;
        do {
            Scanner input = new Scanner(System.in);
            System.out.println("1 - Bridge");
            System.out.println("2 - Ladder");
            System.out.print("Enter the Number : ");
            while (!input.hasNextInt()) {
                System.out.println("\n" + "----Your Input is Wrong----- ");
                System.out.print("\n" + "Please Enter the Number Again :");
                input.next();
            }
            option = input.nextInt();

            switch (option) {
                case 1:
                    System.out.print("Enter File Number : ");
                    Scanner input1 = new Scanner(System.in);
                    int fileNum = input1.nextInt();

                    MaximumFlow m = new MaximumFlow();
                    Scanner value = new Scanner(new File("testNodes/bridge_" + fileNum + ".txt"));
                    int nodes = value.nextInt();
                    System.out.println("Number of Nodes : " + nodes);
                    int[][] graph = m.createMatrix(nodes, value);
                    System.out.println("");
                    System.out.print("Enter Source node : ");
                    Scanner input2 = new Scanner(System.in);
                    int source  = input2.nextInt();

                    System.out.print("Enter Sink node : ");
                    Scanner input3 = new Scanner(System.in);
                    int sink  = input3.nextInt();

                    System.out.println("The maximum possible flow is " + m.fordFulkerson(nodes,graph, source, sink));

                    System.out.println("");

                    while (true) {
                        Scanner input4 = new Scanner(System.in);
                        System.out.print("1. Delete Node. \n2. Exit Program. \nChoose Option to execute : ");
                        try {
                            int opt = input4.nextInt();

                            if (opt == 1) {
                                Scanner input5 = new Scanner(System.in);
                                System.out.print("Enter which node you want to delete: ");
                                try{
                                    int delNode = input5.nextInt();
                                    if(delNode < nodes){int[][] newGraph = m.deleteNode(delNode, graph);
                                        System.out.println("The maximum possible flow is " + m.fordFulkerson(nodes, newGraph, source, sink));
                                    }
                                    else{
                                        System.out.println("INCORRECT Value Entered !!!");
                                    }

                                }catch(Exception e) {
                                    System.out.println("Something went WRONG !!!");
                                }

                            }
                            else if (opt == 2) {
                                System.out.println("---------- PROGRAM HAS ENDED ----------");
                                break;
                            } else {
                                System.out.println("INCORRECT Value Entered !!!");
                            }

                        } catch (Exception e) {
                            System.out.println("Something went WRONG !!!");
                        }
                        System.out.println("");
                    }

                    break;
                case 2:
                    System.out.print("Enter File Number : ");
                    Scanner lader = new Scanner(System.in);
                    int fileNum1 = lader.nextInt();

                    MaximumFlow mx = new MaximumFlow();
                    Scanner value1 = new Scanner(new File("testNodes/ladder_" + fileNum1 + ".txt"));
                    int nodes1 = value1.nextInt();
                    System.out.println("Number of Nodes : " + nodes1);
                    int[][] graph1 = mx.createMatrix(nodes1, value1);
                    System.out.println("");
                    break;
            }
        }while (option >=1 && option <= 2);

//        int[][] graph = m.createMatrix(nodes, value);


//        System.out.print("Enter Source node : ");
//        Scanner input2 = new Scanner(System.in);
//        int source  = input2.nextInt();
//
//        System.out.print("Enter Sink node : ");
//        Scanner input3 = new Scanner(System.in);
//        int sink  = input3.nextInt();
//
//        System.out.println("The maximum possible flow is " + m.fordFulkerson(nodes,graph, source, sink));
//
//        System.out.println("");
//
//        while (true) {
//            Scanner input4 = new Scanner(System.in);
//            System.out.print("1. Delete Node. \n2. Exit Program. \nChoose Option to execute : ");
//            try {
//                int option = input4.nextInt();
//                if (option == 1) {
//                    Scanner input5 = new Scanner(System.in);
//                    System.out.print("Enter which node you want to delete: ");
//                    try{
//                        int delNode = input5.nextInt();
//                        if(delNode < nodes){int[][] newGraph = m.deleteNode(delNode, graph);
//                            System.out.println("The maximum possible flow is " + m.fordFulkerson(nodes, newGraph, source, sink));
//                        }
//                        else{
//                            System.out.println("INCORRECT Value Entered !!!");
//                        }
//
//                    }catch(Exception e) {
//                        System.out.println("Something went WRONG !!!");
//                    }
//
//                }
//                else if (option == 2) {
//                    System.out.println("---------- PROGRAM HAS ENDED ----------");
//                    break;
//                } else {
//                    System.out.println("INCORRECT Value Entered !!!");
//                }
//
//            } catch (Exception e) {
//                System.out.println("Something went WRONG !!!");
//            }
//            System.out.println("");
//        }

    }
}

