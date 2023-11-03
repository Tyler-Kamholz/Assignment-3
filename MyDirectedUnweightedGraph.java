import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * 
 * Author: Tyler Kamholz
 * Lab 10
 * This lab was to show we know how to use a searching algorithm 
 * and find out if the graph is strongly connected or not
 * Bugs: None!!! 😁
 * 
 */


public class MyDirectedUnweightedGraph implements MyGraph{

    //           dummy     real
    private Map<MyVertex, MyVertex> vertices;

    //           dummy          real
    private Map<MyVertex, Set<MyVertex>> neighbors;

    private int size = 0; //the size of the graph is number of Verticies + the number of Edges

    public MyDirectedUnweightedGraph() {
        vertices = new HashMap<MyVertex, MyVertex>();
        neighbors = new HashMap<MyVertex, Set<MyVertex>>();
    }

    //dont know if needed
    @Override
    public Set<MyVertex> vertexSet() {
        
        HashSet<MyVertex> vs = new HashSet<>();
        vs.addAll(vertices.values());

        return vs;
    }

    //dont know if needed
    @Override
    public Set<MyVertex> neighborsOf(MyVertex inVtx) {
        
        //check if there is that vertex
        if(!vertices.containsKey(inVtx)) {
            return null;
        }

        //grab the set
        Set<MyVertex> setOfNeighbors = neighbors.get(inVtx);

        //if there is no neighbors make an empty set to send
        if(setOfNeighbors == null) {
            Set<MyVertex> emptySet = new HashSet<>();
            return emptySet;
        }

        //return the set
        return setOfNeighbors;

    }

    @Override
    public boolean addVertex(MyVertex vtx) {
        
       if(vertices.putIfAbsent(vtx, vtx) == null) {

            size++;
            return true;

        }

        return false;
    }

    @Override
    public boolean addEdge(MyVertex dummyVtx1, MyVertex dummyVtx2) {
        
        if(dummyVtx1.equals(dummyVtx2)) {
            return false;
        }

        addVertex(dummyVtx1);
        addVertex(dummyVtx2);

        MyVertex realVtx2 = vertices.get(dummyVtx2);

        if(addEdgeHelper(dummyVtx1, realVtx2)) {

            size++;
            return true;

        }

        return false;
    }

    private boolean addEdgeHelper(MyVertex vtx1, MyVertex vtx2) {

        //if vtx1 has no neighbors make a set and put it into the neighbors
        if(neighbors.get(vtx1) == null) {

            Set<MyVertex> neighborSet = new HashSet<>(); 
            neighborSet.add(vtx2);

            neighbors.put(vtx1, neighborSet); // puts the new set into the vertex
            return true;

        } else {

            // this means that there is already the max amount of edges
            if(vertexSet().size() == neighbors.get(vtx1).size() - 1) {

                return false;

            } else {

                Set<MyVertex> neighborSet = new HashSet<>();
                neighborSet.addAll(neighbors.get(vtx1)); // adds all the previous edges

                if(!neighborSet.add(vtx2)) {

                    //if it cannont add to the set, its because there is already that edge
                    return false;

                }

                neighbors.put(vtx1, neighborSet); //puts the new set into the vertex
                return true;

            }
        }
    }

    @Override
    public void read(Scanner in) {
       

        //this is what we have to impliment 

        /* while(in.hasNextLine()) {
            
            String line = in.nextLine();
            Scanner sc = new Scanner(line);
            String token1 = sc.next();
            MyVertex vtx1 = new MyVertex(token1);

            if(sc.hasNext()) {

                String token2 = sc.next();
                MyVertex vtx2 = new MyVertex(token2);
                addEdge(vtx1, vtx2);

            } else {

                addVertex(vtx1);

            }

            sc.close();

        } */

    }

    //dont know if needed
    @Override
    public int size() {

        return size;

    }

}
