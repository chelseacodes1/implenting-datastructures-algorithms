public class MyStack<E> implements DelayedStack<E> {
	private int capacity; 
	private E[] array;
	private E[] newArray;
    private int size;
    private int maxDelay;
	private int delayCondition; 
	private boolean pushFlag; 
	private boolean popFlag; 
	private boolean clearFlag; 

    // constructor 
	@SuppressWarnings("unchecked")
    public MyStack(int maxDelay) {
        this.maxDelay = maxDelay;
        this.capacity = 10; 
        this.size = 0; 
		this.array = (E[]) new Object[capacity];
		this.delayCondition = maxDelay;
    }

    /**
	 * Returns the number of elements currently in the stack.
	 * @return The size of the stack.
	 */
	public int size() {
        return this.size; 
    }

	/**
	 * Add an element to this stack.
	 * @param element The element to be added.
	 */
	@SuppressWarnings("unchecked")
	public void push(E element) {
		if (this.size == 0) {
			this.array = (E[]) new Object[capacity];
		}

        // if the array is at capacity (no space), have to resize it 
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
		pushFlag = true; 

		// A pop was before, push has now occurred which resets the max delay = pop flag should be set to false and only becomes true once the delay condition = 0 
		// reset delay conditions 
		if (popFlag) {
			this.delayCondition = this.maxDelay;
			popFlag = false; 
		}

		if (clearFlag) {
			this.delayCondition = this.maxDelay;
			clearFlag = false; 
		}

		this.delayCondition--;
    }
	
	/**
	 * Attempt to remove an element from this stack. 
	 * The first element to remove is the most recent one that was added.
	 * If the stack is empty, throw an IllegalStateException.
	 * If an element cannot be removed due to the delay condition, return null.
	 * 
	 * @return The element that was removed.
	 * @throws IllegalStateException if the stack is empty.
	 */
	public E pop() throws IllegalStateException {
		if (this.size == 0) {
			throw new IllegalStateException();
		} 

		if (getDelay() == 0) {
			//store the elem to be removed in a temporary variable, will need to return it 
			E temp = array[this.size - 1]; 
			array[this.size - 1] = null;
			this.size--; 
			popFlag = true;
			//pushFlag = false; 
			return temp;
		} else {
			return null;
		}
	}
	
	/**
	 * Return the element at the top of this stack, without removing it.
	 * 
	 * @return The element at the top of the stack.
	 * @throws IllegalStateException if the stack is empty.
	 */
	public E peek() throws IllegalStateException {
		if (this.size == 0) {
			throw new IllegalStateException();
		}
		return this.array[this.size - 1];
	}
	
	/**
	 * Returns how many more elements must be added before the stack will allow removals to commence.
	 * @return
	 */
	public int getDelay() {
		if (this.maxDelay <= 0 || this.delayCondition <= 0) {
			return 0; 
		} return this.delayCondition; 
    }
	
	/**
	 * Sets the maximum delay, which is the number of elements that must be added to the stack before any can be removed.
	 * Once a removal is allowed to occur, there is no limit on the number of removals.
	 * Modifying this value while the stack is in use does not change the current delay value. It only changes the next time the delay is reset.
	 * 
	 * @param d The number of elements that must be added before any can be removed.
	 */
	public void setMaximumDelay(int d) {
		//if (getDelay() == 0 && pushFlag) {
			this.maxDelay = d;
		//}
		//}
		//if (getDelay() == 0) {
		//	this.maxDelay = d; 
		//}
    }
	
	/**
	 * Get the maximum possible delay, which is the number of push operations that must occur before pop operations can occur, given that the first push operation has not yet occurred (ie. a pop operation just occurred).
	 * @return The maximum number of push operations before pop operations can occur.
	 */
	public int getMaximumDelay() {
		return this.maxDelay; 
    }
	
	/**
	 * Remove all elements in this stack. Similarly to the pop operation, it is constrained by the delay condition.
	 * @return Whether the clear succeeded or failed due to the delay condition.
	 */
	@SuppressWarnings("unchecked")
	public boolean clear() {
		if (getDelay() == 0) {
			this.array = (E[]) new Object[0];
			this.size = 0; 
			clearFlag = true; 
			return true; 
		} else {
			return false; 
		}
    }
	
	/**
	 * Check whether the stack contains this element, according to the .equals() method of the element.
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
		/*DelayedStack<String> s = new MyStack<String>(4);
		s.push("first element");
		System.out.println(s.getDelay()); // 3
		s.push("something else");
		System.out.println(s.getDelay()); //2 
		System.out.println("Pop: " + s.pop()); //return value is null
		s.push("third");
		System.out.println(s.getDelay()); //1 
		s.push("fourth");
		System.out.println(s.getDelay()); // 0 
		System.out.println("Pop: " + s.pop()); // fourth 
		s.push("another one");
		System.out.println("Pop: " + s.pop()); // null 
		System.out.println(s.getDelay()); //3
		s.push("2");
		System.out.println(s.getDelay()); //2
		s.push("3");
		System.out.println(s.getDelay()); //1
		s.push("4");
		System.out.println(s.getDelay()); //0
		System.out.println("Pop: " + s.pop()); // 4
		System.out.println(s.getDelay()); //0 --> 4
		System.out.println("Pop: " + s.pop()); // 3
		System.out.println(s.getDelay()); //0 --> 4 
		System.out.println("Pop: " + s.pop()); // 2 
		System.out.println("Pop: " + s.pop()); // another one
		System.out.println("Pop: " + s.pop()); // third
		System.out.println("Pop: " + s.pop()); // something else 
		//System.out.println("Pop: " + s.pop()); // first elem 
		//System.out.println(s.clear());
		//System.out.println(s.size()); */

		//Testing clear
		DelayedStack<Integer> s = new MyStack<Integer>(4);
		s.push(1);
		s.push(2);
		s.push(3);
		System.out.println(s.clear()); // false
		s.push(4);
		System.out.println(s.size()); // 4
		System.out.println(s.clear()); // true
		System.out.println(s.size()); // 0
		s.push(5);
		s.push(6);
		s.push(7);
		System.out.println(s.size()); // 3
		System.out.println(s.clear()); // false
		s.push(8);
		System.out.println(s.size()); // 4
		System.out.println(s.clear()); // true
		s.push(9); 
		System.out.println(s.size()); // 2




		/*DelayedStack<String> s = new MyStack<String>(4);
		s.push("first element");
		System.out.println("Get delay: " + s.getDelay()); // 3
		s.push("something else");
		System.out.println("Get delay: " + s.getDelay()); //2 
		System.out.println("Pop: " + s.pop()); //return value is null
		s.push("third");
		System.out.println("Get delay: " + s.getDelay()); //1 
		s.push("fourth");
		System.out.println("Get delay: " + s.getDelay()); // 0 
		System.out.println("Pop: " + s.pop()); // fourth 
		s.push("another one");

		System.out.println("Pop: " + s.pop()); // null 
		System.out.println("Get delay: " + s.getDelay()); //3
		s.push("2");
		System.out.println("Get delay: " + s.getDelay()); //2
		s.push("3");
		System.out.println("Get delay: " + s.getDelay()); //1
		s.push("4");
		System.out.println("Get delay: " + s.getDelay()); //0
		System.out.println("Pop: " + s.pop()); // 4
		System.out.println("Get delay: " + s.getDelay()); //0 --> 4
		System.out.println("Pop: " + s.pop()); // 3
		System.out.println("Get delay: " + s.getDelay()); //0 --> 4 
		System.out.println("Pop: " + s.pop()); // 2 
		System.out.println("Pop: " + s.pop()); // another one
		System.out.println("Pop: " + s.pop()); // third
		System.out.println("Pop: " + s.pop()); // something else 
		*/




		//System.out.println(s.peek()); // throw exception? 

		/*s.push("1");
		s.push("2");
		s.push("3");
		System.out.println(s.clear()); // false 
		System.out.println(s.peek()); // 3
		s.push("4");
		System.out.println(s.peek()); // 4
		System.out.println(s.clear()); // true
		System.out.println(s.size()); // 0*/ 



		
		/*testing size, getDelay, contains 
		s.push("1");
		System.out.println(s.size()); // 1
		System.out.println(s.getDelay()); // 3
		s.push("2");
		s.push("hello");
		System.out.println(s.size()); // 3
		System.out.println(s.getDelay()); // 1
		s.push("hi"); 
		System.out.println(s.size()); // 4
		System.out.println(s.getDelay()); // 0
		System.out.println(s.contains("hello")); // true
		System.out.println(s.contains("hi")); // false 
		*/
	}

}
