import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class MyDirectedWeightedGraph implements MyGraph {

    private Map<MyVertex, MyVertex> vertices;
    private Map<MyVertex, Map<MyVertex, Integer>> weightedNeighbors; // Map to store neighbors and their weights
    private int size = 0;
    double shortestPathLength = Integer.MAX_VALUE;
    List<String> shortestPath;
    int heightGraph;
    int numVertices;
    int penalty;
    int pathDist;





    public void findShortestRoute(int startNode) {

        String startNodeString = startNode + "";
        MyVertex startVertex = new MyVertex(startNodeString);

        // Adds the start number to the current path
        LinkedList<String> currentPath = new LinkedList<>();
        currentPath.add(startNodeString);

        long startTime = System.currentTimeMillis();

        // Search
        search(startVertex, startVertex, currentPath, 0);

        long endTime = System.currentTimeMillis();

        // Writes to the console
        System.out.print("Shortest Route: ");
        for (String node : shortestPath) {
            System.out.print(node + " ");
        }

        // Had to put this here because the salesman has to go back home
        System.out.println();
        System.out.println("Total Length: " + Math.round(shortestPathLength));
        System.out.println("Total Time Taken: " + (endTime - startTime) + "ms");
    }

    private void search(MyVertex startNode, MyVertex currentNode, List<String> currentPath, double currentPathLength) {
        currentNode.setKnown(true);

        if (currentPath.size() == pathDist - 1) {
            // Check if we've visited all nodes and can return to the starting node
            int edgeWeight = getEdgeWeight(startNode, currentNode); // Weight to return to the start
            if (currentPathLength + edgeWeight < shortestPathLength) {
                shortestPathLength = currentPathLength + edgeWeight;
                shortestPath = currentPath;
                shortestPath.add(startNode.getLabel()); // Add the start node to complete the path
            }
        } else {
            for (MyVertex neighbor : neighborsOf(currentNode)) {
                // Assuming neighborsOf(currentNode) returns the correct neighbors
                if (!neighbor.isKnown() && !currentPath.contains(neighbor.getLabel())) {
                    currentPath.add(neighbor.getLabel());
                    double edgeWeight = getEdgeWeight(currentNode, neighbor);
                    search(startNode, neighbor, currentPath, currentPathLength + edgeWeight);
                    currentPath.remove(currentPath.size() - 1); // Backtrack
                }
            }
        }

        // Mark the current node as unvisited to allow other routes
        currentNode.setKnown(false);
    }

    public int getEdgeWeight(MyVertex start, MyVertex next) {

        return weightedNeighbors.get(start).get(next);
        
    }

    public MyDirectedWeightedGraph() {
        vertices = new HashMap<>();
        weightedNeighbors = new HashMap<>();
    }

    @Override
    public Set<MyVertex> vertexSet() {
        return new HashSet<>(vertices.values());
    }

    @Override
    public Set<MyVertex> neighborsOf(MyVertex inVtx) {
        if (!vertices.containsKey(inVtx)) {
            return null;
        }

        Map<MyVertex, Integer> neighborsWithWeights = weightedNeighbors.get(inVtx);

        if (neighborsWithWeights == null) {
            return new HashSet<>();
        }

        return neighborsWithWeights.keySet();
    }

    @Override
    public boolean addVertex(MyVertex vtx) {
        if (vertices.putIfAbsent(vtx, vtx) == null) {
            size++;
            return true;
        }
        return false;
    }

    @Override
    public boolean addEdge(MyVertex dummyVtx1, MyVertex dummyVtx2, int weight) {
        if (dummyVtx1.equals(dummyVtx2)) {
            return false;
        }

        addVertex(dummyVtx1);
        addVertex(dummyVtx2);

        MyVertex realVtx2 = vertices.get(dummyVtx2);

        if (addEdgeHelper(dummyVtx1, realVtx2, weight)) {
            size++;
            return true;
        }

        return false;
    }

    private boolean addEdgeHelper(MyVertex vtx1, MyVertex vtx2, int weight) {
        Map<MyVertex, Integer> neighborMap = weightedNeighbors.get(vtx1);

        if (neighborMap == null) {
            neighborMap = new HashMap<>();
            neighborMap.put(vtx2, weight);
            weightedNeighbors.put(vtx1, neighborMap);
            return true;
        } else {
            if (neighborMap.containsKey(vtx2)) {
                return false; // Edge already exists
            } else {
                neighborMap.put(vtx2, weight);
                return true;
            }
        }
    }

    @Override
    public void read(Scanner in) {

        penalty = in.nextInt();
        numVertices = in.nextInt();
        heightGraph = (int) Math.sqrt(numVertices);
        pathDist = (numVertices - ((heightGraph * (heightGraph - 1)) /2 ));
        int half = numVertices / 2;

        int counter1 = 0;
        int counter2 = 1;

        LinkedList<Integer> list = new LinkedList<>();
 
        while (in.hasNextInt()) {

            list.add(in.nextInt());

        }
       
        for (int i = 1; i < half; i++) {

            if ((i & (i - 1)) == 0 && i != 0) { 
                counter1 = counter1 + 1;
                counter2 = counter2 + 1;
            }

            int leftWeight = list.pollFirst();
            int rightWeight = list.pollFirst();

            MyVertex startVertex = new MyVertex(i + "");
            MyVertex vtx1 = new MyVertex((i + counter1) + "");
            MyVertex vtx2 = new MyVertex((i + counter2) + "");

            addEdge(startVertex, vtx1, leftWeight);
            addEdge(startVertex, vtx2, rightWeight);

            System.out.println(startVertex + " " + vtx1 + " " + leftWeight);
            System.out.println(startVertex + " " + vtx2 + " " + rightWeight);
    
        }

        counter1 = 1;
        counter2 = 2;

        for (int i = numVertices; i > pathDist; i--) {

            if ((i & (i - 1)) == 0 && i != 0) { 
                counter1 = counter1 + 1;
                counter2 = counter2 + 1;
            }

            int leftWeight = list.pollLast();
            int rightWeight = list.pollLast();
        
            MyVertex startVertex = new MyVertex(i + "");
            MyVertex vtx1 = new MyVertex((i - counter1) + "");
            MyVertex vtx2 = new MyVertex((i - counter2) + "");
        
            addEdge(vtx1, startVertex, leftWeight);
            addEdge(vtx2, startVertex, rightWeight);
        
            System.out.println(vtx1 + " " + startVertex + " " + leftWeight);
            System.out.println(vtx2 + " " + startVertex + " " + rightWeight);

        } 

    }

    @Override
    public int size() {

        return size;

    }

}
