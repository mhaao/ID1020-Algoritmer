/*
package Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BreadthFirstPaths {
    private static final int INFINITY = Integer.MAX_VALUE;
    private boolean[] marked;  // marked[v] = is there an s-v path
    private int[] edgeTo;      // edgeTo[v] = previous edge on shortest s-v path
    private int[] distTo;      // distTo[v] = number of edges shortest s-v path

    */
/**
     * Computes the shortest path between the source vertex {@code s}
     * and every other vertex in the graph {@code G}.
     * @param G the graph
     * @param s the source vertex
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     *//*

    public BreadthFirstPaths(Graph G, int s) {
        marked = new boolean[G.V()];
        distTo = new int[G.V()];
        edgeTo = new int[G.V()];
        validateVertex(s);
        bfs(G, s);

        assert check(G, s);
    }

    */
/**
     * Computes the shortest path between any one of the source vertices in {@code sources}
     * and every other vertex in graph {@code G}.
     * @param G the graph
     * @param sources the source vertices
     * @throws IllegalArgumentException if {@code sources} is {@code null}
     * @throws IllegalArgumentException unless {@code 0 <= s < V} for each vertex
     *         {@code s} in {@code sources}
     *//*

    public BreadthFirstPaths(Graph G, Iterable<Integer> sources) {
        marked = new boolean[G.V()];
        distTo = new int[G.V()];
        edgeTo = new int[G.V()];
        for (int v = 0; v < G.V(); v++)
            distTo[v] = INFINITY;
        validateVertices(sources);
        bfs(G, sources);
    }


    // breadth-first search from a single source
    private void bfs(Graph G, int s) {
        Queue2LIFO.Queue<Integer> q = new Queue2LIFO.Queue<Integer>();
        for (int v = 0; v < G.V(); v++)
            distTo[v] = INFINITY;
        distTo[s] = 0;
        marked[s] = true;
        q.enqueue(s);

        while (!q.isEmpty()) {
            int v = q.dequeue();
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    q.enqueue(w);
                }
            }
        }
    }

    // breadth-first search from multiple sources
    private void bfs(Graph G, Iterable<Integer> sources) {
        Queue2LIFO.Queue<Integer> q = new Queue2LIFO.Queue<Integer>();
        for (int s : sources) {
            marked[s] = true;
            distTo[s] = 0;
            q.enqueue(s);
        }
        while (!q.isEmpty()) {
            int v = q.dequeue();
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    q.enqueue(w);
                }
            }
        }
    }

    */
/**
     * Is there a path between the source vertex {@code s} (or sources) and vertex {@code v}?
     * @param v the vertex
     * @return {@code true} if there is a path, and {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     *//*

    public boolean hasPathTo(int v) {
        validateVertex(v);
        return marked[v];
    }

    */
/**
     * Returns the number of edges in a shortest path between the source vertex {@code s}
     * (or sources) and vertex {@code v}?
     * @param v the vertex
     * @return the number of edges in such a shortest path
     *         (or {@code Integer.MAX_VALUE} if there is no such path)
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     *//*

    public int distTo(int v) {
        validateVertex(v);
        return distTo[v];
    }

    */
/**
     * Returns a shortest path between the source vertex {@code s} (or sources)
     * and {@code v}, or {@code null} if no such path.
     * @param  v the vertex
     * @return the sequence of vertices on a shortest path, as an Iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     *//*

    public Iterable<Integer> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<Integer>();
        int x;
        for (x = v; distTo[x] != 0; x = edgeTo[x])
            path.push((char) x);
        path.push((char) x);
        return (Iterable<Integer>) path;
    }


    // check optimality conditions for single source
    private boolean check(Graph G, int s) {

        // check that the distance of s = 0
        if (distTo[s] != 0) {
            System.out.println("distance of source " + s + " to itself = " + distTo[s]);
            return false;
        }

        // check that for each edge v-w dist[w] <= dist[v] + 1
        // provided v is reachable from s
        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v)) {
                if (hasPathTo(v) != hasPathTo(w)) {
                    System.out.println("edge " + v + "-" + w);
                    System.out.println("hasPathTo(" + v + ") = " + hasPathTo(v));
                    System.out.println("hasPathTo(" + w + ") = " + hasPathTo(w));
                    return false;
                }
                if (hasPathTo(v) && (distTo[w] > distTo[v] + 1)) {
                    System.out.println("edge " + v + "-" + w);
                    System.out.println("distTo[" + v + "] = " + distTo[v]);
                    System.out.println("distTo[" + w + "] = " + distTo[w]);
                    return false;
                }
            }
        }

        // check that v = edgeTo[w] satisfies distTo[w] = distTo[v] + 1
        // provided v is reachable from s
        for (int w = 0; w < G.V(); w++) {
            if (!hasPathTo(w) || w == s) continue;
            int v = edgeTo[w];
            if (distTo[w] != distTo[v] + 1) {
                System.out.println("shortest path edge " + v + "-" + w);
                System.out.println("distTo[" + v + "] = " + distTo[v]);
                System.out.println("distTo[" + w + "] = " + distTo[w]);
                return false;
            }
        }

        return true;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    // throw an IllegalArgumentException if vertices is null, has zero vertices,
    // or has a vertex not between 0 and V-1
    private void validateVertices(Iterable<Integer> vertices) {
        if (vertices == null) {
            throw new IllegalArgumentException("argument is null");
        }
        int V = marked.length;
        int count = 0;
        for (Integer v : vertices) {
            count++;
            if (v == null) {
                throw new IllegalArgumentException("vertex is null");
            }
            validateVertex(v);
        }
        if (count == 0) {
            throw new IllegalArgumentException("zero vertices");
        }
    }

    */
/**
     * Unit tests the {@code BreadthFirstPaths} data type.
     *
     * @param args the command-line arguments
     *//*

    public static void main(String[] args) throws FileNotFoundException {
        Scanner readFil = new Scanner(new File("Test4.txt")); //läsa från fil

        //Läsa alla Hörnen
        StringBuilder sor = new StringBuilder();
        while (readFil.hasNextLine())
        {
            sor.append(readFil.nextLine());
            sor.append('\n');
        }
        //min alla lista är out
        String läsaOut = sor.toString();
        // Skapa en ny symbolgraf där ord separeras med "".
        SymbolGraph3 st = new SymbolGraph3(läsaOut, " ");

        //Ta emot hörnen 1 Input
        System.out.println("Första Hörnen: ");
        Scanner first = new Scanner(System.in);//Input scanner
        String startHornen = first.nextLine();//Scanner till String
        int vertex1= st.index(startHornen);//String till index

        //Ta emot hörnen 2 Input
        System.out.println("Andra Hörnen: ");
        Scanner end = new Scanner(System.in);//Input scanner
        String endHornen = end.nextLine();//Scanner till String
        int vertex2= st.index(endHornen);//String till index

        String filename  = startHornen;// First state
        String delimiter = endHornen;// End state.







        Graph G = new Graph(vertex1);
        // StdOut.println(G);

        int s = Integer.parseInt(endHornen);
        BreadthFirstPaths bfs = new BreadthFirstPaths(G, s);

        for (int v = 0; v < G.V(); v++) {
            if (bfs.hasPathTo(v)) {
                System.out.printf("%d to %d (%d):  ", s, v, bfs.distTo(v));
                for (int x : bfs.pathTo(v)) {
                    if (x == s) System.out.print(x);
                    else        System.out.print("-" + x);
                }
                System.out.println();
            }

            else {
                System.out.printf("%d to %d (-):  not connected\n", s, v);
            }

        }
    }


}
*/
