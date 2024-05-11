/**
 * @author
 * Yugui Ke
 * 113778667
 * Recitation 3
 * yugui.ke@stonybrook.edu
 */

import big.data.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.Hashtable;

public class AuctionTable implements Serializable {
    private Hashtable<String, Auction> auctionTable;

    /**
     * Creates a new <i>AuctionTable</i> from an URL pointing to xml file.
     * @param URL <i>String</i> containing URL.
     * @return <i>AuctionTable</i> object with information from URL.
     * @throws IllegalArgumentException Called when the URL isn't valid.
     */
    public static AuctionTable buildFromURL(String URL)
      throws IllegalArgumentException {
        try { // Encase everything in try, any Exception should be caused by
              // invalid/incompatible URL

            // Loads the data.
            DataSource ds = DataSource.connectXML(URL).load();

            // Grabs info about seller/buyer.
            String[] seller_names = ds.fetchStringArray("listing/seller_info" +
              "/seller_name");
            String[] current_bids = ds.fetchStringArray("listing/auction_info" +
              "/current_bid");
            String[] times_left = ds.fetchStringArray("listing/auction_info" +
              "/time_left");
            String[] id_nums = ds.fetchStringArray("listing/auction_info" +
              "/id_num");
            String[] bidder_names = ds.fetchStringArray("listing" +
              "/auction_info/high_bidder/bidder_name");

            // Grabs the item_info.
            String[] memory = ds.fetchStringArray("listing/item_info/memory");
            String[] hard_drive = ds.fetchStringArray("listing/item_info" +
                                                              "/hard_drive");
            String[] cpu = ds.fetchStringArray("listing/item_info/cpu");

            Hashtable<String, Auction> auctionTable = new
              Hashtable<String, Auction>();

            // Runs through every single listing.
            for (int i = 0; i < seller_names.length; i++) {
                // Removes all non-numbers from times_left
                String[] times = times_left[i].replaceAll("[^0-9]+", " ")
                  .trim().split("\\s");

                auctionTable.put(id_nums[i], new Auction(
                  // If the times length is 1, then it is only hours, otherwise,
                  // it is days and hours, which needs a computation.
                  times.length == 1 ? Integer.parseInt(times[0]) :
                  Integer.parseInt(times[0]) * 24 + Integer.parseInt(times[1]),
                  // Get rid of ,$ in current_bids and parse it into a double.
                  Double.parseDouble(current_bids[i].replaceAll("[,$]", "")),
                  // No change needed for these strings.
                  id_nums[i], seller_names[i], bidder_names[i],
                  // items_info formatted together.
                  cpu[i] + " - " + memory[i] + " - " + hard_drive[i]));
            }

            // Creates a new AuctionTable given the Hashtable.
            return new AuctionTable(auctionTable);
        } catch (Exception e) {
            throw new IllegalArgumentException("URL invalid.");
        }
    }

    /**
     * Simple constructor.
     * @param auctionTable <i>Hashtable</i> that contains the data.
     */
    public AuctionTable(Hashtable<String, Auction> auctionTable) {
        this.auctionTable = auctionTable;
    }

    /**
     * Put a new auction into the auctionTable.
     * @param auctionID id of the new auction.
     * @param auction the new auction.
     * @throws IllegalArgumentException Called when auctionId already exist
     * within the table.
     */
    public void putAuction(String auctionID, Auction auction)
      throws IllegalArgumentException {
        auctionTable.put(auctionID,auction);
    }

    /**
     * Gets an auction from a id.
     * @param auctionID Id of the Auction wanted.
     * @return <i>Auction</i> object that corresponds to the id.
     */
    public Auction getAuction(String auctionID) {
        return auctionTable.get(auctionID);
    }

    /**
     * Let time pass for the auctions in the table.
     * @param numHours Numbers of hours to pass.
     * @throws IllegalArgumentException The the number of hours is negative.
     */
    public void letTimePass(int numHours) throws IllegalArgumentException {
        if (numHours < 0) {
            throw new IllegalArgumentException("Value cannot go below 0.");
        }

        Collection<Auction> auctions = auctionTable.values();
        for (Auction auction : auctions) {
            auction.decrementTimeRemaining(numHours);
        }
    }

    /**
     * Removes expired auctions from the table.
     */
    public void removeExpiredAuctions() {
        // Create a cloned set of auctions, prevents concurrentModification
        // Exception.
        Collection<Auction> auctions =
          ((Hashtable)auctionTable.clone()).values();
        for (Auction auction: auctions) {
            if (auction.getTimeRemaining() == 0) {
                auctionTable.remove(auction.getAuctionID());
            }
        }
    }

    /**
     * Prints the auctionTable in a human-readable fashion.
     */
    public void printTable() {
        System.out.println(" Auction ID |      Bid   |        Seller         " +
          "|          Buyer          |    Time   |  Item Info\n" +
          "==================================================================" +
          "=================================================================");

        Collection<Auction> auctions = auctionTable.values();
        for (Auction auction : auctions) {
            System.out.println(auction);
        }
    }
}
