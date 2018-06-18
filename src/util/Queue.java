public class Queue implements QueueInterface {
	
	// private Node class
	private class Node {
		private Object item;
		private Node next;
		
		// constructor
		Node(Object newItem) {
			this.item = newItem;
			this.next = null;
		}
	}
	
	Node HEAD;
	Node TAIL;
	int numItems = 0;
	
	// isEmpty() returns lists condition
	@Override
	public boolean isEmpty() {
		return (numItems == 0);
	}

	// length() returns length of list
	@Override
	public int length() {
		return numItems;
	}

	
	// enqueue() adds Object to end of list
	@Override
	public void enqueue(Object newItem) {
		Node newNode = new Node(newItem);
		
		if(HEAD == null) {
			HEAD = newNode;
			TAIL = newNode;
			numItems++;
		}
		else {
			TAIL.next = newNode;
			TAIL = newNode;
			numItems++;
		}
	}
	
	// dequeue() deletes item at front of list
	@Override
	public Object dequeue() throws QueueEmptyException {
		
		if(isEmpty() == true) {
			throw new QueueEmptyException("Error: List is empty");
		}
		else {
			Object delete = HEAD.item;
			HEAD = HEAD.next;
			numItems--;
			return delete;
		}
	}

	// peek() returns item at front of list
	@Override
	public Object peek() throws QueueEmptyException {
		return HEAD.item;
	}

	// dequeueAll() makes list empty
	@Override
	public void dequeueAll() throws QueueEmptyException {
		this.HEAD = null;
		this.TAIL = null;
		this.numItems = 0;
		
	}
	
	@Override
	public String toString() {
		String s = "";
		for(Node h = this.HEAD; h != null; h = h.next) {
			s += h.item + " ";
		}
		return s;
	}
}
