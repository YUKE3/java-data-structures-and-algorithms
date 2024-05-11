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

public class PlannerManager {
    /**
     * Main method.
     * @param args Not used.
     */
    public static void main(String[] args) {
        // Instantiates bufferedReader for userInput.
        InputStreamReader streamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(streamReader);

        boolean program = true; // A variable to keep program running.

        // Other variables needed for the program.
        String input = "";
        Planner planner = new Planner();
        Planner backup = new Planner();

        while (program) {
            System.out.println("\n(A) Add Course\n(G) Get Course\n" +
              "(R) Remove Course\n(P) Print Courses in Planner\n" +
              "(F) Filter by Department Code\n(L) Look For Course\n" +
              "(S) Size\n(B) Backup\n(PB) Print Courses in Backup\n" +
              "(RB) Revert to Backup\n(Q) Quit\n\n"); // Prints Menu.
            System.out.print("Enter a selection: ");
            // Makes input for selection case-insensitive.
            input = getUserInput(bufferedReader).toLowerCase();
            System.out.println(); // Blank line.
            if (input.length() == 1) {
                switch(input.charAt(0)) {
                    case 'a': // Add Course.
                        Course newCourse = null;
                        try { // Creates a newCourse object.
                            newCourse = createNewCourse(bufferedReader);
                        } catch (NegativeNumberException e) {
                            System.out.println("\nThat field cannot be " +
                              "negative.");
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("\nInput expects a valid " +
                                                       "number.");
                            break;
                        } catch (DepartmentCodeException e) {
                            System.out.println("\nDepartment code not valid.");
                            break;
                        }

                        System.out.print("Enter position: ");
                        try {
                            planner.addCourse(newCourse,Integer.parseInt(
                              getUserInput(bufferedReader)));
                        } catch (FullPlannerException e) {
                            System.out.println("\nPlanner is full.");
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println("\nPosition wasn't valid.");
                            break;
                        }

                        System.out.println("\n" + newCourse.getDepartment() +
                          " " + newCourse.getCode() + "." +
                          newCourse.getPrintableSection() + " successfully " +
                          "added to the planner.");
                        break;
                    case 'g': // Gets course and print it out.
                        System.out.print("Enter position: ");
                        String output = "No. Course Name               Depa" +
                          "rtment Code Section Instructor\n";
                        output += "------------------------------------------" +
                          "-------------------------------------\n";
                        try {
                            // Gets information from a course picked by user.
                            int index = Integer.parseInt(
                              getUserInput(bufferedReader));
                            Course course = planner.getCourse(index);
                            output += String.format("%3s %-26s%-11s%4s %7s %-1s"
                              ,index,course.getName(),course.getDepartment(),
                              course.getCode(),course.getPrintableSection(),
                              course.getInstructor());
                        } catch (IllegalArgumentException e) {
                            System.out.println("\nPosition is invalid.");
                            break;
                        }
                        System.out.println("\n" + output);
                        break;
                    case 'r': // Removes a Course at a given position.
                        System.out.print("Enter position: ");
                        try {
                            int index = Integer.parseInt(getUserInput(
                              bufferedReader));
                            // Create a temp Course for writing output message.
                            Course temp = planner.getCourse(index);
                            planner.removeCourse(index);
                            System.out.println("\n" + temp.getDepartment() +
                              " " + temp.getCode() + "." +
                              temp.getPrintableSection() + " successfully " +
                              "removed from the planner.");
                        } catch (IllegalArgumentException e) {
                            System.out.println("\nPosition is not valid.");
                            break;
                        }
                        break;
                    case 'p': // Prints out all the Courses.
                        System.out.println("Planner:");
                        planner.printAllCourses();
                        break;
                    case 'f': // Prints out a filtered list of Courses.
                        System.out.print("Enter department code: ");
                        Planner.filter(planner,getUserInput(bufferedReader));
                        break;
                    case 'l': // Search for a Courses.
                        try {
                            // Creates an identical one.
                            Course search = createNewCourse(bufferedReader);
                            // Loops through planner to find an equal.
                            for (int i = 1; i < planner.size() + 1; i++) {
                                try {
                                    if (planner.getCourse(i).equals(search)) {
                                        System.out.println("\n" +
                                          search.getDepartment() + " " +
                                          search.getCode() + "." +
                                          search.getPrintableSection() + " is" +
                                          " found in the planner at position " +
                                          i + ".");
                                        break;
                                    }
                                } catch (IllegalArgumentException e) {
                                    // Ends the loop when position becomes
                                    // invalid, though it shouldn't happen.
                                    System.out.println("Not found.");
                                    break;
                                }
                                if (i == planner.size()) {
                                    // Last loop.
                                    System.out.println("\nNot found.");
                                }
                            }
                        } catch (NegativeNumberException e) {
                            System.out.println("\nThat field cannot be " +
                              "negative.");
                            break;
                        } catch (DepartmentCodeException e) {
                            System.out.println("\nDepartment code invalid.");
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("\nInput expects a valid " +
                                                       "number.");
                        }
                        break;
                    case 's': // Prints the size of planner.
                        System.out.println("\nThere are " + planner.size() +
                          " courses in the planner.");
                        break;
                    case 'b': // Create a clone of planner into backup.
                        backup = (Planner)planner.clone();
                        System.out.println("Created a backup of the current " +
                          "planner.");
                        break;
                    case 'q': // Ends the program.
                        program = false; // Ends the while loop.
                        System.out.print("\nProgram terminating successfully." +
                          "..");
                        break;
                    default: // If char isn't any of the ones above.
                        System.out.println("\nNot an option.");
                        break;
                }
            } else if (input.equals("pb")) { // Print Courses in Backup.
                System.out.println("Planner (Backup):");
                backup.printAllCourses();
            } else if (input.equals("rb")) { // Sets planner to clone of backup.
                planner = (Planner)backup.clone();
                System.out.println("Planner successfully reverted to the " +
                  "backup copy.");
            } else { // If selection isn't any of the choices.
                System.out.println("\nNot an option.");
            }
        }
    }

    /**
     * Gets the user's input.
     * @param reader Used to read user's input.
     * @return Returns a String containing user's input.
     */
    private static String getUserInput(BufferedReader reader) {
        try {
            return reader.readLine();
        } catch (IOException e) {
            // Catches IO exception, shouldn't happen since it's System.in
            System.out.println("\nInput failed, inputting blank.");
            return "";
        }
    }

    /**
     * Creates a new Course object from user input.
     *
     * @param bufferedReader Used to read user's input.
     * @return Returns a Course object.
     * @throws NegativeNumberException Indicates a field was looking for
     *   positive number.
     * @throws NumberFormatException Indicates a field was looking for a number.
     */
    private static Course createNewCourse(BufferedReader bufferedReader) throws
      NegativeNumberException, NumberFormatException, DepartmentCodeException {
        // Creates an "empty" Course.
        Course newCourse = new Course("","",0, (byte) 0,"");

        System.out.print("Enter course name: ");
        newCourse.setName(getUserInput(bufferedReader));

        System.out.print("Enter department: ");
        newCourse.setDepartment(getUserInput(bufferedReader));

        System.out.print("Enter course code: ");
        newCourse.setCode(Integer.parseInt(getUserInput(bufferedReader)));

        System.out.print("Enter course section: ");
        newCourse.setSection(Byte.parseByte(getUserInput(bufferedReader)));

        System.out.print("Enter instructor: ");
        newCourse.setInstructor(getUserInput(bufferedReader));

        return newCourse;
    }
}
