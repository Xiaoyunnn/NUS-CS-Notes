import java.util.ArrayList;
import java.util.Arrays;

public class Trie {

    // Wildcards
    final char WILDCARD = '.';
    TrieNode root;


    private class TrieNode {
        // TODO: Create your TrieNode class here.
	   // int[] present_chars = new int[62];
	   TrieNode[] present_chars = new TrieNode[62];
	   char key;
	   boolean isEnd;

	   public TrieNode() {
	       this.isEnd = false;
	       for (int i = 0; i < 62; i++) {
	           present_chars[i] = null;
           }
       }

	   public TrieNode(char key) {
	       this();
	       this.key = key;
       }
    }

    
    public Trie() {
        // TODO: Initialise a trie class here.
	   root = new TrieNode();
    }

    // inserts string s into the Trie
    void insert(String s) {
        // TODO
        TrieNode curr = root;
        int len = s.length();
        for (int i = 0; i < len; i++) {

            char x = s.charAt(i);
            int index = findIndex(x);

            if (curr.present_chars[index] == null) {
                // current index is not initialised yet
                TrieNode child = new TrieNode(x);
                curr.present_chars[index] = child;
                curr = child;
            } else {
                curr = curr.present_chars[index];
            }
        }
        curr.isEnd = true;
    }

    // checks whether string s exists inside the Trie or not
    boolean contains(String s) {
        // TODO
        TrieNode curr = root;
        for (int i = 0; i < s.length(); i++) {
            char x = s.charAt(i);
            int index = findIndex(x);

            if (curr.present_chars[index] == null) {
                return false;
            } else {
                curr = curr.present_chars[index];
            }
        }
        return curr.isEnd;
    }

    int findIndex(char character) {
        if (character <= 57) {
            // numbers 0 to 9
            // 48: int rep of '0'
            return character - 48;
        } else if (character <= 90) {
            // capital letters A to Z
            // 55 = 65 (int rep of 'A') - 10 (starting index)
            return character - 55;
        } else {
            // smaller letter a to z
            // 61 = 97 (int rep of 'a') - 36 (starting index)
            return character - 61;
        }
    }

    // Search for string with prefix matching the specified pattern sorted by lexicographical order.
    // Return results in the specified ArrayList.
    // Only return at most the first limit results.
    void prefixSearch(String s, ArrayList<String> results, int limit) {
        // TODO
        StringBuilder str = new StringBuilder("");
        continueSearch(root, s, str, results, limit, 0);
    }

    void printAllStrings(TrieNode node, StringBuilder prefix, ArrayList<String> results, int limit) {
        if (node.isEnd && results.size() < limit) {
            results.add(prefix.toString());
        }
        for (int i = 0; i < 62; i++) {
            if (node.present_chars[i] != null) {
                StringBuilder newStr = new StringBuilder(prefix.append(node.present_chars[i].key));
                prefix.deleteCharAt(prefix.length() - 1);
                printAllStrings(node.present_chars[i], newStr, results, limit);
            }
        }
    }

    void continueSearch(TrieNode curr, String s, StringBuilder currentStr, ArrayList<String> results, int limit, int idx) {
        if (s.length() == idx) {
            printAllStrings(curr, currentStr, results, limit);
        } else {
            char currentChar = s.charAt(idx);
            if (currentChar != WILDCARD) {
                int index = findIndex(currentChar);
                if (curr.present_chars[index] != null) {
                    StringBuilder newString = new StringBuilder(currentStr.append(curr.present_chars[index].key));
                    currentStr.deleteCharAt(currentStr.length() - 1);
                    continueSearch(curr.present_chars[index], s, newString, results, limit, idx + 1);
                }
            } else {
                for (int i = 0; i < 62; i++) {
                    if (curr.present_chars[i] != null) {
                        // search through all the available children
                        StringBuilder newString = new StringBuilder(currentStr.append(curr.present_chars[i].key));
                        currentStr.deleteCharAt(currentStr.length() - 1);
                        // currentStr + curr.present_chars[i].key;
                        continueSearch(curr.present_chars[i], s, newString, results, limit, idx + 1);
                    }
                }
            }
        }
    }


    // Simplifies function call by initializing an empty array to store the results.
    // PLEASE DO NOT CHANGE the implementation for this function as it will be used
    // to run the test cases.
    String[] prefixSearch(String s, int limit) {
        ArrayList<String> results = new ArrayList<String>();
        prefixSearch(s, results, limit);
        return results.toArray(new String[0]);
    }


    public static void main(String[] args) {
        Trie t = new Trie();
        t.insert("papa");
        t.insert("peeepa");
        t.insert("peggy");
        t.insert("peeggy");
        t.insert("pepe");
        t.insert("peggie");
        t.insert("peter");
        t.insert("piper");
        t.insert("picked");
        t.insert("a");
        t.insert("peck");
        t.insert("of");
        t.insert("pickled");
        t.insert("peppers");
        t.insert("pepppito");
        t.insert("pepi");
        t.insert("pik");
//        System.out.println(t.root.present_chars[0].key);
//        System.out.println(t.root.present_chars[0].present_chars[0].key);
//        System.out.println(t.root.present_chars[36].key);
//        System.out.println(t.contains("a"));
//        System.out.println(t.contains("pe99107"));


        String[] result1 = t.prefixSearch("pe", 5);

        System.out.println(Arrays.toString(result1));
        String[] result2 = t.prefixSearch("", 10);
        System.out.println(Arrays.toString(result2));
        String[] result3 = t.prefixSearch("p.p", 10);
        System.out.println(Arrays.toString(result3));

        // result1 should be:
        // ["peck", "pepi", "peppers", "pepppito", "peter"]
        // result2 should contain the same elements with result1 but may be ordered arbitrarily
    }
}
//        for (int num = limit; num > 0; num--) {
//
//                while (!u.isEnd) {
//                for (int i = 0; i < 62; i++) {
//        if (u.present_chars[i] != null) {
//        u = u.present_chars[i];
//        u.visited = true;
//        newWord += (char) u.key;
//        }
//        }
//        }
//        if (!u.visited) {
//        System.out.println(newWord);
//        results.add(newWord);
//        }
//        u = curr;
//        newWord = word;
//
//
//        }
//                char nextChar = s.charAt(1);
//                int nextIndex = findIndex(nextChar);
//
//                for (int i = 0; i < 62; i++) {
//                    if (curr.present_chars[i] != null) {
////                        curr = curr.present_chars[i];
////                        currentStr += curr.key;
////                        continueSearch(curr, s.substring(1), currentStr, results, limit);
//
//                        if (curr.present_chars[i].present_chars[nextIndex] != null) {
//                            System.out.println(i);
//                            currentStr = currentStr + curr.present_chars[i].key + nextChar;
//                            curr = curr.present_chars[i].present_chars[nextIndex];
//                            System.out.println("curr key " + curr.key);
//                            continueSearch(curr, s.substring(2), currentStr, results, limit);
//                        }
//                    }
//                }
//        TrieNode curr = root;
//        String word = "";
//
//        for (int i = 0; i < s.length(); i++) {
//            int index;
//            char currChar = s.charAt(i);
//
//            if (currChar != WILDCARD) {
//                index = findIndex(currChar);
//
//                if (curr.present_chars[index] != null) {
//                    curr = curr.present_chars[index];
//                    word += (char) curr.key;
//                }
//            } else if (i == s.length() - 1) {
//                // currChar == WILDCARD && '.' is the last character
//                // current character is '.'
////                if (i < s.length() - 1) {
////                    // look for the string that matches the next char
////                    char nextChar = s.charAt(i + 1);
////                    int nextIndex = findIndex(nextChar);
////
////                    // for
////
////                }
//                printAllStrings(curr, word, results, limit);
//            } else {
//
//            }
//        }
//        printAllStrings(curr, word, results, limit);