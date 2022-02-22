/**
 * Implement this interface in a separate file called "MyQueue.java"
 * You are not allowed to use any imports from the java standard libraries for this task.
 * The data structure must be completely of your own creation.
 */
public interface DelayedQueue<E> {

	/**
	 * Returns the number of elements currently in the queue.
	 * @return The size of the queue.
	 */
	public int size();
	
	/**
	 * Add an element to this queue.
	 * @param element The element to be added.
	 */
	public void enqueue(E element);
	
	/**
	 * Attempt to remove an element from this queue. 
	 * The first element to remove is the most recent one that was added.
	 * If the enqueue is empty, throw an IllegalStateException.
	 * If an element cannot be removed due to the delay condition, return null.
	 * 
	 * @return The element that was removed.
	 * @throws IllegalStateException if the queue is empty.
	 */
	public E dequeue() throws IllegalStateException;
	
	/**
	 * Return the element at the front of this queue, without removing it.
	 * 
	 * @return The element at the front of the queue.
	 * @throws IllegalStateException if the queue is empty.
	 */
	public E peek() throws IllegalStateException;
	
	/**
	 * Returns how many more elements must be added before the queue will allow removals to commence.
	 * @return
	 */
	public int getDelay();
	
	/**
	 * Sets the maximum delay, which is the number of elements that must be added to the queue before any can be removed.
	 * Once a removal is allowed to occur, there is no limit on the number of removals.
	 * Modifying this value while the queue is in use does not change the current delay value. It only changes the next time the delay is reset.
	 * 
	 * @param d The number of elements that must be added before any can be removed.
	 */
	public void setMaximumDelay(int d);
	
	/**
	 * Get the maximum possible delay, which is the number of push operations that must occur before pop operations can occur, given that the first push operation has not yet occurred (ie. a pop operation just occurred).
	 * @return The maximum number of push operations before pop operations can occur.
	 */
	public int getMaximumDelay();
	
	/**
	 * Remove all elements in this queue. Similarly to the pop operation, it is constrained by the delay condition.
	 * @return Whether the clear succeeded or failed due to the delay condition.
	 */
	public boolean clear();
	
	/**
	 * Check whether the queue contains this element, according to the .equals() method of the element.
	 * @param elem The element to search for.
	 * @return Whether the element was found or not.
	 */
	public boolean contains(E elem);
}
