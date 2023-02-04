import java.util.*;
public class CustomSQueue {
    private Stack<Integer> s1; // stack 1
    private Stack<Integer> s2; // stack 2

    public CustomSQueue() // empty constructor
    {
        s1 = new Stack<Integer>();
        s2 = new Stack<Integer>();
    }

    public CustomSQueue(Stack<Integer> s) // constructor which takes stack as a parameter
    {
        s1 = s; // sets s1 to the passed stack
        s2 = new Stack<Integer>(); // sets s2 to empty
    }

    public void add(int n) // to add an element, we simply push it to the top of s1
    {
        s1.push(n);
    }

    public int poll() // method to remove elements in FIFO order
    {
        if (s1.empty() && s2.empty()) // if both stacks are empty, error condition
            return -1;
        if (s2.empty()) // if s2 is empty, pushes elements from s1 to s2 so that the elements that are added first are at the top
        {
            while (!s1.empty())
            {
                s2.push(s1.pop());
            }
        }
        return s2.pop(); // returns and remove the top element of s2, which is always in FIFO order
    }

    public String toString()
    {
        String ret = "{ ";
        while (!s1.empty())
        {
            s2.push(s1.pop());

        }
        while (!s2.empty())
        {
            int temp = s2.pop();
            ret = ret + temp;
            s1.push(temp);
            if (s2.size() != 0)
                ret = ret + ", ";
        }
        ret = ret + " }";
        return ret;

    }
}
