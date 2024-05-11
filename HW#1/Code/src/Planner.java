/**
 * @author
 * Yugui Ke
 * 113778667
 * Recitation 3
 * yugui.ke@stonybrook.edu
 */

public class Planner {
    private Course[] planner;
    private final int MAX_COURSES = 50;
    private int size;

    /**
     * Constructs an instance of the Planner with no Course objects in it. <br>
     * <i>Postcondition:<br></i>
     * This Planner has been initialized to an empty list of Courses.
     */
    public Planner() {
        planner = new Course[MAX_COURSES+1];
        size = 0;
    }

    /**
     * Determines the number of courses currently in the list. <br>
     * <i>Preconditions:<br></i>
     * This Planner has been instantiated.
     * @return Returns int representing the size.
     */
    public int size() {
        return size;
    }

    /**
     * Adds a course to the planner at a specific position.
     *
     * @param newCourse Course object to be added.
     * @param position Position where it should be added.<br>
     *                 <i>Preconditions:</i><br>
     *                 This Course object has been instantiated and 1 ≤
     *                 position ≤ items_currently_in_list + 1. The number of
     *                 Course objects in this Planner is less than MAX_COURSES.
     *                 <br><i>Postconditions:</i><br>
     *                 The new Course is now listed in the correct preference
     *                 on the list. All Courses that were originally greater
     *                 than or equal to position are moved back one position.
     * @throws IllegalArgumentException Indicates position isn't valid.
     * @throws FullPlannerException Indicates that the planner is full.
     */
    public void addCourse(Course newCourse, int position)
          throws IllegalArgumentException, FullPlannerException {
        if (position < 1 || position > size() + 1) {
            throw new IllegalArgumentException();
        }
        if (size() > MAX_COURSES - 1) {
            throw new FullPlannerException();
        }
        Course[] newPlanner = new Course[MAX_COURSES+1]; // Creates a new array.
        for (int i = 1; i < position; i++) {
            newPlanner[i] = planner[i];
        } // Gets all courses before position.
        newPlanner[position] = newCourse; // Adds the new course.
        for (int i = position + 1; i < size() + 2; i++) {
            newPlanner[i] = planner[i-1];
        } // Gets all course after position.
        planner = newPlanner; // Replaces current planner.
        size++;
    }

    /**
     * Add a course at the end of the planner.
     *
     * @param newCourse Course to be added.
     * @throws FullPlannerException Indicates that the planner is full.
     * @throws IllegalArgumentException Indicates that position to insert
     *   course isn't valid. (Shouldn't happen with this method).
     */
    public void addCourse(Course newCourse) throws FullPlannerException,
      IllegalArgumentException{
        // Uses the other method to add the course at the end.
        addCourse(newCourse,size() + 1);
    }

    /**
     * Remove a course at a given position.
     *
     * @param position position of where course should be removed.<br>
     *                 <i>Preconditions:</i><br>
     *                 This Planner has been instantiated and 1 ≤ position ≤
     *                 items_currently_in_list.    <br>
     *                 <i>Postconditions:</i><br>
     *                 The Course at the desired position has been removed.
     *                 All Courses that were originally greater than or equal
     *                 to position are moved backward one position.
     * @throws IllegalArgumentException Indicates that position given doesn't
     *   have a course.
     */
    public void removeCourse(int position) throws IllegalArgumentException {
        if (position < 1 || position > size() + 1) {
            throw new IllegalArgumentException();
        }
        Course[] newPlanner = new Course[MAX_COURSES+1];
        for (int i = 1; i < size() + 1; i++) {
            if (i < position) { // Gets all the courses before the position.
                newPlanner[i] = (Course)planner[i].clone();
            }
            // i == position is skipped here.
            if (i > position) { // Gets all the courses after the position.
                newPlanner[i-1] = (Course)planner[i].clone();
            }
        }
        planner = newPlanner;
        size--;
    }

    /**
     * Gets a course at a position.
     *
     * @param position Position of the course in the planner.<br>
     *                 <i>Preconditions:</i><br>
     *                 The Planner object has been instantiated and 1 ≤
     *                 position ≤ items_currently_in_list
     * @return Returns a Course object.
     * @throws IllegalArgumentException Course doesn't exist at that position.
     */
    public Course getCourse(int position) throws IllegalArgumentException {
        if (position < 1 || position > size()) {
            throw new IllegalArgumentException();
        }
        return planner[position];
    }

    /**
     * Prints out the Course list where the department matches the given.
     * @param planner Planner that provides the list of Courses.
     * @param department Department to match with.<br>
     *                   <i>Preconditions:</i><br>
     *                   This Planner object has been instantiated.
     *                   <i>Postconditions:</i><br>
     *                   Displays a neatly formatted table of each course
     *                   filtered from the Planner. Keep the preference numbers
     *                   the same.
     */
    public static void filter(Planner planner, String department) {
        String output = "No. Course Name               Department Code " +
          "Section Instructor\n";
        output += "-------------------------------------------" +
          "------------------------------------\n";
        for (int i = 1; i < planner.size() + 1; i++) {
            try { // Loops through all courses and adding matched one into
                // the String.
                if (planner.getCourse(i).getDepartment().equals(department)) {
                    output += String.format("%3s %-26s%-11s%4s %7s %-1s", i,
                      planner.getCourse(i).getName(),
                      planner.getCourse(i).getDepartment(),
                      planner.getCourse(i).getCode(),
                      planner.getCourse(i).getPrintableSection(),
                      planner.getCourse(i).getInstructor());
                    output += "\n";
                }
            } catch (IllegalArgumentException e) {
                break; // Ends the loop if position is invalid.
            }
        }

        System.out.println(output);
    }

    /**
     * Check if a Course exist in Planner.
     * @param course Course to check for.<br>
     *               <i>Preconditions:</i><br>
     *               This Planner and Course has both been instantiated.<br>
     * @return Returns true if Course exists in Planner, otherwise false.
     */
    public boolean exists(Course course) {
        for (int i = 1; i < size() + 1; i++) {
            if (planner[i].equals(course)) {
                return true;
            } // Simply loops through the array and comparing them.
        }
        return false;
    }

    /**
     * Clones the current Planner object. <br>
     *     <i>Preconditions:</i><br>
     *         This Planner object has been instantiated.
     * @return Returns an Object that is the clone of this Planner.
     */
    public Object clone() {
        Planner p = new Planner();
        for (int i = 1; i < size() + 1; i++) {
            try {
                // Clone course for deep cloning.
                p.addCourse((Course)getCourse(i).clone());
            } catch (FullPlannerException e) {
                // This shouldn't happen if the original object has the same
                // MAX_COURSES
                System.out.println("Planner is full.");
            } catch (IllegalArgumentException e) {
                i = size() + 1; // Ends the loop when position becomes invalid.
            }
        }
        return (Object)p;
    }

    /**
     * Prints out all Courses in this Planner.<br>
     *     <i>Preconditions:</i><br>
     *         This Planner has been instantiated.<br>
     *     <i>Postconditions:</i><br>
     *         Displays a neatly formatted table of each course from the
     *         Planner.
     */
    public void printAllCourses() {
        System.out.println(toString());
    }

    /**
     * Organizes this Planner's information into a table as a String.
     *
     * @return Returns a String consisting of a table.
     */
    public String toString() {
        String output = "No. Course Name               Department Code " +
          "Section Instructor\n";
        output += "-------------------------------------------" +
          "------------------------------------\n";
        // Loops through all Courses and extracting their information into
        // Strings.
        for (int i = 1; i < size() + 1; i++) {
            output += String.format("%3s %-26s%-11s%4s %7s %-1s",i,
              planner[i].getName(),planner[i].getDepartment(),
              planner[i].getCode(),planner[i].getPrintableSection(),
              planner[i].getInstructor());
            output += "\n";
        }
        return output;
    }
}
