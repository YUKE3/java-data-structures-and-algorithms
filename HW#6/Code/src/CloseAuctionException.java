/**
 * @author
 * Yugui Ke
 * 113778667
 * Recitation 3
 * yugui.ke@stonybrook.edu
 */

public class CloseAuctionException extends Exception {
    /**
     * Called when the auction has no time left.
     */
    public CloseAuctionException() {
        super("CloseAuctionException");
    }

    /**
     * <i>CloseAuctionException</i> with a custom message.
     * @param msg the custom message.
     */
    public CloseAuctionException(String msg) {
        super(msg);
    }
}
