import java.util.Hashtable;

//Adjacency matrix implementation of a graph
public class Graph {

    private final double[][] adjMatrix;
    private final int verticesCount;
    public Hashtable<Square, Integer> SquareHash;

    public Graph(int _vert) {
        //set up the graph
        this.verticesCount = _vert;
        this.adjMatrix = new double[this.verticesCount][this.verticesCount];
        this.SquareHash = new Hashtable<>();

    }

    //add edge to from one square to another
    public void addEdge(int i, int j, double weight) {
        adjMatrix[i][j] = weight;
        adjMatrix[j][i] = weight;
    }

    //remove edge to from one square to another
    public void removeEdge(int i , int j) {
        adjMatrix[i][j] = -1;
        adjMatrix[j][i] = -1;
    }

    //prints out the adjacency matrix
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < this.verticesCount; i++) {
            for (double j : adjMatrix[i]) {
                if (j == Double.POSITIVE_INFINITY) {
                    s.append("x").append("  |");
                } else{
                    s.append(j).append("|");
                }
            }
            s.append("\n");
        }
        return s.toString();
    }

}
