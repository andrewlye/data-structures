public class HashTableSeparateChaining {
    private Entry[] theWords; // hash table of words
    private int tableSize; // size of hash table
    private static final int DEFAULT_TABLE_SIZE = 5; // default size of hash table
    private int items; // current number of items in the table
    private final double maxLoad = 0.75; // max load factor
    private double loadFactor; // current load factor

    public HashTableSeparateChaining() { // default constructor
        this(DEFAULT_TABLE_SIZE);
    }

    public HashTableSeparateChaining(int size) { // special constructor with size parameter
        tableSize = size;
        theWords = new Entry[nextPrime(size)];  // ensures prime table size
        items = 0;
        loadFactor = 0;
    }

    public static void wordCount(String str) { // static word count method
        HashTableSeparateChaining table = new HashTableSeparateChaining();
        table.add(str);
        table.printWithoutIndex();
    }

    public void add(String str) { // public add method
        if (str == null)
        {System.out.println("Input cannot be null!"); return;} // error handling for null input
        String[] arr = str.split("\\P{Alpha}+"); // create array of words
        for(int i = 0; i < arr.length; i++)
        {
            Entry temp = new Entry(arr[i].toLowerCase()); // pass words into table
            add(temp);
        }
    }

    private void add(Entry in) { // private add helper method
        int index = hash(in.word);
        if (theWords[index] == null) { // if entry is new and index is empty, add and increase items
            theWords[index] = in;
            items++;
            checkLoad();
            return;
        }
        else { // else check through the list of words at the index
            Entry trav = theWords[index];
            if (in.word.equals(trav.word))
            { trav.numOccur++; return; } // preliminary check if entry equals the first item in the list at the index
            while(trav.next != null) { // loop through the rest of the list
                trav = trav.next;
                if (in.word.equals(trav.word)) { // if entry already exists, keep the same number of items and update occurence
                    trav.numOccur++;
                    return;
                }
            }
            trav.next = in; // if entry is new, add entry and increase items
            items++;
            checkLoad(); // only check load factor after actual new items are added
        }
    }

    private void checkLoad() { // checks/updates load factor
        loadFactor = ((double) items) / tableSize;
        if (loadFactor >= maxLoad) 
            rehash(); // rehashes table if load factor reaches predetermined max load factor
    }

    private void rehash() { // rehash method
        Entry[] oldWords = theWords; // 
        items = 0;
        tableSize = nextPrime(2 * tableSize); // set the new table size to the next prime number at least double the current table size
        theWords = new Entry[tableSize];
        for (int i = 0; i < oldWords.length; i++) { // insert old values into new table
            if (oldWords[i] != null) {
                Entry trav = oldWords[i];
                while (trav != null) {
                    for (int j = 0; j < trav.numOccur; j++)
                        add(trav.word); // add old words into the table with their new hash codes
                    trav = trav.next;
                }
            }
        }
    }

    private int hash(String str) { // hash function
        return Math.abs(str.hashCode()) % tableSize;
    }

    private static int nextPrime(int n) { // internal next prime method
        if ((n % 2) == 0)
            n++;
        while (!isPrime(n))
            n = n + 2;
        return n;
    }

    private static boolean isPrime(int n) {
        if (n == 2 || n == 3)
            return true;
        if (n == 1 || n % 2 == 0)
            return false;
        for (int i = 3; i * i <= n; i = i + 2) {
            if (n % i == 0)
                return false;
        }
        
        return true;
    }

    public void print() { // print method
        for(int i = 0; i < theWords.length; i++) {
            System.out.println("Index " + i);
            if (theWords[i] != null) {
                Entry trav = theWords[i];
                while (trav != null) {
                    System.out.println("   " + trav.word + ": " + trav.numOccur);
                    trav= trav.next;
                }
            }
        }
        System.out.println(loadFactor);
        System.out.println(items);
        System.out.println(tableSize);
    }

    public void printWithoutIndex() { // print method without index labels, used in wordCount
        for(int i = 0; i < theWords.length; i++) {
            if (theWords[i] != null) {
                Entry trav = theWords[i];
                while (trav != null) {
                    System.out.println(trav.word + ": " + trav.numOccur);
                    trav= trav.next;
                }
            }
        }
    }

    private class Entry { // inner entry class
        private String word;
        private int numOccur;
        private Entry next;

        public Entry(String in) {
            word = in;
            next = null;
            numOccur = 1;
        }

    }
}