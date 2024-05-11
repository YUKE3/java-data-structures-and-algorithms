/**
 * @author
 * Yugui Ke
 * 113778667
 * Recitation 3
 * yugui.ke@stonybrook.edu
 */

public class TrainCarNode {
    private TrainCarNode prev;
    private TrainCarNode next;
    private TrainCar car;

    /**
     * Creates a new node with nothing in it.
     */
    public TrainCarNode() {}

    /**
     * Creates a new node containing passed <i>TrainCar</i> object.
     * @param car <i>TrainCar</i> object in new <i>TrainCarNode</i>.
     */
    public TrainCarNode(TrainCar car) {
        this.car = car;
    }

    /**
     * Gets the previous <i>TrainCarNode</i>.
     * @return previous <i>TrainCarNode</i>.
     */
    public TrainCarNode getPrev() {
        return prev;
    }

    /**
     * Sets the previous <i>TrainCarNode</i>.
     * @param node New <i>TrainCarNode</i> to be set as previous.
     */
    public void setPrev(TrainCarNode node) {
        prev = node;
    }

    /**
     * Gets the next <i>TrainCarNode</i>.
     * @return returns next <i>TrainCarNode</i>.
     */
    public TrainCarNode getNext() {
        return next;
    }

    /**
     * Sets the next <i>TrainCarNode</i>.
     * @param node New <i>TrainCarNode</i> to be set as next.
     */
    public void setNext(TrainCarNode node) {
        next = node;
    }

    /**
     * Gets the <i>TrainCar</i> object contained in this node.
     * @return <i>TrainCar</i> object.
     */
    public TrainCar getCar() {
        return car;
    }

    /**
     * Sets the <i>TrainCar</i> object contained in this node.
     * @param car new <i>TrainCar</i> object.
     */
    public void setCar(TrainCar car) {
        this.car = car;
    }

    /**
     * Translates this node into String format.
     * @return String containing information about this node.
     */
    public String toString() {
        //TODO: Return an actual string.
        return "" + getCar().getCarLength();
    }
}
