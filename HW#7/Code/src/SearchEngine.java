/**
 * @author
 * Yugui Ke
 * 113778667
 * Recitation 3
 * yugui.ke@stonybrook.edu
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class SearchEngine {
    public static final String PAGES_FILE = "pages.txt";
    public static final String LINKS_FILE = "links.txt";

    private WebGraph web;

    /**
     * Main method.
     * @param args not used.
     * @throws IOException Shouldn't happen.
     */
    public static void main(String[] args) throws IOException {
        System.out.println("Loading WebGraph data...");

        // Builds the WebGraph.
        WebGraph web = WebGraph.buildFromFiles(PAGES_FILE,LINKS_FILE);

        // Setups the BufferedReader for user input.
        InputStreamReader inputStream = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(inputStream);

        System.out.println("Success!");

        program: while(true) {
            // Prints menu.
            System.out.print("\nMenu:\n" +
              "    (AP) - Add a new page to the graph.\n" +
              "    (RP) - Remove a page from the graph.\n" +
              "    (AL) - Add a link between pages in the graph.\n" +
              "    (RL) - Remove a link between pages in the graph.\n" +
              "    (P)  - Print the graph.\n" +
              "    (S)  - Search for pages with a keyword.\n" +
              "    (Q)  - Quit.\n\n" +
              "Please select an option: ");

            String userInput = reader.readLine();

            if (userInput.equals("AP")) { // Add a new page to the graph.
                System.out.print("Enter a URL: ");
                String url = reader.readLine();
                System.out.print("Enter keywords (space-separated): ");

                try {
                    web.addPage(url,
                      Arrays.asList(reader.readLine().trim().split("\\s")));
                    // Gets the userInput, trims it, then converts it into
                    // Collection of String.

                    System.out.println("\n" + url + " successfully added to " +
                      "the WebGraph!");
                } catch (IllegalArgumentException e) {
                    System.out.println("\n" + e.getMessage());
                }
            } else if (userInput.equals("RP")) { // Remove a page from the graph
                System.out.print("Enter a URL: ");
                String url = reader.readLine();

                web.removePage(url);

                System.out.print("\n" + url + " has been removed from the " +
                  "graph!\n");
            } else if (userInput.equals("AL")) { // Add a link between pages
                // in the graph.
                System.out.print("Enter a source URL: ");
                String source = reader.readLine();
                System.out.print("Enter a destination URL: ");
                String destination = reader.readLine();

                try {
                    web.addLink(source, destination);
                    System.out.println("\nLink successfully added from " +
                      source + " to " + destination + "!");
                } catch (IllegalArgumentException e) {
                    System.out.println("\n" + e.getMessage());
                }
            } else if (userInput.equals("RL")) { // Remove a link between
                // pages in the graph.
                System.out.print("Enter a source URL: ");
                String source = reader.readLine();
                System.out.print("Enter a destination URL: ");
                String destination = reader.readLine();

                web.removeLink(source,destination);

                System.out.println("\nLink removed from " + source + " to " +
                  destination + "!");
            } else if (userInput.equals("P")) {
                System.out.print("\n    (I) Sort based on index (ASC)\n" +
                  "    (U) Sort based on URL (ASC)\n" +
                  "    (R) Sort based on rank (DSC)\n\n" +
                  "Please select an option: ");

                userInput = reader.readLine();

                if (userInput.equals("I")) {
                    web.sortByIndex();
                    web.printTable();
                } else if (userInput.equals("U")) {
                    web.sortByURL();
                    web.printTable();
                } else if (userInput.equals("R")) {
                    web.sortByRank();
                    web.printTable();
                } else {
                    System.out.println("\nInvalid input.");
                }
            } else if (userInput.equals("S")) {
                System.out.print("Search keyword: ");
                String keyword = reader.readLine();

                System.out.println();
                web.printFromKeyword(keyword);
            } else if (userInput.equals("Q")) {
                System.out.println("\nGoodbye.");
                break program;
            } else {
                System.out.println("\nInvalid input.");
            }
        }
    }
}
