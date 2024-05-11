/**
 * @author
 * Yugui Ke
 * 113778667
 * Recitation 3
 * yugui.ke@stonybrook.edu
 */

public class TrainCar {
    private double carLength;
    private double carWeight;
    private ProductLoad load;

    /**
     * Creates a new <i>TrainCar</i> object with given values.
     * @param carWeight Weight of the car in <i>double</i>.
     * @param carLength Length of the car in <i>double</i>.
     */
    public TrainCar(double carWeight, double carLength) {
        this.carLength = carLength;
        this.carWeight = carWeight;
    }

    /**
     * Gets the length of the <i>TrainCar</i>.
     * @return Return a double that represents the length.
     */
    public double getCarLength() {
        return carLength;
    }

    /**
     * Gets the weight of the <i>TrainCar</i>.
     * @return Returns a value that represents the weight.
     */
    public double getCarWeight() {
        return carWeight;
    }

    /**
     * Gets the load of the <i>TrainCar</i>.
     * @return Returns a <i>ProductLoad</i> object representing the
     * load.
     */
    public ProductLoad getProductLoad() {
        return load;
    }

    /**
     * Sets the load of the <i>TrainCar</i> to a give load.
     * @param load New load of the <i>TrainCar</i>.
     */
    public void setProductLoad(ProductLoad load) {
        this.load = load;
    }

    /**
     * Whether the car is empty or not.
     * @return Returns a <i>boolean</i> representing if the car is empty
     * or not.
     */
    public boolean isEmpty() {
        return load == null;
    }
}
