public class wordCountDemo {
    public static void main(String[] args) {
        System.out.println("String 1");
        System.out.println("---------------");
        HashTableSeparateChaining.wordCount("Injustice anywhere is a threat to justice everywhere. We are caught in an inescapable network of mutuality, tied in a single garment of destiny. Whatever affects one directly, affects all indirectly. Never again can we afford to live with the narrow, provincial 'outside agitator' idea. Anyone who lives inside the United States can never be considered an outsider anywhere within its bounds.");
        System.out.println();
        System.out.println("String 2");
        System.out.println("---------------");
        HashTableSeparateChaining.wordCount("How much wood would a woodchuck chuck if a woodchuck could chuck wood? He would chuck, he would, as much as he could, and chuck as much wood as a woodchuck would if a woodchuck could chuck wood.");
    }
}
