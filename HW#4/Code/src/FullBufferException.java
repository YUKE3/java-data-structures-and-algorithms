/**
 * @author
 * Yugui Ke
 * 113778667
 * Recitation 3
 * yugui.ke@stonybrook.edu
 */

public class FullBufferException extends Exception {

    /**
     * Called when Router buffers are full.
     */
    public FullBufferException() {
        super("Full Buffer Exception");
    }

    /**
     * FullBufferException with a message.
     * @param msg
     */
    public FullBufferException(String msg) {
        super(msg);
    }
}
