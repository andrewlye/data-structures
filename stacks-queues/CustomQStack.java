import java.util.*;
public class CustomQStack {
    private Queue<Integer> myQueue = new LinkedList<Integer>(); // queue in which we use to emulate a stack
    private int size; // keeps track of size

    public CustomQStack(){ // constructor if no prior queue is passed
        size = 0;
    }
    
    public CustomQStack(Queue<Integer> in){ // constructor if a prior queue is passed
        size = in.size();

        while (in.isEmpty() == false) // 'empty' the passed queue into myQueue
        {
            myQueue.add(in.poll());
        }
    }

    public int pop(){ // returns and removes most recently added element, i.e. remove in LIFO order
        if (size == 0) // error condition
            return -1;
        size--; // decrease size variable
        for (int i = 0; i < myQueue.size() - 1; i++){ // brings last element to the front by moving all elements before to the end
            int temp = myQueue.poll(); // remove first element in queue 
            myQueue.add(temp); // tack on the first element the the end of the queue, repeat until the original last element is at the front
        }

        return myQueue.poll(); // remove and return the new first element 
    }

    public void push(int n){ // adds an element to QStack
        myQueue.add(n);
        size++;
    }

    public boolean empty()
    {
        if (size == 0)
            return true;
        return false;
    }

    public String toString(){
        String ret = "{ ";
        for (int i = 0; i < size; i++)
        {
            int n = myQueue.poll();
            myQueue.add(n);
            ret = ret + n;
            if (i != size - 1)
            {
                ret = ret + ", ";
            }
        }

        ret = ret + " }";

        return ret;
    }

}
