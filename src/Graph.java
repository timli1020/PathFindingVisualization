import java.util.Arrays;
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

        int x = 0;
        int y = 0;
        for (double[] row: this.adjMatrix) {
            Arrays.fill(row, Double.POSITIVE_INFINITY);
            this.adjMatrix[x][y] = 0;
            x += 1;
            y += 1;
        }

    }

    //add edge to from one square to another
    public void addEdge(int i, int j, double weight) {
        adjMatrix[i][j] = weight;
        adjMatrix[j][i] = weight;
    }

    //remove edge to from one square to another
    public void removeEdge(int i , int j) {
        adjMatrix[i][j] = Double.POSITIVE_INFINITY;
        adjMatrix[j][i] = Double.POSITIVE_INFINITY;
    }

    int getMinimumVertex(boolean [] mst, double [] key){
        double minKey = Double.POSITIVE_INFINITY;
        int vertex = -1;
        for (int i = 0; i < this.verticesCount ; i++) {
            if(!mst[i] && minKey>key[i]){
                minKey = key[i];
                vertex = i;
            }
        }
        return vertex;
    }

    public void dijkstra_GetMinDistances(int sourceVertex){
        boolean[] spt = new boolean[this.verticesCount];
        double [] distance = new double[this.verticesCount];
        int INFINITY = Integer.MAX_VALUE;

        //Initialize all the distance to infinity
        for (int i = 0; i < this.verticesCount ; i++) {
            distance[i] = INFINITY;
        }

        //start from the vertex 0
        distance[sourceVertex] = 0;

        //create SPT
        for (int i = 0; i < this.verticesCount ; i++) {

            //get the vertex with the minimum distance
            int vertex_U = getMinimumVertex(spt, distance);

            //include this vertex in SPT
            spt[vertex_U] = true;

            //iterate through all the adjacent vertices of above vertex and update the keys
            for (int vertex_V = 0; vertex_V < this.verticesCount ; vertex_V++) {
                //check of the edge between vertex_U and vertex_V
                if(this.adjMatrix[vertex_U][vertex_V]>0){
                    //check if this vertex 'vertex_V' already in spt and
                    // if distance[vertex_V]!=Infinity

                    if(!spt[vertex_V] && this.adjMatrix[vertex_U][vertex_V]!=INFINITY){
                        //check if distance needs an update or not
                        //means check total weight from source to vertex_V is less than
                        //the current distance value, if yes then update the distance

                        double newKey =  this.adjMatrix[vertex_U][vertex_V] + distance[vertex_U];
                        if(newKey<distance[vertex_V])
                            distance[vertex_V] = newKey;
                    }
                }
            }
        }
        //print shortest path tree
        printDijkstra(sourceVertex, distance);
    }

    public void printDijkstra(int sourceVertex, double [] key){
        System.out.println("Dijkstra Algorithm: (Adjacency Matrix)");
        for (int i = 0; i < this.verticesCount ; i++) {
            System.out.println("Source Vertex: " + sourceVertex + " to vertex " +   + i +
                    " distance: " + key[i]);
        }
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
