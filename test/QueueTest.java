public class QueueTest {

	public static void main(String[] args) {
		
		int i = 0;
		Queue queue = new Queue();
		int[][] myArray = {{1,2},{3,4},{5,6},{7,8},{9,10}};
		
		// initialize Job class array
		Job[] job = new Job[myArray.length];
		
		// putting integers to Job object array
		while(i < myArray.length) {
			for(int j = 0; j < 1; j++) {
				job[i] = new Job(myArray[i][j], myArray[i][j + 1]);
			}
			i++;
		}
		i = 0;
		
		System.out.println("List status empty? " + queue.isEmpty());
		
		// adds object into linkedlist
		while(i < job.length) {
			queue.enqueue(job[i]);
			i++;
		}
		i = 0;
		
		System.out.println("<Added objects to linked list>");
		System.out.println("-----------------------------");
		// list status
		System.out.println("List status empty? " + queue.isEmpty());
		
		// prints linkedlist length;
		System.out.println("List size: " +queue.numItems);
		
		// peek before dequeue
		System.out.println("Peek before dequeue: " + queue.peek());
		
		// print list before dequeue
		System.out.println("Before dequeue list: " +queue);
		
		// delete front of list
		System.out.println("Dequeue: " +queue.dequeue());
		
		// print list after dequeue
		System.out.println("After dequeue list: " +queue);
		
		// peek after dequeue
		System.out.println("Peek after dequeue: " + queue.peek());
		
		// list size
		System.out.println("List size: " +queue.numItems);
		
		//delete front of list
		System.out.println("Dequeue: " +queue.dequeue());
		
		// print list after dequeue
		System.out.println("After dequeue list: " +queue);

		// peek after dequeue
		System.out.println("Peek after dequeue: " + queue.peek());
		
		// list size
		System.out.println("List size: " +queue.numItems);
		
		// empty list
		queue.dequeueAll();
		
		// list status
		System.out.println("List status empty? " + queue.isEmpty());
		
		// emptied linked list status
		System.out.println("Emptied linked list");
				
		// prints linkedlist length;
		System.out.println("List size: " +queue.numItems);
		

	}

}
