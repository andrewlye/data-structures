import java.util.*;

public class UWGraph {
    private Vertex[] vertices; // array of vertices
    private int numVertices; // keeps track of the current number of vertices in the graph
    private int max; // max number of vertices
    private static final int DEFAULT_SIZE = 10;

    public UWGraph() {
        this(DEFAULT_SIZE);
    }

    public UWGraph(int size) { // overloaded constructor
        vertices = new Vertex[size];
        numVertices = 0;
        max = size;
    }

    public String[] shortestPath(String from, String to) { // shortest path algorithm using Dijkstra's (although breadth search is simpler since the graph is unweighted)
        clearEncounters(); 
        int startIndex = indexOf(from);
        int endIndex = indexOf(to);
        SPHelper(startIndex);
        return makePath(startIndex, endIndex); 
    }

    private void SPHelper(int src) { // private helper method
        int[] lengths = new int[numVertices]; // construct a partner array of corresponding distances for our vertices
        for (int i = 0; i < numVertices; i++) {
            lengths[i] = 999; // set all lengthts to an arbitrarily high number, in this case lengths = edges
        }
        lengths[src] = 0; // set our starting node's length to 0
        for (int count = 0; count < numVertices - 1; count++) {
            int minDist = 999; 
            int minIndex = 0; // find the index of unencountered vertex with the lowest distance
            for (int j = 0; j < numVertices; j++) {
                if (lengths[j] < minDist && !vertices[j].encountered) {
                    minDist = lengths[j]; 
                    minIndex = j;
                }
            }
            vertices[minIndex].encountered = true; // set the encountered of the vertex to true which acts as the known variable in Dijkstra's

            Vertex v = vertices[minIndex]; // check distances of all the vertex's adjacent vertices through its edges
            Iterator<Edge> itr = v.edges.iterator(); // using iterator makes this step efficient and simple
            while (itr.hasNext()) {
                Edge e = itr.next();
                int target = e.toIndex;
                if (lengths[minIndex] + 1 < lengths[target] && !vertices[target].encountered) { // check if adding an edge to the adjancency is still less than its original est. length
                    lengths[target] = lengths[minIndex] + 1;
                    vertices[target].parent = vertices[minIndex];
                }
            }
        }
    }

    public String[] secondShortestPath(String from, String to) { // second shortes path algorithm
        int startIndex = indexOf(from);
        int endIndex = indexOf(to);
        String[] shortestPath = shortestPath(from, to); // first, find the shortest path
        String remto = shortestPath[shortestPath.length - 1];
        String remFrom = shortestPath[shortestPath.length - 2];
        removeEdge(remFrom, remto); // then remove the last edge in the shortest path (i.e. the edge that makes the path the shortest)
        String[] secondShortest = shortestPath(from, to);; // then run the same algorithm again to find the second shortest path
        addEdge(remFrom, remto); // add the edge again so the graph is left unchanged
        return secondShortest;
    }

    public String[] BFS(String from, String to, String neighborOrder) { // breadth-first search/traversal
        clearEncounters();
        int startIndex = indexOf(from);
        int endIndex = indexOf(to);
        LinkedList<Vertex> q = new LinkedList<Vertex>(); // construct a queue to store our vertices
        vertices[startIndex].parent = null; 
        vertices[startIndex].encountered = true;
        q.add(vertices[startIndex]);

        while(!q.isEmpty()) {
            Vertex v = q.poll(); 
            if (neighborOrder.equals("reverse")) { // if the user specifies reverse-alphabetical order, start from end of our alphabetically sorted edge list
                for (int i = v.edges.size() - 1; i >=0 ; i--){ // for each unencountered neighboring vertex
                    if (vertices[v.edges.get(i).toIndex].encountered == false) {
                        vertices[v.edges.get(i).toIndex].encountered = true; // mark the encounter
                        vertices[v.edges.get(i).toIndex].parent = v; // set parent 
                        q.add(vertices[v.edges.get(i).toIndex]); // and insert the vertex into the queue
                    }
                }
            }
            else { // otherwise assume alphabetical ordering
                for (int i = 0; i < v.edges.size(); i++){
                    if (vertices[v.edges.get(i).toIndex].encountered == false) {
                        vertices[v.edges.get(i).toIndex].encountered = true;
                        vertices[v.edges.get(i).toIndex].parent = v;
                        q.add(vertices[v.edges.get(i).toIndex]);
                    }
                }
            }
        }

        return makePath(startIndex, endIndex);
    }

    public String[] DFS(String from, String to, String neighborOrder) { // depth first search
        clearEncounters();
        int startIndex = indexOf(from);
        int endIndex = indexOf(to);
        if (neighborOrder.equals("reverse"))
            myDFSRev(startIndex, null);
        else
            myDFS(startIndex, null);
        return makePath(startIndex, endIndex);


    }

    private void myDFS(int i, Vertex parent) { // recursive alphabetical order helper method
        vertices[i].encountered = true;
        vertices[i].parent = parent;
        Iterator<Edge> edgeItr = vertices[i].edges.iterator();
        while (edgeItr.hasNext()) {
            int j = edgeItr.next().toIndex;
			if(!vertices[j].encountered) {
				myDFS(j, vertices[i]);
            }
        }
    }

    private void myDFSRev(int i, Vertex parent) { // recursive reverse order helper method
        vertices[i].encountered = true;
        vertices[i].parent = parent;
        ListIterator<Edge> edgeItr = vertices[i].edges.listIterator(vertices[i].edges.size()); // start iterator at end instead
        while (edgeItr.hasPrevious()) {
            int j = edgeItr.previous().toIndex;
			if(!vertices[j].encountered) {
				myDFSRev(j, vertices[i]);
            }
        }
    }

    private String[] makePath(int start, int end) { // method that creates an array representing the path from one node to another depending on type of traversal used
        ArrayList<String> path = new ArrayList<String>();
        trace(vertices[end], vertices[start], path);
        String[] ret = new String[path.size()];
        for (int i = 0; i < path.size(); i++)
            ret[i] = path.get(i);

        return ret;
    }

    private void trace(Vertex v, Vertex stop, ArrayList<String> path) { // recursive helper method that traces the lineage (parents) of vertex's until the root is reached
        path.add(0, v.id);
        if (v.parent == null || v.id.equals(stop.id))
            return;
        trace(v.parent, stop, path);
    }

    public boolean addNode(String name) { // add node method
        if (indexOf(name) != -1)
        {System.out.println("Error: Node '" + name + "' already exists"); return false;}// error handling if node already exists

        int index = numVertices; //index in vertex array to insert node alphabetically
        for (int i = 0; i < numVertices; i++) { // loop through and compare string values 
            if (name.compareTo(vertices[i].id) < 0) {
                index = i; 
                break;
            }
        }

        for (int i = numVertices; i > index; i--) { // shift all vertex's after insertion point down one to make orom
            vertices[i] = vertices[i - 1];
        }
        vertices[index] = new Vertex(name); // insert node/vertex
        numVertices++;

        for (int i = 0; i < numVertices; i++) { // update the edge markers of the vertices that were incremented by one
            ArrayList<Edge> tempList = vertices[i].edges; // create a temp edge list
            for (int j = 0; j < tempList.size(); j++) {
                if (tempList.get(j).toIndex >= index)
                    tempList.get(j).toIndex++; // increment the toIndex's of the edges
            }
            vertices[i].edges = tempList; // copy templist into the actual adjacency lists
        }

        if (numVertices == max) { // if the number of vertices reaches the array limit, double size and copy over
            max = max * 2;
            Vertex[] old = vertices;
            vertices = new Vertex[max];
            for (int i = 0; i < numVertices; i++) {
                vertices[i] = old[i];
            }
        }
        return true;
    }

    public boolean addNodes(String[] names) { // method to add multiple nodes in an array
        for (String s: names)
            addNode(s);
        return true;
    }

    public boolean addEdge(String from, String to) {// method to add an edge
        int indexFrom = indexOf(from);
        int indexTo = indexOf(to);

        if (indexFrom == -1 || indexTo == -1 || indexTo == indexFrom)
        {System.out.println("Error: Please input valid node name"); return false;} // error handling

        for (int i = 0; i < vertices[indexFrom].edges.size(); i++) {
            if (vertices[indexFrom].edges.get(i).toIndex == indexTo) {
                System.out.println("Error: Edge already exists.");
                return false;
            }
        }

        vertices[indexFrom].edges.add(getListIndex(vertices[indexFrom].edges, indexTo), new Edge(indexTo)); // connect edge from one side
        vertices[indexTo].edges.add(getListIndex(vertices[indexTo].edges, indexFrom), new Edge(indexFrom)); // and then connect from the other

        return true;
    }

    public boolean addEdges(String from, String[] to) { // method to add multiple edges at once
        for (String s: to)
            addEdge(from, s);
        return true;
    }

    public boolean removeNode(String name) { // method to remove a node/vertex
        int index = indexOf(name);
        if (index == -1) // do nothing if node does not exist
        {return false;}
        
        for (int i = index; i < numVertices; i++)
            vertices[i] = vertices[i+1]; // remove the node by 'folding over' the array indices after it so that it is replaced  by the next index over
        numVertices--;

        for (int i = 0; i < numVertices; i++) { // similar to how adding a node needs an increment in edge markers, there is a decrement when a node is removed
            ArrayList<Edge> tempList = vertices[i].edges;
            int removeIndex = -1;
            for (int j = 0; j < tempList.size(); j++) {
                if (tempList.get(j).toIndex > index) // if the index pointer is higher than the remove index, decrement
                    tempList.get(j).toIndex--;
                if (tempList.get(j).toIndex == index && tempList.get(j).toID.equals(name)) { // remove all instances of the index to be deleted
                    removeIndex = j;
                }
            }
            if (removeIndex != -1)
                tempList.remove(removeIndex);
            vertices[i].edges = tempList;

        }
        return true;
    }

    public boolean removeNodes(String[] nodeList) { // removes multiple nodes at once
        for (String s: nodeList)
            removeNode(s);

        return true;
    }

    private void removeEdge(String from, String to) { // private remove edge method that helps in finding the second shortest path
        int fromIndex = indexOf(from);
		int toIndex = indexOf(to);
        if (fromIndex == -1 || toIndex == -1 || fromIndex == toIndex)
            return;
        
        ArrayList<Edge> fromList = vertices[fromIndex].edges;
        ArrayList<Edge> toList = vertices[toIndex].edges;

        for(int i = 0; i < fromList.size(); i++) {
            if (fromList.get(i).toIndex == toIndex) {
                fromList.remove(i);
            }
        }
        for(int i = 0; i < toList.size(); i++) {
            if (toList.get(i).toIndex == fromIndex) {
                toList.remove(i);
            }
        }

        vertices[fromIndex].edges = fromList;
        vertices[toIndex].edges = toList;
    }

    public void printGraph() { // prints the graph in an adjancecy list format
        for (int i = 0; i < numVertices; i++) {
            System.out.print(i + ": ");
            if (vertices[i] != null){
                System.out.print(vertices[i].id + " --> ");
                for (int j = 0; j < vertices[i].edges.size(); j++)
                    System.out.print(vertices[vertices[i].edges.get(j).toIndex].id + " ");
            }
            System.out.println();
        }
    }

    public int indexOf(String s) { // finds the index of a node with name s
        for (int i = 0; i < max; i++) {
            if (vertices[i] != null && vertices[i].id.equals(s))
                return i;

        }
        return -1;
    }

    private int getListIndex(ArrayList<Edge> edges, int in) { // returns the adjacency list index to insert an edge marker alphabetically
        String insert = vertices[in].id;
        String temp;
        for (int i = 0; i < edges.size(); i++) {
            temp = vertices[edges.get(i).toIndex].id;
            if (insert.compareTo(temp) < 0){
                return i;
            }
        }

        return edges.size();
    }

    public void clearEncounters() { // resets all encounter markers and parents
        for (int i = 0; i < vertices.length; i++)
        {
            if (vertices[i] != null) {
                vertices[i].encountered = false;
                vertices[i].parent = null;
            }
        }
    }

    private class Vertex { // private vertex inner class
        private String id;
        private ArrayList<Edge> edges;
        private boolean encountered;
        private Vertex parent;

        public Vertex(String in) {
            id = in;
            edges = new ArrayList<Edge>();
            encountered = false;
        }

    }
    private class Edge { // private edge inner class
        private int toIndex;
        private String toID;
        public Edge (int to) {
            toIndex = to;
            toID = vertices[toIndex].id;
        }
    }
    
}