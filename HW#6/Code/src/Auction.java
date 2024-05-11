/**
 * @author
 * Yugui Ke
 * 113778667
 * Recitation 3
 * yugui.ke@stonybrook.edu
 */

import java.io.Serializable;

public class Auction implements Serializable {
    private int timeRemaining;
    private double currentBid;
    private String auctionID, sellerName, buyerName, itemInfo;

    /**
     * Constructor with all member variables.
     * @param timeRemaining time left for the auction.
     * @param currentBid current bid on the auction.
     * @param auctionID id of the auction.
     * @param sellerName auction's seller's name.
     * @param buyerName name of auction's highest bidder.
     * @param itemInfo some info about the auction.
     */
    public Auction(int timeRemaining, double currentBid, String auctionID,
      String sellerName, String buyerName, String itemInfo) {
        this.timeRemaining = timeRemaining;
        this.currentBid = currentBid;
        this.auctionID = auctionID;
        this.sellerName = sellerName;
        this.buyerName = buyerName;
        this.itemInfo = itemInfo;
    }

    /**
     * Constructor with less information, used when creating new auction.
     * @param timeRemaining time left for this new auction.
     * @param auctionID id for the new auction.
     * @param sellerName name of the seller of this auction.
     * @param itemInfo some info about the auction.
     */
    public Auction(int timeRemaining, String auctionID, String sellerName,
      String itemInfo) {
        this.timeRemaining = timeRemaining;
        this.auctionID = auctionID;
        this.itemInfo = itemInfo;
        this.sellerName = sellerName;
    }

    /**
     * Gets the remaining time for this auction.
     * @return <i>int</i> representing time left.
     */
    public int getTimeRemaining() {
        return timeRemaining;
    }

    /**
     * Gets the current bid of this auction.
     * @return <i>double</i> representing the current bid.
     */
    public double getCurrentBid() {
        return currentBid;
    }

    /**
     * Gets the ID of this auction.
     * @return <i>String</i> representing this auctions id.
     */
    public String getAuctionID() {
        return auctionID;
    }

    /**
     * Gets the name of the seller of this auction.
     * @return <i>String</i> representing seller's name.
     */
    public String getSellerName() {
        return sellerName;
    }

    /**
     * Gets the name of the buyer with highest bid.
     * @return <i>String</i> representing buyer's name.
     */
    public String getBuyerName() {
        return buyerName;
    }

    /**
     * Gets some information of the auction.
     * @return <i>String</i> with some information about the auction.
     */
    public String getItemInfo() {
        return itemInfo;
    }

    /**
     * Decreases the time remaining for this auction by a specific amount.
     * @param time <i>int</i> representing the amount to decrement by.
     */
    public void decrementTimeRemaining(int time) {
        if (time > timeRemaining) {
            timeRemaining = 0;
        } else {
            timeRemaining = timeRemaining - time;
        }
    }

    /**
     * Creates a new bid on this auction.
     * @param bidderName name of the bidder.
     * @param bidAmt amount to be bid.
     * @throws CloseAuctionException called when auction doesn't have time
     * remaining.
     */
    public void newBid(String bidderName, double bidAmt)
      throws CloseAuctionException {
        if (timeRemaining > 0) {
            if (bidAmt > currentBid) {
                currentBid = bidAmt;
                buyerName = bidderName;
            }
        } else {
            throw new CloseAuctionException();
        }
    }

    /**
     * Converts member variables to table view.
     * @return <i>String</i> with a table view of information.
     */
    public String toString() {
        // First format formats the string into readable table.
        // Second format adds thousands separator to currentBid.
        // currentBid, sellerName, buyerName checked to see if it is NULL, if
        // it is, then put blank instead so it doesn't print out null.
        return String.format(" %9s | %10s | %-22s| %-22s| %3s hours | %-42s",
          auctionID,
          (currentBid == 0.0 ? "" : String.format("$ %(,.2f",currentBid)),
          sellerName,
          (buyerName == null ? "" : buyerName), // Buyer could be null.
          timeRemaining,itemInfo.substring(0,Math.min(itemInfo.length(),42)));
    }
}

