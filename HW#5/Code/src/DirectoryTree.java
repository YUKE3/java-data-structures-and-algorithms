/**
 * @author
 * Yugui Ke
 * 113778667
 * Recitation 3
 * yugui.ke@stonybrook.edu
 */

public class DirectoryTree {
    private DirectoryNode root;
    private DirectoryNode cursor;

    // Extra credit member variables:
    private final int CHILD_PER_NODE = 10;

    /**
     * Constructor to create <i>DirectoryTree</i> object. <br>
     * Creates a new <i>DirectoryNode</i> called root.
     */
    public DirectoryTree() {
        root = new DirectoryNode(CHILD_PER_NODE);
        root.setName("root");
        resetCursor();
    }

    /**
     * Moves the cursor reference to root.
     */
    public void resetCursor() {
        cursor = root;
    }

    /**
     * Changes the cursor reference to a given name.
     * @param name <i>String</i> that can relative or absolute path.
     * @throws NotADirectoryException Thrown when the name corresponds to a
     * file.
     */
    public void changeDirectory(String name) throws NotADirectoryException {
        // Implementation for extra credit:
        if (name.equals("..")) {
            if (cursor == root) {
                System.out.println("ERROR: Already at root directory.");
            } else {
                cursor = findPreviousNode(root,cursor);
            }
        } else if (name.contains("/")) {
            String[] paths = name.split("/");
            if (paths[0].equals("root")) {
                // Absolute path.
                resetCursor();
                for (int i = 1; i < paths.length; i++) {
                    changeDirectory(paths[i]);
                }
            } else {
                // Relative path.
                for (int i = 0; i < paths.length; i++) {
                    changeDirectory(paths[i]);
                }
            }
        } else {
            // Cd into child.
            for (int i = 0; i < CHILD_PER_NODE; i++) {
                if (cursor.getChild(i) != null) {
                    if (cursor.getChild(i).getName().equals(name)) {
                        if (!cursor.getChild(i).isFile()) {
                            cursor = cursor.getChild(i);
                            return;
                        } else {
                            throw new NotADirectoryException("ERROR: Cannot " +
                              "change directory into a file.");
                        }
                    }
                }
                if (i == CHILD_PER_NODE-1) {
                    System.out.println("ERROR: No such directory named " +
                      "'" + name + "'.");
                }
            }
        }


        // Original Implementation for ternary tree:

//        if (name.equals("..")) {
//            if (cursor == root) {
//                System.out.println("ERROR: Already at root directory");
//            } else {
//                cursor = findPreviousNode(root,cursor);
//            }
//        } else if (cursor.getLeft().getName().equals(name)) {
//            if (!cursor.getLeft().isFile()) {
//                cursor = cursor.getLeft();
//            } else {
//                throw new NotADirectoryException("ERROR: Cannot change " +
//                  "directory into a file");
//            }
//        } else if (cursor.getMiddle().getName().equals(name)) {
//            if (!cursor.getMiddle().isFile()) {
//                cursor = cursor.getMiddle();
//            } else {
//                throw new NotADirectoryException("ERROR: Cannot change " +
//                  "directory into a file");
//            }
//        } else if (cursor.getRight().getName().equals(name)) {
//            if (!cursor.getRight().isFile()) {
//                cursor = cursor.getRight();
//            } else {
//                throw new NotADirectoryException("ERROR: Cannot change " +
//                  "directory into a file");
//            }
//        } else {
//            throw new NotADirectoryException("ERROR: No such directory named " +
//              "'" + name + "'.");
//        }
    }

    /**
     * Gets the current working directory.
     * @return <i>String</i> representing the path of the working directory.
     */
    public String presentWorkingDirectory() {
        return getPath(root,cursor,"");
    }

    /**
     * List all child of the working directory.
     * @return <i>String</i> containing all child of the working directory.
     */
    public String listDirectory() {
        String output = "";

        // Implementation for ternary tree.
//        if (cursor.getLeft() != null) {
//            output += cursor.getLeft().getName() + " ";
//        } else {
//            return output;
//        }
//        if (cursor.getMiddle() != null) {
//            output += cursor.getMiddle().getName() + " ";
//        } else {
//            return output;
//        }
//        if (cursor.getRight() != null) {
//            output += cursor.getRight().getName();
//        }

        // Implementation for extra credit.
        for (int i = 0; i < CHILD_PER_NODE; i++) {
            if (cursor.getChild(i) != null) {
                output += cursor.getChild(i).getName() + " ";
            }
        }

        return output;
    }

    /**
     * Prints the directory from the cursor.
     */
    public void printDirectoryTree() {
        printDirectoryTree("",cursor);
    }

    /**
     * Create a bew child directory under the cursor.
     * @param name Name of new Directory.
     * @throws FullDirectoryException Thrown when no more child can be added.
     * @throws IllegalArgumentException Thrown when the name isn't usable.
     */
    public void makeDirectory(String name) throws
      FullDirectoryException, IllegalArgumentException {
        if (name.contains("\\s") || name.contains("/") || name.contains("root"))
        {
            throw new IllegalArgumentException("ERROR: Invalid name.");
        }
        for (int i = 0; i < CHILD_PER_NODE; i++) {
            if (cursor.getChild(i) != null) {
                if (cursor.getChild(i).getName().equals(name)) {
                    throw new IllegalArgumentException("Error: Duplicate " +
                      "name.");
                }
            }
        }

//        DirectoryNode newNode = new DirectoryNode();
        // Extra credit implementation V
        DirectoryNode newNode = new DirectoryNode(CHILD_PER_NODE);
        newNode.setIsFile(false);
        newNode.setName(name);

        try {
            cursor.addChild(newNode);
        } catch (NotADirectoryException e) {
            System.out.println("Cannot make a directory in a file.");
        }
    }

    /**
     * Creates child file under cursor.
     * @param name Name of the new file child.
     * @throws FullDirectoryException Thrown when the cursor cannot add more.
     * @throws IllegalArgumentException Thrown when the name isn't usable.
     */
    public void makeFile(String name) throws
      FullDirectoryException, IllegalArgumentException {
        if (name.contains("\\s") || name.contains("/") || name.contains("root"))
        {
            throw new IllegalArgumentException("ERROR: Invalid name.");
        }
        for (int i = 0; i < CHILD_PER_NODE; i++) {
            if (cursor.getChild(i) != null) {
                if (cursor.getChild(i).getName().equals(name)) {
                    throw new IllegalArgumentException("Error: duplicated " +
                      "name.");
                }
            }
        }

        DirectoryNode newNode = new DirectoryNode();
        newNode.setIsFile(true);
        newNode.setName(name);

        try {
            cursor.addChild(newNode);
        } catch (NotADirectoryException e) {
            System.out.println("Cannot make a file in a file.");
        }
    }

    // Additional methods

    /**
     * Recursive method to print out a directory tree.
     * @param indents <i>String</i> variable to keep track of indents.
     * @param node <i>DirectoryNode</i> to be printed for that method call.
     */
    private void printDirectoryTree(String indents, DirectoryNode node) {
        if (node.isFile()) {
            System.out.println(indents + " - " + node.getName());
        } else {
            System.out.println(indents + "|- " + node.getName());
        }

        // Implementation for extra credit:
        for (int i = 0; i < CHILD_PER_NODE; i++) {
            if (!node.isFile()) {
                if (node.getChild(i) != null) {
                    printDirectoryTree(indents + "    ", node.getChild(i));
                }
            }
        }

        // Implementation for ternary tree:
//        if (node.getLeft() != null) {
//            printDirectoryTree(indents + "    ", node.getLeft());
//        }
//        if (node.getMiddle() != null) {
//            printDirectoryTree(indents + "    ", node.getMiddle());
//        }
//        if (node.getRight() != null) {
//            printDirectoryTree(indents + "    ", node.getRight());
//        }
    }

    /**
     * Recursive method to get the path of a <i>DirectoryNode</i>.
     * @param node Node to start search from.
     * @param target Node to find.
     * @param path The path that the recursive call took to get there.
     * @return Returns a <i>String</i> containing the path taken to reach the
     * target node.
     */
    private String getPath(DirectoryNode node,
      DirectoryNode target, String path) {
        path += node.getName() + "/";
        if (node == target) {
            return path.substring(0,path.length()-1); // Removes last /
        } else {
            String outcome = "";

            // Implementation for extra credit:
            for (int i = 0; i < CHILD_PER_NODE; i++) {
                if (node.getChild(i) != null) {
                    if (!node.getChild(i).isFile()) {
                        outcome = getPath(node.getChild(i),target,path);
                        if (!outcome.equals("")) {
                            return outcome;
                        }
                    }
                }
            }

            // Implementation for ternary tree:
//            if (node.getLeft() != null) {
//                if (!node.getLeft().isFile()) {
//                    outcome = getPath(node.getLeft(),target,path);
//                    if (!outcome.equals("")) {
//                        return outcome;
//                    }
//                }
//            }
//            if (node.getMiddle() != null) {
//                if (!node.getMiddle().isFile()) {
//                    outcome = getPath(node.getMiddle(),target,path);
//                    if (!outcome.equals("")) {
//                        return outcome;
//                    }
//                }
//            }
//            if (node.getRight() != null) {
//                if (!node.getRight().isFile()) {
//                    outcome = getPath(node.getRight(),target,path);
//                    if (!outcome.equals("")) {
//                        return outcome;
//                    }
//                }
//            }
        }

        return "";
    }

    /**
     * Finds a file/directory in the tree.
     * @param name The name of the file or directory.
     * @return Returns a string containing all of the paths to nodes with the
     * same name.
     */
    public String findNode(String name) {
        String output = printFindNode(name,root,"","");
        if (output.equals("")) {
            return "ERROR: No such file exists.\n";
        } else {
            return output;
        }
    }

    /**
     * Recursive method to get the path to a node with a given name.
     * @param name The name of the node to find.
     * @param node The node to check in that method call.
     * @param path Path taken to get to a node.
     * @param output <i>String</i> containing the found paths.
     * @return Returns <i>output</i> after it runs through all possibilities.
     */
    public String printFindNode(String name, DirectoryNode node, String path,
      String output) {
        path += node.getName() + "/";
        if (node.getName().equals(name)) {
            output += path.substring(0,path.length()-1) + "\n";
        } // Gets rid of a slash at the end of path.

        // Implementation for extra credit.
        for (int i = 0; i < CHILD_PER_NODE; i++) {
            if (node.getChild(i) != null) {
                output = printFindNode(name,node.getChild(i),path,output);
            }
        }

        // Implementation for ternary tree:
//        if (node.getLeft() != null) {
//            output = printFindNode(name,node.getLeft(),path,output);
//        }
//        if (node.getMiddle() != null) {
//            output = printFindNode(name,node.getMiddle(),path,output);
//        }
//        if (node.getRight() != null) {
//            output = printFindNode(name,node.getRight(),path,output);
//        }

        return output;
    }

    /**
     * Recursive method to get the parent node of a given node.
     * @param node The node that is being check on method call.
     * @param target The child of the node we're looking for.
     * @return Returns the parent node of the target node.
     */
    private DirectoryNode findPreviousNode(DirectoryNode node,
      DirectoryNode target) {
        // Implementation for ternary tree:
//        if (node.getLeft() == target) {
//            return node;
//        } else if (node.getMiddle() == target) {
//            return node;
//        } else if (node.getRight() == target) {
//            return node;
//        }
//
//        DirectoryNode temp;
//        if (node.getLeft() != null) {
//            temp = findPreviousNode(node.getLeft(),target);
//            if (temp != null) {
//                return temp;
//            }
//        }
//        if (node.getMiddle() != null) {
//            temp = findPreviousNode(node.getMiddle(),target);
//            if (temp != null) {
//                return temp;
//            }
//        }
//        if (node.getRight() != null) {
//            temp = findPreviousNode(node.getRight(),target);
//            if (temp != null) {
//                return temp;
//            }
//        }

        // Implementation for extra credit:
        for (int i = 0; i < CHILD_PER_NODE; i++) {
            if (node.getChild(i) == target) {
                return node;
            }
        }
        DirectoryNode temp;
        for (int i = 0; i < CHILD_PER_NODE; i++) {
            if (node.getChild(i) != null) {
                temp = findPreviousNode(node.getChild(i),target);
                if (temp != null) {
                    return temp;
                }
            }
        }

        return null;
    }

    /**
     * Moves a node from one place to another.
     * @param src Absolute path of the source node.
     * @param dst Absolute path of the destination node.
     * @throws IllegalArgumentException Thrown when any of the parameters
     * isn't valid.
     */
    public void moveNode(String src, String dst) {
        DirectoryNode sourceNode;

        // Sets up the values so java recognizes that there is a reference.
        // Will be replaced later if an exception isn't encountered.
        DirectoryNode sourceParent = root;
        int indexOfSourceNode = -1;

        if (src.contains("/")) {
            String[] srcPath = src.split("/");
            if (!srcPath[0].equals("root")) {
                throw new IllegalArgumentException("ERROR: src is not " +
                  "absolute path.");
            }
            // Grabs the sourceNode from Absolute path.
            sourceNode = root;
            for (int i = 1; i < srcPath.length; i++) {
                inner: for (int u = 0; u < CHILD_PER_NODE; u++) {
                    if (sourceNode.getChild(u) != null) {
                        if (sourceNode.getChild(u).getName().
                          equals(srcPath[i])) {
                            if (i == srcPath.length-1) {
                                sourceParent = sourceNode;
                                indexOfSourceNode = u;
                            }
                            sourceNode = sourceNode.getChild(u);
                            break inner;
                        }
                    }
                    if (u == CHILD_PER_NODE - 1) {
                        // This means if it didn't find a folder.
                        throw new IllegalArgumentException("ERROR: src " +
                          "path is invalid.");
                    }
                }
            }
        } else {
            // Since it has to be absolute path, without / then, is isn't
            // absolute.
            throw new IllegalArgumentException("ERROR: src path is invalid.");
        }

        if (dst.contains("/")) {
            String[] dstPath = dst.split("/");
            if (!dstPath[0].equals("root")) {
                throw new IllegalArgumentException("ERROR: dst is not " +
                  "absolute path.");
            }

            // Grabs the dstNode from Absolute path.
            DirectoryNode dstParent = root;
            for (int i = 1; i < dstPath.length; i++) {
                inner: for (int u = 0; u < CHILD_PER_NODE; u++) {
                    if (dstParent.getChild(u) != null) {
                        if (dstParent.getChild(u).getName().
                          equals(dstPath[i])) {
                            dstParent = dstParent.getChild(u);
                            break inner;
                        }
                    }
                    if (u == CHILD_PER_NODE - 1) {
                        // This means if it didn't find a folder.
                        throw new IllegalArgumentException("ERROR: dst " +
                          "path is invalid.");
                    }
                }
            }

            try {
                dstParent.addChild(sourceNode);
                sourceParent.removeChild(indexOfSourceNode);
            } catch (NotADirectoryException e) {
                System.out.println("Error: dst path is not a directory.");
            } catch (FullDirectoryException e) {
                System.out.println("Error: dst path is full.");
            }
        } else {
            // Since it has to be absolute path, without / then, is isn't
            // absolute.
            throw new IllegalArgumentException("ERROR: dst path is invalid.");
        }


    }
}
