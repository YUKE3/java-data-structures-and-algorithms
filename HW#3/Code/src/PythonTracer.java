/**
 * @author
 * Yugui Ke
 * 113778667
 * Recitation 3
 * yugui.ke@stonybrook.edu
 */

import java.io.*;

public class PythonTracer {
    // Numbers of space in one indent.
    public static final int SPACE_COUNT = 4;

    /**
     * Main method.
     * @param args Unused.
     * @throws IOException Occurs when <i>BufferedReader</i> encounters an
     * error.
     */
    public static void main(String[] args) throws IOException {
        InputStreamReader inputStream = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(inputStream);

        while (true) {
            System.out.print("Please enter a file name (or 'quit' to quit): ");

            String userInput = reader.readLine();

            if (userInput.equals("quit")) {
                System.out.println("Program terminating successfully...");
                break;
            }

            try {
                System.out.println(); // Empty line separator.
                CodeBlock output = traceFile(userInput);
                Complexity overall = new Complexity(0,0);
                overall.setNPower(output.getBlockComplexity().getNPower() +
                  output.getHighestSubComplexity().getNPower());
                overall.setLogPower(output.getBlockComplexity().getLogPower() +
                  output.getHighestSubComplexity().getLogPower());
                System.out.println("Overall complexity of " + userInput +
                  ": " + overall);
            } catch (IllegalArgumentException e) {
                System.out.println("\n" + e.getMessage());
            }

            System.out.println(); // Empty line separator.
        }
    }

    /**
     * Traces a given file for complexity.
     * @param filename <i>String</i> representing the name of the file in
     *                 working directory.
     * @return Returns an <i>CodeBlock</i> object containing the complexity
     * of the file.
     * @throws IOException Happens when <i>BufferedReader</i> breaks.
     */
    public static CodeBlock traceFile(String filename) throws IOException {
        // If the input is empty.
        if (filename.equals("")) {
            throw new IllegalArgumentException("No filename provided.");
        }

        // Initialize stack to an empty stack of CodeBlocks.
        BlockStack stack = new BlockStack();

        // String to keep track of stackTrace.
        String stackTrace = "";

        // Open file using filename.
        // Creates the BufferedReader for reading the file.
        // Throws exception when file isn't found.
        BufferedReader reader;
        try {
            FileInputStream fis = new FileInputStream(filename);
            InputStreamReader inputStream = new InputStreamReader(fis);
            reader = new BufferedReader(inputStream);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File not found.");
        }

        String line = reader.readLine();

        // While file has lines.
        while (line != null) {

            // If line is not empty and line does not start with '#'
            if (line.indexOf("#") != 0 && line.length() > 0) {

                // Counts the number of spaces in line.
                int spaceCount = 0;
                for (char c : line.toCharArray()) {
                    if (c == ' ') {
                        spaceCount++;
                    }
                }

                // indents = number of spaces in line / SPACES_COUNT.
                int indents = spaceCount / SPACE_COUNT;

                // While indents is less than size of stack.
                while (indents < stack.size()) {

                    // If indent is 0.
                    if (indents == 0) {
                        // Close file and return the total complexity of
                        // stack.top
                        return stack.pop();
                    } else {
                        CodeBlock oldTop = stack.pop();
                        Complexity oldTopComplexity = new Complexity(
                          oldTop.getBlockComplexity().getNPower() +
                          oldTop.getHighestSubComplexity().getNPower(),
                          oldTop.getBlockComplexity().getLogPower() +
                          oldTop.getHighestSubComplexity().getLogPower());

                        if (oldTopComplexity.getNPower() >
                          stack.peek().getHighestSubComplexity().getNPower()) {

                            // stack.top's highest sub-complexity =
                            // oldTopComplexity.
                            CodeBlock previous = stack.pop();
                            previous.setHighestSubComplexity(oldTopComplexity);
                            stack.push(previous);
                            stackTrace=printStackTrace(stackTrace,false,stack);

                        } else if (oldTopComplexity.getNPower() ==
                          stack.peek().getHighestSubComplexity().getNPower()) {
                            if (oldTopComplexity.getLogPower() > stack.peek().
                              getHighestSubComplexity().getLogPower()) {

                                // stack.top's highest sub-complexity =
                                // oldTopComplexity.
                                CodeBlock previous = stack.pop();
                                previous.setHighestSubComplexity
                                  (oldTopComplexity);
                                stack.push(previous);
                                stackTrace = printStackTrace(
                                  stackTrace,false,stack);
                            }
                        } else {
                            // Prints when there is no update.
                            String[] traces = stackTrace.split("\\.");
                            String currentTrace = "";
                            for (int i = 0; i < stack.size(); i++) {
                                currentTrace += traces[i];
                                if (i != stack.size() - 1) {
                                    currentTrace += ".";
                                }
                            }
                            System.out.println("Leaving block " + currentTrace +
                              "." + traces[stack.size()]+ ", " +
                              "nothing to update.");
                            printBlock(stack.peek(),currentTrace);
                        }
                    }

                }

                // if line contains a keyword
                int keyword = -1;
                // Trimmed off leading white spaces, so it can look to see if
                // the keyword is at the start of string.
                String trimmedLine = line.replaceAll("^\\s+","");
                for (int i = 0; i < CodeBlock.BLOCK_TYPES.length; i++) {
                    if (trimmedLine.indexOf(
                          CodeBlock.BLOCK_TYPES[i] + " ") == 0) {
                        keyword = i;
                        break; // breaks from loop when keyword is found.
                    }
                }
                if (keyword != -1) {
                    switch (keyword) {
                        // If keyword is "for"
                        case CodeBlock.FOR:
                            // Determine the complexity at the end of line.
                            if (line.contains("log_N:")) {
                                // Create new O(n) CodeBlock and push onto
                                // stack.
                                CodeBlock newBlock = new CodeBlock();
                                newBlock.setBlockComplexity(
                                  new Complexity(0,1));
                                newBlock.setHighestSubComplexity(
                                  new Complexity(0,0));
                                newBlock.setName("for");
                                stack.push(newBlock);
                                stackTrace = printStackTrace(
                                  stackTrace,true,stack);
                            } else if (line.contains("N:")) {
                                // Create new O(log(n)) CodeBlock and push
                                // onto stack.
                                CodeBlock newBlock = new CodeBlock();
                                newBlock.setBlockComplexity(
                                  new Complexity(1,0));
                                newBlock.setHighestSubComplexity(
                                  new Complexity(0,0));
                                newBlock.setName("for");
                                stack.push(newBlock);
                                stackTrace = printStackTrace(
                                  stackTrace,true,stack);
                            }
                            break;
                        // If keyword is "while"
                        case CodeBlock.WHILE:
                            // loopVariable = variable being updated.
                            CodeBlock newBlock = new CodeBlock();
                            // Gets the second word in the line, which is the
                            // loop variable.
                            newBlock.setLoopVariable(trimmedLine.substring(
                              trimmedLine.indexOf(" ")+ 1,trimmedLine.indexOf(
                              " ",trimmedLine.indexOf(" ") + 1)));

                            // Create new O(1) CodeBlock and push onto the
                            // stack.
                            newBlock.setBlockComplexity(new Complexity(0,0));
                            newBlock.setHighestSubComplexity(new Complexity(0
                                    ,0));
                            newBlock.setName("while");
                            stack.push(newBlock);
                            stackTrace = printStackTrace(stackTrace,true,stack);
                            break;
                        default:
                            CodeBlock defaultBlock = new CodeBlock();
                            // Switch statement for the name.
                            switch (keyword) {
                                case CodeBlock.DEF:
                                    defaultBlock.setName("def");
                                    break;
                                case CodeBlock.IF:
                                    defaultBlock.setName("if");
                                    break;
                                case CodeBlock.ELIF:
                                    defaultBlock.setName("else if");
                                    break;
                                case CodeBlock.ELSE:
                                    defaultBlock.setName("else");
                                    break;
                            }
                            // Create new O(1) CodeBlock and push onto the
                            // stack.
                            defaultBlock.setBlockComplexity(new Complexity(0,
                              0));
                            defaultBlock.setHighestSubComplexity(
                              new Complexity(0,0));
                            stack.push(defaultBlock);
                            stackTrace = printStackTrace(stackTrace,true,stack);
                            break;
                    }
                // Else if stack.top is a "while" block and line updates
                // stack.top's loop variable.
                } else if (stack.peek().getName().equals("while")) {
                    // Update the blockComplexity of stack.top.
                    if (line.contains(" -= ")) {
                        CodeBlock topBlock = stack.pop();
                        topBlock.setBlockComplexity(new Complexity(1,0));
                        stack.push(topBlock);

                        // Prints the "Found update statement" line.
                        String[] traces = stackTrace.split("\\.");
                        String currentTrace = "";
                        for (int i = 0; i < stack.size(); i++) {
                            currentTrace += traces[i];
                            if (i != stack.size() - 1) {
                                currentTrace += ".";
                            }
                        }
                        System.out.println("Found update statement, updating " +
                          "block " + currentTrace + ":");
                        printBlock(stack.peek(),currentTrace);
                    } else if (line.contains(" /= ")) {
                        CodeBlock topBlock = stack.pop();
                        topBlock.setBlockComplexity(new Complexity(0,1));
                        stack.push(topBlock);

                        // Prints the "Found update statement" line.
                        String[] traces = stackTrace.split("\\.");
                        String currentTrace = "";
                        for (int i = 0; i < stack.size(); i++) {
                            currentTrace += traces[i];
                            if (i != stack.size() - 1) {
                                currentTrace += ".";
                            }
                        }
                        System.out.println("Found update statement, updating " +
                          "block " + currentTrace + ":");
                        printBlock(stack.peek(),currentTrace);
                    }
                }
            }

            // Else Ignore line.
            line = reader.readLine();
        }

        // while size of stack > 1
        while (stack.size() > 1) {
            CodeBlock oldTop = stack.pop();
            Complexity oldTopComplexity = new Complexity(
              oldTop.getBlockComplexity().getNPower() +
              oldTop.getHighestSubComplexity().getNPower(),
              oldTop.getBlockComplexity().getLogPower() +
              oldTop.getHighestSubComplexity().getLogPower());

            if (oldTopComplexity.getNPower() >
              stack.peek().getHighestSubComplexity().getNPower()) {

                // stack.top's highest sub-complexity =
                // oldTopComplexity.
                CodeBlock previous = stack.pop();
                previous.setHighestSubComplexity(oldTopComplexity);
                stack.push(previous);
                stackTrace = printStackTrace(stackTrace,false,stack);

            } else if (oldTopComplexity.getNPower() ==
              stack.peek().getHighestSubComplexity().getNPower()) {
                if (oldTopComplexity.getLogPower() > stack.peek().
                   getHighestSubComplexity().getLogPower()) {

                    // stack.top's highest sub-complexity =
                    // oldTopComplexity.
                    CodeBlock previous = stack.pop();
                    previous.setHighestSubComplexity(oldTopComplexity);
                    stack.push(previous);
                    stackTrace = printStackTrace(stackTrace,false,stack);
                }
            } else {
                // Prints when there is no update.
                String[] traces = stackTrace.split("\\.");
                String currentTrace = "";
                for (int i = 0; i < stack.size(); i++) {
                    currentTrace += traces[i];
                    if (i != stack.size() - 1) {
                        currentTrace += ".";
                    }
                }
                System.out.println("Leaving block " + currentTrace + "." +
                  traces[stack.size()]+ ", " + "nothing to update.");
                printBlock(stack.peek(),currentTrace);
            }
        }

        // If there is nothing by the end, the file is probably invalid
        // (formatted differently than expected or empty).
        if (stack.size() == 0) {
            throw new IllegalArgumentException("Invalid file.");
        }
        // Since this assignment assumes that there is only a single
        // function, at the end of this algorithm, it should be exiting the
        // last block.
        System.out.println("Leaving block 1.\n");
        return stack.pop();
    }

    /**
     * Prints the stackTrace while updating the stackTrace.
     * @param stackTrace <i>String</i> representing the old stackTrace.
     * @param entering A <i>boolean</i> representing if a new block is being
     *                 entered.
     * @param stack The current <i>BlockStack</i> keeping track of the
     *              complexities.
     * @return Returns an updated stackTrace as a <i>String</i>.
     */
    private static String printStackTrace(String stackTrace,
      boolean entering, BlockStack stack) {

        if (entering) {
            String[] elements = stackTrace.split("\\.");

            // Adds .1 to string if that spot doesn't exist yet.
            if (elements.length < stack.size()) {
                stackTrace += ".1";
            } else if (stackTrace.equals("")) {
                // Basically when the script starts, empty array is split
                // into array size of one despite having nothing. This is to
                // add the 1 at the start.
                stackTrace += "1";
            } else {
                // Adding when there is an element at a certain point,
                // replaces the element and removes all string after that point.
                elements[stack.size()-1] =
                        Integer.parseInt(elements[stack.size()-1])+1 + "";
                String newTrace = "";
                for (int i = 0; i < stack.size(); i++) {
                    newTrace += elements[i] + ".";
                }
                stackTrace = newTrace.substring(0,newTrace.length()-1);
            }
        }

        // Splits the string in order to get the current stackTrace.
        String[] stringBlocks = stackTrace.split("\\.");
        String currentTrace = "";
        for (int i = 0; i < stack.size(); i++) {
            currentTrace += stringBlocks[i];
            if (i != stack.size() - 1) {
                currentTrace += ".";
            }
        }

        // Prints either entering or leaving (with updates).
        if (entering) {
            System.out.println("Entering block " +
              stackTrace + " '" + stack.peek().getName() + "':");
        } else {
            System.out.println("Leaving block " +
              currentTrace + "." + stringBlocks[stack.size()-1] +
              ", updating block " + currentTrace + ":");
        }

        // Prints out the current CodeBlock.
        printBlock(stack.peek(),currentTrace);

        // Returns the string so that it is updated.
        return stackTrace;
    }

    /**
     * Prints out a <i>CodeBlock</i> object in human readable form.
     * @param block block to be printed.
     * @param trace Where it belongs.
     */
    private static void printBlock(CodeBlock block, String trace) {
        System.out.println(String.format("    BLOCK %-9sblock complexity = " +
          "%-11shighest sub-complexity = %s\n",trace+":",
          block.getBlockComplexity(),block.getHighestSubComplexity()));
    }
}
