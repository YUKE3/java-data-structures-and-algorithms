/**
 * @author
 * Yugui Ke
 * 113778667
 * Recitation 3
 * yugui.ke@stonybrook.edu
 */

import java.util.Comparator;

public class RankComparator implements Comparator {
    /**
     * Sorts numerically descending based on the rank of the WebPage.
     */
    public int compare(Object o1, Object o2) {
        WebPage p1 = (WebPage) o1;
        WebPage p2 = (WebPage) o2;

        if (p1.getRank() == p2.getRank()) {
            return 0;
        } else if (p1.getRank() > p2.getRank()) {
            return -1;
        } else {
            return 1;
        }
    }
}
