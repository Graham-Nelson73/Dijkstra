//@author Graham Nelson

public class BinaryHeap {
	// implements a binary heap of where the heap rule is the value in the parent
	// node is less than
	// or equal to the values in the child nodes
	private class Item implements Comparable<Object> {
		private int node;
		private int distance; // the priority

		private Item(int n, int d) {
			node = n;
			distance = d;
		}

		public int compareTo(Object z) {
			return distance - ((Item) z).distance;
		}
	}

	//stores items in min heap structure sorted by item.distance
	private Item items[];
	//stores index of items where node n is stored at locations[n]
	private int locations[];
	private int size;

	public BinaryHeap(int s) {
		items = new Item[s + 1];
		locations = new int[s];
		// initially no nodes have been inserted
		for (int i = 0; i < locations.length; i++)
			locations[i] = -1;
		size = 0;
	}

	public void removeMin() {
		// PRE: getSize() != 0
		// removes the highest priority item in the queue
		locations[items[1].node] = -1;
		items[1] = items[size];
		locations[items[1].node] = 1;
		items[size] = null;
		size--;
		if (size > 0) {
			shiftDown(1);
		}
	}
	
	//recursive method that shifts heap after index is removed
	private void shiftDown(int index) {
		int lChild, rChild, minIndex;
		Item temp;
		lChild = getLeftChild(index);
		rChild = getRightChild(index);
		if (rChild >= size) {
			if (lChild >= size) {
				return;
			} else {
				minIndex = lChild;
			}
		} else {
			if (items[lChild].distance <= items[rChild].distance) {
				minIndex = lChild;
			} else {
				minIndex = rChild;
			}
		}
		if (items[index].distance > items[minIndex].distance) {
			temp = items[minIndex];
			items[minIndex] = items[index];
			locations[items[index].node] = minIndex;
			items[index] = temp;
			locations[temp.node] = index;
			shiftDown(minIndex);
		}
	}
	
	//returns left child of given node
	private int getLeftChild(int node) {
		return node * 2;
	}
	
	//returns right child of given node
	private int getRightChild(int node) {
		return (node * 2) + 1;
	}

	public int getMinNode() {
		// PRE: getSize() != 0
		// returns the highest priority node
		return items[1].node;
	}

	public int getMinDistance() {
		// PRE: getSize() != 0
		// returns the distance of highest priority node
		return items[1].distance;
	}

	public boolean full() {
		// return true if the heap is full otherwise return false
		if (size >= items.length) {
			return true;
		}
		return false;
	}

	public void insert(int n, int d) {
		// PRE !full() and !inserted(n))
		int child = ++size;
		int parent = child/2;
		Item item = new Item(n, d);
		
		items[0] = item;
		while (items[parent].compareTo(item) > 0) {
			items[child] = items[parent];
			locations[items[child].node] = child;
			child = parent;
			parent = child/2;
		}
		items[child] = item;
		locations[n] = child;
	}

	public void decreaseKey(int n, int d) {
		// PRE inserted(n) and d < the distance associated with n
		// replace the current distance associated with n with d
		int index = locations[n];
		items[index] = items[size];
		locations[items[index].node] = index;
		items[size] = null;
		size--;
		shiftDown(index);
		insert(n, d);
	}

	public int getSize() {
		// return the number of values (priority , tree) pairs in the heap
		return size;
	}

	public boolean inserted(int n) {
		return locations[n] != -1;
	}
}