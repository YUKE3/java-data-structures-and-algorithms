/**
 * @author
 * Yugui Ke
 * 113778667
 * Recitation 3
 * yugui.ke@stonybrook.edu
 */

public class DepartmentCodeException extends Exception {
    /**
     * Called when department code isn't what it's expected.
     */
    public DepartmentCodeException() {
        super("Department code not valid.");
    }

    /**
     * Additional method for custom message.
     * @param message message passed to super.
     */
    public DepartmentCodeException(String message) {
        super(message);
    }
}
