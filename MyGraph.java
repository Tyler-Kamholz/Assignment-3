import java.util.*;

/**
 * Basic operations for an un-weighted graph
 * 
 * Given. DO NOT modify! Don't even give comment blocks.
 */
public interface MyGraph {
    public abstract Set<MyVertex> vertexSet();

    public abstract Set<MyVertex> neighborsOf(MyVertex inVtx);

    public abstract boolean addVertex(MyVertex vtx);

    public abstract  boolean addEdge(MyVertex dummyVtx1, MyVertex dummyVtx2, int weight, String direction);

    public abstract void read(Scanner in);

    public abstract int size(); 
}
