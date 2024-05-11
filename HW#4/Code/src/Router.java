/**
 * @author
 * Yugui Ke
 * 113778667
 * Recitation 3
 * yugui.ke@stonybrook.edu
 */

import java.util.Collection;
import java.util.LinkedList;

public class Router extends LinkedList {
    /**
     * Empty constructor to instantiate this object.
     */
    public Router() {}

    /**
     * Adds a <i>Packet</i> to the <i>Router</i>'s queue.
     * @param p <i>Packet</i> object to be added.
     */
    public void enqueue(Packet p) {
        add(p);
    }

    /**
     * Revmoes a <i>Packet</i> from the <i>Router</i>'s queue.
     * @return <i>Packet</i> object at the start of <i>Router</i>'s queue.
     */
    public Packet dequeue() {
        return (Packet)removeFirst();
    }

    /**
     * Peeks at the first <i>Packet</i> in the queue.
     * @return <i>Packet</i> Object at the start of <i>Router</i>'s queue.
     */
    public Packet peek() {
        return (Packet)peekFirst();
    }

    // Size un-necessary, extended from LinkedList.
    // isEmpty un-necessary,

    /**
     * Creates a human-readable form of <i>Router</i>'s contents.
     * @return <i>String</i> containing all the <i>Packet</i> in the queue.
     */
    public String toString() {
        String output = "{";
        Object[] givenArray = toArray();
        for (Object p : givenArray) {
            output += p + ",";
        }
        // If there is nothing added.
        if (output.equals("{")) {
            return "{}";
        }
        return output.substring(0,output.length()-1) + "}";
    }

    /**
     * Determines which <i>Router</i> has the most buffer space from a
     * <i>Collection</i> of <i>Router</i>.<br/>
     * Checking if the <i>Collection</i> have filled up <i>Router</i> is done
     * in <i>Simulator</i> class.
     * @param router <i>Collection</i> of <i>Router</i> to search.
     * @return <i>int</i> representing the index of free <i>Router</i> in the
     * <i>Collection</i>
     * @throws IllegalArgumentException If the <i>Collection</i> is empty.
     */
    public static int sendPacketTo(Collection router)
      throws IllegalArgumentException {
        Object[] routerArray = router.toArray();
        if (routerArray.length == 0) {
            throw new IllegalArgumentException("Collection is empty.");
        }
        int freeRouter = 0;
        for (int i = 1; i < routerArray.length; i++) {
            if (((Router)routerArray[i]).size() <
              ((Router)routerArray[freeRouter]).size()) {
                freeRouter = i;
            }
        }
        // Returns the router with least load
        // Returns zero when that router is the one with least load OR when
        // all routers are the same size. (Which may mean everything is full)
        // Checking if the Collection have filled up Routers are done in
        // Simulator class.
        return freeRouter;
    }
}
