/**
 * @author
 * Yugui Ke
 * 113778667
 * Recitation 3
 * yugui.ke@stonybrook.edu
 */

public class TrainLinkedList {
    private TrainCarNode head;
    private TrainCarNode tail;
    private TrainCarNode cursor;

    private int size;
    private double totalCarLength;
    private double totalValue;
    private double totalCarWeight;
    private double totalLoadWeight;
    private boolean isDangerous;
    private int cursorLocation;

    /**
     * Constructs an instance of the <i>TrainLinkedList</i> with nothing in it.
     * <br><i><b>Post-conditions:</b></i><br>
     *     This <i>TrainLinkedList</i> has been initialized to an empty linked list.
     *     <br>Head, tail, and cursor are all set to null.
     */
    public TrainLinkedList() {
        size = 0;
        totalCarLength = 0;
        totalValue = 0;
        totalCarWeight = 0;
        totalLoadWeight = 0;
        isDangerous = false;
        cursorLocation = 0;
    }

    /**
     * Returns a reference to the <i>TrainCar</i> at the current cursor's node.
     * <br><i><b>Pre-conditions:</b></i><br>
     *     The list is not empty (cursor is not null).
     * @return The reference to the <i>TrainCar</i> at the current cursor's
     * node.
     */
    public TrainCar getCursorData() {
        return cursor.getCar();
    }

    /**
     * Places <i>car</i> in the node current referenced by cursor.
     * <br><i><b>Pre-conditions:</b></i><br>
     *     The list is not empty (cursor is not null).
     * <br><i><b>Post-conditions:</b></i><br>
     *     The cursor node now contains reference to <i>car</i> as its data.
     * @param car new <i>car</i> to be placed in the node.
     */
    public void setCursorData(TrainCar car) {
        if (!cursor.getCar().isEmpty()) {
            // Removes the previous values that is inside the cursor.
            totalLoadWeight -= getCursorData().getProductLoad().getWeight();
            totalValue -= getCursorData().getProductLoad().getValue();
        }
        cursor.setCar(car);
        // Adds in the new values.
        totalLoadWeight += car.getProductLoad().getWeight();
        totalValue += car.getProductLoad().getValue();
        if (car.getProductLoad().isDangerous()) {
            isDangerous = true;
        }
    }

    /**
     * Moves the cursor to point at the next <i>TrainCarNode</i>
     * <br><i><b>Pre-conditions:</b></i><br>
     *     The list is not empty (cursor is not null) and cursor does not
     *     currently reference the tail of the list.
     * <br><i><b>Post-conditions:</b></i><br>
     *     The cursor has been advanced to the next <i>TrainCarNode</i>, or
     *     has remained at the tail of the list.
     * @throws IllegalArgumentException Thrown when cursor pointed to tail or
     * when cursor is null.
     */
    public void cursorForward() throws IllegalArgumentException {
        if (cursor == null) {
            throw new IllegalArgumentException("Cursor is null.");
        }
        if (cursor.getNext() != null) {
            cursor = cursor.getNext();
            cursorLocation++;
        } else {
            throw new IllegalArgumentException("No next car, cannot move " +
              "cursor forward.");
        }
    }

    /**
     * Moves the cursor to point at the previous <i>TrainCarNode</i>
     * <br><i><b>Pre-conditions:</b></i><br>
     *     The list is not empty (cursor is not null) and cursor does not
     *     currently reference the head of the list.
     * <br><i><b>Post-conditions:</b></i><br>
     *     The cursor has been moved back to the previous
     *     <i>TrainCarNode</i>, or has remained at the head of the list.
     * @throws IllegalArgumentException Thrown when cursor is pointed to head
     * or when cursor is null.
     */
    public void cursorBackward() throws IllegalArgumentException {
        if (cursor == null) {
            throw new IllegalArgumentException("Cursor is null.");
        }
        if (cursor.getPrev() != null) {
            cursor = cursor.getPrev();
            cursorLocation--;
        } else {
            throw new IllegalArgumentException("No previous car, cannot move " +
              "backward.");
        }
    }

    /**
     * Inserts a car into the train after the cursor position.
     * <br><i><b>Pre-conditions:</b></i><br>
     *     This <i>TrainCar</i> object has been instantiated.
     * <br><i><b>Post-conditions:</b></i><br>
     *     The new <i>TrainCar</i> has been inserted into the train after the
     *     position of the cursor. <br>
     *     All <i>TrainCar</i> objects previously on the train are still on
     *     the train, and their order has been preserved. <br>
     *     The cursor now points to the inserted car.
     * @param newCar the new <i>TrainCar</i> to be inserted into the train.
     * @throws IllegalArgumentException Indicates that <i>newCar</i> is null.
     */
    public void insertAfterCursor(TrainCar newCar)
      throws IllegalArgumentException {
        if (newCar == null) {
            throw new IllegalArgumentException("newCar cannot be null.");
        }

        if (!newCar.isEmpty()) {
            if (newCar.getProductLoad().isDangerous()) {
                isDangerous = true;
            }
            totalLoadWeight += newCar.getProductLoad().getWeight();
            totalValue += newCar.getProductLoad().getValue();
        }
        totalCarWeight += newCar.getCarWeight();
        totalCarLength += newCar.getCarLength();
        size++;
        cursorLocation++;

        TrainCarNode newNode = new TrainCarNode(newCar);

        if (cursor != null) {
            newNode.setPrev(cursor);
            newNode.setNext(cursor.getNext());
            if (cursor.getNext() != null) {
                cursor.getNext().setPrev(newNode);
            }
            cursor.setNext(newNode);
        }

        // Checks if the new Node is going to be a head or tail.
        if (newNode.getPrev() == null) {
            head = newNode;
        }
        if (newNode.getNext() == null) {
            tail = newNode;
        }

        if (cursor != null) {
            cursor = cursor.getNext();
        } else {
            cursor = newNode;
        }
    }

    /**
     * Removes the <i>TrainCarNode</i> referenced by the cursor and returns
     * the <i>TrainCar</i> contained within the node.
     * <br><i><b>Pre-conditions:</b></i><br>
     *     The cursor is not null.
     * <br><i><b>Post-conditions:</b></i><br>
     *     The <i>TrainCarNode</i> referenced by the cursor has been removed
     *     from the train. <br>
     *     The cursor now reference the next node, or the previous node if no
     *     next node exist.
     * @return <i>TrainCar</i> contained within the node that is removed.
     * @throws IllegalArgumentException If there is no cursor.
     */
    public TrainCar removeCursor() {
        if (cursor == null) {
            throw new IllegalArgumentException("No cursor found.");
        }

        TrainCar removed = getCursorData();

        if (cursor.getNext() != null && cursor.getPrev() != null) {
            cursor.getPrev().setNext(cursor.getNext());
            cursor.getNext().setPrev(cursor.getPrev());
            cursor = cursor.getNext();
        } else if (cursor.getNext() == null && cursor.getPrev() != null) {
            cursor.getPrev().setNext(null);
            cursor = cursor.getPrev();
            cursorLocation--;
            tail = cursor;
        } else if (cursor.getNext() != null && cursor.getPrev() == null) {
            cursor.getNext().setPrev(null);
            cursor = cursor.getNext();
            head = cursor;
        } else {
            cursor = null;
            cursorLocation = 0;
            head = null;
            tail = null;
        }

        // If the the removed contains dangerous content, the isDangerous
        // variable needs to be re-verified.
        if (!removed.isEmpty()) {
            if (removed.getProductLoad().isDangerous()) {
                // Creates a tempNode before head to cycle through all nodes.
                TrainCarNode tempNode = new TrainCarNode();
                tempNode.setNext(head);
                boolean stillDangerous = false;
                while (tempNode.getNext() != null) {
                    tempNode = tempNode.getNext();
                    if (!tempNode.getCar().isEmpty()) {
                        if (tempNode.getCar().getProductLoad().isDangerous()) {
                            stillDangerous = true;
                            break;
                        }
                    }
                }
                isDangerous = stillDangerous;
            }
        }
        totalCarLength -= removed.getCarLength();
        totalCarWeight -= removed.getCarWeight();
        size--;

        // When removing the last node of the list.
        if (cursorLocation > size()) {
            cursorLocation = size();
        }

        if (!removed.isEmpty()) {
            totalValue -= removed.getProductLoad().getValue();
            totalLoadWeight -= removed.getProductLoad().getWeight();
        }

        return removed;
    }

    /**
     * Determines the number of <i>TrainCar</i> objects currently on the train.
     * @return The number of <i>TrainCar</i> objects on this train.
     */
    public int size() {
        return size;
    }

    /**
     * Returns the total length of the train in meters.
     * @return The sum of the lengths of each <i>TrainCar</i> in the train.
     */
    public double getLength() {
        return totalCarLength;
    }

    /**
     * Returns the total value of product carried by the train.
     * @return The sum of the values of each <i>TrainCar</i> in the train.
     */
    public double getValue() {
        return totalValue;
    }

    /**
     * Returns the total weight in tons of the train.
     * @return The sum of the weight of each <i>TrainCar</i> plus the sum of
     * the <i>ProductLoad</i> carried by that car.
     */
    public double getWeight() {
        return totalCarWeight + totalLoadWeight;
    }

    /**
     * Whether or not there is a dangerous product on one of the <i>TrainCar</i>
     *  objects on the train.
     * @return Returns <i>true</i> if the train contains at least one
     * <i>TrainCar</i> carrying a dangerous <i>ProductLoad</i>, <i>false</i>
     * otherwise.
     */
    public boolean isDangerous() {
        return isDangerous;
    }

    /**
     * Searches the list for all <i>ProductLoad</i> objects with the
     * indicated name and sums together their weight and value (Also keeps
     * track of whether the product is dangerous or not), then prints a
     * single <i>ProductLoad</i> record to the console.
     * @param name the name of the <i>productLoad</i> to find on the train.
     */
    public void findProduct(String name) {
        TrainCarNode searchNode = new TrainCarNode();
        searchNode.setPrev(tail);

        double value = 0;
        double weight = 0;
        boolean dangerous = false;
        boolean found = false;

        // Looping from tail to head b/c dangerousness should be set to
        // "first match found", thus the last one iterated by this loop.
        while (searchNode.getPrev() != null) {
            searchNode = searchNode.getPrev();
            if (!searchNode.getCar().isEmpty()) {
                if (searchNode.getCar().getProductLoad().getName().equals(name))
                {
                    value += searchNode.getCar().getProductLoad().getValue();
                    weight += searchNode.getCar().getProductLoad().getWeight();
                    dangerous =
                      searchNode.getCar().getProductLoad().isDangerous();
                    found = true;
                }
            }
        }

        if (found) {
            System.out.println("\n        Name      Weight (t)     " +
              "Value ($)   Dangerous\n    =======================" +
              "============================");
            System.out.println(String.format("%12s%16s%14s%12s",
              name,weight,String.format("%,.2f",value),dangerous ?
              "YES" : "NO"));
        } else {
            System.out.println("No record of " + name + " on board train.");
        }
    }

    /**
     * Prints a neatly formatted table of the car number, car length, car
     * weight, load name, load weight, load value, and load dangerousness for
     * all of the car on the train.
     */
    public void printManifest() {
        // Prints the first three lines (headers).
        System.out.println("    CAR:                               LOAD:\n" +
          "      Num   Length (m)    Weight (t)  |    Name      Weight (t)   " +
          "  Value ($)   Dangerous\n    ==================================+=" +
          "==================================================");

        TrainCarNode iteratingNode = new TrainCarNode();
        iteratingNode.setNext(head);
        int row = 0;
        while (iteratingNode.getNext() != null) {
            iteratingNode = iteratingNode.getNext();
            row++;

            // Default values if LOAD is empty.
            String name = "Empty";
            double weight = 0.0;
            double value = 0.0;
            String danger = "NO";

            // Sets different values if LOAD is not empty.
            if (!iteratingNode.getCar().isEmpty()) {
                name = iteratingNode.getCar().getProductLoad().getName();
                weight = iteratingNode.getCar().getProductLoad().getWeight();
                value = iteratingNode.getCar().getProductLoad().getValue();
                danger =iteratingNode.getCar().getProductLoad().isDangerous()
                  ? "YES" : "NO";
            }

            // Prints an arrow if it is where the cursor is.
            System.out.print(row == cursorLocation ? " ->" : "   ");

            // Prints out the rest of information.
            System.out.println(String.format("%5s%14s%14s  |%10s%14s%14s%12s",
              row,iteratingNode.getCar().getCarLength(),
              iteratingNode.getCar().getCarWeight(),name,weight,
              String.format("%,.2f",value),danger));
        }
        System.out.println();
    }

    /**
     * Removes all dangerous cars from the train, maintaining the order of
     * the cars in the train.
     * <br><i><b>Post-conditions:</b></i><br>
     *     All dangerous cars have been removed from this train.<br>
     *     The order of all non-dangerous cars must be maintained upon the
     *     completion of this method. <br>
     *     If the cursor was on a node that contained a dangerous car, the
     *     cursor would then be set to the head of the linked list.
     */
    public void removeDangerousCars() {
        // If cursor is null (There is nothing in list).
        if (cursor == null) {
            return;
        }

        // Loops with the current cursor until the current cursor doesn't
        // contain a dangerous car.
        while (!getCursorData().isEmpty()) {
            if (getCursorData().isEmpty()) {
                break;
            } else {
                if (getCursorData().getProductLoad().isDangerous()) {
                    removeCursor();
                } else {
                    break;
                }
            }
        }
        // Setup where the cursor will return to after its iterations.
        TrainCarNode originalCursor = cursor;
        int originalCursorPosition = cursorLocation;

        // Iterates backwards from the current cursor.
        while (cursor.getPrev() != null) {
            cursor = cursor.getPrev();
            if (!getCursorData().isEmpty()) {
                if (getCursorData().getProductLoad().isDangerous()) {
                    // Since this is iterating backwards, the cursor will
                    // always land in the next position, which gets iterated
                    // backwards to a new node.
                    removeCursor();

                    // Position of the cursor shifts as Cars get deleted.
                    originalCursorPosition--;
                }
            }
        }

        cursor = originalCursor;

        // Iterates forward from the current cursor.
        while (cursor.getNext() != null) {
            cursor = cursor.getNext();
            if (!getCursorData().isEmpty()) {
                if (getCursorData().getProductLoad().isDangerous()) {
                    if (cursor.getNext() == null) {
                        // If there is no next, after removing the node, it
                        // will go to the previous node, which will not
                        // iterate anymore.
                        removeCursor();
                    } else {
                        // If there is a next node, then it will go to the
                        // next node, and iterate into the next-next node.
                        // Therefore, the node need to be set to the previous
                        // node after the removal to prevent that from
                        // happening.
                        removeCursor();
                        cursor = cursor.getPrev();
                    }
                }
            }
        }

        // Sets the cursor back to where it's suppose to be.
        cursorLocation = originalCursorPosition;
        cursor = originalCursor;
    }

    /**
     * Returns a neatly formatted String representation of the train.
     * @return A neatly formatted string containing information about the
     * train, including it's size (number of cars), length in meters, weight
     * in tons, value in dollars, and whether it is dangerous or not.
     */
    public String toString() {
        return "Train: " + size() + " cars, " + getLength() + " meters, " +
          getWeight() + " tons, $" + String.format("%,.2f",getValue()) +
          " value, " + (isDangerous() ? "DANGEROUS." : "not dangerous.");
    }
}
