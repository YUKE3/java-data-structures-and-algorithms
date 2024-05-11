/**
 * @author
 * Yugui Ke
 * 113778667
 * Recitation 3
 * yugui.ke@stonybrook.edu
 */

public class CodeBlock {
    // Array representing block types.
    public static final String[] BLOCK_TYPES = {"def","for","while","if","elif",
      "else"};

    // Named reference to array indices.
    public static final int DEF = 0;
    public static final int FOR = 1;
    public static final int WHILE = 2;
    public static final int IF = 3;
    public static final int ELIF = 4;
    public static final int ELSE = 5;

    // Member variables.
    private Complexity blockComplexity;
    private Complexity highestSubComplexity;
    private String name;
    private String loopVariable;

    /**
     * Empty constructor to instantiate the class.
     */
    public CodeBlock() {
    }

    /**
     * sets the blockComplexity of this <i>CodeBlock</i>.
     * @param blockComplexity new <i>Complexity</i> for blockComplexity.
     */
    public void setBlockComplexity(Complexity blockComplexity) {
        this.blockComplexity = blockComplexity;
    }

    /**
     * Sets the highestSubComplexity of this <i>CodeBlock</i>.
     * @param highestSubComplexity new <i>Complexity</i> for
     *                             highestSubComplexity.
     */
    public void setHighestSubComplexity(Complexity highestSubComplexity) {
        this.highestSubComplexity = highestSubComplexity;
    }

    /**
     * Sets the name of this <i>CodeBlock</i>.
     * @param name new <i>String</i> for name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the loopVariable of this <i>CodeBlock</i>.
     * @param loopVariable new <i>String</i> for loopVariable.
     */
    public void setLoopVariable(String loopVariable) {
        this.loopVariable = loopVariable;
    }

    /**
     * Gets the blockComplexity of this <i>CodeBlock</i>.
     * @return returns <i>Complexity</i> object representing the
     * blockComplexity.
     */
    public Complexity getBlockComplexity() {
        return blockComplexity;
    }

    /**
     * Gets the highestSubComplexity of this <i>CodeBlock</i>.
     * @return returns <i>Complexity</i> object representing the
     * highestSubComplexity.
     */
    public Complexity getHighestSubComplexity() {
        return highestSubComplexity;
    }

    /**
     * Gets the name of this <i>CodeBlock</i>.
     * @return returns <i>String</i> object containing the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the loopVariable of this <i>CodeBlock</i>.
     * @return returns <i>String</i> object containing the loopVariable.
     */
    public String getLoopVariable() {
        return loopVariable;
    }
}
