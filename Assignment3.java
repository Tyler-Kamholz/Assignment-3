import java.util.*;
import java.io.*;

public class Assignment3  {
    public static void main(String[] args) {
        Scanner in = null;
        try {
            if (args.length < 1) {
                in = new Scanner(System.in);
            }
            else {
                in = new Scanner(new File(args[0]));
            }
            MyDirectedUnweightedGraph graph = new MyDirectedUnweightedGraph();
            graph.read(in);
    
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