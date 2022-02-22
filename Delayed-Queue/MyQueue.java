public class MyQueue<E> implements DelayedQueue<E> {
    private int capacity; 
	private E[] array;
	private E[] newArray;
    private int size;
    private int maxDelay;
	private int delayCondition; 
	private boolean dequeueFlag; 
	private boolean clearFlag; 


    // constructor 
	@SuppressWarnings("unchecked")
    public MyQueue(int maxDelay) {
        this.maxDelay = maxDelay;
        this.capacity = 10; 
        this.size = 0; 
		this.array = (E[]) new Object[capacity];
		this.delayCondition = maxDelay;
    }

    /**
	 * Returns the number of elements currently in the queue.
	 * @return The size of the queue.
	 */

	public int size() {
        return this.size; 
    }
	
	/**
	 * Add an element to this queue.
	 * @param element The element to be added.
	 */
    @SuppressWarnings("unchecked")
	public void enqueue(E element) {
		if (this.size == 0) {
			this.array = (E[]) new Object[capacity];
		}

        if (this.size == this.capacity) {
			// create a new array with more space 
			this.capacity *= 2;
			E[] newArray = (E[]) new Object[this.capacity];

			// copy elements from old array into new array 
			for (int i = 0; i < this.size; i++) {
				newArray[i] = array[i];
			} 
			// assign the new array to be the class attribute array 
			this.array = newArray; 
        }
		//there is space in array, add element and increment size
        array[this.size] = element;
        this.size++;

        // reset delay condition if dequeue just occurred previously 
        if (dequeueFlag) {
			this.delayCondition = this.maxDelay;
			dequeueFlag = false; 
		} if (clearFlag) {
			this.delayCondition = this.maxDelay;
			clearFlag = false; 
		}this.delayCondition--;

    }
	
	/**
	 * Attempt to remove an element from this queue. 
	 * The first element to remove is the oldest element 
	 * If the enqueue is empty, throw an IllegalStateException.
	 * If an element cannot be removed due to the delay condition, return null.
	 * 
	 * @return The element that was removed.
	 * @throws IllegalStateException if the queue is empty.
	 */
    @SuppressWarnings("unchecked")
	public E dequeue() throws IllegalStateException {
        if (this.size == 0) {
			throw new IllegalStateException();
		} 

		if (getDelay() == 0) {
			//store the oldest elem 
			E oldestElement = array[0]; 

            // create newArray with size 1 less than the original array 
            E[] newArray = (E[]) new Object[this.size - 1];

            for (int i = 0, j = 0; i < this.size; i++) {
                if (i != 0) {
                    newArray[j++] = array[i];
                }
            } 
			this.array = newArray; 
			this.size--; 
			this.dequeueFlag = true;
			return oldestElement;
		} else {
			return null;
		}
    }
	
	/**
	 * Return the element at the front of this queue, without removing it.
	 * 
	 * @return The element at the front of the queue.
	 * @throws IllegalStateException if the queue is empty.
	 */
	public E peek() throws IllegalStateException {
        if (this.size == 0) {
			throw new IllegalStateException();
		}
		return this.array[0];
	}
	
	/**
	 * Returns how many more elements must be added before the queue will allow removals to commence.
	 * @return
	 */
	public int getDelay() {
        if (this.maxDelay <= 0 || this.delayCondition <= 0) {
			return 0; 
		} return this.delayCondition; 
    }
	
	/**
	 * Sets the maximum delay, which is the number of elements that must be added to the queue before any can be removed.
	 * Once a removal is allowed to occur, there is no limit on the number of removals.
	 * Modifying this value while the queue is in use does not change the current delay value. It only changes the next time the delay is reset.
	 * 
	 * @param d The number of elements that must be added before any can be removed.
	 */
	public void setMaximumDelay(int d) {
        this.maxDelay = d; 
    }
	
	/**
	 * Get the maximum possible delay, which is the number of push operations that must occur before pop operations can occur, given that the first push operation has not yet occurred (ie. a pop operation just occurred).
	 * @return The maximum number of push operations before pop operations can occur.
	 */
	public int getMaximumDelay() {
        return this.maxDelay; 
    }
	
	/**
	 * Remove all elements in this queue. Similarly to the pop operation, it is constrained by the delay condition.
	 * @return Whether the clear succeeded or failed due to the delay condition.
	 */
	@SuppressWarnings("unchecked")
	public boolean clear() {
		if (getDelay() == 0) {
			this.array = (E[]) new Object[0];
			this.size = 0; 
			//clearFlag = true; 
			return true; 
		} else {
			return false; 
		}
    }
	
	/**
	 * Check whether the queue contains this element, according to the .equals() method of the element.
	 * @param elem The element to search for.
	 * @return Whether the element was found or not.
	 */
	public boolean contains(E elem) {
		for (int i = 0; i < this.size; i++) {
			E value = array[i];
			if (value.equals(elem)) {
				return true; 
			}
		} return false; 
    }

	public static void main(String[] args) {
		//Example 1 output 
		DelayedQueue<String> s = new MyQueue<String>(4);
		s.enqueue("first element");
		System.out.println(s.getDelay()); // 3
		s.enqueue("something else");
		System.out.println(s.getDelay()); //2 
		System.out.println("Dequeue: " + s.dequeue()); //return value is null
		s.enqueue("third");
		System.out.println(s.getDelay()); //1 
		s.enqueue("fourth");
		System.out.println(s.getDelay()); // 0 
		System.out.println("Dequeue: " + s.dequeue()); // first elem 
		s.enqueue("another one");
		System.out.println("Dequeue: " + s.dequeue()); // null 
		System.out.println(s.getDelay()); //3
		s.enqueue("2");
		System.out.println(s.getDelay()); //2
		s.enqueue("3");
		System.out.println(s.getDelay()); //1
		s.enqueue("4");
		System.out.println(s.getDelay()); //0
		System.out.println("Dequeue: " + s.dequeue()); // something else 
		System.out.println(s.getDelay()); // 0 
		System.out.println("Dequeue: " + s.dequeue()); // third 
		System.out.println(s.getDelay()); //0 
		System.out.println("Dequeue: " + s.dequeue()); // fourth 
		System.out.println("Dequeue: " + s.dequeue()); // another one 
		System.out.println("Dequeue: " + s.dequeue()); // 2 
		System.out.println("Dequeue: " + s.dequeue()); // 3 
	}


}
