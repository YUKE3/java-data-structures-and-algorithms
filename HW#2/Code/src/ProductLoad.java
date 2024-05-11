/**
 * @author
 * Yugui Ke
 * 113778667
 * Recitation 3
 * yugui.ke@stonybrook.edu
 */

public class ProductLoad {
    private String name;
    private double weight;
    private double value;
    private boolean isDangerous;

    /**
     * Constructs new ProductLoad object with given value.
     * @param name Name of the product.
     * @param weight Weight of the product.
     * @param value Value of the product.
     * @param isDangerous If the product is dangerous.
     */
    public ProductLoad(String name, double weight, double value,
      boolean isDangerous) {
        this.name = name;
        this.weight = weight;
        this.value = value;
        this.isDangerous = isDangerous;
    }

    /**
     * Gets the name of the product.
     * @return Returns <i>String</i> containing the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name to the passed <i>String</i>.
     * @param name New name for the product in <i>String</i>.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the weight of the product.
     * @return Returns a <i>double</i> representing the weight.
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Sets the weight of the product.
     * @param weight New weight of the product in <i>double</i>.
     * @throws IllegalArgumentException Throws exception if passed value
     * isn't positive or a <i>double</i> object.
     */
    public void setWeight(double weight) throws IllegalArgumentException{
        if (weight < 0) {
            throw new IllegalArgumentException("Weight cannot be negative.");
        }
        this.weight = weight;
    }

    /**
     * Gets the value of the product.
     * @return Returns a <i>double</i> representing the value.
     */
    public double getValue() {
        return value;
    }

    /**
     * Sets the value of the product.
     * @param value New value of the product in <i>double</i>.
     * @throws IllegalArgumentException Throws exception if passed value
     * isn't positive or a double object.
     */
    public void setValue(double value) throws IllegalArgumentException {
        if (value < 0) {
            throw new IllegalArgumentException("Value cannot be negative.");
        }
        this.value = value;
    }

    /**
     * Checks if product is dangerous.
     * @return Returns a <i>boolean</i> of whether is product is dangerous or
     * not.
     */
    public boolean isDangerous() {
        return isDangerous;
    }

    /**
     * Sets if the product is dangerous or not.
     * @param danger <i>boolean</i> representing danger.
     */
    public void setDangerous(boolean danger) {
        isDangerous = danger;
    }
}
