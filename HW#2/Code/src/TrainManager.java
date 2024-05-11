/**
 * @author
 * Yugui Ke
 * 113778667
 * Recitation 3
 * yugui.ke@stonybrook.edu
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TrainManager {
    public static void main(String[] args) {
        // Instantiates bufferedReader for userInput.
        InputStreamReader streamReader = new InputStreamReader(System.in);
        BufferedReader bR = new BufferedReader(streamReader);

        boolean program = true; // Variable to keep program running.

        String input = ""; // String object to hold user's input.
        TrainLinkedList list = new TrainLinkedList(); // Linked list.

        while (program) {
            System.out.println("\n(F) Cursor Forward\n" +
              "(B) Cursor Backward\n(I) Insert Car After Cursor\n" +
              "(R) Remove Car At Cursor\n(L) Set Product Load\n" +
              "(S) Search For Product\n(T) Display Train\n" +
              "(M) Display Manifest\n(D) Remove Dangerous Cars\n" +
              "(Q) Quit\n");
            System.out.print("Enter a selection: ");
            input = getUserInput(bR).toLowerCase(); // Case-insensitive.
            if (input.length() == 1) {
                System.out.println(); // Prints an empty line.
                switch (input.charAt(0)) {
                    case 'f': // Cursor Forward
                        try {
                            list.cursorForward();
                            System.out.println("Cursor moved forward");
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 'b': // Cursor Backward
                        try {
                            list.cursorBackward();
                            System.out.println("Cursor moved backward");
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 'i': // Insert Car After Cursor
                        try {
                            insertCar(bR, list);
                        } catch (NumberFormatException e) {
                            System.out.println("\nNumber inputted incorrectly" +
                              ".");
                        } catch (IllegalArgumentException e) {
                            System.out.println("\n" + e.getMessage());
                        }
                        break;
                    case 'r': // Remove Car At Cursor
                        try {
                            TrainCar removed = list.removeCursor();
                            System.out.println("Car successfully unlinked. " +
                               "The following load has been removed from the " +
                               "train:");
                            System.out.println("        Name      Weight (t)" +
                              "     Value ($)   Dangerous\n    =============" +
                              "======================================");
                            if (!removed.isEmpty()) {
                                // Gets values and formats them.
                                System.out.println(String.format(
                                  "%12s%16s%14s%12s",
                                  removed.getProductLoad().getName(),
                                  removed.getProductLoad().getWeight(),
                                  removed.getProductLoad().getValue(),
                                  removed.getProductLoad().isDangerous() ?
                                  "YES" : "NO"));
                            } else {
                                // Prints everything with given nothing values.
                                System.out.println(String.format(
                                  "%12s%16s%14s%12s","Empty",0.0,0.0,"NO"));
                            }
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 's': // Search for Product
                        System.out.print("Enter product name: ");
                        list.findProduct(getUserInput(bR));
                        break;
                    case 'm': // Display Manifest
                        list.printManifest();
                        break;
                    case 'l': // Set Product Load
                        try {
                            setLoad(bR, list);
                        } catch (NumberFormatException e) {
                            System.out.println("Number inputted incorrectly" +
                              ".");
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 't': // Display Train
                        System.out.println(list);
                        break;
                    case 'd': // Remove Dangerous Cars
                        list.removeDangerousCars();
                        System.out.println("Dangerous cars successfully" +
                          " removed from the train.");
                        break;
                    case 'q': // Quit
                        System.out.println("Program terminating successfully." +
                          "..");
                        program = false;
                        break;
                    default: // Invalid input.
                        System.out.println("Input is not valid.");
                        break;
                }
            } else {
                System.out.println("\nInput is not valid.");
            }
        }
    }

    /**
     * Gets the user's inputs.
     * @param reader <i>BufferedReader</i> to read user's input.
     * @return Returns a <i>String</i> containing user's input.
     */
    private static String getUserInput(BufferedReader reader) {
        try {
            return reader.readLine();
        } catch (IOException e) {
            System.out.println("Input failed, inputting blank instead.");
            return "";
        }
    }

    /**
     * Insert a new empty car after the cursor, cursor it set to the new car.
     * @param reader <i>BufferedReader</i> to read user's input.
     * @param list <i>TrainLinkedList</i> where car is inserted into.
     * @throws NumberFormatException Thrown when <i>double</i> isn't inputted
     * correctly.
     * @throws IllegalArgumentException Thrown when input isn't in range.
     */
    private static void insertCar(BufferedReader reader, TrainLinkedList list)
      throws NumberFormatException, IllegalArgumentException {
        System.out.print("Enter car length in meters: ");
        double length = Double.parseDouble(getUserInput(reader));
        if (length < 0) {
            throw new IllegalArgumentException("Length can't be less than 0.");
        }
        System.out.print("Enter car weight in tons: ");
        double weight = Double.parseDouble(getUserInput(reader));
        if (weight < 0) {
            throw new IllegalArgumentException("Weight can't be less than 0.");
        }
        System.out.println(); // Empty line.

        list.insertAfterCursor(new TrainCar(weight,length));

        System.out.println("New train car " + length + " meters " + weight +
          " tons inserted into train.");
    }

    /**
     * Sets the product load at the current position in the list.
     * @param reader <i>BufferedReader</i> to read user's input.
     * @param list <i>TrainLinkedList</i> that is being manipulated.
     * @throws NumberFormatException Thrown when <i>double</i> isn't inputted
     * correctly.
     * @throws IllegalArgumentException Thrown if user input isn't in range
     * or if there already exist a load in the <i>TrainCar</i> object.
     */
    private static void setLoad(BufferedReader reader, TrainLinkedList list)
      throws NumberFormatException, IllegalArgumentException {
        if (list.size() == 0) {
            throw new IllegalArgumentException("There is no car.");
        }

        if (!list.getCursorData().isEmpty()) {
            throw new IllegalArgumentException("There is already a load in " +
              "this car.");
        }

        System.out.print("Enter product name: ");
        String name = getUserInput(reader);
        System.out.print("Enter product weight in tons: ");
        double weight = Double.parseDouble(getUserInput(reader));
        if (weight < 0) {
            throw new IllegalArgumentException("Weight can't be less than 0.");
        }
        System.out.print("Enter product value in dollars: ");
        double value = Double.parseDouble(getUserInput(reader));
        if (value < 0) {
            throw new IllegalArgumentException("Value can't be less than 0.");
        }
        System.out.print("Enter is product dangerous? (y/n) ");
        String danger = getUserInput(reader).toLowerCase(); // Case-insensitive.

        // Checks if input for danger is valid.
        boolean isDangerous;
        if (danger.equals("y")) {
            isDangerous = true;
        } else if (danger.equals("n")) {
            isDangerous = false;
        } else {
            throw new IllegalArgumentException("Invalid input.");
        }

        // Create a clone of the original TrainCar object.
        TrainCar car = new TrainCar(list.getCursorData().getCarWeight(),
          list.getCursorData().getCarLength());

        car.setProductLoad(new ProductLoad(name,weight,value,isDangerous));
        list.setCursorData(car);

        System.out.println("\n" + weight + " tons of " + name + " added to " +
          "the current car.");
    }
}
