/**
 * @author
 * Yugui Ke
 * 113778667
 * Recitation 3
 * yugui.ke@stonybrook.edu
 */

public class NegativeNumberException extends Exception {
    /**
     * Called when expecting a positive number.
     */
    public NegativeNumberException() {
        super("Negative Number Exception.");
    }

    /**
     * Called when expecting a positive number.
     * @param message Create the Exception with a custom message.
     */
    public NegativeNumberException(String message) {
        super(message);
    }
}
