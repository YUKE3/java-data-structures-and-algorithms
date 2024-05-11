/**
 * @author
 * Yugui Ke
 * 113778667
 * Recitation 3
 * yugui.ke@stonybrook.edu
 */

import java.io.*;

public class AuctionSystem {
    private static AuctionTable auctionTable;
    private static String userName;

    /**
     * Main method.
     * @param args unused.
     * @throws IOException Called when java.io encounters a problem.
     */
    public static void main(String[] args) throws IOException {
        // Reader for user input.
        InputStreamReader inputStream = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(inputStream);

        System.out.println("Starting...");
        try {
            // Tries to locate auction.obj.
            FileInputStream file = new FileInputStream("auction.obj");

            System.out.println("Loading previous Auction Table...");

            ObjectInputStream inStream = new ObjectInputStream(file);
            auctionTable = (AuctionTable)inStream.readObject();
        } catch (FileNotFoundException e) {
            // If not found.
            System.out.println("No previous auction table detected.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.print("Please select a username: ");
        userName = reader.readLine();

        System.out.println(); // Empty line separator.

        main: while (true) {
            System.out.print("Menu:\n" +
              "    (D) - Import Data from URL\n" +
              "    (A) - Create a New Auction\n" +
              "    (B) - Bid on an Item\n" +
              "    (I) - Get Info on Auction\n" +
              "    (P) - Print All Auctions\n" +
              "    (R) - Remove Expired Auctions\n" +
              "    (T) - Let Time Pass\n" +
              "    (Q) - Quit\n\n" + "Please select an option: ");

            // Gets user choice.
            String choice = reader.readLine();

            if (choice.length() == 1) {
                switch (choice.charAt(0)) {
                    case 'D': // Import Data from URL.
                        System.out.print("Please enter a URL: ");
                        String URL = reader.readLine();
                        System.out.println("\nLoading...");

                        try {
                            auctionTable = AuctionTable.buildFromURL(URL);
                            System.out.println("Auction data loaded " +
                              "successfully!\n");
                        } catch (IllegalArgumentException e) {
                            // When URL is invalid.
                            System.out.println("Auction data failed loading, " +
                              "invalid URL.\n");
                        }
                        break;
                    case 'A': // Create a New Auction
                        try {
                            if (auctionTable == null) {
                                throw new Exception("No table loaded.");
                            }

                            // Gets all necessary information.
                            System.out.println(
                              "\nCreating new Auction as " + userName);
                            System.out.print("Please enter an Auction ID: ");
                            String auction_id = reader.readLine();
                            System.out.print("Please enter an Auction time " +
                              "(hours): ");
                            int auction_time =
                              Integer.parseInt(reader.readLine());
                            System.out.print("Please enter some Item Info: ");
                            String item_info = reader.readLine();

                            // Creates new Auction and put it in the table.
                            auctionTable.putAuction(auction_id,
                              new Auction(auction_time,auction_id,userName,
                              item_info));

                            // Prints success message.
                            System.out.println("\nAuction " + auction_id + " " +
                              "inserted into table.\n");
                        } catch (NumberFormatException e) {
                            // When input is invalid.
                            System.out.println("\nInvalid input.\n");
                        } catch (IllegalArgumentException e) {
                            System.out.println("\nAuction with that ID " +
                              "already exist.\n");
                        } catch (Exception e) {
                            System.out.println("\n" + e.getMessage() + "\n");
                            break;
                        }
                        break;
                    case 'B': // Bid on an Item.
                        System.out.print("Please enter an Auction ID: ");
                        String auction_id = reader.readLine();
                        Auction auction = auctionTable.getAuction(auction_id);
                        if (auction != null) {
                            if (auction.getTimeRemaining() != 0) {
                                // If the double isn't 0.0, it will format it.
                                System.out.println("\nAuction " + auction_id +
                                  " is OPEN\n    Current Bid: " +
                                  (auction.getCurrentBid() == 0.0 ? "None" :
                                  String.format("$ %(,.2f",
                                  auction.getCurrentBid())) + "\n");

                                System.out.print("What would you like to " +
                                  "bid?: ");

                                try {
                                    Double previousBid =
                                      auction.getCurrentBid();
                                    Double bidAmt = Double.parseDouble(
                                      reader.readLine());
                                    auction.newBid(userName,bidAmt);

                                    // If the current bid is bigger than last
                                    // bid, then that should mean it works.
                                    if (auction.getCurrentBid() > previousBid) {
                                        System.out.println("Bid accepted.\n");
                                    } else {
                                        System.out.println("Bid declined.\n");
                                    }
                                } catch (CloseAuctionException e) {
                                    // This shouldn't happen, it is already
                                    // checked.
                                    System.out.println("\nAuction is closed\n");
                                    break;
                                } catch (NumberFormatException e) {
                                    System.out.println("\nInvalid input.\n");
                                    break;
                                }

                            } else {
                                System.out.println("\nAuction " + auction_id +
                                  " is CLOSE\n    Current Bid: " +
                                  (auction.getCurrentBid() == 0.0 ? "None" :
                                  String.format("$ %(,.2f",
                                  auction.getCurrentBid())) +
                                  "\n\nYou can no longer bid on this item.\n");
                            }
                        } else {
                            System.out.println("\nNo auction by that ID.\n");
                        }
                        break;
                    case 'I': // Get Info on Auction
                        if (auctionTable == null) {
                            System.out.println("\nNo data in system.\n");
                            break;
                        }
                        System.out.print("Please enter an Auction ID: ");
                        String id = reader.readLine();

                        Auction auc = auctionTable.getAuction(id);
                        if (auc != null) {
                            System.out.println("\nAuction " + id + "\n" +
                              "    Seller: " + auc.getSellerName() + "\n" +
                              "    Buyer: " + (auc.getBuyerName() == null ?
                              "" : auc.getBuyerName()) + "\n" +
                              "    Time: " + auc.getTimeRemaining() +
                              (auc.getTimeRemaining()==1 ?" hour\n":" hours\n")
                              + "    Info: " + auc.getItemInfo() + "\n");
                        } else {
                            System.out.println("\nAuction does not exist.\n");
                        }
                        break;
                    case 'P': // Print All Auctions
                        System.out.println(); // Empty line separator.
                        auctionTable.printTable();
                        System.out.println(); // Empty line separator.
                        break;
                    case 'R': // Remove Expired Auctions
                        System.out.println("\nRemoving expired auctions...");
                        auctionTable.removeExpiredAuctions();
                        System.out.println("All expired auctions removed.\n");
                        break;
                    case 'T': // Let Time Pass
                        System.out.print("How many hours should pass: ");
                        try {
                            int time = Integer.parseInt(reader.readLine());
                            System.out.println("\nTime passing...");
                            auctionTable.letTimePass(time);
                            System.out.println("Auction times updated.\n");
                        } catch (Exception e) {
                            System.out.println("\nInvalid input.\n");
                            break;
                        }
                        break;
                    case 'Q': // Quit
                        System.out.println("\nWriting Auction Table to file.." +
                          ".");
                        FileOutputStream file =
                          new FileOutputStream("auction.obj");
                        ObjectOutputStream outStream =
                          new ObjectOutputStream(file);
                        outStream.writeObject(auctionTable);
                        System.out.println("Done!\n\nGoodbye.");
                        break main; // Ends program.
                    default:
                        System.out.println("Invalid input, please re-enter.");
                        break;
                }
            } else {
                System.out.println("Invalid input, please re-enter.");
            }
        }
    }
}
