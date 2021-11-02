import java.util.Arrays;

/**
 * ScapeGoat Tree class
 *
 * This class contains some of the basic code for implementing a ScapeGoat tree.
 * This version does not include any of the functionality for choosing which node
 * to scapegoat.  It includes only code for inserting a node, and the code for rebuilding
 * a subtree.
 */

public class SGTree {

    // Designates which child in a binary tree
    enum Child {LEFT, RIGHT}

    /**
     * TreeNode class.
     *
     * This class holds the data for a node in a binary tree.
     *
     * Note: we have made things public here to facilitate problem set grading/testing.
     * In general, making everything public like this is a bad idea!
     *
     */
    public static class TreeNode {
        int key;
        int weight;
        public TreeNode left = null;
        public TreeNode right = null;

        TreeNode(int k) {
            key = k;
            weight = 1;
        }
    }

    // Root of the binary tree
    public TreeNode root = null;

    /**
     * Count the number of nodes in the specified subtree
     *
     * @param node  the parent node, not to be counted
     * @param child the specified subtree (LEFT / RIGHT)
     * @return number of nodes
     */
    public int countNodes(TreeNode node, Child child) {
        // TODO: Implement this
        if (child == Child.LEFT) {
            return countNodes(node.left);
        } else {
            return countNodes(node.right);
        }
    }

    public int countNodes(TreeNode node) {
        if (node == null) {
            return 0;
        } else {
            return countNodes(node.left) + countNodes(node.right) + 1;
        }
    }

    /**
     * Build an array of nodes in the specified subtree.
     *
     * @param node  the parent node, not to be included in returned array
     * @param child the specified subtree
     * @return array of nodes
     */
    TreeNode[] enumerateNodes(TreeNode node, Child child) {
        // TODO: Implement this
        int size = countNodes(node, child);
        TreeNode[] inOrderArr = new TreeNode[size];
        if (child == Child.LEFT) {
            enumerateNodes(node.left, inOrderArr);
        } else {
            enumerateNodes(node.right, inOrderArr);
        }
        index = 0; // reset index
        return inOrderArr;
    }

    int index = 0; // global var

    void enumerateNodes(TreeNode node, TreeNode[] arr) {
        if (node != null) {
            enumerateNodes(node.left, arr);
            arr[index++] = node;
            enumerateNodes(node.right, arr);
        }
    }

    /**
     * Builds a tree from the list of nodes Returns the node that is the new root of the subtree
     *
     * @param nodeList ordered array of nodes
     * @return the new root node
     */
    TreeNode buildTree(TreeNode[] nodeList) {
        // TODO: Implement this
        return buildTree(nodeList, 0, nodeList.length);
    }

    TreeNode buildTree(TreeNode[] nodeList, int begin, int end) {
        if (begin < end) {
            int mid = begin + (end - begin) / 2;
            TreeNode newRoot = nodeList[mid];
            newRoot.left = buildTree(nodeList, begin, mid);
            newRoot.right = buildTree(nodeList, mid + 1, end);
            return newRoot;
        } else {
            return null;
        }
    }



    /**
    * Rebuild the specified subtree of a node.
    * 
    * @param node the part of the subtree to rebuild
    * @param child specifies which child is the root of the subtree to rebuild
    */
    public void rebuild(TreeNode node, Child child) {
        // Error checking: cannot rebuild null tree
        if (node == null) return;

        // First, retrieve a list of all the nodes of the subtree rooted at child
        TreeNode[] nodeList = enumerateNodes(node, child);
        // index = 0;

        // Then, build a new subtree from that list
        TreeNode newChild = buildTree(nodeList);

        // Finally, replace the specified child with the new subtree
        if (child == Child.LEFT) {
            node.left = newChild;
        } else if (child == Child.RIGHT) {
            node.right = newChild;
        }
    }

    /**
    * Insert a key into the tree
    *
    * @param key the key to insert
    */
    public void insert(int key) {
        if (root == null) {
            root = new TreeNode(key);
            return;
        }

        TreeNode node = root;

        while (true) {
            node.weight++;
            if (key <= node.key) {
                if (node.left == null) break;
                node = node.left;
            } else {
                if (node.right == null) break;
                node = node.right;
            }
        }

        if (key <= node.key) {
            //node.weight++;
            node.left = new TreeNode(key);
        } else {
            //node.weight++;
            node.right = new TreeNode(key);
        }
    }


    // Simple main function for debugging purposes
    public static void main(String[] args) {
        SGTree tree = new SGTree();
        for (int i = 0; i < 7; i++) {
            tree.insert(i);
        }
        //System.out.println(tree.countNodes(tree.root, Child.RIGHT));
        System.out.println(tree.root.weight);
        System.out.println(tree.root.right.weight);
        System.out.println(tree.root.right.right.weight);
//        TreeNode[] arr = tree.enumerateNodes(tree.root, Child.RIGHT);
//        for (int i = 0; i < arr.length; i++) {
//            System.out.println(arr[i].key);
//        }
//        tree.rebuild(tree.root, Child.RIGHT);
//        System.out.println("root = "+ tree.root.key);
//        System.out.println(tree.countNodes(tree.root, Child.LEFT));
//        System.out.println(tree.countNodes(tree.root, Child.RIGHT));
//        System.out.println("right child = " + tree.root.right.key);
//        System.out.println(tree.countNodes(tree.root.right, Child.LEFT));
//        System.out.println(tree.countNodes(tree.root.right, Child.RIGHT));
//        TreeNode[] arr2 = tree.enumerateNodes(tree.root, Child.RIGHT);
//        for (int i = 0; i < arr2.length; i++) {
//            System.out.println("key = " + arr[i].key);
//        }

    }
}
//        int begin = 0;
//        int end = nodeList.length;
//        if (begin < end) {
//            int mid = begin + (end - begin) / 2;
//            TreeNode newRoot = nodeList[mid];
//            newRoot.left = buildTree(nodeList, begin, mid);
//            newRoot.right = buildTree(nodeList, mid + 1, end);
//            return newRoot;
//        } else {
//            return null;
//        }

//    public void insert(int key) {
//
//        root = insert(root, key);
//
//        TreeNode u = root;
//        while (u != null) {
//            // check balance on left and right
//            if (checkBalance(u.left) && !checkBalance(u.right)) {
//                // right node is unbalanced;
//                System.out.println(u.key + " right unbalanced");
//                rebuild(u, Child.RIGHT);
//                fixWeights(u, Child.RIGHT);
//                u = u.left;
//            } else if (!checkBalance(u.left) && checkBalance(u.right)) {
//                // left node is unbalanced;
//                System.out.println(u.key + " left unbalanced");
//                rebuild(u, Child.LEFT);
//                fixWeights(u, Child.LEFT);
//                u = u.right;
//            } else if (!checkBalance(u.left) && checkBalance(u.right)) {
//                System.out.println(u.key + " both unbalanced");
//                rebuild(u, Child.LEFT);
//                fixWeights(u, Child.LEFT);
//                rebuild(u, Child.RIGHT);
//                fixWeights(u, Child.RIGHT);
//                break;
//            } else {
//                // already balanced
//                System.out.println(u.key + " both balanced");
//                break;
//            }
//        }
//    }



//    public TreeNode insert(TreeNode node, int key) {
//        if (node == null) {
//            return new TreeNode(key);
//        }
//
//        if (key < node.key) {
//            node.left = insert(node.left, key);
//        } else if (key > node.key) {
//            node.right = insert(node.right, key);
//        } else {
//            // duplicate node
//            return node;
//        }
//
//        // update weight after every insertion
//        node.weight = 1 + weight(node.left) + weight(node.right);
//        return node;
//    }

//        TreeNode parent = null;
//        while (u != null) {
//            if (!checkBalance(u) && u != root) {
//                // only re-balance when u is not root
//                if (u == parent.left) {
//                    // left child is the one unbalanced
//                    // System.out.println("rebuild at " + parent.left.key);
//                    rebuild(parent, Child.LEFT);
//                    fixWeights(parent, Child.LEFT);
//                } else {
//                    // right child is the one unbalanced
//                    // System.out.println("rebuild at " + parent.right.key);
//                    rebuild(parent, Child.RIGHT);
//                    fixWeights(parent, Child.RIGHT);
//                }
//                // tree is balanced;
//                break;
//            }
//
//            // if current node u is balanced, make u the parent
//            // check balance for u's children
//            parent = u;
//            if (key < u.key) {
//                // check in the direction where the new node is inserted
//                // (assuming all other nodes were balanced previously)
//                u = u.left;
//            } else if (key > u.key) {
//                u = u.right;
//            } else {
//                // duplicate node
//                break;
//            }
//        }