/**
 * @author
 * Yugui Ke
 * 113778667
 * Recitation 3
 * yugui.ke@stonybrook.edu
 */

import java.util.Comparator;

public class URLComparator implements Comparator {
    /**
     * Sorts alphabetically ascending based on URL of the WebPage.
     */
    public int compare(Object o1, Object o2) {
        WebPage p1 = (WebPage) o1;
        WebPage p2 = (WebPage) o2;

        return (p1.getUrl().compareTo(p2.getUrl()));
    }
}
