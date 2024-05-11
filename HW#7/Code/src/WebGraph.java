/**
 * @author
 * Yugui Ke
 * 113778667
 * Recitation 3
 * yugui.ke@stonybrook.edu
 */

import java.io.*;
import java.util.*;

public class WebGraph {
    public static final int MAX_PAGES = 40;

    private Collection<WebPage> pages;
    private ArrayList<ArrayList<Integer>> edges;

    private ArrayList<String> index;

    /**
     * Build a WebGraph from two files.
     * @param pagesFile file containing all the pages.
     * @param linksFile file containing links between these pages.
     * @return returns a WebGraph created with these files.
     * @throws IllegalArgumentException thrown when the given files are not
     * valid in some way.
     */
    public static WebGraph buildFromFiles(String pagesFile, String linksFile)
      throws IllegalArgumentException {

        try {
            // Hashtable used for index.
            ArrayList<String> index = new ArrayList<String>();

            // File input stuff.
            FileInputStream fis = new FileInputStream(pagesFile);
            InputStreamReader inStream = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(inStream);

            ArrayList<WebPage> pages = new ArrayList<WebPage>();

            String line = reader.readLine();
            while (line != null) {
                // Splits line into data.
                line = line.trim();
                String[] data = line.split("\\s");

                index.add(data[0]);
                // Creates new WebPage, then add into pages.
                WebPage page = new WebPage(data[0],
                  Arrays.asList(Arrays.copyOfRange(data,1,data.length)),
                  pages.size());
                pages.add(page);

                // Reads the next line in the file.
                line = reader.readLine();
            }

            // Resets the file reader for the second file.
            fis = new FileInputStream(linksFile);
            inStream = new InputStreamReader(fis);
            reader = new BufferedReader(inStream);

            // Creates the edges double array.
            ArrayList<ArrayList<Integer>> edges =
              new ArrayList<ArrayList<Integer>>();

            // Makes the double array filled with zeros.
            for (int i = 0; i < pages.size(); i++) {
                edges.add(new ArrayList<Integer>());

                for (int u = 0; u < pages.size(); u++) {
                    edges.get(i).add(0);
                }
            }

            line = reader.readLine();
            while (line != null) {
                line = line.trim();
                String[] data = line.split("\\s");

                edges.get(index.indexOf(data[0])).set(index.indexOf(data[1]),1);

                line = reader.readLine();
            }

            return new WebGraph(pages,edges,index);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File not found.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new IllegalArgumentException();
    }

    /**
     * Add a new WebPage to the WebGraph.
     * @param url The URL of the new WebPage.
     * @param keywords The Keywords associated with the new Webpage.
     * @throws IllegalArgumentException Thrown when the parameters are
     * invalid, the WebGraph is full, or if the URL already exist in the
     * WebGraph.
     */
    public void addPage(String url, Collection<String> keywords)
      throws IllegalArgumentException {
        if (url == null || keywords == null) {
            throw new IllegalArgumentException("Parameters are null.");
        }

        if (index.contains(url)) {
            throw new IllegalArgumentException("ERROR: " + url + " already " +
              "exists in the WebGraph. Could not add a new WebPage.");
        }

        if (pages.size() > MAX_PAGES - 1) {
            throw new IllegalArgumentException("ERROR: WebGraph is full.");
        }

        index.add(url);
        pages.add(new WebPage(url,keywords,pages.size()));

        // Adds a zero to every row.
        for (int i = 0; i < edges.size(); i++) {
            edges.get(i).add(0);
        }
        // Adds a new row.
        edges.add(new ArrayList<Integer>());
        // Fill the new row with all zeros.
        for (int i = 0; i < edges.size(); i++) {
            edges.get(edges.size() - 1).add(0);
        }

        updatePageRanks();
    }

    /**
     * Adds a new Link between WebPages.
     * @param source The source of the link.
     * @param destination The destination of the link.
     * @throws IllegalArgumentException Thrown when the source of destination
     * is invalid.
     */
    public void addLink(String source, String destination)
      throws IllegalArgumentException {
        if (source == null || destination == null) {
            throw new IllegalArgumentException("Error: Parameters are null.");
        }

        if (!index.contains(source)) {
            throw new IllegalArgumentException("ERROR: " + source + " could " +
              "not be found in the WebGraph.");
        }

        if (!index.contains(destination)) {
            throw new IllegalArgumentException("ERROR: " + destination + " " +
              "could not be found in the WebGraph.");
        }

        // Sets the entry to 1 in the matrix.
        edges.get(index.indexOf(source)).set(index.indexOf(destination),1);

        updatePageRanks();
    }

    /**
     * Remove a WebPage from the WebGraph.
     * @param url The URL of the WebPage.
     */
    public void removePage(String url) {
        // When url isn't valid.
        if (index.indexOf(url) == -1) {
            return;
        }

        // row and column.
        edges.remove(index.indexOf(url));
        for (int i = 0; i < edges.size(); i++) {
            edges.get(i).remove(index.indexOf(url));
        }

        // Sorts before adjusting the index.
        sortByIndex();

        // Removes WebPage from pages.
        ((ArrayList)pages).remove(index.indexOf(url));

        // Adjusts the index.
        for (int i = index.indexOf(url); i < pages.size(); i++) {
            WebPage wp = ((WebPage)(((ArrayList)pages).get(i)));
            wp.setIndex(wp.getIndex()-1);
        }

        // Removes the reference from the index ArrayList.
        index.remove(url);

        updatePageRanks();
    }

    /**
     * Removes a link from the WebPage.
     * @param source The source of the link.
     * @param destination The destination of the link.
     */
    public void removeLink(String source, String destination) {
        // Either parameter is invalid.
        if (index.indexOf(source) == -1 || index.indexOf(destination) == -1) {
            return;
        }

        // Sets entry to zero at that spot.
        edges.get(index.indexOf(source)).set(index.indexOf(destination),0);

        updatePageRanks();
    }

    /**
     * Update the page rank of every single WebPage in the WebGraph.
     */
    public void updatePageRanks() {
        // Loops through all WebPage.
        for (WebPage page : pages) {
            int rank = 0;

            // Adds all number in a certain column.
            for (ArrayList<Integer> row : edges) {
                rank += row.get(index.indexOf(page.getUrl()));
            }

            // Sets the rank of the page.
            page.setRank(rank);
        }
    }

    /**
     * Prints the WebGraph in a human-readable table.
     */
    public void printTable() {
        // Prints the header.
        System.out.println("Index     URL               PageRank  Links" +
          "               Keywords\n-----------------------------------" +
          "------------------------------------------------------------" +
          "-------");

        // Cycles through every page.
        for (WebPage wp : pages) {
            // Creates a String for Links section.
            String links = "";
            ArrayList<Integer> array = edges.get(index.indexOf(wp.getUrl()));
            for (int i = 0; i < array.size(); i++) {
                if (array.get(i) == 1) {
                    links += " " + i + ",";
                }
            }

            // Removes the last ","
            if (!links.equals("")) {
                links = links.substring(0,links.length()-1);
            }

            // Prints out with new information (PageRank and Links).
            System.out.println(wp.toString().replaceAll("\\*\\*\\*",
              String.format("   %-5s|%-19s",wp.getRank(),links)));
        }
    }

    /**
     * Constructor for the WebGraph.
     * @param pages Pages of the WebGraph.
     * @param edges The links of the WebGraph.
     * @param index A reference for internal use.
     */
    public WebGraph(Collection<WebPage> pages,
      ArrayList<ArrayList<Integer>> edges, ArrayList<String> index) {
        this.pages = pages;
        this.edges = edges;
        this.index = index;

        updatePageRanks();
    }

    /**
     * Sorts the WebGraph by URL.
     */
    public void sortByURL() {
        Collections.sort((ArrayList)pages,new URLComparator());
    }

    /**
     * Sorts the WebGraph by Index.
     */
    public void sortByIndex() {
        Collections.sort((ArrayList)pages, new IndexComparator());
    }

    /**
     * Sorts the WebGraph by Rank.
     */
    public void sortByRank() {
        Collections.sort((ArrayList)pages,new RankComparator());
    }

    /**
     * Prints a human read-able table containing Pages containing
     * @param keyword Keyword to be matched.
     */
    public void printFromKeyword(String keyword) {
        ArrayList<WebPage> list = new ArrayList<WebPage>();

        for (WebPage wp : pages) {
            if (wp.getKeywords().contains(keyword)) {
                list.add(wp);
            }
        }

        if (list.size() == 0) {
            System.out.println("No search results found for the keyword "
              + keyword + "."); // Prints if there is nothing from keyword.
        } else {
            System.out.println("Rank   PageRank    URL\n" +
              "---------------------------------------------");

            sortByRank();
            // Sorted by PageRank.
            for (int i = 0; i < list.size(); i++) {
                System.out.println(String.format("  %-3s|    %-6s| "
                  + list.get(i).getUrl(),i+1,list.get(i).getRank()));
            } // Uses i+1 as the "Rank"
        }
    }
}
