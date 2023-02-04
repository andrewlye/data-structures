public class BSTExample {
    public static void main(String[] args) {
        BST_Class bst = new BST_Class(); // create a BST object
        bst.insert(45); // insert data
        bst.insert(10);
        bst.insert(7);
        bst.insert(12);
        bst.insert(90);
        bst.insert(50);
        System.out.println("The created BST (inorder): ");
        bst.inorder();
        System.out.println("Preorder traversal: ");
        bst.preorder();
        System.out.println("Postorder traversal: ");
        bst.postorder();

        bst.deleteKey(12); // delete node
        System.out.println("The BST after deletion of 12 (inorder): ");
        bst.inorder();
        bst.deleteKey(90); // delete node
        System.out.println("The BST after deletion of 90 (inorder): ");
        bst.inorder();
        bst.deleteKey(45); // delete leaf node
        System.out.println("The BST after deletion of 45 (inorder): ");
        bst.inorder();

        System.out.println("Key 50 found in BST: " + bst.search(50)); // contains 50
        System.out.println("Key 12 found in BST: " + bst.search(12)); // contains 12
    }
}
