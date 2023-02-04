import java.util.LinkedList;
import java.util.Stack;

public class CustomExampleCode {
    public static void main(String[] args)
    {

        System.out.println("Testing QStack"); // tests for QStack
        LinkedList<Integer> ll = new LinkedList<Integer>(); // create linked list to pass to QStack
        ll.add(0);
        ll.add(1);
        ll.add(2);
        ll.add(3);
        ll.add(4);
        ll.add(5);
        CustomQStack QStack = new CustomQStack(ll);
        System.out.print("Our QStack: ");
        System.out.println(QStack);
        System.out.print("Element popped: ");
        System.out.println(QStack.pop()); // remove 5 from top (end) of stack
        QStack.push(7); // push 7 to top (end) of stack
        System.out.print("Our QStack after a pop and push: ");
        System.out.println(QStack);
        System.out.print("Empty: ");
        System.out.println(QStack.empty());
        System.out.println();

        System.out.println("Testing SQueue"); // tests for SQueue
        Stack<Integer> stackIn = new Stack<Integer>(); // create new stack to pass to SQueue
        stackIn.push(1);
        stackIn.push(2);
        stackIn.push(3);
        CustomSQueue SQueue = new CustomSQueue(stackIn);
        System.out.print("Our SQueue: ");
        System.out.println(SQueue);
        SQueue.add(4);
        SQueue.add(5);
        SQueue.add(6);
        System.out.print("Our SQueue after elements are added: ");
        System.out.println(SQueue);
        System.out.print("Element to remove (poll): ");
        System.out.println(SQueue.poll());
        System.out.print("Our SQueue after a poll: "); // removes the front of our queue
        System.out.println(SQueue);
        


    }
}
