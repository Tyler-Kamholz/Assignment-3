import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class MyDirectedWeightedGraph implements MyGraph {

    private Map<MyVertex, MyVertex> vertices;
    private Map<MyVertex, Map<MyVertex, Integer>> weightedNeighbors; // Map to store neighbors and their weights
    private int size = 0;

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

        int penalty = in.nextInt();
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

            System.out.println(startVertex + " " + vtx1 + " " + leftWeight);
            System.out.println(startVertex + " " + vtx2 + " " + rightWeight);
    
        }

        

        for (int i = numVertices; i > half; i -= 2) {


            int leftWeight = list.pollLast();
            int rightWeight = list.pollLast();
        
            MyVertex startVertex = new MyVertex(i + "");
            MyVertex vtx1 = new MyVertex((i - 1) + "");
            MyVertex vtx2 = new MyVertex((i - 2) + "");
        
            addEdge(startVertex, vtx1, leftWeight);
            addEdge(startVertex, vtx2, rightWeight);
        
            System.out.println(startVertex + " " + vtx1 + " " + leftWeight);
            System.out.println(startVertex + " " + vtx2 + " " + rightWeight);

        } 

    }

    // dont know if needed
    @Override
    public int size() {

        return size;

    }

}
