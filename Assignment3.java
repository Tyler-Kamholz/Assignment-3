import java.util.*;
import java.io.*;

public class Assignment3  {
    public static void main(String[] args) {

        Scanner in = null;
        try {

            in = new Scanner(new File("input.txt"));

            MyDirectedWeightedGraph graph = new MyDirectedWeightedGraph();
            graph.read(in);
            graph.findShortestRoute(1);
    
            //do whatever here
        }
        catch (FileNotFoundException ex) { 

            System.out.println("!! Input file not found: " + args[0]);  
            return;

        }
        finally {
            if (in != null) {
                in.close();
            }
        }
    }
}