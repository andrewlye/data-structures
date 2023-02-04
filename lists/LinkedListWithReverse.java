public class LinkedListWithReverse{ // create a simple linked list class so we have access to pointers
    private Node head;
    private int count;

    public LinkedListWithReverse() // constructor
    {
        count = 0;
    }

    public void reverse() // reverse method
    {
        Node prev = null;
        Node temp = null;

        while (head != null)
        {
            temp = head.getNext(); // before changing the next of head, store in temp 
            head.setNext(prev); // change next of head
            prev = head; // move previous and head one step forward
            head = temp;
            
        }

        head = prev;
    }

    public void add(int x) // add method
    {
        if (count == 0)
        {
            head = new Node(x);
            count++;
        }
        else
        {
            Node n = head;
            while (n != null)
            {
                if(n.getNext() == null) 
                {
                    n.setNext(new Node(x));
                    count++;
                    break;
                }
                n = n.getNext();
            }
        }
    }

    public String toString() { // toString
        
        String ret = "{ ";
		Node n = head;		
		while(n != null) {
			ret = ret + n;
			if(n.getNext()!=null) 
				ret = ret + ", ";
			n = n.getNext();
		}
		ret = ret + " }";

        return ret;
	}

    private class Node{ // private node class
        private int num;
        private Node next;

        public Node(int n)
        {
            num = n;
        }

        public Node getNext() {				
			return next;
		}
		
		public void setNext(Node next) {	
			this.next = next;
		}
		
		public String toString() {			
			return "" + num;
		}
    }

}
