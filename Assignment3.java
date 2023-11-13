import java.util.*;
import java.io.*;

public class Assignment3  {
    public static void main(String[] args) {

        Scanner in = null;
        try {

            in = new Scanner(new File("input.txt"));

            MyDirectedWeightedGraph graph = new MyDirectedWeightedGraph();
            graph.read(in);
    
            graph.printAllPaths("1", "9");
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

    public void generateRandInputFile() {
        File file = new File("input2.txt");
        try {
            FileWriter write = new FileWriter(file);

            Random rand = new Random();

            int penalty = rand.nextInt(50) - 30;
            String fileData = penalty + " " + 1000 + " ";
            for(int i = 0; i < 1000000; i++) {
                
            }
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}