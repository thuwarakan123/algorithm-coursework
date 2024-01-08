import java.util.LinkedList;

public class Flow_Network {
    // total nodes of the flow network
    private int totalNodes;
    // implement of a matrix of flow network
    private int[][] flowEdge;
    // start node of this flow network
    private int sourceNode;
    // target node of this flow network
    private int targetNode;
    // implement a Residual Network
    private int[][] residualNetwork;
    // define the maximum flow
    private int maxFlow;
    // create a node array
    private int[] nodeArray;

    public Flow_Network(int node){
        totalNodes = node;
        sourceNode = 0;
        targetNode = node-1;
        flowEdge = new int[node][node];
        residualNetwork = new int[node][node];
        maxFlow = 0;
        nodeArray = new int[node];

    }

    //method for add edge to the flow network
    public void addEdge(int fromNode, int toNode, int capacity){
        flowEdge[fromNode][toNode] = capacity;
    }

    //method to remove the edge on flow network
    public void removeEdge(int fromNode, int toNode){
        flowEdge[fromNode][toNode] = 0;
    }


    public void printFlow(){
        for(int i=0; i<totalNodes; i++){
            for(int j=0; j<totalNodes; j++){
                System.out.print(flowEdge[i][j] + " ");
            }
            System.out.println();
        }
    }

    // toString method to print the details of the flow network
    @Override
    public String toString() {
        String node = " ";
        for(int i = 0; i < totalNodes ; i++){
            if(i == totalNodes - 1){
                node = node + i + ". ";
            }
            else {
                node = node + i + ", ";
            }
        }
        String message = "\nThis Flow Network Contain " + totalNodes + " Nodes and those are :" + node +
                "\nStart node : " + sourceNode + "\nEnd Node : " + targetNode + "\nMaximum Flow : " + getMaxFlow();
        return message;

    }

    // implantation of the bfs algorithm
    private boolean searchNode() {
        boolean visited[] = new boolean[totalNodes];
        for (int i = 0; i < totalNodes; ++i) {
            visited[i] = false;
        }
        LinkedList<Integer> Line = new LinkedList();
        Line.add(sourceNode);
        visited[sourceNode] = true;
        nodeArray[sourceNode] = -1;

        while (Line.size() != 0) {
            int u = Line.poll();

            for (int i = 0; i < totalNodes; i++) {
                if (visited[i] == false && residualNetwork[u][i] > 0) {
                    if (i == targetNode) {
                        nodeArray[i] = u;
                        return true;
                    }
                    Line.add(i);
                    nodeArray[i] = u;
                    visited[i] = true;
                }
            }
        }
        return false;
    }

    //method for calculate the maximum flow value
    public int getMaxFlow() {
        // add values to the residualNetwork
        for (int i = 0; i < totalNodes; i++) {
            for (int j = 0; j < totalNodes; j++) {
                residualNetwork[i][j] = flowEdge[i][j];
            }
        }

        while (searchNode()) {
            int path_flow = Integer.MAX_VALUE;
            for (int v = targetNode; v != sourceNode; v = nodeArray[v]) {
                int u = nodeArray[v];
                path_flow = Math.min(path_flow, residualNetwork[u][v]);
            }

            for (int v = targetNode; v != sourceNode; v = nodeArray[v]) {
                int u = nodeArray[v];
                residualNetwork[u][v] -= path_flow;
                residualNetwork[v][u] += path_flow;
            }
            maxFlow = maxFlow + path_flow;
        }
        return maxFlow;
    }
}
