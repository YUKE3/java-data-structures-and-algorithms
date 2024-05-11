/**
 * @author
 * Yugui Ke
 * 113778667
 * Recitation 3
 * yugui.ke@stonybrook.edu
 */

public class NotADirectoryException extends Exception {
    /**
     * Exception called when a <i>DirectoryNode</i> is a file.
     */
    public NotADirectoryException() {
        super("Not an directory.");
    }

    /**
     * <i>NotADirectoryException</i> with a custom message.
     * @param msg <i>String</i> containing message.
     */
    public NotADirectoryException(String msg) {
        super(msg);
    }
}
