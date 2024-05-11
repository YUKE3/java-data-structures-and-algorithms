/**
 * @author
 * Yugui Ke
 * 113778667
 * Recitation 3
 * yugui.ke@stonybrook.edu
 */

import java.util.Stack;

public class BlockStack {
    private Stack<CodeBlock> stack;
    private int size;

    /**
     * Creates a empty <i>BlockStack</i> object.
     */
    public BlockStack() {
        stack = new Stack<CodeBlock>();
        size = 0;
    }

    /**
     * Push a <i>CodeBlock</i> object onto the stack.
     * @param block new block to be added.
     */
    public void push(CodeBlock block) {
        size++;
        stack.push(block);
    }

    /**
     * Pops a <i>CodeBlock</i> object off the stack.
     * @return a <i>CodeBlock</i> object that was at the top of the stack.
     */
    public CodeBlock pop() {
        size--;
        return stack.pop();
    }

    /**
     * Peeks at the top of the stack.
     * @return a <i>CodeBlock</i> object at the top of the stack.
     */
    public CodeBlock peek() {
        return stack.peek();
    }

    /**
     * Gets the size of the stack.
     * @return an <i>int</i> representing the size of the stack.
     */
    public int size() {
        return size;
    }

    /**
     * Checks if stack is empty.
     * @return an <i>boolean</i>, true if stack is empty, false otherwise.
     */
    public boolean isEmpty() {
        return stack.isEmpty();
    }
}
