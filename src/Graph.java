import java.awt.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedList;

//Adjacency matrix implementation of a graph
public class Graph {

    private final double[][] adjMatrix;
    private final int verticesCount;
    public Hashtable<Square, Integer> SquareHash;
    public Hashtable<Integer, Square> IntForSquareHash;

    public Graph(int _vert) {
        //set up the graph
        this.verticesCount = _vert;
        this.adjMatrix = new double[this.verticesCount][this.verticesCount];
        this.SquareHash = new Hashtable<>();
        this.IntForSquareHash = new Hashtable<>();

        int x = 0;
        int y = 0;
        for (double[] row : this.adjMatrix) {
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
    public void removeEdge(int i, int j) {
        adjMatrix[i][j] = Double.POSITIVE_INFINITY;
        adjMatrix[j][i] = Double.POSITIVE_INFINITY;
    }

    public void changeSquareState(int squarePos) throws InterruptedException {
        Square square = this.IntForSquareHash.get(squarePos);
        if (square.state == Square.SquareState.START || square.state == Square.SquareState.WALL
                || square.state == Square.SquareState.DEST) {
            return;
        } else if (square.state == Square.SquareState.PATH){
            this.IntForSquareHash.get(squarePos).setBackground(Color.yellow);
        } else {
            this.IntForSquareHash.get(squarePos).setBackground(Color.gray);
        }
    }

    int getMinimumVertex(boolean[] mst, double[] key) {
        double minKey = Double.POSITIVE_INFINITY;
        int vertex = -1;
        for (int i = 0; i < this.verticesCount; i++) {
            if (!mst[i] && minKey > key[i]) {
                minKey = key[i];
                vertex = i;
            }
        }
        return vertex;
    }


    public void dijkstra(int sourceVertex, int destVertex) throws InterruptedException {
        boolean[] spt = new boolean[this.verticesCount];
        double[] distance = new double[this.verticesCount];
        int INFINITY = Integer.MAX_VALUE;

        //Initialize all the distance to infinity
        for (int i = 0; i < this.verticesCount; i++) {
            distance[i] = INFINITY;
        }

        int[] parent = new int[this.verticesCount];

        //start from the vertex 0
        distance[sourceVertex] = 0;

        //create SPT
        for (int i = 0; i < this.verticesCount; i++) {


            if (spt[destVertex]) {
                break;
            }

            //get the vertex with the minimum distance
            int vertex_U = getMinimumVertex(spt, distance);

            //include this vertex in SPT
            spt[vertex_U] = true;

            //iterate through all the adjacent vertices of above vertex and update the keys
            for (int vertex_V = 0; vertex_V < this.verticesCount; vertex_V++) {
                //check of the edge between vertex_U and vertex_V
                if (this.adjMatrix[vertex_U][vertex_V] > 0) {
                    //check if this vertex 'vertex_V' already in spt and
                    // if distance[vertex_V]!=Infinity


                    if (!spt[vertex_V] && this.adjMatrix[vertex_U][vertex_V] != INFINITY) {
                        //check if distance needs an update or not
                        //means check total weight from source to vertex_V is less than
                        //the current distance value, if yes then update the distance
                        this.changeSquareState(vertex_U);

                        double newKey = this.adjMatrix[vertex_U][vertex_V] + distance[vertex_U];
                        if (newKey < distance[vertex_V]) {
                            distance[vertex_V] = newKey;
                            parent[vertex_V] = vertex_U;
                        }
                    }
                }
            }
        }
        //print shortest path tree
        //printDijkstra(sourceVertex, distance);
        //printPath(destVertex, parent);
        LinkedList pathList = PreparePath(sourceVertex, destVertex, distance, parent);
        this.DrawPath(pathList);
    }


    public void printDijkstra(int sourceVertex, double[] key) {
        System.out.println("Dijkstra Algorithm: (Adjacency Matrix)");
        for (int i = 0; i < this.verticesCount; i++) {
            System.out.println("Source Vertex: " + sourceVertex + " to vertex " + +i +
                    " distance: " + key[i]);
        }
    }

    public void DrawPath(LinkedList pathList) throws InterruptedException {
        for (int i = 1; i < pathList.size() - 1; i++) {
            int squareVertex = (int) pathList.get(i);
            Square square = this.IntForSquareHash.get(squareVertex);
            square.state = Square.SquareState.PATH;
            this.changeSquareState(squareVertex);
        }
    }

    private LinkedList PreparePath(int startVertex, int destVertex, double[] distances, int[] parents) {
        LinkedList resultList = new LinkedList();
        for (int vertexIndex = destVertex; vertexIndex <= destVertex; vertexIndex++) {
            if (vertexIndex != startVertex) {
                LinkedList<Integer> pathList = new LinkedList<>();
                resultList = TracePath(vertexIndex, parents, pathList);
            }
        }
        return resultList;
    }

    private LinkedList TracePath(int currVertex, int[] parents, LinkedList pathList) {
        // Base case : Source node has
        // been processed
        if (currVertex == 0) {
            return pathList;
        }

        pathList.add(currVertex);
        return TracePath(parents[currVertex], parents, pathList);
    }


    //prints out the adjacency matrix
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < this.verticesCount; i++) {
            for (double j : adjMatrix[i]) {
                if (j == Double.POSITIVE_INFINITY) {
                    s.append("x").append("  |");
                } else {
                    s.append(j).append("|");
                }
            }
            s.append("\n");
        }
        return s.toString();
    }

}
