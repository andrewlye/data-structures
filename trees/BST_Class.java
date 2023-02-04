public class BST_Class {
    private Node root;

    public BST_Class() { 
        root = null;
    }

    public void insert(int n) {
        root = add(n, root); // call to private recursive add method
    }

    private Node add(int n, Node r) {
        if (r == null) // if root node is empty
        {return new Node(n);}

        if (n < r.data) { // if element to be inserted is less than root
            r.left = add(n, r.left); // call add method on left subtree
        }
        if (n > r.data) { // else call on right subtree
            r.right = add(n, r.right);
        }

        return r; // if element equals node, return node (i.e. replace)
    }

    public void deleteKey(int key) {
        root = delete(key, root); // call to private recursive deletion method
    }

    private Node delete(int n, Node root) {
        if (root == null) // element to be deleted not found
        {return root;}
        //search for root to be deleted
        if (n < root.data)
            root.left = delete(n, root.left);
        else if (n > root.data)
            root.right = delete(n, root.right);
        else { // executed when root equals the root to be deleted
            //if root only has one children, swap 
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;
            root.data = findMin(root.right);
            root.right = delete(root.data, root.right);
            
        }
        return root;
    }

    public int minValue() {
        return findMin(root); // call to private recursive findMin method
    }

    private int findMin(Node root) {
        if (root == null) // if tree is empty, throw exception 
            return -1;
        else if (root.left == null) // if left node is null, then current root is the min
            return root.data;
        return findMin(root.left);
    }

    public int maxValue() { // findMax implementation, same as findMin
        return findMax(root);
    }

    private int findMax(Node root) {
        if (root == null)
            return -1;
        else if (root.right == null)
            return root.data;
        return findMax(root.right);

    }

    public boolean search(int n) {
        if (isEmpty())
            return false;
        else
            return contains(n, root); // call to recursive contains method
    }

    private boolean contains(int n, Node root) {
        if (root == null) // null, does not contain key
            return false;
        if (n < root.data)
            return contains(n, root.left);
        else if (n > root.data)
            return contains(n, root.right);
        else
            return true; // is n = root.data, return true
    }

    public void clear(){
        root = null;
    }

    public boolean isEmpty(){
        return root == null;
    }

    public void inorder() {
        if (isEmpty())
            System.out.println("Empty Tree!");
        else {
            inorder(root);
            System.out.println();
        }

    }

    private void inorder(Node root) { // left, root, right
        if (root != null) {
            inorder(root.left);
            System.out.print(root.data + " ");
            inorder(root.right);
        }

    }

    public void postorder() {
        if (isEmpty())
            System.out.println("Empty Tree!");
        else {
            postorder(root);
            System.out.println();
        }

    }

    private void postorder(Node root) { // left, right, root
        if (root != null) {
            postorder(root.left);
            postorder(root.right);
            System.out.print(root.data + " ");
        }

    }

    public void preorder() { 
        if (isEmpty())
            System.out.println("Empty Tree!");
        else {
            preorder(root);
            System.out.println();
        }

    }

    private void preorder(Node root) { // root, left, right
        if (root != null) {
            System.out.print(root.data + " ");
            preorder(root.left);
            preorder(root.right);
        }

    }
    
    private class Node {
        int data;
        Node left;
        Node right;

        public Node(int n) {
            data = n;
            left = null;
            right = null;
        }
    }
}
