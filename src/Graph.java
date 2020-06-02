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
            this.IntForSquareHash.get(squarePos).setBackground(Color.blue);
        } else {
            this.IntForSquareHash.get(squarePos).setBackground(Color.darkGray);
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

    int getMinDist2(double[] dist, LinkedList queue) {
        double min = Double.POSITIVE_INFINITY;
        int min_index = -1;
        for (int i = 0; i < dist.length; i++) {
            if (dist[i] < min && queue.contains(i)) {
                min = dist[i];
                min_index = i;
            }
        }
        return min_index;
    }

    public void dijkstra_GetMinDistances(int sourceVertex, int destVertex) throws InterruptedException {
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

    public void dijkstra(int sourceVertex, int destVertex) throws InterruptedException {
        int nVertices = this.adjMatrix[0].length;

        // shortestDistances[i] will hold the
        // shortest distance from src to i
        double[] shortestDistances = new double[nVertices];

        // added[i] will true if vertex i is
        // included / in shortest path tree
        // or shortest distance from src to
        // i is finalized
        boolean[] added = new boolean[nVertices];

        // Initialize all distances as
        // INFINITE and added[] as false
        for (int vertexIndex = 0; vertexIndex < nVertices;
             vertexIndex++) {
            shortestDistances[vertexIndex] = Integer.MAX_VALUE;
            added[vertexIndex] = false;
        }

        // Distance of source vertex from
        // itself is always 0
        shortestDistances[sourceVertex] = 0;

        // Parent array to store shortest
        // path tree
        int[] parents = new int[nVertices];

        // The starting vertex does not
        // have a parent
        parents[sourceVertex] = 0;

        // Find shortest path for all
        // vertices
        for (int i = 1; i < nVertices; i++) {

            if(i == destVertex) {
                break;
            }



            // Pick the minimum distance vertex
            // from the set of vertices not yet
            // processed. nearestVertex is
            // always equal to startNode in
            // first iteration.
            int nearestVertex = -1;
            double shortestDistance = Double.POSITIVE_INFINITY;
            for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
                if (!added[vertexIndex] && shortestDistances[vertexIndex] < shortestDistance) {
                    nearestVertex = vertexIndex;
                    shortestDistance = shortestDistances[vertexIndex];
                }
            }

            // Mark the picked vertex as
            // processed
            added[nearestVertex] = true;

            // Update dist value of the
            // adjacent vertices of the
            // picked vertex.
            for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
                double edgeDistance = this.adjMatrix[nearestVertex][vertexIndex];

                if (edgeDistance > 0 && ((shortestDistance + edgeDistance) < shortestDistances[vertexIndex])) {
                    parents[vertexIndex] = nearestVertex;
                    shortestDistances[vertexIndex] = shortestDistance + edgeDistance;
                    this.changeSquareState(vertexIndex);
                }
            }
        }

        PreparePath(sourceVertex, destVertex, shortestDistances, parents);
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
