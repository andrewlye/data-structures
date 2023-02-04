import java.util.*;
public class Fibonacci {

    private ArrayList<Integer> fibArray;
    
    public Fibonacci(int size) // constructor
    {
        fibArray = new ArrayList<Integer>();
        for (int i = 0; i < size; i++)
        {
            fibArray.add(fibIteration(i+1)); // fills ArrayList using iterative fib method
        }

    }

    public void add(int num) // adds a number to the end
    {
        fibArray.add(num);
    }

    public void add(int index, int num) // adds a number to a specified index 
    {
        fibArray.add(index, num);
    }

    public void remove(int index) // removes the number at the specified index
    {
        fibArray.remove(index);
    }

    public boolean ifContains(int n) // returns true if n is in the list, false otherwise
    {
        for(int i = 0; i < fibArray.size(); i++)
        {
            if (fibArray.get(i) == n)
                return true;
        }
        return false;
    }

    public int grab() // returns an element from a random index in the list
    {
        int index = (int) (Math.random() * fibArray.size());
        return fibArray.get(index);
    }

    public int numItems() // returns the number of unique items in the list
    {
        int numItems = 0;
        ArrayList<Integer> uniqueList = new ArrayList<Integer>(); // empty unique list generated
        for (int i = 0; i < fibArray.size(); i++)
        {
            if (uniqueList.contains(fibArray.get(i))) {}
            else
            {
                uniqueList.add(fibArray.get(i));
            }
        }

        return uniqueList.size(); // returns size of unique list

    }

    public String toString() // toString overwrite to print in readable format
    {
        String ret = "{ ";
        for (int i = 0; i < fibArray.size(); i++)
        {
            if (i != fibArray.size() - 1)
                ret = ret + fibArray.get(i) + ", ";
            else
                ret = ret + fibArray.get(i);
        }
        ret = ret + (" }");

        return ret;
    }


    /* Recursion isn't a great way to tackle our fibonacci problem, because as n increases, the number of recursive method
     * calls grows exponentially. This is extremely inefficient. 
     * The complexity of our recursive fibonacci method can be expressed as O(2^n)
     */

    public static int fibRecursion(int n)
    {
        if (n == 1 || n == 2)
            return 1;
        else   
            return fibRecursion(n - 1) + fibRecursion(n - 2);
    }

    /* Iteration is a much better way to calculate fibonacci numbers. In this implementation, the algorithm computes a given
     * fibonacci number by only keeping track of the two most recent numbers and adding them together. This is much more
     * efficient, and can handle much larger inputs of n than our recursive method.
     * The complexity of our iterative fibonacci method can be expressed as O(n)
    */

    public static int fibIteration(int n)
    {
        if (n <= 0)
            return -1;
        int back1 = 1;
        int back2 = 1;
        int fib = 1;
        for (int i = 3; i <= n; i++)
        {
            fib = back1 + back2;
            back2 = back1;
            back1 = fib;
        }

        return fib;
    }
    public static void main(String[] args) // main method with some example code
    {
        System.out.println("The 12th fibonacci number is " + fibIteration(12));
        Fibonacci fibArray = new Fibonacci(10); // creates new array filled with n fibonacci numbers
        System.out.println(fibArray);
        fibArray.add(0, 6); // inserts 6 at the beginning of the list
        System.out.println(fibArray);
        fibArray.add(3, 17); // inserts 17 at the index 3 of the list
        System.out.println(fibArray);
        fibArray.add(11); // inserts 11 at the end of the list
        System.out.println(fibArray);
        fibArray.remove(11); // removes the element at index 11
        System.out.println(fibArray);
        System.out.println("Unique items: " + fibArray.numItems()); // prints number of unique items
        System.out.println("Random element: " + fibArray.grab()); // returns a random element from the list

        
    }
}   


