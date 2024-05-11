/**
 * @author
 * Yugui Ke
 * 113778667
 * Recitation 3
 * yugui.ke@stonybrook.edu
 */

public class DirectoryNode {
    private String name;
    private DirectoryNode left;
    private DirectoryNode middle;
    private DirectoryNode right;

    private boolean isFile;

    // Extra credit member variables:
    private DirectoryNode[] nodes;

    /**
     * Empty constructor for instantiating this class.
     */
    public DirectoryNode() {}

    /**
     * Extra credit: <br>
     * Constructor for creating Directory node with any number of child.
     * @param numOfChild <i>int</i> representing the number of child this
     *                   node will have.
     */
    public DirectoryNode(int numOfChild) {
        nodes = new DirectoryNode[numOfChild];
    }

    /**
     * Gets the name of this <i>DirectoryNode</i>.
     * @return <i>String</i> containing the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this <i>DirectoryNode</i>.
     * @param name <i>String</i> containing the new name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the left child of this <i>DirectoryNode</i>.
     * @return <i>DirectoryNode</i> representing the left child.
     */
    public DirectoryNode getLeft() {
        return left;
    }

    /**
     * Gets the middle child of this <i>DirectoryNode</i>.
     * @return <i>DirectoryNode</i> representing the middle child.
     */
    public DirectoryNode getMiddle() {
        return middle;
    }

    /**
     * Gets the right child of this <i>DirectoryNode</i>.
     * @return <i>DirectoryNode</i> representing the right child.
     */
    public DirectoryNode getRight() {
        return right;
    }

    /**
     * Adds a child to this <i>DirectoryNode</i>.<br>
     * <i>Pre-conditions:</i><br>
     * <ul>
     *     <li>This node is not a file.</li>
     *     <li>There is at least one empty position in the children of this
     *     node.</li>
     * </ul>
     * <i>Post-conditions:</i><br>
     * <ul><li>newChild has been added as a child of this node.</li></ul>
     * @param node <i>DirectoryNode</i> representing a new child.
     * @throws NotADirectoryException thrown when <i>DirectoryNode</i> is a
     * file.
     * @throws FullDirectoryException thrown when all children of this
     * <i>DirectoryNode</i> is filled.
     */
    public void addChild(DirectoryNode node) throws
      NotADirectoryException, FullDirectoryException {
        if (isFile) {
            throw new NotADirectoryException();
        }

        // Original implementation for ternary binary tree.

//        if (left == null) {
//            left = node;
//        } else if (middle == null) {
//            middle = node;
//        } else if (right == null) {
//            right = node;
//        } else {
//            throw new FullDirectoryException("ERROR: Present directory is " +
//              "full.");
//        }

        // Implementation for extra credit

        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] == null) {
                nodes[i] = node;
                if (i == 0) {
                    left = node;
                } else if (i == nodes.length-1) {
                    right = node;
                } else if (i == nodes.length / 2) {
                    middle = node;
                }
                return;
            }
        }
        throw new FullDirectoryException("ERROR: Present directory is full.");
    }

    // Additional methods

    /**
     * Tells you if this <i>DirectoryNode</i> is a file.
     * @return <i>boolean</i> isFile.
     */
    public boolean isFile() {
        return isFile;
    }

    /**
     * Determines if this <i>DirectoryNode</i> is a file.
     * @param isFile <i>boolean</i> representing if this node is a file.
     */
    public void setIsFile(boolean isFile) {
        this.isFile = isFile;
    }

    // Methods for extra credit

    /**
     * Extra credit method: <br>
     * Get a child of this node by its index.
     * @param index <i>int</i> representing the index of wanted child.
     * @return returns the child.
     */
    public DirectoryNode getChild(int index) {
        if (!isFile) {
            return nodes[index];
        } else {
            return null;
        }
    }

    /**
     * Extra credit method: <br>
     * Removes a child form this node by its index. Used in moveFile.
     * @param index <i>int</i> representing the index of child to be removed.
     */
    public void removeChild(int index) {
        nodes[index] = null;
    }
}
