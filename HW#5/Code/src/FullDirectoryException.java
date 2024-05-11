/**
 * @author
 * Yugui Ke
 * 113778667
 * Recitation 3
 * yugui.ke@stonybrook.edu
 */

public class FullDirectoryException extends Exception {
    /**
     * Exception called when a <i>DirectoryNode</i> has all children.
     */
    public FullDirectoryException() {
        super("Directory is full.");
    }

    /**
     * Exception with custom message.
     * @param msg <i>String</i> for custom message.
     */
    public FullDirectoryException(String msg) {
        super(msg);
    }
}
