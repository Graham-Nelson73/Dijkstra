//@author Graham Nelson

import java.io.*;
import java.util.*;

public class Dijkstra {
	private class Vertex {
		private EdgeNode edges1;
		private EdgeNode edges2;
		private boolean known;
		private int distance;
		private int previous;

		private Vertex() {
			edges1 = null;
			edges2 = null;
			known = false;
			distance = Integer.MAX_VALUE;
			previous = -1;
		}
	}

	private class EdgeNode {
		private int vertex1;
		private int vertex2;
		private EdgeNode next1;
		private EdgeNode next2;
		private int weight;

		private EdgeNode(int v1, int v2, EdgeNode e1, EdgeNode e2, int w) {
			// PRE: v1 < v2
			vertex1 = v1;
			vertex2 = v2;
			next1 = e1;
			next2 = e2;
			weight = w;
		}
	}

	private Vertex[] g;

	public Dijkstra(int size) {
		g = new Vertex[size];
		for (int i = 0; i < g.length; i++) {
			g[i] = new Vertex();
		}
	}

	public void addEdge(int v1, int v2, int w) {
		// PRE: v1 and v2 are legitimate vertices
		// (i.e. 0 <= v1 < g.length and 0 <= v2 < g.length
		g[v1].edges1 = new EdgeNode(v1, v2, g[v1].edges1, g[v2].edges2, w);
		g[v2].edges2 = g[v1].edges1;
	}
	
	  public void printRoutes(int j) {
	        g[j].distance = 0;
	        BinaryHeap p = new BinaryHeap(g.length);
	        //fill heap with nodes
	        for(int i = 0; i < g.length; i++) {
	        	if(!p.full() && !p.inserted(i)) {
	        		p.insert(i, g[i].distance);
	        	}
	        }
	        //loop until heap is empty
	        while (p.getSize() != 0) {
	            Vertex minVert = g[p.getMinNode()];
	            if(!minVert.known) {
	            	minVert.known = true;
	                EdgeNode currentEdge = minVert.edges1;
	                //loops through both edge lists and update heap with decrease key
	                //sets previous to best path to current node, removes current node from
	                //heap when both edge lists have been searched
	                while (currentEdge != null) {
	                    if(minVert.distance + currentEdge.weight < g[currentEdge.vertex2].distance) {
	                        g[currentEdge.vertex2].distance = minVert.distance + currentEdge.weight;
	                        g[currentEdge.vertex2].previous = p.getMinNode();
	                        if(p.inserted(currentEdge.vertex2)) {
	                        	p.decreaseKey(currentEdge.vertex2, g[currentEdge.vertex2].distance);
	                        }
	                    }
	                    currentEdge = currentEdge.next1;
	                }
	                currentEdge = minVert.edges2;
	                //loop through edge list 2
	                while (currentEdge != null) {
	                    if(minVert.distance + currentEdge.weight < g[currentEdge.vertex1].distance) {
	                        g[currentEdge.vertex1].distance = minVert.distance + currentEdge.weight;
	                        g[currentEdge.vertex1].previous = p.getMinNode();
	                        if(p.inserted(currentEdge.vertex1)) {
	                        	p.decreaseKey(currentEdge.vertex1, g[currentEdge.vertex1].distance);
	                        }
	                    }
	                    currentEdge = currentEdge.next2;
	                }
	            }
	            p.removeMin();
	        }
	        //loop through each node i printing paths
	        Vertex vert;
	        String outPath;
	        for (int i = 0; i < g.length; i++) {
	            outPath = i + "";
	            vert = g[i];
	            while(vert.previous >= 0) {
	                outPath = vert.previous + " - " + outPath;
	                vert = g[vert.previous];
	            }
	            System.out.println("Path to node " + i + ": " + outPath);
	        }
	    }

	public static void main(String args[]) throws IOException {
		BufferedReader b = new BufferedReader(new FileReader(args[0]));
		String line = b.readLine();
		int numNodes = new Integer(line);
		line = b.readLine();
		int source = new Integer(line);
		System.out.println(source);
		Dijkstra g = new Dijkstra(numNodes);
		line = b.readLine();
		while (line != null) {
			Scanner scan = new Scanner(line);
			g.addEdge(scan.nextInt(), scan.nextInt(), scan.nextInt());
			line = b.readLine();
		}
		g.printRoutes(source);
	}
}