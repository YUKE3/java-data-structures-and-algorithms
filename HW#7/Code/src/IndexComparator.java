/**
 * @author
 * Yugui Ke
 * 113778667
 * Recitation 3
 * yugui.ke@stonybrook.edu
 */

import java.util.Comparator;

public class IndexComparator implements Comparator {
    /**
     * compare method for comparison, compares by index. sorts descending.
     * @param o1 WebPage to be compared.
     * @param o2 WebPage to be compared to.
     * @return returns 0 if index is equal, 1 if this index is greater, -1
     * otherwise.
     */
    public int compare(Object o1, Object o2) {
        WebPage p1 = (WebPage) o1;
        WebPage p2 = (WebPage) o2;

        if (p1.getIndex() == p2.getIndex())
            return 0;
        else if (p1.getIndex() > p2.getIndex())
            return 1;
        else
            return -1;
    }
}
