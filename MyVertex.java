import java.util.HashMap;

/**
 Given. DO NOT modify! Don't give any comment blocks.
 */
public class MyVertex {
    protected String label;
    protected boolean known;
    HashMap<MyVertex, Integer> neighbors; 
    HashMap<MyVertex, String> turnDirection;
    
    public MyVertex(String inLabel) {
        label = inLabel;
        neighbors = new HashMap<>();
        turnDirection = new HashMap<>();
    }
    
    public String getLabel() {
        return label;
    }
    
    public boolean isKnown() {
        return known;
    }
    
    public void setKnown(boolean inKnown) {
        known = inKnown;
    }
    
    public void addNeighbor(MyVertex neighbor, int weight, String direction) {
        neighbors.put(neighbor, weight);
        turnDirection.put(neighbor, direction);
    }

    public String toString() {
        return label;
    }

    public int hashCode() {
        int result = 1;
        result = 31 * result + ((label == null) ? 0 : label.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        MyVertex other = (MyVertex) obj;
        if (label == null) {
            if (other.label != null) {
                return false;
            }
        } else if (!label.equals(other.label))
            return false;
        return true;
    }


}