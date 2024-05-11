/**
 * @author
 * Yugui Ke
 * 113778667
 * Recitation 3
 * yugui.ke@stonybrook.edu
 */

public class FullPlannerException extends Exception {
    /**
     * Called when Planner object's size() bigger than MAX_COURSES - 1
     */
    public FullPlannerException() {
        super("Full Planner Exception");
    }

    /**
     * Called when Planner object's size() bigger than MAX_COURSES - 1
     * @param message Message passed to super.
     */
    public FullPlannerException(String message) {
        super(message);
    }
}
