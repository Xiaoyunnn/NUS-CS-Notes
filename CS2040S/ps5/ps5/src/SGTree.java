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
            // every new node will be initialised as a leaf node with weight 1
            weight = 1;
        }
    }

    // Root of the binary tree
    public TreeNode root = null;


    /**
     * Count the number of nodes in the specified subtree
     *
     * @param node  the parent node, not to be counted
     * @param child the specified subtree
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
     * Determines if a node is balanced.  If the node is balanced, this should return true.  Otherwise, it should return false.
     * A node is unbalanced if either of its children has weight greater than 2/3 of its weight.
     *
     * @param node a node to check balance on
     * @return true if the node is balanced, false otherwise
     */
    public boolean checkBalance(TreeNode node) {
        // TODO: Implement this
        if (node == null) {
            return true;
        } else {
            return weight(node.left) <= (2.0/3.0 * weight(node)) && weight(node.right) <= (2.0/3.0 * weight(node));
        }
        // return countNodes(node, Child.LEFT) <= (2.0/3.0 * countNodes(node)) && countNodes(node, Child.RIGHT) <= (2.0 / 3.0 * countNodes(node));
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

        // Then, build a new subtree from that list
        TreeNode newChild = buildTree(nodeList); // new root
        //newChild.weight = 1 + weight(newChild.left) + weight(newChild.right);

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
            node.left = new TreeNode(key);
        } else {
            node.right = new TreeNode(key);
        }

        TreeNode u = root;

        while (u != null) {
            if (key <= u.key) {
                // check in the direction where the new node is inserted
                // (assuming all other nodes were balanced previously)
                if (!checkBalance(u.left)) {
                    // System.out.println("rebuild at " + u.left.key);
                    rebuild(u, Child.LEFT);
                    fixWeights(u, Child.LEFT);
                    return;
                } else {
                    u = u.left;
                }
            } else {
                if (!checkBalance(u.right)) {
                    // System.out.println("rebuild at " + u.right.key);
                    rebuild(u, Child.RIGHT);
                    fixWeights(u, Child.RIGHT);
                    return;
                } else {
                    u = u.right;
                }
            }
        }
    }

    int weight(TreeNode node) {
        if (node == null) {
            return 0;
        } else {
            return node.weight;
        }
    }

    void fixWeights(TreeNode node, Child child) {
        if (child == Child.LEFT) {
            node.left.weight = fixWeights(node.left);
        } else {
            node.right.weight = fixWeights(node.right);
        }
    }

    int fixWeights(TreeNode node) {
        if (node == null) {
            return 0;
        } else {
            if (node.left != null && node.right != null) {
                node.left.weight = fixWeights(node.left);
                node.right.weight = fixWeights(node.right);
                return node.left.weight + node.right.weight + 1;
            } else {
                return fixWeights(node.left) + fixWeights(node.right) + 1;
            }
        }
    }

    // Simple main function for debugging purposes
    public static void main(String[] args) {
        SGTree tree = new SGTree();
        for (int i = 0; i < 20; i++) {
            System.out.println( i + " inserted ********");
            tree.insert(i);

        }

        // tree.rebuild(tree.root, Child.RIGHT);
        System.out.println(tree.root.right.key + " key");
        System.out.println(tree.root.right.left.weight);
        System.out.println(tree.root.right.left.key + " key");
        System.out.println(tree.root.right.right.weight);
        System.out.println(tree.root.right.right.key + " key");
        //System.out.println(tree.countNodes(tree.root.right.right));
        System.out.println(tree.root.right.right.right.weight);
        System.out.println(tree.root.right.right.right.key);

    }
}
