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
    int penalty;
    double shortestPathLength = 0;
    ArrayList<MyVertex> shortestPath;



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
        int numVertices = in.nextInt();
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

            startVertex.turnDirection.put(vtx1, "up");
            startVertex.turnDirection.put(vtx2, "down");

            System.out.println(startVertex + " " + vtx1 + " " + leftWeight);
            System.out.println(startVertex + " " + vtx2 + " " + rightWeight);
    
        }

        counter1 = 1;
        counter2 = 2;
        int heightGraph = (int) Math.sqrt(numVertices);

        for (int i = numVertices; i > (numVertices - ((heightGraph * (heightGraph - 1)) /2 )); i--) {

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

            startVertex.turnDirection.put(vtx1, "down");
            startVertex.turnDirection.put(vtx2, "up");
        
            System.out.println(vtx1 + " " + startVertex + " " + leftWeight);
            System.out.println(vtx2 + " " + startVertex + " " + rightWeight);

        } 

    }

    // dont know if needed
    @Override
    public int size() {

        return size;

    }

    public void printAllPaths(String startLabel, String endLabel) {
        Set<MyVertex> keys = vertices.keySet();
        MyVertex start = null;
        MyVertex end = null;

        for (MyVertex v : keys) {
            if (v.label.equals(startLabel)) {
                start = v;
            }
            if (v.label.equals(endLabel)) {
                end = v;
            }
        }
        
        ArrayList<MyVertex> path = new ArrayList<>();
        path.add(start);

        printAllPathsUntil(start, end, path, 0, "");
        shortestPathLength -= penalty;
        System.out.println(shortestPathLength);
    }

    public void printAllPathsUntil(MyVertex current, MyVertex end, ArrayList<MyVertex> path, int pathWeight, String currentDirection) {
        if (current.equals(end)) {
            if (pathWeight > shortestPathLength) {
                shortestPath = path;
                shortestPathLength = pathWeight;
            }
            System.out.println(path + " : " + pathWeight);
            return;
        }

        current.setKnown(true);

        for (MyVertex v : neighborsOf(current)) {
            if (!v.isKnown()) {
                path.add(v);
                int newPathWeight = pathWeight + weightedNeighbors.get(current).get(v);
                if (!current.turnDirection.get(v).equals(currentDirection)) {
                    newPathWeight += penalty;
                }

                printAllPathsUntil(v, end, path, newPathWeight, current.turnDirection.get(v));

                path.remove(v);
            }
        }

        current.setKnown(false);

    }

}
