/**
 * @author
 * Yugui Ke
 * 113778667
 * Recitation 3
 * yugui.ke@stonybrook.edu
 */

import java.util.Collection;

public class WebPage {
    private String url;
    private int index;
    private int rank;
    private Collection<String> keywords;

    /**
     * Empty constructor.
     */
    public WebPage() {}

    /**
     * WebPage constructor with all member variables except rank.
     * @param url URL of the WebPage.
     * @param keywords Collections of keywords associated with this WebPage.
     * @param index The index of this WebPage in the WebGraph.
     */
    public WebPage(String url, Collection<String> keywords, int index) {
        this.url = url;
        this.index = index;
        this.keywords = keywords;
    }

    /**
     * Gets the URL of this WebPage.
     * @return returns the URL of this WebPage.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Gets the index of this WebPage.
     * @return returns the index of this WebPage.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Gets the rank of this WebPage.
     * @return returns the rank of this WebPage.
     */
    public int getRank() {
        return rank;
    }

    /**
     * Gets the keywords associated with this WebPage.
     * @return returns a collection of String containing the keywords.
     */
    public Collection<String> getKeywords() {
        return keywords;
    }

    /**
     * Sets the index of this WebPage.
     * @param index the new index of this WebPage.
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Sets the rank of this WebPage.
     * @param rank the new rank for this WebPage.
     */
    public void setRank(int rank) {
        this.rank = rank;
    }

    /**
     * Get a String containing all of this WebPage's details.<br>
     * Contains "***" in the String to be replaced with external information.
     * @return returns a String with this WebPage's information.
     */
    public String toString() {
        return String.format("   %-4s| %-19s|***| "
          + keywords.toString().substring(1,keywords.toString().length()-1),
          index,url);
    }
}
