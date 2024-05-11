/**
 * @author
 * Yugui Ke
 * 113778667
 * Recitation 3
 * yugui.ke@stonybrook.edu
 */

public class Packet {
    private static int packetCount = 0;

    private int id;
    private int packetSize;
    private int timeArrive;
    private int timeToDest;

    /**
     * Constructor.
     * Sets the Id of <i>Packet</i> to packetCount+1.
     */
    public Packet() {
        packetCount++;
        setId(packetCount);
    }

    /**
     * Sets the packetCount.
     * @param packetCount <i>int</i> representing the packetCount.
     */
    public static void setPacketCount(int packetCount) {
        Packet.packetCount = packetCount;
    }

    /**
     * Gets the packetCount.
     * @return <i>int</i> representing the packetCount.
     */
    public static int getPacketCount() {
        return packetCount;
    }

    /**
     * Sets the Id.
     * @param id <i>int</i> representing new Id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the Id.
     * @return <i>int</i> representing Id.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the packetSize.
     * @param packetSize <i>int</i> representing new packetSize.
     */
    public void setPacketSize(int packetSize) {
        this.packetSize = packetSize;
    }

    /**
     * Gets the packetSize
     * @return <i>int</i> representing the packetSize.
     */
    public int getPacketSize() {
        return packetSize;
    }

    /**
     * Sets the timeArrive.
     * @param timeArrive <i>int</i> representing new timeArrive.
     */
    public void setTimeArrive(int timeArrive) {
        this.timeArrive = timeArrive;
    }

    /**
     * Gets the timeArrive.
     * @return <i>int</i> representing timeArrive.
     */
    public int getTimeArrive() {
        return timeArrive;
    }

    /**
     * Sets the timeToDest.
     * @param timeToDest <i>int</i> representing new timeToDest.
     */
    public void setTimeToDest(int timeToDest) {
        this.timeToDest = timeToDest;
    }

    /**
     * Gets the timeToDest.
     * @return <i>int</i> representing timeToDest.
     */
    public int getTimeToDest() {
        return timeToDest;
    }

    /**
     * Human-readable format for <i>Packet</i>
     * @return <i>String</i> containing id, timeArrive, timeToDest.
     */
    public String toString() {
        return "[" + id + ", " + timeArrive + ", " + timeToDest + "]";
    }
}
